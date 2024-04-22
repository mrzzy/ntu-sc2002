package sg.edu.ntu.sc2002;

import java.util.Scanner;

public interface StaffAction {
    /**
     * Title of the Staff Action displayed in the user interface.
     *
     * @return Title of the staff action.
     */
    public String title();

    /**
     * Execute Action on the given Fast Food Chain.
     *
     * @param in Stdin scanner used by action to read user input.
     * @param branch The particular branch the staff performs the action on.
     * @return State of the branch post performing action.
     */
    public Branch execute(Scanner in, Branch branch);
}
