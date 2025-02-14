package Enums;

public enum UserRole {
    ADMIN("Admin"),
    PATIENT("Patient"),
    DOCTOR("General Doctor"),
    SURGEON("Surgeon"),
    THERAPIST("Therapist"),
    PSYCHOLOGIST("Psychologist");

    private final String displayName;

    UserRole(String displayName) {
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
