package javax.comm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Enumeration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@EnabledOnOs(OS.MAC)
public class MacPlatformTest
{
    private static final Logger log = LoggerFactory.getLogger(MacPlatformTest.class);

    @Test
    public void testLoadsMacPlatform()
    {
        Platform platform = PlatformProvider.getPlatform();
        assertEquals(MacPlatform.class, platform.getClass());
    }

    @Test
    public void testLoadsMacCommDriver()
    {
        CommDriver driver = PlatformProvider.getPlatform().getCommDriver();
        assertEquals(MacCommDriver.class, driver.getClass());
    }

    @Test
    public void testFindsSerialPorts()
    {
        Enumeration<CommPortIdentifier> identifiers = CommPortIdentifier.getPortIdentifiers();
        int portCount = 0;
        while (identifiers.hasMoreElements())
        {
            CommPortIdentifier identifier = identifiers.nextElement();
            log.debug("Found port: {}", identifier);
            ++portCount;
        }
        assertNotEquals(0, portCount);
    }
}
