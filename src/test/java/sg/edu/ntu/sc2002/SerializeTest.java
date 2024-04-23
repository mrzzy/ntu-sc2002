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
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SerializeTest {
    private Chain chain = Init.initChain(new Args());

    @BeforeEach
    public void setup() {
        Branch branch = chain.getBranches().iterator().next();
        branch.getNewOrderList()
                .add(new Order(new ArrayList<>(branch.getMenu()), DiningOption.DINE_OUT, 1));
    }

    @Test
    public void save() throws IOException {
        File saveFile = File.createTempFile("chain", "");
        Serialize.save(chain, saveFile.getPath());
        assertTrue(Files.size(Path.of(saveFile.getPath())) > 0);
        saveFile.delete();
    }

    @Test
    public void restore() throws IOException {
        File saveFile = File.createTempFile("chain", "");
        Serialize.save(chain, saveFile.getPath());

        Chain restoreChain = Serialize.restore(saveFile.getPath());
        assertEquals(chain, restoreChain);
        saveFile.delete();
    }
}
