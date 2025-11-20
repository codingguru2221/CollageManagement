# College Management System

A comprehensive college management system with separate modules for administrators, teachers, and students.

## Features

### Admin Module
- View all students and teachers
- Manage college events
- Overview of all college activities

### Teacher Module
- View students by batch/year
- Add internal exam results
- Add final results
- Manage student information

### Student Module
- View final results
- View internal exam results
- View fee payment history
- Make fee payments

## Database Schema

The system uses a MySQL database with the following tables:
- `users` - For authentication (admin, teacher, student)
- `admins` - Admin details
- `teachers` - Teacher details
- `students` - Student details
- `courses` - Course information
- `course_assignments` - Which teacher teaches which course
- `enrollments` - Student course enrollments
- `internal_results` - Internal exam results
- `final_results` - Final semester results
- `fee_structure` - Course fee structure
- `fee_payments` - Student fee payments
- `events` - College events

## Setup Instructions

1. Create a MySQL database named `college_management`
2. Execute the `database_schema.sql` script to create tables
3. Update the database credentials in `src/main/java/com/qrcode/util/DatabaseConnection.java`
4. Build and run the application using Maven:
   ```
   mvn compile exec:java
   ```

## Default Login Credentials

- Admin: username: `admin`, password: `admin123`
- Teacher: username: `teacher1`, password: `teacher123`
- Student: username: `student1`, password: `student123`

## Technologies Used

- Java
- MySQL
- JDBC
- Maven