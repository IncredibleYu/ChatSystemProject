package Test;

import Packet.Packet;
import Prococols.UDPServer;

public class UDPServerTest
{
    public static void main(String[] args)
    {
        UDPServer myUDPServer =  new UDPServer();
        Packet packet = new Packet("localhost", "Hello world!");
        myUDPServer.broadcast(packet);
        System.out.println("[SERVER] Broadcast envoyé");
        myUDPServer.closeConnection();

    }
}
