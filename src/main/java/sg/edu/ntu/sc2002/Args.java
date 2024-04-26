/*
 * NTU SC2002 project
 * Args
 */

package sg.edu.ntu.sc2002;

import com.beust.jcommander.Parameter;

/** Command line arguments for {@link Application}. */
public class Args {
        @Parameter(names = { "-r",
                        "--restore" }, description = "Optional. Path of serialization file to save / restore application state."
                                        + " Unspecified defaults to 'chain.txt'.")
        private String statePath = "";

        @Parameter(names = { "-b",
                        "--branch" }, description = "Optional. Path of excel file to initialise branches from. Overrides"
                                        + " serialization state.")
        private String branchPath = "";

        @Parameter(names = { "-s",
                        "--staff" }, description = "Optional. Path of excel file to initialise branch staff from. Overrides"
                                        + " serialization state.")
        private String staffPath = "";

        @Parameter(names = { "-m",
                        "--menu" }, description = "Optional. Path of excel file to initialise branch menus from. Overrides"
                                        + " serialization state.")
        private String menuPath = "";

        @Parameter(names = { "-h", "--help" }, help = true)
        private boolean help;

        /**
         * Get the path of the state file.
         * 
         * @return Path of the state file.
         */
        public String getStatePath() {
                return (statePath.length() > 0) ? statePath : "chain.txt";
        }

        /**
         * Check if the help flag is set.
         * 
         * @return True if the help flag is set.
         */
        public boolean isHelp() {
                return help;
        }

        /**
         * Get the path of the branch file.
         * 
         * @return Path of the branch file.
         */
        public String getBranchPath() {
                return branchPath;
        }

        /**
         * Get the path of the staff file.
         * 
         * @return Path of the staff file.
         */
        public String getStaffPath() {
                return staffPath;
        }

        /**
         * Get the path of the menu file.
         * 
         * @return Path of the menu file.
         */
        public String getMenuPath() {
                return menuPath;
        }
}
