/*
 * NTU SC2002 project
 * User
 */

package sg.edu.ntu.sc2002;

import java.io.Serializable;

/** Defines a authenticated user of the FOMS system. */
public class User implements Serializable {
    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** default password for each user */
    public static final String DEFAULT_PASSWORD = "password";

    /** username of the user */
    private String username;

    /** name of the user */
    private String name;

    /** branch the user belongs to */
    private String branchBelongTo;

    /** age of the user */
    private int age;

    /** gender of the user */
    private Gender gender;

    /** password set by the user */
    private String password;

    /** role of the user */
    private IRole role;

    /**
     * Constructor to create a User with username and role. Name, branchBelongTo, gender, and
     * password are set to default values.
     *
     * @param username Username of User.
     * @param role Role of User.
     */
    public User(String username, IRole role) {
        this(username, username, "", -1, Gender.UNKNOWN, DEFAULT_PASSWORD, role);
    }

    /**
     * Constructor to create a User.
     *
     * @param username Username of User.
     * @param name Name of User.
     * @param branchBelongTo Branch of User.
     * @param age Age of User.
     * @param gender Gender of User.
     * @param password Password of User.
     * @param role Role of User.
     */
    public User(
            String username,
            String name,
            String branchBelongTo,
            int age,
            Gender gender,
            String password,
            IRole role) {
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

    // getter

    /**
     * @return Username of the User.
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return Password of the User.
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return Branch the User belongs to.
     */
    public String getBranchBelongTo() {
        return branchBelongTo;
    }

    /**
     * @return Role of the User.
     */
    public IRole getRole() {
        return role;
    }

    /**
     * @return Name of the User.
     */
    public String getName() {
        return name;
    }

    /**
     * @return Age of the User.
     */
    public int getAge() {
        return age;
    }

    /**
     * @return Gender of the User.
     */
    public Gender getGender() {
        return gender;
    }

    // setter

    /**
     * Updates the User password.
     *
     * @param password New password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Updates the User role.
     *
     * @param role New role.
     */
    public void setRole(IRole role) {
        this.role = role;
    }

    /**
     * Updates the User's name.
     *
     * @param name New name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Updates the User age.
     *
     * @param age New age.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Updates the User gender.
     *
     * @param gender New gender.
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Updates the User's branch.
     *
     * @param branchBelongTo New branch.
     */
    public void setBranchBelongTo(String branchBelongTo) {
        this.branchBelongTo = branchBelongTo;
    }

    @Override
    public int hashCode() {
        // users are unique by username
        return username.hashCode();
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
