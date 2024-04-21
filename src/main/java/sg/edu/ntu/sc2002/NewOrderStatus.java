/*
 * NTU SC2002 project
 * NewOrderStatus
 */

package sg.edu.ntu.sc2002;

/** Marks an {@link Order} in the 'newly submitted' status */
public class NewOrderStatus extends OrderStatus {
    private static final long serialVersionUID = 1L;

    @Override
    OrderStatus process() {
        return new PickupOrderStatus();
    }
}
