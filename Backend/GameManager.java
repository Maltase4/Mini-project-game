package laststory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameManager {

    private final Scanner sc = new Scanner(System.in);
    private Player player;
    private Hospital hospital;
    private final StoryState story = new StoryState();

    public void play() {
        printTitle();
        Difficulty difficulty = chooseDifficulty();
        player = new Player("Alex Morgan", difficulty.getStartingMoney());
        hospital = new Hospital(difficulty);

        prologue();
        day1();
        day2();
        day3();
        day4();
        day5();
        day6();
        day7();

        System.out.println("\nThanks for playing THE LAST SHIFT.");
    }

  

    private void printTitle() {
        System.out.println("=========================================================");
        System.out.println("                    THE LAST SHIFT");
        System.out.println("      A 7-day hospital mystery - St. Mary's Hospital");
        System.out.println("=========================================================\n");
    }

    private Difficulty chooseDifficulty() {
        int pick = choice("Choose your difficulty:",
                "EASY   - more money, more doctors, easier cases",
                "NORMAL - balanced",
                "HARD   - tight budget, short-staffed, harsher cases");
        return switch (pick) {
            case 1 -> Difficulty.EASY;
            case 2 -> Difficulty.NORMAL;
            default -> Difficulty.HARD;
        };
    }



    private void prologue() {
        printScene("""
                It's raining. St. Mary's Hospital looks old - half the lights in
                the entrance are broken. A handwritten sign reads:
                "EMERGENCY DEPARTMENT - PLEASE BE PATIENT"

                Dr. Daniel Carter, the senior physician, barely looks up.
                  CARTER: "You're Morgan? Good. You're on emergency."
                  ALEX:   "I thought there were two senior doctors."
                  CARTER: "There were." (he walks off)
                  ALEX:   "What happened to the other one?"
                  CARTER: "He quit."

                Later, Maya - a nurse who's worked here six years - pulls you aside,
                nervous.
                  MAYA: "Three patients came in last night. Different ages, different
                         conditions. They all had the same blood abnormality."
                  ALEX: "That's unusual."
                  MAYA: "Very. And nobody wants to talk about it."
                """);
        pause();
    }



    private void day1() {
        dayHeader(1, "Something Is Wrong");

        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient("Patient 101", "Broken arm", Severity.MINOR));
        patients.add(new Patient("Patient 102", "Fever", Severity.MODERATE));
        patients.add(new Patient("Patient 103", "Chest pain", Severity.SERIOUS, true));
        patients.add(new Patient("Patient 104", "Severe bleeding", Severity.CRITICAL));
        hospital.runShift(patients, player, story, sc);

        printScene("""
                Patient 103's chart says they were admitted to St. Mary's three
                months ago and "discharged successfully." But the patient insists
                they've never set foot here before.
                """);

        int pick = choice("What do you do?",
                "Tell Maya about the discrepancy",
                "Report it to Dr. Carter",
                "Ignore it");

        switch (pick) {
            case 1 -> {
                story.setFlag("toldMaya");
                player.changeMayaTrust(10);
                player.changeInvestigation(10);
                printScene("Maya's eyes widen. \"That matches what I've been seeing,\" she whispers.");
            }
            case 2 -> {
                story.setFlag("toldCarter");
                player.changeCarterTrust(8);
                player.changeInvestigation(5);
                printScene("Carter frowns at the chart for a long moment. \"Keep this between us,\" he says.");
            }
            default -> {
                story.setFlag("ignoredDay1");
                player.changeInvestigation(-5);
                printScene("You file it away as a clerical error. It nags at you anyway.");
            }
        }
        story.setFlag("investigatedPatient103");
        endOfDay();
    }



    private void day2() {
        dayHeader(2, "The Missing Records");

        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient("Patient 205", "Fracture", Severity.MODERATE));
        patients.add(new Patient("Patient 206", "Appendicitis", Severity.SERIOUS));
        patients.add(new Patient("Patient 207", "Allergic reaction", Severity.MODERATE));
        hospital.runShift(patients, player, story, sc);

        printScene("""
                Patient 103's file is gone - paper AND digital.
                  MAYA:  "Someone deleted it."
                  ALEX:  "Who has access?"
                  MAYA:  "Doctors. Administration. IT."
                  ALEX:  "Anyone else?"
                  MAYA:  (hesitates) "The research department."

                You learn St. Mary's has a private research wing, supposedly
                developing a treatment for a rare disease. It doesn't accept
                visitors.
                """);

        int pick = choice("How do you respond?",
                "Push Maya for more details about the research wing",
                "Quietly note it and move on for now");

        if (pick == 1) {
            player.changeMayaTrust(5);
            player.changeInvestigation(10);
            story.setFlag("pushedMaya");
        } else {
            player.changeInvestigation(3);
        }
        endOfDay();
    }



    private void day3() {
        dayHeader(3, "The Research Wing");

        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient("Patient 308", "Chest trauma", Severity.CRITICAL));
        patients.add(new Patient("Patient 309", "Migraine", Severity.MINOR));
        patients.add(new Patient("Patient 310", "Laceration", Severity.MODERATE));
        hospital.runShift(patients, player, story, sc);

        printScene("""
                While hunting for medication overnight, you find an old access
                card belonging to Dr. Ethan Vale - the doctor who supposedly
                quit. The card still works.
                """);

        int pick = choice("What do you do with the card?",
                "Enter the research wing",
                "Give the card to hospital administration",
                "Keep the card and investigate later");

        switch (pick) {
            case 1 -> {
                story.setFlag("enteredResearchWing");
                player.changeInvestigation(20);
                printScene("""
                        Dozens of patient files line the shelves - every one shows the
                        same strange blood abnormality. Worse: they aren't listed as
                        patients. They're listed as TRIAL SUBJECTS.

                        You find a folder labeled PROJECT ORPHEUS. Footsteps approach.
                        You slip out just in time.
                        """);
                story.setFlag("foundOrpheus");
            }
            case 2 -> {
                story.setFlag("reportedCard");
                player.changeCarterTrust(5);
                player.changeInvestigation(-5);
                printScene("Administration thanks you and quietly pockets the card. Nothing more is said.");
            }
            default -> {
                story.setFlag("keptCard");
                player.changeInvestigation(5);
                printScene("You slide the card into your coat pocket. It can wait - for now.");
            }
        }
        endOfDay();
    }



    private void day4() {
        dayHeader(4, "Maya's Secret");

        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient("Patient 411", "Fracture", Severity.MODERATE));
        patients.add(new Patient("Patient 412", "Cardiac event", Severity.CRITICAL));
        patients.add(new Patient("Patient 413", "Fever", Severity.MINOR));
        hospital.runShift(patients, player, story, sc);

        printScene("""
                You confront Maya. She finally admits she's known about Project
                Orpheus for two years - an experimental neurological treatment
                with dangerous side effects the hospital has been hiding because
                the research is worth millions. Dr. Vale tried to expose them.
                That's why he "quit."
                """);

        int pick = choice("How do you respond to Maya?",
                "Reassure her - you're in this together",
                "Press her harder on what else she's hiding");

        if (pick == 1) {
            player.changeMayaTrust(15);
            story.setFlag("helpedMaya");
        } else {
            player.changeMayaTrust(-5);
            player.changeInvestigation(8);
        }
        story.setEvidenceCollected(story.hasFlag("foundOrpheus") && player.getInvestigation() >= 30);
        endOfDay();
    }



    private void day5() {
        dayHeader(5, "Blackwood's Deal");

        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient("Patient 520", "Broken leg", Severity.MODERATE));
        patients.add(new Patient("Patient 521", "Internal bleeding", Severity.CRITICAL));
        hospital.runShift(patients, player, story, sc);

        printScene("""
                Director Richard Blackwood summons you.
                  BLACKWOOD: "You're asking a lot of questions, Doctor."
                  ALEX:      "I'm treating patients."
                  BLACKWOOD: "No. You're investigating." He offers a deal:
                             stop asking questions, and you'll get your own
                             department.
                  ALEX:      "What about the patients?"
                  BLACKWOOD: "Patients come and go. Money keeps hospitals alive."
                """);

        int pick = choice("Your move:",
                "Accept the deal",
                "Refuse outright",
                "Pretend to accept, secretly keep gathering evidence");

        switch (pick) {
            case 1 -> {
                story.setFlag("acceptedDeal");
                player.changeBlackwoodRelationship(30);
                player.changeInvestigation(-15);
                player.addMoney(3000);
            }
            case 2 -> {
                story.setFlag("refusedDeal");
                player.changeBlackwoodRelationship(-20);
                player.changeReputation(5);
                player.changeInvestigation(5);
            }
            default -> {
                story.setFlag("pretendedToAccept");
                player.changeBlackwoodRelationship(10);
                player.changeInvestigation(10);
            }
        }
        endOfDay();
    }



    private void day6() {
        dayHeader(6, "Mass Casualty");
        printScene("A bus has crashed near the hospital. Fifteen patients are incoming - " +
                "four critical, six serious, five moderate. You have " + hospital.getCapacity() +
                " doctors and limited beds.");

        List<Patient> patients = new ArrayList<>();
        String[] critNames = {"Crash Victim A", "Crash Victim B", "Crash Victim C", "Crash Victim D"};
        for (String n : critNames) patients.add(new Patient(n, "Trauma", Severity.CRITICAL));
        String[] seriousNames = {"Crash Victim E", "Crash Victim F", "Crash Victim G", "Crash Victim H", "Crash Victim I", "Crash Victim J"};
        for (String n : seriousNames) patients.add(new Patient(n, "Trauma", Severity.SERIOUS));
        String[] modNames = {"Crash Victim K", "Crash Victim L", "Crash Victim M", "Crash Victim N", "Crash Victim O"};
        for (String n : modNames) patients.add(new Patient(n, "Trauma", Severity.MODERATE));

        hospital.runShift(patients, player, story, sc);

        printScene("""
                Amid the chaos, Maya recognizes one of the crash victims:
                Dr. Vale. He's alive - barely. Before surgery, he grabs your wrist.
                  VALE: "Room 417. Everything... is in Room 417."
                He loses consciousness.
                """);
        endOfDay();
    }

 

    private void day7() {
        dayHeader(7, "Room 417");

        printScene("""
                Inside Room 417 is a server holding years of research data.
                Project Orpheus wasn't originally meant to harm anyone - the
                treatment genuinely worked. But when the hospital discovered a
                side effect could be extremely profitable, they kept the trial
                running instead of stopping it. Hundreds of patients were
                affected. You now have the evidence.
                """);

        if (story.hasFlag("enteredResearchWing")) {
            story.setEvidenceCollected(true);
        }

        printScene("""
                Blackwood catches you at the server.
                  BLACKWOOD: "You have no idea what you're doing."
                  ALEX:      "I know exactly what I'm doing."
                  BLACKWOOD: "If this gets out, St. Mary's closes."
                  ALEX:      "Then maybe it should."
                  BLACKWOOD: "Think carefully." (sets a USB drive on the table)
                             "Give me the evidence, and I'll make sure everyone
                             walks away."
                """);

        int pick = choice("FINAL DECISION - what do you do with the evidence?",
                "Release the evidence publicly",
                "Give the evidence to the authorities",
                "Destroy the evidence and protect the hospital",
                "Give the evidence to Blackwood");

        Ending ending = Ending.resolve(player, story, pick);
        printEnding(ending);
    }



    private void dayHeader(int day, String title) {
        System.out.println();
        System.out.println("=========================================================");
        System.out.println("  DAY " + day + " - " + title.toUpperCase());
        System.out.println("=========================================================");
    }

    private void printScene(String text) {
        System.out.println();
        System.out.println(text.strip());
    }

    private void endOfDay() {
        System.out.println();
        System.out.println("--- End of day report ---");
        player.printStatus();
        story.nextDay();
        pause();
    }

    private void printEnding(Ending ending) {
        System.out.println();
        System.out.println("=========================================================");
        System.out.println("                     " + ending.getTitle());
        System.out.println("=========================================================");
        System.out.println(ending.getText());
        System.out.println("---------------------------------------------------------");
        System.out.printf("Final stats -> Reputation: %d | Investigation: %d | Money: $%d%n",
                player.getReputation(), player.getInvestigation(), player.getMoney());
        System.out.printf("Maya Trust: %d | Carter Trust: %d | Blackwood: %d%n",
                player.getMayaTrust(), player.getCarterTrust(), player.getBlackwoodRelationship());
        System.out.printf("Patients saved: %d/%d%n", story.getPatientsSaved(), story.getPatientsTotal());
    }

   
    private int choice(String prompt, String... options) {
        System.out.println();
        System.out.println(prompt);
        for (int i = 0; i < options.length; i++) {
            System.out.println("  [" + (i + 1) + "] " + options[i]);
        }
        System.out.print("> ");
        while (true) {
            String line = sc.nextLine().trim();
            try {
                int value = Integer.parseInt(line);
                if (value >= 1 && value <= options.length) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
                
            }
            System.out.print("Please enter a number between 1 and " + options.length + ": ");
        }
    }

    private void pause() {
        System.out.println("\n(press ENTER to continue)");
        sc.nextLine();
    }
}
