/*
 * NTU SC2002 project
 * Manager Staff Action
 */
package sg.edu.ntu.sc2002;

import java.util.Scanner;

/** Implementation of the {@link IManagerAction} interface. */
public class ManagerStaffAction implements IManagerAction {
    /** Method to be executed by the action. */
    private ManagerStaffMethod method;

    /**
     * Constructor to create a Manager Staff Action.
     *
     * @param method Method of the Manager Staff Action.
     */
    public ManagerStaffAction(ManagerStaffMethod method) {
        this.method = method;
    }

    /**
     * Title of the Action displayed in the user interface.
     *
     * @return Title of the action.
     */
    @Override
    public String title() {
        switch (method) {
            case LIST_STAFF_ALL:
                return "List All Staff";
            default: // Last possible case
                return "";
        }
    }

    /**
     * List all staff details on the given Fast Food Branch.
     *
     * @param in Stdin scanner used by action to read user input.
     * @param branch Fast Food Branch to perform the action on.
     */
    private void listStaffAll(Scanner in, Branch branch) {
        System.out.println("-------------------------");
        System.out.println("List of Managers:");
        for (User manager : branch.getManagers()) {
            System.out.println(String.format("Name: %s, Age: %d", manager.getName(), manager.getAge()));
        }
        System.out.println("-------------------------");
        System.out.println("List of Staff:");
        for (User staff : branch.getStaffs()) {
            System.out.println(String.format("Name: %s, Age: %d", staff.getName(), staff.getAge()));
        }
    }

    /**
     * Execute Action on the given Fast Food Branch.
     *
     * @param in Stdin scanner used by action to read user input.
     * @param branch Fast Food Branch to perform the action on.
     * @return State of Fast Food Branch post performing action.
     */
    @Override
    public Branch execute(Scanner in, Branch branch) {
        switch (this.method) {
            case LIST_STAFF_ALL -> listStaffAll(in, branch);
        }

        return branch;
    }
}
