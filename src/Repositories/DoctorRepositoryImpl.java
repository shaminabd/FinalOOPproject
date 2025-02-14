package Repositories;

import Config.PostgresDB;
import Enums.Specialization;
import Enums.UserRole;
import Models.Doctor;
import Models.User;
import Repositories.Interfaces.IDoctorRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorRepositoryImpl implements IDoctorRepository {
    private final Connection connection;

    public DoctorRepositoryImpl() {
        this.connection = PostgresDB.getInstance().connect();
    }


    public List<Doctor> getDoctorsWithHospitalName() {
        // SQL JOIN query to retrieve doctor and hospital details with all required fields
        String query = "SELECT d.id, d.name, d.email, d.password, d.hospital_id, d.specialization, h.hospital_name " +
                "FROM doctors d " +
                "JOIN hospitals h ON d.hospital_id = h.hospital_id";  // SQL JOIN between doctors and hospitals

        List<Doctor> doctorList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            // Loop through the result set to map data to Doctor objects
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                int hospitalId = resultSet.getInt("hospital_id");
                String specialization = resultSet.getString("specialization");
                String hospitalName = resultSet.getString("hospital_name");

                // Map the specialization and role
                Specialization spec = Specialization.valueOf(specialization);  // Assuming specialization is stored as string in DB
                UserRole role = UserRole.DOCTOR;  // Assuming role is fixed for Doctor, otherwise, map it accordingly

                // Create a new Doctor object with all 7 required fields
                Doctor doctor = new Doctor(id, name, email, password, hospitalId, role, spec);
                doctorList.add(doctor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doctorList;  // Return the list of doctors with hospital names and other details
    }



    @Override
    public void addDoctor(Doctor doctor) {

        String query = "INSERT INTO doctors (hospital_id, specialization) VALUES (?, ?) RETURNING id";

        try (PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, doctor.getHospitalId());
            statement.setString(2, doctor.getSpecialization().toString());

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = statement.getGeneratedKeys()) {
                    if (rs.next()) {
                        int generatedId = rs.getInt(1);
                        doctor.setId(generatedId);
                        System.out.println("Doctor registered with ID: " + generatedId);
                    }
                }
            } else {
                System.out.println("No rows affected, doctor registration failed.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}
