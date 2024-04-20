/*
 * NTU SC2002 project
 * Item
 */

package sg.edu.ntu.sc2002;

import java.io.Serializable;

/** Defines a Food Item offered by the Fast Food Chain */
public class Item implements Serializable {
    private String name;
    private double price;
    private String description;
    private boolean available;
    private String category;
    private static final long serialVersionUID = 1L;

    public Item(String name, double price, String description, boolean available, String category){
        this.name = name;
        this.price = price;
        this.description = description;
        this.available = available;
        this.category = category;
    }

    public setPrice(double newPrice){
        this.price = newPrice;
    }

    public setAvailable(boolean newAvailable){
        this.available = newAvailable;
    }
}
