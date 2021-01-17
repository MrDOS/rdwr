package javax.comm;

import java.io.FileDescriptor;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.TreeMap;

public class CommPortIdentifier
{
    public static final int PORT_SERIAL = 1;
    public static final int PORT_PARALLEL = 2;

    private static final Platform PLATFORM = PlatformProvider.getPlatform();
    private static final TreeMap<String, CommPortIdentifier> PORTS = new TreeMap<>();

    private final String name;
    private final int type;
    private final CommDriver driver;
    private final HashSet<CommPortOwnershipListener> ownershipListeners = new HashSet<>();

    String owner = null;
    private CommPort port = null;

    public CommPortIdentifier(String name, int type, CommDriver driver)
    {
        this.name = name;
        this.type = type;
        this.driver = driver;
    }

    @Override
    public String toString()
    {
        return "CommPortIdentifier[name=" + this.name + ";type=" + this.type + ";driver=" + this.driver + "]";
    }

    public static Enumeration<CommPortIdentifier> getPortIdentifiers()
    {
        /* A major hole in the Java Communications API is that it assumes that
         * serial port availability doesn't change over time. The original
         * comments on `CommDriver.initialize()` imply that it will be called
         * only once, by a static initializer on this class. That's not really
         * how our world full of USB serial port interfaces works any more.
         * Fortunately, because we control both ends of the interface and
         * application-level code isn't supposed to call
         * `CommDriver.initialize()`, we'll ensure the implementation is safe
         * to call repeatedly, and invoke it before any operations which use
         * the port map.
         *
         * TODO: Handle port removal, including listener cleanup.
         *
         * TODO: Consider the concurrency implications of returning a view into
         * the port map. Perhaps better to return a copy of the collection. */
        synchronized (CommPortIdentifier.PORTS)
        {
            CommPortIdentifier.PLATFORM.getCommDriver().initialize();

            return Collections.enumeration(CommPortIdentifier.PORTS.values());
        }
    }

    public static CommPortIdentifier getPortIdentifier(String portName) throws NoSuchPortException
    {
        synchronized (CommPortIdentifier.PORTS)
        {
            CommPortIdentifier.PLATFORM.getCommDriver().initialize();

            CommPortIdentifier identifier = CommPortIdentifier.PORTS.get(portName);
            if (identifier == null)
            {
                throw new NoSuchPortException();
            }
            return identifier;
        }
    }

    public static CommPortIdentifier getPortIdentifier(CommPort port) throws NoSuchPortException
    {
        synchronized (CommPortIdentifier.PORTS)
        {
            /* No point in calling `CommDriver.initialize()` here; any new
             * ports won't be open, so they couldn't possibly be what we're
             * looking for.
             *
             * TODO: Reconsider whether we need to reinitialize the port list
             * here after adding support for port removal. */

            for (CommPortIdentifier identifier : CommPortIdentifier.PORTS.values())
            {
                if (identifier.port == port)
                {
                    return identifier;
                }
            }

            throw new NoSuchPortException();
        }
    }

    public static void addPortName(String portName, int portType, CommDriver driver)
    {
        if (CommPortIdentifier.PORTS.containsKey(portName))
        {
            return;
        }

        CommPortIdentifier identifier = new CommPortIdentifier(portName, portType, driver);
        CommPortIdentifier.PORTS.put(portName, identifier);
    }

    public CommPort open(String appname, int timeout) throws PortInUseException
    {
        if (this.isCurrentlyOwned())
        {
            /* Ask the current owner to relinquish the port. */
            for (CommPortOwnershipListener listener : this.ownershipListeners)
            {
                listener.ownershipChange(CommPortOwnershipListener.PORT_OWNERSHIP_REQUESTED);
            }

            if (this.isCurrentlyOwned())
            {
                /* The current owner either isn't listening for ownership
                 * events, or they ignored us. */
                throw new PortInUseException();
            }
        }

        /* Set the owner before the port: if the port constructor fails, it
         * should release ownership. And we wouldn't want to immediately reset
         * the ownership! */
        this.owner = appname;
        this.port = this.driver.getCommPort(this.name, this.type);
        return this.port;
    }

    public CommPort open(FileDescriptor fd) throws UnsupportedCommOperationException
    {
        throw new UnsupportedCommOperationException();
    }

    public String getName()
    {
        return this.name;
    }

    public int getPortType()
    {
        return this.type;
    }

    /**
     * Forcibly remove the current ownership information from the port
     * identifier. This is called internally by {@link CommPort#close()}, and
     * is not really intended to be called from outside of the library – hence
     * the default access.
     */
    void releaseOwnership()
    {
        this.owner = null;
    }

    public String getCurrentOwner()
    {
        return this.owner;
    }

    public boolean isCurrentlyOwned()
    {
        return this.owner != null;
    }

    public void addPortOwnershipListener(CommPortOwnershipListener listener)
    {
        this.ownershipListeners.add(listener);
    }

    public void removePortOwnershipListener(CommPortOwnershipListener listener)
    {
        this.ownershipListeners.remove(listener);
    }
}
