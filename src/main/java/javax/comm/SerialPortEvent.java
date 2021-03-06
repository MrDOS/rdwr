package javax.comm;

import java.util.EventObject;

public class SerialPortEvent extends EventObject
{
    public static final long serialVersionUID = 1L;

    /* _Public access_ is deprecated, not the field itself. */
    @Deprecated
    public int eventType;

    public static final int DATA_AVAILABLE = 1;
    public static final int OUTPUT_BUFFER_EMPTY = 2;
    public static final int CTS = 3;
    public static final int DSR = 4;
    public static final int RI = 5;
    public static final int CD = 6;
    public static final int OE = 7;
    public static final int PE = 8;
    public static final int FE = 9;
    public static final int BI = 10;

    private final boolean oldValue;
    private final boolean newValue;

    public SerialPortEvent(SerialPort srcport, int eventtype, boolean oldvalue, boolean newvalue)
    {
        super(srcport);
        this.eventType = eventtype;
        this.oldValue = oldvalue;
        this.newValue = newvalue;
    }

    public int getEventType()
    {
        return this.eventType;
    }

    public boolean getNewValue()
    {
        return this.newValue;
    }

    public boolean getOldValue()
    {
        return this.oldValue;
    }
}
