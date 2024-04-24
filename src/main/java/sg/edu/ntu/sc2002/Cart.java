package sg.edu.ntu.sc2002;

import java.util.ArrayList;

/** Defines a class to store the items ordered by the customer. */
public class Cart {
    /** List of items in the cart. */
    private ArrayList<Item> cartItems;

    /** Constructor to create a Cart object. */
    public Cart() {
        this.cartItems = new ArrayList<Item>();
    }

    // getter
    /**
     * Get the list of items in the cart.
     *
     * @return list of items in the cart.
     */
    public ArrayList<Item> getCart() {
        return this.cartItems;
    }

    /** View the items in the cart. */
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
    /**
     * Add an item to the cart.
     *
     * @param item Item to be added to the cart.
     */
    public void addCart(Item item) {
        this.cartItems.add(item);
    }

    /**
     * Remove an item from the cart.
     *
     * @param itemIndex Index of the item to be removed from the cart.
     */
    public void removeCart(int itemIndex) {
        this.cartItems.remove(itemIndex);
    }

    /** copy the cart. */
    public ArrayList<Item> copyCart() {
        ArrayList<Item> copy = new ArrayList<Item>();
        for (Item item : this.cartItems) {
            copy.add(item.copy());
        }
        return copy;
    }
}
