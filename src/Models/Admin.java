package Models;

import Enums.UserRole;

public class Admin extends User {
    public Admin(int id, String name, String email, String password) {
        super(id, name, email, password, UserRole.ADMIN);

    }

    public void manageSystem() {
        System.out.println("Admin " + this.getName() + " is managing the system.");
    }
}
