package Controllers;

import Models.DataBase;
import Models.User;
import Protocols.UDPReceiver;

public class Controller {

    private User actu;
    private ControllerChat cSystem;

    private UserManager userManager;
    private UDPReceiver udpReceiver;

    public static int maxSessions;

    private DataBase db;

    public Controller()
    {
        //setUDPReceiver(new UDPReceiver());
        setUserManager(new UserManager());
        setDb(new DataBase());

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


