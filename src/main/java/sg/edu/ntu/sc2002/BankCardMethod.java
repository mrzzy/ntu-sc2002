package sg.edu.ntu.sc2002;

import java.util.Scanner;

public class BankCardMethod implements PaymentMethod{
    private String name;

    public BankCardMethod(){
        this.name = "Bank card";
    }

    public String getName(){
        return this.name;
    }

    public boolean pay(int amountCents, Scanner in){
        System.out.println("Enter email");
        in.nextLine();
        return true;
    }
}
