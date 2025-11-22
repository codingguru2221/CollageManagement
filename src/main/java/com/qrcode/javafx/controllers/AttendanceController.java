package com.qrcode.javafx.controllers;

import com.qrcode.model.Student;
import com.qrcode.service.TeacherService;
import com.qrcode.util.CameraQRCodeScanner;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class AttendanceController extends BaseDashboardController {
    
    @FXML
    private ComboBox<Student> studentComboBox;
    
    @FXML
    private TextField courseIdField;
    
    @FXML
    private Label dateLabel;
    
    @FXML
    private Button scanQRButton;
    
    @FXML
    private Button backButton;
    
    @FXML
    private TextArea statusArea;
    
    private TeacherService teacherService = new TeacherService();
    
    @FXML
    public void initialize() {
        // Set current date
        dateLabel.setText("Date: " + LocalDate.now().toString());
        
        // Load students
        loadStudents();
    }
    
    private void loadStudents() {
        try {
            List<Student> students = teacherService.getAllStudents();
            studentComboBox.getItems().addAll(students);
            if (!students.isEmpty()) {
                studentComboBox.getSelectionModel().selectFirst();
            }
        } catch (Exception e) {
            statusArea.appendText("Error loading students: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleScanQR(ActionEvent event) {
        Student selectedStudent = studentComboBox.getValue();
        String courseIdText = courseIdField.getText();
        
        if (selectedStudent == null) {
            showAlert(Alert.AlertType.WARNING, "No Student Selected", "Please select a student.");
            return;
        }
        
        if (courseIdText.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "No Course ID", "Please enter a course ID.");
            return;
        }
        
        try {
            int courseId = Integer.parseInt(courseIdText);
            
            // Disable the scan button during scanning
            scanQRButton.setDisable(true);
            statusArea.appendText("Initializing camera for QR code scanning...\n");
            
            // Run camera scanning in a background task
            Task<String> scanTask = new Task<String>() {
                @Override
                protected String call() throws Exception {
                    CameraQRCodeScanner scanner = new CameraQRCodeScanner();
                    try {
                        scanner.initializeCamera();
                        updateMessage("Camera initialized. Scanning for QR code...");
                        return scanner.scanQRCodeFromCamera(30); // 30 second timeout
                    } finally {
                        scanner.closeCamera();
                    }
                }
            };
            
            scanTask.messageProperty().addListener((obs, oldMessage, newMessage) -> {
                statusArea.appendText(newMessage + "\n");
            });
            
            scanTask.setOnSucceeded(e -> {
                String qrCode = scanTask.getValue();
                if (qrCode != null) {
                    statusArea.appendText("QR Code detected: " + qrCode + "\n");
                    // Verify and mark attendance
                    verifyAndMarkAttendance(selectedStudent, courseId, qrCode);
                } else {
                    statusArea.appendText("No QR code detected within timeout period.\n");
                }
                scanQRButton.setDisable(false);
            });
            
            scanTask.setOnFailed(e -> {
                statusArea.appendText("Error during scanning: " + scanTask.getException().getMessage() + "\n");
                scanQRButton.setDisable(false);
            });
            
            // Start the background task
            new Thread(scanTask).start();
            
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Course ID", "Please enter a valid numeric course ID.");
        } catch (Exception e) {
            statusArea.appendText("Error initializing camera: " + e.getMessage() + "\n");
            scanQRButton.setDisable(false);
        }
    }
    
    private void verifyAndMarkAttendance(Student student, int courseId, String qrCode) {
        try {
            statusArea.appendText("Verifying QR code...\n");
            
            if (teacherService.verifyQRCode(student.getStudentId(), qrCode)) {
                statusArea.appendText("QR code verified successfully.\n");
                statusArea.appendText("Marking attendance...\n");
                
                String date = LocalDate.now().toString();
                if (teacherService.markAttendance(student.getStudentId(), courseId, date, qrCode)) {
                    statusArea.appendText("Attendance marked successfully for " + student.getName() + "!\n");
                    showAlert(Alert.AlertType.INFORMATION, "Success", 
                              "Attendance marked successfully for " + student.getName() + "!");
                } else {
                    statusArea.appendText("Failed to mark attendance.\n");
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to mark attendance.");
                }
            } else {
                statusArea.appendText("Invalid QR code for student.\n");
                showAlert(Alert.AlertType.ERROR, "Invalid QR Code", "The scanned QR code is not valid for this student.");
            }
        } catch (Exception e) {
            statusArea.appendText("Error verifying QR code: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleBack(ActionEvent event) {
        try {
            // Load the teacher dashboard FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/teacher_dashboard.fxml"));
            Parent root = loader.load();
            
            // Get the controller and pass the user
            TeacherDashboardController controller = loader.getController();
            controller.setUser(getCurrentUser());
            
            // Create and show the dashboard scene
            Scene scene = new Scene(root);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Teacher Dashboard");
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