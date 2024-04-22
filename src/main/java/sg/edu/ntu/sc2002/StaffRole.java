/*
 * NTU SC2002 project
 * Staff Role
 */
package sg.edu.ntu.sc2002;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StaffRole implements Role {
    @Override
    public char code() {
        return 'S';
    }

    public static List<StaffAction> getOrderAction() {
        return new ArrayList<StaffAction>(
                Arrays.asList(
                        new StaffOrderAction(StaffOrderMethod.VIEW_ORDERS),
                        new StaffOrderAction(StaffOrderMethod.VIEW_ORDER_DETAILS),
                        new StaffOrderAction(StaffOrderMethod.PROCESS_ORDER)));
    }
}
