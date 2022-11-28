package Thread;

import Packet.Packet;

import javax.swing.event.EventListenerList;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ThreadUDPReceiver {
    private DatagramSocket socket;
    private Boolean stop;
    private EventListenerList listeners;

    public ThreadUDPReceiver() {
        this.stop = false;
        listeners = new EventListenerList();
        try {
            this.socket = new DatagramSocket(1234);
        }catch (SocketException e){
            e.printStackTrace();
        }
    }

    public void run(){
        byte[] b = new byte[1024*10];
        Packet p = null;
        while(!this.stop){
            DatagramPacket datagramPacket = new DatagramPacket(b, b.length);
            try {
                socket.receive(datagramPacket);
                ByteArrayInputStream bais = new ByteArrayInputStream(datagramPacket.getData());
                try {
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    p = (Packet) ois.readObject();
                }catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                this.newMessageReceived(datagramPacket.getAddress(),p);
            }catch (SocketException e) {
                System.err.println("Socket closed");
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

        }

    public void newMessageReceived(InetAddress addressSour, Packet p) {
        for (Listener listener: getListeners()){
            listener.aMessageHasBeenReceived(addressSour, p);
        }
    }

    public Listener[] getListeners() {
        return listeners.getListeners(Listener.class);
    }

    public void setStop(Boolean stop) {
        this.stop = stop;
    }

    public void addListener (Listener newlistener){
        this.listeners.add(Listener.class,newlistener);
    }

    public void deleteListener (Listener oldlistener){
        this.listeners.remove(Listener.class,oldlistener);
    }

    public void closeSocket(){
        if (!this.socket.isClosed()){
            this.socket.close();
        }
    }
}

