/*
 * NTU SC2002 project
 * User
 */

package sg.edu.ntu.sc2002;

import java.io.Serializable;

/** Defines a authenticated user of the FOMS system. */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String DEFAULT_PASSWORD = "password";
    private String username;
    private String name;
    private String password;
    private Role role;

    public User(String username, String name, Role role) {
        this.username = username;
        this.name = name;
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
        return this.username.equals(username) && this.password.equals(password);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        // users are unique by username
        return username.hashCode();
    }

}
