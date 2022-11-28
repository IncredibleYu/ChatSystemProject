package Model;

public class User {
    private int id;
    private String pseudo;
    private String addressIP;
    private int port;

    public User(int id, String pseudo, String addressIP, int port)
    {
        this.id = id;
        this.pseudo = pseudo;
        this.addressIP = addressIP;
        this.port = port;
    }

    public int getId() {
        return id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getAddressIP() {
        return addressIP;
    }

    public int getPort() {
        return port;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setAddressIP(String addressIP) {
        this.addressIP = addressIP;
    }

    public void setPort(int port) {
        this.port = port;
    }

}
