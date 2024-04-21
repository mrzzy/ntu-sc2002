/*
 * NTU SC2002 project
 * Session
 */

package sg.edu.ntu.sc2002;

import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

/** Defines an application Session acquired by a User to interact with the application. */
public record Session(Role role, Optional<User> user) {
    /**
     * Create application Session by challenging over STDOUT.
     *
     * @param users Map of users to authenticate against.
     * @param in Scanner used to capture user input for authentication challenge.
     * @throws IllegalArgumentException On invalid input from user: invalid option or invalid
     *     credentials.
     * @return Created application Session.
     */
    public static Session authenticate(Map<String, User> users, Scanner in) {
        System.out.println("-------------------------");
        System.out.println("Are you a customer or a admin/staff?");
        System.out.println("1) Customer");
        System.out.println("2) Admin/Staff");
        System.out.print("Option: ");
        int choice = Input.nextInt(in);

        if (choice == 1) {
            return new Session(new CustomerRole(), Optional.empty());
        }
        if (choice == 2) {
            // prompt credentials
            System.out.print("Username: ");
            String username = in.next().strip();
            System.out.print("Password: ");
            String password = in.next().strip();

            // check user credentials against users map
            Optional<User> user =
                    Optional.ofNullable(users.get(username))
                            .flatMap(
                                    u ->
                                            (u.login(username, password))
                                                    ? Optional.of(u)
                                                    : Optional.empty());
            if (user.isEmpty()) {
                throw new IllegalArgumentException("Invalid username or password.");
            }

            return new Session(user.get().getRole(), user);
        }
        throw new IllegalArgumentException("Invalid option: " + choice);
    }

    /**
     * Process user request to change password using given input scanner.
     *
     * @param in Scanner used to capture user input for new password.
     * @throws RuntimeException If Session not authenticated as a user.
     */
    public void changePassword(Scanner in) {
        if (user.isEmpty()) {
            throw new RuntimeException(
                    "Cannot change password: Session not authenticated as user.");
        }
        System.out.print("New password: ");
        user.get().setPassword(in.next().strip());
    }
}
