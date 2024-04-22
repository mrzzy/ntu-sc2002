/*
 * NTU SC2002 project
 * Staff Role
 */
package sg.edu.ntu.sc2002;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StaffRole implements Role {
    /**
     * Get the code that uniquely identifies the role
     *
     * @return Single character code for role.
     */
    @Override
    public char code() {
        return 'S';
    }

    /**
     * Get actions that the role is authorised to take.
     *
     * @return Set of actions that the role is authorised to take.
     */
    public static List<StaffAction> getOrderAction() {
        return new ArrayList<StaffAction>(
                Arrays.asList(
                        new StaffOrderAction(StaffOrderMethod.VIEW_ORDERS),
                        new StaffOrderAction(StaffOrderMethod.VIEW_ORDER_DETAILS),
                        new StaffOrderAction(StaffOrderMethod.PROCESS_ORDER)));
    }
}
