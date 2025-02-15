package Factories;

import Enums.Specialization;
import Models.*;
import Enums.UserRole;
import Enums.Specialization;

public class UserFactory {
    public static User createUser(UserRole role, int id, String name, String email, String password, Integer hospitalId, Specialization specialization) {
        if (hospitalId == null && (role == UserRole.DOCTOR || role == UserRole.SURGEON || role == UserRole.THERAPIST || role == UserRole.PSYCHOLOGIST)) {
            throw new IllegalArgumentException("Hospital ID is required for doctors.");
        }

        switch (role) {
            case ADMIN:
                return new Admin(id, name, email, password);
            case PATIENT:
                return new Patient(id, name, email, password);
            case DOCTOR:
                return new Doctor(id, name, email, password, hospitalId, UserRole.DOCTOR, specialization);
            case SURGEON:
                return new Surgeon(id, name, email, password, hospitalId);
            case THERAPIST:
                return new Therapist(id, name, email, password, hospitalId);
            case PSYCHOLOGIST:
                return new Psychologist(id, name, email, password, hospitalId);
            default:
                throw new IllegalArgumentException("Invalid user role provided: " + role);
        }
    }
}
