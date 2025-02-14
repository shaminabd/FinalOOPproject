package Exceptions;

public class HospitalNotFoundException extends RuntimeException {
    public HospitalNotFoundException(String message) {
        super(message);
    }
}
