package sg.edu.ntu.sc2002;

import java.util.Set;

public class ViewMenuAction {
    public static void viewMenu(Set<Item> menu){
        System.out.println("-------------------------");
        int i = 1;
        for (Item item : menu){
            System.out.println(String.format("%d) Name: %s, Category: %s, Price: %.2f, Description: %s", i++, item.getName(), item.getCategory(), item.getPrice(), item.getDescription()));
        }
    }
}
