/*
 * NTU SC2002 project
 * Init
 */

package sg.edu.ntu.sc2002;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.naming.LimitExceededException;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;

/** Implements reading of initial Fast Food Chain state from Excel workbooks in resources. */
public class Init {
    private static final Map<String, Role> ROLES =
            Map.of(
                    "S", new StaffRole(),
                    "M", new ManagerRole(),
                    "A", new AdminRole(),
                    "C", new CustomerRole());

    /**
     * Read initial Fast Food Chain state from initial Excel Workbooks in resources.
     *
     * @return Initial Fast Food Chain state.
     */
    public static Chain initChain() {
        Map<String, Branch> branches = parseBranches(Init.readInitWorkbook("branch_list.xlsx"));
        Map<String, Set<Item>> menus = parseMenus(Init.readInitWorkbook("menu_list.xlsx"));
        Map<String, Set<User>> staffs = parseStaffs(Init.readInitWorkbook("staff_list.xlsx"));
        // add available payment methods
        HashSet<PaymentMethod> paymentMethods = new HashSet<PaymentMethod>();
        paymentMethods.add(new PaypalMethod());
        paymentMethods.add(new BankCardMethod());

        // assign menus & staffs to branches
        branches.forEach(
                (branchName, branch) -> {
                    branch.getMenu().addAll(menus.get(branchName));
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
                });

        // admin user has no branch
        User admin = staffs.get("").iterator().next();
        Map<String, User> staffsByName =
                staffs.values().stream()
                        .flatMap(Set::stream)
                        .collect(Collectors.toMap(User::getUsername, Function.identity()));

        return new Chain(admin, staffsByName, new HashSet<>(branches.values()), paymentMethods);
    }

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

    protected static List<Row> readInitWorkbook(String path) {
        // read init data excel workbooks
        try {
            InputStream stream = Init.class.getResourceAsStream(path);
            ReadableWorkbook workbook = new ReadableWorkbook(stream);
            List<Row> rows =
                    workbook.getFirstSheet()
                            .openStream()
                            .filter(row -> row.getFirstNonEmptyCell().isPresent())
                            .collect(Collectors.toList());
            workbook.close();
            return rows;
        } catch (IOException e) {
            throw new RuntimeException("Unexpected exception reading init excel" + " files", e);
        }
    }
}
