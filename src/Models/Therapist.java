package Models;

import Enums.Specialization;
import Enums.UserRole;

public class Therapist extends Doctor {
    public Therapist(int id, String name, String email, String password, int hospitalId) {
        super(id, name, email, password, hospitalId, UserRole.DOCTOR, Specialization.THERAPIST);
    }

    public void provideTherapy() {
        System.out.println("💆 Therapist " + this.getName() + " is providing therapy.");
    }
}
