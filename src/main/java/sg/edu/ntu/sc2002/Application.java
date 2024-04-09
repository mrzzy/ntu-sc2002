/*
 * NTU SC2002 project
 * Application
 */
package sg.edu.ntu.sc2002;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

/** Entrypoint of the Fastfood Ordering &amp; Management System. */
public class Application {
    public static void main(String[] args) {
        // TODO: load chain from serialised state or init data
        User admin = new User("admin", new AdminRole());
        Chain chain =
                new Chain(
                        admin,
                        new HashMap<String, User>(Map.of(admin.getUsername(), admin)),
                        new HashSet<Branch>(),
                        new HashSet<PaymentMethod>());

        Scanner in = new Scanner(System.in);
        System.out.println("Fastfood Ordering & Management System");

        // authentication loop
        Session session;
        while (true) {
            try {
                session = Session.authenticate(chain.getStaffs(), in);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Failed to authenticate: " + e.getMessage());
            }
        }
        session.role().getAction();

        // Core loop
        while (true) {
            // Print available actions
            // quit action
            System.out.println("0) Quit");
            // role actions
            ArrayList<Action> actions = new ArrayList<>(session.role().getAction());
            for (int i = 0; i < actions.size(); i++) {
                System.out.println(String.format("%d) %s", i + 1, actions.get(i).title()));
            }
            // session action: reset password
            if (session.user().isPresent()) {
                System.out.printf("%d) Reset password\n", actions.size() + 1);
            }

            System.out.print("Option: ");
            int choice = in.nextInt();
            if (choice <= 0) {
                // quit
                break;
            }
            if (choice <= actions.size()) {
                // execute action
                chain = actions.get(choice - 1).execute(in, chain);
                continue;
            }
            if (choice == actions.size() + 1) {
                // reset password
                session.changePassword(in);
                continue;
            }
            System.out.println("Invalid option.");
        }

        in.close();
    }
}
