package src;

/**
 * Represents a system administrator.
 * Extends User — demonstrates INHERITANCE.
 * Admins manage students, courses, and instructors.
 */
public class Admin extends User {
    private String adminLevel; // e.g., "SUPER", "REGULAR"

    public Admin(String id, String name, String email, String password, String adminLevel) {
        super(id, name, email, password);
        this.adminLevel = adminLevel;
    }

    public String getAdminLevel() { return adminLevel; }
    public void setAdminLevel(String adminLevel) { this.adminLevel = adminLevel; }

    /**
     * Polymorphic displayDetails — shows admin-specific info.
     * Overrides User.displayDetails().
     */
    @Override
    public void displayDetails() {
        System.out.println("==============================");
        System.out.println("ADMINISTRATOR PROFILE");
        System.out.println("==============================");
        System.out.println("ID          : " + getId());
        System.out.println("Name        : " + getName());
        System.out.println("Email       : " + getEmail());
        System.out.println("Admin Level : " + adminLevel);
        System.out.println("==============================");
    }

    @Override
    public String getRole() {
        return "ADMIN";
    }

    public String toFileString() {
        return "ADMIN|" + getId() + "|" + getName() + "|" + getEmail()
                + "|" + getPassword() + "|" + adminLevel;
    }

    public static Admin fromFileString(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 6) return null;
        return new Admin(parts[1], parts[2], parts[3], parts[4], parts[5]);
    }
}
