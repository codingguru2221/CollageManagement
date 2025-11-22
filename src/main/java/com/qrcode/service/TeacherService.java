package com.qrcode.service;

import com.qrcode.model.Teacher;
import com.qrcode.model.Student;
import com.qrcode.model.Course;
import com.qrcode.model.InternalResult;
import com.qrcode.model.FinalResult;
import com.qrcode.util.DatabaseConnection;
import com.qrcode.util.CameraQRCodeScanner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherService {
    
    /**
     * Get teacher details by user ID
     * @param userId
     * @return Teacher object
     */
    public Teacher getTeacherByUserId(int userId) {
        Teacher teacher = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            String query = "SELECT * FROM teachers WHERE user_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                teacher = new Teacher();
                teacher.setTeacherId(resultSet.getInt("teacher_id"));
                teacher.setUserId(resultSet.getInt("user_id"));
                teacher.setName(resultSet.getString("name"));
                teacher.setEmail(resultSet.getString("email"));
                teacher.setPhone(resultSet.getString("phone"));
                teacher.setDepartment(resultSet.getString("department"));
                teacher.setHireDate(resultSet.getString("hire_date"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching teacher details: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return teacher;
    }
    
    /**
     * Get students by batch/year
     * @param batch
     * @return List of Student objects
     */
    public List<Student> getStudentsByBatch(String batch) {
        List<Student> students = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            String query = "SELECT * FROM students WHERE batch = ? ORDER BY name";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, batch);
            
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                Student student = new Student();
                student.setStudentId(resultSet.getInt("student_id"));
                student.setUserId(resultSet.getInt("user_id"));
                student.setRollNumber(resultSet.getString("roll_number"));
                student.setName(resultSet.getString("name"));
                student.setEmail(resultSet.getString("email"));
                student.setPhone(resultSet.getString("phone"));
                student.setAddress(resultSet.getString("address"));
                student.setDateOfBirth(resultSet.getString("date_of_birth"));
                student.setAdmissionDate(resultSet.getString("admission_date"));
                student.setBatch(resultSet.getString("batch"));
                student.setDepartment(resultSet.getString("department"));
                student.setQrCode(resultSet.getString("qr_code")); // Get QR code
                
                students.add(student);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching students by batch: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return students;
    }
    
    /**
     * Get all students (for teachers to see all students)
     * @return List of Student objects
     */
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            String query = "SELECT * FROM students ORDER BY batch, name";
            preparedStatement = connection.prepareStatement(query);
            
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                Student student = new Student();
                student.setStudentId(resultSet.getInt("student_id"));
                student.setUserId(resultSet.getInt("user_id"));
                student.setRollNumber(resultSet.getString("roll_number"));
                student.setName(resultSet.getString("name"));
                student.setEmail(resultSet.getString("email"));
                student.setPhone(resultSet.getString("phone"));
                student.setAddress(resultSet.getString("address"));
                student.setDateOfBirth(resultSet.getString("date_of_birth"));
                student.setAdmissionDate(resultSet.getString("admission_date"));
                student.setBatch(resultSet.getString("batch"));
                student.setDepartment(resultSet.getString("department"));
                student.setQrCode(resultSet.getString("qr_code")); // Get QR code
                
                students.add(student);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all students: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return students;
    }
    
    /**
     * Get courses taught by a teacher
     * @param teacherId
     * @return List of Course objects
     */
    public List<Course> getCoursesByTeacher(int teacherId) {
        List<Course> courses = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            String query = "SELECT c.* FROM courses c JOIN course_assignments ca ON c.course_id = ca.course_id WHERE ca.teacher_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, teacherId);
            
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                Course course = new Course();
                course.setCourseId(resultSet.getInt("course_id"));
                course.setCourseCode(resultSet.getString("course_code"));
                course.setCourseName(resultSet.getString("course_name"));
                course.setCredits(resultSet.getInt("credits"));
                course.setDepartment(resultSet.getString("department"));
                
                courses.add(course);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching courses by teacher: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return courses;
    }
    
    /**
     * Add internal exam result
     * @param result
     * @return true if successful, false otherwise
     */
    public boolean addInternalResult(InternalResult result) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            String query = "INSERT INTO internal_results (student_id, course_id, exam_type, marks_obtained, max_marks, exam_date, semester, academic_year) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, result.getStudentId());
            preparedStatement.setInt(2, result.getCourseId());
            preparedStatement.setString(3, result.getExamType());
            preparedStatement.setDouble(4, result.getMarksObtained());
            preparedStatement.setDouble(5, result.getMaxMarks());
            preparedStatement.setString(6, result.getExamDate());
            preparedStatement.setString(7, result.getSemester());
            preparedStatement.setString(8, result.getAcademicYear());
            
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error adding internal result: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            // Close resources
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Add final result
     * @param result
     * @return true if successful, false otherwise
     */
    public boolean addFinalResult(FinalResult result) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            String query = "INSERT INTO final_results (student_id, course_id, internal_marks, practical_marks, final_exam_marks, total_marks, max_marks, grade, cgpa, semester, academic_year, result_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, result.getStudentId());
            preparedStatement.setInt(2, result.getCourseId());
            preparedStatement.setDouble(3, result.getInternalMarks());
            preparedStatement.setDouble(4, result.getPracticalMarks());
            preparedStatement.setDouble(5, result.getFinalExamMarks());
            preparedStatement.setDouble(6, result.getTotalMarks());
            preparedStatement.setDouble(7, result.getMaxMarks());
            preparedStatement.setString(8, result.getGrade());
            preparedStatement.setDouble(9, result.getCgpa());
            preparedStatement.setString(10, result.getSemester());
            preparedStatement.setString(11, result.getAcademicYear());
            preparedStatement.setString(12, result.getResultDate());
            
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error adding final result: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            // Close resources
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Mark student attendance using QR code
     * @param studentId The student ID
     * @param courseId The course ID
     * @param date The attendance date
     * @param qrCode The QR code used
     * @return true if successful, false otherwise
     */
    public boolean markAttendance(int studentId, int courseId, String date, String qrCode) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            String query = "INSERT INTO attendance (student_id, course_id, date, status, qr_code_used) VALUES (?, ?, ?, 'present', ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, courseId);
            preparedStatement.setString(3, date);
            preparedStatement.setString(4, qrCode);
            
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error marking attendance: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            // Close resources
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Verify if a QR code is valid for a student
     * @param studentId The student ID
     * @param qrCode The QR code to verify
     * @return true if valid, false otherwise
     */
    public boolean verifyQRCode(int studentId, String qrCode) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            String query = "SELECT roll_number FROM students WHERE student_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, studentId);
            
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                String rollNumber = resultSet.getString("roll_number");
                // Use the proper validation method that checks the prefix
                return com.qrcode.util.QRCodeScanner.validateStudentQRCode(qrCode, studentId, rollNumber);
            } else {
                System.out.println("DEBUG: No student found with ID " + studentId);
            }
        } catch (SQLException e) {
            System.err.println("Error verifying QR code: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return false;
    }
    
    /**
     * Scan QR code using camera and mark attendance
     * @param studentId The student ID
     * @param courseId The course ID
     * @return true if attendance was marked successfully, false otherwise
     */
    public boolean scanQRCodeAndMarkAttendance(int studentId, int courseId) {
        CameraQRCodeScanner scanner = new CameraQRCodeScanner();
        
        try {
            // Check if camera is available
            if (!CameraQRCodeScanner.isCameraAvailable()) {
                System.err.println("No camera available for QR code scanning");
                return false;
            }
            
            // Initialize camera
            scanner.initializeCamera();
            
            System.out.println("Camera initialized. A window will appear showing the camera feed.");
            System.out.println("Position the student's QR code in the frame and wait for detection.");
            System.out.println("The scanning will timeout after 30 seconds if no QR code is detected.");
            
            // Scan QR code from camera (timeout after 30 seconds)
            String scannedQRCode = scanner.scanQRCodeFromCamera(30);
            
            if (scannedQRCode != null) {
                // Get current date
                String date = java.time.LocalDate.now().toString();
                
                // Verify QR code and mark attendance
                if (verifyQRCode(studentId, scannedQRCode)) {
                    return markAttendance(studentId, courseId, date, scannedQRCode);
                } else {
                    System.err.println("Invalid QR code for student ID: " + studentId);
                    return false;
                }
            } else {
                System.err.println("No QR code detected within timeout period");
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error during camera-based QR code scanning: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            // Close camera
            scanner.closeCamera();
        }
    }
}