package Protocols;

import Controllers.Controller;
import Controllers.UserManager;
import Models.User;
import Packet.Packet;
import Views.General;

import java.io.*;
import java.net.*;
import java.util.Enumeration;

import static Views.General.setApp;

public class UDPReceiver extends Thread {

    private int cas ;
    private int port;
    private boolean ouvert ;
    private boolean disponible ;
    private DatagramPacket rPacket;
    private static DatagramSocket sSocket;
    private Controller app;
    private Boolean stop;

    private DatagramSocket socket;

    public UDPReceiver() {
        setOuvert(true);
        setDisponible(true);
    }

    public UDPReceiver(Controller app) {
        setOuvert(true);
        setApp(app);
        setDisponible(true);
    }

    public UDPReceiver(String name, Controller app)
    {
        super(name);

        setOuvert(true);
        setApp(app);
        setDisponible(true);

        this.stop = false;
    }

    public void run2() {
        int serverPort=4445;
        try {
            sSocket = new DatagramSocket(serverPort);
            String sentence="";
            byte[] array = new byte[100000000];

            //System.out.printf("Listening on udp:%s:%d%n", getCurrentIp().getHostAddress(), serverPort);
            while (ouvert) {
                //cas de la connexion
                if (getCas()==1) {
                    try {
                        sSocket.setSoTimeout(2000);
                        rPacket = new DatagramPacket(array, array.length);
                        sSocket.receive(rPacket);
                        sentence = new String( rPacket.getData(), 0, rPacket.getLength() );
                        //System.out.println("On a re�u: "+ sentence);
                        User usertoadd= User.toUser(sentence);
                        String[] parametersuser=sentence.split("_");
                        String validate= parametersuser[0];
                        //Si r�ponse n�gative then renvoi faux
                        if (validate.equals("notOk")) {
                            //System.out.println("pseudo Not ok");
                            setDisponible(false);
                            setCas(3);
                        }else {
                            //Si r�ponse positive then renvoi vrai
                            //System.out.println("pseudo ok");
                            setDisponible(true);
                            if(usertoadd.getIP().equals(getApp().getActu().getIP())) {
                                //nothing to do
                            }
                            else if(!(usertoadd.getIP().equals("IP"))) {
                                //si on est le 1er du r�seau on ajoute personne
                                //System.out.println("on ajoute "+usertoadd);
                                getApp().getUserManager().addConnectedUser(usertoadd);

                            }
                        }

                    }
                    catch(SocketTimeoutException e){
                        sentence="ok_pseudo_IP_4445";
                        setDisponible(true);
                    }
                    catch(Exception e) {
                        e.printStackTrace();
                    }

                }

                //cas du changement de pseudo
                else if (getCas()==2) {
                    try {
                        sSocket.setSoTimeout(2000);
                        rPacket = new DatagramPacket(array, array.length);
                        sSocket.receive(rPacket);
                        sentence = new String( rPacket.getData(), 0, rPacket.getLength() );
                        User usertoadd= User.toUser(sentence);
                        String[] parametersuser=sentence.split("_");
                        String validate= parametersuser[0];
                        if (validate.equals("notOk")) {
                            setDisponible(false);
                            setCas(3);
                        }else {
                            setDisponible(true);
                        }
                    }
                    catch (SocketTimeoutException e ) {
                        setDisponible(true);

                    }
                    catch(Exception e) {
                        e.printStackTrace();
                    }
                }
                else if (getCas() == 3) {
                    System.out.println("TestCas3");
                }


            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run()
    {
        System.out.println("[UDP] Hello, I am " + this.getName());
        try
        {
            this.socket = new DatagramSocket(1234);
            while (!this.stop)
            {
                byte[] buffer = new byte[1024 * 10];
                DatagramPacket datagramPacket = new DatagramPacket(buffer, 0, buffer.length);
                socket.receive(datagramPacket);
                byte[] b_array = datagramPacket.getData();
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(b_array);

                ObjectInputStream ois = new ObjectInputStream(byteArrayInputStream);

                Packet packet = (Packet) ois.readObject();

                String addressIP = datagramPacket.getAddress().getHostAddress();

                System.out.println(addressIP);
                System.out.println(app.getActu().getIP());
                if(!addressIP.equals(app.getActu().getIP()))
                {
                    // Envoie "je suis là"
                    if (packet.getMessage().equals("Presence"))
                    {
                        packet.setUser(app.getActu());
                        sendResponse(addressIP, packet);
                    }

                    // Met à jour l'annuaire
                    if (packet.getMessage().equals("Pseudo"))
                    {
                        app.getUserManager().addMember(packet.getUser());
                        General.miseAJourContact();
                    }

                    // Met à jour le pseudo dans l'annuaire
                    if (packet.getMessage().equals("ChangePseudo"))
                    {
                        User user = app.getUserManager().getMemberByIP(addressIP);
                        System.out.println(user.getPseudo());
                        user.setPseudo(packet.getUser().getPseudo());
                        System.out.println(user.getPseudo());
                        General.miseAJourContact();
                    }
                }

                /*if (packet.getUser() != null)
                {
                    System.out.println(packet.getUser().getPseudo());
                    app.getUserManager().addMember(packet.getUser());
                }

                // Envoie de ma présence

                //InetAddress addressIP = null;

                //addressIP = InetAddress.getLocalHost();
                String addressIP = UDPReceiver.getCurrentIp().getHostAddress();
                System.out.println("Adresse IP : " + addressIP);
                System.out.println("All members : ");
                app.getUserManager().showAllMembers();
                User user = app.getUserManager().getMemberByIP(addressIP);

                System.out.println("Pseudo : " + user.getPseudo());
                packet.setUser(user);
                sendResponse(addressIP, packet);

                //this.newMessageReceived(datagramPacket.getAddress(),p);
                //System.out.println(p.getMessage());*/

            }
        }
        catch (SocketException e)
        {
            System.err.println("[CLIENT] Socket closed");
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void sendResponse(/*InetAddress address*/String address, Packet packet) throws IOException
    {
        try
        {
            // create packet send
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(outputStream);
            os.writeObject(packet);
            os.close();

            byte[] buffer = outputStream.toByteArray();
            int port = 1235;
            DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
            datagramPacket.setAddress(/*address*/InetAddress.getByName(address));
            datagramPacket.setPort(port);

            // send packet to user
            DatagramSocket senderSocket = new DatagramSocket();
            senderSocket.send(datagramPacket);
        }
        catch (UnknownHostException e)
        {
            System.err.println("Unknown Host");
            e.printStackTrace();
        }
        catch (SocketException e)
        {
            System.err.println("Socket closed");
        }
        catch (IOException e)
        {
            System.err.println("Failed with sending message");
            e.printStackTrace();
        }
    }

    public static InetAddress getCurrentIp() {
        try {
            Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) networkInterfaces .nextElement();
                Enumeration nias = ni.getInetAddresses();
                while(nias.hasMoreElements()) {
                    InetAddress ia= (InetAddress) nias.nextElement();
                    if (!ia.isLinkLocalAddress() && !ia.isLoopbackAddress() && ia instanceof Inet4Address) {
                        return ia;
                    }
                }
            }
        }
        catch (SocketException e) {
            System.out.println("unable to get current IP " + e.getMessage());
        }
        return null;
    }


    public Controller getApp() {
        return app;
    }

    public void setApp(Controller app) {
        this.app = app;
    }

    public void closeSocket()
    {
        this.stop = true;
        sSocket.close();
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public boolean isOuvert() {
        return ouvert;
    }

    public void setOuvert(boolean ouvert) {
        this.ouvert = ouvert;
    }

    public int getCas() {
        return cas;
    }

    public void setCas(int cas) {
        this.cas = cas;
    }
}
