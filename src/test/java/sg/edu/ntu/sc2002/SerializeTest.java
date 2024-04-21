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
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SerializeTest {
    private Chain chain = Init.initChain();

    @BeforeEach
    public void setup() {
        Branch branch = chain.getBranches().iterator().next();
        branch.getOrders()
                .putAll(
                        Stream.of(
                                        new Order(
                                                new ArrayList<>(branch.getMenu()),
                                                DiningOption.DINE_OUT))
                                .collect(Collectors.toMap(o -> o.getId(), Function.identity())));
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
