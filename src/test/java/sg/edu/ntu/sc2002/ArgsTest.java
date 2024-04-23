/*
 * NTU SC2002 project
 * Args
 * Unit Test
 */

package sg.edu.ntu.sc2002;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.beust.jcommander.JCommander;
import org.junit.jupiter.api.Test;

public class ArgsTest {
    @Test
    public void testParse() {
        String[][] argvs =
                new String[][] {
                    {
                        "-r",
                        "restore.txt",
                        "-b",
                        "branch.xlsx",
                        "-s",
                        "staff.xlsx",
                        "-m",
                        "menu.xlsx"
                    },
                    {
                        "--restore",
                        "restore.txt",
                        "--branch",
                        "branch.xlsx",
                        "--staff",
                        "staff.xlsx",
                        "--menu",
                        "menu.xlsx"
                    },
                };

        for (String[] argv : argvs) {
            Args args = new Args();
            JCommander.newBuilder().addObject(args).build().parse(argv);
            assertEquals(args.getStatePath(), "restore.txt");
            assertEquals(args.getBranchPath(), "branch.xlsx");
            assertEquals(args.getStaffPath(), "staff.xlsx");
            assertEquals(args.getMenuPath(), "menu.xlsx");
        }
    }

    @Test
    public void testHelp() {
        Args args = new Args();
        JCommander.newBuilder().addObject(args).build().parse("-h");
        assertTrue(args.isHelp());
    }
}
