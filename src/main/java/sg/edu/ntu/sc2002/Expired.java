package sg.edu.ntu.sc2002;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class Expired {

    public Date getTime() {
        return new Date();
    }

    public void cancelChecker(Branch branch) {
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
                60000); // Schedule the task to run every 60 seconds (1 minute)
        return;
    }

    public void checkExpired(Branch branch) {
        long currentTimeMillis = System.currentTimeMillis();

        for (Order order : branch.getReadyToPickupList()) {

            long orderTimestampMillis = order.getOrderStatus().getTimestamp().getTime();

            if (currentTimeMillis - orderTimestampMillis
                    >= 300000) { // 300000 milliseconds = 5 minutes
                order.cancel();
                branch.getCancelledOrderList().add(order);
                branch.getReadyToPickupList()
                        .remove(order); // Remove the order from readyToPickupList
            }
        }
        return;
    }
}
