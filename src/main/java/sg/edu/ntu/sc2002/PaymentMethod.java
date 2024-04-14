/*
 * NTU SC2002 project
 * Payment Method
 */

package sg.edu.ntu.sc2002;

import java.io.Serializable;

/** Payment Method used by Customers to Pay for Orders */
public interface PaymentMethod extends Serializable {
    /**
     * Charge the payment method to pay for given amount.
     *
     * @param amountCents Amount to charge in cents.
     * @return {@code true} If the charge was successful, false otherwise.
     */
    public boolean pay(int amountCents);
}
