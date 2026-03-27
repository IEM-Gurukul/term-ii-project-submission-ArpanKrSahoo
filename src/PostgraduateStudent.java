package src;

/**
 * Represents a postgraduate (Masters/PhD) student.
 * Extends Student — demonstrates multi-level INHERITANCE.
 */
public class PostgraduateStudent extends Student {
    private String researchArea;
    private int maxCourses;

    public PostgraduateStudent(String id, String name, String email,
                               String password, String department, String researchArea) {
        super(id, name, email, password, department);
        this.researchArea = researchArea;
        this.maxCourses = 4; // postgrads take fewer taught courses
    }

    public String getResearchArea() { return researchArea; }
    public void setResearchArea(String researchArea) { this.researchArea = researchArea; }

    public int getMaxCourses() { return maxCourses; }
    public void setMaxCourses(int max) { this.maxCourses = max; }

    public boolean canRegisterMore() {
        return getRegisteredCourseIds().size() < maxCourses;
    }

    /**
     * Overrides displayDetails — demonstrates POLYMORPHISM.
     */
    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Type          : Postgraduate");
        System.out.println("Research Area : " + researchArea);
        System.out.println("Max Courses   : " + maxCourses);
        System.out.println("==============================");
    }

    @Override
    public String getRole() {
        return "POSTGRAD";
    }

    @Override
    public String toFileString() {
        return "POSTGRAD|" + getId() + "|" + getName() + "|" + getEmail()
                + "|" + getPassword() + "|" + getDepartment() + "|"
                + (getRegisteredCourseIds().isEmpty() ? "NONE" : String.join(",", getRegisteredCourseIds()))
                + "|" + researchArea + "|" + maxCourses;
    }

    public static PostgraduateStudent fromFileString(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 9) return null;
        PostgraduateStudent s = new PostgraduateStudent(
                parts[1], parts[2], parts[3], parts[4], parts[5], parts[7]);
        s.setMaxCourses(Integer.parseInt(parts[8]));
        if (!parts[6].equals("NONE")) {
            for (String courseId : parts[6].split(",")) s.addCourse(courseId);
        }
        return s;
    }
}
