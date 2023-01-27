package fr.insa.gei.ChatSystemProject;

import fr.insa.gei.ChatSystemProject.Controllers.Controller;
import fr.insa.gei.ChatSystemProject.Models.Packet;
import fr.insa.gei.ChatSystemProject.Protocols.UDPReceiver;
import fr.insa.gei.ChatSystemProject.Protocols.UDPSender;
import fr.insa.gei.ChatSystemProject.Views.AuthentificationView;

public class Main
{
    public static void main(String[] args)
    {
        Controller app = new Controller();

        //UDPSender udp = new UDPSender("CLIENT", app);
        UDPReceiver udp = new UDPReceiver("SERVEUR", app, 1234);
        udp.start();
        app.setUDPReceiver(udp);

        Packet packet = new Packet();
        packet.setMessage("Presence");
        UDPSender.broadcast(packet);
        System.out.println("[SERVER] Broadcast envoyé");

        System.out.println("[SERVER] Waiting for response...");
        System.out.println("[SERVER] Response received.");

        new AuthentificationView(app);
    }
}
