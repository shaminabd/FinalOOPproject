package Factories;

import Models.Appointment;

public class AppointmentFactory {
    public static Appointment createAppointment(int appointmentId, int doctorId, int patientId, String dateTime) {
        if (dateTime == null || dateTime.trim().isEmpty()) {
            throw new IllegalArgumentException("Appointment date/time cannot be empty.");
        }
        return new Appointment(appointmentId, doctorId, patientId, dateTime);
    }
}
