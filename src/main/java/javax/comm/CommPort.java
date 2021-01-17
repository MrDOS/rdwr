package javax.comm;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class CommPort implements AutoCloseable
{
    protected String name;

    public String getName()
    {
        return this.name;
    }

    @Override
    public String toString()
    {
        return "CommPort[name=" + this.name + "]";
    }

    public abstract InputStream getInputStream() throws IOException;

    public abstract OutputStream getOutputStream() throws IOException;

    @Override
    public void close()
    {
        CommPortIdentifier identifier;
        try
        {
            identifier = CommPortIdentifier.getPortIdentifier(this);
        }
        catch (NoSuchPortException e)
        {
            /* If the port has already been delisted, e.g. due to hardware
             * disconnect, then there's no ownership to clean up. */
            return;
        }
        identifier.releaseOwnership();
    }

    public abstract void enableReceiveThreshold(int thresh) throws UnsupportedCommOperationException;

    public abstract void disableReceiveThreshold();

    public abstract boolean isReceiveThresholdEnabled();

    public abstract int getReceiveThreshold();

    public abstract void enableReceiveTimeout(int rcvTimeout) throws UnsupportedCommOperationException;

    public abstract void disableReceiveTimeout();

    public abstract boolean isReceiveTimeoutEnabled();

    public abstract int getReceiveTimeout();

    public abstract void enableReceiveFraming(int framingByte) throws UnsupportedCommOperationException;

    public abstract void disableReceiveFraming();

    public abstract boolean isReceiveFramingEnabled();

    public abstract int getReceiveFramingByte();

    public abstract void setInputBufferSize(int size);

    public abstract int getInputBufferSize();

    public abstract void setOutputBufferSize(int size);

    public abstract int getOutputBufferSize();
}
