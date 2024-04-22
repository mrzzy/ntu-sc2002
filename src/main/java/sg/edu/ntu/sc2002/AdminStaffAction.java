package sg.edu.ntu.sc2002;

import java.util.Scanner;

public class AdminStaffAction implements AdminAction {
    private AdminStaffMethod method;

    public AdminStaffAction(AdminStaffMethod method) {
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
            case ADD_STAFF:
                return "Add Staff";
            case EDIT_STAFF:
                return "Edit Staff";
            case REMOVE_STAFF:
                return "Remove Staff";
            case LIST_STAFF_ALL:
                return "List All Staff";
            default: // Last possible case
                return "";
        }
    }

    private void addStaff(Scanner in, Chain chain) {
        System.out.println("Add Staff");

        System.out.print("Please enter staff name: ");
        String name = in.next();
        System.out.print("Please enter staff username: ");
        String username = in.next();

        System.out.print("Please enter staff branch: ");
        String branchBelongTo = in.next();
        Branch selectedBranch = null;
        for (Branch branch : chain.getBranches()){
            if (branch.getName().equals(branchBelongTo)){
                if (branch.getStaffs().size() >= branch.getStaffQuota()){
                    System.out.println("Quota exceeded.");
                    return;
                } else {
                    selectedBranch = branch;
                    break;
                }
            }
        }
        if (selectedBranch == null){
            System.out.println("Branch does not exist.");
            return;
        }

        System.out.print("Please enter staff age: ");
        int age = Input.nextInt(in);
        System.out.println("Please enter gender (M|F): ");
        // TODO : Parse / Strip
        char genderCode = in.next().charAt(0);
        Gender gender = Gender.fromCode(genderCode);
        System.out.println("Please enter password: ");
        String password = in.next();

        // Create staff
        User user =
                new User(username, name, branchBelongTo, age, gender, password, new StaffRole());

        if (chain.getStaffs().containsKey(username)) {
            System.out.println("The current staff already exists");
            return;
        }

        selectedBranch.getStaffs().add(user);
        chain.getStaffs().put(username, user);
    }

    private void editStaff(Scanner in, Chain chain) {
        System.out.println("Edit Staff");

        System.out.print("Please enter staff username: ");
        String username = in.next();

        if (!chain.getStaffs().containsKey(username)) {
            System.out.println("This user does not exist");
            return;
        }
        User user = chain.getStaffs().get(username);
        User branchUser = null;

        for (Branch branch : chain.getBranches()){
            for (User userInBranch:branch.getStaffs()){
                if (userInBranch.getUsername().equals(username)){
                    branchUser = userInBranch;
                }
            }
        }

        if (branchUser == null) {
            System.out.println("This user does not exist");
            return;
        }

        while (true) {
            System.out.println("0) Quit");
            System.out.println("1) Edit name");
            System.out.println("2) Edit age");
            System.out.println("3) Edit gender");
            System.out.println("4) Edit password");
            int choice = Input.nextInt(in);

            if (choice == 0) break;
            if (choice == 1) {
                System.out.println("Please enter new name: ");
                String newName = in.next();
                user.setName(newName);
                branchUser.setName(newName);
            }
            if (choice == 2) {
                System.out.println("Please enter new age: ");
                int newAge = Input.nextInt(in);
                user.setAge(newAge);
                branchUser.setAge(newAge);
            }
            if (choice == 3) {
                System.out.println("Please enter new gender: ");
                // TODO : foolproof this
                char genderCode = in.next().charAt(0);
                user.setGender(Gender.fromCode(genderCode));
                branchUser.setGender(Gender.fromCode(genderCode));
            }
            if (choice == 4) {
                System.out.println("Please enter new password: ");
                String newPassword = in.next();
                user.setPassword(newPassword);
                branchUser.setPassword(newPassword);
            }
        }
    }

    private void removeStaff(Scanner in, Chain chain) {
        System.out.println("Remove Staff");

        System.out.print("Please enter staff username: ");
        String username = in.next();

        if (!chain.getStaffs().containsKey(username)) {
            System.out.println("This user does not exist");
            return;
        }

        chain.getStaffs().remove(username);
        for (Branch branch : chain.getBranches()){
            for (User user:branch.getStaffs()){
                if (user.getUsername().equals(username)){
                    branch.getStaffs().remove(user);
                    return;
                }
            }
        }
    }

    private void listStaffAll(Scanner in, Chain chain) {
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
                        staff.getRole().code());
            }
        }

        for (Branch branch : chain.getBranches()) {
            for (User manager : branch.getManagers()) {
                System.out.printf(
                        "%s, %s, %s, %d, %s, %s\n",
                        branch.getName(),
                        branch.getLocation(),
                        manager.getName(),
                        manager.getAge(),
                        manager.getGender(),
                        manager.getRole().code());
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
            case ADD_STAFF -> addStaff(in, chain);
            case EDIT_STAFF -> editStaff(in, chain);
            case REMOVE_STAFF -> removeStaff(in, chain);
            case LIST_STAFF_ALL -> listStaffAll(in, chain);
        }
        return chain;
    }
}
