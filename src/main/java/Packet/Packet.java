package Packet;

public class Packet {
    private String ipAddress;
    private String broadcastMessage;

    public Packet(String ipAddress, String broadcastMessage){
        this.ipAddress=ipAddress;
        this.broadcastMessage = broadcastMessage;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getMessage() {
        return broadcastMessage;
    }

    public void setMessage(String newBroadCastMessage)
    {
        this.broadcastMessage = newBroadCastMessage;
    }
}
