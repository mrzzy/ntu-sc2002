/*
 * NTU SC2002 project
 * Staff Role
 */
package sg.edu.ntu.sc2002;

import java.util.ArrayList;
import java.util.List;

public class StaffRole implements Role {
    @Override
    public char code() {
        // TODO Auto-generated method stub
        return 'S';
    }

    @Override
    public List<Action> getAction() {
        // TODO Auto-generated method stub
        return new ArrayList<>();
    }
}
