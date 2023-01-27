package fr.insa.gei.ChatSystemProject.Models;

import fr.insa.gei.ChatSystemProject.Models.User;

import java.io.Serializable;

public class Packet implements Serializable
{
    private static final long serialVersionUID = -4507489610617393544L;

    private User user;
    private String message;

    public Packet()
    {
    }

    public Packet(User user)
    {
        this.user = user;
    }

    public Packet(String message)
    {
        this.message = message;
    }

    public Packet(String message, User myUser)
    {
        this.message = message;
        this.user = myUser;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}
