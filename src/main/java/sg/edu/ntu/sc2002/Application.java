/*
 * NTU SC2002 project
 * Application
 */
package sg.edu.ntu.sc2002;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

/** Fastfood Ordering &amp; Management System application. */
public class Application {
    /**
     * Entrypoint of Fastfood Ordering &amp; Management System
     *
     * @param args Optional. User may pass the path of serialization file to save / restore Fast
     *     Food {@link Chain} state. Otherwise, Chain stat will be saved to a temporary file.
     */
    public static void main(String[] args) {
        System.out.println("Fastfood Ordering & Management System");
        // init or restore chain state
        Chain chain;
        if (args.length >= 1 && Files.exists(Path.of(args[0]))) {
            // restore chain state from serialised file
            chain = Serialize.restore(args[0]);
            System.out.println("Restored application state: " + args[0]);
        } else {
            // initialize chain state from resources
            chain = Init.initChain();
        }

        // authentication loop
        Scanner in = new Scanner(System.in);
        Session session;
        while (true) {
            try {
                session = Session.authenticate(chain.getStaffs(), in);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Failed to authenticate: " + e.getMessage());
            }
        }

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

        // save chain state
        String savePath;
        if (args.length >= 1) {
            savePath = args[0];
        } else {
            try {
                savePath = File.createTempFile("chain", "").getPath();
            } catch (IOException e) {
                throw new RuntimeException("Could not create save file.", e);
            }
        }
        Serialize.save(chain, savePath);
        System.out.println("Saved application state: " + savePath);
        in.close();
    }
}
