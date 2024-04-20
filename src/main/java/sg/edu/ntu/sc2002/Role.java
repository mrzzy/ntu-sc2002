/*
 * NTU SC2002 project
 * User Role
 */

package sg.edu.ntu.sc2002;

import java.io.Serializable;
import java.util.List;

/** Role held by a {@link User} defines the actions the User is authorised to take. */
public interface Role extends Serializable {
    /**
     * Get the code that uniquely identifies the role
     *
     * @return Single character code for role.
     */
    public char code();
}