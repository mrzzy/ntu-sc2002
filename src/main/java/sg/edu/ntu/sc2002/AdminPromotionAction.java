package sg.edu.ntu.sc2002;

import java.util.Scanner;

public class AdminPromotionAction implements IAdminAction {
    private AdminPromotionMethod method;

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

        System.out.println("Please enter branch name: ");
        String branch = in.next();

        for (Branch b : chain.getBranches()) {
            if (b.getName().equals(branch)) {
                // Check how many branch managers exist in this branch
                if (b.getManagers().size() >= b.getManagerQuota()) {
                    System.out.println(
                            "This branch has hit it's total number of branch managers and is unable"
                                    + " to add more");
                    return;
                }

                // Add the new manager in
                b.getManagers().add(user);
                System.out.printf("%s has been assigned to %s\n", user.getName(), b.getName());
            }
        }
    }

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

        // Once the branch is selected, check that there is space for
        // staff -> manager promotion, under the quota
        if (selectedBranch.getManagers().size() >= selectedBranch.getManagerQuota()) {
            System.out.println(
                    "Selected branch does not have enough quota to contain more managers");
            return;
        }

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
