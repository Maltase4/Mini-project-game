package laststory;


public enum Severity {
    MINOR(1),
    MODERATE(2),
    SERIOUS(3),
    CRITICAL(4);

    private final int weight;

    Severity(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }
}
