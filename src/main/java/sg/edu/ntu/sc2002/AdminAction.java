package sg.edu.ntu.sc2002;

import java.util.HashSet;
import java.util.Scanner;

public class AdminAction implements Action {
    private AdminMethod method;

    public AdminAction(AdminMethod method) {
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
            case ADD_STAFF: return "Add Staff";
            case EDIT_STAFF: return "Edit Staff";
            case REMOVE_STAFF: return "Remove Staff";
            case LIST_STAFF_ALL: return "List All Staff";
            case ASSIGN_STAFF: return "Assign Staff";
            case PROMOTE_STAFF: return "Promote Staff";
            case TRANSFER_STAFF: return "Transfer Staff";
            case ADD_PAYMENT: return "Add Payment";
            case REMOVE_PAYMENT: return "Remove Payment";
            case OPEN_BRANCH: return "Open Branch";
            default: // Last possible case
                return "Close Branch";
        }
    }

    // TODO : Exception here is not intended long term
    private void openBranch(Scanner in, Chain chain) {
        System.out.println("Open branch");
        System.out.print("Please enter branch name: ");
        String name = in.next();
        System.out.print("Please enter branch location: ");
        String location = in.next();
        System.out.print("Please enter branch staff quota: ");
        int quota = in.nextInt();

        // TODO : Handle this case
        // Check that no existing branch has the same name AND location
        for (Branch branch : chain.getBranches()) {
            if (branch.getName().equals(name) && branch.getLocation().equals(location)) {
                System.out.println("Cannot have same branch names and locations");
            }
        }

        Branch branch =
                new Branch(
                        name,
                        location,
                        quota,
                        new HashSet<User>(),
                        new HashSet<User>(),
                        new HashSet<Item>());
        chain.getBranches().add(branch);

        System.out.printf("Successfully opened branch %s\n", name);
    }

    private void closeBranch(Scanner in, Chain chain) {
        System.out.println("Close branch");
        System.out.print("Please enter branch name: ");
        String name = in.next();
        System.out.print("Please enter branch location: ");
        String location = in.next();

        boolean removed =
                chain.getBranches()
                        .removeIf(
                                b -> b.getName().equals(name) && b.getLocation().equals(location));

        if (removed) System.out.printf("Successfully closed branch %s\n", name);
        else
            System.out.println(
                    "Branch name and location does not exist in our records to be closed");
    }

    private void listAllStaff(Scanner in, Chain chain) {
        System.out.println("List all staff");

        // TODO : filter branch / role / gender / age
        for (Branch branch : chain.getBranches()) {
            for (User staff : branch.getStaffs()) {
                System.out.printf(
                        "%s, %s, %s, %d, %s, %s\n",
                        branch.getName(),
                        branch.getLocation(),
                        staff.getName(),
                        staff.getAge(),
                        staff.getGender(),
                        staff.getRole().toString());
            }
        }
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
            case LIST_STAFF_ALL -> listAllStaff(in, chain);
        }

        return chain;
    }
}
