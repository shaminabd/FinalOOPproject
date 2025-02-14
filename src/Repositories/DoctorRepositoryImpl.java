package Repositories;

import Config.PostgresDB;
import Enums.Specialization;
import Models.Doctor;
import Models.User;
import Repositories.Interfaces.IDoctorRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DoctorRepositoryImpl implements IDoctorRepository {
    private final Connection connection;

    public DoctorRepositoryImpl() {
        this.connection = PostgresDB.getInstance().connect();
    }

    @Override
    public boolean addDoctor(Doctor doctor) {
        // SQL query to insert the doctor into the doctors table
        String query = "INSERT INTO doctors (id, hospital_id, specialization) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            // We can safely access fields since the object is a Doctor
            statement.setInt(1, doctor.getId());  // Use the doctor ID from the users table
            statement.setInt(2, doctor.getHospitalId());  // Get hospital ID from the Doctor class
            statement.setString(3, doctor.getSpecialization().toString());  // Get specialization from the Doctor class
            return statement.executeUpdate() > 0;  // Return true if insertion is successful
        } catch (SQLException e) {
            e.printStackTrace();  // Handle SQL exception
        }
        return false;  // Return false if insertion fails
    }

}
