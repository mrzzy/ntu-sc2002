/*
 * NTU SC2002 project
 * Action
*/

package sg.edu.ntu.sc2002;

import java.util.Scanner;

/**
 * Defines an executable Action on a Fast Food {@link Chain}.
 */
public interface Action {
    /**
     * Title of the Action displayed in the user interface.
     * @return Tile of the action.
    */
    public String title();

    /** Execute Action on the given Fast Food Chain.
     * 
     * @param in Stdin scanner used by action to read user input.
     * @param chain Fast Food Chain to perform the action on.
     * @return State of Fast Food Chain post performing action.
    */
    public Chain execute(Scanner in, Chain chain);
}
