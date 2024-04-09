package sg.edu.ntu.sc2002;

import java.util.Set;

public class AdminRole implements Role {
    /**
     * Get the code that uniquely identifies the role
     *
     * @return Single character code for role.
     */
    @Override
    public char code() {
        return 0;
    }

    /**
     * Get actions that the role is authorised to take.
     *
     * @return Set of actions that the role is authorised to take.
     */
    @Override
    public Set<Action> getAction() {
        return Set.of(new AdminAction());
    }
}
