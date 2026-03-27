package src;

/**
 * Custom exceptions for the Student Course Registration System.
 * Demonstrates EXCEPTION HANDLING with meaningful error messages.
 */
public class RegistrationException extends Exception {

    public RegistrationException(String message) {
        super(message);
    }

    // ─── Specific sub-exceptions ─────────────────────────────────────────────

    /** Thrown when a student tries to register for a course they're already in. */
    public static class DuplicateRegistrationException extends RegistrationException {
        public DuplicateRegistrationException(String studentId, String courseId) {
            super("Student [" + studentId + "] is already registered for course [" + courseId + "].");
        }
    }

    /** Thrown when a course has no available seats. */
    public static class CourseFullException extends RegistrationException {
        public CourseFullException(String courseId) {
            super("Course [" + courseId + "] is full. No seats available.");
        }
    }

    /** Thrown when a student ID is not found in the system. */
    public static class StudentNotFoundException extends RegistrationException {
        public StudentNotFoundException(String studentId) {
            super("Student with ID [" + studentId + "] was not found.");
        }
    }

    /** Thrown when a course ID is not found in the system. */
    public static class CourseNotFoundException extends RegistrationException {
        public CourseNotFoundException(String courseId) {
            super("Course with ID [" + courseId + "] was not found.");
        }
    }

    /** Thrown when a student tries to drop a course they're not registered for. */
    public static class NotEnrolledException extends RegistrationException {
        public NotEnrolledException(String studentId, String courseId) {
            super("Student [" + studentId + "] is not enrolled in course [" + courseId + "].");
        }
    }

    /** Thrown when an instructor ID is not found. */
    public static class InstructorNotFoundException extends RegistrationException {
        public InstructorNotFoundException(String instructorId) {
            super("Instructor with ID [" + instructorId + "] was not found.");
        }
    }

    /** Thrown when a student exceeds their maximum allowed course load. */
    public static class CourseLoadExceededException extends RegistrationException {
        public CourseLoadExceededException(String studentId, int max) {
            super("Student [" + studentId + "] has reached the maximum course limit of " + max + ".");
        }
    }
}
