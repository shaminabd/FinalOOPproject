package Enums;

public enum Specialization {
    SURGEON("Surgeon"),
    THERAPIST("Therapist"),
    PSYCHOLOGIST("Psychologist");

    private final String displayName;

    Specialization(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
