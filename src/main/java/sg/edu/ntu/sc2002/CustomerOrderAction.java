package sg.edu.ntu.sc2002;

import java.util.Scanner;
import java.util.Set;

/** Implementation of the {@link CustomerAction} interface. */
public class CustomerOrderAction implements CustomerAction {
    /**
     * List of methods related to customer order actions
     */
    private CustomerOrderMethod method;
    /**
     * Cart of the Customer
     */
    public static Cart myCart;
    /**
     * Total price of the items in the Customer's Cart
     */
    public static double totalPrice;

    /**
     * Title of the Action displayed in the user interface.
     *
     * @return Title of the action.
     */
    @Override
    public String title() {
        switch (method) {
            case ADD_TO_CART:
                return "Add Item to Cart";
            case REMOVE_FROM_CART:
                return "Remove Item from Cart";
            case CUSTOMISE_ITEM_IN_CART:
                return "Customise Item in Cart";
            case VIEW_CART:
                return "View Cart";
            case PAY:
                return "Make Payment";
            default: // Last possible case
                return "";
        }
    }

    /**
     * Constructor to create a Customer Order Action.
     * 
     * @param method Method of the Customer Order Action.
     */
    public CustomerOrderAction(CustomerOrderMethod method) {
        this.method = method;
    }

    
    private void computeTotalCost() {
        double newTotalPrice = 0;
        for (Item item : myCart.getCart()) {
            newTotalPrice += item.getPrice();
        }
        totalPrice = newTotalPrice;
    }

    /** Display list of items and total price in Customer's Cart. */
    private void viewCart() {
        System.out.println("-------------------------");
        myCart.viewCart();
        computeTotalCost();
    }

    /**
     * Adds the Customer's chosen item to their Cart.
     * 
     * @param in   Stdin scanner used by action to read user input.
     * @param menu Item menu from the chosen Fast Food Branch.
     * @return Outcome of adding the Item to Cart.
     */
    private boolean addToCart(Scanner in, Set<Item> menu) {
        System.out.println("-------------------------");
        int i = 1;
        for (Item item : menu) {
            if (item.getAvailable()) {
                System.out.println(
                        String.format(
                                "%d) Name: %s, Price: %.2f, Description: %s, Category: %s",
                                i++,
                                item.getName(),
                                item.getPrice(),
                                item.getDescription(),
                                item.getCategory()));
            }
        }
        System.out.println("Type item number to add:");
        int itemNumber = Input.nextInt(in);

        System.out.println("Any customisation?");
        String itemCustomisation = in.nextLine();

        i = 1;
        Item itemToAdd = null;
        for (Item item : menu) {
            if (itemNumber == i) {
                itemToAdd = item.copy();
                itemToAdd.setCustomisation(itemCustomisation);
                break;
            }
            i++;
        }

        if (itemToAdd != null) {
            myCart.addCart(itemToAdd);
            computeTotalCost();
            System.out.println("Item added successfully.");
            return true;
        } else {
            System.out.println("Invalid choice.");
            return false;
        }
    }

    /**
     * Removes the Customer's chosen item from their Cart.
     * 
     * @param in Stdin scanner used by action to read user input.
     * @return Outcome of removing the Item from the Cart.
     */
    private boolean removeFromCart(Scanner in) {
        System.out.println("-------------------------");
        int i = 1;
        for (Item item : myCart.getCart()) {
            System.out.println(
                    String.format(
                            "%d: Name: %s, Price: %.2f, Customisation: %s, Category: %s",
                            i++,
                            item.getName(),
                            item.getPrice(),
                            item.getCustomisation(),
                            item.getCategory()));
        }
        System.out.println("Type item number to remove:");
        int itemIndex = Input.nextInt(in);

        if (itemIndex < 0 || itemIndex >= i) {
            System.out.println("Invalid index. Try again.");
            return false;
        }
        
        myCart.removeCart(itemIndex - 1);
        computeTotalCost();
        return true;
    }

    /**
     * Customises the Customer's chosen item in their Cart.
     * 
     * @param in Stdin scanner used by action to read user input.
     * @return Outcome of customising the Item in the Cart.
     */
    private boolean customiseItemInCart(Scanner in) {
        System.out.println("-------------------------");
        if (myCart.getCart().size() == 0) {
            System.out.println("Cart is Empty!");
            return false;
        }
        System.out.println("Type item number to customise:");
        int itemIndex = Input.nextInt(in);
        if (itemIndex < 0) {
            System.out.println("Invalid index. Try again.");
            return false;
        }
        System.out.println("Edit customisation:");
        String newCustomisation = in.nextLine();
        myCart.getCart().get(itemIndex - 1).setCustomisation(newCustomisation);
        return true;
    }

    /**
     * Prompts user to choose payment type and calls the payment method in that
     * type.
     * 
     * @param in             Stdin scanner used by action to read user input.
     * @param branch         Fast Food Branch to perform the action on.
     * @param paymentMethods Set of available payment methods supported by the Fast
     *                       Food Chain.
     * @return Outcome of the transaction.
     */
    private boolean pay(Scanner in, Branch branch, Set<PaymentMethod> paymentMethods) {
        System.out.println("-------------------------");
        if (myCart.getCart().size() == 0) {
            System.out.println("Cart is Empty!");
            return false;
        }

        System.out.println(String.format("Total Price: %.2f", totalPrice));
        System.out.println("Select Payment Method:");
        int i = 1;
        for (PaymentMethod paymentMethod : paymentMethods) {
            System.out.println(String.format("%d) %s", i++, paymentMethod.getName()));
        }
        int choice = Input.nextInt(in);

        i = 1;
        PaymentMethod paymentMethodSelected = null;
        for (PaymentMethod paymentMethod : paymentMethods) {
            if (choice == i) {
                paymentMethodSelected = paymentMethod;
            }
            i++;
        }
        if (paymentMethodSelected == null) {
            System.out.println("Invalid choice.");
            return false;
        }
        
        int amountCents = (int) totalPrice * 100;
        boolean paymentSuccess = paymentMethodSelected.pay(amountCents, in);

        if (paymentSuccess) {
            System.out.println("Payment Successful.");
            Order myOrder = createOrder(in, branch);

            myCart.getCart().clear();
            computeTotalCost();

            branch.getNewOrderList().add(myOrder);
            return true;
        } else {
            System.out.println("Payment Unsuccessful.");
            return false;
        }
    }

    /**
     * Creates the order.
     * 
     * @param in     Stdin scanner used by action to read user input.
     * @param branch Fast Food Branch to perform the action on.
     * @return Order
     */
    public Order createOrder(Scanner in, Branch branch) {
        DiningOption myOption = null;
        while (myOption == null) {
            System.out.println("-------------------------");
            System.out.println("Select Dine-in Option: \n1) Dine-in \n2) Takeaway");
            int choice = Input.nextInt(in);
            if (choice == 1) {
                myOption = DiningOption.DINE_IN;
                System.out.println("You have chosen to dine-in.");
            } else if (choice == 2) {
                myOption = DiningOption.DINE_OUT;
                System.out.println("You have chosen to takeaway.");
            } else {
                System.out.println("Invalid choice.");
            }
        }
        branch.setOrderId();
        Order myOrder = new Order(myCart.getCart(), myOption, branch.getOrderId());
        printReceipt(branch.getOrderId());
        return myOrder;
    }

    /**
     * Displays the order information
     * 
     * @param orderID ID number of the order created.
     */
    public void printReceipt(int orderID) {
        System.out.println(String.format("Your order ID is: %d", orderID));
        System.out.println("-------------------------");
        System.out.println("RECEIPT");
        myCart.viewCart();
        double newTotalPrice = 0;
        for (Item item : myCart.getCart()) {
            newTotalPrice += item.getPrice();
        }
        System.out.println(String.format("Total Price: %.2f", newTotalPrice));
    }

    /**
     * Execute Action on the given Fast Food Branch.
     *
     * @param in             Stdin scanner used by action to read user input.
     * @param branch         Fast Food Branch to perform the action on.
     * @param paymentMethods Payment methods offered by the Fast Food Chain.
     * @return State of Fast Food Branch post performing action.
     */
    @Override
    public Branch execute(Scanner in, Branch branch, Set<PaymentMethod> paymentMethods) {
        switch (this.method) {
            case ADD_TO_CART -> addToCart(in, branch.getMenu());
            case REMOVE_FROM_CART -> removeFromCart(in);
            case CUSTOMISE_ITEM_IN_CART -> customiseItemInCart(in);
            case VIEW_CART -> viewCart();
            case PAY -> pay(in, branch, paymentMethods);
        }
        return branch;
    }
}
