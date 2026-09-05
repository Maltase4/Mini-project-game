package laststory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * St. Mary's Hospital. Owns the doctors and runs the triage
 * mini-game that plays out at the start of every day.
 */
public class Hospital {

    private final List<Doctor> doctors = new ArrayList<>();
    private int bedsAvailable;

    public Hospital(Difficulty difficulty) {
        String[] names = {"Dr. Reyes", "Dr. Patel", "Dr. Kim", "Dr. Alvarez", "Dr. Novak", "Dr. Brooks"};
        String[] specialties = {"General", "Cardiology", "Surgery", "General", "Trauma", "Pediatrics"};
        for (int i = 0; i < difficulty.getDoctorCount(); i++) {
            doctors.add(new Doctor(names[i % names.length], specialties[i % specialties.length]));
        }
        this.bedsAvailable = difficulty.getDoctorCount() + 2;
    }

    public int getCapacity() {
        return doctors.size();
    }

    /**
     * Runs one shift: the player picks which patients to treat given
     * limited doctor capacity. Untreated CRITICAL/SERIOUS patients risk
     * dying, which hits reputation and the patientsSaved/Lost counters.
     */
    public void runShift(List<Patient> incoming, Player player, StoryState story, Scanner sc) {
        System.out.println();
        System.out.println("==================== HOSPITAL SHIFT ====================");
        System.out.println("Doctors on duty: " + doctors.size() + "   Beds available: " + bedsAvailable);
        System.out.println("Patients incoming: " + incoming.size());
        System.out.println("---------------------------------------------------------");
        for (int i = 0; i < incoming.size(); i++) {
            Patient p = incoming.get(i);
            System.out.printf("[%d] %-16s | %-18s | %s%n", i + 1, p.getName(), p.getCondition(), p.getSeverity());
        }
        System.out.println("---------------------------------------------------------");

        int capacity = Math.min(doctors.size(), bedsAvailable);
        List<Patient> chosen = new ArrayList<>();

        while (chosen.size() < capacity && chosen.size() < incoming.size()) {
            System.out.println("Choose a patient to treat (" + chosen.size() + "/" + capacity
                    + " doctors assigned). Enter patient number, or 0 to stop assigning:");
            int pick = readInt(sc, 0, incoming.size());
            if (pick == 0) break;
            Patient p = incoming.get(pick - 1);
            if (chosen.contains(p)) {
                System.out.println("Already assigned a doctor to " + p.getName() + ".");
                continue;
            }
            chosen.add(p);
            System.out.println("-> A doctor is now treating " + p.getName() + ".");
        }

        System.out.println();
        System.out.println("--- End of shift results ---");
        for (Patient p : incoming) {
            if (chosen.contains(p)) {
                p.setStatus(PatientStatus.RECOVERING);
                story.recordPatientOutcome(true);
                System.out.println(p.getName() + " (" + p.getSeverity() + ") was treated and is stable.");
            } else {
                boolean survivesAnyway = survivesWithoutTreatment(p.getSeverity());
                if (survivesAnyway) {
                    p.setStatus(PatientStatus.RECOVERING);
                    story.recordPatientOutcome(true);
                    System.out.println(p.getName() + " (" + p.getSeverity() + ") wasn't treated in time but stabilized on their own.");
                } else {
                    p.setStatus(PatientStatus.DEAD);
                    story.recordPatientOutcome(false);
                    player.changeReputation(-6);
                    System.out.println(p.getName() + " (" + p.getSeverity() + ") did not survive the wait. Reputation -6.");
                }
            }
        }

        int treatedCount = chosen.size();
        int income = treatedCount * 300;
        player.addMoney(income);
        if (treatedCount == incoming.size()) {
            player.changeReputation(2);
        }
        System.out.println();
        System.out.println("Billing income from today's shift: $" + income);
    }

    private boolean survivesWithoutTreatment(Severity severity) {
        double surviveChance;
        switch (severity) {
            case MINOR -> surviveChance = 0.98;
            case MODERATE -> surviveChance = 0.85;
            case SERIOUS -> surviveChance = 0.45;
            case CRITICAL -> surviveChance = 0.15;
            default -> surviveChance = 0.5;
        }
        return Math.random() < surviveChance;
    }

    private int readInt(Scanner sc, int min, int max) {
        while (true) {
            String line = sc.nextLine().trim();
            try {
                int value = Integer.parseInt(line);
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
                // fall through to reprompt
            }
            System.out.print("Please enter a number between " + min + " and " + max + ": ");
        }
    }
}
