/*
 * NTU SC2002 project
 * Customer Role
 */
package sg.edu.ntu.sc2002;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomerRole implements Role {
    /**
     * Get the code that uniquely identifies the role
     *
     * @return Single character code for role.
     */
    @Override
    public char code() {
        // TODO Auto-generated method stub
        return 'C';
    }

    /**
     * Get type of actions that the role is authorised to take.
     *
     * @return List of actions that the role is authorised to take.
     */
    public static List<CustomerAction> getOrderAction() {
        CustomerOrderAction.myCart = new Cart();
        CustomerOrderAction.totalPrice = 0;
        return new ArrayList<CustomerAction>(
                Arrays.asList(
                        new CustomerOrderAction(CustomerOrderMethod.ADD_TO_CART),
                        new CustomerOrderAction(CustomerOrderMethod.REMOVE_FROM_CART),
                        new CustomerOrderAction(CustomerOrderMethod.CUSTOMISE_ITEM_IN_CART),
                        new CustomerOrderAction(CustomerOrderMethod.VIEW_CART),
                        new CustomerOrderAction(CustomerOrderMethod.PAY)));
    }

    /**
     * Get type of actions that the role is authorised to take.
     *
     * @return List of actions that the role is authorised to take.
     */
    public static List<CustomerAction> getCollectAction() {

        return new ArrayList<CustomerAction>(
                Arrays.asList(
                        new CustomerCollectAction(CustomerCollectMethod.VIEW_ORDER_STATUS),
                        new CustomerCollectAction(CustomerCollectMethod.COLLECT)));
    }
}
