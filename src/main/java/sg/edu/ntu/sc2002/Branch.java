/*
* NTU SC2002 project
* Branch
*/

package sg.edu.ntu.sc2002;

import java.io.Serializable;
import java.util.Set;

/** Defines a Branch of Fast Food Restaurant {@link Chain}. */
public class Branch implements Serializable {
    private final static long serialVersionUID = 1L;
    private String name;
    private String location;
    private Set<User> staff;
    private Set<Item> menu;

    public Branch(String name, String location, Set<User> staff, Set<Item> menu) {
        this.name = name;
        this.location = location;
        this.staff = staff;
        this.menu = menu;
    }

    public String getName() {
        return name;
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
