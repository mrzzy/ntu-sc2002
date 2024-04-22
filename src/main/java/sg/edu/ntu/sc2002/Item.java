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
    private String customisation;
    private boolean available;
    private String category;
    private static final long serialVersionUID = 1L;

    public Item(String name, double price, String description, boolean available, String category) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.customisation = "";
        this.available = available;
        this.category = category;
    }

    public Item(
            String name,
            double price,
            String description,
            String customisation,
            boolean available,
            String category) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.customisation = customisation;
        this.available = available;
        this.category = category;
    }

    // getters

    public String getName() {
        return this.name;
    }

    public Double getPrice() {
        return this.price;
    }

    public String getDescription() {
        return this.description;
    }

    public String getCustomisation() {
        return this.customisation;
    }

    public boolean getAvailable() {
        return this.available;
    }

    public String getCategory() {
        return this.category;
    }

    // setters

    public void setPrice(double newPrice) {
        this.price = newPrice;
    }

    public void setDescription(String newDescription) {
        this.description = newDescription;
    }

    public void setAvailable(boolean newAvailable) {
        this.available = newAvailable;
    }

    public void setCustomisation(String newCustomisation) {
        this.customisation = newCustomisation;
    }

    public Item copy() {
        return new Item(this.name, this.price, this.description, this.available, this.category);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        long temp;
        temp = Double.doubleToLongBits(price);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((customisation == null) ? 0 : customisation.hashCode());
        result = prime * result + (available ? 1231 : 1237);
        result = prime * result + ((category == null) ? 0 : category.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Item other = (Item) obj;
        if (name == null) {
            if (other.name != null) return false;
        } else if (!name.equals(other.name)) return false;
        if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price)) return false;
        if (description == null) {
            if (other.description != null) return false;
        } else if (!description.equals(other.description)) return false;
        if (customisation == null) {
            if (other.customisation != null) return false;
        } else if (!customisation.equals(other.customisation)) return false;
        if (available != other.available) return false;
        if (category == null) {
            if (other.category != null) return false;
        } else if (!category.equals(other.category)) return false;
        return true;
    }
}
