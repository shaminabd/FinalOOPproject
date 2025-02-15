package Repositories.Interfaces;

import Models.Hospital;
import java.util.List;

public interface IHospitalRepository {
    Hospital getHospitalById(int id);
    List<Hospital> getAllHospitals();
    boolean createHospital(Hospital hospital);
    boolean deleteHospital(int id);
}
