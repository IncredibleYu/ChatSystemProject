package Test;

import java.io.IOException;
import Thread.ThreadUDPReceiver;

public class UDPClientTest {
    public static void main(String[] args) throws IOException
    {
        ThreadUDPReceiver myThread = new ThreadUDPReceiver();
        myThread.run();
    }
}
