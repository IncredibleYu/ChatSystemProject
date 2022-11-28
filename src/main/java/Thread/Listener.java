package Thread;

import Packet.Packet;

import java.net.InetAddress;
import java.util.EventListener;

public interface Listener extends EventListener {
    void aMessageHasBeenReceived(InetAddress addressSour, Packet p);

}
