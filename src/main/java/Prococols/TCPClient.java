package Prococols;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TCPClient
{

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public TCPClient()
    {
        this.startConnection();
    }

    private void startConnection()
    {
        try {
            // Creating a Socket instance to Establish a connexion to the server
            socket = new Socket("localhost", 1234);

            //Getting the input flows
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //Getting the outputs flows
            out = new PrintWriter(socket.getOutputStream(), true);
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

    public void clientReceiveDataFromServer()
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

    public void clientSendDataToServer(String message)
    {
        this.out.println("[CLIENT->SERVER] " + message);
    }


    public void run() throws IOException
    {
        /**
         ** -------------------------------
         ** Show infos on internal console
         ** -------------------------------
         **/
        String getDateAndTime = in.readLine();
        System.out.println(getDateAndTime);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        System.out.println("[CLIENT->CLIENT] Heure et Date Interne : " + sdf.format(new Date()));

        /**
         ** -------------------------------------
         ** Communication (send and receive data)
         ** -------------------------------------
         **/

        // Receive data
        this.clientReceiveDataFromServer();

        // Send Data
        this.clientSendDataToServer("[CLIENT->SERVER] LES DONNEES QUE J'ENVOIE"); //Send message

        // Receive data
        this.clientReceiveDataFromServer();
    }

    public void closeConnection() throws IOException
    {
        //Closing the connection
        socket.close();
    }
}
