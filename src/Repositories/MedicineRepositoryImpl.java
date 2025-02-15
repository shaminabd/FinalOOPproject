package Repositories;

import Repositories.Interfaces.IMedicineRepository;
import Models.Medicine;
import Config.PostgresDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicineRepositoryImpl implements IMedicineRepository {
    private final Connection connection;

    public MedicineRepositoryImpl() {
        this.connection = PostgresDB.getInstance().connect();
    }

    @Override
    public Medicine getMedicineById(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM medicines WHERE id = ?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new Medicine(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("quantity")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Medicine getMedicineByName(String name) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM medicines WHERE name = ?");
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new Medicine(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("quantity")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Medicine> getAllMedicines() {
        List<Medicine> medicines = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM medicines");
            while (rs.next()) {
                medicines.add(new Medicine(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("quantity")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicines;
    }

    @Override
    public boolean updateStock(int medicineId, int newQuantity) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE medicines SET quantity = ? WHERE id = ?");
            statement.setInt(1, newQuantity);
            statement.setInt(2, medicineId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean createMedicine(Medicine medicine) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO medicines (name, quantity) VALUES (?, ?)");
            statement.setString(1, medicine.getName());
            statement.setInt(2, medicine.getQuantity());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteMedicine(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM medicines WHERE id = ?");
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
