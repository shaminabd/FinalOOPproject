import Controllers.AdminController;
import Controllers.DoctorController;
import Controllers.PatientController;
import Models.User;
import Services.UserService;

import java.util.Scanner;

public class MyApplication {
    private final AdminController adminController;
    private final DoctorController doctorController;
    private final PatientController patientController;
    private final UserService userService;
    private final Scanner scanner = new Scanner(System.in);

    public MyApplication(UserService userService, AdminController adminController, DoctorController doctorController, PatientController patientController) {
        this.userService = userService;
        this.adminController = adminController;
        this.doctorController = doctorController;
        this.patientController = patientController;
    }

    public void start() {
        while (true) {
            try {
                printHeader("WELCOME TO HOSPITAL SYSTEM");
                System.out.println("1️⃣  Login");
                System.out.println("2️⃣  Register (Patient/Doctor)");
                System.out.println("3️⃣  Exit");
                printLine();
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> loginMenu();
                    case 2 -> registerMenu();
                    case 3 -> {
                        printHeader("Exiting...");
                        return;
                    }
                    default -> printError("Invalid choice! Please try again.");
                }
            } catch (Exception e) {
                printError("An unexpected error occurred: " + e.getMessage());
                scanner.nextLine();
            }
        }
    }

    private void loginMenu() {
        try {
            printHeader("LOGIN");
            System.out.print("Enter Email: ");
            String email = scanner.nextLine();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            User user = userService.login(email, password);

            if (user == null) {
                printError("Invalid credentials.");
                return;
            }

            switch (user.getRole()) {
                case ADMIN -> adminController.start(user);
                case DOCTOR -> doctorController.start(user);
                case PATIENT -> patientController.start(user);
                default -> printError("Unauthorized access.");
            }
        } catch (Exception e) {
            printError("Login failed: " + e.getMessage());
        }
    }

    private void registerMenu() {
        try {
            printHeader("REGISTER");
            System.out.println("1️⃣  Register as Patient");
            System.out.println("2️⃣  Register as Doctor");
            printLine();
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> patientController.register();
                case 2 -> doctorController.register();
                default -> printError("Invalid selection.");
            }
        } catch (Exception e) {
            printError("Registration failed: " + e.getMessage());
        }
    }

    private void printHeader(String title) {
        System.out.println("\n- - - - - - - - - - - - - - - -");
        System.out.println("       " + title);
        System.out.println("- - - - - - - - - - - - - - - -");
    }

    private void printLine() {
        System.out.println("- - - - - - - - - - - - - - - -");
    }

    private void printError(String message) {
        System.out.println("\u001B[31m✘ " + message + "\u001B[0m");
    }
}
