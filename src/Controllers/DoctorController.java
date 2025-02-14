package Controllers;

import Controllers.Interfaces.UserController;
import Enums.Specialization;
import Models.*;
import Enums.UserRole;
import Services.AdminService;
import Services.UserService;
import Services.DoctorService;
import java.util.List;
import java.util.Scanner;

public class DoctorController implements UserController {
    private final UserService userService;
    private final DoctorService doctorService;
    private final AdminService adminService;
    private final Scanner scanner = new Scanner(System.in);
    private int doctorId;
    private boolean isLoggedIn = false;

    public DoctorController(UserService userService, DoctorService doctorService, AdminService adminService) {
        this.userService = userService;
        this.doctorService = doctorService;
        this.adminService = adminService;
        this.isLoggedIn = false;
    }
    public void start(User user) {
        this.doctorId = user.getId();
        printHeader("Welcome, Dr. " + user.getName() + "!");
        isLoggedIn = true;
        doctorMenu();  // Go directly to doctor dashboard
    }


    @Override
    public void register() {
        printHeader("Doctor Registration");

        // Prompt for Doctor's Information
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        // Specialization Choice
        System.out.println("Select Specialization: ");
        System.out.println("1. Surgeon");
        System.out.println("2. Therapist");
        System.out.println("3. Psychologist");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Specialization specialization;
        switch (choice) {
            case 1: specialization = Specialization.SURGEON; break;
            case 2: specialization = Specialization.THERAPIST; break;
            case 3: specialization = Specialization.PSYCHOLOGIST; break;
            default:
                printError("Invalid selection. Doctor not registered.");
                return;
        }

        System.out.print("Enter Hospital ID (e.g., 1, 2, 3, etc.): ");
        int hospitalId = scanner.nextInt();
        scanner.nextLine();


        Doctor doctor = new Doctor(0, name, email, password, hospitalId, UserRole.DOCTOR, specialization);
        if (userService.registerUser(doctor)) {

            doctorService.createDoctor(doctor);
            // Once the user is created, insert the doctor into the doctors table

        } else {
            printError("Doctor registration failed.");
        }
    }

    @Override
    public void logout() {
        printHeader("Logging out...");
        isLoggedIn = false;
    }

    private void doctorMenu() {
        while (isLoggedIn) {
            printHeader("DOCTOR DASHBOARD");
            System.out.println("1. View Appointments");
            System.out.println("2. View Medicines");
            System.out.println("3. Prescribe Medicine");
            System.out.println("4. View Patients");
            System.out.println("5. Logout");
            printLine();
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> viewAppointments();
                case 2 -> viewMedicines();
                case 3 -> prescribeMedicine();
                case 4 -> viewPatients();
                case 5 -> logout();
                default -> printError("Invalid choice. Try again.");
            }
        }
    }

    private void viewAppointments() {
        printHeader("APPOINTMENTS LIST");
        List<Appointment> appointments = doctorService.viewAppointments(doctorId);
        appointments.forEach(a -> System.out.println(a.getAppointmentId() + " - Patient ID: " + a.getPatientId() + " at " + a.getDateTime()));
    }

    private void viewMedicines() {
        printHeader("AVAILABLE MEDICINES");
        List<Medicine> medicines = doctorService.viewMedicines();
        medicines.forEach(m -> System.out.println(m.getId() + " - " + m.getName() + " (" + m.getQuantity() + ")"));
    }

    private void prescribeMedicine() {
        List<User> patients = adminService.viewPatients();
        patients.forEach(p -> System.out.println(p.getId() + " - " + p.getName()));
        System.out.print("Enter Patient ID: ");
        int patientId = scanner.nextInt();
        List<Medicine> medicines = adminService.viewMedicines();
        medicines.forEach(m -> System.out.println(m.getId() + " - " + m.getName() + " (" + m.getQuantity() + ")"));
        System.out.print("Enter Medicine ID: ");
        int medicineId = scanner.nextInt();
        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Recommendation: ");
        String recommendation = scanner.nextLine();

        if (doctorService.prescribeMedicine(doctorId, patientId, medicineId, quantity, recommendation)) {
            printSuccess("Medicine prescribed successfully.");
        } else {
            printError("Failed to prescribe medicine.");
        }
    }

    private void viewPatients() {
        printHeader("PATIENTS LIST");
        List<User> patients = doctorService.viewPatients();
        patients.forEach(p -> System.out.println(p.getId() + " - " + p.getName()));
    }

    // UI Formatting Helpers
    private void printHeader(String title) {
        System.out.println("\n- - - - - - - - - - - - - - - -");
        System.out.println("       " + title);
        System.out.println("- - - - - - - - - - - - - - - -");
    }

    private void printLine() {
        System.out.println("- - - - - - - - - - - - - - - -");
    }

    private void printSuccess(String message) {
        System.out.println("\u001B[32m✔ " + message + "\u001B[0m");
    }

    private void printError(String message) {
        System.out.println("\u001B[31m✘ " + message + "\u001B[0m");
    }
}
