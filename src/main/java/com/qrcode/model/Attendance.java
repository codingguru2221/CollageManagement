package com.qrcode.model;

public class Attendance {
    private int attendanceId;
    private int studentId;
    private int courseId;
    private String date;
    private String status; // present, absent, late
    private String qrCodeUsed;
    private String timestamp;
    
    // Constructors
    public Attendance() {}
    
    public Attendance(int attendanceId, int studentId, int courseId, String date, String status, String qrCodeUsed, String timestamp) {
        this.attendanceId = attendanceId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.date = date;
        this.status = status;
        this.qrCodeUsed = qrCodeUsed;
        this.timestamp = timestamp;
    }
    
    // Getters and Setters
    public int getAttendanceId() {
        return attendanceId;
    }
    
    public void setAttendanceId(int attendanceId) {
        this.attendanceId = attendanceId;
    }
    
    public int getStudentId() {
        return studentId;
    }
    
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
    
    public int getCourseId() {
        return courseId;
    }
    
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
    
    public String getDate() {
        return date;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getQrCodeUsed() {
        return qrCodeUsed;
    }
    
    public void setQrCodeUsed(String qrCodeUsed) {
        this.qrCodeUsed = qrCodeUsed;
    }
    
    public String getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    
    @Override
    public String toString() {
        return "Attendance{" +
                "attendanceId=" + attendanceId +
                ", studentId=" + studentId +
                ", courseId=" + courseId +
                ", date='" + date + '\'' +
                ", status='" + status + '\'' +
                ", qrCodeUsed='" + qrCodeUsed + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}