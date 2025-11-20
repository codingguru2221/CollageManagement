package com.qrcode.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/college_management";
    private static final String USERNAME = "root"; // Change this to your MySQL username
    private static final String PASSWORD = "Codex@123"; // Change this to your MySQL password
    
    private static Connection connection = null;
    
    // Private constructor to prevent instantiation
    private DatabaseConnection() {}
    
    /**
     * Get a connection to the database
     * @return Connection object
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        try {
            if (connection == null || connection.isClosed()) {
                // Load MySQL JDBC Driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                
                // Establish connection
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            }
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
        }
        
        return connection;
    }
    
    /**
     * Close the database connection
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection!");
            e.printStackTrace();
        }
    }
}