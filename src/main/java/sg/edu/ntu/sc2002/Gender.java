/*
 * NTU SC2002 project
 * Gender
 */

package sg.edu.ntu.sc2002;

public enum Gender {
    MALE,
    FEMALE,
    UNKNOWN;

    public static Gender fromCode(char code) {
        switch (code) {
            case 'M':
                return Gender.MALE;
            case 'F':
                return Gender.FEMALE;
            default:
                return Gender.UNKNOWN;
        }
    }
}
