package Controllers;

import Controllers.Interfaces.UserController;
import Models.*;
import Enums.UserRole;
import Services.UserService;
import Services.PatientService;
import java.util.List;
import java.util.Scanner;

public class PatientController implements UserController {
    private final UserService userService;
    private final PatientService patientService;
    private final Scanner scanner = new Scanner(System.in);
    private int patientId;
    private boolean isLoggedIn = false;

    public PatientController(UserService userService, PatientService patientService) {
        this.userService = userService;
        this.patientService = patientService;
        this.isLoggedIn = false;
    }

    public void start(User user) {
        this.patientId = user.getId();
        printHeader("Welcome, " + user.getName() + "!");
        isLoggedIn = true;
        patientMenu();  // Go directly to patient dashboard
    }


    @Override
    public void register() {
        printHeader("PATIENT REGISTRATION");
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        User newUser = new Patient(0, name, email, password);
        if (userService.registerUser(newUser)) {
            printSuccess("Patient registered successfully.");
        } else {
            printError("Registration failed. Email might already exist.");
        }
    }

    @Override
    public void logout() {
        printHeader("Logging out...");
        isLoggedIn = false;
    }

    private void patientMenu() {
        while (isLoggedIn) {
            printHeader("PATIENT DASHBOARD");
            System.out.println("1. Book Appointment");
            System.out.println("2. View Appointments");
            System.out.println("3. View Medicine Reports");
            System.out.println("4. Logout");
            printLine();
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> bookAppointment();
                case 2 -> viewAppointments();
                case 3 -> viewMedicineReports();
                case 4 -> logout();
                default -> printError("Invalid choice. Try again.");
            }
        }
    }

    private void bookAppointment() {
        System.out.print("Enter Doctor ID: ");
        int doctorId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Date & Time (YYYY-MM-DD HH:MM): ");
        String dateTime = scanner.nextLine();

        if (patientService.bookAppointment(doctorId, patientId, dateTime)) {
            printSuccess("Appointment booked successfully.");
        } else {
            printError("Failed to book appointment.");
        }
    }

    private void viewAppointments() {
        printHeader("YOUR APPOINTMENTS");
        List<Appointment> appointments = patientService.viewAppointments(patientId);
        appointments.forEach(a -> System.out.println(a.getAppointmentId() + " - Doctor ID: " + a.getDoctorId() + " at " + a.getDateTime()));
    }

    private void viewMedicineReports() {
        printHeader("YOUR MEDICINE REPORTS");
        List<MedicineReport> reports = patientService.viewMedicineReports(patientId);
        reports.forEach(r -> System.out.println("Report ID: " + r.getReportId() + " - Medicine: " + r.getMedicineName() + " - Recommendation: " + r.getRecommendation()));
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
