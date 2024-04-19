/*
 * NTU SC2002 project
 * Manager Role
 */
package sg.edu.ntu.sc2002;

import java.util.ArrayList;
import java.util.List;

public class ManagerRole implements Role extends StaffRole {
    @Override
    public char code() {
        // TODO Auto-generated method stub
        return 'M';
    }

    public List<Action> getMenuAction() {
        // TODO Auto-generated method stub
        return new ArrayList<>(
            Arrays.asList(
                new ManagerMenuAction(ManagerMenuMethod.ADD_ITEM),
                new ManagerMenuAction(ManagerMenuMethod.REMOVE_ITEM),
                new ManagerMenuAction(ManagerMenuMethod.UPDATE_ITEM)
            )
        );
    }

    public List<Action> getStaffAction() {
        // TODO Auto-generated method stub
        return new ArrayList<>(
            Arrays.asList(
                new ManagerStaffAction(ManagerStaffMethod.LIST_STAFF_ALL)
            )
        );
    }
}
