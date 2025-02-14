import Controllers.*;
import Controllers.Interfaces.UserController;
import Config.DatabaseInitializer;
import Repositories.*;
import Repositories.Interfaces.*;
import Services.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        DatabaseInitializer.initializeDatabase();

        IUserRepository userRepository = new UserRepositoryImpl();
        IHospitalRepository hospitalRepository = new HospitalRepositoryImpl();
        IMedicineRepository medicineRepository = new MedicineRepositoryImpl();
        IAppointmentRepository appointmentRepository = new AppointmentRepositoryImpl();
        IMedicineReportRepository medicineReportRepository = new MedicineReportRepositoryImpl();
        IDoctorRepository doctorRepository = new DoctorRepositoryImpl();
        IPatientRepository patientRepository = new PatientRepositoryImpl();

        UserService userService = new UserService(userRepository);
        AdminService adminService = new AdminService(hospitalRepository, userRepository, medicineRepository, appointmentRepository, medicineReportRepository);
        DoctorService doctorService = new DoctorService(appointmentRepository, medicineRepository, medicineReportRepository, userRepository, hospitalRepository, doctorRepository);
        PatientService patientService = new PatientService(appointmentRepository, medicineReportRepository, userRepository, patientRepository);

        AdminController adminController = new AdminController(userService, adminService);
        DoctorController doctorController = new DoctorController(userService, doctorService);
        PatientController patientController = new PatientController(userService, patientService);


        MyApplication app = new MyApplication(userService,adminController, doctorController, patientController);
        app.start();
    }
}