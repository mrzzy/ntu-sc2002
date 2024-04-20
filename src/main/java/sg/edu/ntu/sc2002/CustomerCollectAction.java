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
        
        for (Order order : branch.getOrderList()){
            if (order.getId() == orderId){
                OrderStatusType currentStatus = order.getOrderStatus().getStatus();
                switch (currentStatus) {
                    case OrderStatusType.NEW:
                        System.out.println("Your order is still processing...");
                        return;
                    case OrderStatusType.READY_TO_PICKUP:
                        System.out.println("Your order is ready to pickup!");
                        return;  
                    default:
                        break;
                }
            }
        }
        System.out.println("Your order does not exist. Check with our staff if you have not collected your order.");
    }

    public void collect(Scanner in, Branch branch){
        System.out.println("Enter your order ID:");
        int orderId = in.nextInt();
        for (Order order : branch.getOrderList()){
            if (order.getId() == orderId){
                if (order.getOrderStatus().getStatus() == OrderStatusType.READY_TO_PICKUP){
                    order.collect();
                } else{
                    System.out.println("Your food is not ready to be collected.");
                }
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
