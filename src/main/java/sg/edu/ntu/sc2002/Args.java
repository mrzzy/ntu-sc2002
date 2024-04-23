/*
 * NTU SC2002 project
 * Args
 */

package sg.edu.ntu.sc2002;

import com.beust.jcommander.Parameter;

/** Command line arguments for {@link Application}. */
public class Args {
    @Parameter(
            names = {"-r", "--restore"},
            description =
                    "Optional. Path of serialization file to save / restore application state."
                            + " Unspecified defaults to 'chain-history/chain.txt'.")
    private String statePath = "";

    @Parameter(
            names = {"-b", "--branch"},
            description =
                    "Optional. Path of excel file to initialise branches from. Overrides"
                            + " serialization state.")
    private String branchPath = "";

    @Parameter(
            names = {"-s", "--staff"},
            description =
                    "Optional. Path of excel file to initialise branch staff from. Overrides"
                            + " serialization state.")
    private String staffPath = "";

    @Parameter(
            names = {"-m", "--menu"},
            description =
                    "Optional. Path of excel file to initialise branch menus from. Overrides"
                            + " serialization state.")
    private String menuPath = "";

    @Parameter(
            names = {"-h", "--help"},
            help = true)
    private boolean help;

    public String getStatePath() {
        return (statePath.length() > 0) ? statePath : "chain.txt";
    }

    public boolean isHelp() {
        return help;
    }

    public String getBranchPath() {
        return branchPath;
    }

    public String getStaffPath() {
        return staffPath;
    }

    public String getMenuPath() {
        return menuPath;
    }
}
