/*
 * NTU SC2002 project
 * Customer Role
 */
package sg.edu.ntu.sc2002;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class CustomerRole implements Role {
    @Override
    public char code() {
        // TODO Auto-generated method stub
        return 'C';
    }

    public static List<CustomerAction> getOrderAction() {

        return new ArrayList<CustomerAction>(
            Arrays.asList(
                new CustomerOrderMethod(CustomerOrderMethod.ADD_TO_CART),
                new CustomerOrderMethod(CustomerOrderMethod.REMOVE_FROM_CART),
                new CustomerOrderMethod(CustomerOrderMethod.VIEW_CART)
            )
        );
    }

    public static List<CustomerAction> getPaymentAction() {

        return new ArrayList<CustomerAction>(
            Arrays.asList(
                new CustomerPaymentAction(CustomerPaymentMethod.PAY)
            )
        );
    }

    public static List<CustomerAction> getCollectAction() {

        return new ArrayList<CustomerAction>(
            Arrays.asList(
                new CustomerCollectAction(CustomerCollectMethod.VIEW_ORDER_STATUS),
                new CustomerCollectAction(CustomerCollectMethod.COLLECT)
            )
        );
    }
}
