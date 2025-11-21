# College Management System

A comprehensive college management system with separate modules for administrators, teachers, and students.

## Features

### Admin Module
- View all students and teachers
- Manage college events
- Overview of all college activities
- Register new students (automatically generates unique QR codes)

### Teacher Module
- View students by batch/year
- Add internal exam results
- Add final results
- Manage student information
- Mark student attendance using QR codes

### Student Module
- View final results
- View internal exam results
- View fee payment history
- Make fee payments
- View personal QR code
- Download QR code as image for attendance

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
- `attendance` - Student attendance records

## Setup Instructions

1. Create a MySQL database named `college_management`
2. Execute the `database_schema.sql` script to create tables
3. Update the database credentials in `src/main/java/com/qrcode/util/DatabaseConnection.java`
4. Build and run the application using Maven:
   ```
   mvn compile exec:java
   ```

## QR Code Attendance System

Each student is automatically assigned a unique QR code during registration. This QR code can be used for attendance tracking:

1. Students can view their QR code in the student dashboard
2. Students can download their QR code as a PNG image
3. Teachers can scan or manually enter the QR code to mark attendance
4. Attendance records are stored in the database with timestamps

## Default Login Credentials

- Admin: username: `admin`, password: `admin123`
- Teacher: username: `teacher1`, password: `teacher123`
- Student: username: `student1`, password: `student123`

## Technologies Used

- Java
- MySQL
- JDBC
- Maven
- ZXing QR Code Library