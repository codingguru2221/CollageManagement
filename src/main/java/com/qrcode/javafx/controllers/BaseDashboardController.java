package com.qrcode.javafx.controllers;

import com.qrcode.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BaseDashboardController implements DashboardController {
    
    @FXML
    protected Label welcomeLabel;
    
    protected User currentUser;
    
    @Override
    public void setUser(User user) {
        this.currentUser = user;
        if (welcomeLabel != null && user != null) {
            welcomeLabel.setText("Welcome, " + user.getUsername() + "!");
        }
    }
    
    protected User getCurrentUser() {
        return currentUser;
    }
}