/*
 * NTU SC2002 project
 * Item
 */

package sg.edu.ntu.sc2002;

import java.io.Serializable;

/** Defines a Food Item offered by the Fast Food Chain */
public record Item(
        String name, double price, String description, boolean available, String category)
        implements Serializable {
    private static final long serialVersionUID = 1L;
}
