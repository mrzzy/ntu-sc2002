package sg.edu.ntu.sc2002;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class AdminBranchAction implements AdminAction {
    private AdminBranchMethod method;

    /** Constructor to set the Admin methods for the branch action types. */
    public AdminBranchAction(AdminBranchMethod method) {
        this.method = method;
    }

    /**
     * Title of the Action displayed in the user interface.
     *
     * @return Title of the action.
     */
    @Override
    public String title() {
        switch (method) {
            case OPEN_BRANCH:
                return "Open Branch";
            case CLOSE_BRANCH:
                return "Close Branch";
            default: // Last possible case
                return "";
        }
    }

    private void openBranch(Scanner in, Chain chain) {
        System.out.println("Open branch");
        System.out.print("Please enter branch name: ");
        String name = in.next();
        System.out.print("Please enter branch location: ");
        String location = in.next();
        System.out.print("Please enter branch staff quota: ");
        int quota = Input.nextInt(in);

        // Check that no existing branch has the same name AND location
        for (Branch branch : chain.getBranches()) {
            if (branch.getName().equals(name) && branch.getLocation().equals(location)) {
                System.out.printf(
                        "Branch name %s and location %s already exists!\n",
                        branch.getName(), branch.getLocation());
                return;
            }
        }

        Branch branch =
                new Branch(
                        name,
                        location,
                        quota,
                        new HashSet<User>(),
                        new HashSet<User>(),
                        new HashSet<Item>(),
                        new ArrayList<Order>(),
                        new ArrayList<Order>(),
                        new ArrayList<Order>(),
                        new ArrayList<Order>());
        chain.getBranches().add(branch);

        System.out.printf("Successfully opened branch %s at location %s\n", name, location);
    }

    private void closeBranch(Scanner in, Chain chain) {
        System.out.println("Close branch");
        System.out.print("Please enter branch name: ");
        String name = in.next();

        boolean removed = chain.getBranches().removeIf(b -> b.getName().equals(name));

        if (removed) System.out.printf("Successfully closed branch %s\n", name);
        else System.out.printf("%s does not exist to be closed!\n");
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
            case OPEN_BRANCH -> openBranch(in, chain);
            case CLOSE_BRANCH -> closeBranch(in, chain);
        }
        return chain;
    }
}
