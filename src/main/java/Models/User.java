package Models;

import Protocols.UDPReceiver;

import java.io.Serializable;

public class User  implements Serializable
{
    private static final long serialVersionUID = -4507489610617393544L;

    private int id;
    private String pseudo;
    //private InetAddress addressIP;
    private String addressIP;
    private int port;
    private boolean pseudoIsUsed;

    /**
     * 1) Constructeur d'un User sans attributs
     */
    public User() {
        this.setIP(UDPReceiver.getCurrentIp().getHostAddress());
        this.setPort(1234);
    }

    /**
     * 2) Constructeur d'un User
     * @param address ip de l'utilisateur
     * @param port port de l'utilisateur
     * @param pseudo pseudo l'utilisateur
     */
    public User(String address, int port, String pseudonym) {
        this.setIP(address);
        this.setPort(port);
        this.setPseudo(pseudonym);
    }

    public User(int id, String pseudo, int port)
    {
        this.id = id;
        this.pseudo = pseudo;

        this.setIP(UDPReceiver.getCurrentIp().getHostAddress());
        this.port = port;
        this.pseudoIsUsed = false;
    }

    //-------------------- GETTEURS & SETTEURS -----------------------------//

    public int getId()
    {
        return id;
    }

    public String getPseudo()
    {
        return pseudo;
    }

    /*public InetAddress getAddressIP()
    {
        return addressIP;
    }

    public void setAddressIP(InetAddress addressIP)
    {
        this.addressIP = addressIP;
    }
    */

    public String getIP()
    {
        return addressIP;
    }

    public void setIP(String address)
    {
        this.addressIP = address;
    }

    public int getPort()
    {
        return port;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setPseudo(String pseudo)
    {
        this.pseudo = pseudo;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public boolean pseudoIsUsed() {
        return this.pseudoIsUsed;
    }

    public void setPseudoIsUsed(boolean pseudoIsUsed) {
        this.pseudoIsUsed = pseudoIsUsed;
    }

    //-------------------- Methodes -----------------------------//

    /**
     * Methode pour renvoyer les caracteristiques d'un utilsiateur
     */
    @Override
    public String toString() {
        return "_"+this.pseudo+"_"+this.addressIP+"_"+String.valueOf(this.port);
    }

    /**
     * Methode pour creer un type User a partir de ses caracteristiques de type String recuperee
     * @param s
     * @return User
     */
    public static User toUser(String s) {
        String[] parametersuser=s.split("_");
        //String validate= parametersuser[0];
        String userpseudo = parametersuser[1];
        String userip = parametersuser[2];
        String userport = parametersuser[3];
        User users = new User(userip, Integer.parseInt(userport), userpseudo);
        return users;
    }

}
