/*
 * NTU SC2002 project
 * Manager Role
 */
package sg.edu.ntu.sc2002;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class ManagerRole extends StaffRole implements Role{
    @Override
    public char code() {
        return 'M';
    }

    public static List<ManagerAction> getMenuAction() {

        return new ArrayList<ManagerAction>(
            Arrays.asList(
                new ManagerMenuAction(ManagerMenuMethod.ADD_ITEM),
                new ManagerMenuAction(ManagerMenuMethod.REMOVE_ITEM),
                new ManagerMenuAction(ManagerMenuMethod.UPDATE_ITEM)
            )
        );
    }

    public static List<ManagerAction> getStaffAction() {
        return new ArrayList<ManagerAction>(
            Arrays.asList(
                new ManagerStaffAction(ManagerStaffMethod.LIST_STAFF_ALL)
            )
        );
    }
}
