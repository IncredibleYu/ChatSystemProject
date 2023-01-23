import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import Controllers.UserManager;
import Models.User;
import org.junit.Test;

public class SimpleTest
{
    @Test
    public  void  testUserExists()
    {
        UserManager userManager = new UserManager();
        User user = new User();
        user.setPseudo("Test");
        userManager.add(user);

        userManager.showAllMembers();
        System.out.println(userManager.checkUserExists("Test"));

        //assertTrue(userManager.checkUserExists("Test"));
    }

    @Test
    public  void  testUserDoNotExists() {
        UserManager userManager = new UserManager();
        User user = new User();
        user.setPseudo("Test");
        userManager.add(user);

        assertFalse(userManager.checkUserExists("Test2"));
    }
}
