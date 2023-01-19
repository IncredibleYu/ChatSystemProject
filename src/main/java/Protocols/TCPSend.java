package Protocols;

import Controllers.Controller;
import Models.Message;
import Models.User;
import Views.General;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPSend extends Thread{
    private Socket socket;
    private User otheruser;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Controller app;

    public TCPSend (Controller app, Socket sock) {
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

    public TCPSend (Controller app, User u2) {
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

    public void SendMessage(String data) {

    }

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

        //getApp().getDb().addMessage(socket.getInetAddress().getHostAddress(), msg);

        General.displayNotification(" vous a envoyé un message.",socket.getInetAddress().getHostAddress());
        General.display(msg.getEmetteur().getPseudo());
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
