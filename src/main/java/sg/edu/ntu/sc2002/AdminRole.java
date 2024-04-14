package sg.edu.ntu.sc2002;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class AdminRole implements Role {
    /**
     * Get the code that uniquely identifies the role
     *
     * @return Single character code for role.
     */
    @Override
    public char code() {
        return 'A';
    }

    /**
     * Get actions that the role is authorised to take.
     *
     * @return Set of actions that the role is authorised to take.
     */
    @Override
    public List<Action> getAction() {
        return new ArrayList<Action>(
                Arrays.asList(
                        new AdminAction(AdminMethod.ADD_STAFF),
                        new AdminAction(AdminMethod.EDIT_STAFF),
                        new AdminAction(AdminMethod.REMOVE_STAFF),
                        new AdminAction(AdminMethod.LIST_STAFF_ALL),
                        new AdminAction(AdminMethod.ASSIGN_MANAGER),
                        new AdminAction(AdminMethod.PROMOTE_STAFF),
                        new AdminAction(AdminMethod.TRANSFER_STAFF),
                        new AdminAction(AdminMethod.ADD_PAYMENT),
                        new AdminAction(AdminMethod.REMOVE_PAYMENT),
                        new AdminAction(AdminMethod.OPEN_BRANCH),
                        new AdminAction(AdminMethod.CLOSE_BRANCH)
                ));
    }
}
