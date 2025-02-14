package Controllers;

import Controllers.Interfaces.UserController;
import Models.*;
import Enums.UserRole;
import Services.UserService;
import Services.AdminService;
import java.util.List;
import java.util.Scanner;

public class AdminController implements UserController {
    private final UserService userService;
    private final AdminService adminService;
    private final Scanner scanner = new Scanner(System.in);
    private boolean isLoggedIn = false;

    public AdminController(UserService userService, AdminService adminService) {
        this.userService = userService;
        this.adminService = adminService;
        this.isLoggedIn = false;
    }


    @Override
    public void register() {
        printError("Admin registration is not allowed.");
    }

    @Override
    public void logout() {
        printHeader("Logging out...");
        isLoggedIn = false;
    }

    public void start(User user) {
        printHeader("Welcome, " + user.getName() + " (Admin)!");
        isLoggedIn = true;
        adminMenu(); // Directly show admin dashboard
    }

    private void adminMenu() {
        while (isLoggedIn) {
            printHeader("ADMIN DASHBOARD");
            System.out.println("1. View Hospitals");
            System.out.println("2. Create Hospital");
            System.out.println("3. Delete Hospital");
            System.out.println("4. View Doctors");
            System.out.println("5. View Patients");
            System.out.println("6. Manage Medicines");
            System.out.println("7. View Appointments");
            System.out.println("8. Logout");
            printLine();
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> viewHospitals();
                case 2 -> createHospital();
                case 3 -> deleteHospital();
                case 4 -> viewDoctors();
                case 5 -> viewPatients();
                case 6 -> manageMedicines();
                case 7 -> viewAppointments();
                case 8 -> logout();
                default -> printError("Invalid choice. Try again.");
            }
        }
    }

    private void viewHospitals() {
        printHeader("HOSPITALS LIST");
        List<Hospital> hospitals = adminService.viewHospitals();
        hospitals.forEach(h -> System.out.println(h.getHospitalId() + " - " + h.getHospitalName()));
    }

    private void createHospital() {
        printHeader("CREATE NEW HOSPITAL");
        System.out.print("Enter hospital name: ");
        String name = scanner.nextLine();
        if (adminService.createHospital(name)) {
            printSuccess("Hospital created successfully.");
        } else {
            printError("Error creating hospital.");
        }
    }

    private void deleteHospital() {
        printHeader("DELETE HOSPITAL");
        System.out.print("Enter hospital ID to delete: ");
        int id = scanner.nextInt();
        if (adminService.deleteHospital(id)) {
            printSuccess("Hospital deleted successfully.");
        } else {
            printError("Error deleting hospital.");
        }
    }

    private void viewDoctors() {
        printHeader("DOCTORS LIST");
        List<User> doctors = adminService.viewDoctors();
        doctors.forEach(d -> System.out.println(d.getId() + " - " + d.getName()));
    }

    private void viewPatients() {
        printHeader("PATIENTS LIST");
        List<User> patients = adminService.viewPatients();
        patients.forEach(p -> System.out.println(p.getId() + " - " + p.getName()));
    }

    private void manageMedicines() {
        printHeader("MANAGE MEDICINES");
        List<Medicine> medicines = adminService.viewMedicines();
        medicines.forEach(m -> System.out.println(m.getId() + " - " + m.getName() + " (" + m.getQuantity() + ")"));
    }

    private void viewAppointments() {
        printHeader("APPOINTMENTS LIST");
        List<Appointment> appointments = adminService.viewAppointments();
        appointments.forEach(a -> System.out.println(a.getAppointmentId() + " - Doctor: " + a.getDoctorId() + ", Patient: " + a.getPatientId() + ", Time: " + a.getDateTime()));
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
        System.out.println("\u001B[32m✔ " + message + "\u001B[0m"); // Green text
    }

    private void printError(String message) {
        System.out.println("\u001B[31m✘ " + message + "\u001B[0m"); // Red text
    }
}
