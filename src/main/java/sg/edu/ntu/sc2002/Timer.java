package sg.edu.ntu.sc2002;

import java.util.Date;
import java.util.List;

public class Timer {

    public Date getTime(){
        return new Date();
    }

    public void checkExpired(List<Order> orderList){
        for (Order order : orderList) {
            if (order.getOrderStatus().getStatus() == OrderStatusType.READY_TO_PICKUP) { // Check if the order is not already cancelled
                Date orderTimestamp = order.getOrderStatus().getTimestamp();
                long orderTimestampMillis = orderTimestamp.getTime(); // Convert order timestamp to milliseconds since epoch
                Date currentTimestamp = getTime();
                long currentTimeMillis = currentTimestamp.getTime();
                if (orderTimestampMillis <= currentTimeMillis) {
                    order.cancel();
                    System.out.println("Order " + order.getId() + " cancelled because it has expired.");
                }
            }
        }
    }
}
