/*
 * NTU SC2002 project
 * Manager Action Handler
 */
package sg.edu.ntu.sc2002;

import java.util.ArrayList;
import java.util.Scanner;

/** Handles all executable Manager Actions on a Fast Food {@link Branch}. */
public class ManagerActionHandler {
    /**
     * Prompts user for the type of action to be performed and passes the specific
     * type of action objects to handleAction method for execution.
     * 
     * @param in     Stdin scanner used by action to read user input.
     * @param branch Fast Food Branch to perform the action on.
     * @return State of Fast Food Branch post performing action.
     */
    public static Branch actionDispatcher(Scanner in, Branch branch) {
        // manager actions
        ArrayList<ManagerAction> managerMenuActions = new ArrayList<>(ManagerRole.getMenuAction());
        ArrayList<ManagerAction> managerStaffActions = new ArrayList<>(ManagerRole.getStaffAction());

        // staff actions
        ArrayList<StaffAction> managerOrderActions = new ArrayList<>(StaffRole.getOrderAction());

        while (true) {
            System.out.println("-------------------------");
            System.out.println("0) Quit");
            System.out.println("1) View menu");
            System.out.println("2) Edit menu action");
            System.out.println("3) Staff action");
            System.out.println("4) Order action");

            int actionChoice = Input.nextInt(in);
            switch (actionChoice) {
                case 0:
                    return branch;
                case 1:
                    ViewMenuAction.viewMenu(branch.getMenu());
                    break;
                case 2:
                    branch = handleAction(managerMenuActions, in, branch);
                    break;
                case 3:
                    branch = handleAction(managerStaffActions, in, branch);
                    break;
                case 4:
                    branch = handleOrderAction(managerOrderActions, in, branch);
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    /**
     * Lists all methods available under a specific type of role-based action and
     * executes based on user's choice at runtime.
     * 
     * @param managerActions The list of ManagerAction objects of a specific type of
     *                       action.
     * @param in             Stdin scanner used by action to read user input.
     * @param branch         Fast Food Branch to perform the action on.
     * @return State of Fast Food Branch post performing action.
     */
    private static Branch handleAction(
            ArrayList<ManagerAction> managerActions, Scanner in, Branch branch) {
        while (true) {
            // Print available actions
            // quit action
            System.out.println("-------------------------");
            System.out.println("0) Quit");
            for (int i = 0; i < managerActions.size(); i++) {
                System.out.println(String.format("%d) %s", i + 1, managerActions.get(i).title()));
            }
            int choice = Input.nextInt(in);
            if (choice <= 0) {
                // quit
                return branch;
            }
            if (choice <= managerActions.size()) {
                // execute action
                branch = managerActions.get(choice - 1).execute(in, branch);
                continue;
            }
            System.out.println("Invalid option.");
        }
    }

    /**
     * Lists all methods available under a specific type of role-based action and
     * executes based on user's choice at runtime.
     * 
     * @param staffActions The list of StaffAction objects of a specific type of
     *                     action.
     * @param in           Stdin scanner used by action to read user input.
     * @param branch       Fast Food Branch to perform the action on.
     * @return State of Fast Food Branch post performing action.
     */
    private static Branch handleOrderAction(
            ArrayList<StaffAction> staffActions, Scanner in, Branch branch) {
        while (true) {
            // Print available actions
            // quit action
            System.out.println("-------------------------");
            System.out.println("0) Quit");
            for (int i = 0; i < staffActions.size(); i++) {
                System.out.println(String.format("%d) %s", i + 1, staffActions.get(i).title()));
            }
            int choice = Input.nextInt(in);
            if (choice <= 0) {
                // quit
                return branch;
            }
            if (choice <= staffActions.size()) {
                // execute action
                branch = staffActions.get(choice - 1).execute(in, branch);
                continue;
            }
            System.out.println("Invalid option.");
        }
    }
}
