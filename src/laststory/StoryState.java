package laststory;

import java.util.HashMap;
import java.util.Map;

/**
 * Tracks branching-story flags and hospital-wide totals across the
 * seven days. Scenes read and write this object instead of using
 * scattered booleans, which is what lets Day 6/7 react to choices
 * made all the way back on Day 1.
 */
public class StoryState {

    private int day = 1;
    private final Map<String, Boolean> flags = new HashMap<>();

    private int patientsSaved = 0;
    private int patientsLost = 0;
    private int patientsTotal = 0;

    private boolean evidenceCollected = false;

    public int getDay() {
        return day;
    }

    public void nextDay() {
        day++;
    }

    public void setFlag(String key) {
        flags.put(key, true);
    }

    public void clearFlag(String key) {
        flags.put(key, false);
    }

    public boolean hasFlag(String key) {
        return flags.getOrDefault(key, false);
    }

    public void recordPatientOutcome(boolean saved) {
        patientsTotal++;
        if (saved) {
            patientsSaved++;
        } else {
            patientsLost++;
        }
    }

    public int getPatientsSaved() {
        return patientsSaved;
    }

    public int getPatientsLost() {
        return patientsLost;
    }

    public int getPatientsTotal() {
        return patientsTotal;
    }

    public double getSaveRate() {
        if (patientsTotal == 0) return 1.0;
        return (double) patientsSaved / patientsTotal;
    }

    public boolean isEvidenceCollected() {
        return evidenceCollected;
    }

    public void setEvidenceCollected(boolean value) {
        this.evidenceCollected = value;
    }
}
