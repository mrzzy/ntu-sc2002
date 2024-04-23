package sg.edu.ntu.sc2002;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

/** Handles all executable Customer Actions on a Fast Food {@link Branch}. */
public class CustomerActionHandler {
    /**
     * Prompts user for the type of action to be performed and passes the specific type of action
     * objects to handleAction method for execution.
     *
     * @param in Stdin scanner used by action to read user input.
     * @param branch Fast Food Branch to perform the action on.
     * @param paymentMethods Set of payment methods supported by the Fast Food Chain.
     * @return State of Fast Food Branch post performing action.
     */
    public static Branch actionDispatcher(
            Scanner in, Branch branch, Set<IPaymentMethod> paymentMethods) {

        // customer methods
        ArrayList<ICustomerAction> customerOrderActions =
                new ArrayList<>(CustomerRole.getOrderAction());
        ArrayList<ICustomerAction> customerCollectActions =
                new ArrayList<>(CustomerRole.getCollectAction());

        while (true) {
            System.out.println("-------------------------");
            System.out.println("0) Quit");
            System.out.println("1) View menu");
            System.out.println("2) Order action");
            System.out.println("3) Collect action");

            int actionChoice = Input.nextInt(in);
            switch (actionChoice) {
                case 0:
                    return branch;
                case 1:
                    ViewMenuAction.viewMenu(branch);
                    break;
                case 2:
                    branch = handleAction(customerOrderActions, in, branch, paymentMethods);
                    break;
                case 3:
                    branch = handleAction(customerCollectActions, in, branch, paymentMethods);
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    /**
     * Lists all methods available under a specific type of role-based action and executes based on
     * user's choice at runtime.
     *
     * @param customerActions The list of CustomerAction objects of a specific type of action.
     * @param in Stdin scanner used by action to read user input.
     * @param branch Fast Food Branch to perform the action on.
     * @param paymentMethods Set of payment methods supported by the Fast Food Chain.
     * @return State of Fast Food Branch post performing action.
     */
    public static Branch handleAction(
            ArrayList<ICustomerAction> customerActions,
            Scanner in,
            Branch branch,
            Set<IPaymentMethod> paymentMethods) {
        while (true) {
            // Print available actions
            // quit action
            System.out.println("-------------------------");
            System.out.println("0) Quit");
            for (int i = 0; i < customerActions.size(); i++) {
                System.out.println(String.format("%d) %s", i + 1, customerActions.get(i).title()));
            }

            int choice = Input.nextInt(in);
            if (choice <= 0) {
                // quit
                return branch;
            }
            if (choice <= customerActions.size()) {
                // execute action
                branch = customerActions.get(choice - 1).execute(in, branch, paymentMethods);
                continue;
            }
            System.out.println("Invalid option.");
        }
    }
}
