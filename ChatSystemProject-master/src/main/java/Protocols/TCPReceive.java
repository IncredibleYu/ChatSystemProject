package Protocols;

import Controllers.Controller;

import java.net.ServerSocket;
import java.net.Socket;

public class TCPReceive {
    TCPSend chat ;
    private Socket link;
    private static boolean ouvert;
    private Controller app;

    public TCPReceive(Controller app) {
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
                chat = new TCPSend(app,link);

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
        TCPReceive.ouvert = ouvert;
    }


}
