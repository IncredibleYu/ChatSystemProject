package Prococols;

import Packet.Packet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPClient
{
    private DatagramSocket socket;
    private Boolean stop;

    public UDPClient()
    {
        this.stop = false;
        this.startConnection();
    }

    public void startConnection()
    {
        try
        {
            this.socket = new DatagramSocket(1234);
        }
        catch (SocketException e)
        {
            e.printStackTrace();
        }
    }

    public void run()
    {
        byte[] b = new byte[1024*10];
        Packet p = new Packet("", "");
        while(!this.stop)
        {
            DatagramPacket packet = new DatagramPacket(b, b.length);
            try
            {
                socket.receive(packet);
                ByteArrayInputStream bais = new ByteArrayInputStream(packet.getData());
                ObjectInputStream ois = new ObjectInputStream(bais);

                p.setMessage("[CLIENT] " + (String) ois.readObject());

                //this.newMessageReceived(datagramPacket.getAddress(),p);
                System.out.println(p.getMessage());
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
    }

    public void closeConnection()
    {
        this.stop = true;
        if (!this.socket.isClosed())
        {
            this.socket.close();
        }
    }
}