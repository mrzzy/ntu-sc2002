/*
 * NTU SC2002 project
 * Init
 */

package sg.edu.ntu.sc2002;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.naming.LimitExceededException;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;

/** Initialises Fast Food {@link Chain} state. */
public class Init {
    private static final Map<String, IRole> ROLES =
            Map.of(
                    "S", new StaffRole(),
                    "M", new ManagerRole(),
                    "A", new AdminRole(),
                    "C", new CustomerRole());

    /**
     * Initialise Fast Food Chain state given arguments. Restores Fast Food Chain state if state
     * file exists. Otherwise it initializes state from Excel workbooks in resources. Overrides
     * Chain with user provided Excel workbooks if any.
     *
     * @param args User provided arguments on how state should be initialised.
     * @return Initialised Fast Food Chain state.
     */
    public static Chain initChain(Args args) {
        // read init chain state from resources
        Map<String, Branch> branches =
                parseBranches(Init.readExcel(Init.class.getResource("branch_list.xlsx").getPath()));
        Map<String, Set<Item>> menus =
                parseMenus(Init.readExcel(Init.class.getResource("menu_list.xlsx").getPath()));
        Map<String, Set<User>> staffs =
                parseStaffs(Init.readExcel(Init.class.getResource("staff_list.xlsx").getPath()));
        User admin = staffs.get("").iterator().next();

        // restore chain state if present
        if (Files.exists(Path.of(args.getStatePath()))) {
            Chain chain = Serialize.restore(args.getStatePath());
            branches =
                    chain.getBranches().stream()
                            .collect(Collectors.toMap(Branch::getName, Function.identity()));
            menus =
                    chain.getBranches().stream()
                            .collect(
                                    Collectors.toMap(
                                            Branch::getName,
                                            branch -> new HashSet<>(branch.getMenu())));
            staffs =
                    chain.getBranches().stream()
                            .collect(
                                    Collectors.toMap(
                                            Branch::getName,
                                            branch -> {
                                                Set<User> users = new HashSet<>(branch.getStaffs());
                                                users.addAll(branch.getManagers());
                                                return users;
                                            }));
            // admin belongs in empty branch
            admin = chain.getAdmin();
            staffs.put("", Set.of(admin));
        }

        // read user provided overrides if any
        if (args.getBranchPath().length() > 0) {
            branches = parseBranches(Init.readExcel(args.getBranchPath()));
        }
        if (args.getStaffPath().length() > 0) {
            staffs = parseStaffs(Init.readExcel(args.getStaffPath()));
        }
        if (args.getMenuPath().length() > 0) {
            menus = parseMenus(Init.readExcel(args.getMenuPath()));
        }

        // override menus & staff in branches
        branches = Init.overrideStaff(branches, staffs);
        branches = Init.overrideMenus(branches, menus);

        // construct chain
        Map<String, User> staffsByName =
                staffs.values().stream()
                        .flatMap(Set::stream)
                        .collect(Collectors.toMap(User::getUsername, Function.identity()));

        return new Chain(
                admin,
                staffsByName,
                new HashSet<>(branches.values()),
                Set.of(new PayNowMethod(), new BankCardMethod()));
    }

    /** Override branches with with given menus. */
    protected static Map<String, Branch> overrideMenus(
            Map<String, Branch> branches, Map<String, Set<Item>> menus) {
        branches.forEach(
                (branchName, branch) -> {
                    // override menus in branch
                    if (menus.containsKey(branchName)) {
                        branch.getMenu().clear();
                        branch.getMenu().addAll(menus.get(branchName));
                    }
                });
        return branches;
    }

    /** Override branches with with given staff. */
    protected static Map<String, Branch> overrideStaff(
            Map<String, Branch> branches, Map<String, Set<User>> staffs) {
        branches.forEach(
                (branchName, branch) -> {
                    // override staff in branch
                    if (staffs.containsKey(branchName)) {
                        branch.getStaffs().clear();
                        branch.getManagers().clear();
                        staffs.get(branchName)
                                .forEach(
                                        user -> {
                                            try {
                                                branch.assign(user);
                                            } catch (LimitExceededException e) {
                                                System.out.printf(
                                                        "Warning: Ignoring assignment that exceeds"
                                                                + " quota: user=%s, branch=%s\n",
                                                        user.getUsername(), branchName);
                                            }
                                        });
                    }
                });
        return branches;
    }

    /** Parse Staff from list of rows. */
    protected static Map<String, Set<User>> parseStaffs(List<Row> rows) {
        return rows.stream()
                // skip header row
                .skip(1)
                // group users by branch
                .collect(
                        Collectors.groupingBy(
                                row -> row.getCellAsString(5).orElse(""),
                                // parse user from row
                                Collectors.mapping(
                                        (Row row) ->
                                                new User(
                                                        row.getCellAsString(1).get(),
                                                        row.getCellAsString(0).get(),
                                                        row.getCellAsString(5).orElse(""),
                                                        row.getCellAsNumber(4).get().intValue(),
                                                        Gender.fromCode(
                                                                row.getCellAsString(3)
                                                                        .get()
                                                                        .charAt(0)),
                                                        User.DEFAULT_PASSWORD,
                                                        ROLES.get(row.getCellAsString(2).get())),
                                        Collectors.toSet())));
    }

    /** Parse Menus from list of rows. */
    protected static Map<String, Set<Item>> parseMenus(List<Row> rows) {
        // parse menu items from menu list
        return rows.stream()
                // skip header row
                .skip(1)
                .collect(
                        Collectors.groupingBy(
                                row -> row.getCellAsString(2).get(),
                                Collectors.mapping(
                                        row ->
                                                new Item(
                                                        row.getCellAsString(0).get(),
                                                        row.getCellAsNumber(1).get().doubleValue(),
                                                        "",
                                                        true,
                                                        row.getCellAsString(3).get()),
                                        Collectors.toSet())));
    }

    /** Parse Branches from list of rows. */
    protected static Map<String, Branch> parseBranches(List<Row> rows) {
        return rows.stream()
                // skip header row
                .skip(1)
                .collect(
                        Collectors.toMap(
                                row -> row.getCellAsString(0).get(),
                                row -> {
                                    return new Branch(
                                            row.getCellAsString(0).get(),
                                            row.getCellAsString(1).get(),
                                            row.getCellAsNumber(2).get().intValue(),
                                            new HashSet<>(),
                                            new HashSet<>(),
                                            new HashSet<>(),
                                            new ArrayList<>(),
                                            new ArrayList<>(),
                                            new ArrayList<>(),
                                            new ArrayList<>());
                                }));
    }

    /** Read Excel workbook at the given path as a list of rows. */
    protected static List<Row> readExcel(String path) {
        // read init data excel workbooks
        try {
            InputStream stream = new FileInputStream(path);
            ReadableWorkbook workbook = new ReadableWorkbook(stream);
            List<Row> rows =
                    workbook.getFirstSheet()
                            .openStream()
                            .filter(row -> row.getFirstNonEmptyCell().isPresent())
                            .collect(Collectors.toList());
            workbook.close();
            return rows;
        } catch (IOException e) {
            throw new RuntimeException(
                    "Unexpected exception reading init excel files: " + e.getMessage(), e);
        }
    }
}
