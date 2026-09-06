package laststory;


public class Patient {

    private final String name;
    private final String condition;
    private final Severity severity;
    private PatientStatus status;
    private final boolean storyImportant; // ties this patient to the plot (e.g. Patient 103)

    public Patient(String name, String condition, Severity severity, boolean storyImportant) {
        this.name = name;
        this.condition = condition;
        this.severity = severity;
        this.status = PatientStatus.WAITING;
        this.storyImportant = storyImportant;
    }

    public Patient(String name, String condition, Severity severity) {
        this(name, condition, severity, false);
    }

    public String getName() {
        return name;
    }

    public String getCondition() {
        return condition;
    }

    public Severity getSeverity() {
        return severity;
    }

    public PatientStatus getStatus() {
        return status;
    }

    public void setStatus(PatientStatus status) {
        this.status = status;
    }

    public boolean isStoryImportant() {
        return storyImportant;
    }

    @Override
    public String toString() {
        return String.format("%-16s | %-18s | %-8s | %s",
                name, condition, severity, status);
    }
}
