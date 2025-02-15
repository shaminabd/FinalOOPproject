package Repositories;

import Repositories.Interfaces.IHospitalRepository;
import Models.Hospital;
import Config.PostgresDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HospitalRepositoryImpl implements IHospitalRepository {
    private final Connection connection;

    public HospitalRepositoryImpl() {
        this.connection = PostgresDB.getInstance().connect();
    }

    @Override
    public Hospital getHospitalById(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM hospitals WHERE hospital_id = ?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new Hospital(rs.getInt("hospital_id"), rs.getString("hospital_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Hospital> getAllHospitals() {
        List<Hospital> hospitals = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM hospitals");
            while (rs.next()) {
                hospitals.add(new Hospital(rs.getInt("hospital_id"), rs.getString("hospital_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hospitals;
    }

    @Override
    public boolean createHospital(Hospital hospital) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO hospitals (hospital_name) VALUES (?)");
            statement.setString(1, hospital.getHospitalName());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteHospital(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM hospitals WHERE hospital_id = ?");
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
