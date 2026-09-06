package laststory;

public enum Ending {

    HERO("THE HERO", """
            You expose Project Orpheus AND keep St. Mary's alive.
            The government takes over the research program; the
            treatment is eventually redesigned and becomes legitimate.

            Maya walks into your office.
              MAYA: "Ready for another impossible week?"
              ALEX: "Absolutely not."
            Maya laughs. The phone rings.
              ALEX: "...I'll get it."
            """),

    TRUTH("THE TRUTH", """
            Alex exposes Project Orpheus. The hospital is investigated
            and Blackwood is arrested. St. Mary's closes for a time,
            then reopens under new management. Maya and Carter return.

            You couldn't save everyone. But you stopped the people
            who were willing to sacrifice others for money.
            Some battles aren't won by saving a life -
            they're won by refusing to let others be sacrificed.
            """),

    DEAL("THE DEAL", """
            Alex accepts the promotion and becomes head of emergency
            medicine. Salary up. Hospital profitable. Project Orpheus
            continues.

              BLACKWOOD: "Congratulations, Doctor."

            Alex looks through the glass at the emergency room.
            A new patient is wheeled in. A familiar blood-test result
            appears on the screen. Alex knows exactly what it means.
            """),

    SILENCE("THE SILENCE", """
            You destroy the evidence. The hospital survives. Your
            colleagues survive. But you know what happened.

            Maya eventually leaves. Carter retires. Years later, a new
            doctor asks: "Was this always how things were?"
            Alex looks at them. "No." "What happened?"
            Alex doesn't answer.
            """),

    COLLAPSE("HOSPITAL COLLAPSE", """
            Too many patients were lost. Reputation and funding ran out.
            The government shuts down the emergency department.

            The investigation was never completed. Whatever was
            happening at St. Mary's remained buried. For now.
            """);

    private final String title;
    private final String text;

    Ending(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

   
    public static Ending resolve(Player player, StoryState story, int finalChoice) {
        boolean collapsed = player.getReputation() < 20
                || player.getMoney() <= 0
                || story.getSaveRate() < 0.4;

        if (collapsed) {
            return COLLAPSE;
        }

        if (finalChoice == 4) {
            return DEAL;
        }

        if (finalChoice == 3) {
            return SILENCE;
        }

    
        boolean heroRequirements =
                player.getInvestigation() >= 80
                        && story.isEvidenceCollected()
                        && player.getMayaTrust() >= 70
                        && player.getCarterTrust() >= 70
                        && story.getSaveRate() >= 0.9
                        && player.getReputation() >= 80;

        return heroRequirements ? HERO : TRUTH;
    }
}
