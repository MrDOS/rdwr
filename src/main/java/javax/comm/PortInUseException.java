package javax.comm;

public class PortInUseException extends Exception
{
    public static final long serialVersionUID = 1L;

    public String currentOwner;
}
