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

        chain.getStaffs().forEach((k, v) -> {
            if (v.getRole().code() == 'M') {
                System.out.println(k.toString());
            }
        });
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
            case OPEN_BRANCH -> openBranch(in, chain);
            case CLOSE_BRANCH -> closeBranch(in, chain);
        }

        return chain;
    }
}
