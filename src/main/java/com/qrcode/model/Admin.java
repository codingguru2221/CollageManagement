package com.qrcode.model;

public class Admin {
    private int adminId;
    private int userId;
    private String name;
    private String email;
    private String phone;
    
    // Constructors
    public Admin() {}
    
    public Admin(int adminId, int userId, String name, String email, String phone) {
        this.adminId = adminId;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
    
    // Getters and Setters
    public int getAdminId() {
        return adminId;
    }
    
    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}