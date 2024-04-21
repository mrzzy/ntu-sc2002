package sg.edu.ntu.sc2002;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

public class CustomerActionHandler {
    public static Branch actionDispatcher(Scanner in, Branch branch, Set<PaymentMethod> paymentMethods) {

        // customer methods
        ArrayList<CustomerAction> customerOrderActions = new ArrayList<>(CustomerRole.getOrderAction());
        ArrayList<CustomerAction> customerCollectActions = new ArrayList<>(CustomerRole.getCollectAction());

        while (true) {
            System.out.println("-------------------------");
            System.out.println("0) Quit");
            System.out.println("1) View menu");
            System.out.println("2) Order action");
            System.out.println("3) Collect action");

            int actionChoice = in.nextInt();
            switch (actionChoice) {
                case 0:
                    return branch;
                case 1:
                    ViewMenuAction.viewMenu(branch.getMenu());
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
    
    public static Branch handleAction(ArrayList<CustomerAction> customerActions, Scanner in, Branch branch, Set<PaymentMethod> paymentMethods){
        while (true) {
            // Print available actions
            // quit action
            System.out.println("-------------------------");
            System.out.println("0) Quit");
            for (int i = 0; i < customerActions.size(); i++) {
                System.out.println(String.format("%d) %s", i + 1, customerActions.get(i).title()));
            }
            int choice = in.nextInt();
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
