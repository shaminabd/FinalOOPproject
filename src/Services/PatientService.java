package Services;

import Models.*;
import Repositories.Interfaces.*;
import Exceptions.AppointmentConflictException;
import Exceptions.UserNotFoundException;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
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


    public boolean bookAppointment(int doctorId, int patientId, String dateTime) {
        Timestamp timestamp = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date parsedDate = sdf.parse(dateTime);
            timestamp = new Timestamp(parsedDate.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Invalid date-time format.");
        }

        Appointment appointment = new Appointment(0, doctorId, patientId, timestamp);

        return appointmentRepository.createAppointment(appointment);
    }



    public void createPatient(Patient patient) {

        patientRepository.addPatient(patient);


        if (patient.getId() > 0) {
            System.out.println("Patient registered with ID: " + patient.getId());
        } else {
            throw new IllegalStateException("Failed to register patient.");
        }
    }




    public List<Appointment> viewAppointments(int patientId) {
        return appointmentRepository.getAppointmentsByPatientId(patientId);
    }



    public List<MedicineReport> viewMedicineReports(int patientId) {
        return medicineReportRepository.getReportsByPatientId(patientId);
    }
}
