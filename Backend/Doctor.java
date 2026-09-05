package laststory;

/**
 * A staff doctor. In this mini-project a Doctor simply represents
 * one unit of treatment capacity for the Hospital during a shift.
 */
public class Doctor {

    private final String name;
    private final String specialty;

    public Doctor(String name, String specialty) {
        this.name = name;
        this.specialty = specialty;
    }

    public String getName() {
        return name;
    }

    public String getSpecialty() {
        return specialty;
    }

    @Override
    public String toString() {
        return name + " (" + specialty + ")";
    }
}
