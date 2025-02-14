package Repositories;

import Config.PostgresDB;
import Models.User;
import Repositories.Interfaces.IPatientRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PatientRepositoryImpl implements IPatientRepository {
    private final Connection connection;

    public PatientRepositoryImpl() {
        this.connection = PostgresDB.getInstance().connect();
    }

    @Override
    public boolean addPatient(User patient) {
        String query = "INSERT INTO patients (id, name) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, patient.getId());  // Assuming patient ID is set after insertion into users table
            statement.setString(2, patient.getName());  // Set patient's name
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

