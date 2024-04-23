/*
 * NTU SC2002 project
 * Order
 */

package sg.edu.ntu.sc2002;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/** Defines a Fast Food Order of {@link Item}. */
public class Order implements Serializable {
    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** List of items in the order */
    private List<Item> items;

    /** Dining option for the order */
    private DiningOption diningOption;

    /** Timestamp of the latest order status update */
    private Date timestamp;

    /** Order ID */
    private int orderId;

    /**
     * Creates a new order with the given items, dining option and order ID.
     *
     * @param items List of items in the order.
     * @param diningOption Dining option for the order.
     * @param orderId Order ID.
     */
    public Order(List<Item> items, DiningOption diningOption, int orderId) {
        this.items = items;
        this.diningOption = diningOption;
        this.timestamp = new Date();
        this.orderId = orderId;
    }

    /**
     * Get the order ID.
     *
     * @return Order ID.
     */
    public int getId() {
        return this.orderId;
    }

    /**
     * Get the list of items in the order.
     *
     * @return List of items in the order.
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * Get the dining option for the order.
     *
     * @return Dining option for the order.
     */
    public DiningOption getDiningOption() {
        return diningOption;
    }

    /**
     * Get the timestamp of the latest order status update.
     *
     * @return Timestamp of the latest order status update.
     */
    public Date getTimestamp() {
        return this.timestamp;
    }

    /**
     * Set the timestamp of the latest order status update.
     *
     * @param timestamp Timestamp of the latest order status update.
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp; // Update timestamp
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + orderId;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Order other = (Order) obj;
        if (items == null) {
            if (other.items != null) return false;
        } else if (!items.equals(other.items)) return false;
        if (diningOption != other.diningOption) return false;
        if (timestamp == null) {
            if (other.timestamp != null) return false;
        } else if (!timestamp.equals(other.timestamp)) return false;
        if (orderId != other.orderId) return false;
        return true;
    }
}
