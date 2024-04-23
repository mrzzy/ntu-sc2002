package sg.edu.ntu.sc2002;

import java.util.Date;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

/** Handles all expired orders in a {@link Branch}. */
public class Expired {

    /**
     * Schedules a task to check for expired orders in a {@link Branch}.
     *
     * @param branch Branch to check for expired orders.
     */
    public static void cancelChecker(Branch branch) {
        Timer timer = new Timer();
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        // Call the checkExpired method
                        checkExpired(branch);
                    }
                },
                0,
                20000); // Schedule the task to run every 20 seconds
        return;
    }

    /**
     * Checks for expired orders in a {@link Branch} and moves them to the cancelled order list.
     *
     * @param branch Branch to check for expired orders.
     */
    private static void checkExpired(Branch branch) {
        long currentTimeMillis = System.currentTimeMillis();
        if (branch.getReadyToPickupList().size() == 0) {
            return;
        }
        Iterator<Order> iterator = branch.getReadyToPickupList().iterator();
        while (iterator.hasNext()) {
            Order order = iterator.next();
            long orderTimestampMillis = order.getTimestamp().getTime();

            if (currentTimeMillis - orderTimestampMillis
                    >= 120000) { // 300000 milliseconds = 5 minutes
                order.setTimestamp(new Date());
                branch.getCancelledOrderList().add(order);
                iterator.remove();
            }
        }
        return;
    }
}
