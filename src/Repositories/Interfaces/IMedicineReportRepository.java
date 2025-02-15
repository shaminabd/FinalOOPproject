package Repositories.Interfaces;

import Models.MedicineReport;
import java.util.List;

public interface IMedicineReportRepository {
    MedicineReport getReportById(int id);
    List<MedicineReport> getReportsByDoctorId(int doctorId);
    List<MedicineReport> getReportsByPatientId(int patientId);
    List<MedicineReport> getAllReports(); // 🔹 NEW: Fetch all reports (for Admin)
    boolean createReport(MedicineReport report);
    boolean updateReport(int reportId, MedicineReport updatedReport); // 🔹 NEW: Update a report
    boolean deleteReport(int reportId); // 🔹 NEW: Delete a report
}
