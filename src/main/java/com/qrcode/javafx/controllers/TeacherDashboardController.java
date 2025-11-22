package com.qrcode.javafx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class TeacherDashboardController extends BaseDashboardController {
    
    @FXML
    private Button viewStudentsButton;
    
    @FXML
    private Button markAttendanceButton;
    
    @FXML
    private Button addResultButton;
    
    @FXML
    private Button logoutButton;
    
    @FXML
    private void handleViewStudents(ActionEvent event) {
        showAlert(Alert.AlertType.INFORMATION, "Feature Not Implemented", 
                  "View Students feature will be implemented in the next version.");
    }
    
    @FXML
    private void handleMarkAttendance(ActionEvent event) {
        try {
            // Load the attendance FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/attendance.fxml"));
            Parent root = loader.load();
            
            // Get the controller and pass the user
            AttendanceController controller = loader.getController();
            controller.setUser(getCurrentUser());
            
            // Create and show the attendance scene
            Scene scene = new Scene(root);
            Stage stage = (Stage) markAttendanceButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Mark Attendance");
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not load attendance screen: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleAddResult(ActionEvent event) {
        showAlert(Alert.AlertType.INFORMATION, "Feature Not Implemented", 
                  "Add Result feature will be implemented in the next version.");
    }
    
    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            // Load the login FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Parent root = loader.load();
            
            // Create and show the login scene
            Scene scene = new Scene(root);
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("College Management System - Login");
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not load login screen: " + e.getMessage());
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