/*
 * NTU SC2002 project
 * Gender
 */

package sg.edu.ntu.sc2002;

/** Enumeration for different genders. */
public enum Gender {
    MALE,
    FEMALE,
    UNKNOWN;

    /** 
     * Instantiates the relevant Gender based on the user char input.
     * 
     * @param code Type of gender.
     * @return Gender enum.
     */
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
