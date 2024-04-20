/*
 * NTU SC2002 project
 * Manager Role
 */
package sg.edu.ntu.sc2002;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class ManagerRole implements Role extends StaffRole {
    @Override
    public char code() {
        // TODO Auto-generated method stub
        return 'M';
    }

    public static List<ManagerAction> getMenuAction() {
        // TODO Auto-generated method stub
        return new ArrayList<ManagerAction>(
            Arrays.asList(
                new ManagerMenuAction(ManagerMenuMethod.ADD_ITEM),
                new ManagerMenuAction(ManagerMenuMethod.REMOVE_ITEM),
                new ManagerMenuAction(ManagerMenuMethod.UPDATE_ITEM)
            )
        );
    }

    public static List<ManagerAction> getStaffAction() {
        // TODO Auto-generated method stub
        return new ArrayList<ManagerAction>(
            Arrays.asList(
                new ManagerStaffAction(ManagerStaffMethod.LIST_STAFF_ALL)
            )
        );
    }
}
