/*
 * NTU SC2002 project
 * Branch
 */

package sg.edu.ntu.sc2002;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.naming.LimitExceededException;

/** Defines a Branch of Fast Food Restaurant {@link Chain}. */
public class Branch implements Serializable {
    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** name of the branch */
    private String name;

    /** location of the branch */
    private String location;

    /** staff quota of the branch */
    private int staffQuota;

    /** staffs in the branch */
    private Set<User> staffs;

    /** managers in the branch */
    private Set<User> managers;

    /** menu of the branch */
    private Set<Item> menu;

    /** list of new orders */
    private List<Order> newOrderList;

    /** list of orders ready to be picked up */
    private List<Order> readyToPickupList;

    /** list of completed orders */
    private List<Order> completedOrderList;

    /** list of cancelled orders */
    private List<Order> cancelledOrderList;

    /** last order id of the branch */
    private int lastOrderId;

    /**
     * Creates a new branch with the given name, location, staff quota, staffs, managers, menu, new
     * order list, ready to pickup list, completed order list and cancelled order list.
     */
    public Branch(
            String name,
            String location,
            int staffQuota,
            Set<User> staffs,
            Set<User> managers,
            Set<Item> menu,
            List<Order> newOrderList,
            List<Order> readyToPickupList,
            List<Order> completedOrderList,
            List<Order> cancelledOrderList) {
        this.name = name;
        this.location = location;
        this.staffQuota = staffQuota;
        this.staffs = staffs;
        this.managers = managers;
        this.menu = menu;
        this.newOrderList = newOrderList;
        this.readyToPickupList = readyToPickupList;
        this.completedOrderList = completedOrderList;
        this.cancelledOrderList = cancelledOrderList;
        this.lastOrderId =
                newOrderList.size()
                        + readyToPickupList.size()
                        + completedOrderList.size()
                        + cancelledOrderList.size();
    }

    /**
     * Creates a new branch with the given name, location and staff quota. Initializes staffs,
     * managers, menu, new order list, ready to pickup list, completed order list and cancelled
     * order list as empty.
     */
    public Branch(String name, String location, int staffQuota) {
        this(
                name,
                location,
                staffQuota,
                new HashSet<>(),
                new HashSet<>(),
                new HashSet<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>());
    }

    /**
     * Assign the given user to work in this branch. User will be assigned based on their {@link
     * IRole}: staff or manager.
     *
     * @throws IllegalArgumentException If given user with an unsupported role.
     * @throws LimitExceededException If assignment of staff exceeds quota.
     */
    public void assign(User user) throws LimitExceededException {
        switch (user.getRole()) {
            case ManagerRole r -> {
                if (getManagerQuota() <= getManagers().size()) {
                    throw new LimitExceededException("Manager assignment exceeds quota.");
                }
                getManagers().add(user);
            }
            case StaffRole r -> {
                if (getStaffQuota() <= getStaffs().size()) {
                    throw new LimitExceededException("Staff assignment exceeds quota.");
                }
                getStaffs().add(user);
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
     * independent of staff quota.
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

    // getter

    /**
     * Name of the branch.
     *
     * @return Name of the branch.
     */
    public String getName() {
        return name;
    }

    /**
     * Location of the branch.
     *
     * @return Location of the branch.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Staff in the branch.
     *
     * @return List of Staff in the branch.
     */
    public Set<User> getStaffs() {
        return staffs;
    }

    /**
     * Menu of the branch.
     *
     * @return Menu of the branch.
     */
    public Set<Item> getMenu() {
        return menu;
    }

    /**
     * Menu of the branch, sorted in alphanumeric order of name.
     *
     * @return Sorted Menu of the branch.
     */
    public List<Item> getSortedMenu() {
        return menu.stream()
                .sorted(Comparator.comparing(Item::getName))
                .collect(Collectors.toList());
    }

    /**
     * Managers in the branch.
     *
     * @return List of managers in the branch.
     */
    public Set<User> getManagers() {
        return managers;
    }

    /**
     * Orders ready to be picked up.
     *
     * @return List of orders ready to be picked up.
     */
    public List<Order> getReadyToPickupList() {
        return this.readyToPickupList;
    }

    /**
     * New orders.
     *
     * @return List of new orders.
     */
    public List<Order> getNewOrderList() {
        return this.newOrderList;
    }

    /**
     * Completed orders.
     *
     * @return List of completed orders.
     */
    public List<Order> getCompletedOrderList() {
        return this.completedOrderList;
    }

    /**
     * Cancelled orders.
     *
     * @return List of cancelled orders.
     */
    public List<Order> getCancelledOrderList() {
        return this.cancelledOrderList;
    }

    /**
     * Last order id.
     *
     * @return Last order id.
     */
    public int getOrderId() {
        return this.lastOrderId;
    }

    // setter
    /** Set the order id. */
    public void setOrderId() {
        this.lastOrderId++;
    }

    @Override
    public int hashCode() {
        // branches are unique by name
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Branch other = (Branch) obj;
        if (name == null) {
            if (other.name != null) return false;
        } else if (!name.equals(other.name)) return false;
        if (location == null) {
            if (other.location != null) return false;
        } else if (!location.equals(other.location)) return false;
        if (staffQuota != other.staffQuota) return false;
        if (staffs == null) {
            if (other.staffs != null) return false;
        } else if (!staffs.equals(other.staffs)) return false;
        if (managers == null) {
            if (other.managers != null) return false;
        } else if (!managers.equals(other.managers)) return false;
        if (menu == null) {
            if (other.menu != null) return false;
        } else if (!menu.equals(other.menu)) return false;
        if (newOrderList == null) {
            if (other.newOrderList != null) return false;
        } else if (!newOrderList.equals(other.newOrderList)) return false;
        if (readyToPickupList == null) {
            if (other.readyToPickupList != null) return false;
        } else if (!readyToPickupList.equals(other.readyToPickupList)) return false;
        if (completedOrderList == null) {
            if (other.completedOrderList != null) return false;
        } else if (!completedOrderList.equals(other.completedOrderList)) return false;
        if (cancelledOrderList == null) {
            if (other.cancelledOrderList != null) return false;
        } else if (!cancelledOrderList.equals(other.cancelledOrderList)) return false;
        if (lastOrderId != other.lastOrderId) return false;
        return true;
    }

    public void checkExpired() {
        Expired expired = new Expired();
        expired.cancelChecker(this);
    }
}
