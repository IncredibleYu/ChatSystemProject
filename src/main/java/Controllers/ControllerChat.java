package Controllers;

import Packet.Packet;
import Protocols.TCPReceiver;
import Protocols.UDPReceiver;
import Protocols.UDPSender;
import Views.General;

import java.io.IOException;

public class ControllerChat
{
    private static Controller app;
    private UDPReceiver serverUDP;
    private UDPReceiver udplisten;

    public ControllerChat(Controller app)
    {
        this.setApp(app);
        setUdplisten(getApp().getUDPReceiver());
    }


    public static Controller getApp()
    {
        return app;
    }

    private void setApp(Controller app)
    {
        ControllerChat.app = app;
    }

    private void setUdplisten(UDPReceiver serverUDP)
    {
        this.serverUDP = serverUDP;
    }

    /**
     * Methode pour la connexion d'un utilisateur
     *
     * @param newPseudo String
     * @return boolean
     */
    public boolean Connexion(String newPseudo)
    {
        /*udplisten.setCas(1);
        udplisten.start();
        int port = 4445;
        try
        {
            UDPSender.envoiebroadcast(("CONNEXION_" + newPseudo + "_" + getApp().getActu().getIP() + "_" + port), port);
            Thread.sleep(2000);
        } catch (Exception e)
        {
            System.out.println("Erreur broadcast dans Connexion");
        } finally
        {
            getApp().getActu().setPseudo(newPseudo);
            udplisten.setCas(3);
        }
        return udplisten.isDisponible();*/
        return true;
    }


    public boolean editNickname(String newPseudo, int port)
    {
        /*serverUDP.setCas(2);
        try
        {
            //System.out.println("Tentative de changement de pseudo en broadcast");
            UDPSender.envoiebroadcast(("CHANGEMENTPSEUDO_" + newPseudo + "_" + getApp().getActu().getIP() + "_" + getApp().getActu().getPort()), port);
            Thread.sleep(2000); //on attends les réponses
        } catch (Exception e)
        {
            System.out.println("Erreur broadcast dans ChangePseudo");
        } finally
        {
            getApp().getActu().setPseudo(newPseudo);
            serverUDP.setCas(3);

        }
        return serverUDP.isDisponible();*/
        return true;
    }


    public void Deconnexion()
    {
        /*int port = 4445;
        try
        {
            UDPSender.envoiebroadcast(("DECONNEXION_" + getApp().getActu().getPseudo() + "_" + getApp().getActu().getIP() + "_" + getApp().getActu().getPort()), port);
        } catch (IOException e)
        {
            e.printStackTrace();
        }*/

        //getApp().getUDPReceiver().setOuvert(false);
        getApp().getUDPReceiver().closeSocket();
        TCPReceiver.setOuvert(false);

        Packet packet = new Packet();
        packet.setMessage("Deconnexion");
        packet.setUser(getApp().getActu());
        UDPSender.broadcast(packet);

        General.dispose();
        System.exit(0);
    }


}