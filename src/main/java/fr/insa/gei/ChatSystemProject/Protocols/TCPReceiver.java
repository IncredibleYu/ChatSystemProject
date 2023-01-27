package fr.insa.gei.ChatSystemProject.Protocols;

import fr.insa.gei.ChatSystemProject.Controllers.Controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPReceiver extends Thread {
    TCPSender chat ;
    private Socket link;
    private static boolean ouvert;

    private Controller app;

    public TCPReceiver(String name, Controller app)
    {
        super(name);
        System.out.println("[TCP] Hello, I am " + this.getName());
        this.app = app;
        this.ouvert = true;
    }

    public void run()
    {
        System.out.println("[TCP] Hello, I am " + this.getName());
        ServerSocket server;
        try {
            server = new ServerSocket(2000);
            System.out.println("listening on port 2000 ready to have conversation");
            while(ouvert)
            {
                link = server.accept();
                chat = new TCPSender("CLIENT", app, link);
            }
            //link.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setOuvert(boolean ouvert)
    {
        TCPReceiver.ouvert = ouvert;
    }

    public void closeConnection()
    {
        try
        {
            // Closing the connection
            this.link.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


}
