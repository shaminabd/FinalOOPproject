package Utils;

import Exceptions.UnauthorizedAccessException;
import Models.User;
import Enums.UserRole;

public class RoleBasedAccess {
    public static void checkAccess(User user, UserRole requiredRole) {
        if (user.getRole() != requiredRole) {
            throw new UnauthorizedAccessException("Access denied: " + user.getRole() + " cannot perform this action.");
        }
    }
}
