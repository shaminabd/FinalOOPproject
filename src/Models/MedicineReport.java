package Models;

public class MedicineReport {
    private int reportId;
    private int doctorId;
    private int patientId;
    private int medicineId;
    private String medicineName;
    private int quantity;
    private String recommendation;

    public MedicineReport(int reportId, int doctorId, int patientId, int medicineId,String medicineName, int quantity, String recommendation) {
        this.reportId = reportId;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.medicineId = medicineId;
        this.medicineName = medicineName;
        this.quantity = quantity;
        this.recommendation = recommendation;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public int getPatientId() {
        return patientId;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public int getQuantity() {
        return quantity;
    }
}
