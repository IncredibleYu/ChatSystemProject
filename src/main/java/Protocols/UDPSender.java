package Protocols;

import Controllers.Controller;
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
    private Controller app;

    //Constructor
    public UDPSender(String name, Controller app)
    {
        super(name);
        this.app = app;

        System.out.println("[UDP] Hello, I am " + this.getName());
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

    public static void sendResponse(String address, Packet packet) throws IOException
    {
        try
        {
            // create packet send
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(outputStream);
            os.writeObject(packet);
            os.close();

            byte[] buffer = outputStream.toByteArray();
            int port = 1234;
            DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
            System.out.println(address);
            datagramPacket.setAddress(InetAddress.getByName(address));
            datagramPacket.setPort(port);

            // send packet to user
            DatagramSocket senderSocket = new DatagramSocket();
            senderSocket.send(datagramPacket);
            senderSocket.close();
        }
        catch (UnknownHostException e)
        {
            System.err.println("Unknown Host");
            e.printStackTrace();
        }
        catch (SocketException e)
        {
            System.err.println("Socket closed");
        }
        catch (IOException e)
        {
            System.err.println("Failed with sending message");
            e.printStackTrace();
        }
    }

    public static void broadcast(Packet packet)
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
            senderSocket.setBroadcast(true);
            senderSocket.send(datagramPacket);
            senderSocket.close();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}

