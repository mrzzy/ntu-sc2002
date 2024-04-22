package sg.edu.ntu.sc2002;

import java.util.Scanner;

public record PayNowMethod(String name) implements PaymentMethod {
    public PayNowMethod() {
        this("PayNow");
    }

    public String getName() {
        return this.name;
    }

    public boolean pay(int amountCents, Scanner in) {
        System.out.println("Enter your phone number:");
        in.next();
        return true;
    }
}
