package src;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an academic course in the system.
 * Uses encapsulation — all fields are private with getters/setters.
 * Uses ArrayList to track enrolled students.
 */
public class Course {
    private String courseId;
    private String courseName;
    private String instructorId;   // linked to Instructor
    private String departmentId;   // linked to Department
    private int maxCapacity;
    private int creditHours;
    private List<String> enrolledStudentIds;

    public Course(String courseId, String courseName, String departmentId,
                  int maxCapacity, int creditHours) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.departmentId = departmentId;
        this.maxCapacity = maxCapacity;
        this.creditHours = creditHours;
        this.enrolledStudentIds = new ArrayList<>();
        this.instructorId = null;
    }

    // Getters and Setters
    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public String getInstructorId() { return instructorId; }
    public void setInstructorId(String instructorId) { this.instructorId = instructorId; }

    public String getDepartmentId() { return departmentId; }
    public void setDepartmentId(String departmentId) { this.departmentId = departmentId; }

    public int getMaxCapacity() { return maxCapacity; }
    public void setMaxCapacity(int maxCapacity) { this.maxCapacity = maxCapacity; }

    public int getCreditHours() { return creditHours; }
    public void setCreditHours(int creditHours) { this.creditHours = creditHours; }

    public List<String> getEnrolledStudentIds() { return enrolledStudentIds; }

    /**
     * Check if there is space available in this course.
     */
    public boolean hasSpace() {
        return enrolledStudentIds.size() < maxCapacity;
    }

    public int getAvailableSeats() {
        return maxCapacity - enrolledStudentIds.size();
    }

    public int getEnrolledCount() {
        return enrolledStudentIds.size();
    }

    /**
     * Enroll a student in this course.
     * Returns false if course is full.
     */
    public boolean enrollStudent(String studentId) {
        if (!hasSpace() || enrolledStudentIds.contains(studentId)) return false;
        enrolledStudentIds.add(studentId);
        return true;
    }

    /**
     * Remove a student from this course.
     */
    public boolean removeStudent(String studentId) {
        return enrolledStudentIds.remove(studentId);
    }

    /**
     * Check if a student is enrolled in this course.
     */
    public boolean isEnrolled(String studentId) {
        return enrolledStudentIds.contains(studentId);
    }

    public void display() {
        System.out.println("==============================");
        System.out.println("COURSE DETAILS");
        System.out.println("==============================");
        System.out.println("Course ID    : " + courseId);
        System.out.println("Name         : " + courseName);
        System.out.println("Department   : " + departmentId);
        System.out.println("Instructor   : " + (instructorId != null ? instructorId : "Not assigned"));
        System.out.println("Credit Hours : " + creditHours);
        System.out.println("Capacity     : " + enrolledStudentIds.size() + "/" + maxCapacity);
        System.out.println("Seats Left   : " + getAvailableSeats());
        System.out.println("==============================");
    }

    public String toFileString() {
        String students = enrolledStudentIds.isEmpty() ? "NONE" : String.join(",", enrolledStudentIds);
        String instr = (instructorId == null) ? "NONE" : instructorId;
        return "COURSE|" + courseId + "|" + courseName + "|" + departmentId
                + "|" + instr + "|" + maxCapacity + "|" + creditHours + "|" + students;
    }

    public static Course fromFileString(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 8) return null;
        Course c = new Course(parts[1], parts[2], parts[3],
                Integer.parseInt(parts[5]), Integer.parseInt(parts[6]));
        if (!parts[4].equals("NONE")) c.setInstructorId(parts[4]);
        if (!parts[7].equals("NONE")) {
            for (String sid : parts[7].split(",")) c.enrollStudent(sid);
        }
        return c;
    }

    @Override
    public String toString() {
        return courseId + " | " + courseName + " | Dept: " + departmentId
                + " | Seats: " + getAvailableSeats() + "/" + maxCapacity;
    }
}
