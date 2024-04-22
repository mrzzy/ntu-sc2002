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
        System.out.println("-------------------------");
        while (true) {

            String name = null;
            while (name == null) {
                System.out.println("Enter item name:");
                name = in.nextLine();
            }

            double price = 0.0;
            while (price <= 0.0) {
                System.out.println("Enter item price:");
                price = Input.nextDouble(in);
            }

            String description = null;
            while (description == null) {
                in.nextLine();
                System.out.println("Enter item description:");
                description = in.nextLine();
            }

            boolean available = false;
            while (true) {
                System.out.println("Enter item availability (Y/N):");
                String availability = in.next();
                if (availability.equalsIgnoreCase("Y")) {
                    available = true;
                    break;
                } else if (availability.equalsIgnoreCase("N")) {
                    available = false;
                    break;
                } else {
                    System.out.println("Invalid input! Please enter either Y or N.");
                    continue; // Restart the loop to allow the user to input again
                }
            }

            System.out.println("Enter item category:");
            String category = in.next();

            // Create and add the item to the menu
            Item item = new Item(name, price, description, available, category);
            branch.getMenu().add(item);
            System.out.println("Item added successfully.");

            // Exit the loop after adding the item
            break;
        }
    }

    public void removeItem(Scanner in, Branch branch) {
        System.out.println("-------------------------");
        System.out.println("Enter item number:");
        int choice = Input.nextInt(in);
        Item itemToRemove = null;
        int i = 1;
        for (Item item : branch.getMenu()) {
            if (choice == i) {
                itemToRemove = item;
                break;
            }
            i++;
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
        System.out.println("-------------------------");
        System.out.println("What item number do you want to update?");
        int itemChoice = Input.nextInt(in);
        Item itemToUpdate = null;
        int i = 1;
        for (Item item : branch.getMenu()) {
            if (itemChoice == i) {
                itemToUpdate = item;
                break;
            }
            i++;
        }
        // Check if the item exists in the menu
        if (itemToUpdate == null) {
            System.out.println("Invalid choice.");
        } else {
            while (true) {
                System.out.println("-------------------------");
                System.out.println("What do you want to update?");
                System.out.println("0) Quit");
                System.out.println("1) Price");
                System.out.println("2) Description");
                int choice = Input.nextInt(in);
                switch (choice) {
                    case 0:
                        return;
                    case 1:
                        System.out.println("Enter new price:");
                        double price = 0.0;
                        while (price <= 0.0) {
                            price = Input.nextDouble(in);
                        }
                        itemToUpdate.setPrice(price);
                        System.out.println("Price updated successfully.");
                        break;
                    case 2:
                        System.out.println("Enter new description:");
                        String description = null;
                        while (description == null) {
                            in.nextLine();
                            description = in.nextLine();
                            itemToUpdate.setDescription(description);
                            System.out.println("Description updated successfully.");
                        }
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
