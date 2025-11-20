package com.qrcode.model;

public class FeePayment {
    private int paymentId;
    private int studentId;
    private double amount;
    private String paymentDate;
    private String paymentMethod;
    private String transactionId;
    private String semester;
    private String academicYear;
    private String receiptNumber;
    private String status;
    
    // Constructors
    public FeePayment() {}
    
    public FeePayment(int paymentId, int studentId, double amount, String paymentDate, String paymentMethod,
                      String transactionId, String semester, String academicYear, String receiptNumber, String status) {
        this.paymentId = paymentId;
        this.studentId = studentId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.transactionId = transactionId;
        this.semester = semester;
        this.academicYear = academicYear;
        this.receiptNumber = receiptNumber;
        this.status = status;
    }
    
    // Getters and Setters
    public int getPaymentId() {
        return paymentId;
    }
    
    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }
    
    public int getStudentId() {
        return studentId;
    }
    
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public String getPaymentDate() {
        return paymentDate;
    }
    
    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }
    
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public String getTransactionId() {
        return transactionId;
    }
    
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
    
    public String getSemester() {
        return semester;
    }
    
    public void setSemester(String semester) {
        this.semester = semester;
    }
    
    public String getAcademicYear() {
        return academicYear;
    }
    
    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }
    
    public String getReceiptNumber() {
        return receiptNumber;
    }
    
    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "FeePayment{" +
                "paymentId=" + paymentId +
                ", studentId=" + studentId +
                ", amount=" + amount +
                ", paymentDate='" + paymentDate + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", semester='" + semester + '\'' +
                ", academicYear='" + academicYear + '\'' +
                ", receiptNumber='" + receiptNumber + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}