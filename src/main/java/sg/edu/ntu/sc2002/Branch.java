/*
 * NTU SC2002 project
 * Branch
 */

package sg.edu.ntu.sc2002;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.naming.LimitExceededException;

/** Defines a Branch of Fast Food Restaurant {@link Chain}. */
public class Branch implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String location;
    private int staffQuota;
    private Set<User> staffs;
    private Set<User> managers;
    private Set<Item> menu;

    public Branch(
            String name,
            String location,
            int staffQuota,
            Set<User> staffs,
            Set<User> managers,
            Set<Item> menu) {
        this.name = name;
        this.location = location;
        this.staffQuota = staffQuota;
        this.staffs = staffs;
        this.managers = managers;
        this.menu = menu;
    }

    public Branch(String name, String location, int staffQuota) {
        this(name, location, staffQuota, new HashSet<>(), new HashSet<>(), new HashSet<>());
    }

    /**
     * Assign the given user to work in this branch. User will be assigned based on their {@link
     * Role}: staff or manager.
     *
     * @throws IllegalArgumentException If given user with an unsupported role.
     * @throws LimitExceededException If assignment of staff exceeds quota.
     */
    public void assign(User user) throws LimitExceededException {
        switch (user.getRole()) {
            case StaffRole r -> {
                if (getStaffQuota() >= getStaffs().size()) {
                    throw new LimitExceededException("Staff assignment exceeds quota.");
                }
                getStaffs().add(user);
            }
            case ManagerRole r -> {
                if (getManagerQuota() >= getManagers().size()) {
                    throw new LimitExceededException("Manager assignment exceeds quota.");
                }
                getManagers().add(user);
            }
            default ->
                    throw new IllegalArgumentException(
                            "Refusing to assign unsupported role: " + user.getRole());
        }
    }

    /**
     * Get the staff quota for this branch. Note that staff quota is excludes managers in the
     * branch.
     */
    public int getStaffQuota() {
        return staffQuota;
    }

    /**
     * Derives manager quota for this branch based on staff quota. Note that manager quota is
     * indedpendent of staff quota.
     */
    public int getManagerQuota() {
        if (staffQuota >= 1 && staffQuota <= 4) {
            return 1;
        }
        if (staffQuota >= 5 && staffQuota <= 8) {
            return 2;
        }
        if (staffQuota >= 9 && staffQuota <= 15) {
            return 3;
        }

        throw new IllegalStateException(
                "Expected Staff quota assigned to branch to be within 1 - 15");
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public Set<User> getStaffs() {
        return staffs;
    }

    public Set<Item> getMenu() {
        return menu;
    }

    public Set<User> getManagers() {
        return managers;
    }
}
