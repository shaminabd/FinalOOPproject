package Utils;

import java.util.regex.Pattern;
import Exceptions.InvalidEmailException;

public class EmailValidator {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    public static boolean validateEmail(String email) {
        if (email == null || !Pattern.matches(EMAIL_REGEX, email)) {
            throw new InvalidEmailException("Invalid email format: " + email);
        }
        return true;
    }

    public static boolean isValid(String email) {
        return email != null && Pattern.matches(EMAIL_REGEX, email);
    }
}
