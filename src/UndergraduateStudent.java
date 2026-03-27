package src;

/**
 * Represents an undergraduate student.
 * Extends Student — demonstrates multi-level INHERITANCE.
 */
public class UndergraduateStudent extends Student {
    private int year;       // Year 1, 2, 3, or 4
    private int maxCourses; // Undergrads are limited to fewer courses

    public UndergraduateStudent(String id, String name, String email,
                                String password, String department, int year) {
        super(id, name, email, password, department);
        this.year = year;
        this.maxCourses = 6; // default cap for undergrads
    }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public int getMaxCourses() { return maxCourses; }
    public void setMaxCourses(int maxCourses) { this.maxCourses = maxCourses; }

    public boolean canRegisterMore() {
        return getRegisteredCourseIds().size() < maxCourses;
    }

    /**
     * Overrides displayDetails to include year and course cap.
     * Demonstrates POLYMORPHISM.
     */
    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Type       : Undergraduate (Year " + year + ")");
        System.out.println("Max Courses: " + maxCourses);
        System.out.println("==============================");
    }

    @Override
    public String getRole() {
        return "UNDERGRAD";
    }

    @Override
    public String toFileString() {
        return "UNDERGRAD|" + getId() + "|" + getName() + "|" + getEmail()
                + "|" + getPassword() + "|" + getDepartment() + "|"
                + (getRegisteredCourseIds().isEmpty() ? "NONE" : String.join(",", getRegisteredCourseIds()))
                + "|" + year + "|" + maxCourses;
    }

    public static UndergraduateStudent fromFileString(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 9) return null;
        UndergraduateStudent s = new UndergraduateStudent(
                parts[1], parts[2], parts[3], parts[4], parts[5], Integer.parseInt(parts[7]));
        s.setMaxCourses(Integer.parseInt(parts[8]));
        if (!parts[6].equals("NONE")) {
            for (String courseId : parts[6].split(",")) s.addCourse(courseId);
        }
        return s;
    }
}
