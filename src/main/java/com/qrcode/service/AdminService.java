package com.qrcode.service;

import com.qrcode.model.Admin;
import com.qrcode.model.Student;
import com.qrcode.model.Teacher;
import com.qrcode.model.Event;
import com.qrcode.model.User;
import com.qrcode.util.DatabaseConnection;
import java.sql.ResultSet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminService {
    
    /**
     * Get admin details by user ID
     * @param userId
     * @return Admin object
     */
    public Admin getAdminByUserId(int userId) {
        Admin admin = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            String query = "SELECT * FROM admins WHERE user_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                admin = new Admin();
                admin.setAdminId(resultSet.getInt("admin_id"));
                admin.setUserId(resultSet.getInt("user_id"));
                admin.setName(resultSet.getString("name"));
                admin.setEmail(resultSet.getString("email"));
                admin.setPhone(resultSet.getString("phone"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching admin details: " + e.getMessage());
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
        
        return admin;
    }
    
    /**
     * Get all students
     * @return List of Student objects
     */
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            String query = "SELECT * FROM students";
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
            System.err.println("Error fetching students: " + e.getMessage());
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
     * Get all teachers
     * @return List of Teacher objects
     */
    public List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            String query = "SELECT * FROM teachers";
            preparedStatement = connection.prepareStatement(query);
            
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                Teacher teacher = new Teacher();
                teacher.setTeacherId(resultSet.getInt("teacher_id"));
                teacher.setUserId(resultSet.getInt("user_id"));
                teacher.setName(resultSet.getString("name"));
                teacher.setEmail(resultSet.getString("email"));
                teacher.setPhone(resultSet.getString("phone"));
                teacher.setDepartment(resultSet.getString("department"));
                teacher.setHireDate(resultSet.getString("hire_date"));
                
                teachers.add(teacher);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching teachers: " + e.getMessage());
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
        
        return teachers;
    }
    
    /**
     * Get all events
     * @return List of Event objects
     */
    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            String query = "SELECT * FROM events ORDER BY event_date DESC";
            preparedStatement = connection.prepareStatement(query);
            
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                Event event = new Event();
                event.setEventId(resultSet.getInt("event_id"));
                event.setTitle(resultSet.getString("title"));
                event.setDescription(resultSet.getString("description"));
                event.setEventDate(resultSet.getString("event_date"));
                event.setEventTime(resultSet.getString("event_time"));
                event.setVenue(resultSet.getString("venue"));
                event.setOrganizer(resultSet.getString("organizer"));
                event.setCreatedBy(resultSet.getInt("created_by"));
                event.setCreatedAt(resultSet.getString("created_at"));
                
                events.add(event);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching events: " + e.getMessage());
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
        
        return events;
    }
    
    /**
     * Add a new event
     * @param event
     * @return true if successful, false otherwise
     */
    public boolean addEvent(Event event) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            String query = "INSERT INTO events (title, description, event_date, event_time, venue, organizer, created_by) VALUES (?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, event.getTitle());
            preparedStatement.setString(2, event.getDescription());
            preparedStatement.setString(3, event.getEventDate());
            preparedStatement.setString(4, event.getEventTime());
            preparedStatement.setString(5, event.getVenue());
            preparedStatement.setString(6, event.getOrganizer());
            preparedStatement.setInt(7, event.getCreatedBy());
            
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error adding event: " + e.getMessage());
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
     * Add a new student
     * @param student
     * @param user
     * @return true if successful, false otherwise
     */
    public boolean addStudent(Student student, User user) {
        // Use the new registerStudent method from StudentService which generates QR codes
        StudentService studentService = new StudentService();
        return studentService.registerStudent(student, user.getUsername(), user.getPassword());
    }
    
    /**
     * Add a new teacher
     * @param teacher
     * @param user
     * @return true if successful, false otherwise
     */
    public boolean addTeacher(Teacher teacher, User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement userStatement = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false);
            
            // First, create user account
            String userQuery = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
            userStatement = connection.prepareStatement(userQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            userStatement.setString(1, user.getUsername());
            userStatement.setString(2, user.getPassword());
            userStatement.setString(3, user.getRole());
            
            int userRowsAffected = userStatement.executeUpdate();
            
            if (userRowsAffected > 0) {
                // Get generated user ID
                ResultSet userResultSet = userStatement.getGeneratedKeys();
                int userId = 0;
                if (userResultSet.next()) {
                    userId = userResultSet.getInt(1);
                }
                
                // Then, create teacher record
                String teacherQuery = "INSERT INTO teachers (user_id, name, email, phone, department, hire_date) VALUES (?, ?, ?, ?, ?, ?)";
                preparedStatement = connection.prepareStatement(teacherQuery);
                preparedStatement.setInt(1, userId);
                preparedStatement.setString(2, teacher.getName());
                preparedStatement.setString(3, teacher.getEmail());
                preparedStatement.setString(4, teacher.getPhone());
                preparedStatement.setString(5, teacher.getDepartment());
                preparedStatement.setString(6, teacher.getHireDate());
                
                int teacherRowsAffected = preparedStatement.executeUpdate();
                
                if (teacherRowsAffected > 0) {
                    connection.commit();
                    return true;
                }
            }
            
            connection.rollback();
            return false;
        } catch (SQLException e) {
            System.err.println("Error adding teacher: " + e.getMessage());
            e.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            return false;
        } finally {
            // Close resources
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (userStatement != null) userStatement.close();
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}