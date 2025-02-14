package Models;

import Enums.Specialization;
import Enums.UserRole;

public class Psychologist extends Doctor {
    public Psychologist(int id, String name, String email, String password, int hospitalId) {
        super(id, name, email, password, hospitalId, UserRole.DOCTOR, Specialization.PSYCHOLOGIST);
    }

    public void conductSession(String patientName) {
        System.out.println("🧠 Psychologist " + this.getName() + " is conducting a therapy session with " + patientName + ".");
    }
}
