package sg.edu.ntu.sc2002;

public class Payment implements PaymentMethod{
    /**
     * Charge the payment method to pay for given amount.
     *
     * @param amountCents Amount to charge in cents.
     * @return {@code true} If the charge was successful, false otherwise.
     */

    private String name;

    public Payment(String name) {
        this.name = name;
    }

    @Override
    public boolean pay(int amountCents) {
        return false;
    }
}
