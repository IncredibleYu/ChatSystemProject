package Protocols;

import Controllers.Controller;
import Models.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPReceiver extends Thread {
    TCPSender chat ;
    private Socket link;
    private static boolean ouvert;

    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader in;
    //private ObjectInputStream in;
    private PrintWriter out;
    //private ObjectOutputStream out;
    private boolean stop;

    private Controller app;

    public TCPReceiver(String name, Controller app)
    {
        super(name);
        System.out.println("[TCP] Hello, I am " + this.getName());
        this.app = app;
        setOuvert(true);

        /*this.stop = false;
        this.startConnection();*/
    }

    public TCPReceiver(Controller app) {
        this.app = app;
        setOuvert(true);
    }

    public void run() {
        ServerSocket server;
        try {
            server = new ServerSocket(2000);
            System.out.println("listening on port 2000 ready to have conversation");
            while(ouvert) {
                link = server.accept();
                chat = new TCPSender(app,link);

            }
            //link.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Controller getApp() {
        return app;
    }

    public void setApp(Controller app) {
        this.app = app;
    }

    public static void setOuvert(boolean ouvert) {
        TCPReceiver.ouvert = ouvert;
    }

    private void startConnection()
    {
        try
        {
            // Create a ServerSocket instance
            this.serverSocket = new ServerSocket(2000);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void run2()
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
