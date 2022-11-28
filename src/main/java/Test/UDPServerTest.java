package Test;
import Packet.Packet;
import Thread.*;

import java.io.IOException;

public class UDPServerTest {
    ThreadUDPReceiver receiver;

    public void setup() throws IOException {
        /*ThreadUDPSender myThread = new ThreadUDPSender();
        Packet packet = new Packet("127.0.0.1", "Hello world!");
        myThread.broadcast(packet);*/
    }

    public static void main(String[] args) throws IOException {
        ThreadUDPSender myThread =  new ThreadUDPSender();
        Packet packet = new Packet("localhost", "Hello world!");
        myThread.broadcast(packet);
        System.out.println("Test");
    }
}
