package javax.comm;

class PlatformProvider
{
    static Platform OVERRIDE = null;

    static Platform getPlatform() throws UnsupportedPlatformException
    {
        if (PlatformProvider.OVERRIDE != null)
        {
            return PlatformProvider.OVERRIDE;
        }

        String os = System.getProperty("os.name");

        if (os.contains("Mac"))
        {
            return new MacPlatform();
        }

        throw new UnsupportedPlatformException();
    }

    static void overridePlatform(Platform override)
    {
        PlatformProvider.OVERRIDE = override;
    }
}
