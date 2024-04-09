/*
 * NTU SC2002 project
 * Session
 * Unit Test
 */

package sg.edu.ntu.sc2002;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

public class SessionTest {
    @Test
    public void authenticateValidCustomer() {
        Scanner in = new Scanner("1" + "\n");
        Session.authenticate(Map.of(), in);
    }

    @Test
    public void authenticateValidUser() {
        User user = new User("john", null);
        Scanner in =
                new Scanner("2" + "\n" + user.getUsername() + "\n" + User.DEFAULT_PASSWORD + "\n");
        Session.authenticate(Map.of(user.getUsername(), user), in);
    }

    @Test
    public void authenticateInvalidOption() {
        Scanner in = new Scanner("0" + "\n");
        assertThrows(IllegalArgumentException.class, () -> Session.authenticate(Map.of(), in));
    }

    @Test
    public void authenticateInvalidUserCredentials() {
        User user = new User("john", null);
        Scanner in = new Scanner("2" + "\n" + user.getUsername() + "\n" + "bad password" + "\n");
        assertThrows(
                IllegalArgumentException.class,
                () -> Session.authenticate(Map.of(user.getUsername(), user), in));
    }

    @Test
    void changePasswordValidUser() {
        User user = new User("john", null);
        Session session = new Session(user.getRole(), Optional.of(user));
        String newPassword = "alice";
        session.changePassword(new Scanner(newPassword));
        assertEquals(user.getPassword(), newPassword);
    }

    @Test
    void changePasswordInvalidUser() {
        Session session = new Session(null, Optional.empty());
        assertThrows(RuntimeException.class, () -> session.changePassword(new Scanner("bad")));
    }
}
