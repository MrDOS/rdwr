package javax.comm;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MacCommDriver implements CommDriver
{
    private static final Logger log = LoggerFactory.getLogger(MacCommDriver.class);

    private static final Path DEV_DIR = new File("/dev").toPath();
    private static final Pattern PORT_PATTERN = Pattern.compile("^(cu|tty)");

    @Override
    public void initialize()
    {
        try
        {
            Files.list(MacCommDriver.DEV_DIR)
                 .filter(p -> PORT_PATTERN.matcher(p.getFileName().toString()).matches())
                 .forEach(p -> CommPortIdentifier.addPortName(p.toString(), CommPortIdentifier.PORT_SERIAL, this));
        }
        catch (IOException e)
        {
            log.error("Encountered an error listing available serial ports. " +
                      "rdwr's enumeration of available ports will be " +
                      "inaccurate!",
                      e);
            return;
        }
    }

    @Override
    public CommPort getCommPort(String portName, int portType)
    {
        return null;
    }
}
