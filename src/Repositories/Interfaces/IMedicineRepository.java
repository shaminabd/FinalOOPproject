package Repositories.Interfaces;

import Models.Medicine;
import java.util.List;

public interface IMedicineRepository {
    Medicine getMedicineById(int id);
    Medicine getMedicineByName(String name);
    List<Medicine> getAllMedicines();
    boolean updateStock(int medicineId, int newQuantity);
    boolean createMedicine(Medicine medicine);
    boolean deleteMedicine(int id);
}
