package javax.comm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Enumeration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CommPortIdentifierTest
{
    @BeforeAll
    public static void setupPlatform()
    {
        PlatformProvider.overridePlatform(new TestPlatform());
    }

    @Test
    public void testListsPorts()
    {
        Enumeration<CommPortIdentifier> ports = CommPortIdentifier.getPortIdentifiers();
        assertEquals("portA", ports.nextElement().getName());
        assertEquals("portB", ports.nextElement().getName());
    }

    @Test
    public void testGetPortIdentifierByName() throws NoSuchPortException
    {
        CommPortIdentifier identifier = CommPortIdentifier.getPortIdentifier("portA");
        assertEquals("portA", identifier.getName());
    }

    @Test
    public void testGetPortIdentifierByPort() throws NoSuchPortException, PortInUseException
    {
        CommPortIdentifier identifier = CommPortIdentifier.getPortIdentifier("portA");
        CommPort port = identifier.open("test", 0);
        assertEquals(identifier, CommPortIdentifier.getPortIdentifier(port));
    }

    @Test
    public void testFailsToGetNonexistantPortIdentifier()
    {
        assertThrows(NoSuchPortException.class, () -> CommPortIdentifier.getPortIdentifier("portZ"));
    }

    @Test
    public void testOpensPort() throws NoSuchPortException, PortInUseException
    {
        CommPortIdentifier identifier = CommPortIdentifier.getPortIdentifier("portB");
        identifier.open("test", 0);
    }

    @Test
    public void testFailsToOpenPortTwice() throws NoSuchPortException, PortInUseException
    {
        CommPortIdentifier identifier = CommPortIdentifier.getPortIdentifier("portC");
        identifier.open("testA", 0);
        assertThrows(PortInUseException.class, () -> identifier.open("testB", 0));
    }

    @Test
    public void testOpeningPortPropagatesOwnershipRequest() throws NoSuchPortException, PortInUseException
    {
        CommPortIdentifier identifier = CommPortIdentifier.getPortIdentifier("portD");

        CommPortOwnershipListener listener = mock(CommPortOwnershipListener.class);
        identifier.addPortOwnershipListener(listener);

        identifier.open("testA", 0);
        /* Our mock of the port can't actually close and release the port, but
         * that's fine; we'll just swallow the exception. The important part
         * is... */
        assertThrows(PortInUseException.class, () -> identifier.open("testB", 0));
        /* ...whether the listener callback was called. */
        verify(listener).ownershipChange(CommPortOwnershipListener.PORT_OWNERSHIP_REQUESTED);
    }

    static class TestPlatform implements Platform
    {
        @Override
        public CommDriver getCommDriver()
        {
            return new TestCommDriver();
        }
    }

    static class TestCommDriver implements CommDriver
    {
        @Override
        public void initialize()
        {
            for (String portName : new String[] {"portA",
                                                 "portB",
                                                 "portC",
                                                 "portD"})
            {
                CommPortIdentifier.addPortName(portName, CommPortIdentifier.PORT_SERIAL, this);
            }
        }

        @Override
        public CommPort getCommPort(String portName, int portType)
        {
            CommPort port = mock(CommPort.class);
            when(port.getName()).thenReturn(portName);
            return port;
        }
    }
}
