/*
 * NTU SC2002 project
 * Init
 * Unit Test
 */

package sg.edu.ntu.sc2002;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

public class InitTest {
    private Map<String, Branch> branches =
            Init.parseBranches(
                    Init.readExcel(Init.class.getResource("branch_list.xlsx").getPath()));
    private Map<String, Set<Item>> menus =
            Init.parseMenus(Init.readExcel(Init.class.getResource("menu_list.xlsx").getPath()));
    private Map<String, Set<User>> staffs =
            Init.parseStaffs(Init.readExcel(Init.class.getResource("staff_list.xlsx").getPath()));

    @Test
    public void testReadExcel() {
        // check that only non empty rows where read
        assertEquals(Init.readExcel(Init.class.getResource("staff_list.xlsx").getPath()).size(), 8);
        assertEquals(Init.readExcel(Init.class.getResource("menu_list.xlsx").getPath()).size(), 9);
        assertEquals(
                Init.readExcel(Init.class.getResource("branch_list.xlsx").getPath()).size(), 4);
    }

    @Test
    public void testParseStaffs() {
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
    public void testParseMenus() {
        menus.get("NTU").contains(new Item("FRIES", 3.2, "", true, "side"));
    }

    @Test
    public void testParseBranches() {
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
                        new ArrayList<>()));
    }

    @Test
    public void testOverrideStaff() {
        // override staff in NTU branch with staff from a different branch
        branches = Init.overrideStaff(branches, Map.of("NTU", staffs.get("JE")));
        assertEquals(branches.get("NTU").getStaffs().iterator().next().getUsername(), "MaryL");
        assertEquals(branches.get("NTU").getManagers().iterator().next().getUsername(), "AlicaA");
    }

    @Test
    public void testOverrideMenu() {
        // override menus in NTU branch with menus from a different branch
        branches = Init.overrideMenus(branches, Map.of("NTU", menus.get("JP")));
        assertEquals(branches.get("NTU").getMenu(), menus.get("JP"));
    }

    @Test
    public void testInitChainResources() {
        Chain chain = Init.initChain(new Args());
        assertEquals(
                chain.getAdmin(),
                new User(
                        "boss",
                        "Boss",
                        "",
                        62,
                        Gender.FEMALE,
                        User.DEFAULT_PASSWORD,
                        new AdminRole()));

        assertEquals(chain.getBranches().size(), 3);
        assertEquals(chain.getStaffs().size(), 7);
        assertEquals(
                chain.getBranches().stream().flatMap(branch -> branch.getMenu().stream()).count(),
                8);
    }

    @Test
    public void testInitChainRestore() throws IOException {
        // make a change to chain and save it
        Chain saved = Init.initChain(new Args());
        saved.getAdmin().setAge(99);
        File saveFile = File.createTempFile("chain", ".txt");
        Serialize.save(saved, saveFile.getPath());

        // restore chain from state
        Chain restored =
                Init.initChain(
                        new Args() {
                            public String getStatePath() {
                                return saveFile.getPath();
                            }
                            ;
                        });

        // check that change has been reflected
        assertEquals(99, restored.getAdmin().getAge());
    }

    @Test
    public void testInitChainOverride() {
        Chain chain =
                Init.initChain(
                        new Args() {
                            @Override
                            public String getStaffPath() {
                                return InitTest.class
                                        .getResource("staff_list_name_change.xlsx")
                                        .getPath();
                            }

                            @Override
                            public String getMenuPath() {
                                return InitTest.class
                                        .getResource("menu_list_name_change.xlsx")
                                        .getPath();
                            }
                        });

        assertEquals(
                chain.getStaffs().keySet().stream().sorted().collect(Collectors.toList()),
                List.of("A", "B", "C", "D", "E", "F", "G"));
        assertEquals(
                chain.getBranches().stream()
                        .flatMap(branch -> branch.getMenu().stream().map(Item::getName))
                        .sorted()
                        .collect(Collectors.toList()),
                List.of("1", "2", "3", "4", "5", "6", "7", "8"));
    }
}
