package sg.edu.ntu.sc2002;

import java.util.Set;

public class ViewMenuAction {
    public static void viewMenu(Set<Item> menu){
        System.out.println("-------------------------");
        for (Item item : menu){
            System.out.println(String.format("Name: %s, Category: %s, Price: %.2f", item.getName(), item.getCategory(), item.getPrice()));
        }
    }
}
