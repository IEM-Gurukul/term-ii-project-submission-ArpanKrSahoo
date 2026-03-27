package src;

import java.util.List;
import java.util.Map;

/**
 * Main entry point for the Student Course Registration System.
 *
 * Provides a role-based console menu:
 *   1. Student Menu
 *   2. Admin Menu
 *   3. Instructor Menu
 *
 * Data is loaded on startup and saved on exit.
 */
public class Main {

    private static EnrollmentManager manager = new EnrollmentManager();

    public static void main(String[] args) {
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║  Student Course Registration System      ║");
        System.out.println("║  Java OOP Project                        ║");
        System.out.println("╚══════════════════════════════════════════╝");

        // Load saved data on startup
        FileManager.loadAll(manager);

        // Seed demo data if the system is empty (first run)
        if (manager.getAllStudents().isEmpty()) {
            seedDemoData();
        }

        boolean running = true;
        while (running) {
            MenuHelper.header("MAIN MENU — Select Your Role");
            System.out.println("  1. Student");
            System.out.println("  2. Administrator");
            System.out.println("  3. Instructor");
            System.out.println("  0. Exit");
            MenuHelper.divider();

            int choice = MenuHelper.readInt("Enter choice: ");
            switch (choice) {
                case 1: studentMenu(); break;
                case 2: adminMenu();   break;
                case 3: instructorMenu(); break;
                case 0:
                    FileManager.saveAll(manager);
                    System.out.println("\nGoodbye! Data saved.");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }

        MenuHelper.closeScanner();
    }

    // ─── Student Menu ─────────────────────────────────────────────────────────

    private static void studentMenu() {
        String studentId = MenuHelper.readString("Enter your Student ID: ");
        Student student;
        try {
            student = manager.getStudent(studentId);
        } catch (RegistrationException.StudentNotFoundException e) {
            System.out.println("ERROR: " + e.getMessage());
            return;
        }

        boolean back = false;
        while (!back) {
            MenuHelper.header("STUDENT MENU — " + student.getName());
            System.out.println("  1. View My Profile");
            System.out.println("  2. View My Timetable");
            System.out.println("  3. View Available Courses");
            System.out.println("  4. Register for a Course");
            System.out.println("  5. Drop a Course");
            System.out.println("  0. Back to Main Menu");
            MenuHelper.divider();

            int choice = MenuHelper.readInt("Enter choice: ");
            try {
                switch (choice) {
                    case 1:
                        student.displayDetails();
                        break;
                    case 2:
                        manager.displayStudentTimetable(studentId);
                        break;
                    case 3:
                        listAllCourses();
                        break;
                    case 4:
                        String courseIdReg = MenuHelper.readString("Enter Course ID to register: ");
                        manager.registerStudentForCourse(studentId, courseIdReg);
                        break;
                    case 5:
                        String courseIdDrop = MenuHelper.readString("Enter Course ID to drop: ");
                        manager.dropStudentFromCourse(studentId, courseIdDrop);
                        break;
                    case 0:
                        back = true;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (RegistrationException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
            if (!back) MenuHelper.pause();
        }
    }

    // ─── Admin Menu ───────────────────────────────────────────────────────────

    private static void adminMenu() {
        String adminId = MenuHelper.readString("Enter Admin ID: ");
        Admin admin = manager.getAdmin(adminId);
        if (admin == null) {
            System.out.println("ERROR: Admin not found.");
            return;
        }

        boolean back = false;
        while (!back) {
            MenuHelper.header("ADMIN MENU — " + admin.getName());
            System.out.println("  --- Student Management ---");
            System.out.println("  1.  Add Student");
            System.out.println("  2.  Remove Student");
            System.out.println("  3.  Update Student");
            System.out.println("  4.  List All Students");
            System.out.println("  --- Course Management ---");
            System.out.println("  5.  Add Course");
            System.out.println("  6.  Remove Course");
            System.out.println("  7.  Update Course");
            System.out.println("  8.  List All Courses");
            System.out.println("  9.  Search Courses");
            System.out.println("  --- Instructor Management ---");
            System.out.println("  10. Add Instructor");
            System.out.println("  11. Assign Instructor to Course");
            System.out.println("  12. List All Instructors");
            System.out.println("  --- Departments ---");
            System.out.println("  13. Add Department");
            System.out.println("  14. List All Departments");
            System.out.println("  0.  Back to Main Menu");
            MenuHelper.divider();

            int choice = MenuHelper.readInt("Enter choice: ");
            try {
                switch (choice) {
                    case 1:  addStudentFlow();  break;
                    case 2:  removeStudentFlow(); break;
                    case 3:  updateStudentFlow(); break;
                    case 4:  listAllStudents(); break;
                    case 5:  addCourseFlow();  break;
                    case 6:  removeCourseFlow(); break;
                    case 7:  updateCourseFlow(); break;
                    case 8:  listAllCourses(); break;
                    case 9:  searchCoursesFlow(); break;
                    case 10: addInstructorFlow(); break;
                    case 11: assignInstructorFlow(); break;
                    case 12: listAllInstructors(); break;
                    case 13: addDepartmentFlow(); break;
                    case 14: listAllDepartments(); break;
                    case 0:  back = true; break;
                    default: System.out.println("Invalid choice.");
                }
            } catch (RegistrationException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
            if (!back) MenuHelper.pause();
        }
    }

    // ─── Instructor Menu ──────────────────────────────────────────────────────

    private static void instructorMenu() {
        String instructorId = MenuHelper.readString("Enter your Instructor ID: ");
        Instructor instructor;
        try {
            instructor = manager.getInstructor(instructorId);
        } catch (RegistrationException.InstructorNotFoundException e) {
            System.out.println("ERROR: " + e.getMessage());
            return;
        }

        boolean back = false;
        while (!back) {
            MenuHelper.header("INSTRUCTOR MENU — " + instructor.getName());
            System.out.println("  1. View My Profile");
            System.out.println("  2. View My Assigned Courses");
            System.out.println("  3. View Enrolled Students for a Course");
            System.out.println("  0. Back to Main Menu");
            MenuHelper.divider();

            int choice = MenuHelper.readInt("Enter choice: ");
            try {
                switch (choice) {
                    case 1:
                        instructor.displayDetails();
                        break;
                    case 2:
                        List<Course> myCourses = manager.searchCoursesByInstructor(instructorId);
                        if (myCourses.isEmpty()) {
                            System.out.println("No courses assigned.");
                        } else {
                            for (Course c : myCourses) c.display();
                        }
                        break;
                    case 3:
                        String cId = MenuHelper.readString("Enter Course ID: ");
                        Course course = manager.getCourse(cId);
                        System.out.println("Enrolled students in [" + course.getCourseName() + "]:");
                        if (course.getEnrolledStudentIds().isEmpty()) {
                            System.out.println("  No students enrolled.");
                        } else {
                            for (String sid : course.getEnrolledStudentIds()) {
                                try {
                                    Student s = manager.getStudent(sid);
                                    System.out.println("  - " + s.getName() + " (" + sid + ")");
                                } catch (RegistrationException.StudentNotFoundException ex) {
                                    System.out.println("  - " + sid + " (data unavailable)");
                                }
                            }
                        }
                        break;
                    case 0:
                        back = true;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (RegistrationException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
            if (!back) MenuHelper.pause();
        }
    }

    // ─── Admin Sub-flows ──────────────────────────────────────────────────────

    private static void addStudentFlow() {
        MenuHelper.header("ADD STUDENT");
        System.out.println("Type: 1=General  2=Undergraduate  3=Postgraduate");
        int type = MenuHelper.readInt("Select type: ");
        String id   = MenuHelper.readString("Student ID : ");
        String name = MenuHelper.readString("Name       : ");
        String email= MenuHelper.readString("Email      : ");
        String pass = MenuHelper.readString("Password   : ");
        String dept = MenuHelper.readString("Department : ");

        switch (type) {
            case 2:
                int year = MenuHelper.readInt("Year (1-4): ");
                manager.addStudent(new UndergraduateStudent(id, name, email, pass, dept, year));
                break;
            case 3:
                String research = MenuHelper.readString("Research Area: ");
                manager.addStudent(new PostgraduateStudent(id, name, email, pass, dept, research));
                break;
            default:
                manager.addStudent(new Student(id, name, email, pass, dept));
        }
    }

    private static void removeStudentFlow() throws RegistrationException {
        String id = MenuHelper.readString("Student ID to remove: ");
        manager.removeStudent(id);
    }

    private static void updateStudentFlow() throws RegistrationException {
        String id      = MenuHelper.readString("Student ID to update: ");
        String newName = MenuHelper.readString("New Name (leave blank to keep): ");
        String newEmail= MenuHelper.readString("New Email (leave blank to keep): ");
        manager.updateStudent(id, newName, newEmail);
    }

    private static void listAllStudents() {
        MenuHelper.header("ALL STUDENTS");
        Map<String, Student> all = manager.getAllStudents();
        if (all.isEmpty()) {
            System.out.println("No students registered.");
        } else {
            for (Student s : all.values()) {
                System.out.println(s);
            }
        }
    }

    private static void addCourseFlow() {
        MenuHelper.header("ADD COURSE");
        String id       = MenuHelper.readString("Course ID    : ");
        String name     = MenuHelper.readString("Course Name  : ");
        String dept     = MenuHelper.readString("Department ID: ");
        int    capacity = MenuHelper.readInt("Max Capacity : ");
        int    credits  = MenuHelper.readInt("Credit Hours : ");
        manager.addCourse(new Course(id, name, dept, capacity, credits));
    }

    private static void removeCourseFlow() throws RegistrationException {
        String id = MenuHelper.readString("Course ID to remove: ");
        manager.removeCourse(id);
    }

    private static void updateCourseFlow() throws RegistrationException {
        String id      = MenuHelper.readString("Course ID to update: ");
        String newName = MenuHelper.readString("New Name (blank to keep): ");
        int    newCap  = MenuHelper.readInt("New Capacity (0 to keep): ");
        manager.updateCourse(id, newName, newCap);
    }

    private static void listAllCourses() {
        MenuHelper.header("ALL COURSES");
        Map<String, Course> all = manager.getAllCourses();
        if (all.isEmpty()) {
            System.out.println("No courses available.");
        } else {
            for (Course c : all.values()) {
                System.out.println(c);
            }
        }
    }

    private static void searchCoursesFlow() {
        MenuHelper.header("SEARCH COURSES");
        System.out.println("Search by: 1=Name  2=Department  3=Instructor");
        int type    = MenuHelper.readInt("Select: ");
        String term = MenuHelper.readString("Search term: ");
        List<Course> results;

        switch (type) {
            case 2:  results = manager.searchCoursesByDepartment(term);  break;
            case 3:  results = manager.searchCoursesByInstructor(term);  break;
            default: results = manager.searchCoursesByName(term);
        }

        if (results.isEmpty()) {
            System.out.println("No courses found.");
        } else {
            System.out.println("Found " + results.size() + " course(s):");
            for (Course c : results) c.display();
        }
    }

    private static void addInstructorFlow() {
        MenuHelper.header("ADD INSTRUCTOR");
        String id   = MenuHelper.readString("Instructor ID : ");
        String name = MenuHelper.readString("Name          : ");
        String email= MenuHelper.readString("Email         : ");
        String pass = MenuHelper.readString("Password      : ");
        String dept = MenuHelper.readString("Department    : ");
        manager.addInstructor(new Instructor(id, name, email, pass, dept));
    }

    private static void assignInstructorFlow() throws RegistrationException {
        String iId = MenuHelper.readString("Instructor ID: ");
        String cId = MenuHelper.readString("Course ID    : ");
        manager.assignInstructorToCourse(iId, cId);
    }

    private static void listAllInstructors() {
        MenuHelper.header("ALL INSTRUCTORS");
        Map<String, Instructor> all = manager.getAllInstructors();
        if (all.isEmpty()) {
            System.out.println("No instructors in system.");
        } else {
            for (Instructor i : all.values()) System.out.println(i);
        }
    }

    private static void addDepartmentFlow() {
        MenuHelper.header("ADD DEPARTMENT");
        String id   = MenuHelper.readString("Department ID  : ");
        String name = MenuHelper.readString("Department Name: ");
        manager.addDepartment(new Department(id, name));
    }

    private static void listAllDepartments() {
        MenuHelper.header("ALL DEPARTMENTS");
        Map<String, Department> all = manager.getAllDepartments();
        if (all.isEmpty()) {
            System.out.println("No departments.");
        } else {
            for (Department d : all.values()) d.display();
        }
    }

    // ─── Demo Data Seed ───────────────────────────────────────────────────────

    /**
     * Seeds the system with demo data on first run.
     * Useful for testing without having to input everything manually.
     */
    private static void seedDemoData() {
        System.out.println("\n[INFO] Seeding demo data...");

        // Departments
        manager.addDepartment(new Department("CS", "Computer Science"));
        manager.addDepartment(new Department("EE", "Electrical Engineering"));

        // Courses
        manager.addCourse(new Course("CS101", "Introduction to Programming", "CS", 30, 3));
        manager.addCourse(new Course("CS201", "Data Structures", "CS", 25, 3));
        manager.addCourse(new Course("CS301", "Database Systems", "CS", 20, 3));
        manager.addCourse(new Course("EE101", "Circuit Analysis", "EE", 30, 4));

        // Instructors
        manager.addInstructor(new Instructor("I001", "Dr. Alice Smith", "alice@uni.ac", "pass123", "CS"));
        manager.addInstructor(new Instructor("I002", "Prof. Bob Jones", "bob@uni.ac",  "pass456", "EE"));

        // Assign instructors
        try {
            manager.assignInstructorToCourse("I001", "CS101");
            manager.assignInstructorToCourse("I001", "CS201");
            manager.assignInstructorToCourse("I002", "EE101");
        } catch (RegistrationException e) {
            System.err.println("Seed error: " + e.getMessage());
        }

        // Students
        manager.addStudent(new UndergraduateStudent("S001", "John Doe",   "john@uni.ac",   "pass", "CS", 2));
        manager.addStudent(new UndergraduateStudent("S002", "Jane Smith",  "jane@uni.ac",   "pass", "EE", 1));
        manager.addStudent(new PostgraduateStudent( "S003", "Ravi Patel",  "ravi@uni.ac",   "pass", "CS", "Machine Learning"));

        // Admin
        manager.addAdmin(new Admin("A001", "System Admin", "admin@uni.ac", "admin123", "SUPER"));

        // Register some students
        try {
            manager.registerStudentForCourse("S001", "CS101");
            manager.registerStudentForCourse("S001", "CS201");
            manager.registerStudentForCourse("S002", "EE101");
            manager.registerStudentForCourse("S003", "CS301");
        } catch (RegistrationException e) {
            System.err.println("Seed error: " + e.getMessage());
        }

        System.out.println("[INFO] Demo data loaded. Admin ID: A001 | Students: S001, S002, S003\n");
    }
}
