/*
 * NTU SC2002 project
 * Gender
 */

package sg.edu.ntu.sc2002;

public enum Gender {
    MALE,
    FEMALE,
    UNKNOWN;

    public static char toCode(Gender gender) {
        switch (gender) {
            case MALE:
                return 'M';
            case FEMALE:
                return 'F';
            default:
                return 'U';
        }
    }

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
