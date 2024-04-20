/*
 * NTU SC2002 project
 * Manager Action Handler
 */
package sg.edu.ntu.sc2002;

import java.util.ArrayList;
import java.util.Scanner;

public class ManagerActionHandler {

    public static Branch actionDispatcher(Scanner in, Branch branch) {

        // manager methods
        ArrayList<ManagerAction> managerMenuActions = new ArrayList<>(ManagerRole.getMenuAction());
        ArrayList<ManagerAction> managerStaffActions = new ArrayList<>(ManagerRole.getStaffAction());

        // staff methods
        ArrayList<StaffAction> managerOrderActions = new ArrayList<>(staffRole.getOrderAction());

        while (true) {
            System.out.println("0) Quit");
            System.out.println("1) Menu action");
            System.out.println("2) Staff action");
            System.out.println("3) Order action");

            int actionChoice = in.nextInt();
            switch (actionChoice) {
                case 0:
                    return branch;
                case 1:
                    branch = handleAction(managerMenuActions, in, branch);
                    break;
                case 2:
                    branch = handleAction(managerStaffActions, in, branch);
                    break;
                case 3:
                    branch.setOrderList(handleAction(managerOrderActions, in, branch.getOrderList()));
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static Branch handleAction(ArrayList<ManagerAction> managerActions, Scanner in, Branch branch) {
        while (true) {
            // Print available actions
            // quit action
            System.out.println("0) Quit");
            for (int i = 0; i < managerActions.size(); i++) {
                System.out.println(String.format("%d) %s", i + 1, managerActions.get(i).title()));
            }
            int choice = in.nextInt();
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

    private static ArrayList<Order> handleAction(ArrayList<StaffAction> staffActions, Scanner in,
            ArrayList<Order> orderList) {
        while (true) {
            // Print available actions
            // quit action
            System.out.println("0) Quit");
            for (int i = 0; i < staffActions.size(); i++) {
                System.out.println(String.format("%d) %s", i + 1, staffActions.get(i).title()));
            }
            int choice = in.nextInt();
            if (choice <= 0) {
                // quit
                return orderList;
            }
            if (choice <= staffActions.size()) {
                // execute action
                orderList = staffActions.get(choice - 1).execute(in, orderList);
                continue;
            }
            System.out.println("Invalid option.");
        }
    }
}
