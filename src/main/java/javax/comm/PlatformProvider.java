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

        /* TODO: Platform detection/mapping. */

        throw new UnsupportedPlatformException();
    }

    static void overridePlatform(Platform override)
    {
        PlatformProvider.OVERRIDE = override;
    }
}
