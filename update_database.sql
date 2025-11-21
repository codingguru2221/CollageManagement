USE college_management;

-- Add qr_code column to students table
ALTER TABLE students ADD qr_code VARCHAR(255);

-- Create attendance table if it doesn't exist
CREATE TABLE IF NOT EXISTS attendance (
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