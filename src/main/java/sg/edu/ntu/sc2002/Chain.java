/*
 * NTU SC2002 project
 * Fast Food Chain
 */

package sg.edu.ntu.sc2002;

import java.util.Set;

/** Defines a Fast Food Restaurant Chain */
public class Chain {
    private User admin;
    Set<User> staff;
    Set<Branch> branches;
    Set<PaymentMethod> paymentMethods;

    public Chain(
            User admin, Set<User> staff, Set<Branch> branches, Set<PaymentMethod> paymentMethods) {
        this.admin = admin;
        this.staff = staff;
        this.branches = branches;
        this.paymentMethods = paymentMethods;
    }

    public Set<User> getStaff() {
        return staff;
    }

    public Set<Branch> getBranches() {
        return branches;
    }

    public Set<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }
}
