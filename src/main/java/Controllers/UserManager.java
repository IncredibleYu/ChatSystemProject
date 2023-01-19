package Controllers;

import Models.User;

import java.net.InetAddress;
import java.util.ArrayList;

public class UserManager extends ArrayList<User>
{
    private static ArrayList<User> listMembers;
    //static ArrayList<User> users = new ArrayList<User>();

    public UserManager()
    {
        super();
        listMembers = new ArrayList<User>();
    }


    public synchronized void addMember(User newMember)
    {
        if(!this.checkUserExists(newMember.getPseudo()))
        {
            this.listMembers.add(newMember);
        }
    }

    public void addConnectedUser (User i) {
        listMembers.add(i);
    }

    /**
     * @return n int : taille de la listes
     */
    public synchronized int length()
    {
        int count = 0;

        for(User user : getAllMembers())
        {
            count++;
        }
        return count;
    }

    public synchronized User getMemberById(int userId)
    {
        for(User member : getAllMembers())
        {
            if(member.getId() == userId)
            {
                return member;
            }
        }
        return null;
    }

    public synchronized User getMemberByPseudo(String pseudo)
    {
        for(User member : getAllMembers())
        {
            if(member.getPseudo().equals(pseudo))
            {
                return member;
            }
        }
        return null;
    }

    /**
     * Methode pour recuperer un type User d'apres son pseudp
     * @return User user
     */
    public static User getUserfromPseudo(String pseudo) {
        User toget = null;
        for (User user : listMembers) {
            if (user.getPseudo().equals(pseudo)) {
                toget=user;
                return toget;
            }
        }
        return null;
    }

    /**
     * Methode pour recuperer un type User d'apres son adresse IP
     * @return user avec l'ip correspondante
     */
    public synchronized User getMemberByIP(String addressIP)
    {
        for(User member : getAllMembers())
        {
            if (member.getIP().equals(addressIP))
            {
                return member;
            }
        }
        return null;
    }

    /**
     * Methode pour recuperer un type User d'apres son adresse IP
     * @return user avec l'ip correspondante
     */
    public User getUserfromIP (String ip) {
        User toget = null;
        for (User user : listMembers) {
            if (user.getIP().equals(ip)) {
                toget=user;
                return toget;
            }
        }
        return null;
    }

    /**
     * Methode pour recuperer le champ Pseudp d'un type User d'apres son pseudo
     * @return String pseudo
     */
    public String getPseudofromIP (String IP) {
        String toget = null;
        for (User user : listMembers) {
            if (user.getIP().equals(IP)) {
                toget=user.getPseudo();
                return toget;
            }
        }
        return null;
    }

    public synchronized void deleteMember(User oldMember)
    {
        this.listMembers.remove(oldMember);
    }

    public void deleteUser (User i) {
        listMembers.remove(i);
    }

    public synchronized ArrayList<User> getAllMembers()
    {
        return this.listMembers;
    }

    /**
     * Methode pour creer un tableau des utilisateurs en ligne
     * @return String [] tab : tableau des utilsiateurs en ligne (Tableau de String)
     */
    public String[] getListPseudo() {
        if (!(listMembers.isEmpty())) {
            String[] tab= new String[length()];
            int i = 0;
            for (User user : listMembers) {
                tab[i]=user.getPseudo();
                i++;
            }
            return tab;
        }
        else {
            String[] tab= {};
            return tab;
        }
    }

    public synchronized void showAllMembers() {
        for (User member : getAllMembers())
        {
            System.out.println("(" + member.getPseudo() + ", " + member.getIP() + ")");
        }
    }

    public static void showActifUsers() {
        for (User user : listMembers) {
        }
    }

    public synchronized boolean checkUserExists(String pseudo)
    {
        for(User member : getAllMembers())
        {
            if(member.getPseudo().equals(pseudo))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Methode pour savoir si un pseudo appartient bien a un utilisateur
     * @return boolean
     */
    public boolean appartient (String pseudo) {
        for (User user : listMembers) {
            if (user.getPseudo().equals(pseudo)) {
                return true;
            }
        }
        return false;
    }
}
