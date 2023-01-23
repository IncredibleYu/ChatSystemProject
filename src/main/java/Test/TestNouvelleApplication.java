package Test;

import Controllers.Controller;
import Controllers.UserManager;
import Packet.Packet;
import Protocols.UDPSender;
import Views.AuthentificationView;

public class TestNouvelleApplication
{
    public static void main(String[] args)
    {
        Controller app = new Controller();

        UDPSender udp = new UDPSender("CLIENT", app);
        udp.start();
        app.setUdpSender(udp);
        Packet packet = new Packet();
        packet.setMessage("Presence");
        udp.broadcast(packet);
        System.out.println("[SERVER] Broadcast envoyé");

        System.out.println("[SERVER] Waiting for response...");
        System.out.println("[SERVER] Response received.");

        new AuthentificationView(app);
    }
}
