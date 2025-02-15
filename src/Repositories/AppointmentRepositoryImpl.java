package Repositories;

import Repositories.Interfaces.IAppointmentRepository;
import Models.Appointment;
import Config.PostgresDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentRepositoryImpl implements IAppointmentRepository {
    private final Connection connection;

    public AppointmentRepositoryImpl() {
        this.connection = PostgresDB.getInstance().connect();
    }

    @Override
    public Appointment getAppointmentById(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM appointments WHERE appointment_id = ?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new Appointment(
                        rs.getInt("appointment_id"),
                        rs.getInt("doctor_id"),
                        rs.getInt("patient_id"),
                        rs.getTimestamp("date_time")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Appointment> getAppointmentsByDoctorId(int doctorId) {
        List<Appointment> appointments = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM appointments WHERE doctor_id = ?");
            statement.setInt(1, doctorId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                appointments.add(new Appointment(
                        rs.getInt("appointment_id"),
                        rs.getInt("doctor_id"),
                        rs.getInt("patient_id"),
                        rs.getTimestamp("date_time")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    @Override
    public List<Appointment> getAppointmentsByPatientId(int patientId) {
        List<Appointment> appointments = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM appointments WHERE patient_id = ?");
            statement.setInt(1, patientId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                appointments.add(new Appointment(
                        rs.getInt("appointment_id"),
                        rs.getInt("doctor_id"),
                        rs.getInt("patient_id"),
                        rs.getTimestamp("date_time")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    @Override
    public boolean createAppointment(Appointment appointment) {
        try {
            // Insert the appointment into the 'appointments' table directly
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO appointments (doctor_id, patient_id, date_time) VALUES (?, ?, ?)");
            statement.setInt(1, appointment.getDoctorId());
            statement.setInt(2, appointment.getPatientId());  // Pass patientId here directly
            statement.setTimestamp(3, appointment.getDateTime());
            int affectedRows = statement.executeUpdate();


            if (affectedRows > 0) {
                try (ResultSet rs = statement.getGeneratedKeys()) {
                    if (rs.next()) {
                        int generatedId = rs.getInt(1);
                        appointment.setAppointmentId(generatedId);
                        System.out.println("Appointment created with ID: " + generatedId);
                    }
                }
            } else {
                System.out.println("No rows affected, appointment creation failed.");
            }

            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    @Override
    public boolean deleteAppointment(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM appointments WHERE appointment_id = ?");
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM appointments");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                appointments.add(new Appointment(
                        rs.getInt("appointment_id"),
                        rs.getInt("doctor_id"),
                        rs.getInt("patient_id"),
                        rs.getTimestamp("date_time")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }


    @Override
    public boolean updateAppointment(int id, Appointment updatedAppointment) {
        try {
            if (checkAppointmentConflict(updatedAppointment.getDoctorId(), updatedAppointment.getDateTime())) {
                return false;
            }

            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE appointments SET doctor_id = ?, patient_id = ?, date_time = ? WHERE appointment_id = ?");
            statement.setInt(1, updatedAppointment.getDoctorId());
            statement.setInt(2, updatedAppointment.getPatientId());
            statement.setTimestamp(3, updatedAppointment.getDateTime());
            statement.setInt(4, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean checkAppointmentConflict(int doctorId, Timestamp dateTime) {
        try {
            PreparedStatement checkStmt = connection.prepareStatement(
                    "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND date_time = ?");
            checkStmt.setInt(1, doctorId);
            checkStmt.setTimestamp(2, dateTime);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
