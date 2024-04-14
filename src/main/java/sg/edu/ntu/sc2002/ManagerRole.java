/*
 * NTU SC2002 project
 * Manager Role
 */
package sg.edu.ntu.sc2002;

import java.util.ArrayList;
import java.util.List;

public class ManagerRole implements Role {
    @Override
    public char code() {
        // TODO Auto-generated method stub
        return 'M';
    }

    @Override
    public List<Action> getAction() {
        // TODO Auto-generated method stub
        return new ArrayList<>();
    }
}
