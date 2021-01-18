package javax.comm;

public class MacPlatform implements Platform
{
    private final MacCommDriver commDriver = new MacCommDriver();

    @Override
    public CommDriver getCommDriver()
    {
        return this.commDriver;
    }
}
