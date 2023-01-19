package Test;

import Controllers.Controller;
import Controllers.UserManager;
import Models.User;
import Packet.Packet;
import Protocols.TCPServer;
import Protocols.UDPReceiver;
import Protocols.UDPSender;

public class TestApplicationDejaOuvert
{
    public static void main(String[] args)
    {
        Controller app = new Controller();

        User myUser = new User(0, "NearFire", 1234);
        app.getUserManager().addMember(myUser);

        UDPReceiver udp = new UDPReceiver("SERVEUR", app);
        TCPServer tcp = new TCPServer("SERVEUR", app);

        udp.start();
        tcp.start();

        //udp.closeConnection();
        //tcp.closeConnection();
    }
}
