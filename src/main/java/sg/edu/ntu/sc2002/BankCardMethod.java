package sg.edu.ntu.sc2002;

import java.util.Scanner;

/** Implementation of the {@link PaymentMethod} interface. */
public record BankCardMethod() implements PaymentMethod {
    /**
     * Name of the payment method.
     *
     * @return Name of the method.
     */
    public String getName() {
        return "BankCard";
    }

    /**
     * Make payment for the order using the specified method
     *
     * @param amountCents The total cost of the order in cents.
     * @param in Stdin scanner used by action to read user input.
     * @return The outcome of the transaction.
     */
    public boolean pay(int amountCents, Scanner in) {
        System.out.println("Enter PIN");
        in.next();
        return true;
    }
}
