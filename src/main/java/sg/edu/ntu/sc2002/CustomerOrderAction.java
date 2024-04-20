package sg.edu.ntu.sc2002;

import java.util.Scanner;
import java.util.Set;

public class CustomerOrderAction implements CustomerAction{
    private CustomerOrderMethod method;
    private Cart myCart;

    @Override
    public String title() {
        switch (method) {
            case ADD_TO_CART:
                return "Add Item to Cart";
            case REMOVE_FROM_CART:
                return "Remove Item from Cart";
            case VIEW_CART:
                return "View Cart";
            case PAY:
                return "Make Payment";
            default: // Last possible case
                return "";
        }
    }

    public CustomerOrderAction(CustomerOrderMethod method){
        this.method = method;
        this.myCart = new Cart();
    }

    public void viewCart(){
        myCart.viewCart();
    }

    public boolean addToCart(Scanner in, Set<Item> menu){
        for (Item item:menu){
            if (item.getAvailable()){
                System.out.println(String.format("Name: %s, Price: %f, Description: %s, Category: %s", item.getName(), item.getPrice(), item.getDescription(), item.getCategory()));
            }
        }
        System.out.println("Type item name to add:");
        String itemName = in.nextLine();

        System.out.println("Any customisation?");
        String itemDescription = in.nextLine();

        Item itemToAdd = null;
        for (Item item:menu){
            if (itemName.equals(item.getName())){
                itemToAdd = item.copy();
                itemToAdd.setDescription(itemDescription);
                break;
            }
        }
        if (itemToAdd != null){
            myCart.addCart(itemToAdd);
            System.out.println("Item added successfully.");
            return true;
        } else {
            System.out.println("Item not added.");
            return false;
        }
    }

    public boolean removeFromCart(Scanner in){
        int i = 1;
        for (Item item:myCart.getCart()){
            System.out.println(String.format("%d: Name: %s, Price: %f, Description: %s, Category: %s", i++, item.getName(), item.getPrice(), item.getDescription(), item.getCategory()));
        }
        System.out.println("Type item number to remove:");
        int itemIndex = in.nextInt();

        if (itemIndex < 0 || itemIndex >= i){
            System.out.println("Invalid index. Try again.");
            return false;
        }

        myCart.removeCart(itemIndex-1);
        return true;
    }

    public void pay(Scanner in, Branch branch, Set<PaymentMethod> paymentMethods){

    }

    @Override
    public Branch execute(Scanner in, Branch branch, Set<PaymentMethod> paymentMethods) {
        switch (this.method) {
            case ADD_TO_CART -> addToCart(in, branch.getMenu());
            case REMOVE_FROM_CART -> removeFromCart(in);
            case VIEW_CART -> viewCart();
            case PAY -> pay(in, branch, paymentMethods);
        }
        return branch;
    }
}
