package sg.edu.ntu.sc2002;

import java.util.Scanner;
import java.util.Set;

public class CustomerCollectAction implements CustomerAction {

    private CustomerCollectMethod method;

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

    public CustomerCollectAction(CustomerCollectMethod method){
        this.method = method;
    }

    public void viewOrderStatus(Scanner in, Branch branch){
        System.out.println("Enter your order ID:");
        int orderId = in.nextInt();
        
        for (Order order : branch.getReadyToPickupList()){
            if (order.getId() == orderId){
                System.out.println("Your order is ready to pickup!");
                return;  
            } 
        }
        for (Order order : branch.getNewOrderList()){
            if (order.getId() == orderId){
                System.out.println("Your order is still preparing...");
                return;  
            } 
        }
        for (Order order : branch.getCompletedOrderList()){
            if (order.getId() == orderId){
                System.out.println("Your order has been completed.");
                return;  
            } 
        }
        for (Order order : branch.getCancelledOrderList()){
            if (order.getId() == orderId){
                System.out.println("Your order was cancelled.");
                return;  
            } 
        }
        System.out.println("Invalid order ID.");
    }

    public void collect(Scanner in, Branch branch){
        System.out.println("Enter your order ID:");
        int orderId = in.nextInt();
        for (Order order : branch.getReadyToPickupList()){
            if (order.getId() == orderId){
                order.collect();
                branch.getCompletedOrderList().add(order);
                branch.getReadyToPickupList().remove(order);
                return;
            }
        }
        for (Order order : branch.getNewOrderList()){
            if (order.getId() == orderId){
                System.out.println("Your order is still preparing...");
                return;  
            } 
        }
        System.out.println("Your order does not exist. Check with our staff if you have not collected your order.");
    }

    @Override
    public Branch execute(Scanner in, Branch branch, Set<PaymentMethod> paymentMethods) {
        switch (this.method) {
            case VIEW_ORDER_STATUS -> viewOrderStatus(in, branch);
            case COLLECT -> collect(in, branch);
        }
        return branch;
    }
}
