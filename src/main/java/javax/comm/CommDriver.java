package javax.comm;

interface CommDriver
{
    public void initialize();

    public CommPort getCommPort(String portName, int portType);
}
