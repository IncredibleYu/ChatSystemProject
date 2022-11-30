package Test;

import Prococols.TCPServer;
import java.io.IOException;

public class TCPServerTest
{
    public static void main(String[] args) throws IOException
    {
        TCPServer myTCPServer = new TCPServer();
        myTCPServer.run();
        myTCPServer.closeConnection();
    }
}
