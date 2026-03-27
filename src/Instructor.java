package src;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an instructor in the system.
 * Extends User — demonstrates INHERITANCE.
 */
public class Instructor extends User {
    private String department;
    private List<String> assignedCourseIds;

    public Instructor(String id, String name, String email, String password, String department) {
        super(id, name, email, password);
        this.department = department;
        this.assignedCourseIds = new ArrayList<>();
    }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public List<String> getAssignedCourseIds() { return assignedCourseIds; }

    public void assignCourse(String courseId) {
        if (!assignedCourseIds.contains(courseId)) {
            assignedCourseIds.add(courseId);
        }
    }

    public boolean unassignCourse(String courseId) {
        return assignedCourseIds.remove(courseId);
    }

    /**
     * Polymorphic displayDetails — shows instructor-specific info.
     * Overrides User.displayDetails().
     */
    @Override
    public void displayDetails() {
        System.out.println("==============================");
        System.out.println("INSTRUCTOR PROFILE");
        System.out.println("==============================");
        System.out.println("ID         : " + getId());
        System.out.println("Name       : " + getName());
        System.out.println("Email      : " + getEmail());
        System.out.println("Department : " + department);
        System.out.println("Courses    : " + (assignedCourseIds.isEmpty()
                ? "No courses assigned"
                : String.join(", ", assignedCourseIds)));
        System.out.println("==============================");
    }

    @Override
    public String getRole() {
        return "INSTRUCTOR";
    }

    public String toFileString() {
        String courses = assignedCourseIds.isEmpty() ? "NONE" : String.join(",", assignedCourseIds);
        return "INSTRUCTOR|" + getId() + "|" + getName() + "|" + getEmail()
                + "|" + getPassword() + "|" + department + "|" + courses;
    }

    public static Instructor fromFileString(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 7) return null;
        Instructor i = new Instructor(parts[1], parts[2], parts[3], parts[4], parts[5]);
        if (!parts[6].equals("NONE")) {
            for (String courseId : parts[6].split(",")) i.assignCourse(courseId);
        }
        return i;
    }
}
