/*
 * NTU SC2002 project
 * Fast Food Chain
 */

package sg.edu.ntu.sc2002;

import java.util.Map;
import java.util.Set;

/** Defines a Fast Food Restaurant Chain */
public class Chain {
    private User admin;
    private Map<String, User> staffs;
    private Set<Branch> branches;
    private Set<PaymentMethod> paymentMethods;

    public Chain(
            User admin,
            Map<String, User> staffs,
            Set<Branch> branches,
            Set<PaymentMethod> paymentMethods) {
        this.admin = admin;
        this.staffs = staffs;
        this.branches = branches;
        this.paymentMethods = paymentMethods;
    }

    public User getAdmin() {
        return admin;
    }

    public Set<Branch> getBranches() {
        return branches;
    }

    public Set<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public Map<String, User> getStaffs() {
        return staffs;
    }
}
