package Config;

import Config.Interfaces.IDB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresDB implements IDB {
    private static PostgresDB instance;
    private static Connection connection;
    private static final String URL = "jdbc:postgresql://localhost:5432/hospital_demo_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";

    private PostgresDB() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Connected to the database successfully!");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ PostgreSQL JDBC Driver not found!");
        } catch (SQLException e) {
            System.err.println("❌ Error connecting to PostgreSQL: " + e.getMessage());
        }
    }

    public static PostgresDB getInstance() {
        if (instance == null) {
            instance = new PostgresDB();
        }
        return instance;
    }

    @Override
    public Connection connect() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✅ Reconnected to the database.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error re-establishing connection: " + e.getMessage());
        }
        return connection;
    }

    @Override
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✅ Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error closing database connection: " + e.getMessage());
        }
    }
}
