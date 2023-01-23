package Thread;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import model.Message ;

public class ThreadUDPSender
{
    private DatagramSocket socket;

    public ThreadUDPSender()
    {
        try{
            this.socket = new DatagramSocket();
        } catch (SocketException e) {
            System.err.println("Erreur de la création du socket");
            e.printStackTrace();
        }
    }

    public void SendMessage(Message mes)


    public static void broadcast(String message, InetAddress host, int port) throws IOException
    {
        dgramSocket = new DatagramSocket();
        //dgramSocket.setBroadcast(true);

        // Envoie
        DatagramPacket outPacket = new DatagramPacket(message.getBytes(), message.length(), host, port);
        dgramSocket.send(outPacket);

        // Recevoir
        byte[] buffer = new byte[256];
        DatagramPacket inPacket = new DatagramPacket(buffer, buffer.length);
        dgramSocket.receive(inPacket);
        String response = new String(inPacket.getData(), 0,  inPacket.getLength());
        System.out.println(response);

        dgramSocket.close();
    }
}
