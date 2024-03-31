/*
* NTU SC2002 project
* Order Status
*/

package sg.edu.ntu.sc2002;

import java.util.Date;

/**
 * Defines a status an {@link Order} can have.
 */
public interface OrderStatus {
    /**
     * Transition order status based on current time.
     * 
     * @param timestamp Current time timestamp.
     * @return Next order status post transition (if any).
     */
    OrderStatus step(Date timestamp);

    /**
     * Transition order status by processing order.
     * 
     * @return Next order status post transition (if any).
     */
    OrderStatus process();

    /**
     * Transition order status by processing collecting order.
     * 
     * @return Next order status post transition (if any).
     */
    OrderStatus collect();
}
