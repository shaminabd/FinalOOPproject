    package Services;

    import Enums.Specialization;
    import Models.*;
    import Enums.UserRole;
    import Repositories.Interfaces.*;
    import Exceptions.MedicineStockException;
    import Exceptions.UnauthorizedAccessException;
    import Utils.DataValidation;
    import java.util.List;
    import java.util.stream.Collectors;

    import static Enums.Specialization.PSYCHOLOGIST;

    public class DoctorService {
        private final IAppointmentRepository appointmentRepository;
        private final IMedicineRepository medicineRepository;
        private final IMedicineReportRepository medicineReportRepository;
        private final IUserRepository userRepository;
        private final IHospitalRepository hospitalRepository;
        private final IDoctorRepository doctorRepository;

        public DoctorService(IAppointmentRepository appointmentRepository, IMedicineRepository medicineRepository,
                             IMedicineReportRepository medicineReportRepository, IUserRepository userRepository,
                             IHospitalRepository hospitalRepository, IDoctorRepository doctorRepository) {
            this.appointmentRepository = appointmentRepository;
            this.medicineRepository = medicineRepository;
            this.medicineReportRepository = medicineReportRepository;
            this.userRepository = userRepository;
            this.hospitalRepository = hospitalRepository;
            this.doctorRepository = doctorRepository;
        }




        public List<Appointment> viewAppointments(int doctorId) {
            return appointmentRepository.getAppointmentsByDoctorId(doctorId);
        }

        public List<Medicine> viewMedicines() {
            return medicineRepository.getAllMedicines();
        }

        public List<Hospital> viewHospitals() {
            return hospitalRepository.getAllHospitals();
        }

        public void createDoctor(Doctor doctor) {

            doctorRepository.addDoctor(doctor);
        }

        public List<Doctor> getDoctorsWithHospitalName() {
            return doctorRepository.getDoctorsWithHospitalName();  // Call to repository method
        }

        public List<User> viewPatients() {
            return userRepository.getAllUsers().stream()
                    .filter(user -> user.getRole() == UserRole.PATIENT)
                    .collect(Collectors.toList());
        }

        public Hospital viewMyHospital(int doctorId) {
            User doctor = userRepository.getUserById(doctorId);
            if (doctor instanceof Doctor) {
                return hospitalRepository.getHospitalById(((Doctor) doctor).getHospitalId());
            }
            return null;
        }

        public List<MedicineReport> viewMedicineReports(int doctorId) {
            return medicineReportRepository.getReportsByDoctorId(doctorId);
        }


        public boolean prescribeMedicine(int doctorId, int patientId, int medicineId, int quantity, String recommendation) {
            User doctor = userRepository.getUserById(doctorId);


            if (!(UserRole.DOCTOR.equals(doctor.getRole()))) {
                throw new UnauthorizedAccessException("Only doctors can prescribe medicine.");
            }


            if (doctor instanceof Doctor) {
                Doctor doctorObj = (Doctor) doctor;
                if (doctorObj.getSpecialization().equals(PSYCHOLOGIST)) {
                    throw new UnauthorizedAccessException("Psychologists cannot prescribe medicine.");
                }
            }


            Medicine medicine = medicineRepository.getMedicineById(medicineId);
            if (medicine == null) {
                throw new MedicineStockException("Invalid medicine ID: " + medicineId);
            }


            DataValidation.validateMedicineQuantity(quantity, medicine.getQuantity());
            if (quantity > 5) {
                throw new MedicineStockException("Cannot prescribe more than 5 units at a time.");
            }

            medicineRepository.updateStock(medicineId, medicine.getQuantity() - quantity);
            MedicineReport report = new MedicineReport(0, doctorId, patientId, medicineId, medicine.getName(), quantity, recommendation);

            return medicineReportRepository.createReport(report);
        }
    }

