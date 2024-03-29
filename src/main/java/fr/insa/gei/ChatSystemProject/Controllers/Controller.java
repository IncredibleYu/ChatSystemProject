package fr.insa.gei.ChatSystemProject.Controllers;

import fr.insa.gei.ChatSystemProject.Models.DataBase;
import fr.insa.gei.ChatSystemProject.Models.User;
import fr.insa.gei.ChatSystemProject.Protocols.UDPReceiver;
import fr.insa.gei.ChatSystemProject.Protocols.UDPSender;

public class Controller {

    private User actu;
    private ControllerChat cSystem;

    private UserManager userManager;
    private UDPReceiver udpReceiver;

    private UDPSender udpSender;

    public static int maxSessions;

    private DataBase db;

    public Controller()
    {
        //setUDPReceiver(new UDPReceiver());
        setUserManager(new UserManager());
        setDb(new DataBase());
        setcSystem(new ControllerChat(this));

        if (!(maxSessions>49))
        {
            maxSessions++;
        }
        else
        {

        }
    }

    public Controller(User user1) {
        this.setActu(user1);
        if (!(maxSessions>49)) {
            maxSessions++;
        }else {

        }
        //setActifUsers(new UserManager());
    }

    public UDPReceiver getUDPReceiver()
    {
        return udpReceiver;
    }

    public void setUDPReceiver(UDPReceiver udpReceiver)
    {
        this.udpReceiver = udpReceiver;
    }

    public UDPSender getUdpSender()
    {
        return udpSender;
    }

    public void setUdpSender(UDPSender udpSender)
    {
        this.udpSender = udpSender;
    }

    public UserManager getUserManager()
    {
        return userManager;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public User getActu() {
        return actu;
    }

    public void setActu(User actu) {
        this.actu = actu;
    }

    public ControllerChat getcSystem() {
        return cSystem;
    }

    public void setcSystem(ControllerChat cSystem) {
        this.cSystem = cSystem;
    }

    public DataBase getDb() {
        return db;
    }

    public void setDb(DataBase db) {
        this.db = db;
    }
}


