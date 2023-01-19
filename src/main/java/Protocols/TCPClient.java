package Protocols;

import Controllers.Controller;
import Models.User;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient extends Thread
{

    private Socket socket;
    private BufferedReader in;
    //private ObjectInputStream in;
    private PrintWriter out;
    //private ObjectOutputStream out;
    //private ChatView myChat;
    private Controller app;

    public TCPClient(String name, Controller app)
    {
        super(name);
        this.app = app;
    }

    public String clientReceiveDataFromServer()
    {
        //InetAddress myAddressIP = null;
        //InetAddress userAddressIP = null;
        String myAddressIP = "";
        String userAddressIP = "";
        try
        {
            String read = this.in.readLine();
            String response = read.split(" ")[1];
            System.out.println(response);

            /*Packet packet = (Packet) this.in.readObject();
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
        this.out.println("[CLIENT->SERVER] " + message);
        /*try
        {
            this.out.writeObject(packet);
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
                String read = this.in.readLine();

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


    public void run()
    {
        System.out.println("[TCP] Hello, I am " + this.getName());
        try
        {
            // Creating a Socket instance to Establish a connexion to the server
            socket = new Socket("localhost", 1236);

            //Getting the input flows
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //this.in = new ObjectInputStream(socket.getInputStream());

            //Getting the outputs flows
            this.out = new PrintWriter(socket.getOutputStream(), true);
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

    public void closeConnection() throws IOException
    {
        //Closing the connection
        socket.close();
    }
}
