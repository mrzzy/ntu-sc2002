/*
 * NTU SC2002 project
 * User Role
 */

package sg.edu.ntu.sc2002;

import java.util.List;
import java.io.Serializable;

/** Role held by a {@link User} defines the actions the User is authorised to take. */
public interface Role extends Serializable {
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
    public List<Action> getAction();
}
