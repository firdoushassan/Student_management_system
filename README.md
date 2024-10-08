# Student Management System

## Overview

This Student Management System is a Java-based desktop application designed to simplify and automate the management of student information. It allows administrators to perform tasks such as adding, editing, deleting, and viewing student records with a user-friendly graphical interface. Moreover, a option to export filtered data from the table to a csv file is also available.

## Features

- **Admin Login**: A secure login system for administrators to access the system.
- **Add New Students**: Allows administrators to add new students with details like name, ID, and profile images.
- **Edit Student Information**: Update existing student records.
- **Delete Students**: Remove students from the system.
- **View All Students**: Display a list of all enrolled students with the ability to search and filter.
- **View Particular Student Details**: View all the details of a particular selected student.
- **Export as CSV**: Export filtered data of the students to a csv file.
- **Database Integration**: Uses MySQL to store and manage student records.
- **Responsive UI**: Built with Java Swing, offering a clean and intuitive interface with support for images and form inputs.

## Technologies Used

- **Java**: Core programming language used to build the application.
- **Swing/AWT**: For building the graphical user interface (GUI).
- **MySQL**: The relational database used to store student information.
- **JDBC (Java Database Connectivity)**: For connecting and executing queries on the MySQL database.
- **MySQL Connector/J**: JDBC driver for MySQL to interact with the database.
  
## Project Structure

- `src/`: Contains all the Java source files for the application.
  - **AdminLogin.java**: Handles the login functionality for the admin.
  - **StudentManagement.java**: Main class for managing student operations.
  - **com.components**: Includes components like adding, editing, deleting, and viewing students.
  - **DBConnection.java**: Handles the database connection using JDBC.
- `student_images/`: Contains sample images for student profiles.
- `mysql-connector-j-9.0.0/`: MySQL connector library for Java, enabling database communication.

## Installation & Setup

1. **Install MySQL**: Make sure you have MySQL installed and running.
2. **Database Setup**: Create a database schema(student_management) into your MySQL server and add tables for student and admin details.Insert Admin details into the admin table before running the project.(or you can import the database provided in the project folder: student_management_database).
3. **Configure JDBC**: Ensure that the MySQL connection URL, username, and password in `DBConnection.java` are correct for your local setup.
4. **Compile and Run**: Compile the Java project using an IDE like Eclipse or IntelliJ, or from the command line with `javac`. Run the application with `java`.

## How to Use

1. Launch the application.
2. Log in using the admin credentials.
3. Navigate through the dashboard to add, edit, or delete student records.
4. Use the search functionality to quickly find student details.
5. Exit when done.

## Screenshots

![Admin Login](Screenshots/Admin_Login.png)

![Student Table](Screenshots/View_Student_Table.png)

![Edit Student](Screenshots/Edit_Student.png)

![Student Detail](Screenshots/Student_Detail.png)

![Add Student](Screenshots/Add_Student.png)

  
-------------------- **Table Structures**--------------------
- ![Admin](Screenshots/admin_table.png)
-  ![Student](Screenshots/student_table.png)

## Dependencies

- Java JDK 8 or higher
- MySQL Server
- MySQL Connector/J (included in the project)
  
## Future Enhancements

- Implement user roles with different access levels.
- Add student attendance and grades management.
- Improve the UI with modern design practices.
