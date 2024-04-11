/*
 * NTU SC2002 project
 * Branch
 */

package sg.edu.ntu.sc2002;

import java.io.Serializable;
import java.util.Set;

/** Defines a Branch of Fast Food Restaurant {@link Chain}. */
public class Branch implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String location;
    private int staffQuota;
    private Set<User> staff;
    private Set<Item> menu;

    public Branch(String name, String location, int staffQuota, Set<User> staff, Set<Item> menu) {
        this.name = name;
        this.location = location;
        this.staffQuota = staffQuota;
        this.staff = staff;
        this.menu = menu;
    }

    public String getName() {
        return name;
    }

    /** Get the staff quota for this branch.
     * Note that staff quota is excludes managers in the branch.
    */
    public int getStaffQuota() {
        return staffQuota;
    }

    /** Derives manager quota for this branch based on staff quota.
     * Note that manager quota is indedpendent of staff quota.
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

    public String getLocation() {
        return location;
    }

    public Set<User> getStaff() {
        return staff;
    }

    public Set<Item> getMenu() {
        return menu;
    }
}
