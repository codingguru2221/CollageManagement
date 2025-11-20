package com.qrcode.service;

import com.qrcode.model.User;
import com.qrcode.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthenticationService {
    
    /**
     * Authenticate user with username and password
     * @param username
     * @param password
     * @return User object if authentication is successful, null otherwise
     */
    public User authenticateUser(String username, String password) {
        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            String query = "SELECT user_id, username, password, role FROM users WHERE username = ? AND password = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                user = new User();
                user.setUserId(resultSet.getInt("user_id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(resultSet.getString("role"));
            }
        } catch (SQLException e) {
            System.err.println("Error during authentication: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                // Don't close connection here as it's managed by DatabaseConnection class
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return user;
    }
}