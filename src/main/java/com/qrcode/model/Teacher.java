package com.qrcode.model;

public class Teacher {
    private int teacherId;
    private int userId;
    private String name;
    private String email;
    private String phone;
    private String department;
    private String hireDate;
    
    // Constructors
    public Teacher() {}
    
    public Teacher(int teacherId, int userId, String name, String email, String phone, String department, String hireDate) {
        this.teacherId = teacherId;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.department = department;
        this.hireDate = hireDate;
    }
    
    // Getters and Setters
    public int getTeacherId() {
        return teacherId;
    }
    
    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
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
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public String getHireDate() {
        return hireDate;
    }
    
    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }
    
    @Override
    public String toString() {
        return "Teacher{" +
                "teacherId=" + teacherId +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", department='" + department + '\'' +
                ", hireDate='" + hireDate + '\'' +
                '}';
    }
}