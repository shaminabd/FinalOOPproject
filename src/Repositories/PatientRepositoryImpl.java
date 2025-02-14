package Repositories;

import Config.PostgresDB;
import Models.Patient;
import Repositories.Interfaces.IPatientRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientRepositoryImpl implements IPatientRepository {
    private final Connection connection;

    public PatientRepositoryImpl() {
        this.connection = PostgresDB.getInstance().connect();
    }

    @Override
    public void addPatient(Patient patient) {
        String query = "INSERT INTO patients (name) VALUES (?) RETURNING id";
        try (PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, patient.getName());

            int affectedRows = statement.executeUpdate();


            if (affectedRows > 0) {
                try (ResultSet rs = statement.getGeneratedKeys()) {
                    if (rs.next()) {
                        int generatedId = rs.getInt(1);
                        patient.setId(generatedId);
                        System.out.println("Patient registered with ID: " + generatedId);
                    }
                }
            } else {
                System.out.println("No rows affected, patient registration failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle SQL exception
        }
    }

}

