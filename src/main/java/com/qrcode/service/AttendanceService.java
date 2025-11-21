package com.qrcode.service;

import com.qrcode.model.Attendance;
import com.qrcode.model.Student;
import com.qrcode.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttendanceService {
    
    /**
     * Mark attendance for a student using QR code
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
            String query = "INSERT INTO attendance (student_id, course_id, date, qr_code_used) VALUES (?, ?, ?, ?)";
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
            String query = "SELECT qr_code FROM students WHERE student_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, studentId);
            
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                String storedQRCode = resultSet.getString("qr_code");
                return qrCode.equals(storedQRCode);
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
     * Get attendance records for a student
     * @param studentId The student ID
     * @return List of Attendance objects
     */
    public List<Attendance> getAttendanceByStudent(int studentId) {
        List<Attendance> attendanceList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            String query = "SELECT * FROM attendance WHERE student_id = ? ORDER BY date DESC";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, studentId);
            
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                Attendance attendance = new Attendance();
                attendance.setAttendanceId(resultSet.getInt("attendance_id"));
                attendance.setStudentId(resultSet.getInt("student_id"));
                attendance.setCourseId(resultSet.getInt("course_id"));
                attendance.setDate(resultSet.getString("date"));
                attendance.setStatus(resultSet.getString("status"));
                attendance.setQrCodeUsed(resultSet.getString("qr_code_used"));
                attendance.setTimestamp(resultSet.getString("timestamp"));
                
                attendanceList.add(attendance);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching attendance records: " + e.getMessage());
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
        
        return attendanceList;
    }
    
    /**
     * Get attendance records for a course
     * @param courseId The course ID
     * @return List of Attendance objects
     */
    public List<Attendance> getAttendanceByCourse(int courseId) {
        List<Attendance> attendanceList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            String query = "SELECT * FROM attendance WHERE course_id = ? ORDER BY date DESC";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, courseId);
            
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                Attendance attendance = new Attendance();
                attendance.setAttendanceId(resultSet.getInt("attendance_id"));
                attendance.setStudentId(resultSet.getInt("student_id"));
                attendance.setCourseId(resultSet.getInt("course_id"));
                attendance.setDate(resultSet.getString("date"));
                attendance.setStatus(resultSet.getString("status"));
                attendance.setQrCodeUsed(resultSet.getString("qr_code_used"));
                attendance.setTimestamp(resultSet.getString("timestamp"));
                
                attendanceList.add(attendance);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching attendance records: " + e.getMessage());
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
        
        return attendanceList;
    }
}