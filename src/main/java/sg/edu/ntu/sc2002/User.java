/*
 * NTU SC2002 project
 * User
 */

package sg.edu.ntu.sc2002;

import java.io.Serializable;

/** Defines a authenticated user of the FOMS system. */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_PASSWORD = "password";
    String username;
    String password;
    Role role;

    public User(String username, Role role) {
        this.username = username;
        this.password = DEFAULT_PASSWORD;
        this.role = role;
    }

    /**
     * Attempt to Login as user with given credentials
     *
     * @param username Username credential.
     * @param password Password credential.
     * @return {@code true} if login was successful, {@code false} otherwise.
     */
    public boolean login(String username, String password) {
        return this.username == username && this.password == password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
