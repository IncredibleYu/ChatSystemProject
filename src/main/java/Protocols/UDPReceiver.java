package Protocols;

import Controllers.Controller;
import Controllers.UserManager;
import Models.User;
import Packet.Packet;
import Views.General;

import java.io.*;
import java.net.*;
import java.util.Enumeration;

import static Views.General.setApp;

public class UDPReceiver extends Thread {

    private int cas ;
    private int port;
    private boolean ouvert ;
    private Controller app;

    private DatagramSocket socket;

    public UDPReceiver(String name, Controller app, int port)
    {
        super(name);
        this.app = app;
        this.ouvert = true;
        this.port = port;
    }

    public void run()
    {
        System.out.println("[UDP] Hello, I am " + this.getName());
        try
        {
            this.socket = new DatagramSocket(port);

            while (ouvert)
            {
                byte[] buffer = new byte[1024 * 10];
                DatagramPacket datagramPacket = new DatagramPacket(buffer, 0, buffer.length);
                socket.receive(datagramPacket);
                byte[] b_array = datagramPacket.getData();
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(b_array);

                ObjectInputStream ois = new ObjectInputStream(byteArrayInputStream);

                Packet packet = (Packet) ois.readObject();

                String addressIP = datagramPacket.getAddress().getHostAddress();

                if(!addressIP.equals(UDPReceiver.getCurrentIp().getHostAddress()))
                {
                    // Met à jour l'annuaire
                    if (packet.getMessage().equals("Pseudo"))
                    {
                        System.out.println(packet.getUser().getPseudo());
                        app.getUserManager().addMember(packet.getUser());
                        General.miseAJourContact();
                    }

                    // Envoie "je suis là" seulement si connecté
                    if (packet.getMessage().equals("Presence") && app.getActu() != null)
                    {
                        packet.setMessage("Pseudo");
                        packet.setUser(app.getActu());
                        UDPSender.sendResponse(addressIP, packet);
                    }

                    // Met à jour le pseudo dans l'annuaire
                    if (packet.getMessage().equals("ChangePseudo"))
                    {
                        User user = app.getUserManager().getMemberByIP(addressIP);
                        String oldPseudo = user.getPseudo();
                        String newPseudo = packet.getUser().getPseudo();
                        System.out.println(oldPseudo);
                        user.setPseudo(newPseudo);
                        System.out.println(user.getPseudo());
                        this.app.getDb().updateMessages(oldPseudo, newPseudo);
                        General.miseAJourContact();
                    }

                    if (packet.getMessage().equals("Deconnexion"))
                    {
                        System.out.println(packet.getUser().getPseudo());
                        User userToDelete = app.getUserManager().getMemberByPseudo(packet.getUser().getPseudo());
                        app.getUserManager().deleteMember(userToDelete);
                        General.miseAJourContact();
                    }
                }
            }
        }
        catch (SocketException e)
        {
            System.err.println("[CLIENT] Socket closed");
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static InetAddress getCurrentIp() {
        try {
            Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) networkInterfaces .nextElement();
                Enumeration nias = ni.getInetAddresses();
                while(nias.hasMoreElements()) {
                    InetAddress ia= (InetAddress) nias.nextElement();
                    if (!ia.isLinkLocalAddress() && !ia.isLoopbackAddress() && ia instanceof Inet4Address) {
                        return ia;
                    }
                }
            }
        }
        catch (SocketException e) {
            System.out.println("unable to get current IP " + e.getMessage());
        }
        return null;
    }

    public void closeSocket()
    {
        this.ouvert = false;
        socket.close();
    }
}
