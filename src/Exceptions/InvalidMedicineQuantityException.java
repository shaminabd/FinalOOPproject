package Exceptions;

public class InvalidMedicineQuantityException extends RuntimeException {
    public InvalidMedicineQuantityException(String message) {
        super(message);
    }
}
