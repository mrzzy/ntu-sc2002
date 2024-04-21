/*
 * NTU SC2002 project
 * Order
 * Unit Test
 */

package sg.edu.ntu.sc2002;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import org.junit.jupiter.api.Test;

public class OrderTest {
    @Test
    public void testGetStatusExpires() {
        // simulate an order about to expire
        Date expireOn = Date.from(Instant.now());
        Order order =
                new Order(
                        "test",
                        new ArrayList<>(),
                        DiningOption.DINE_IN,
                        new PickupOrderStatus(expireOn));
        // check that order status transitioned after expiry date
        assertInstanceOf(
                CanceledOrderStatus.class,
                order.getStatus(Date.from(expireOn.toInstant().plusSeconds(1))));
    }
}
