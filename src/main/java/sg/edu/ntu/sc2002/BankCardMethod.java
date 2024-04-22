package sg.edu.ntu.sc2002;

import java.util.Scanner;

public record BankCardMethod() implements PaymentMethod {
    public String getName() {
        return "Bank Card";
    }

    public boolean pay(int amountCents, Scanner in) {
        System.out.println("Enter PIN");
        in.next();
        return true;
    }
}
