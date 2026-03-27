Student Course Registration System
Java OOP Project
====================================

HOW TO COMPILE:
  javac src/*.java

HOW TO RUN:
  java src.Main

DEMO CREDENTIALS (auto-seeded on first run):
  Admin ID     : A001  (password: admin123)
  Students     : S001, S002, S003  (password: pass)
  Instructors  : I001, I002  (password: pass123 / pass456)

FILE STRUCTURE:
  src/
    User.java                  - Abstract base class (Inheritance root)
    Student.java               - Student class (extends User)
    UndergraduateStudent.java  - Undergrad (extends Student)
    PostgraduateStudent.java   - Postgrad (extends Student)
    Instructor.java            - Instructor (extends User)
    Admin.java                 - Admin (extends User)
    Course.java                - Course entity
    Department.java            - Department entity
    RegistrationException.java - Custom exceptions hierarchy
    EnrollmentManager.java     - Central controller (business logic)
    FileManager.java           - File handling (save/load)
    MenuHelper.java            - Console input utility
    Main.java                  - Entry point + menu system

OOP CONCEPTS DEMONSTRATED:
  Encapsulation  → Private fields + getters/setters in all classes
  Inheritance    → User → Student → Undergrad/Postgrad; User → Instructor, Admin
  Polymorphism   → displayDetails() overridden in each User subclass
  Abstraction    → Complex logic hidden in EnrollmentManager methods

DATA PERSISTENCE:
  Data is saved to data/system_data.txt on exit and loaded on startup.
