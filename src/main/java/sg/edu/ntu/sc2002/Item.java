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

    public Item(String name, double price, boolean available, String category){
        this.name = name;
        this.price = price;
        this.description = "";
        this.available = available;
        this.category = category;
    }

    public Item(String name, double price, String description, boolean available, String category){
        this.name = name;
        this.price = price;
        this.description = description;
        this.available = available;
        this.category = category;
    }

    // getters

    public String getName(){
        return this.name;
    }

    public Double getPrice(){
        return this.price;
    }

    public String getDescription(){
        return this.description;
    }

    public boolean getAvailable(){
        return this.available;
    }

    public String getCategory(){
        return this.category;
    }

    //setters

    public void setPrice(double newPrice){
        this.price = newPrice;
    }

    public void setAvailable(boolean newAvailable){
        this.available = newAvailable;
    }

    public void setDescription(String newDescription){
        this.description = newDescription;
    }

    public Item copy(){
        return new Item(this.name,
                        this.price,
                        this.available,
                        this.category
        );
    }
}
