package Factories;

import Models.MedicineReport;

public class MedicineReportFactory {
    public static MedicineReport createMedicineReport(int reportId, int doctorId, int patientId, int medicineId, String medicineName, int quantity, String recommendation) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Medicine quantity must be greater than zero.");
        }
        return new MedicineReport(reportId, doctorId, patientId, medicineId, medicineName, quantity, recommendation);
    }
}
