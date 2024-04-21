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
    private OrderStatus status;

    public Order(List<Item> items, DiningOption diningOption, OrderStatus status) {
        this.items = items;
        this.diningOption = diningOption;
        this.status = status;
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
        return status;
    }
}
