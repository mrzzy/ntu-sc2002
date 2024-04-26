package sg.edu.ntu.sc2002;

import java.util.Scanner;
import javax.naming.LimitExceededException;

/** Implementation of the {@link IAdminAction} interface. */
public class AdminPromotionAction implements IAdminAction {
    private AdminPromotionMethod method;

    /** Constructor for AdminPromotionAction class */
    public AdminPromotionAction(AdminPromotionMethod method) {
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
            case ASSIGN_MANAGER:
                return "Assign Manager";
            case PROMOTE_STAFF:
                return "Promote Staff";
            default: // Last possible case
                return "";
        }
    }

    /**
     * Assigns a manager to a branch given that there is enough quota
     *
     * @param in Stdin scanner used by action to read user input.
     * @param chain Fast Food Chain to perform the action on.
     */
    private void assignManager(Scanner in, Chain chain) {
        System.out.println("Assign manager");

        System.out.print("Please enter manager username: ");
        String username = in.next();

        if (!chain.getStaffs().containsKey(username)) {
            System.out.println("This user does not exist");
            return;
        }

        User user = chain.getStaffs().get(username);
        if (user.getRole().code() != 'M') {
            System.out.println("This user is not a manager");
            return;
        }

        System.out.println("Please enter branch name to transfer to: ");
        String branch = in.next();

        Branch removeBranch = null;
        Branch assignBranch = null;

        for (Branch b : chain.getBranches()) {
            // Remove the user from the old branch
            if (b.getName().equals(user.getBranchBelongTo())) {
                removeBranch = b;
            }
            if (b.getName().equals(branch)) {
                assignBranch = b;
            }
        }

        // Check that the new branch has quota
        // int managerQuota = assignBranch.getManagerQuota();
        // int managerCount = assignBranch.getManagers().size();
        // if (managerCount >= managerQuota) {
        // System.out.printf(
        // "This branch has hit it's total number of branch managers of %d with total"
        // + " staff count of %d and is unable to add more managers.\n",
        // managerQuota, assignBranch.getStaffs().size());
        // return;
        // }

        try {
            // Update the user branch name
            user.setBranchBelongTo(assignBranch.getName());
            // Remove manager from old branch
            removeBranch.getManagers().remove(user);
            // Add the new manager in
            assignBranch.assign(user);
        } catch (LimitExceededException e) {
            System.out.printf(
                    "This branch has hit it's total number of branch managers of %d with total"
                            + " staff count of %d and is unable to add more managers.\n",
                    assignBranch.getManagerQuota(), assignBranch.getStaffs().size());
            removeBranch.getManagers().add(user);
            user.setBranchBelongTo(removeBranch.getName());
            ;
            return;
        }

        System.out.println("Added to new branch");
        System.out.printf("%s has been assigned to %s\n", user.getName(), assignBranch.getName());
    }

    /**
     * Promotes a staff to a manager
     *
     * @param in Stdin scanner used by action to read user input.
     * @param chain Fast Food Chain to perform the action on.
     */
    private void promoteStaff(Scanner in, Chain chain) {
        System.out.println("Promote staff");

        System.out.print("Please enter the branch that you want to promote the staff from: ");
        String branch = in.next();

        Branch selectedBranch = null;
        for (Branch b : chain.getBranches()) {
            if (b.getName().equals(branch)) {
                selectedBranch = b;
                break;
            }
        }

        if (selectedBranch == null) {
            System.out.println("This branch does not exist");
            return;
        }

        // Promotion does not have quota checking
        // Once the branch is selected, check that there is space for
        // staff -> manager promotion, under the quota
        // if (selectedBranch.getManagers().size() >= selectedBranch.getManagerQuota())
        // {
        // System.out.println(
        // "Selected branch does not have enough quota to contain more managers");
        // return;
        // }

        // if (selectedBranch.getStaffs().size() == 1) {
        // System.out.println(
        // "Cannot promote to branch manager if a branch only has one staff! 0 staffs at
        // a"
        // + " branch is not allowed");
        // return;
        // }

        System.out.println("Please enter the username of the staff you want to promote:");
        System.out.println("Available staff:");
        // Print out all available staff that is not a manger
        for (User staff : selectedBranch.getStaffs()) {
            System.out.printf("%s \n", staff.getUsername());
        }

        String username = in.next();
        User selectedUser = null;
        for (User staff : selectedBranch.getStaffs()) {
            if (staff.getUsername().equals(username)) {
                selectedUser = staff;
                break;
            }
        }

        if (selectedUser == null) {
            System.out.println("Invalid username selected!");
            return;
        }

        // Change to manager role
        selectedUser.setRole(new ManagerRole());
        // Remove from existing staff set
        // Add to branch manager set
        selectedBranch.getStaffs().remove(selectedUser);
        selectedBranch.getManagers().add(selectedUser);

        System.out.printf(
                "%s has been promoted from staff to manager at branch %s\n",
                selectedUser.getUsername(), selectedBranch.getName());
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
            case ASSIGN_MANAGER -> assignManager(in, chain);
            case PROMOTE_STAFF -> promoteStaff(in, chain);
        }
        return chain;
    }
}
