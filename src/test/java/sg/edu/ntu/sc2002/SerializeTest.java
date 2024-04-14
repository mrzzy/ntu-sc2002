/*
 * NTU SC2002 project
 * Serialize
 * Unit Test
 */

package sg.edu.ntu.sc2002;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class SerializeTest {
    @Test
    public void save() throws IOException {
        File saveFile = File.createTempFile("chain", "");
        Serialize.save(Init.initChain(), saveFile.getPath());
        assertTrue(Files.size(Path.of(saveFile.getPath())) > 0);
        saveFile.delete();
    }

    @Test
    public void restore() throws IOException {
        File saveFile = File.createTempFile("chain", "");
        Chain saveChain = Init.initChain();
        Serialize.save(saveChain, saveFile.getPath());

        Chain restoreChain = Serialize.restore(saveFile.getPath());
        assertEquals(saveChain, restoreChain);
        saveFile.delete();
    }
}
