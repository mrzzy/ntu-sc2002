/*
* NTU SC2002 project
* User
* Unit Test
*/

package sg.edu.ntu.sc2002;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class UserTest {
    @Test
    public void loginCorrect() {
        User user = new User("john", null);
        assertTrue(user.login("john", "password"));
        user.setPassword("abc");
        assertTrue(user.login("john", "abc"));
    }

    @Test
    public void loginWrong() {
        User user = new User("john", null);
        // wrong username
        assertFalse(user.login("phil", "password"));
        // wrong password
        user.setPassword("abc");
        assertFalse(user.login("john", "password"));
    }
}
