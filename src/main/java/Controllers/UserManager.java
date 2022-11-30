package Controllers;

import Models.User;
import java.util.ArrayList;

public class UserManager
{
    private ArrayList<User> listMembers;

    public UserManager()
    {
        ArrayList<User> listMembers = new ArrayList<User>();
    }

    public void addMember(User newMember)
    {
        this.listMembers.add(newMember);
    }

    public void deleteMember(User oldMember)
    {
        this.listMembers.remove(oldMember);
    }

    public ArrayList<User> getAllMembers()
    {
        return this.listMembers;
    }

    public void showAllMembers() {
        for (User member : getAllMembers())
        {
            System.out.println(member.getPseudo());
        }
    }
}
