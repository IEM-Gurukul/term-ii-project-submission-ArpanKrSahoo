package src;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an academic department.
 * Groups courses by department for better organization.
 */
public class Department {
    private String departmentId;
    private String name;
    private List<String> courseIds;

    public Department(String departmentId, String name) {
        this.departmentId = departmentId;
        this.name = name;
        this.courseIds = new ArrayList<>();
    }

    public String getDepartmentId() { return departmentId; }
    public void setDepartmentId(String departmentId) { this.departmentId = departmentId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<String> getCourseIds() { return courseIds; }

    public void addCourse(String courseId) {
        if (!courseIds.contains(courseId)) {
            courseIds.add(courseId);
        }
    }

    public boolean removeCourse(String courseId) {
        return courseIds.remove(courseId);
    }

    public void display() {
        System.out.println("==============================");
        System.out.println("DEPARTMENT: " + name + " (" + departmentId + ")");
        System.out.println("Courses    : " + (courseIds.isEmpty()
                ? "No courses"
                : String.join(", ", courseIds)));
        System.out.println("==============================");
    }

    public String toFileString() {
        String courses = courseIds.isEmpty() ? "NONE" : String.join(",", courseIds);
        return "DEPT|" + departmentId + "|" + name + "|" + courses;
    }

    public static Department fromFileString(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 4) return null;
        Department d = new Department(parts[1], parts[2]);
        if (!parts[3].equals("NONE")) {
            for (String courseId : parts[3].split(",")) d.addCourse(courseId);
        }
        return d;
    }

    @Override
    public String toString() {
        return departmentId + " - " + name;
    }
}
