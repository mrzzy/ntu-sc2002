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
            case ADD_STAFF:
                return "Add Staff";
            case EDIT_STAFF:
                return "Edit Staff";
            case REMOVE_STAFF:
                return "Remove Staff";
            case LIST_STAFF_ALL:
                return "List All Staff";
            case ASSIGN_MANAGER:
                return "Assign Manager";
            case PROMOTE_STAFF:
                return "Promote Staff";
            case TRANSFER_STAFF:
                return "Transfer Staff";
            case ADD_PAYMENT:
                return "Add Payment";
            case REMOVE_PAYMENT:
                return "Remove Payment";
            case OPEN_BRANCH:
                return "Open Branch";
            default: // Last possible case
                return "Close Branch";
        }
    }

    private void addStaff(Scanner in, Chain chain) {
        System.out.println("Add Staff");

        System.out.print("Please enter staff name: ");
        String name = in.next();
        System.out.print("Please enter staff username: ");
        String username = in.next();
        System.out.print("Please enter staff age: ");
        int age = in.nextInt();
        System.out.println("Please enter gender (M|F): ");
        // TODO : Parse / Strip
        char genderCode = in.next().charAt(0);
        Gender gender = Gender.fromCode(genderCode);
        System.out.println("Please enter password: ");
        String password = in.next();

        // Create staff
        User user = new User(username, name, age, gender, password, new StaffRole());

        if (chain.getStaffs().containsKey(username)) {
            System.out.println("The current staff already exists");
            return;
        }

        chain.getStaffs().put(username, user);
    }

    private void editStaff(Scanner in, Chain chain) {
        System.out.println("Edit Staff");

        System.out.print("Please enter staff username: ");
        String username = in.next();

        if (!chain.getStaffs().containsKey(username)) {
            System.out.println("This user does not exist");
        }

        User user = chain.getStaffs().get(username);

        while (true) {
            System.out.println("0) Quit");
            System.out.println("1) Edit name");
            System.out.println("2) Edit age");
            System.out.println("3) Edit gender");
            System.out.println("4) Edit password");
            int choice = in.nextInt();

            if (choice == 0) break;
            if (choice == 1) {
                System.out.println("Please enter new name: ");
                String newName = in.next();
                user.setName(newName);
            }
            if (choice == 2) {
                System.out.println("Please enter new age: ");
                int newAge = in.nextInt();
                user.setAge(newAge);
            }
            if (choice == 3) {
                System.out.println("Please enter new gender: ");
                // TODO : foolproof this
                char genderCode = in.next().charAt(0);
                user.setGender(Gender.fromCode(genderCode));
            }
            if (choice == 4) {
                System.out.println("Please enter new password: ");
                String newPassword = in.next();
                user.setPassword(newPassword);
            }
        }
    }

    private void removeStaff(Scanner in, Chain chain) {
        System.out.println("Remove Staff");

        System.out.print("Please enter staff username: ");
        String username = in.next();

        if (!chain.getStaffs().containsKey(username)) {
            System.out.println("This user does not exist");
        }

        chain.getStaffs().remove(username);
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
            System.out.println("Selected branch does not have enough quota to contain more managers");
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

        System.out.printf("%s has been promoted from staff to manager at branch %s\n", selectedUser.getUsername(), selectedBranch.getName());
    }

    private void transferStaff(Scanner in, Chain chain) {
        System.out.println("Transfer staff");

        // Get branch FROM
        System.out.println("Please select from which branch you want to transfer from: ");
        System.out.println("Available branches : ");
        for (Branch b: chain.getBranches()) {
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
            System.out.println(user.getUsername());
        }
        for (User user : selectedFromBranch.getManagers()) {
            System.out.println(user.getUsername());
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
        for (Branch b: chain.getBranches()) {
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
        if (branchFrom == branchTo) {
            System.out.println("The branch you are transferring from and to is the same!");
            return;
        }

        // If it's a staff, check the quota limit
        if (selectedUser.getRole().code() == 'S') {
            if (selectedToBranch.getStaffs().size() >= selectedToBranch.getStaffQuota()) {
                System.out.println("The branch you selected to transfer to has already has maxed out their staff quota");
                return;
            }
            // Transfer
            selectedToBranch.getStaffs().add(selectedUser);
            selectedFromBranch.getStaffs().remove(selectedUser);
        }
        // If it's a manager, check the manager quota limit
        if (selectedUser.getRole().code() == 'M') {
            if (selectedToBranch.getManagers().size() >= selectedToBranch.getManagerQuota()) {
                System.out.println("The branch you selected to transfer to has already haxed out their manager quota");
                return;
            }
            selectedToBranch.getManagers().add(selectedUser);
            selectedFromBranch.getStaffs().remove(selectedUser);
        }

        System.out.printf("Successfully transferred %s from %s to %s\n", selectedUser.getUsername(), selectedFromBranch.getName(), selectedToBranch.getName());
    }

    private void addPayment(Scanner in, Chain chain) {
        System.out.println("Add payment");

        System.out.println("Please enter payment method name");
        String paymentName = in.next();

        for (PaymentMethod paymentMethod: chain.getPaymentMethods()) {
            if (paymentMethod.getName().equals(paymentName)) {
                System.out.println("Payment method already exists!");
                return;
            }
        }

        chain.getPaymentMethods().add(new PaymentMethod(paymentName));

        System.out.printf("Added %s to payment methods\n", paymentName);
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
            case ADD_STAFF -> addStaff(in, chain);
            case EDIT_STAFF -> editStaff(in, chain);
            case REMOVE_STAFF -> removeStaff(in, chain);
            case LIST_STAFF_ALL -> listAllStaff(in, chain);
            case ASSIGN_MANAGER -> assignManager(in, chain);
            case PROMOTE_STAFF -> promoteStaff(in, chain);
            case TRANSFER_STAFF -> transferStaff(in, chain);
            case ADD_PAYMENT -> addPayment(in, chain);
            case OPEN_BRANCH -> openBranch(in, chain);
            case CLOSE_BRANCH -> closeBranch(in, chain);
        }

        return chain;
    }
}
