/*
 * NTU SC2002 project
 * Application Test
 * Unit test
 */
package sg.edu.ntu.sc2002;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

/** Application system tests. */
public class ApplicationTest {
    private ByteArrayOutputStream out = new ByteArrayOutputStream();
    private PrintStream systemOut = System.out;

    @BeforeEach
    public void setup() throws IOException {
        // capture stdout for testing
        System.setOut(new PrintStream(out));
    }

    @Test
    public void testInvalidLogin() {
        // test case 24: Attempt to log in with incorrect credentials as a staff member.
        Application.run(
                new String[] {},
                input(
                        String.join(
                                System.lineSeparator(),
                                // login staff / admin
                                "2",
                                "bad",
                                "bad")));
        assertTrue(out.toString().contains("Failed to authenticate"));
        out.reset();
    }

    @Test
    public void testChangePassword() {
        // test case 25: Log in as a staff member, change the default password,
        // and log in again with the new password.
        // change password of 'MaryL' to 'yram'
        String newPassword = "yram";
        Application.run(
                new String[] {},
                input(
                        String.join(
                                        System.lineSeparator(),
                                        // login staff / admin
                                        "2",
                                        "MaryL",
                                        User.DEFAULT_PASSWORD,
                                        // change password
                                        "Y",
                                        newPassword,
                                        // quit
                                        "0")
                                + System.lineSeparator()));

        // login with yram
        Application.run(
                new String[] {},
                input(
                        String.join(
                                        System.lineSeparator(),
                                        // login staff
                                        "2",
                                        "MaryL",
                                        newPassword,
                                        // don't change password
                                        "N",
                                        // exit
                                        "0")
                                + System.lineSeparator()));
    }

    @Test
    public void testUploadStaff() {
        // test case 26: Upload a staff list file during system initialization.
        Application.run(
                new String[] {
                    "--staff",
                    ApplicationTest.class.getResource("staff_list_name_change.xlsx").getPath()
                },
                input(
                        String.join(
                                        System.lineSeparator(),
                                        // login admin
                                        "2",
                                        "G",
                                        User.DEFAULT_PASSWORD,
                                        // don't change password
                                        "N",
                                        // staff action
                                        "3",
                                        // list all staff
                                        "4",
                                        "N",
                                        "N",
                                        "N",
                                        "N",
                                        // exit staff action
                                        "0",
                                        // exit
                                        "0")
                                + System.lineSeparator()));
        List.of("Alex", "Bob", "Charile", "Dennis", "Elizabeth", "Frankie")
                .forEach(name -> assertTrue(out.toString().contains(name)));
    }

    @RepeatedTest(value = 5)
    public void testPersistence() throws IOException {
        // test case 27: Perform multiple sessions of the application, adding,
        // updating, and removing menu items.
        Application.run(
                new String[] {},
                input(
                        String.join(
                                        System.lineSeparator(),
                                        // login admin
                                        "2",
                                        "Alexei",
                                        User.DEFAULT_PASSWORD,
                                        // don't change password
                                        "N",
                                        // edit menu action
                                        "2",
                                        // add item
                                        "1",
                                        // item name
                                        "Apple",
                                        "3.6",
                                        "An Apple",
                                        "Y",
                                        "side",
                                        // exit menu action
                                        "0",
                                        // exit
                                        "0")
                                + System.lineSeparator()));
        out.reset();

        Application.run(
                new String[] {},
                input(
                        String.join(
                                        System.lineSeparator(),
                                        // login admin
                                        "2",
                                        "Alexei",
                                        User.DEFAULT_PASSWORD,
                                        // don't change password
                                        "N",
                                        // view menu action
                                        "1",
                                        // edit menu action
                                        "2",
                                        // update item
                                        "3",
                                        // item number
                                        "2",
                                        // update description
                                        "2",
                                        "Fuji apple",
                                        // exit update item action
                                        "0",
                                        // exit menu action
                                        "0",
                                        // exit
                                        "0")
                                + System.lineSeparator()));
        // check that view menu includes item from previous run
        assertTrue(out.toString().contains("Apple"));
        out.reset();

        Application.run(
                new String[] {},
                input(
                        String.join(
                                        System.lineSeparator(),
                                        // login admin
                                        "2",
                                        "Alexei",
                                        User.DEFAULT_PASSWORD,
                                        // don't change password
                                        "N",
                                        // view menu action
                                        "1",
                                        // edit menu action
                                        "2",
                                        // remove item
                                        "2",
                                        // item number
                                        "2",
                                        // exit menu action
                                        "0",
                                        // exit
                                        "0")
                                + System.lineSeparator()));
        // check that view menu includes item description from previous run
        assertTrue(out.toString().contains("Fuji apple"));
        out.reset();

        Application.run(
                new String[] {},
                input(
                        String.join(
                                        System.lineSeparator(),
                                        // login admin
                                        "2",
                                        "Alexei",
                                        User.DEFAULT_PASSWORD,
                                        // don't change password
                                        "N",
                                        // view menu action
                                        "1",
                                        // exit
                                        "0")
                                + System.lineSeparator()));
        // check item has been removed
        assertFalse(out.toString().contains("Apple"));
        out.reset();
    }

    @AfterEach
    public void teardown() throws IOException {
        // restore stdout
        System.setOut(systemOut);
        // clean up state file
        Path state = Path.of("chain.txt");
        if (Files.exists(state)) {
            Files.delete(state);
        }
    }

    private Scanner input(String content) {
        return new Scanner(new ByteArrayInputStream(content.getBytes()));
    }
}
