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

        // ------------------------ VIEWING DATA ------------------------



        public List<Appointment> viewAppointments(int doctorId) {
            return appointmentRepository.getAppointmentsByDoctorId(doctorId);
        }

        public List<Medicine> viewMedicines() {
            return medicineRepository.getAllMedicines();
        }

        public List<Hospital> viewHospitals() {
            return hospitalRepository.getAllHospitals();
        }

        public boolean createDoctor(Doctor doctor) {
            // Call the repository method to insert the doctor into the doctors table
            return doctorRepository.addDoctor(doctor);
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

        // ------------------------ PRESCRIBING MEDICINE ------------------------

        public boolean prescribeMedicine(int doctorId, int patientId, int medicineId, int quantity, String recommendation) {
            User doctor = userRepository.getUserById(doctorId);

            // Ensure the user is a Doctor and not a Psychologist
            if (!(doctor instanceof Doctor doctorObj)) {
                throw new UnauthorizedAccessException("Only doctors can prescribe medicine.");
            }
            if (doctorObj.getRole() == UserRole.PSYCHOLOGIST) {
                throw new UnauthorizedAccessException("Psychologists cannot prescribe medicine.");
            }

            // Retrieve medicine and check availability
            Medicine medicine = medicineRepository.getMedicineById(medicineId);
            if (medicine == null) {
                throw new MedicineStockException("Invalid medicine ID: " + medicineId);
            }

            // Validate prescription quantity and stock
            DataValidation.validateMedicineQuantity(quantity, medicine.getQuantity());
            if (quantity > 5) {
                throw new MedicineStockException("Cannot prescribe more than 5 units at a time.");
            }

            // Reduce stock and create a medicine report
            medicineRepository.updateStock(medicineId, medicine.getQuantity() - quantity);
            MedicineReport report = new MedicineReport(0, doctorId, patientId, medicineId, medicine.getName(), quantity, recommendation);

            return medicineReportRepository.createReport(report);
        }
    }
