package sg.edu.ntu.sc2002;

import java.util.ArrayList;

/** Defines a class to store the items ordered by the customer. */
public class Cart {
    private ArrayList<Item> cartItems;

    /**
     * Initialises an empty list of items when a CustomerRole is created.
     *
     * @return Tile of the action.
     */
    public Cart() {
        this.cartItems = new ArrayList<Item>();
    }

    // getter
    public ArrayList<Item> getCart() {
        return this.cartItems;
    }

    public void viewCart() {
        if (this.cartItems.size() == 0) {
            System.out.println("No items in cart.");
            return;
        }
        int i = 1;
        for (Item item : this.cartItems) {
            String name = item.getName();
            Double price = item.getPrice();
            String customisation = item.getCustomisation();
            boolean available = item.getAvailable();
            String availability = available ? "Yes" : "No";
            String category = item.getCategory();
            System.out.println(
                    String.format(
                            "%d) Name: %s, Price: %.2f, Customisation: %s, Available: %s, Category:"
                                    + " %s",
                            i++, name, price, customisation, availability, category));
        }
    }

    // setter
    public void addCart(Item item) {
        this.cartItems.add(item);
    }

    public void removeCart(int itemIndex) {
        this.cartItems.remove(itemIndex);
    }
}
