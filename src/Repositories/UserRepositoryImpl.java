package Repositories;

import Repositories.Interfaces.IUserRepository;
import Models.User;
import Config.PostgresDB;
import Enums.UserRole;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements IUserRepository {
    private final Connection connection;

    public UserRepositoryImpl() {
        this.connection = PostgresDB.getInstance().connect();
    }

    @Override
    public User getUserById(int id) {
        String query = "SELECT * FROM users WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        UserRole.valueOf(rs.getString("role").toUpperCase())
                );
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving user by ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        UserRole.valueOf(rs.getString("role").toUpperCase())
                );
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving user by email: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        UserRole.valueOf(rs.getString("role").toUpperCase())
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving all users: " + e.getMessage());
        }
        return users;
    }

    @Override
    public boolean createUser(User user) {
        String query = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole().name());
            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {

                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error creating user: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        String query = "UPDATE users SET name = ?, email = ?, password = ?, role = ? WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword()); // Plaintext password
            statement.setString(4, user.getRole().name());
            statement.setInt(5, user.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteUser(int id) {
        String query = "DELETE FROM users WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
        }
        return false;
    }
}
