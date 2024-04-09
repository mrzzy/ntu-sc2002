package sg.edu.ntu.sc2002;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdminTest {
    @Test
    public void loginCorrect() {
        User user = new User("john", new AdminRole());
        assertTrue(user.login("john", "password"));
        user.setPassword("abc");
        assertTrue(user.login("john", "abc"));
    }
}
