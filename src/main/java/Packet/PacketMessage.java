package Packet;
import Message.Message;
public class PacketMessage extends Packet{
    private String message;
    public PacketMessage(String ipAddress, String message1) {
        super(ipAddress, "");
        this.message = message1;
    }

    public String getMessage() {
        return message;
    }
}
