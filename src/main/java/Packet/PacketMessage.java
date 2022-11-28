package Packet;
import Message.Message;
public class PacketMessage extends Packet{
    private Message message;
    public PacketMessage(String ipAddress, Message message1) {
        super(ipAddress);
        this.message = message1;
    }

    public Message getMessage() {
        return message;
    }
}
