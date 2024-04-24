/*
 * NTU SC2002 project
 * Application Test
 * Unit test
 */
package sg.edu.ntu.sc2002;

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
        // change password of 'boss' to 'yram'
        String newPassword = "yram";
        Application.run(
                new String[] {},
                input(
                        String.join(
                                        System.lineSeparator(),
                                        // login staff / admin
                                        "2",
                                        "boss",
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
                                        "boss",
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
