package sg.edu.ntu.sc2002;

import java.util.Scanner;
import java.util.ArrayList;

public class StaffActionHandler {

    public static Branch actionDispatcher(Scanner in, Branch branch){
        ArrayList<StaffAction> staffOrderActions = new ArrayList<>(StaffRole.getOrderAction());
        //ArrayList<StaffAction> staffMenuActions = new ArrayList<>(staffRole.getOrderAction());

        while (true) {
            System.out.println("0) Quit");
            System.out.println("1) Staff action");

            int actionChoice = in.nextInt();
            switch (actionChoice) {
                case 0:
                    return branch;
                case 1:
                    branch = handleAction(staffOrderActions, in, branch);
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static Branch handleAction(ArrayList<StaffAction> staffActions, Scanner in, Branch branch) {
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
