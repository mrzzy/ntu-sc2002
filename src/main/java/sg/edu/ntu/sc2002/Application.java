/*
 * NTU SC2002 project
 * Application
 */
package sg.edu.ntu.sc2002;

import com.beust.jcommander.JCommander;
import java.util.Scanner;

/** Fast food Ordering &amp; Management System application. */
public class Application {
    /**
     * Entrypoint of Fast food Ordering &amp; Management System
     *
     * @param args See usage information by parsing the {@code --help} flag.
     */
    public static void main(String[] args) {
        // parse command line args
        Args arguments = new Args();
        JCommander parser = JCommander.newBuilder().addObject(arguments).build();
        parser.parse(args);
        // help: show usage and exit
        if (arguments.isHelp()) {
            parser.usage();
            return;
        }

        System.out.println("Fast food Ordering & Management System");
        // init or restore chain state
        Chain chain = Init.initChain(arguments);

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
        boolean selectedValidBranch = false;

        if (!session.user().isPresent()) {
            while (selectedBranch == null) {
                int i = 1;
                System.out.println("-------------------------");
                System.out.println("Select a branch:");
                for (Branch branch : chain.getBranches()) {
                    System.out.println(String.format("%d) %s", i++, branch.getName()));
                }
                int choice = Input.nextInt(in);
                i = 1;
                for (Branch branch : chain.getBranches()) {
                    if (i == choice) {
                        if (branch.getStaffs().size() == 0 && branch.getManagers().size() == 0) {
                            System.out.println(
                                    "Branch has no available staff or managers to serve you!");
                            break;
                        }
                        selectedBranch = branch;
                        selectedValidBranch = true;
                        System.out.println("-------------------------");
                        System.out.println(
                                String.format("Selected branch: %s", selectedBranch.getName()));
                        break;
                    }
                    i++;
                }
            }

        } else {
            String choice = null;
            while (choice == null) {
                if (session.role().code() == 'M' || session.role().code() == 'S') {
                    for (Branch branch : chain.getBranches()) {
                        if (branch.getName().equals(session.user().get().getBranchBelongTo())) {
                            selectedBranch = branch;
                            break;
                        }
                    }
                }
                // session action: reset password
                System.out.println("-------------------------");
                System.out.printf("Reset password (Y/N (Press any Key))?");
                choice = in.next();
                if (choice.toLowerCase().equals("y")) {
                    session.changePassword(in);
                }
            }
        }

        // Check if order has expired for all branches, runs in background
        for (Branch branch : chain.getBranches()) {
            Expired.cancelChecker(branch);
        }

        // Print available role actions
        char role = session.role().code();
        switch (role) {
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
                CustomerActionHandler.actionDispatcher(
                        in, selectedBranch, chain.getPaymentMethods());
                break;
        }

        // save chain state
        Serialize.save(chain, arguments.getStatePath());
        System.out.println("Saved application state: " + arguments.getStatePath());
        in.close();
        System.exit(0);
    }
}
