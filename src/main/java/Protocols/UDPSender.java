package Protocols;

import Controllers.Controller;
import Controllers.UserManager;
import Models.User;
import Packet.Packet;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;


/**
 * Class permettant l'envoi en udp
 *
 */

public class UDPSender extends Thread
{
    private DatagramSocket socket;
    private Controller app;

    //Constructor
    public UDPSender(String name, Controller app)
    {
        super(name);
        this.app = app;

        System.out.println("[UDP] Hello, I am " + this.getName());

        try
        {
            socket = new DatagramSocket(1235);
            //socket.setSoTimeout(1000);
        }
        catch (SocketException e)
        {
            System.err.println("Error in creation socket");
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        byte[] buffer = new byte[1024 * 10];

        while(true)
        {
            try
            {
                DatagramPacket datagramPacket = new DatagramPacket(buffer, 0, buffer.length);
                socket.receive(datagramPacket);
                byte[] b_array = datagramPacket.getData();
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(b_array);

                ObjectInputStream ois = new ObjectInputStream(byteArrayInputStream);
                Packet packet = (Packet) ois.readObject();
                System.out.println(packet.getUser().getPseudo());
                app.getUserManager().addMember(packet.getUser());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void envoiebroadcast(String broadcastMessage, int port) throws IOException {
        for (InetAddress  addrbroadcast : listAllBroadcastAddresses()) {
            DatagramSocket socket = new DatagramSocket();
            socket.setBroadcast(true);
            byte[] buffer = broadcastMessage.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, addrbroadcast, port);
            //System.out.println("Envoi msg en broadcast to"+addrbroadcast);
            socket.send(packet);
            socket.close();
        }
    }

    public static List<InetAddress> listAllBroadcastAddresses() throws SocketException {
        List<InetAddress> broadcastList = new ArrayList<>();
        Enumeration<NetworkInterface> interfaces
                = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();

            if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                continue;
            }

            networkInterface.getInterfaceAddresses().stream()
                    .map(a -> a.getBroadcast())
                    .filter(Objects::nonNull)
                    .forEach(broadcastList::add);
        }
        return broadcastList;
    }


    public static void sendUDP(String msg, int port, String laddr) throws SocketException {

        DatagramSocket socket = new DatagramSocket();

        byte[] buffer = msg.getBytes();
        DatagramPacket packet;
        try {
            packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(laddr), port);
            //System.out.println("Envoi msg");
            socket.send(packet);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcast(Packet packet)
    {
        try
        {
            // create packet send
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutput os = new ObjectOutputStream(outputStream);
            os.writeObject(packet);
            os.close();
            byte[] buffer = outputStream.toByteArray();

            int port = 1234;
            DatagramPacket datagramPacket = new DatagramPacket(buffer, 0, buffer.length);
            datagramPacket.setAddress(InetAddress.getByName("255.255.255.255"));
            datagramPacket.setPort(port);

            // send packet broadcast
            DatagramSocket senderSocket = new DatagramSocket();
            senderSocket.send(datagramPacket);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void closeConnection()
    {
        if (!this.socket.isClosed())
        {
            this.socket.close();
        }
    }


}

