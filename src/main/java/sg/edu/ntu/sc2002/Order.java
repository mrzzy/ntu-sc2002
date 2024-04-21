/*
 * NTU SC2002 project
 * Order
 */

package sg.edu.ntu.sc2002;

import java.io.Serializable;
import java.util.List;
import java.util.Date;

/** Defines a Fast Food Order of {@link Item}. */
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Item> items;
    private DiningOption diningOption;
    private OrderStatus orderStatus;
    private int orderId;

    public Order(List<Item> items, DiningOption diningOption, int orderId){
        this.items = items;
        this.diningOption = diningOption;
        this.orderStatus = new OrderStatus();
        this.orderId = orderId;
    }

    /**
     * Transition order status by processing order.
     *
     * @return Next order status post transition (if any).
     */

     /** Process the items in this Order. Performed by Staff. */
    public void process(){
        if (this.orderStatus.getStatus() == OrderStatusType.NEW){
            this.orderStatus.setStatus(OrderStatusType.READY_TO_PICKUP);
        }    
        this.orderStatus.setTimestamp(new Date()); // Update timestamp
    }

    /** Collect this Order. Performed by Branch. */
    public void cancel(){
        if (this.orderStatus.getStatus() == OrderStatusType.READY_TO_PICKUP){
            this.orderStatus.setStatus(OrderStatusType.CANCELLED);
        }    
        this.orderStatus.setTimestamp(new Date()); // Update timestamp
    }

    /** Collect this Order. Performed by Customer. */
    public void collect(){
        if (this.orderStatus.getStatus() == OrderStatusType.READY_TO_PICKUP){
            this.orderStatus.setStatus(OrderStatusType.COMPLETED);
        }    
        this.orderStatus.setTimestamp(new Date()); // Update timestamp
    }

    public int getId(){
        return this.orderId;
    }


    public List<Item> getItems() {
        return items;
    }

    public DiningOption getDiningOption() {
        return diningOption;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
}
