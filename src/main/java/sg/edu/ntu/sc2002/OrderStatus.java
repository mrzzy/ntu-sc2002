/*
 * NTU SC2002 project
 * Order Status
 */

package sg.edu.ntu.sc2002;

import java.io.Serializable;
import java.util.Date;

/** Defines a status an {@link Order} can have. */
public interface OrderStatus extends Serializable {
    /**
     * Transition order status based on current time.
     *
     * @param timestamp Current time timestamp.
     * @return Next order status post transition (if any).
     */
    default OrderStatus step(Date timestamp) {
        // default implementation: do nothing.
        return this;
    }

    /**
     * Transition order status by processing order.
     *
     * @throws UnsupportedOperationException If processing order in an unsupported status.
     * @return Next order status post transition (if any).
     */
    default OrderStatus process() {
        throw new UnsupportedOperationException(
                "Processing Order in status is unsupported: " + this);
    }

    /**
     * Transition order status by collecting order.
     *
     * @throws UnsupportedOperationException If collecting order in an unsupported status.
     * @return Next order status post transition (if any).
     */
    default OrderStatus collect() {
        throw new UnsupportedOperationException(
                "Collecting Order in status is unsupported: " + this);
    }
}
