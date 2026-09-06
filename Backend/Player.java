package laststory;


public class Player {

    private final String name;
    private int money;
    private int reputation;   // 0-100
    private int investigation; // 0-100
    private int mayaTrust;    // 0-100
    private int carterTrust;  // 0-100
    private int blackwoodRelationship; // 0-100

    public Player(String name, int startingMoney) {
        this.name = name;
        this.money = startingMoney;
        this.reputation = 50;
        this.investigation = 0;
        this.mayaTrust = 50;
        this.carterTrust = 50;
        this.blackwoodRelationship = 20;
    }

    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }

    public void addMoney(int amount) {
        this.money = Math.max(0, this.money + amount);
    }

    public int getReputation() {
        return reputation;
    }

    public void changeReputation(int delta) {
        this.reputation = clamp(this.reputation + delta);
    }

    public int getInvestigation() {
        return investigation;
    }

    public void changeInvestigation(int delta) {
        this.investigation = clamp(this.investigation + delta);
    }

    public int getMayaTrust() {
        return mayaTrust;
    }

    public void changeMayaTrust(int delta) {
        this.mayaTrust = clamp(this.mayaTrust + delta);
    }

    public int getCarterTrust() {
        return carterTrust;
    }

    public void changeCarterTrust(int delta) {
        this.carterTrust = clamp(this.carterTrust + delta);
    }

    public int getBlackwoodRelationship() {
        return blackwoodRelationship;
    }

    public void changeBlackwoodRelationship(int delta) {
        this.blackwoodRelationship = clamp(this.blackwoodRelationship + delta);
    }

    private int clamp(int value) {
        return Math.max(0, Math.min(100, value));
    }

    public void printStatus() {
        System.out.println("----------------------------------------------------");
        System.out.printf("  Money: $%-8d Reputation: %-4d Investigation: %d%n",
                money, reputation, investigation);
        System.out.printf("  Maya Trust: %-4d Carter Trust: %-4d Blackwood: %d%n",
                mayaTrust, carterTrust, blackwoodRelationship);
        System.out.println("----------------------------------------------------");
    }
}
