/*
 * NTU SC2002 project
 * Application
 */
package sg.edu.ntu.sc2002;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

        // select the branch
        Branch selectedBranch = null;

        if (session.role().code() =='C'){
            System.out.println("Select a branch:");
            for (Branch branch : chain.getBranches()){
                System.out.println(String.format("%s", branch.getName()));
            }
            String choice = in.nextLine();
            for (Branch branch : chain.getBranches()){
                if (branch.getName().equals(choice)){
                    selectedBranch = branch;
                    break;
                }
            }
        } else if (session.user().isPresent()){
            if (session.role().code() =='M' || session.role().code() =='S'){
                for (Branch branch : chain.getBranches()){
                    if (branch.getName().equals(session.user().get().getBranchBelongTo())){
                        selectedBranch = branch;
                        break;
                    }
                }
            }
            // session action: reset password
            System.out.printf("Reset password (Y)?");
            String choice = in.nextLine();
            if (choice.toLowerCase().equals("y")) {
                session.changePassword(in);
            } 
        }

        // Check if order has expired for all branches, runs in background
        for (Branch branch : chain.getBranches()){
            branch.checkExpired();
        }

        // Print available role actions
        char role = session.role().code();
        switch(role){
            case 'A':
                AdminActionHandler.actionDispatcher(in, chain);
                break;
            case 'M':
                ManagerActionHandler.actionDispatcher(in, selectedBranch);
                break;
            case 'S':
                StaffActionHandler.actionDispatcher(in, selectedBranch);
                break;
            case 'C':
                CustomerActionHandler.actionDispatcher(in, selectedBranch, chain.getPaymentMethods());
                break;
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
