package src;

import java.io.*;
import java.util.Map;

/**
 * FileManager — Utility layer for data persistence.
 *
 * Saves and loads all system data to/from a single text file.
 * Demonstrates FILE HANDLING using BufferedReader/BufferedWriter.
 *
 * File format: one record per line, pipe-delimited.
 * Each line starts with a type tag: STUDENT, UNDERGRAD, POSTGRAD,
 * INSTRUCTOR, ADMIN, COURSE, DEPT
 */
public class FileManager {

    private static final String DEFAULT_FILE = "data/system_data.txt";

    // ─── Save ──────────────────────────────────────────────────────────────────

    /**
     * Save all system data to the default file path.
     */
    public static void saveAll(EnrollmentManager manager) {
        saveAll(manager, DEFAULT_FILE);
    }

    /**
     * Save all system data to the specified file path.
     * Creates directories if they don't exist.
     */
    public static void saveAll(EnrollmentManager manager, String filePath) {
        File file = new File(filePath);
        // Create parent directory if needed
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            // Save departments
            for (Department dept : manager.getAllDepartments().values()) {
                writer.write(dept.toFileString());
                writer.newLine();
            }

            // Save courses
            for (Course course : manager.getAllCourses().values()) {
                writer.write(course.toFileString());
                writer.newLine();
            }

            // Save instructors
            for (Instructor instructor : manager.getAllInstructors().values()) {
                writer.write(instructor.toFileString());
                writer.newLine();
            }

            // Save students (all types)
            for (Student student : manager.getAllStudents().values()) {
                writer.write(student.toFileString());
                writer.newLine();
            }

            // Save admins
            for (Admin admin : manager.getAdmins().values()) {
                writer.write(admin.toFileString());
                writer.newLine();
            }

            System.out.println("Data saved successfully to: " + filePath);

        } catch (IOException e) {
            System.err.println("ERROR saving data: " + e.getMessage());
        }
    }

    // ─── Load ──────────────────────────────────────────────────────────────────

    /**
     * Load all system data from the default file path.
     */
    public static void loadAll(EnrollmentManager manager) {
        loadAll(manager, DEFAULT_FILE);
    }

    /**
     * Load all system data from the specified file path.
     * Silently skips if the file does not exist (first run).
     */
    public static void loadAll(EnrollmentManager manager, String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("No saved data found. Starting fresh.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int loaded = 0;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                // Determine type from the first segment
                String type = line.split("\\|")[0];

                switch (type) {
                    case "DEPT":
                        Department dept = Department.fromFileString(line);
                        if (dept != null) manager.addDepartment(dept);
                        break;

                    case "COURSE":
                        Course course = Course.fromFileString(line);
                        if (course != null) {
                            // Add directly to avoid double-registering with dept
                            manager.getAllCourses().put(course.getCourseId(), course);
                        }
                        break;

                    case "INSTRUCTOR":
                        Instructor instructor = Instructor.fromFileString(line);
                        if (instructor != null) manager.addInstructor(instructor);
                        break;

                    case "STUDENT":
                        Student student = Student.fromFileString(line);
                        if (student != null) manager.getAllStudents().put(student.getId(), student);
                        break;

                    case "UNDERGRAD":
                        UndergraduateStudent ug = UndergraduateStudent.fromFileString(line);
                        if (ug != null) manager.getAllStudents().put(ug.getId(), ug);
                        break;

                    case "POSTGRAD":
                        PostgraduateStudent pg = PostgraduateStudent.fromFileString(line);
                        if (pg != null) manager.getAllStudents().put(pg.getId(), pg);
                        break;

                    case "ADMIN":
                        Admin admin = Admin.fromFileString(line);
                        if (admin != null) manager.getAdmins().put(admin.getId(), admin);
                        break;

                    default:
                        System.err.println("WARNING: Unknown record type [" + type + "] — skipping.");
                }
                loaded++;
            }

            System.out.println("Data loaded successfully from: " + filePath
                    + " (" + loaded + " records)");

        } catch (IOException e) {
            System.err.println("ERROR loading data: " + e.getMessage());
        }
    }

    // ─── Utility ───────────────────────────────────────────────────────────────

    /**
     * Delete all saved data (reset the system).
     */
    public static boolean deleteData(String filePath) {
        File file = new File(filePath);
        if (file.exists() && file.delete()) {
            System.out.println("Data file deleted: " + filePath);
            return true;
        }
        return false;
    }
}
