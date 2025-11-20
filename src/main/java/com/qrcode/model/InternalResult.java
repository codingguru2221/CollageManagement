package com.qrcode.model;

public class InternalResult {
    private int resultId;
    private int studentId;
    private int courseId;
    private String examType;
    private double marksObtained;
    private double maxMarks;
    private String examDate;
    private String semester;
    private String academicYear;
    
    // Constructors
    public InternalResult() {}
    
    public InternalResult(int resultId, int studentId, int courseId, String examType, double marksObtained, 
                          double maxMarks, String examDate, String semester, String academicYear) {
        this.resultId = resultId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.examType = examType;
        this.marksObtained = marksObtained;
        this.maxMarks = maxMarks;
        this.examDate = examDate;
        this.semester = semester;
        this.academicYear = academicYear;
    }
    
    // Getters and Setters
    public int getResultId() {
        return resultId;
    }
    
    public void setResultId(int resultId) {
        this.resultId = resultId;
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
    
    public String getExamType() {
        return examType;
    }
    
    public void setExamType(String examType) {
        this.examType = examType;
    }
    
    public double getMarksObtained() {
        return marksObtained;
    }
    
    public void setMarksObtained(double marksObtained) {
        this.marksObtained = marksObtained;
    }
    
    public double getMaxMarks() {
        return maxMarks;
    }
    
    public void setMaxMarks(double maxMarks) {
        this.maxMarks = maxMarks;
    }
    
    public String getExamDate() {
        return examDate;
    }
    
    public void setExamDate(String examDate) {
        this.examDate = examDate;
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
    
    @Override
    public String toString() {
        return "InternalResult{" +
                "resultId=" + resultId +
                ", studentId=" + studentId +
                ", courseId=" + courseId +
                ", examType='" + examType + '\'' +
                ", marksObtained=" + marksObtained +
                ", maxMarks=" + maxMarks +
                ", examDate='" + examDate + '\'' +
                ", semester='" + semester + '\'' +
                ", academicYear='" + academicYear + '\'' +
                '}';
    }
}