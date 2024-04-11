package sg.edu.ntu.sc2002;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AdminTest {
    @Test
    public void loginCorrect() {
        User user = new User("john", "John", new AdminRole());
        assertTrue(user.login("john", "password"));
        user.setPassword("abc");
        assertTrue(user.login("john", "abc"));
    }
}
