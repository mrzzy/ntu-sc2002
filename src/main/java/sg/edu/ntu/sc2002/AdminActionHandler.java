package sg.edu.ntu.sc2002;

import java.util.ArrayList;
import java.util.Scanner;

/** Handles all executable Admin Actions on a Fast Food {@link Chain}. */
public class AdminActionHandler {
    /**
     * Prompts user for the type of action to be performed and passes the specific type of action objects to handleAction method for execution.
     * @param in Stdin scanner used by action to read user input.
     * @param chain Fast Food Chain to perform the action on.
     * @return State of Fast Food Chain post performing action.
     */
    public static Chain actionDispatcher(Scanner in, Chain chain) {
        ArrayList<AdminAction> adminTransferActions =
                new ArrayList<>(AdminRole.getTransferAction());
        ArrayList<AdminAction> adminPromotionActions =
                new ArrayList<>(AdminRole.getPromotionAction());
        ArrayList<AdminAction> adminStaffActions = new ArrayList<>(AdminRole.getStaffAction());
        ArrayList<AdminAction> adminPaymentActions = new ArrayList<>(AdminRole.getPaymentAction());
        ArrayList<AdminAction> adminBranchActions = new ArrayList<>(AdminRole.getBranchAction());

        while (true) {
            System.out.println("-------------------------");
            System.out.println("0) Quit");
            System.out.println("1) Transfer action");
            System.out.println("2) Promotion action");
            System.out.println("3) Staff action");
            System.out.println("4) Payment action");
            System.out.println("5) Branch action");

            int actionChoice = Input.nextInt(in);
            switch (actionChoice) {
                case 0:
                    return chain;
                case 1:
                    chain = handleAction(adminTransferActions, in, chain);
                    break;
                case 2:
                    chain = handleAction(adminPromotionActions, in, chain);
                    break;
                case 3:
                    chain = handleAction(adminStaffActions, in, chain);
                    break;
                case 4:
                    chain = handleAction(adminPaymentActions, in, chain);
                    break;
                case 5:
                    chain = handleAction(adminBranchActions, in, chain);
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    /**
     * Lists all methods available under a specific type of role-based action and executes based on user's choice at runtime.
     * @param adminActions The list of AdminAction objects of a specific type of action.
     * @param in Stdin scanner used by action to read user input.
     * @param chain Fast Food Chain to perform the action on.
     * @return State of Fast Food Chain post performing action.
     */
    public static Chain handleAction(ArrayList<AdminAction> adminActions, Scanner in, Chain chain) {
        while (true) {
            // Print available actions
            // quit action
            System.out.println("-------------------------");
            System.out.println("0) Quit");
            for (int i = 0; i < adminActions.size(); i++) {
                System.out.println(String.format("%d) %s", i + 1, adminActions.get(i).title()));
            }
            int choice = Input.nextInt(in);
            if (choice <= 0) {
                // quit
                return chain;
            }
            if (choice <= adminActions.size()) {
                // execute action
                chain = adminActions.get(choice - 1).execute(in, chain);
                continue;
            }
            System.out.println("Invalid option.");
        }
    }
}
