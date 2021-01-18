package javax.comm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TooManyListenersException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MacSerialPort extends SerialPort
{
    private static final Logger log = LoggerFactory.getLogger(MacSerialPort.class);

    private final String portName;

    private final FileInputStream in;
    private final FileOutputStream out;

    private boolean open = false;
    private IOException constructorException = null;

    private SerialPortEventListener listener = null;

    MacSerialPort(String portName)
    {
        this.portName = portName;

        /* The interface of the `CommPortIdentifier.getPortIdentifier(String)`
         * method which calls this constructor doesn't permit exceptions to be
         * thrown. We'll try to open the port here, and if we encounter a
         * failure, we'll remember the exception and raise it when the
         * application calls `getInputStream() or `getOutputStream()`, which
         * _are_ permitted to raise exceptions. */
        FileInputStream in = null;
        FileOutputStream out = null;
        try
        {
            File file = new File(portName);
            in = new FileInputStream(file);
            out = new FileOutputStream(file);
            this.open = true;
        }
        catch (IOException e)
        {
            this.constructorException = e;
            /* `CommPort.close()` will release ownership of the port. */
            super.close();
        }
        finally
        {
            this.in = in;
            this.out = out;
        }
    }

    @Override
    public String toString()
    {
        return "MacSerialPort[portName=" + this.portName + ";open=" + this.open + "]";
    }

    @Override
    public void close()
    {
        if (!this.open)
        {
            return;
        }

        super.close();

        try
        {
            this.in.close();
            this.out.close();
        }
        catch (IOException e)
        {
            /* The `CommPort` interface gives us no facility for raising an
             * exception on failure, so we'll log the error and move on. */
            log.error("Error closing port {}!", this.portName, e);
        }
        this.open = false;
    }

    @Override
    public int getBaudRate()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getDataBits()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getStopBits()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getParity()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void sendBreak(int millis)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void setFlowControlMode(int flowcontrol) throws UnsupportedCommOperationException
    {
        // TODO Auto-generated method stub

    }

    @Override
    public int getFlowControlMode()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setSerialPortParams(int baudrate, int dataBits, int stopBits, int parity)
            throws UnsupportedCommOperationException
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void setDTR(boolean dtr)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isDTR()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setRTS(boolean rts)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isRTS()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isCTS()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isDSR()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isRI()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isCD()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void addEventListener(SerialPortEventListener lsnr) throws TooManyListenersException
    {
        if (!this.open)
        {
            return;
        }

        if (this.listener != null)
        {
            throw new TooManyListenersException();
        }

        this.listener = lsnr;
    }

    @Override
    public void removeEventListener()
    {
        this.listener = null;
    }

    @Override
    public void notifyOnDataAvailable(boolean enable)
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void notifyOnOutputEmpty(boolean enable)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void notifyOnCTS(boolean enable)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void notifyOnDSR(boolean enable)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void notifyOnRingIndicator(boolean enable)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void notifyOnCarrierDetect(boolean enable)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void notifyOnOverrunError(boolean enable)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void notifyOnParityError(boolean enable)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void notifyOnFramingError(boolean enable)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void notifyOnBreakInterrupt(boolean enable)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public InputStream getInputStream() throws IOException
    {
        if (this.constructorException != null)
        {
            throw this.constructorException;
        }

        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OutputStream getOutputStream() throws IOException
    {
        if (this.constructorException != null)
        {
            throw this.constructorException;
        }

        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void enableReceiveThreshold(int thresh) throws UnsupportedCommOperationException
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void disableReceiveThreshold()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isReceiveThresholdEnabled()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int getReceiveThreshold()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void enableReceiveTimeout(int rcvTimeout) throws UnsupportedCommOperationException
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void disableReceiveTimeout()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isReceiveTimeoutEnabled()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int getReceiveTimeout()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void enableReceiveFraming(int framingByte) throws UnsupportedCommOperationException
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void disableReceiveFraming()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isReceiveFramingEnabled()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int getReceiveFramingByte()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setInputBufferSize(int size)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public int getInputBufferSize()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setOutputBufferSize(int size)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public int getOutputBufferSize()
    {
        // TODO Auto-generated method stub
        return 0;
    }

}
