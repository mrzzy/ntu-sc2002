/*
 * NTU SC2002 project
 * Payment Method
 */

package sg.edu.ntu.sc2002;

import java.io.Serializable;
import java.util.Scanner;

/** Payment Method used by Customers to Pay for Orders */
public interface PaymentMethod extends Serializable {
    /**
     * Get the name of the Payment Method.
     *
     * @return Name of the Payment Method.
     */
    public String getName();

    /**
     * Pay for the Order with the given amount.
     *
     * @param amountCents Amount to be paid in cents.
     * @param in Stdin scanner used by action to read user input.
     * @return True if payment is successful, false otherwise.
     */
    public boolean pay(int amountCents, Scanner in);
}
