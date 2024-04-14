/*
 * NTU SC2002 project
 * Staff Role
 */
package sg.edu.ntu.sc2002;

import java.util.HashSet;
import java.util.Set;

public class StaffRole implements Role {
    @Override
    public char code() {
        // TODO Auto-generated method stub
        return 'S';
    }

    @Override
    public Set<Action> getAction() {
        // TODO Auto-generated method stub
        return new HashSet<>();
    }
}
