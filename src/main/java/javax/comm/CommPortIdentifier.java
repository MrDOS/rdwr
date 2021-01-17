package javax.comm;

import java.io.FileDescriptor;
import java.util.Enumeration;

public class CommPortIdentifier
{
    public static final int PORT_SERIAL = 1;
    public static final int PORT_PARALLEL = 2;

    private final String name;
    private final CommPort port;
    private final int type;
    private final CommDriver driver;

    public CommPortIdentifier(String name, CommPort port, int type, CommDriver driver)
    {
        this.name = name;
        this.port = port;
        this.type = type;
        this.driver = driver;
    }

    public static Enumeration getPortIdentifiers()
    {
        /* TODO */
        return null;
    }

    public static CommPortIdentifier getPortIdentifier(String portName) throws NoSuchPortException
    {
        /* TODO */
        return null;
    }

    public static CommPortIdentifier getPortIdentifier(CommPort port) throws NoSuchPortException
    {
        /* TODO */
        return null;
    }

    public static void addPortName(String portName, int portType, CommDriver driver)
    {
        /* TODO */
    }

    public CommPort open(String appname, int timeout) throws PortInUseException
    {
        /* TODO */
        return null;
    }

    public CommPort open(FileDescriptor fd) throws UnsupportedCommOperationException
    {
        /* TODO */
        return null;
    }

    public String getName()
    {
        return this.name;
    }

    public int getPortType()
    {
        return this.type;
    }

    public String getCurrentOwner()
    {
        /* TODO */
        return null;
    }

    public boolean isCurrentlyOwned()
    {
        /* TODO */
        return false;
    }

    public void addPortOwnershipListener(CommPortOwnershipListener listener)
    {
        /* TODO */
    }

    public void removePortOwnershipListener(CommPortOwnershipListener listener)
    {
        /* TODO */
    }
}
