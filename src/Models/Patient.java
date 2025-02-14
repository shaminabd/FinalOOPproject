package Models;

import Enums.UserRole;

public class Patient extends User {
    public Patient(int id, String name, String email, String password) {
        super(id, name, email, password, UserRole.PATIENT);
    }

    public void requestAppointment() {
        System.out.println("Patient " + this.getName() + " is requesting an appointment.");
    }
}
