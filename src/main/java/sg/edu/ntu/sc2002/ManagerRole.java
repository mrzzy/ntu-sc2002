/*
 * NTU SC2002 project
 * Manager Role
 */
package sg.edu.ntu.sc2002;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Implementation of the {@link IRole} interface. */
public class ManagerRole extends StaffRole implements IRole {
    /**
     * Get the code that uniquely identifies the role
     *
     * @return Single character code for role.
     */
    @Override
    public char code() {
        return 'M';
    }

    /**
     * Get actions that the role is authorised to take.
     *
     * @return Set of actions that the role is authorised to take.
     */
    public static List<IManagerAction> getMenuAction() {

        return new ArrayList<IManagerAction>(
                Arrays.asList(
                        new ManagerMenuAction(ManagerMenuMethod.ADD_ITEM),
                        new ManagerMenuAction(ManagerMenuMethod.REMOVE_ITEM),
                        new ManagerMenuAction(ManagerMenuMethod.UPDATE_ITEM)));
    }

    /**
     * Get actions that the role is authorised to take.
     *
     * @return Set of actions that the role is authorised to take.
     */
    public static List<IManagerAction> getStaffAction() {
        return new ArrayList<IManagerAction>(
                Arrays.asList(new ManagerStaffAction(ManagerStaffMethod.LIST_STAFF_ALL)));
    }
}
