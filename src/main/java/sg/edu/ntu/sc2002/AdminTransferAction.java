package sg.edu.ntu.sc2002;

import java.util.Scanner;
import javax.naming.LimitExceededException;

/** Implementation of the {@link IAdminAction} interface. */
public class AdminTransferAction implements IAdminAction {
    private AdminTransferMethod method;

    /**
     * Constructor for AdminTransferAction.
     *
     * @param method AdminTransferMethod to be executed.
     */
    public AdminTransferAction(AdminTransferMethod method) {
        this.method = method;
    }

    /**
     * Title of the Action displayed in the user interface.
     *
     * @return Tile of the action.
     */
    @Override
    public String title() {
        switch (method) {
            case TRANSFER_STAFF:
                return "Transfer Staff";
            default: // Last possible case
                return "";
        }
    }

    /**
     * Transfer a staff from one branch to another branch, can only transfer within the
     * staff/manager quota limits
     *
     * @param in Stdin scanner used by action to read user input.
     * @param chain Fast Food Chain to perform the action on.
     */
    private void transferStaff(Scanner in, Chain chain) {
        System.out.println("Transfer staff");

        // Get branch FROM
        System.out.println("Please select from which branch you want to transfer from: ");
        System.out.println("Available branches : ");
        for (Branch b : chain.getBranches()) {
            System.out.println(b.getName());
        }

        String branchFrom = in.next();
        Branch selectedFromBranch = null;
        for (Branch b : chain.getBranches()) {
            if (b.getName().equals(branchFrom)) {
                selectedFromBranch = b;
                break;
            }
        }

        if (selectedFromBranch == null) {
            System.out.println("The branch selected does not exist");
            return;
        }

        // Get a staff/manager from branch FROM
        System.out.println("Please select the staff/manager username you want to transfer : ");
        System.out.println("Available staff : ");
        for (User user : selectedFromBranch.getStaffs()) {
            System.out.printf("Staff : %s\n", user.getUsername());
        }
        for (User user : selectedFromBranch.getManagers()) {
            System.out.printf("Manager : %s\n", user.getUsername());
        }

        String username = in.next();
        User selectedUser = null;
        for (User user : selectedFromBranch.getStaffs()) {
            if (user.getUsername().equals(username)) {
                selectedUser = user;
                break;
            }
        }
        for (User user : selectedFromBranch.getManagers()) {
            if (user.getUsername().equals(username)) {
                selectedUser = user;
                break;
            }
        }

        if (selectedUser == null) {
            System.out.println("Selected user does not exist");
            return;
        }

        // Get branch To
        System.out.println("Please select from which branch you want to transfer to:");
        System.out.println("Available branches : ");
        for (Branch b : chain.getBranches()) {
            System.out.println(b.getName());
        }

        String branchTo = in.next();
        Branch selectedToBranch = null;
        for (Branch b : chain.getBranches()) {
            if (b.getName().equals(branchTo)) {
                selectedToBranch = b;
                break;
            }
        }

        if (selectedToBranch == null) {
            System.out.println("The branch selected does not exist");
            return;
        }

        // Check that the from and to branch is not the same
        if (branchFrom.equals(branchTo)) {
            System.out.println("The branch you are transferring from and to is the same!");
            return;
        }

        // If it's a staff, check the quota limit
        if (selectedUser.getRole().code() == 'S') {
            try {
                // Transfer the user to this branch
                selectedToBranch.assign(selectedUser);
                // If successful, set the branch
                selectedUser.setBranchBelongTo(selectedToBranch.getName());
                // Remove from old branch
                selectedFromBranch.getStaffs().remove(selectedUser);
            } catch (LimitExceededException e) {
                System.out.println(
                        "The branch you selected to transfer to has already has maxed out their"
                                + " staff quota");
                return;
            }
        }

        // If it's a manager, check the manager quota limit
        if (selectedUser.getRole().code() == 'M') {
            try {
                selectedToBranch.assign(selectedUser);
                selectedUser.setBranchBelongTo(selectedToBranch.getName());
                selectedFromBranch.getManagers().remove(selectedUser);
            } catch (LimitExceededException e) {
                System.out.println(
                        "The branch you selected to transfer to has already maxed out their manager"
                                + " quota");
                return;
            }
        }

        System.out.printf(
                "Successfully transferred %s from %s to %s\n",
                selectedUser.getUsername(),
                selectedFromBranch.getName(),
                selectedToBranch.getName());
    }

    /**
     * Execute Action on the given Fast Food Chain.
     *
     * @param in Stdin scanner used by action to read user input.
     * @param chain Fast Food Chain to perform the action on.
     * @return State of Fast Food Chain post performing action.
     */
    @Override
    public Chain execute(Scanner in, Chain chain) {
        switch (this.method) {
            case TRANSFER_STAFF -> transferStaff(in, chain);
        }
        return chain;
    }
}
