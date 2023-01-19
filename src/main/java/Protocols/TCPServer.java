package Protocols;

import Controllers.Controller;
import Models.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer extends Thread
{

    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader in;
    //private ObjectInputStream in;
    private PrintWriter out;
    //private ObjectOutputStream out;
    private boolean stop;
    private Controller app;

    public TCPServer(String name, Controller app)
    {
        super(name);
        this.app = app;
        this.stop = false;
        System.out.println("[TCP] Hello, I am " + this.getName());
        this.startConnection();
    }

    private void startConnection()
    {
        try
        {
            // Create a ServerSocket instance
            this.serverSocket = new ServerSocket(1236);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void run()
    {
        //InetAddress myAddressIP = null;
        //InetAddress userAddressIP = null;
        String myAddressIP = null;
        String userAddressIP = null;
        try
        {
            /**
             ** ------------------------
             ** Waiting for Connections
             ** ------------------------
             **/
            while(!this.stop)
            {
                // Waiting for prospective clients connections (blocking)
                this.socket = serverSocket.accept();

                // Getting the input flows
                this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //this.in = new ObjectInputStream(socket.getInputStream());

                // Getting the output flows
                this.out = new PrintWriter(socket.getOutputStream(), true);
                //this.out = new ObjectOutputStream(socket.getOutputStream());


                /**
                 ** --------------
                 ** Communication
                 ** --------------
                 **/
                /*Packet packet = new Packet("OK");
                this.serverSendDataToClient(packet);*/
                this.serverSendDataToClient("OK");

                User userToTalk, myUser;

                //myAddressIP = InetAddress.getLocalHost();
                //userAddressIP = socket.getInetAddress();
                myAddressIP = UDPReceiver.getCurrentIp().getHostAddress();
                userAddressIP = UDPReceiver.getCurrentIp().getHostAddress();

                myUser = app.getUserManager().getMemberByIP(myAddressIP);
                userToTalk = app.getUserManager().getMemberByIP(userAddressIP);
                //new ChatView(userToTalk.getPseudo(), myUser.getPseudo());
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public String serverReceiveDataFromClient()
    {
        try
        {
            String read = this.in.readLine();
            System.out.println(read);

            /*Packet packet = (Packet) in.readObject();
            System.out.println("Message: " + packet.getMessage());*/
            return read;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public void serverSendDataToClient(String message/*Packet packet*/)
    {
        this.out.println("[SERVER->CLIENT] " + message);
        /*try
        {
            this.out.writeObject(packet);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }*/
    }

    public void closeConnection()
    {
        try
        {
            // Closing the connection
            this.socket.close();
            this.serverSocket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        this.stop = true;
    }
}
