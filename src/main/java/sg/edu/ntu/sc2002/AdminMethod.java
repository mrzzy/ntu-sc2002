package sg.edu.ntu.sc2002;

public enum AdminMethod {
    ADD_STAFF, // Add new staff
    EDIT_STAFF, // Edit existing staff
    REMOVE_STAFF, // Remove a staff
    LIST_STAFF_ALL, // Filter by branch/role/gender/age
    ASSIGN_MANAGER, // Assign managers to each branch within quota constraint
    PROMOTE_STAFF, // Promote staff to a branch manager
    TRANSFER_STAFF, // Transfer a staff/manager among branches
    ADD_PAYMENT, // Add a payment method
    REMOVE_PAYMENT, // Remove a payment method
    OPEN_BRANCH, // Open branch
    CLOSE_BRANCH, // Close branch
}
