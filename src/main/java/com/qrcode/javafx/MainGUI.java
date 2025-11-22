package com.qrcode.javafx;

import com.qrcode.util.DatabaseInitializer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainGUI extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Initialize database and tables
        DatabaseInitializer.initializeDatabase();
        
        // Load the login FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Parent root = loader.load();
        
        // Create scene and stage
        Scene scene = new Scene(root, 500, 400);
        primaryStage.setTitle("College Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}