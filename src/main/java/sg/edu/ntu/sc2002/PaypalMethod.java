package sg.edu.ntu.sc2002;

import java.util.Scanner;

public class PaypalMethod implements PaymentMethod{
    private String name;

    PaypalMethod(){
        this.name = "Paypal";
    }

    public String getName(){
        return this.name;
    }

    public boolean pay(int amountCents, Scanner in){
        System.out.println("Enter email:");
        in.nextLine();
        return true;
    }
}
