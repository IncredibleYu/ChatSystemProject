package fr.insa.gei.ChatSystemProject.Controllers;

import fr.insa.gei.ChatSystemProject.Controllers.UserManager;
import fr.insa.gei.ChatSystemProject.Models.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class UserManagerTest
{
    private UserManager userManager;
    private User[] users;
    private ArrayList<User> myList;

    @Before
    public void initAll()
    {
        userManager = new UserManager();
        myList = new ArrayList<>();

        users = new User[7];
        String[] tab = new String[7];

        for(int i = 0; i < 7; i++)
        {
            users[i] = new User();
            users[i].setPseudo("Test" + i);
            userManager.addMember(users[i]);
            myList.add(users[i]);
            tab[i] = users[i].getPseudo();
        }

    }

    @Test
    public void testLength()
    {
        assertEquals(userManager.length(), 7);
        assertNotEquals(userManager.length(), 8);
    }

    @Test
    public void testGetMemberByPseudo()
    {
        assertEquals(userManager.getMemberByPseudo("Test0"), users[0]);
    }

    @Test
    public void testGetMemberByIP()
    {
        assertEquals(userManager.getMemberByIP("10.1.5.227"), users[0]);
    }

    @Test
    public void testGetPseudoFromIP()
    {
        assertEquals(userManager.getPseudofromIP("10.1.5.227"),  "Test0");
    }

    @Test
    public void testDeleteMember()
    {
        // Check user exists
        assertTrue(userManager.checkUserExists("Test6"));

        // Delete member
        userManager.deleteMember(userManager.getMemberByPseudo("Test6"));
        myList.remove(users[5]);

        // Check user doesn't exists
        assertFalse(userManager.checkUserExists("Test6"));
    }

    @Test
    public void testGetAllMember()
    {
        assertEquals(userManager.getAllMembers(), myList);
    }

    @Test
    public void testGetListPseudo()
    {
        String[] tab = new String[7];

        for(int i = 0; i < 7; i++)
        {
            tab[i] = users[i].getPseudo();
        }

        assertEquals(userManager.getListPseudo(), tab);
    }

    @Test
    public void testUserExists()
    {
        userManager.showAllMembers();
        assertTrue(userManager.checkUserExists("Test0"));
    }

    @Test
    public void testUserDoNotExists()
    {
        assertFalse(userManager.checkUserExists("Test7"));
    }

    @Test
    public void testAppartient()
    {
        assertTrue(userManager.appartient("Test0"));
    }
}
