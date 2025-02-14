package Factories;

import Models.Appointment;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppointmentFactory {
    public static Appointment createAppointment(int appointmentId, int doctorId, int patientId, String dateTime) {
        if (dateTime == null || dateTime.trim().isEmpty()) {
            throw new IllegalArgumentException("Appointment date/time cannot be empty.");
        }

        // Convert dateTime string to Timestamp
        Timestamp timestamp = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date parsedDate = sdf.parse(dateTime);  // Parse the string to a Date
            timestamp = new Timestamp(parsedDate.getTime());  // Convert Date to Timestamp
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Invalid date-time format.");
        }

        // Return new Appointment object with Timestamp
        return new Appointment(appointmentId, doctorId, patientId, timestamp);
    }
}
