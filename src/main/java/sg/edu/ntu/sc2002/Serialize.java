/*
 * NTU SC2002 project
 * Serialize
 */

package sg.edu.ntu.sc2002;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/** Serialization utilities for Fast Food {@link Chain} state. */
public class Serialize {
    /**
     * Save the given Chain object state at the given path.
     *
     * @param chain Fast Food {@link Chain} object to save.
     * @param path Path to serialization file to write object state to.
     */
    public static void save(Chain chain, String path) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
            out.writeObject(chain);
            out.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Could not open file for writing: " + path, e);
        } catch (IOException e) {
            throw new RuntimeException("IO Error writing stream header.", e);
        }
    }

    /**
     * Restore the given Chain object state from the given path.
     *
     * @param path Path to serialization to read object state from.
     * @return Fast Food {@link Chain} with restored object state.
     */
    public static Chain restore(String path) {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
            Chain chain = (Chain) in.readObject();
            in.close();
            System.out.println("Restored successfully");
            return chain;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Could not open file for writing: " + path, e);
        } catch (IOException e) {
            throw new RuntimeException("IO Error writing stream header.", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Could not decode class of object. ", e);
        }
    }
}
