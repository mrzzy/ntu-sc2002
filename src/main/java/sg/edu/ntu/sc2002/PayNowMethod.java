package sg.edu.ntu.sc2002;

import java.util.Scanner;

/** Implementation of the {@link PaymentMethod} interface. */
public record PayNowMethod(String name) implements PaymentMethod {
    
    /**
     * Constructor to set name of payment method.
     */
    public PayNowMethod() {
        this("PayNow");
    }

    /**
     * Name of the payment method.
     * 
     * @return Name of the method.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Make payment for the order using the specified method
     * 
     * @param amountCents The total cost of the order in cents.
     * @param in Stdin scanner used by action to read user input.
     * @return The outcome of the transaction.
     */
    public boolean pay(int amountCents, Scanner in) {
        System.out.println("Enter your phone number:");
        in.next();
        return true;
    }
}
