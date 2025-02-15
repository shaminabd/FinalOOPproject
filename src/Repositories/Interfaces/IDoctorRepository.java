package Repositories.Interfaces;

import Models.Doctor;
import Models.User;

import java.util.List;

public interface IDoctorRepository {
    void addDoctor(Doctor doctor);

    List<Doctor> getDoctorsWithHospitalName();
}
