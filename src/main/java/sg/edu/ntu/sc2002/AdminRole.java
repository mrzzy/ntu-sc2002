package sg.edu.ntu.sc2002;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Implementation of the {@link IRole} interface. */
public class AdminRole implements IRole {
    /**
     * Get the code that uniquely identifies the role
     *
     * @return Single character code for role.
     */
    @Override
    public char code() {
        return 'A';
    }

    /**
     * Get actions that the role is authorised to take.
     *
     * @return Set of actions that the role is authorised to take.
     */
    public static List<IAdminAction> getTransferAction() {
        return new ArrayList<IAdminAction>(
                Arrays.asList(new AdminTransferAction(AdminTransferMethod.TRANSFER_STAFF)));
    }

    public static List<IAdminAction> getPromotionAction() {
        return new ArrayList<IAdminAction>(
                Arrays.asList(
                        new AdminPromotionAction(AdminPromotionMethod.ASSIGN_MANAGER),
                        new AdminPromotionAction(AdminPromotionMethod.PROMOTE_STAFF)));
    }

    public static List<IAdminAction> getStaffAction() {
        return new ArrayList<IAdminAction>(
                Arrays.asList(
                        new AdminStaffAction(AdminStaffMethod.ADD_STAFF),
                        new AdminStaffAction(AdminStaffMethod.EDIT_STAFF),
                        new AdminStaffAction(AdminStaffMethod.REMOVE_STAFF),
                        new AdminStaffAction(AdminStaffMethod.LIST_STAFF_ALL)));
    }

    public static List<IAdminAction> getPaymentAction() {
        return new ArrayList<IAdminAction>(
                Arrays.asList(
                        new AdminPaymentAction(AdminPaymentMethod.ADD_PAYMENT),
                        new AdminPaymentAction(AdminPaymentMethod.REMOVE_PAYMENT)));
    }

    public static List<IAdminAction> getBranchAction() {
        return new ArrayList<IAdminAction>(
                Arrays.asList(
                        new AdminBranchAction(AdminBranchMethod.OPEN_BRANCH),
                        new AdminBranchAction(AdminBranchMethod.CLOSE_BRANCH)));
    }
}
