package Test;

import Prococols.UDPClient;

public class UDPClientTest {
    public static void main(String[] args)
    {
        UDPClient myUDPClient = new UDPClient();
        myUDPClient.run();
        myUDPClient.closeConnection();
    }
}
