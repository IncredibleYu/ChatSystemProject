package Controllers;

import Protocols.*;

import java.net.SocketException;
import java.util.ArrayList;

public class ThreadManager
{
    private ArrayList<Thread> listThreads;
    private Controller app;

    public ThreadManager(Controller app)
    {
        listThreads = new ArrayList<Thread>();
        this.app = app;
    }

    public Thread newThread(String protocol, String mode) throws SocketException
    {
        if(protocol != "UDP" && protocol != "TCP") return null;
        if(mode != "CLIENT" && mode != "SERVER") return null;
        Thread myThread = null;
        if(protocol.equals("UDP"))
        {
            if(mode.equals("CLIENT"))
            {
                myThread = new UDPSender("CLIENT", this.app);
            }
            else
            {
                myThread = new UDPReceiver("SERVEUR", this.app);
            }
        }
        else
        {
            if(mode.equals("CLIENT"))
            {
                myThread = new TCPClient("CLIENT", this.app);
            }
            else
            {
                myThread = new TCPServer("SERVEUR", this.app);
            }
        }
        if(myThread != null)
        {
            this.listThreads.add(myThread);
            myThread.start();
            return myThread;
        }
        else
        {
            return null;
        }
    }

    public void deleteThread(Thread deleteThread)
    {
        this.listThreads.remove(deleteThread);
    }
}
