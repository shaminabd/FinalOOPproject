package Factories;

import Models.Hospital;

public class HospitalFactory {
    public static Hospital createHospital(int hospitalId, String hospitalName) {
        if (hospitalName == null || hospitalName.trim().isEmpty()) {
            throw new IllegalArgumentException("Hospital name cannot be empty.");
        }
        return new Hospital(hospitalId, hospitalName);
    }
}
