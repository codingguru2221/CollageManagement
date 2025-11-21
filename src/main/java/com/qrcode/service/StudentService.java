package com.qrcode.service;

import com.qrcode.model.Student;
import com.qrcode.model.FinalResult;
import com.qrcode.model.InternalResult;
import com.qrcode.model.FeePayment;
import com.qrcode.util.DatabaseConnection;
import com.qrcode.util.QRCodeUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class StudentService {
    
    /**
     * Register a new student and generate a unique QR code
     * @param student The student to register
     * @return true if successful, false otherwise
     */
    public boolean registerStudent(Student student) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false); // Start transaction
            
            // First, insert the user record
            String userQuery = "INSERT INTO users (username, password, role) VALUES (?, ?, 'student')";
            preparedStatement = connection.prepareStatement(userQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, student.getEmail()); // Using email as username
            preparedStatement.setString(2, "student123"); // Default password, should be changed later
            preparedStatement.executeUpdate();
            
            generatedKeys = preparedStatement.getGeneratedKeys();
            int userId = 0;
            if (generatedKeys.next()) {
                userId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
            
            // Generate unique QR code for the student
            String qrCodeText = QRCodeUtil.generateUniqueQRCodeString(userId, student.getRollNumber());
            
            // Save the QR code to the database
            String studentQuery = "INSERT INTO students (user_id, roll_number, name, email, phone, address, " +
                                  "date_of_birth, admission_date, batch, department, qr_code) " +
                                  "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(studentQuery);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, student.getRollNumber());
            preparedStatement.setString(3, student.getName());
            preparedStatement.setString(4, student.getEmail());
            preparedStatement.setString(5, student.getPhone());
            preparedStatement.setString(6, student.getAddress());
            preparedStatement.setString(7, student.getDateOfBirth());
            preparedStatement.setString(8, student.getAdmissionDate());
            preparedStatement.setString(9, student.getBatch());
            preparedStatement.setString(10, student.getDepartment());
            preparedStatement.setString(11, qrCodeText); // Save QR code text
            preparedStatement.executeUpdate();
            
            connection.commit(); // Commit transaction
            return true;
        } catch (SQLException e) {
            System.err.println("Error registering student: " + e.getMessage());
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback(); // Rollback transaction on error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            // Close resources
            try {
                if (generatedKeys != null) generatedKeys.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.setAutoCommit(true); // Reset auto-commit
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Get student details by user ID
     * @param userId
     * @return Student object
     */
    public Student getStudentByUserId(int userId) {
        Student student = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            String query = "SELECT * FROM students WHERE user_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                student = new Student();
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
            }
        } catch (SQLException e) {
            System.err.println("Error fetching student details: " + e.getMessage());
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
        
        return student;
    }
    
    /**
     * Get student by student ID
     * @param studentId
     * @return Student object
     */
    public Student getStudentById(int studentId) {
        Student student = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            String query = "SELECT * FROM students WHERE student_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, studentId);
            
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                student = new Student();
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
            }
        } catch (SQLException e) {
            System.err.println("Error fetching student details: " + e.getMessage());
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
        
        return student;
    }
    
    /**
     * Generate and save QR code image for a student
     * @param studentId The student ID
     * @param outputPath The directory where to save the QR code image
     * @return The file path of the saved QR code image
     */
    public String generateAndSaveStudentQRCode(int studentId, String outputPath) {
        try {
            Student student = getStudentById(studentId);
            if (student == null) {
                throw new IllegalArgumentException("Student not found with ID: " + studentId);
            }
            
            // Generate and save QR code image
            return QRCodeUtil.generateAndSaveStudentQRCode(studentId, student.getRollNumber(), outputPath);
        } catch (Exception e) {
            System.err.println("Error generating QR code: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Get final results for a student
     * @param studentId
     * @return List of FinalResult objects
     */
    public List<FinalResult> getFinalResultsByStudent(int studentId) {
        List<FinalResult> results = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            String query = "SELECT * FROM final_results WHERE student_id = ? ORDER BY semester, academic_year";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, studentId);
            
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                FinalResult result = new FinalResult();
                result.setResultId(resultSet.getInt("result_id"));
                result.setStudentId(resultSet.getInt("student_id"));
                result.setCourseId(resultSet.getInt("course_id"));
                result.setInternalMarks(resultSet.getDouble("internal_marks"));
                result.setPracticalMarks(resultSet.getDouble("practical_marks"));
                result.setFinalExamMarks(resultSet.getDouble("final_exam_marks"));
                result.setTotalMarks(resultSet.getDouble("total_marks"));
                result.setMaxMarks(resultSet.getDouble("max_marks"));
                result.setGrade(resultSet.getString("grade"));
                result.setCgpa(resultSet.getDouble("cgpa"));
                result.setSemester(resultSet.getString("semester"));
                result.setAcademicYear(resultSet.getString("academic_year"));
                result.setResultDate(resultSet.getString("result_date"));
                
                results.add(result);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching final results: " + e.getMessage());
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
        
        return results;
    }
    
    /**
     * Get internal results for a student
     * @param studentId
     * @return List of InternalResult objects
     */
    public List<InternalResult> getInternalResultsByStudent(int studentId) {
        List<InternalResult> results = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            String query = "SELECT * FROM internal_results WHERE student_id = ? ORDER BY exam_date DESC";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, studentId);
            
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                InternalResult result = new InternalResult();
                result.setResultId(resultSet.getInt("result_id"));
                result.setStudentId(resultSet.getInt("student_id"));
                result.setCourseId(resultSet.getInt("course_id"));
                result.setExamType(resultSet.getString("exam_type"));
                result.setMarksObtained(resultSet.getDouble("marks_obtained"));
                result.setMaxMarks(resultSet.getDouble("max_marks"));
                result.setExamDate(resultSet.getString("exam_date"));
                result.setSemester(resultSet.getString("semester"));
                result.setAcademicYear(resultSet.getString("academic_year"));
                
                results.add(result);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching internal results: " + e.getMessage());
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
        
        return results;
    }
    
    /**
     * Get fee payment history for a student
     * @param studentId
     * @return List of FeePayment objects
     */
    public List<FeePayment> getFeePaymentsByStudent(int studentId) {
        List<FeePayment> payments = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            String query = "SELECT * FROM fee_payments WHERE student_id = ? ORDER BY payment_date DESC";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, studentId);
            
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                FeePayment payment = new FeePayment();
                payment.setPaymentId(resultSet.getInt("payment_id"));
                payment.setStudentId(resultSet.getInt("student_id"));
                payment.setAmount(resultSet.getDouble("amount"));
                payment.setPaymentDate(resultSet.getString("payment_date"));
                payment.setPaymentMethod(resultSet.getString("payment_method"));
                payment.setTransactionId(resultSet.getString("transaction_id"));
                payment.setSemester(resultSet.getString("semester"));
                payment.setAcademicYear(resultSet.getString("academic_year"));
                payment.setReceiptNumber(resultSet.getString("receipt_number"));
                payment.setStatus(resultSet.getString("status"));
                
                payments.add(payment);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching fee payments: " + e.getMessage());
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
        
        return payments;
    }
    
    /**
     * Make a fee payment
     * @param payment
     * @return true if successful, false otherwise
     */
    public boolean makeFeePayment(FeePayment payment) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            String query = "INSERT INTO fee_payments (student_id, amount, payment_date, payment_method, transaction_id, semester, academic_year, receipt_number, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, payment.getStudentId());
            preparedStatement.setDouble(2, payment.getAmount());
            preparedStatement.setString(3, payment.getPaymentDate());
            preparedStatement.setString(4, payment.getPaymentMethod());
            preparedStatement.setString(5, payment.getTransactionId());
            preparedStatement.setString(6, payment.getSemester());
            preparedStatement.setString(7, payment.getAcademicYear());
            preparedStatement.setString(8, payment.getReceiptNumber());
            preparedStatement.setString(9, payment.getStatus());
            
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error making fee payment: " + e.getMessage());
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
}