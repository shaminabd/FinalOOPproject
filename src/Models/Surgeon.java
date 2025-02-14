package Models;

import Enums.Specialization;
import Enums.UserRole;

public class Surgeon extends Doctor {
    public Surgeon(int id, String name, String email, String password, int hospitalId) {
        super(id, name, email, password, hospitalId, UserRole.DOCTOR, Specialization.SURGEON);
    }

    public void performSurgery() {
        System.out.println("🔪 Surgeon " + this.getName() + " is performing a surgery.");
    }
}
