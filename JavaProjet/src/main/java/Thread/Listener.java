package Thread;

import Packet.Packet;

import java.net.InetAddress;
import java.util.EventListener;

public interface OurMessageListener extends EventListener {
    void aMessageHasBeenReceived(InetAddress addressSour, Packet p);

    void aMessageHasBeenReceivedd(InetAddress addressSour, Packet.Packet p);
}
