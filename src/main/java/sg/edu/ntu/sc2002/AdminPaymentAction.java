package sg.edu.ntu.sc2002;

import java.util.Scanner;

/** Implementation of the {@link AdminAction} interface. */
public class AdminPaymentAction implements AdminAction {
    private AdminPaymentMethod method;

    public AdminPaymentAction(AdminPaymentMethod method) {
        this.method = method;
    }

    /**
     * Title of the Action displayed in the user interface.
     *
     * @return Title of the action.
     */
    @Override
    public String title() {
        switch (method) {
            case ADD_PAYMENT:
                return "Add Payment Method";
            case REMOVE_PAYMENT:
                return "Remove Payment Method";
            default: // Last possible case
                return "";
        }
    }

    private void addPayment(Scanner in, Chain chain) {
        System.out.println("Add payment");

        System.out.println("Here are the currently available payment methods:");
        for (PaymentMethod paymentMethod : chain.getPaymentMethods()) {
            System.out.println(paymentMethod.getName());
        }
        System.out.println("Here is all the payment methods we support");
        System.out.println("PayNow");
        System.out.println("Paypal");
        System.out.println("BankCard");

        System.out.println("Please enter payment method name");
        String paymentName = in.next();

        for (PaymentMethod paymentMethod : chain.getPaymentMethods()) {
            if (paymentMethod.getName().equals(paymentName)) {
                System.out.println("Payment method already exists!");
                return;
            }
        }

        switch (paymentName) {
            case "PayNow":
                {
                    chain.getPaymentMethods().add(new PayNowMethod());
                    System.out.printf("Added %s as a new payment method\n", paymentName);
                    break;
                }
            case "Paypal":
                {
                    chain.getPaymentMethods().add(new PaypalMethod());
                    System.out.printf("Added %s as a new payment method\n", paymentName);
                    break;
                }
            case "BankCard":
                {
                    chain.getPaymentMethods().add(new BankCardMethod());
                    System.out.printf("Added %s as a new payment method\n", paymentName);
                    break;
                }
            default:
                System.out.printf(
                        "We do not support adding %s as a new payment method right now!\n",
                        paymentName);
                break;
        }
    }

    private void removePayment(Scanner in, Chain chain) {
        System.out.println("Remove payment");

        System.out.println("Please select which payment method you want to remove");
        System.out.println("Here are the available payment methods:");
        for (PaymentMethod paymentMethod : chain.getPaymentMethods()) {
            System.out.println(paymentMethod.getName());
        }

        String paymentName = in.next();
        PaymentMethod selectedPaymentMethod = null;
        for (PaymentMethod paymentMethod : chain.getPaymentMethods()) {
            if (paymentMethod.getName().equals(paymentName)) {
                selectedPaymentMethod = paymentMethod;
                break;
            }
        }

        if (selectedPaymentMethod == null) {
            System.out.println("Selected payment name does not exist!");
            return;
        }

        chain.getPaymentMethods().remove(selectedPaymentMethod);
        System.out.printf(
                "Removed %s from existing payment methods\n", selectedPaymentMethod.getName());
    }

    /**
     * Execute Action on the given Fast Food Chain.
     *
     * @param in Stdin scanner used by action to read user input.
     * @param chain Fast Food Chain to perform the action on.
     * @return State of Fast Food Chain post performing action.
     */
    @Override
    public Chain execute(Scanner in, Chain chain) {
        switch (this.method) {
            case ADD_PAYMENT -> addPayment(in, chain);
            case REMOVE_PAYMENT -> removePayment(in, chain);
        }
        return chain;
    }
}
