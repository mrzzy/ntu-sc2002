package sg.edu.ntu.sc2002;

import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AdminStaffAction implements IAdminAction {
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

    /**
     * Adds a new staff to a branch
     *
     * @param in Stdin scanner used by action to read user input.
     * @param chain Fast Food Chain to perform the action on.
     */
    private void addStaff(Scanner in, Chain chain) {
        System.out.println("Add Staff");

        System.out.print("Please enter staff name: ");
        String name = in.next();
        System.out.print("Please enter staff username: ");
        String username = in.next();
        System.out.print("Please enter staff branch: ");
        String branch = in.next();

        Branch selectedBranch =
                chain.getBranches().stream()
                        .filter(b -> branch.equals(b.getName()))
                        .findAny()
                        .orElse(null);
        if (selectedBranch == null) {
            System.out.println("Branch does not exist.");
            return;
        }

        System.out.print("Please enter staff age: ");
        int age = Input.nextInt(in);
        System.out.print("Please enter gender (M|F): ");
        char genderCode = in.next().charAt(0);
        // Sanity check
        if (genderCode != 'M' && genderCode != 'F') {
            System.out.println("Please enter M or F for gender");
            return;
        }
        Gender gender = Gender.fromCode(genderCode);

        System.out.print("Please enter password: ");
        String password = in.next();

        // Create staff
        User user = new User(username, name, branch, age, gender, password, new StaffRole());

        if (chain.getStaffs().containsKey(username) || username == "boss") {
            System.out.println("The current staff already exists");
            return;
        }

        selectedBranch.getStaffs().add(user);
        // chain.getStaffs().put(username, user);
    }

    /**
     * Edits the details of an existing staff
     *
     * @param in Stdin scanner used by action to read user input.
     * @param chain Fast Food Chain to perform the action on.
     */
    private void editStaff(Scanner in, Chain chain) {
        System.out.println("Edit Staff");

        System.out.print("Please enter staff username: ");
        String username = in.next();

        if (!chain.getStaffs().containsKey(username)) {
            System.out.println("This user does not exist");
            return;
        }
        User user = chain.getStaffs().get(username);

        User branchUser =
                chain.getBranches().stream()
                        .flatMap(b -> b.getStaffs().stream())
                        .filter(s -> username.equals(s.getUsername()))
                        .findAny()
                        .orElse(null);
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
                if (genderCode != 'M' && genderCode != 'F') {
                    System.out.println("Gender can only be M or F!");
                    return;
                }
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

    /**
     * Remove a staff, given their username
     *
     * @param in Stdin scanner used by action to read user input.
     * @param chain Fast Food Chain to perform the action on.
     */
    private void removeStaff(Scanner in, Chain chain) {
        System.out.println("Remove Staff");

        System.out.print("Please enter staff username: ");
        String username = in.next();

        if (!chain.getStaffs().containsKey(username)) {
            System.out.println("This user does not exist");
            return;
        }

        chain.getStaffs().remove(username);
        for (Branch branch : chain.getBranches()) {
            for (User user : branch.getStaffs()) {
                if (user.getUsername().equals(username)) {
                    branch.getStaffs().remove(user);
                    return;
                }
            }
        }
    }

    /**
     * List all staffs out, with options to filter the list by branch, role, gender, age
     *
     * @param in Stdin scanner used by action to read user input.
     * @param chain Fast Food Chain to perform the action on.
     */
    private void listStaffAll(Scanner in, Chain chain) {
        System.out.println("List all staff");

        System.out.println("Do you want to filter by branch? (Y|N)");
        char filterBranchPredicate = in.next().charAt(0);
        String branch = null;
        if (filterBranchPredicate == 'Y') {
            while (true) {
                System.out.println("Please input which branch you want to filter by");
                branch = in.next();

                String finalBranch = branch;
                Branch branchExists =
                        chain.getBranches().stream()
                                .filter(b -> finalBranch.equals(b.getName()))
                                .findAny()
                                .orElse(null);
                if (branchExists != null) break;
                System.out.println("BRanch does not exist! Please try again");
            }
        }

        System.out.println("Do you want to filter by role? (Y|N)");
        char filterRolePredicate = in.next().charAt(0);
        char role = 0;
        if (filterRolePredicate == 'Y') {
            while (true) {
                System.out.println("Please input which role you want to filter by (S|M)");
                role = in.next().charAt(0);
                // If valid role, then break
                if (role == 'S' || role == 'M') break;
                System.out.println("Role to filter by is neither S or M. Cannot filter by this");
            }
        }

        System.out.println("Do you want to filter by gender? (Y|N)");
        char filterGenderPredicate = in.next().charAt(0);
        char gender = 0;
        if (filterGenderPredicate == 'Y') {
            while (true) {
                System.out.println("Please input which gender you want to filter by (M|F)");
                gender = in.next().charAt(0);
                // If valid role, then break
                if (gender == 'M' || gender == 'F') break;
                System.out.println("Gender to filter by is neither M or F. Cannot filter by this");
            }
        }

        System.out.println("Do you want to filter by age? (Y|N)");
        char filterAgePredicate = in.next().charAt(0);
        int minAge = 0;
        int maxAge = 0;
        if (filterAgePredicate == 'Y') {
            while (true) {
                System.out.println("Please input the minimum age you want to filter by");
                minAge = in.nextInt();
                System.out.println("Please input the maximum age you want to filter by");
                maxAge = in.nextInt();

                // If there exists a valid range, even if it's an age range of 1
                // this can be searched
                if (minAge <= maxAge) {
                    break;
                }

                System.out.println("Minimum age must be less than or equals to the maximum age.");
            }
        }

        String filterBranch = branch;
        char filterRole = role;
        char filterGender = gender;
        int filterMinAge = minAge;
        int filterMaxAge = maxAge;

        // Filter by branch first
        Stream<Branch> allBranches = chain.getBranches().stream();
        if (filterBranchPredicate == 'Y')
            allBranches = allBranches.filter(b -> b.getName().equals(filterBranch));

        Set<Branch> branches = allBranches.collect(Collectors.toSet());

        // Filter users by role | gender | age
        Stream<User> staffStream = branches.stream().flatMap(b -> b.getStaffs().stream());
        if (filterRolePredicate == 'Y')
            staffStream = staffStream.filter(s -> s.getRole().code() == filterRole);
        if (filterGenderPredicate == 'Y')
            staffStream = staffStream.filter(s -> Gender.toCode(s.getGender()) == filterGender);
        if (filterAgePredicate == 'Y')
            staffStream =
                    staffStream.filter(
                            s -> s.getAge() >= filterMinAge && s.getAge() <= filterMaxAge);
        Set<User> staffs = staffStream.collect(Collectors.toSet());

        Stream<User> managerStream = branches.stream().flatMap(b -> b.getManagers().stream());
        if (filterRolePredicate == 'Y')
            managerStream = managerStream.filter(s -> s.getRole().code() == filterRole);
        if (filterGenderPredicate == 'Y')
            managerStream = managerStream.filter(s -> Gender.toCode(s.getGender()) == filterGender);
        if (filterAgePredicate == 'Y')
            managerStream =
                    managerStream.filter(
                            s -> s.getAge() >= filterMinAge && s.getAge() <= filterMaxAge);
        Set<User> managers = managerStream.collect(Collectors.toSet());

        if (staffs.size() == 0 && managers.size() == 0) {
            System.out.println("No one to list!");
            return;
        }

        for (User staff : staffs) {
            System.out.printf(
                    "%s, %s, %d, %s, %s\n",
                    staff.getBranchBelongTo(),
                    staff.getName(),
                    staff.getAge(),
                    staff.getGender(),
                    staff.getRole().code());
        }
        for (User manager : managers) {
            System.out.printf(
                    "%s, %s, %d, %s, %s\n",
                    manager.getBranchBelongTo(),
                    manager.getName(),
                    manager.getAge(),
                    manager.getGender(),
                    manager.getRole().code());
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
