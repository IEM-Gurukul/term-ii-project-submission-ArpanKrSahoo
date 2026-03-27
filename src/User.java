/**
 * Abstract base class for all users in the system.
 * Demonstrates INHERITANCE and ABSTRACTION.
 */
public abstract class User {
    private String id;
    private String name;
    private String email;
    private String password;

    public User(String id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Getters and Setters (Encapsulation)
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    /**
     * Abstract method — each subclass implements its own display logic.
     * Demonstrates POLYMORPHISM via method overriding.
     */
    public abstract void displayDetails();

    /**
     * Abstract method — returns the role name for this user type.
     */
    public abstract String getRole();

    @Override
    public String toString() {
        return "[" + getRole() + "] ID: " + id + " | Name: " + name + " | Email: " + email;
    }
}
