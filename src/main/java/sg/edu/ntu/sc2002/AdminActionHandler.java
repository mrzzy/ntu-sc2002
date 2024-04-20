package sg.edu.ntu.sc2002;

import java.util.ArrayList;
import java.util.Scanner;

public class AdminActionHandler {

    public static Chain actionDispatcher(Scanner in, Chain chain){
        ArrayList<AdminAction> adminTransferActions = new ArrayList<>(AdminRole.getTransferAction());
        ArrayList<AdminAction> adminPromotionActions = new ArrayList<>(AdminRole.getPromotionAction());
        ArrayList<AdminAction> adminStaffActions = new ArrayList<>(AdminRole.getStaffAction());
        ArrayList<AdminAction> adminPaymentActions = new ArrayList<>(AdminRole.getPaymentAction());
        ArrayList<AdminAction> adminBranchActions = new ArrayList<>(AdminRole.getBranchAction());

        while (true) {
            System.out.println("0) Quit");
            System.out.println("1) Transfer action");
            System.out.println("2) Promotion action");
            System.out.println("3) Staff action");
            System.out.println("4) Payment action");
            System.out.println("5) Branch action");

            int actionChoice = in.nextInt();
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

    public static Chain handleAction(ArrayList<AdminAction> adminActions, Scanner in, Chain chain){
        while (true) {
            // Print available actions
            // quit action
            System.out.println("0) Quit");
            for (int i = 0; i < adminActions.size(); i++) {
                System.out.println(String.format("%d) %s", i + 1, adminActions.get(i).title()));
            }
            int choice = in.nextInt();
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
