/*
 * NTU SC2002 project
 * Payment Method
 */

package sg.edu.ntu.sc2002;

import java.io.Serializable;

/** Payment Method used by Customers to Pay for Orders */
public class PaymentMethod implements Serializable {
    private String name;

    public PaymentMethod(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Charge the payment method to pay for given amount.
     *
     * @param amountCents Amount to charge in cents.
     * @return {@code true} If the charge was successful, false otherwise.
     *  TODO : Add a general payment procedure for all payment methods
     */
    public boolean pay(int amountCents) {
        return false;
    }
}
