/*
 * NTU SC2002 project
 * PickupOrderStatus
 * Unit Test
 */

package sg.edu.ntu.sc2002;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.time.Instant;
import java.util.Date;
import org.junit.jupiter.api.Test;

public class PickupOrderStatusTest {
    @Test
    public void testPickupStatusExpiry() {
        OrderStatus status = new PickupOrderStatus();
        status =
                status.step(
                        Date.from(
                                Instant.now().plusSeconds(PickupOrderStatus.EXPIRES_SECONDS + 1)));
        assertInstanceOf(CanceledOrderStatus.class, status);
    }
}
