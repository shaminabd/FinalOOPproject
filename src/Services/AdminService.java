package Services;

import Models.*;
import Enums.UserRole;
import Exceptions.HospitalNotFoundException;
import Exceptions.UserNotFoundException;
import Repositories.Interfaces.*;
import java.util.List;
import java.util.stream.Collectors;

public class AdminService {
    private final IHospitalRepository hospitalRepository;
    private final IUserRepository userRepository;
    private final IMedicineRepository medicineRepository;
    private final IAppointmentRepository appointmentRepository;
    private final IMedicineReportRepository medicineReportRepository;

    public AdminService(IHospitalRepository hospitalRepository, IUserRepository userRepository,
                        IMedicineRepository medicineRepository, IAppointmentRepository appointmentRepository,
                        IMedicineReportRepository medicineReportRepository) {
        this.hospitalRepository = hospitalRepository;
        this.userRepository = userRepository;
        this.medicineRepository = medicineRepository;
        this.appointmentRepository = appointmentRepository;
        this.medicineReportRepository = medicineReportRepository;
    }



    public boolean createHospital(String hospitalName) {
        return hospitalRepository.createHospital(new Hospital(0, hospitalName));
    }

    public boolean deleteHospital(int hospitalId) {
        Hospital hospital = hospitalRepository.getHospitalById(hospitalId);
        if (hospital == null) {
            throw new HospitalNotFoundException("Hospital with ID " + hospitalId + " not found.");
        }
        return hospitalRepository.deleteHospital(hospitalId);
    }

    public List<Hospital> viewHospitals() {
        return hospitalRepository.getAllHospitals();
    }


    public List<User> viewAllUsers() {
        return userRepository.getAllUsers();
    }

    public boolean deleteUser(int userId) {
        User user = userRepository.getUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("User with ID " + userId + " not found.");
        }
        return userRepository.deleteUser(userId);
    }

    public List<User> viewDoctors() {
        return userRepository.getAllUsers().stream()
                .filter(user -> user.getRole() == UserRole.DOCTOR)
                .collect(Collectors.toList());
    }

    public List<User> viewPatients() {
        return userRepository.getAllUsers().stream()
                .filter(user -> user.getRole() == UserRole.PATIENT)
                .collect(Collectors.toList());
    }



    public boolean addMedicine(String name, int quantity) {
        Medicine existingMedicine = medicineRepository.getMedicineByName(name);
        if (existingMedicine != null) {
            return updateMedicineStock(existingMedicine.getId(), existingMedicine.getQuantity() + quantity);
        }
        return medicineRepository.createMedicine(new Medicine(0, name, quantity));
    }

    public boolean updateMedicineStock(int medicineId, int newQuantity) {
        return medicineRepository.updateStock(medicineId, newQuantity);
    }

    public boolean deleteMedicine(int medicineId) {
        return medicineRepository.deleteMedicine(medicineId);
    }

    public List<Medicine> viewMedicines() {
        return medicineRepository.getAllMedicines();
    }



    public List<Appointment> viewAppointments() {
        return appointmentRepository.getAllAppointments(); // Fetch all appointments
    }

    public List<Appointment> viewAppointmentsByDoctor(int doctorId) {
        return appointmentRepository.getAppointmentsByDoctorId(doctorId);
    }

    public List<Appointment> viewAppointmentsByPatient(int patientId) {
        return appointmentRepository.getAppointmentsByPatientId(patientId);
    }

    public boolean deleteAppointment(int appointmentId) {
        return appointmentRepository.deleteAppointment(appointmentId);
    }



    public List<MedicineReport> viewMedicineReports() {
        return medicineReportRepository.getAllReports(); // Fetch all reports
    }

    public List<MedicineReport> viewMedicineReportsByDoctor(int doctorId) {
        return medicineReportRepository.getReportsByDoctorId(doctorId);
    }

    public List<MedicineReport> viewMedicineReportsByPatient(int patientId) {
        return medicineReportRepository.getReportsByPatientId(patientId);
    }



    public List<Surgeon> getSurgeons() {
        List<User> allDoctors = userRepository.getAllUsers();
        return allDoctors.stream()
                .filter(user -> user instanceof Surgeon)  // Only include Surgeons
                .map(user -> (Surgeon) user)  // Cast to Surgeon
                .collect(Collectors.toList());  // Collect into a list
    }

}
