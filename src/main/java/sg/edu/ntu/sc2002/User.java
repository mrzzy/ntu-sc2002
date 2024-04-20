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
    private String branchBelongTo;
    private int age;
    private Gender gender;
    private String password;
    private Role role;

    public User(String username, Role role) {
        this(username, username, "", -1, Gender.UNKNOWN, DEFAULT_PASSWORD, role);
    }

    public User(String username, String name, String branchBelongTo, int age, Gender gender, String password, Role role) {
        this.username = username;
        this.name = name;
        this.branchBelongTo = branchBelongTo;
        this.age = age;
        this.gender = gender;
        this.password = password;
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

    //setter

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setBranchBelongTo(String branchBelongTo) {
        this.branchBelongTo = branchBelongTo;
    }

    //getter
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getBranchBelongTo() {
        return branchBelongTo;
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

    public int getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        User other = (User) obj;
        return getUsername().equals(other.getUsername())
                && getName().equals(other.getName())
                && getAge() == (other.getAge())
                && getGender().equals(other.getGender())
                && getPassword().equals(other.getPassword())
                && getRole().code() == getRole().code();
    }
}
