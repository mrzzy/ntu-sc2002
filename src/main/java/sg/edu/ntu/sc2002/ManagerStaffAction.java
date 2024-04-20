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

    public void listStaffAll(Scanner in, Branch branch) {
        for (User staff : branch.getStaffs()) {
            System.out.println(String.format("Name: %s, Age: %d", staff.getName(), staff.getAge()));
        }
    }
}