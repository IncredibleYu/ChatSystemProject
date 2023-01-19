package Models;


import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class Message {
    /*dans user il y a ipaddress port pseudo*/
    private User emetteur;
    private User destinataire;
    private String contenu;
    private String date;

    public Message(User emetteur, User destinataire, String contenu) {
        this.setEmetteur(emetteur);
        this.setDestinataire(destinataire);
        this.setContenu(contenu);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        this.setDate(dateFormat.format(new Date()));
    }



    public Message(String msg) {
        this.setData(msg);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        this.setTime(dateFormat.format(new Date()));
    }

    public Message() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        this.setTime(dateFormat.format(new Date()));
    }


    String getDate() {
        return date;
    }

    private void setTime(String string) {
        this.date = string;
    }


    public void setTimeString(String date) {
        this.date = date;
    }

    public void setData(String data) {
        this.contenu = data;
    }

    public String getData() {
        return contenu;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String msg) {
        // TODO Auto-generated method stub
        this.contenu = msg;
    }

    public User getEmetteur() {
        return this.emetteur;
    }

    public void setEmetteur(User user) {
        this.emetteur = user;
    }

    public User getDestinataire() {
        return this.destinataire;
    }

    public void setDestinataire(User destinataire) {
        this.destinataire = destinataire;
    }



    public String toString() {
        String smsg= "Sender: "+this.getEmetteur()+"\n"+"Receiver:  "+this.getDestinataire()+"\n"
                +"Time:  "+ this.getDate()+"\n"+ "Data:  "+this.getData()+"\n";
        return smsg;
    }



    public static Message toMessage(String smsg) {
        String[] paramsg=smsg.split("\n");
        User sender= User.toUser(paramsg[0].split(":")[1]);
        User receiver= User.toUser(paramsg[1].split(":")[1]);
        String[] fulldate=paramsg[2].split(":");
        String date= (fulldate[1]+":"+fulldate[2]);
        //typemsg type=toTypemsg(paramsg[3].split(":")[1]);
        String [] tabdata=paramsg[3].split(":");
        String data="";
        for (int i=1;i<tabdata.length;i++) {
            data+=tabdata[i];
        }
        return new Message(sender,receiver,data);

    }


    public String getTimeString() {
        return date.toString();
    }

    public String getTime() { return date; }
}

