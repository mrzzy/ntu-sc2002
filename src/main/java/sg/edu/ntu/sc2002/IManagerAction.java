/*
 * NTU SC2002 project
 * ManagerAction
 */

package sg.edu.ntu.sc2002;

import java.util.Scanner;

/** Defines an executable Action on a Fast Food {@link Chain}. */
public interface IManagerAction {
    /**
     * Title of the Manager Action displayed in the user interface.
     *
     * @return Tile of the manager action.
     */
    public String title();

    /**
     * Execute Action on the given Fast Food Chain.
     *
     * @param in Stdin scanner used by action to read user input.
     * @param branch The particular branch the manager perform the action on.
     * @return State of the branch post performing action.
     */
    public Branch execute(Scanner in, Branch branch);
}
