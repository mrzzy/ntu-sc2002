/*
 * NTU SC2002 project
 * Order
 */

package sg.edu.ntu.sc2002;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/** Defines a Fast Food Order of {@link Item}. */
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private List<Item> items;
    private DiningOption diningOption;
    private OrderStatus status;

    public Order(String id, List<Item> items, DiningOption diningOption, OrderStatus status) {
        this.id = id;
        this.items = items;
        this.diningOption = diningOption;
        this.status = status;
    }

    public Order(List<Item> items, DiningOption diningOption) {
        this(UUID.randomUUID().toString(), items, diningOption, new NewOrderStatus());
    }

    /** Process the items in this Order. Performed by Staff. */
    public void process() {
        status = status.process();
    }

    /** Collect this Order. Performed by Customer. */
    public void collect() {
        status = status.collect();
    }

    public List<Item> getItems() {
        return items;
    }

    public DiningOption getDiningOption() {
        return diningOption;
    }

    public OrderStatus getStatus() {
        return this.getStatus(Date.from(Instant.now()));
    }

    protected OrderStatus getStatus(Date timestamp) {
        status = status.step(timestamp);
        return status;
    }

    public String getId() {
        return id;
    }

    @Override
    public int hashCode() {
        // orders are unique by order id
        return getId().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Order other = (Order) obj;
        if (id == null) {
            if (other.id != null) return false;
        } else if (!id.equals(other.id)) return false;
        if (items == null) {
            if (other.items != null) return false;
        } else if (!items.equals(other.items)) return false;
        if (diningOption != other.diningOption) return false;
        if (status == null) {
            if (other.status != null) return false;
        } else if (!status.equals(other.status)) return false;
        return true;
    }
}
