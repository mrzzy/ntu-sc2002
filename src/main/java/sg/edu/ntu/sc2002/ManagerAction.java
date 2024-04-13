package sg.edu.ntu.sc2002;

import java.util.Scanner;

public class ManagerAction extends StaffAction {
    private ManagerMethod method;

    private void addMenu(Scanner in, Chain chain, Branch branch, String name, double price, String description, boolean available, String category) {
        Item item = new Item(name, price, description, available, category);
        menu.add(item);
    }

    private void editMenu(Chain chain, Set<Item> menu, Item item, double price){
        menu[item].setPrice(price);
    }

    // overload
    private void editMenu(Chain chain, Set<Item> menu, Item item, boolean available){
        menu[item].setStatus(available);
    }

    private void removeMenu(Chain chain, Set<Item> menu, Item item){
        menu.remove(item);
    }

    private void listStaffBranch(Branch branch){
        List<Staff> staffs = branch.getStaffs();
        for staff:staffs{
            System.out.println(staff);
        }
    }

    /**
     * Title of the Action displayed in the user interface.
     *
     * @return Tile of the action.
     */
    @Override
    public String title() {
        return "Manager Actions: Add Menu, Edit Menu, Remove Menu, List Staff Branch.";
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
        System.out.println(title());
        System.out.println("Enter action: ");

        // Read user input for action
        String action = in.next().trim(); 
        String choice;

        Branch branch = // get branch from chain??
        Set<Item> menu;
        List<Branch> branches = chain.getBranches();
        for (Branch b : branches) {
            if (b.equals(branch)) {
                menu = b.getMenu();
                break;
            }
        }

        switch (action) {
            case "Add Menu":
                System.out.println("Enter item name:");
                String name = in.next();
                System.out.println("Enter item price:");
                float price;
                try {
                    price = in.nextDouble();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid price format. Please enter a valid integer.");
                    return;
                }
                System.out.println("Enter item description:");
                String description = in.next();
                System.out.println("Enter item availability (yes/no):");
                String availableInput = in.next().toLowerCase(); // Convert input to lowercase
                boolean available;
                if (availableInput.equals("yes")) {
                    available = true;
                } else if (availableInput.equals("no")) {
                    available = false;
                } else {
                    System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                }
                System.out.println("Enter item category:");
                String category = in.next();
                addMenu(in, chain, menu, name, price, description, available, category);
                break;
            case "Edit Menu":
                System.out.println("1: Edit price");
                System.out.println("2: Edit availability");
                int choice = in.nextInt();
                // display menu, prompt user to choose item
                System.out.println("Choose item");
                String name = in.nextInt();
                Item item = chain.getBranches[branch].getMenu(name);
                switch (choice){
                    case 1:
                        // prompt user to choose price
                        System.out.println("Enter new price");
                        double price = in.nextDouble();
                        editMenu(chain, menu, item, price);
                    case 2:
                        // prompt user to choose availability
                        System.out.println("Enter item availability (yes/no):");
                        String availableInput = in.next().toLowerCase(); // Convert input to lowercase
                        boolean available;
                        if (availableInput.equals("yes")) {
                            available = true;
                        } else if (availableInput.equals("no")) {
                            available = false;
                        } else {
                            System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                        }
                        editMenu(chain, menu, item, availability);
                } 
                break;
            case "Remove Menu":
                System.out.println("Enter item name:");
                String name = in.next();
                Item item = menu.getItem();
                removeMenu(chain, menu, item);
                break;
            case "List Staff Branch":
                listStaffBranch(branch);
                break;
            case "Set Status":
                super().setStatus();
            case "View Order":
                super().viewOrder();
            default:
                System.out.println("Invalid action. Please try again.");
        }

        return chain;
    }
}
