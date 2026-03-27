[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/pG3gvzt-)
# PCCCS495 – Term II Project

## Project Title
Student Course Registration System
---

## Problem Statement (max 150 words)
Traditional course registration processes often suffer from manual errors, lack of real-time capacity tracking, and difficulty in managing student-instructor assignments. This project implements a Student Course Registration System to automate enrollment, prevent duplicate registrations, and manage academic data efficiently. By providing distinct interfaces for Students, Administrators, and Instructors, the system ensures data integrity, prevents over-enrollment, and maintains a persistent record of academic activities through file-based storage.
---

## Target User
Students, Academic Administrators, and Course Instructors.
---

## Core Features

- **Role-Based Management:** Specific operations for Students (Register/Drop), Administrators (Manage Users/Courses), and Instructors (View Assignments).
- **Enrollment Logic:** Automated validation for course capacity, duplicate registrations, and prerequisite checks.
- **Data Persistence:** Saving and loading student, course, and instructor data using File I/O for consistency across sessions.

---

## OOP Concepts Used

- Abstraction: Using abstract methods or interfaces for common operations like `displayDetails()` and hiding complex enrollment logic in the `EnrollmentManager`.
- Inheritance: Implementing a base `User` class extended by `Student`, `Instructor`, and `Admin`.
- Polymorphism: Method overriding to provide specific behavior for different user roles and overloaded search methods for course discovery.
- Exception Handling: Custom exceptions for `CourseFullException`, `DuplicateRegistrationException`, and `UserNotFoundException`.
- Collections / Threads: Using `ArrayList` and `HashMap` for efficient data management and potential multithreading for concurrent registration requests.

---

## Proposed Architecture Description
The system follows a layered architecture. The **Model Layer** consists of entities like `Student`, `Course`, and `Instructor`. The **Controller Layer** contains the `EnrollmentManager`, which handles business logic and validations. The **Utility Layer** includes the `FileManager` for data persistence. This separation ensures that the UI/Main logic is decoupled from data processing.

---

## Proposed Architecture Description

---

## How to Run

---

## Git Discipline Notes
Minimum 10 meaningful commits required.
