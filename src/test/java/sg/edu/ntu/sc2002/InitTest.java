/*
 * NTU SC2002 project
 * Init
 * Unit Test
 */

package sg.edu.ntu.sc2002;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

public class InitTest {
    @Test
    public void readInitWorkbook() {
        // check that only non empty rows where read
        assertEquals(Init.readInitWorkbook("staff_list.xlsx").size(), 8);
        assertEquals(Init.readInitWorkbook("menu_list.xlsx").size(), 9);
        assertEquals(Init.readInitWorkbook("branch_list.xlsx").size(), 4);
    }

    @Test
    public void parseStaffs() {
        Map<String, Set<User>> staffs = Init.parseStaffs(Init.readInitWorkbook("staff_list.xlsx"));
        assertTrue(
                staffs.get("NTU")
                        .contains(
                                new User(
                                        "kumarB",
                                        "kumar Blackmore",
                                        "NTU",
                                        32,
                                        Gender.MALE,
                                        User.DEFAULT_PASSWORD,
                                        new StaffRole())));
    }

    @Test
    public void parseMenus() {
        Map<String, Set<Item>> menus = Init.parseMenus(Init.readInitWorkbook("menu_list.xlsx"));
        menus.get("NTU").contains(new Item("FRIES", 3.2, "", true, "side"));
    }

    @Test
    public void parseBranches() {
        Map<String, Branch> branches =
                Init.parseBranches(Init.readInitWorkbook("branch_list.xlsx"));
        assertTrue(branches.containsKey("NTU"));
        assertEquals(
                branches.get("NTU"),
                new Branch(
                        "NTU",
                        "North spine Plaza",
                        8,
                        new HashSet<>(),
                        new HashSet<>(),
                        new HashSet<>(),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>()
                ));
    }

    @Test
    public void initChain() {
        Chain chain = Init.initChain();
        assertEquals(
                chain.getAdmin(),
                new User(
                        "boss", "Boss", "", 62, Gender.FEMALE, User.DEFAULT_PASSWORD, new AdminRole()));

        assertEquals(chain.getBranches().size(), 3);
        assertEquals(chain.getStaffs().size(), 7);
        assertEquals(
                chain.getBranches().stream().flatMap(branch -> branch.getMenu().stream()).count(),
                8);
    }
}
