/*
 * NTU SC2002 project
 * Customer Role
 */
package sg.edu.ntu.sc2002;

import java.util.ArrayList;
import java.util.List;

public class CustomerRole implements Role {
    @Override
    public char code() {
        // TODO Auto-generated method stub
        return 'C';
    }

    @Override
    public List<Action> getAction() {
        // TODO Auto-generated method stub
        return new ArrayList<>();
    }
}
