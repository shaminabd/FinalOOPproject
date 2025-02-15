package Factories;

import Models.Medicine;

public class MedicineFactory {
    public static Medicine createMedicine(int id, String name, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        return new Medicine(id, name, quantity);
    }
}
