-- College Management System Database Schema

-- Create database
CREATE DATABASE IF NOT EXISTS college_management;
USE college_management;

-- Users table (for all login accounts)
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('admin', 'teacher', 'student') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Admin table
CREATE TABLE admins (
    admin_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Teachers table
CREATE TABLE teachers (
    teacher_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    department VARCHAR(100),
    hire_date DATE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Students table
CREATE TABLE students (
    student_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    roll_number VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    address TEXT,
    date_of_birth DATE,
    admission_date DATE,
    batch VARCHAR(20),
    department VARCHAR(100),
    qr_code VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Courses table
CREATE TABLE courses (
    course_id INT AUTO_INCREMENT PRIMARY KEY,
    course_code VARCHAR(20) UNIQUE NOT NULL,
    course_name VARCHAR(100) NOT NULL,
    credits INT,
    department VARCHAR(100)
);

-- Course assignments (which teacher teaches which course)
CREATE TABLE course_assignments (
    assignment_id INT AUTO_INCREMENT PRIMARY KEY,
    teacher_id INT,
    course_id INT,
    semester VARCHAR(20),
    academic_year VARCHAR(10),
    FOREIGN KEY (teacher_id) REFERENCES teachers(teacher_id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE CASCADE
);

-- Student enrollments in courses
CREATE TABLE enrollments (
    enrollment_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    course_id INT,
    semester VARCHAR(20),
    academic_year VARCHAR(10),
    enrollment_date DATE,
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE CASCADE
);

-- Internal exam results
CREATE TABLE internal_results (
    result_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    course_id INT,
    exam_type ENUM('internal', 'practical', 'assignment') NOT NULL,
    marks_obtained DECIMAL(5,2),
    max_marks DECIMAL(5,2),
    exam_date DATE,
    semester VARCHAR(20),
    academic_year VARCHAR(10),
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE CASCADE
);

-- Final results
CREATE TABLE final_results (
    result_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    course_id INT,
    internal_marks DECIMAL(5,2),
    practical_marks DECIMAL(5,2),
    final_exam_marks DECIMAL(5,2),
    total_marks DECIMAL(5,2),
    max_marks DECIMAL(5,2),
    grade VARCHAR(5),
    cgpa DECIMAL(3,2),
    semester VARCHAR(20),
    academic_year VARCHAR(10),
    result_date DATE,
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE CASCADE
);

-- Fee structure
CREATE TABLE fee_structure (
    fee_id INT AUTO_INCREMENT PRIMARY KEY,
    course_id INT,
    semester VARCHAR(20),
    tuition_fee DECIMAL(10,2),
    lab_fee DECIMAL(10,2),
    library_fee DECIMAL(10,2),
    other_fees DECIMAL(10,2),
    total_fee DECIMAL(10,2),
    academic_year VARCHAR(10),
    FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE CASCADE
);

-- Student fee payments
CREATE TABLE fee_payments (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    amount DECIMAL(10,2),
    payment_date DATE,
    payment_method ENUM('cash', 'card', 'online', 'cheque') NOT NULL,
    transaction_id VARCHAR(100),
    semester VARCHAR(20),
    academic_year VARCHAR(10),
    receipt_number VARCHAR(50) UNIQUE,
    status ENUM('paid', 'pending', 'overdue') DEFAULT 'paid',
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE
);

-- Events table
CREATE TABLE events (
    event_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    event_date DATE,
    event_time TIME,
    venue VARCHAR(200),
    organizer VARCHAR(100),
    created_by INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Attendance table
CREATE TABLE attendance (
    attendance_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    course_id INT,
    date DATE,
    status ENUM('present', 'absent', 'late') DEFAULT 'present',
    qr_code_used VARCHAR(255),
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE CASCADE
);

-- Insert sample admin user
INSERT INTO users (username, password, role) VALUES ('admin', 'admin123', 'admin');
INSERT INTO admins (user_id, name, email) VALUES (1, 'Principal Name', 'principal@college.edu');

-- Sample data for testing
INSERT INTO users (username, password, role) VALUES 
('teacher1', 'teacher123', 'teacher'),
('student1', 'student123', 'student');

COMMIT;