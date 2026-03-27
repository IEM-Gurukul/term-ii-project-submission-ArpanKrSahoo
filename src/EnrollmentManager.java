package src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * EnrollmentManager — the central controller of the system.
 *
 * Responsibilities:
 *  - Register students in courses (with all validation)
 *  - Drop students from courses
 *  - Search courses
 *  - Manage students, courses, instructors, departments
 *
 * Uses HashMap for O(1) lookup of students and courses.
 * Demonstrates ABSTRACTION — complex logic is hidden behind clean method calls.
 */
public class EnrollmentManager {

    // ─── Data Stores (Collections) ────────────────────────────────────────────
    private Map<String, Student>     students;     // studentId  → Student
    private Map<String, Course>      courses;      // courseId   → Course
    private Map<String, Instructor>  instructors;  // instructorId → Instructor
    private Map<String, Department>  departments;  // deptId     → Department
    private Map<String, Admin>       admins;       // adminId    → Admin

    public EnrollmentManager() {
        students    = new HashMap<>();
        courses     = new HashMap<>();
        instructors = new HashMap<>();
        departments = new HashMap<>();
        admins      = new HashMap<>();
    }

    // ─── Student Management ───────────────────────────────────────────────────

    public void addStudent(Student student) {
        students.put(student.getId(), student);
        System.out.println("Student added: " + student.getName());
    }

    public Student getStudent(String studentId)
            throws RegistrationException.StudentNotFoundException {
        Student s = students.get(studentId);
        if (s == null) throw new RegistrationException.StudentNotFoundException(studentId);
        return s;
    }

    public boolean removeStudent(String studentId)
            throws RegistrationException.StudentNotFoundException {
        Student s = getStudent(studentId);
        // Drop all their courses first
        for (String courseId : new ArrayList<>(s.getRegisteredCourseIds())) {
            Course c = courses.get(courseId);
            if (c != null) c.removeStudent(studentId);
        }
        students.remove(studentId);
        System.out.println("Student removed: " + studentId);
        return true;
    }

    public void updateStudent(String studentId, String newName, String newEmail)
            throws RegistrationException.StudentNotFoundException {
        Student s = getStudent(studentId);
        if (newName  != null && !newName.isEmpty())  s.setName(newName);
        if (newEmail != null && !newEmail.isEmpty()) s.setEmail(newEmail);
        System.out.println("Student updated: " + studentId);
    }

    public Map<String, Student> getAllStudents() { return students; }

    // ─── Course Management ────────────────────────────────────────────────────

    public void addCourse(Course course) {
        courses.put(course.getCourseId(), course);
        // Register with the department if it exists
        Department dept = departments.get(course.getDepartmentId());
        if (dept != null) dept.addCourse(course.getCourseId());
        System.out.println("Course added: " + course.getCourseName());
    }

    public Course getCourse(String courseId)
            throws RegistrationException.CourseNotFoundException {
        Course c = courses.get(courseId);
        if (c == null) throw new RegistrationException.CourseNotFoundException(courseId);
        return c;
    }

    public boolean removeCourse(String courseId)
            throws RegistrationException.CourseNotFoundException {
        Course c = getCourse(courseId);
        // Drop all enrolled students from this course
        for (String studentId : new ArrayList<>(c.getEnrolledStudentIds())) {
            Student s = students.get(studentId);
            if (s != null) s.removeCourse(courseId);
        }
        // Remove from department
        Department dept = departments.get(c.getDepartmentId());
        if (dept != null) dept.removeCourse(courseId);
        courses.remove(courseId);
        System.out.println("Course removed: " + courseId);
        return true;
    }

    public void updateCourse(String courseId, String newName, int newCapacity)
            throws RegistrationException.CourseNotFoundException {
        Course c = getCourse(courseId);
        if (newName != null && !newName.isEmpty()) c.setCourseName(newName);
        if (newCapacity > 0) c.setMaxCapacity(newCapacity);
        System.out.println("Course updated: " + courseId);
    }

    public Map<String, Course> getAllCourses() { return courses; }

    // ─── Instructor Management ────────────────────────────────────────────────

    public void addInstructor(Instructor instructor) {
        instructors.put(instructor.getId(), instructor);
        System.out.println("Instructor added: " + instructor.getName());
    }

    public Instructor getInstructor(String instructorId)
            throws RegistrationException.InstructorNotFoundException {
        Instructor i = instructors.get(instructorId);
        if (i == null) throw new RegistrationException.InstructorNotFoundException(instructorId);
        return i;
    }

    /**
     * Assign an instructor to a course.
     * Updates both the Instructor's course list and the Course's instructorId.
     */
    public void assignInstructorToCourse(String instructorId, String courseId)
            throws RegistrationException {
        Instructor instructor = getInstructor(instructorId);
        Course course = getCourse(courseId);
        course.setInstructorId(instructorId);
        instructor.assignCourse(courseId);
        System.out.println("Instructor [" + instructor.getName()
                + "] assigned to course [" + course.getCourseName() + "].");
    }

    public Map<String, Instructor> getAllInstructors() { return instructors; }

    // ─── Department Management ────────────────────────────────────────────────

    public void addDepartment(Department department) {
        departments.put(department.getDepartmentId(), department);
        System.out.println("Department added: " + department.getName());
    }

    public Department getDepartment(String departmentId) {
        return departments.get(departmentId);
    }

    public Map<String, Department> getAllDepartments() { return departments; }

    // ─── Admin Management ────────────────────────────────────────────────────

    public void addAdmin(Admin admin) {
        admins.put(admin.getId(), admin);
    }

    public Admin getAdmin(String adminId) {
        return admins.get(adminId);
    }

    // ─── Core Enrollment Logic ─────────────────────────────────────────────────

    /**
     * Register a student for a course.
     *
     * Validates:
     *   1. Student exists
     *   2. Course exists
     *   3. Student is not already registered
     *   4. Course has available seats
     *   5. Student has not exceeded their course load (for Undergrad/Postgrad)
     *
     * If all checks pass:
     *   - Adds courseId to student's list
     *   - Adds studentId to course's list
     */
    public void registerStudentForCourse(String studentId, String courseId)
            throws RegistrationException {
        // Step 1 & 2: Fetch student and course (throws if not found)
        Student student = getStudent(studentId);
        Course  course  = getCourse(courseId);

        // Step 3: Duplicate registration check
        if (student.isRegisteredFor(courseId)) {
            throw new RegistrationException.DuplicateRegistrationException(studentId, courseId);
        }

        // Step 4: Capacity check
        if (!course.hasSpace()) {
            throw new RegistrationException.CourseFullException(courseId);
        }

        // Step 5: Course load check for typed students
        if (student instanceof UndergraduateStudent) {
            UndergraduateStudent ug = (UndergraduateStudent) student;
            if (!ug.canRegisterMore()) {
                throw new RegistrationException.CourseLoadExceededException(studentId, ug.getMaxCourses());
            }
        } else if (student instanceof PostgraduateStudent) {
            PostgraduateStudent pg = (PostgraduateStudent) student;
            if (!pg.canRegisterMore()) {
                throw new RegistrationException.CourseLoadExceededException(studentId, pg.getMaxCourses());
            }
        }

        // All checks passed — perform registration
        student.addCourse(courseId);
        course.enrollStudent(studentId);
        System.out.println("SUCCESS: Student [" + student.getName()
                + "] registered for course [" + course.getCourseName() + "].");
    }

    /**
     * Drop a student from a course.
     * Validates that the student is actually enrolled before removing.
     */
    public void dropStudentFromCourse(String studentId, String courseId)
            throws RegistrationException {
        Student student = getStudent(studentId);
        Course  course  = getCourse(courseId);

        if (!student.isRegisteredFor(courseId)) {
            throw new RegistrationException.NotEnrolledException(studentId, courseId);
        }

        student.removeCourse(courseId);
        course.removeStudent(studentId);
        System.out.println("SUCCESS: Student [" + student.getName()
                + "] dropped course [" + course.getCourseName() + "].");
    }

    // ─── Search Features ───────────────────────────────────────────────────────

    /** Search courses by department ID. */
    public List<Course> searchCoursesByDepartment(String departmentId) {
        List<Course> result = new ArrayList<>();
        for (Course c : courses.values()) {
            if (c.getDepartmentId().equalsIgnoreCase(departmentId)) {
                result.add(c);
            }
        }
        return result;
    }

    /** Search courses by instructor ID. */
    public List<Course> searchCoursesByInstructor(String instructorId) {
        List<Course> result = new ArrayList<>();
        for (Course c : courses.values()) {
            if (instructorId.equals(c.getInstructorId())) {
                result.add(c);
            }
        }
        return result;
    }

    /** Search courses by name (case-insensitive partial match). */
    public List<Course> searchCoursesByName(String keyword) {
        List<Course> result = new ArrayList<>();
        for (Course c : courses.values()) {
            if (c.getCourseName().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(c);
            }
        }
        return result;
    }

    /** Get all courses a student is enrolled in. */
    public List<Course> getStudentTimetable(String studentId)
            throws RegistrationException.StudentNotFoundException {
        Student student = getStudent(studentId);
        List<Course> timetable = new ArrayList<>();
        for (String courseId : student.getRegisteredCourseIds()) {
            Course c = courses.get(courseId);
            if (c != null) timetable.add(c);
        }
        return timetable;
    }

    /** Display a student's full timetable. */
    public void displayStudentTimetable(String studentId)
            throws RegistrationException.StudentNotFoundException {
        Student student = getStudent(studentId);
        List<Course> timetable = getStudentTimetable(studentId);
        System.out.println("\n==============================");
        System.out.println("TIMETABLE FOR: " + student.getName());
        System.out.println("==============================");
        if (timetable.isEmpty()) {
            System.out.println("No courses registered.");
        } else {
            for (int i = 0; i < timetable.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, timetable.get(i));
            }
        }
        System.out.println("==============================\n");
    }

    // ─── Bulk Data Access (for FileManager) ───────────────────────────────────

    public Map<String, Admin>      getAdmins()      { return admins; }
}
