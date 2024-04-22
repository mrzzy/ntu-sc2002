package sg.edu.ntu.sc2002;

import java.util.Scanner;
import java.util.Set;

/** Implementation of the {@link CustomerAction} interface. */
public class CustomerCollectAction implements CustomerAction {

    private CustomerCollectMethod method;

    /**
     * Title of the Action displayed in the user interface.
     *
     * @return Title of the action.
     */
    @Override
    public String title() {
        switch (method) {
            case VIEW_ORDER_STATUS:
                return "View Order Status";
            case COLLECT:
                return "Collect Order";
            default: // Last possible case
                return "";
        }
    }

    public CustomerCollectAction(CustomerCollectMethod method) {
        this.method = method;
    }

    /**
     * Displays order status based on the order ID provided by the Customer.
     * 
     * @param in Stdin scanner used by action to read user input.
     * @param branch Fast Food Branch to perform the action on.
     */
    public void viewOrderStatus(Scanner in, Branch branch) {
        System.out.println("Enter your order ID:");
        int orderId = Input.nextInt(in);

        for (Order order : branch.getReadyToPickupList()) {
            if (order.getId() == orderId) {
                System.out.println("Your order is ready to pickup!");
                return;
            }
        }
        for (Order order : branch.getNewOrderList()) {
            if (order.getId() == orderId) {
                System.out.println("Your order is still preparing...");
                return;
            }
        }
        for (Order order : branch.getCompletedOrderList()) {
            if (order.getId() == orderId) {
                System.out.println("Your order has been completed.");
                return;
            }
        }
        for (Order order : branch.getCancelledOrderList()) {
            if (order.getId() == orderId) {
                System.out.println("Your order was cancelled.");
                return;
            }
        }
        System.out.println("Invalid order ID.");
    }

    /**
     * Update the order status from ready to pick up to completed by the Customer.
     * 
     * @param in Stdin scanner used by action to read user input.
     * @param branch Fast Food Branch to perform the action on.
     */
    public void collect(Scanner in, Branch branch) {
        System.out.println("Enter your order ID:");
        int orderId = Input.nextInt(in);
        for (Order order : branch.getReadyToPickupList()) {
            if (order.getId() == orderId) {
                order.collect();
                branch.getCompletedOrderList().add(order);
                branch.getReadyToPickupList().remove(order);
                return;
            }
        }
        for (Order order : branch.getNewOrderList()) {
            if (order.getId() == orderId) {
                System.out.println("Your order is still preparing...");
                return;
            }
        }
        System.out.println(
                "Your order does not exist. Check with our staff if you have not collected your"
                        + " order.");
    }

    /**
     * Execute Action on the given Fast Food Branch.
     *
     * @param in Stdin scanner used by action to read user input.
     * @param branch Fast Food Branch to perform the action on.
     * @param paymentMethods Payment methods offered by the Fast Food Chain.
     * @return State of Fast Food Branch post performing action.
     */
    @Override
    public Branch execute(Scanner in, Branch branch, Set<PaymentMethod> paymentMethods) {
        switch (this.method) {
            case VIEW_ORDER_STATUS -> viewOrderStatus(in, branch);
            case COLLECT -> collect(in, branch);
        }
        return branch;
    }
}
