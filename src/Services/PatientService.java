package Services;

import Models.*;
import Repositories.Interfaces.*;
import Exceptions.AppointmentConflictException;
import Exceptions.UserNotFoundException;
import java.util.List;
import java.util.logging.Logger;

public class PatientService {
    private static final Logger logger = Logger.getLogger(PatientService.class.getName());

    private final IAppointmentRepository appointmentRepository;
    private final IMedicineReportRepository medicineReportRepository;
    private final IUserRepository userRepository;
    private final IPatientRepository patientRepository;

    public PatientService(IAppointmentRepository appointmentRepository, IMedicineReportRepository medicineReportRepository,
                          IUserRepository userRepository, IPatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.medicineReportRepository = medicineReportRepository;
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
    }

    // ------------------------ APPOINTMENTS ------------------------

    public boolean bookAppointment(int doctorId, int patientId, String dateTime) {
        // Check if patient exists
        User patient = userRepository.getUserById(patientId);
        if (patient == null) {
            throw new UserNotFoundException("Patient with ID " + patientId + " not found.");
        }

        // Check if doctor exists
        User doctor = userRepository.getUserById(doctorId);
        if (doctor == null) {
            throw new UserNotFoundException("Doctor with ID " + doctorId + " not found.");
        }

        // Check if there's a conflict using `checkAppointmentConflict()`
        if (appointmentRepository.checkAppointmentConflict(doctorId, dateTime)) {
            throw new AppointmentConflictException("Doctor is already booked at this time.");
        }

        // Create the appointment
        Appointment appointment = new Appointment(0, doctorId, patientId, dateTime);
        boolean success = appointmentRepository.createAppointment(appointment);

        // Logging action
        if (success) {
            logger.info("Patient " + patientId + " booked an appointment with Doctor " + doctorId + " at " + dateTime);
        } else {
            logger.warning("Failed to book appointment for Patient " + patientId);
        }

        return success;
    }

    public boolean createPatient(String name, String email, String password) {
        // Create the user (Patient)
        User patient = new Patient(0, name, email, password);

        // Save the user to the users table
        boolean userCreated = userRepository.createUser(patient);

        if (userCreated) {
            // Insert into the patients table
            return patientRepository.addPatient(patient);
        }
        return false;
    }

    public List<Appointment> viewAppointments(int patientId) {
        return appointmentRepository.getAppointmentsByPatientId(patientId);
    }

    // ------------------------ MEDICINE REPORTS ------------------------

    public List<MedicineReport> viewMedicineReports(int patientId) {
        return medicineReportRepository.getReportsByPatientId(patientId);
    }
}
