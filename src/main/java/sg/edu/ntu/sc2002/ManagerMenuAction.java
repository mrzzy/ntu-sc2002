/*
 * NTU SC2002 project
 * Manager Menu Action
 */
package sg.edu.ntu.sc2002;

import java.util.Scanner;

public class ManagerMenuAction implements ManagerAction {
    private ManagerMenuMethod method;

    public ManagerMenuAction(ManagerMenuMethod method) {
        this.method = method;
    }

    @Override
    public String title() {
        switch (method) {
            case ADD_ITEM:
                return "Add Item";
            case REMOVE_ITEM:
                return "Remove Item";
            case UPDATE_ITEM:
                return "Update Item";
            default: // Last possible case
                return "";
        }
    }


    public void addItem(Scanner in, Branch branch) {
        while (true) {

            String name = null;
            while (name == null){
                in.nextLine();
                System.out.println("Enter item name:");
                name = in.nextLine();
            }
           
            double price = 0.0;
            while (price == 0.0){
                System.out.println("Enter item price:");
                while (true) {
                    if (in.hasNextDouble()) {
                        price = in.nextDouble();
                        in.nextLine(); // Consume newline left by nextDouble()
                        break;
                    } else {
                        System.out.println("Invalid input! Please enter a valid price.");
                        in.nextLine(); // Consume the invalid input to avoid an infinite loop
                    }
                }
            }
            
            System.out.println("Enter item availability (Y/N):");
            boolean available = false;
            String availability = in.next();
            if (availability.equalsIgnoreCase("Y")) {
                available = true;
            } else if (availability.equalsIgnoreCase("N")) {
                available = false;
            } else {
                System.out.println("Invalid input! Please enter either Y or N.");
                continue; // Restart the loop to allow the user to input again
            }

            System.out.println("Enter item category:");
            String category = in.next();

            // Create and add the item to the menu
            Item item = new Item(name, price, available, category);
            branch.getMenu().add(item);

            // Exit the loop after adding the item
            break;
        }
    }

    public void removeItem(Scanner in, Branch branch) {
        System.out.println("Enter item name:");
        String name = in.next();
        Item itemToRemove = null;
        for (Item item : branch.getMenu()) {
            if (item.getName().equals(name)) {
                itemToRemove = item;
            }
        }
        // Check if the item exists in the menu
        if (itemToRemove == null) {
            System.out.println("This item does not exist");
        } else {
            branch.getMenu().remove(itemToRemove);
            System.out.println("Item removed successfully");
        }
    }

    public void updateItem(Scanner in, Branch branch) {
        System.out.println("What item do you want to update?");
        String name = in.next();
        Item itemToUpdate = null;
        for (Item item : branch.getMenu()) {
            if (item.getName().equals(name)) {
                itemToUpdate = item;
            }
        }
        // Check if the item exists in the menu
        if (itemToUpdate == null) {
            System.out.println("This item does not exist");
        } else {
            while (true) {
                System.out.println("What do you want to update?");
                System.out.println("0) Quit");
                System.out.println("1) Price");
                System.out.println("2) Availability");
                int choice = in.nextInt();
                switch (choice) {
                    case 0:
                        return;
                    case 1:
                        System.out.println("Enter new price:");
                        double price = 0.0;
                        boolean validPrice = false;
                        while (!validPrice) {
                            if (in.hasNextDouble()) {
                                price = in.nextDouble();
                                validPrice = true;
                                itemToUpdate.setPrice(price);
                            } else {
                                System.out.println("Invalid input! Please enter a valid price.");
                                in.next(); // Consume the invalid input to avoid an infinite loop
                            }
                        }
                        break;
                    case 2:
                        System.out.println("Enter new availability (Y/N):");
                        boolean available = false;
                        String availability = in.next();
                        if (availability.equalsIgnoreCase("Y")) {
                            available = true;
                        } else if (availability.equalsIgnoreCase("N")) {
                            available = false;
                        } else {
                            System.out.println("Invalid input! Please enter either Y or N.");
                            continue; // Restart the loop to allow the user to input again
                        }
                        itemToUpdate.setAvailable(available);
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            }
        }
    }

    @Override
    public Branch execute(Scanner in, Branch branch) {
        switch (this.method) {
            case ADD_ITEM -> addItem(in, branch);
            case REMOVE_ITEM -> removeItem(in, branch);
            case UPDATE_ITEM -> updateItem(in, branch);
        }
        return branch;
    }
}