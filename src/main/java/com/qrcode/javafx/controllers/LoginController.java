package com.qrcode.javafx.controllers;

import com.qrcode.model.User;
import com.qrcode.service.AuthenticationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    
    @FXML
    private ComboBox<String> roleComboBox;
    
    @FXML
    private TextField usernameField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private Button loginButton;
    
    private AuthenticationService authService = new AuthenticationService();
    
    @FXML
    public void initialize() {
        // Initialize role combo box
        roleComboBox.getItems().addAll("Admin", "Teacher", "Student");
        roleComboBox.setValue("Teacher");
    }
    
    @FXML
    private void handleLogin(ActionEvent event) {
        String role = roleComboBox.getValue();
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Login Failed", "Please enter both username and password.");
            return;
        }
        
        User user = null;
        
        try {
            if ("Student".equals(role)) {
                // For students, we authenticate by roll number
                user = authService.authenticateStudent(username, password);
            } else {
                // For admin and teacher, we authenticate by username
                user = authService.authenticateUser(username, password);
            }
            
            if (user != null && user.getRole().equalsIgnoreCase(role)) {
                // Login successful, navigate to appropriate dashboard
                navigateToDashboard(user);
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid credentials. Please try again.");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Login Error", "An error occurred during login: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void navigateToDashboard(User user) {
        try {
            String fxmlFile = "";
            String title = "";
            
            switch (user.getRole().toLowerCase()) {
                case "admin":
                    fxmlFile = "/fxml/admin_dashboard.fxml";
                    title = "Admin Dashboard";
                    break;
                case "teacher":
                    fxmlFile = "/fxml/teacher_dashboard.fxml";
                    title = "Teacher Dashboard";
                    break;
                case "student":
                    fxmlFile = "/fxml/student_dashboard.fxml";
                    title = "Student Dashboard";
                    break;
                default:
                    showAlert(Alert.AlertType.ERROR, "Navigation Error", "Unknown user role.");
                    return;
            }
            
            // Load the dashboard FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            
            // Get the controller and pass the user
            Object controller = loader.getController();
            if (controller instanceof DashboardController) {
                ((DashboardController) controller).setUser(user);
            }
            
            // Create and show the dashboard scene
            Scene scene = new Scene(root);
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not load dashboard: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}