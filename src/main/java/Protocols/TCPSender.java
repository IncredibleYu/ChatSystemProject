package Protocols;

import Controllers.Controller;
import Models.Message;
import Models.User;
import Views.General;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPSender extends Thread
{
    private Socket socket;
    private User otheruser;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Controller app;

    public TCPSender(String name, Controller app, Socket socket)
    {
        super(name);
        this.app = app;
        this.socket = socket;
        try
        {
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        this.start();
    }

    public TCPSender(String name, Controller app, User u2)
    {
        super(name);
        this.app = app;
        this.otheruser = u2;
        try
        {
            this.socket = new Socket(u2.getIP(),2000);
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public Controller getApp() {
        return app;
    }

    public User getOtheruser() {
        return otheruser;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    /**Metode pour envoyer les messages**/

    public void SendMessage(String data)
    {
        Message msg = new Message(getApp().getActu(),getOtheruser(), data);
        try
        {
            getOut().writeObject(msg.toString());
            getApp().getDb().addMessage(msg);
            socket.close(); //Fermer le socket
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**Metode pour recevoir les messages**/

    public void run()
    {
        System.out.println("[TCP] Hello, I am " + this.getName());
        Message msg = null;
        try
        {
            String data = (String) getIn().readObject();
            msg = Message.toMessage(data);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        getApp().getDb().addMessage(msg);

        General.displayNotification(" vous a envoyé un message.", socket.getInetAddress().getHostAddress());
        General.display(msg.getEmetteur().getPseudo());
        try
        {
            socket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void closeConnection() throws IOException
    {
        //Closing the connection
        socket.close();
    }

}
