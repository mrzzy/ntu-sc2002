package sg.edu.ntu.sc2002;

import java.util.Scanner;

public class Input {
    public static int nextInt(Scanner in) {
        int choice = -1;
        while (choice == -1) {
            try {
                choice = in.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input");
                choice = -1;
            } finally {
                in.nextLine();
            }
        }
        return choice;
    }

    public static double nextDouble(Scanner in) {
        double choice = -1;
        while (choice == -1) {
            try {
                choice = in.nextDouble();
            } catch (Exception e) {
                System.out.println("Invalid input");
                choice = -1;
                in.nextLine();
            }
        }
        return choice;
    }
}
