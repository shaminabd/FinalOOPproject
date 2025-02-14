package Repositories;

import Repositories.Interfaces.IMedicineReportRepository;
import Models.MedicineReport;
import Config.PostgresDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicineReportRepositoryImpl implements IMedicineReportRepository {
    private final Connection connection;

    public MedicineReportRepositoryImpl() {
        this.connection = PostgresDB.getInstance().connect();
    }

    @Override
    public MedicineReport getReportById(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM medicine_reports WHERE report_id = ?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new MedicineReport(
                        rs.getInt("report_id"),
                        rs.getInt("doctor_id"),
                        rs.getInt("patient_id"),
                        rs.getInt("medicine_id"),
                        rs.getString("medicine_name"),
                        rs.getInt("quantity"),
                        rs.getString("recommendation")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<MedicineReport> getReportsByDoctorId(int doctorId) {
        List<MedicineReport> reports = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM medicine_reports WHERE doctor_id = ?");
            statement.setInt(1, doctorId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                reports.add(new MedicineReport(
                        rs.getInt("report_id"),
                        rs.getInt("doctor_id"),
                        rs.getInt("patient_id"),
                        rs.getInt("medicine_id"),
                        rs.getString("medicine_name"),
                        rs.getInt("quantity"),
                        rs.getString("recommendation")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reports;
    }

    @Override
    public List<MedicineReport> getReportsByPatientId(int patientId) {
        List<MedicineReport> reports = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM medicine_reports WHERE patient_id = ?");
            statement.setInt(1, patientId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                reports.add(new MedicineReport(
                        rs.getInt("report_id"),
                        rs.getInt("doctor_id"),
                        rs.getInt("patient_id"),
                        rs.getInt("medicine_id"),
                        rs.getString("medicine_name"),
                        rs.getInt("quantity"),
                        rs.getString("recommendation")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reports;
    }

    @Override
    public List<MedicineReport> getAllReports() {
        List<MedicineReport> reports = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM medicine_reports");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                reports.add(new MedicineReport(
                        rs.getInt("report_id"),
                        rs.getInt("doctor_id"),
                        rs.getInt("patient_id"),
                        rs.getInt("medicine_id"),
                        rs.getString("medicine_name"),
                        rs.getInt("quantity"),
                        rs.getString("recommendation")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reports;
    }

    @Override
    public boolean updateReport(int reportId, MedicineReport updatedReport) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE medicine_reports SET doctor_id = ?, patient_id = ?, medicine_id = ?, medicine_name = ?, quantity = ?, recommendation = ? WHERE report_id = ?"
            );
            statement.setInt(1, updatedReport.getDoctorId());
            statement.setInt(2, updatedReport.getPatientId());
            statement.setInt(3, updatedReport.getMedicineId());
            statement.setString(4, updatedReport.getMedicineName());
            statement.setInt(5, updatedReport.getQuantity());
            statement.setString(6, updatedReport.getRecommendation());
            statement.setInt(7, reportId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteReport(int reportId) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM medicine_reports WHERE report_id = ?");
            statement.setInt(1, reportId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean createReport(MedicineReport report) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO medicine_reports (doctor_id, patient_id, medicine_id, medicine_name, quantity, recommendation) VALUES (?, ?, ?, ?, ?, ?)"
            );
            statement.setInt(1, report.getDoctorId());
            statement.setInt(2, report.getPatientId());
            statement.setInt(3, report.getMedicineId());
            statement.setString(4, report.getMedicineName());
            statement.setInt(5, report.getQuantity());
            statement.setString(6, report.getRecommendation());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
