package src;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Utility class for reading user input from the console.
 * Centralizes all Scanner usage and input validation.
 */
public class MenuHelper {

    private static final Scanner scanner = new Scanner(System.in);

    /** Read a trimmed string input. Prompts the user first. */
    public static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    /** Read an integer input, re-prompting on invalid input. */
    public static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int val = scanner.nextInt();
                scanner.nextLine(); // consume newline
                return val;
            } catch (InputMismatchException e) {
                scanner.nextLine(); // discard invalid input
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    /** Print a divider line. */
    public static void divider() {
        System.out.println("──────────────────────────────────────────");
    }

    /** Print a section header. */
    public static void header(String title) {
        System.out.println("\n==========================================");
        System.out.println("  " + title);
        System.out.println("==========================================");
    }

    /** Pause and wait for the user to press Enter. */
    public static void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    /** Close the shared scanner (call only when the program exits). */
    public static void closeScanner() {
        scanner.close();
    }
}
