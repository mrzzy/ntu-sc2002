/*
 * NTU SC2002 project
 * Item
 */

package sg.edu.ntu.sc2002;

import java.io.Serializable;

/** Defines a Food Item offered by the Fast Food Chain */
public class Item implements Serializable {
    /** item name */
    private String name;

    /** item price */
    private double price;

    /** item description */
    private String description;

    /** item customisation */
    private String customisation;

    /** item availability */
    private boolean available;

    /** item category */
    private String category;

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor when an Item object is created under {@link ManagerMenuAction}. Customisation is
     * set to empty string as default.
     *
     * @param name
     * @param price
     * @param description
     * @param available
     * @param category
     */
    public Item(String name, double price, String description, boolean available, String category) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.customisation = "";
        this.available = available;
        this.category = category;
    }

    /**
     * Constructor when an Item object is created under {@link CustomerOrderAction}
     *
     * @param name
     * @param price
     * @param description
     * @param customisation
     * @param available
     * @param category
     */
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

    /**
     * @return Name of item
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return Price of item
     */
    public Double getPrice() {
        return this.price;
    }

    /**
     * @return Description of item
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @return Customisation of item
     */
    public String getCustomisation() {
        return this.customisation;
    }

    /**
     * @return Availability of item
     */
    public boolean getAvailable() {
        return this.available;
    }

    /**
     * @return Category of item
     */
    public String getCategory() {
        return this.category;
    }

    // setters
    /**
     * Update the price of the item.
     *
     * @param newPrice New item price.
     */
    public void setPrice(double newPrice) {
        this.price = newPrice;
    }

    /**
     * Update the description of the item.
     *
     * @param newDescription New item description.
     */
    public void setDescription(String newDescription) {
        this.description = newDescription;
    }

    /**
     * Update the available of the item.
     *
     * @param newAvailable New item available.
     */
    public void setAvailable(boolean newAvailable) {
        this.available = newAvailable;
    }

    /**
     * Update the customisation of the item.
     *
     * @param newCustomisation New item customisation.
     */
    public void setCustomisation(String newCustomisation) {
        this.customisation = newCustomisation;
    }

    /**
     * Create a copy of the item to be added to the item array list {@link Cart}.
     *
     * @return Item copy.
     */
    public Item copy() {
        return new Item(
                this.name,
                this.price,
                this.description,
                this.customisation,
                this.available,
                this.category);
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
