package Repositories.Interfaces;

import Models.User;

public interface IPatientRepository {
    boolean addPatient(User patient);
}
