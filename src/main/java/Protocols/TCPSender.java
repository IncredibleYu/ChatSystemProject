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
    private BufferedReader inForObject;
    private ObjectOutputStream out;
    private PrintWriter outForObject;
    private Controller app;

    public TCPSender(String name, Controller app)
    {
        super(name);
        this.app = app;
    }

    public TCPSender(Controller app, Socket sock) {
        setApp(app);
        setSocket(sock);
        try {
            setOut(new ObjectOutputStream(sock.getOutputStream()));
            setIn(new ObjectInputStream(sock.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        start();
    }

    public TCPSender(Controller app, User u2) {
        setApp(app);
        setOtheruser(u2);
        try {
            setSocket(new Socket(u2.getIP(),2000));
            setOut(new ObjectOutputStream(socket.getOutputStream()));
            setIn(new ObjectInputStream(socket.getInputStream()));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Controller getApp() {
        return app;
    }

    public void setApp(Controller app) {
        this.app = app;
    }

    public User getOtheruser() {
        return otheruser;
    }

    public void setOtheruser(User otheruser) {
        this.otheruser = otheruser;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public void setIn(ObjectInputStream in) {
        this.in = in;
    }



    /**Metode pour envoyer les messages**/

    public void SendMessage(String data) {
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

    public void run() {
        String data = null;
        Message msg = null;
        try {
            data = (String) getIn().readObject();
            msg = Message.toMessage(data);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        getApp().getDb().addMessage(msg);

        General.displayNotification(" vous a envoyé un message.",socket.getInetAddress().getHostAddress());
        General.display(msg.getEmetteur().getPseudo());
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run2()
    {
        System.out.println("[TCP] Hello, I am " + this.getName());
        try
        {
            // Creating a Socket instance to Establish a connexion to the server
            socket = new Socket("localhost", 2000);

            //Getting the input flows
            this.inForObject = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //this.in = new ObjectInputStream(socket.getInputStream());

            //Getting the outputs flows
            this.outForObject = new PrintWriter(socket.getOutputStream(), true);
            //this.out = new ObjectOutputStream(socket.getOutputStream());

            /**
             ** -------------------------------------
             ** Communication (send and receive data)
             ** -------------------------------------
             **/

            // Receive data
            this.clientReceiveDataFromServer();

            /*
            // Send Data
            this.clientSendDataToServer("[CLIENT->SERVER] LES DONNEES QUE J'ENVOIE"); //Send message
             */

        }
        catch(UnknownHostException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public String clientReceiveDataFromServer()
    {
        //InetAddress myAddressIP = null;
        //InetAddress userAddressIP = null;
        String myAddressIP = "";
        String userAddressIP = "";
        try
        {
            String read = this.inForObject.readLine();
            String response = read.split(" ")[1];
            System.out.println(response);

            /*Packet packet = (Packet) this.inForObject.readObject();
            String response = packet.getMessage();
            System.out.println("Message: " + response);*/

            User userToTalk, myUser;

            //myAddressIP = InetAddress.getLocalHost();
            //userAddressIP = socket.getInetAddress();
            myAddressIP = InetAddress.getLocalHost().getHostAddress();
            userAddressIP = socket.getInetAddress().getHostAddress();

            System.out.println(myAddressIP);
            System.out.println(userAddressIP);

            myUser = app.getUserManager().getMemberByIP(myAddressIP);
            userToTalk = app.getUserManager().getMemberByIP(userAddressIP);

            if(response.equals("OK"))
            {
                if(myUser != null && userToTalk != null)
                {
                    //myChat = new ChatView(userToTalk.getPseudo(), myUser.getPseudo());
                }
                else
                {
                    System.out.println("ERROR : Utilisateurs introuvables.");
                }
            }

            return read;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public void clientSendDataToServer(String message/*Packet packet*/)
    {
        this.outForObject.println("[CLIENT->SERVER] " + message);
        /*try
        {
            this.outForObject.writeObject(packet);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }*/
    }

    public void checkForMessage()
    {
        try
        {
            while (true)
            {
                String read = this.inForObject.readLine();

                String response = read.split(" ")[1];
                System.out.println(response);
                //this.myChat.notifyMessageReceived(response);
            }
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
