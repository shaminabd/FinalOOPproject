package Utils;

import Exceptions.InvalidMedicineQuantityException;
import Exceptions.AppointmentConflictException;
import java.util.List;
import Models.Appointment;

public class DataValidation {

    public static void validateMedicineQuantity(int requested, int available) {
        if (requested > available) {
            throw new InvalidMedicineQuantityException("Not enough stock available. Requested: " + requested + ", Available: " + available);
        }
    }

    public static void checkAppointmentConflict(List<Appointment> existingAppointments, int doctorId, String dateTime) {
        boolean conflictExists = existingAppointments.stream()
                .anyMatch(appt -> appt.getDoctorId() == doctorId && appt.getDateTime().equals(dateTime));

        if (conflictExists) {
            throw new AppointmentConflictException("Doctor is already booked at this time.");
        }
    }
}
