/*
 * NTU SC2002 project
 * Gender
 * Unit Test
 */

package sg.edu.ntu.sc2002;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class GenderTest {
    @Test
    public void fromCode() {
        assertEquals(Gender.MALE, Gender.fromCode('M'));
        assertEquals(Gender.FEMALE, Gender.fromCode('F'));
        assertEquals(Gender.UNKNOWN, Gender.fromCode('?'));
    }
}
