package sg.edu.ntu.sc2002;

import java.util.Scanner;

public class PayNowMethod implements PaymentMethod{
    private String name;

    public PayNowMethod(){
        this.name = "PayNow";
    }

    public String getName(){
        return this.name;
    }

    public boolean pay(int amountCents, Scanner in){
        System.out.println("Scan the QR code:");
        return true;
    }
}
