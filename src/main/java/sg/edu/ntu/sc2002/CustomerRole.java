/*
 * NTU SC2002 project
 * Customer Role
 */
package sg.edu.ntu.sc2002;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomerRole implements Role {

    @Override
    public char code() {
        // TODO Auto-generated method stub
        return 'C';
    }

    public static List<CustomerAction> getOrderAction() {
        CustomerOrderAction.myCart = new Cart();
        CustomerOrderAction.totalPrice = 0;
        return new ArrayList<CustomerAction>(
                Arrays.asList(
                        new CustomerOrderAction(CustomerOrderMethod.ADD_TO_CART),
                        new CustomerOrderAction(CustomerOrderMethod.REMOVE_FROM_CART),
                        new CustomerOrderAction(CustomerOrderMethod.VIEW_CART),
                        new CustomerOrderAction(CustomerOrderMethod.PAY)));
    }

    public static List<CustomerAction> getCollectAction() {

        return new ArrayList<CustomerAction>(
                Arrays.asList(
                        new CustomerCollectAction(CustomerCollectMethod.VIEW_ORDER_STATUS),
                        new CustomerCollectAction(CustomerCollectMethod.COLLECT)));
    }
}
