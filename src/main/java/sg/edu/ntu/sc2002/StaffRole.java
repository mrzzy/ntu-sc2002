/*
 * NTU SC2002 project
 * Staff Role
 */
package sg.edu.ntu.sc2002;

import java.util.ArrayList;
import java.util.List;

public class StaffRole implements Role {
    @Override
    public char code() {
        // TODO Auto-generated method stub
        return 'S';
    }

    @Override
    public List<StaffAction> getAction() {
        // TODO Auto-generated method stub
        return new ArrayList<StaffAction>(
                Arrays.asList(
                        new StaffOrderAction(StaffOrderMethod.VIEW_ORDERS),
                        new StaffOrderAction(StaffOrderMethod.VIEW_ORDER_DETAILS),
                        new StaffOrderAction(StaffOrderMethod.SET_ORDER_STATUS)));
    }
}
