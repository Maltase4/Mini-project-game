package laststory;

/**
 * Difficulty changes gameplay pressure, never the story itself.
 */
public enum Difficulty {
    EASY(25000, 5),
    NORMAL(15000, 4),
    HARD(10000, 3);

    private final int startingMoney;
    private final int doctorCount;

    Difficulty(int startingMoney, int doctorCount) {
        this.startingMoney = startingMoney;
        this.doctorCount = doctorCount;
    }

    public int getStartingMoney() {
        return startingMoney;
    }

    public int getDoctorCount() {
        return doctorCount;
    }
}
