package sg.edu.ntu.sc2002;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Date;

/** Implementation of the {@link StaffAction} interface. */
public class StaffOrderAction implements StaffAction {
    private StaffOrderMethod method;

    public StaffOrderAction(StaffOrderMethod method) {
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
            case VIEW_ORDERS:
                return "View All Orders";
            case VIEW_ORDER_DETAILS:
                return "View Details of an Order";
            case PROCESS_ORDER:
                return "Process an Order";
            default: // Last possible case
                return "";
        }
    }

    /**
     * View all orders of each type under a given Fast Food Branch.
     * 
     * @param in     Stdin scanner used by action to read user input.
     * @param branch Fast Food Branch to perform the action on.
     */
    private void viewOrders(Scanner in, Branch branch) {

        System.out.println("New Order");
        System.out.println("-------------------------");
        for (Order order : branch.getNewOrderList()) {
            System.out.println(
                    String.format(
                            "ID: %d, %s", order.getId(), order.getTimestamp()));
        }
        System.out.println("-------------------------");

        System.out.println("Ready to Pickup");
        System.out.println("-------------------------");
        for (Order order : branch.getReadyToPickupList()) {
            System.out.println(
                    String.format(
                            "ID: %d, %s", order.getId(), order.getTimestamp()));
        }
        System.out.println("-------------------------");

        System.out.println("Completed Orders");
        System.out.println("-------------------------");
        for (Order order : branch.getCompletedOrderList()) {
            System.out.println(
                    String.format(
                            "ID: %d, %s", order.getId(), order.getTimestamp()));
        }
        System.out.println("-------------------------");

        System.out.println("Cancelled Orders");
        System.out.println("-------------------------");
        for (Order order : branch.getCancelledOrderList()) {
            System.out.println(
                    String.format(
                            "ID: %d, %s", order.getId(), order.getTimestamp()));
        }
    }

    /**
     * View order details for new orders under a given Fast Food Branch.
     * 
     * @param in     Stdin scanner used by action to read user input.
     * @param branch Fast Food Branch to perform the action on.
     */
    private void viewOrderDetails(Scanner in, Branch branch) {
        System.out.println("Enter order ID:");
        int orderId = Input.nextInt(in);
        System.out.println("-------------------------");
        for (Order order : branch.getNewOrderList()) {
            if (order.getId() == orderId) {
                System.out.println("Status: Still preparing");
                for (Item item : order.getItems()) {
                    System.out.println(
                            String.format(
                                    "Name: %s, Customisation: %s",
                                    item.getName(), item.getCustomisation()));
                }
                return;
            }
        }

        for (Order order : branch.getReadyToPickupList()) {
            if (order.getId() == orderId) {
                System.out.println("Status: Ready to Pickup");
                for (Item item : order.getItems()) {
                    System.out.println(
                            String.format(
                                    "Name: %s, Customisation: %s",
                                    item.getName(), item.getCustomisation()));
                }
                return;
            }
        }

        for (Order order : branch.getCompletedOrderList()) {
            if (order.getId() == orderId) {
                System.out.println("Status: Completed");
                for (Item item : order.getItems()) {
                    System.out.println(
                            String.format(
                                    "Name: %s, Customisation: %s",
                                    item.getName(), item.getCustomisation()));
                }
                return;
            }
        }

        for (Order order : branch.getCancelledOrderList()) {
            if (order.getId() == orderId) {
                System.out.println("Status: Cancelled");
                for (Item item : order.getItems()) {
                    System.out.println(
                            String.format(
                                    "Name: %s, Customisation: %s",
                                    item.getName(), item.getCustomisation()));
                }
                return;
            }
        }
        System.out.println("Order not found.");
    }

    /**
     * Update the status of a new order to ready to pick up.
     * 
     * @param in     Stdin scanner used by action to read user input.
     * @param branch Fast Food Branch to perform the action on.
     */
    private Branch processOrder(Scanner in, Branch branch) {
        System.out.println("-------------------------");
        try {
            System.out.println("Enter order ID:");
            int orderId = Input.nextInt(in);

            // Check if the entered order ID is negative or zero
            if (orderId <= 0) {
                System.out.println("Invalid order ID.");
                return branch;
            }

            for (Order order : branch.getNewOrderList()) {
                if (order.getId() == orderId) {
                    order.setTimestamp(new Date());
                    branch.getReadyToPickupList().add(order);
                    branch.getNewOrderList().remove(order);
                    System.out.println("Order processed successfully. Ready to be picked up by customer.");
                    return branch;
                }
            }
            System.out.println("Order ID not found.");
            return branch;
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid order ID.");
            in.nextLine(); // Clear the input buffer
            return branch;
        }
    }

    /**
     * Execute Action on the given Fast Food Branch.
     *
     * @param in     Stdin scanner used by action to read user input.
     * @param branch Fast Food Branch to perform the action on.
     * @return State of Fast Food Branch post performing action.
     */
    @Override
    public Branch execute(Scanner in, Branch branch) {
        switch (this.method) {
            case VIEW_ORDERS -> viewOrders(in, branch);
            case VIEW_ORDER_DETAILS -> viewOrderDetails(in, branch);
            case PROCESS_ORDER -> processOrder(in, branch);
        }
        return branch;
    }
}
