package Config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseInitializer {
    private static final String CREATE_USERS_TABLE = """
            CREATE TABLE IF NOT EXISTS users (
                id SERIAL PRIMARY KEY,
                name VARCHAR(100) NOT NULL,
                email VARCHAR(100) UNIQUE NOT NULL,
                password VARCHAR(100) NOT NULL,
                role VARCHAR(50) NOT NULL
            );
        """;

    private static final String CREATE_HOSPITALS_TABLE = """
            CREATE TABLE IF NOT EXISTS hospitals (
                hospital_id SERIAL PRIMARY KEY,
                hospital_name VARCHAR(255) NOT NULL UNIQUE
            );
        """;

    private static final String CREATE_DOCTORS_TABLE = """
            CREATE TABLE IF NOT EXISTS doctors (
                id SERIAL PRIMARY KEY REFERENCES users(id) ON DELETE CASCADE,
                hospital_id INT REFERENCES hospitals(hospital_id) ON DELETE SET NULL,
                specialization VARCHAR(50) NOT NULL
            );
        """;

    private static final String CREATE_PATIENTS_TABLE = """
            CREATE TABLE IF NOT EXISTS patients (
                id SERIAL PRIMARY KEY REFERENCES users(id) ON DELETE CASCADE,
                name VARCHAR(255) NOT NULL
            );
        """;

    private static final String CREATE_MEDICINES_TABLE = """
            CREATE TABLE IF NOT EXISTS medicines (
                id SERIAL PRIMARY KEY,
                name VARCHAR(255) NOT NULL UNIQUE,
                quantity INT NOT NULL CHECK (quantity >= 0)
            );
        """;

    private static final String CREATE_APPOINTMENTS_TABLE = """
            CREATE TABLE IF NOT EXISTS appointments (
                appointment_id SERIAL PRIMARY KEY,
                doctor_id INT REFERENCES doctors(id) ON DELETE CASCADE,
                patient_id INT REFERENCES patients(id) ON DELETE CASCADE,
                date_time TIMESTAMP NOT NULL UNIQUE
            );
        """;

    private static final String CREATE_MEDICINE_REPORTS_TABLE = """
            CREATE TABLE IF NOT EXISTS medicine_reports (
                report_id SERIAL PRIMARY KEY,
                doctor_id INT REFERENCES doctors(id) ON DELETE CASCADE,
                patient_id INT REFERENCES patients(id) ON DELETE CASCADE,
                medicine_id INT REFERENCES medicines(id) ON DELETE CASCADE,
                medicine_name VARCHAR(255) NOT NULL,
                quantity INT NOT NULL CHECK (quantity > 0),
                recommendation TEXT NOT NULL
            );
        """;

    private static final String INSERT_ADMIN = """
            INSERT INTO users (name, email, password, role)
            SELECT 'Admin', 'admin@admin.kz', 'Test123!', 'ADMIN'
            WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'admin@admin.kz');
        """;

    public static void initializeDatabase() {
        try (Connection connection = PostgresDB.getInstance().connect()) {
            if (connection == null) {
                System.err.println("❌ Database connection failed. Exiting initialization.");
                return;
            }

            try (PreparedStatement createUsersTable = connection.prepareStatement(CREATE_USERS_TABLE);
                 PreparedStatement createHospitalsTable = connection.prepareStatement(CREATE_HOSPITALS_TABLE);
                 PreparedStatement createDoctorsTable = connection.prepareStatement(CREATE_DOCTORS_TABLE);
                 PreparedStatement createPatientsTable = connection.prepareStatement(CREATE_PATIENTS_TABLE);
                 PreparedStatement createMedicinesTable = connection.prepareStatement(CREATE_MEDICINES_TABLE);
                 PreparedStatement createAppointmentsTable = connection.prepareStatement(CREATE_APPOINTMENTS_TABLE);
                 PreparedStatement createMedicineReportsTable = connection.prepareStatement(CREATE_MEDICINE_REPORTS_TABLE);
                 PreparedStatement insertAdmin = connection.prepareStatement(INSERT_ADMIN)) {

                createUsersTable.executeUpdate();
                createHospitalsTable.executeUpdate();
                createDoctorsTable.executeUpdate();
                createPatientsTable.executeUpdate();
                createMedicinesTable.executeUpdate();
                createAppointmentsTable.executeUpdate();
                createMedicineReportsTable.executeUpdate();
                insertAdmin.executeUpdate();

                System.out.println("✅ Database initialized successfully!");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
