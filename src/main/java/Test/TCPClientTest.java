package Test;

import Prococols.TCPClient;
import java.io.IOException;

public class TCPClientTest
{
    public static void main(String[] args) throws IOException
    {
        TCPClient myTCPClient = new TCPClient();
        myTCPClient.run();
        myTCPClient.closeConnection();
    }
}
