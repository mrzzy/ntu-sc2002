package sg.edu.ntu.sc2002;

import java.util.Scanner;

/** Utility class to take in Scanner input */
public class Input {

    /**
     * Handles the error when reading an integer input from Scanner class.
     * 
     * @param in Stdin scanner used by action to read user input.
     * @return Scanner input.
     */
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

    /**
     * Handles the error when reading a float input from Scanner class.
     * 
     * @param in Stdin scanner used by action to read user input.
     * @return Scanner input.
     */
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
