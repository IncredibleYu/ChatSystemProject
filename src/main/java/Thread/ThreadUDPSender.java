package Thread;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.rmi.activation.UnknownObjectException;

import Packet.Packet;

public class ThreadUDPSender
{
    private DatagramSocket socket;

    public ThreadUDPSender()
    {
        try{
            this.socket = new DatagramSocket();
        } catch (SocketException e) {
            System.err.println("Error in creation socket");
            e.printStackTrace();
        }
    }

    public void SendMessage(InetAddress address, Packet p) throws IOException {
        int port = 1234;
        byte[] b = new byte[1024*10];

        DatagramSocket senderSocket = new DatagramSocket();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream os = new ObjectOutputStream(outputStream);
            os.writeObject(p);
            os.close();
            b = outputStream.toByteArray();
        }catch(IOException e){
            e.printStackTrace();
        }

        DatagramPacket datagramPacket = new DatagramPacket(b, b.length);
        datagramPacket.setAddress(InetAddress.getByName(String.valueOf(address)));
        datagramPacket.setPort(port);

        try {
            senderSocket.send(datagramPacket);
        } catch (IOException e){
            System.err.println("Failed with sending message");
            e.printStackTrace();
        }
    }


    public static void broadcast(Packet p) throws IOException {
        int port = 1234;
        byte[] b = new byte[1024*10];

        DatagramSocket senderSocket = new DatagramSocket();

        // create packet send
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream os = new ObjectOutputStream(outputStream);
            os.writeObject(p.getMessage());
            os.close();
            b = outputStream.toByteArray();
        }catch(IOException e){
            e.printStackTrace();
        }

        DatagramPacket datagramPacket = new DatagramPacket(b, b.length);
        datagramPacket.setAddress(InetAddress.getByName("255.255.255.255"));
        datagramPacket.setPort(port);
        // send packet broadcast
        try {
            senderSocket.send(datagramPacket);
        }catch (UnknownHostException e) {
            System.err.println("Unknown Host to broadcast");
            e.printStackTrace();
        }catch (SocketException e) {
            System.err.println("Socket closed");
        }catch (IOException e){
            System.err.println("Failed with sending message by broadcast");
            e.printStackTrace();
        }
    }

    public void closeSocket(){
        if (!this.socket.isClosed()){
            this.socket.close();
        }
    }
}
