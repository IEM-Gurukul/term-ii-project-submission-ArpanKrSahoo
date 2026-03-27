package src;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a student in the system.
 * Extends User — demonstrates INHERITANCE.
 * Uses ArrayList for registered courses — demonstrates COLLECTIONS.
 */
public class Student extends User {
    private String department;
    private List<String> registeredCourseIds; // stores Course IDs

    public Student(String id, String name, String email, String password, String department) {
        super(id, name, email, password);
        this.department = department;
        this.registeredCourseIds = new ArrayList<>();
    }

    // Getters and Setters
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public List<String> getRegisteredCourseIds() { return registeredCourseIds; }

    /**
     * Add a course ID to this student's registration list.
     * Called by EnrollmentManager after validation.
     */
    public void addCourse(String courseId) {
        registeredCourseIds.add(courseId);
    }

    /**
     * Remove a course ID from this student's registration list.
     * Called by EnrollmentManager when dropping a course.
     */
    public boolean removeCourse(String courseId) {
        return registeredCourseIds.remove(courseId);
    }

    /**
     * Check if the student is already registered for a given course.
     */
    public boolean isRegisteredFor(String courseId) {
        return registeredCourseIds.contains(courseId);
    }

    /**
     * Polymorphic displayDetails — shows student-specific info.
     * Overrides User.displayDetails().
     */
    @Override
    public void displayDetails() {
        System.out.println("==============================");
        System.out.println("STUDENT PROFILE");
        System.out.println("==============================");
        System.out.println("ID         : " + getId());
        System.out.println("Name       : " + getName());
        System.out.println("Email      : " + getEmail());
        System.out.println("Department : " + department);
        System.out.println("Courses    : " + (registeredCourseIds.isEmpty()
                ? "No courses registered"
                : String.join(", ", registeredCourseIds)));
        System.out.println("==============================");
    }

    @Override
    public String getRole() {
        return "STUDENT";
    }

    /**
     * Serialize student data to a file-friendly string format.
     * Format: STUDENT|id|name|email|password|department|course1,course2,...
     */
    public String toFileString() {
        String courses = registeredCourseIds.isEmpty() ? "NONE" : String.join(",", registeredCourseIds);
        return "STUDENT|" + getId() + "|" + getName() + "|" + getEmail()
                + "|" + getPassword() + "|" + department + "|" + courses;
    }

    /**
     * Deserialize a Student object from a file line.
     */
    public static Student fromFileString(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 7) return null;
        Student s = new Student(parts[1], parts[2], parts[3], parts[4], parts[5]);
        if (!parts[6].equals("NONE")) {
            for (String courseId : parts[6].split(",")) {
                s.addCourse(courseId);
            }
        }
        return s;
    }
}
