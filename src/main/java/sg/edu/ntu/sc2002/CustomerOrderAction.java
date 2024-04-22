package sg.edu.ntu.sc2002;

import java.util.Scanner;
import java.util.Set;

public class CustomerOrderAction implements CustomerAction {
    private CustomerOrderMethod method;
    public static Cart myCart;
    public static double totalPrice;

    @Override
    public String title() {
        switch (method) {
            case ADD_TO_CART:
                return "Add Item to Cart";
            case REMOVE_FROM_CART:
                return "Remove Item from Cart";
            case VIEW_CART:
                return "View Cart";
            case PAY:
                return "Make Payment";
            default: // Last possible case
                return "";
        }
    }

    public CustomerOrderAction(CustomerOrderMethod method) {
        this.method = method;
    }

    public void viewCart() {
        System.out.println("-------------------------");
        myCart.viewCart();
        double newTotalPrice = 0;
        for (Item item : myCart.getCart()) {
            newTotalPrice += item.getPrice();
        }
        System.out.println(String.format("Total Price: %.2f", newTotalPrice));
        totalPrice = newTotalPrice;
    }

    public boolean addToCart(Scanner in, Set<Item> menu) {
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
        in.nextLine();

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
            System.out.println("Item added successfully.");
            return true;
        } else {
            System.out.println("Invalid choice.");
            return false;
        }
    }

    public boolean removeFromCart(Scanner in) {
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
        return true;
    }

    public boolean pay(Scanner in, Branch branch, Set<PaymentMethod> paymentMethods) {
        System.out.println("-------------------------");
        if (myCart.getCart().size() == 0) {
            System.out.println("Cart is Empty!");
            return false;
        }
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
            branch.getNewOrderList().add(myOrder);
            return true;
        } else {
            System.out.println("Payment Unsuccessful.");
            return false;
        }
    }

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
        System.out.println(String.format("Your order ID is: %d", branch.getOrderId()));
        return myOrder;
    }

    @Override
    public Branch execute(Scanner in, Branch branch, Set<PaymentMethod> paymentMethods) {
        switch (this.method) {
            case ADD_TO_CART -> addToCart(in, branch.getMenu());
            case REMOVE_FROM_CART -> removeFromCart(in);
            case VIEW_CART -> viewCart();
            case PAY -> pay(in, branch, paymentMethods);
        }
        return branch;
    }
}
