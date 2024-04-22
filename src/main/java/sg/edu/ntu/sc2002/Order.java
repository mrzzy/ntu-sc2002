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
    private static final long serialVersionUID = 1L;
    private List<Item> items;
    private DiningOption diningOption;
    private Date timestamp;
    private int orderId;

    public Order(List<Item> items, DiningOption diningOption, int orderId) {
        this.items = items;
        this.diningOption = diningOption;
        this.timestamp = new Date();
        this.orderId = orderId;
    }

    /**
     * Transition order status by processing order.
     *
     * @return Next order status post transition (if any).
     */

    /** Process the items in this Order. Performed by user. */
    public int getId() {
        return this.orderId;
    }

    public List<Item> getItems() {
        return items;
    }

    public DiningOption getDiningOption() {
        return diningOption;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

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
