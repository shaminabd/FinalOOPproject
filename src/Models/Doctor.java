package Models;

import Enums.Specialization;
import Enums.UserRole;

public class Doctor extends User {
    private int hospitalId;
    Specialization specialization;

    public Doctor(int id, String name, String email, String password, int hospitalId, UserRole role, Specialization specialization) {
        super(id, name, email, password, role);
        this.hospitalId = hospitalId;
        this.specialization = specialization;
    }

    public int getHospitalId() {
        return hospitalId;
    }

    public int getId() {
        return id;
    }
    @Override
    public String toString() {
        return super.toString() + " | Hospital ID: " + hospitalId;
    }

    public Specialization getSpecialization() {
        return this.specialization;
    }
}
