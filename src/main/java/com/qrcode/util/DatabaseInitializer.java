package com.qrcode.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

public class DatabaseInitializer {
    
    /**
     * Initialize the database and tables
     */
    public static void initializeDatabase() {
        Connection connection = null;
        Statement statement = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.createStatement();
            
            // Read and execute the database schema
            String schema = new String(Files.readAllBytes(Paths.get("database_schema.sql")), StandardCharsets.UTF_8);
            String[] queries = schema.split(";");
            
            for (String query : queries) {
                query = query.trim();
                if (!query.isEmpty() && !query.startsWith("--")) {
                    try {
                        statement.executeUpdate(query);
                    } catch (SQLException e) {
                        // Ignore errors for existing tables
                        if (!e.getMessage().contains("already exists") && 
                            !e.getMessage().contains("Duplicate column name") &&
                            !e.getMessage().contains("Duplicate key name")) {
                            System.err.println("Error executing query: " + query);
                            System.err.println("Error message: " + e.getMessage());
                        }
                    }
                }
            }
            
            // Also try to add the qr_code column if it doesn't exist
            try {
                statement.executeUpdate("ALTER TABLE students ADD qr_code VARCHAR(255)");
                System.out.println("Added qr_code column to students table");
            } catch (SQLException e) {
                // Column might already exist, which is fine
                if (!e.getMessage().contains("Duplicate column name")) {
                    System.err.println("Error adding qr_code column: " + e.getMessage());
                }
            }
            
            // Also try to create the attendance table if it doesn't exist
            try {
                String createAttendanceTable = "CREATE TABLE IF NOT EXISTS attendance (" +
                    "attendance_id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "student_id INT, " +
                    "course_id INT, " +
                    "date DATE, " +
                    "status ENUM('present', 'absent', 'late') DEFAULT 'present', " +
                    "qr_code_used VARCHAR(255), " +
                    "timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE, " +
                    "FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE CASCADE)";
                statement.executeUpdate(createAttendanceTable);
                System.out.println("Attendance table created or already exists");
            } catch (SQLException e) {
                System.err.println("Error creating attendance table: " + e.getMessage());
            }
            
            System.out.println("Database and tables already exist.");
        } catch (Exception e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}