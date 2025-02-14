package Controllers.Interfaces;

import Models.User;

public interface UserController {
    void start(User user); // Start the session with authenticated user
    void register();
    void logout();
}
