package sg.edu.ntu.sc2002;

import java.util.Scanner;

public class AdminAction implements Action {
    private AdminMethod method;

    /**
     * Title of the Action displayed in the user interface.
     *
     * @return Tile of the action.
     */
    @Override
    public String title() {
        return "";
    }

    /**
     * Execute Action on the given Fast Food Chain.
     *
     * @param in    Stdin scanner used by action to read user input.
     * @param chain Fast Food Chain to perform the action on.
     * @return State of Fast Food Chain post performing action.
     */
    @Override
    public Chain execute(Scanner in, Chain chain) {
        return null;
    }
}
