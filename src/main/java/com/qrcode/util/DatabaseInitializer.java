package com.qrcode.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "college_management";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Codex@123";
    
    /**
     * Initialize the database and tables
     * Creates database and tables if they don't exist
     */
    public static void initializeDatabase() {
        try {
            // First, try to connect to the database
            if (!databaseExists()) {
                System.out.println("Database does not exist. Creating database and tables...");
                createDatabaseAndTables();
                System.out.println("Database and tables created successfully!");
            } else {
                System.out.println("Database already exists. Checking tables...");
                // Check if tables exist, if not create them
                if (!tablesExist()) {
                    System.out.println("Tables do not exist. Creating tables...");
                    createTables();
                    System.out.println("Tables created successfully!");
                } else {
                    System.out.println("Database and tables already exist.");
                }
            }
        } catch (Exception e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Check if the database exists
     */
    private static boolean databaseExists() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            Statement statement = connection.createStatement();
            statement.execute("USE " + DB_NAME);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    
    /**
     * Check if the required tables exist
     */
    private static boolean tablesExist() {
        try (Connection connection = DriverManager.getConnection(DB_URL + DB_NAME, USERNAME, PASSWORD)) {
            Statement statement = connection.createStatement();
            // Try to query the users table as a test
            statement.executeQuery("SELECT 1 FROM users LIMIT 1");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    
    /**
     * Create the database and all tables
     */
    private static void createDatabaseAndTables() throws SQLException, IOException {
        // Create database
        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
        }
        
        // Create tables
        createTables();
    }
    
    /**
     * Create all tables from the schema file
     */
    private static void createTables() throws SQLException, IOException {
        try (Connection connection = DriverManager.getConnection(DB_URL + DB_NAME, USERNAME, PASSWORD)) {
            // Read the schema file
            StringBuilder sqlBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader("database_schema.sql"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // Skip comments and empty lines
                    if (!line.trim().isEmpty() && !line.trim().startsWith("--") && !line.trim().startsWith("/*")) {
                        sqlBuilder.append(line).append("\n");
                    }
                }
            }
            
            // Split by semicolon to get individual statements
            String[] statements = sqlBuilder.toString().split(";");
            
            Statement statement = connection.createStatement();
            for (String sql : statements) {
                String trimmedSql = sql.trim();
                if (!trimmedSql.isEmpty() && !trimmedSql.startsWith("--") && !trimmedSql.startsWith("/*") 
                    && !trimmedSql.startsWith("CREATE DATABASE")) {
                    try {
                        statement.executeUpdate(trimmedSql);
                    } catch (SQLException e) {
                        // Ignore errors for statements like "USE database" or duplicate entries
                        if (!e.getMessage().contains("Unknown database") && 
                            !e.getMessage().contains("already exists") &&
                            !e.getMessage().contains("Duplicate")) {
                            System.err.println("SQL Error executing: " + trimmedSql);
                            System.err.println("Error: " + e.getMessage());
                        }
                    }
                }
            }
        }
    }
}