/*
 * NTU SC2002 project
 * Branch
 * Unit Test
 */

package sg.edu.ntu.sc2002;

import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.naming.LimitExceededException;
import org.junit.jupiter.api.Test;

public class BranchTest {
    // junit creates a new instance of branch for each test
    Branch branch = new Branch("test", "test", 1);

    @Test
    public void assignStaff() throws LimitExceededException {
        branch.assign(new User("phil", new StaffRole()));
    }

    @Test
    public void assignManager() throws LimitExceededException {
        branch.assign(new User("phil", new ManagerRole()));
    }

    @Test
    public void assignBadRole() throws LimitExceededException {
        assertThrows(
                IllegalArgumentException.class,
                () -> branch.assign(new User("phil", new CustomerRole())));
    }

    @Test
    public void assignStaffExceedQuota() throws LimitExceededException {
        branch.assign(new User("john", new StaffRole()));
        assertThrows(
                LimitExceededException.class,
                () -> branch.assign(new User("john", new StaffRole())));
    }

    @Test
    public void assignManagerExceedQuota() throws LimitExceededException {
        branch.assign(new User("john", new StaffRole()));

        branch.assign(new User("phil", new ManagerRole()));
        assertThrows(
                LimitExceededException.class,
                () -> branch.assign(new User("phil", new ManagerRole())));
    }
}
