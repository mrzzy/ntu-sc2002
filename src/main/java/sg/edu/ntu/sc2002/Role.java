/*
 * NTU SC2002 project
 * User Role
 */

package sg.edu.ntu.sc2002;

import java.util.Set;

/** Role held by a {@link User} defines the actions the User is authorised to take. */
public interface Role {
    /**
     * Get the code that uniquely identifies the role
     *
     * @return Single character code for role.
     */
    public char code();

    /**
     * Get actions that the role is authorised to take.
     *
     * @return Set of actions that the role is authorised to take.
     */
    public Set<Action> getAction();
}
