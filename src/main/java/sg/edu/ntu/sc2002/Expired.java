package sg.edu.ntu.sc2002;

import java.util.Date;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class Expired {

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

    private static void checkExpired(Branch branch) {
        long currentTimeMillis = System.currentTimeMillis();
        if (branch.getReadyToPickupList().size() == 0){
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
