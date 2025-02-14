package Services;

import Models.User;
import Repositories.Interfaces.IUserRepository;
import Utils.EmailValidator;
import Exceptions.InvalidEmailException;
import Exceptions.UserNotFoundException;
import java.util.logging.Logger;

public class UserService {
    private static final Logger logger = Logger.getLogger(UserService.class.getName());
    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String email, String password) {
        try {
            User user = userRepository.getUserByEmail(email);

            if (user == null || !user.getPassword().equals(password)) {
                printError("Invalid email or password.");
                return null;
            }

            logger.info("User " + user.getEmail() + " logged in successfully.");
            return user;
        } catch (Exception e) {
            printError("An error occurred during login: " + e.getMessage());
            return null;
        }
    }

    public boolean registerUser(User user) {
        try {
            if (!EmailValidator.isValid(user.getEmail())) {
                throw new InvalidEmailException("Invalid email format: " + user.getEmail());
            }

            boolean success = userRepository.createUser(user);

            if (success) {
                logger.info("New user registered: " + user.getEmail());
                return true;
            } else {
                printError("Failed to register user.");
                return false;
            }
        } catch (Exception e) {
            printError("An error occurred during registration: " + e.getMessage());
            return false;
        }
    }

    private void printError(String message) {
        System.out.println("\u001B[31m✘ " + message + "\u001B[0m");
    }
}
