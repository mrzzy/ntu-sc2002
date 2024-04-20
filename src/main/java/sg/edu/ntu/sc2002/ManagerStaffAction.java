/*
 * NTU SC2002 project
 * Manager Staff Action
 */
package sg.edu.ntu.sc2002;

import java.util.Scanner;

public class ManagerStaffAction implements ManagerAction {
    private ManagerStaffMethod method;

    public ManagerStaffAction(ManagerStaffMethod method) {
        this.method = method;
    }

    @Override
    public String title() {
        switch (method) {
            case LIST_STAFF_ALL:
                return "List All Staff";
            default: // Last possible case
                return "";
        }
    }

    public void listStaffAll(Scanner in, Branch branch) {
        for (User staff : branch.getStaffs()) {
            System.out.println(String.format("Name: %s, Age: %d", staff.getName(), staff.getAge()));
        }
    }

    @Override
    public Branch execute(Scanner in, Branch branch) {
        switch (this.method) {
            case LIST_STAFF_ALL -> listStaffAll(in, branch);
        }

        return branch;
    }
}