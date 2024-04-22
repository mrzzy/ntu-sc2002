package sg.edu.ntu.sc2002;

import java.util.Scanner;

public record PaypalMethod() implements PaymentMethod {
    public String getName() {
        return "Paypal";
    }

    public boolean pay(int amountCents, Scanner in) {
        System.out.println("Enter email:");
        in.next();
        return true;
    }
}
