/*
 * NTU SC2002 project
 * NewOrderStatus
 */

package sg.edu.ntu.sc2002;

/** Marks an {@link Order} in the 'newly submitted' status */
public record NewOrderStatus() implements OrderStatus {
    private static final long serialVersionUID = 1L;

    @Override
    public OrderStatus process() {
        return new PickupOrderStatus();
    }
}
