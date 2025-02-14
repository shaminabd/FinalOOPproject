package Repositories.Interfaces;

import Models.Appointment;

import java.sql.Timestamp;
import java.util.List;

public interface IAppointmentRepository {
    Appointment getAppointmentById(int id);
    List<Appointment> getAppointmentsByDoctorId(int doctorId);
    List<Appointment> getAppointmentsByPatientId(int patientId);
    boolean createAppointment(Appointment appointment);
    boolean deleteAppointment(int id);

    // 🔹 NEW METHODS TO ADD
    List<Appointment> getAllAppointments(); // Get all appointments (for Admin)
    boolean updateAppointment(int id, Appointment updatedAppointment); // Update appointment details
    boolean checkAppointmentConflict(int doctorId, Timestamp dateTime); // Check for scheduling conflicts
}
