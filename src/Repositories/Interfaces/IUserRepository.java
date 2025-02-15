package Repositories.Interfaces;

import Models.User;
import java.util.List;

public interface IUserRepository {
    User getUserById(int id);
    User getUserByEmail(String email);
    List<User> getAllUsers();
    boolean createUser(User user);
    boolean updateUser(User user);
    boolean deleteUser(int id);
}
