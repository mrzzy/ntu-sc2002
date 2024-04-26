/*
 * NTU SC2002 project
 * Fast Food Chain
 */

package sg.edu.ntu.sc2002;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/** Defines a Fast Food Restaurant Chain */
public class Chain implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Admin user of the Fast Food Chain */
    private User admin;

    /** Staffs of the Fast Food Chain */
    private Map<String, User> staffs;

    /** Branches of the Fast Food Chain */
    private Set<Branch> branches;

    /** Payment methods of the Fast Food Chain */
    private Set<IPaymentMethod> paymentMethods;

    public Chain(
            User admin,
            Map<String, User> staffs,
            Set<Branch> branches,
            Set<IPaymentMethod> paymentMethods) {
        this.admin = admin;
        this.staffs = staffs;
        this.branches = branches;
        this.paymentMethods = paymentMethods;
    }

    /**
     * Get the admin user of the Fast Food Chain
     * 
     * @return Admin user of the Fast Food Chain
     */
    public User getAdmin() {
        return admin;
    }

    /**
     * Get the branches of the Fast Food Chain
     * 
     * @return Branches of the Fast Food Chain
     */
    public Set<Branch> getBranches() {
        return branches;
    }

    /**
     * Get the payment methods of the Fast Food Chain
     * 
     * @return Payment methods of the Fast Food Chain
     */
    public Set<IPaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    /**
     * Get the staffs of the Fast Food Chain
     * 
     * @return Staffs of the Fast Food Chain
     */
    public Map<String, User> getStaffs() {
        return staffs;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((admin == null) ? 0 : admin.hashCode());
        result = prime * result + ((staffs == null) ? 0 : staffs.hashCode());
        result = prime * result + ((branches == null) ? 0 : branches.hashCode());
        result = prime * result + ((paymentMethods == null) ? 0 : paymentMethods.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Chain other = (Chain) obj;
        if (admin == null) {
            if (other.admin != null)
                return false;
        } else if (!admin.equals(other.admin))
            return false;
        if (staffs == null) {
            if (other.staffs != null)
                return false;
        } else if (!staffs.equals(other.staffs))
            return false;
        if (branches == null) {
            if (other.branches != null)
                return false;
        } else if (!branches.equals(other.branches))
            return false;
        if (paymentMethods == null) {
            if (other.paymentMethods != null)
                return false;
        } else if (!paymentMethods.equals(other.paymentMethods))
            return false;
        return true;
    }
}
