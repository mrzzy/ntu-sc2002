/*
 * NTU SC2002 project
 * Payment Method
 */

package sg.edu.ntu.sc2002;

import java.io.Serializable;
import java.util.Scanner;

/** Payment Method used by Customers to Pay for Orders */
public interface PaymentMethod extends Serializable {
    public String getName();

    public boolean pay(int amountCents, Scanner in);
}
