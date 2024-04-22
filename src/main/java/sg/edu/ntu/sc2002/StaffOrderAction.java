package sg.edu.ntu.sc2002;

import java.util.InputMismatchException;
import java.util.Scanner;

public class StaffOrderAction implements StaffAction {
    private StaffOrderMethod method;

    public StaffOrderAction(StaffOrderMethod method) {
        this.method = method;
    }

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

    public void viewOrders(Scanner in, Branch branch) {

        System.out.println("New Order");
        System.out.println("-------------------------");
        for (Order order : branch.getNewOrderList()) {
            System.out.println(
                    String.format(
                            "ID: %d, %s", order.getId(), order.getOrderStatus().getTimestamp()));
        }
        System.out.println("-------------------------");

        System.out.println("Ready to Pickup");
        System.out.println("-------------------------");
        for (Order order : branch.getReadyToPickupList()) {
            System.out.println(
                    String.format(
                            "ID: %d, %s", order.getId(), order.getOrderStatus().getTimestamp()));
        }
        System.out.println("-------------------------");

        System.out.println("Completed Orders");
        System.out.println("-------------------------");
        for (Order order : branch.getCompletedOrderList()) {
            System.out.println(
                    String.format(
                            "ID: %d, %s", order.getId(), order.getOrderStatus().getTimestamp()));
        }
        System.out.println("-------------------------");

        System.out.println("Cancelled Orders");
        System.out.println("-------------------------");
        for (Order order : branch.getCancelledOrderList()) {
            System.out.println(
                    String.format(
                            "ID: %d, %s", order.getId(), order.getOrderStatus().getTimestamp()));
        }
        System.out.println("-------------------------");

        System.out.println("Invalid order ID.");
    }

    public void viewOrderDetails(Scanner in, Branch branch) {
        System.out.println("Enter order ID:");
        int orderId = Input.nextInt(in);
        for (Order order : branch.getNewOrderList()) {
            if (order.getId() == orderId) {
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

    public Branch processOrder(Scanner in, Branch branch) {
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
                    order.process();
                    branch.getReadyToPickupList().add(order);
                    branch.getNewOrderList().remove(order);
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
