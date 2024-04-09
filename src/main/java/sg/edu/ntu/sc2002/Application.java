/*
 * NTU SC2002 project
 * Application
 */
package sg.edu.ntu.sc2002;

import java.util.HashSet;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

/** Entrypoint of the Fastfood Ordering &amp; Management System. */
public class Application {
    public static void main(String[] args) {
        Role role = null;
        Optional<User> user = Optional.empty();
        Chain chain = new Chain(
                new User("admin", new AdminRole()),
                new HashSet<User>(),
                new HashSet<Branch>(),
                new HashSet<PaymentMethod>()
        );

        Scanner scanner = new Scanner(System.in);

        // Determine if the user is a customer
        System.out.println("Are you a customer or a admin/staff?");
        System.out.println("1) Customer");
        System.out.println("2) Admin/Staff");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1: {
                // TODO : For when we get customer
                // role = new CustomerRole();
                break;
            }
            case 2: {
                // Login ask them to login
                System.out.println("Please enter username : ");
                String username = scanner.next().strip();
                System.out.println("Please enter password : ");
                String password = scanner.next().strip();

                // Check if it's the admin user
                if (username.equals(chain.getAdmin().username) && password.equals(chain.getAdmin().password)) {
                    role = new AdminRole();
                    user = Optional.of(chain.getAdmin());
                    System.out.println("Found");
                } else {
                    for (User staff: chain.getStaffs()) {
                        if (staff.login(username, password)) {
                            // TODO : Add this in
                            // role = new StaffRole();
                            user = Optional.of(staff);
                        }
                    }
                }

                // TODO : Some post-failure-authentication measures
                if (user.isEmpty()) {
                    System.out.println("Username and password not found");
                }

                break;
            }
        }

        // Core loop
        while (true) {
            // TODO : Deal with customers
            // Print available actions
            Set<Action> actions = role.getAction();
            for (Action action : actions) {
                System.out.println(action.title());
            }
            // TODO : How to deal with which of action to pass in?

            break;
        }
    }
}
