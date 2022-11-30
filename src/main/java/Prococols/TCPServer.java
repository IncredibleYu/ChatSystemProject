package Prococols;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TCPServer
{

    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private boolean stop;

    public TCPServer()
    {
        this.stop = true;
        this.startConnection();
    }

    private void startConnection()
    {
        try
        {
            // Create a ServerSocket instance
            this.serverSocket = new ServerSocket(1234);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void run()
    {
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

                // Getting the output flows
                this.out = new PrintWriter(socket.getOutputStream(), true);


                /**
                 ** --------------
                 ** Communication
                 ** --------------
                 **/

                //Writing
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                this.serverSendDataToClient("Heure et Date : " + sdf.format(new Date()));
                this.serverSendDataToClient("En attente de données...");

                //Reading
                this.serverReceiveDataFromClient();

                //Writing
                this.serverSendDataToClient("Bien reçu");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void serverReceiveDataFromClient()
    {
        try
        {
            String input = this.in.readLine();
            System.out.println(input);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void serverSendDataToClient(String message)
    {
        this.out.println("[SERVER->CLIENT] " + message);
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
