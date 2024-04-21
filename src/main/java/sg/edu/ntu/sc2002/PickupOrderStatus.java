/*
 * NTU SC2002 project
 * PickupOrderStatus
 */

package sg.edu.ntu.sc2002;

import java.time.Instant;
import java.util.Date;

/** Marks an {@link Order} in the 'ready to pickup' status. */
public record PickupOrderStatus(Date expiresOn) implements OrderStatus {
    /** Duration of time in seconds before order expires upon reaching Pickup status. */
    public static final long EXPIRES_SECONDS = 3600;

    public PickupOrderStatus(Date expiresOn) {
        this.expiresOn = expiresOn;
    }

    public PickupOrderStatus() {
        this(Date.from(Instant.now().plusSeconds(EXPIRES_SECONDS)));
    }

    @Override
    public OrderStatus step(Date timestamp) {
        return (timestamp.after(expiresOn)) ? new CanceledOrderStatus() : this;
    }

    @Override
    public OrderStatus collect() {
        return new CompletedOrderStatus();
    }
}
