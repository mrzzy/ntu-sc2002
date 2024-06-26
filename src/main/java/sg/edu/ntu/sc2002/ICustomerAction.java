package sg.edu.ntu.sc2002;

import java.util.Scanner;
import java.util.Set;

/** Defines an executable Action on a Fast Food Chain. */
public interface ICustomerAction {
    /**
     * Title of the Customer Action displayed in the user interface.
     *
     * @return Title of the customer action.
     */
    public String title();

    /**
     * Execute Action on the given Fast Food Chain.
     *
     * @param in Stdin scanner used by action to read user input.
     * @param branch The particular branch the customer perform the action on.
     * @param paymentMethods The payment methods the customer chooses.
     * @return State of the branch post performing action.
     */
    public Branch execute(Scanner in, Branch branch, Set<IPaymentMethod> paymentMethods);
}
