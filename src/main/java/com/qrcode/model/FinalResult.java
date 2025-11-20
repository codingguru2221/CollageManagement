package com.qrcode.model;

public class FinalResult {
    private int resultId;
    private int studentId;
    private int courseId;
    private double internalMarks;
    private double practicalMarks;
    private double finalExamMarks;
    private double totalMarks;
    private double maxMarks;
    private String grade;
    private double cgpa;
    private String semester;
    private String academicYear;
    private String resultDate;
    
    // Constructors
    public FinalResult() {}
    
    public FinalResult(int resultId, int studentId, int courseId, double internalMarks, double practicalMarks,
                       double finalExamMarks, double totalMarks, double maxMarks, String grade, double cgpa,
                       String semester, String academicYear, String resultDate) {
        this.resultId = resultId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.internalMarks = internalMarks;
        this.practicalMarks = practicalMarks;
        this.finalExamMarks = finalExamMarks;
        this.totalMarks = totalMarks;
        this.maxMarks = maxMarks;
        this.grade = grade;
        this.cgpa = cgpa;
        this.semester = semester;
        this.academicYear = academicYear;
        this.resultDate = resultDate;
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
    
    public double getInternalMarks() {
        return internalMarks;
    }
    
    public void setInternalMarks(double internalMarks) {
        this.internalMarks = internalMarks;
    }
    
    public double getPracticalMarks() {
        return practicalMarks;
    }
    
    public void setPracticalMarks(double practicalMarks) {
        this.practicalMarks = practicalMarks;
    }
    
    public double getFinalExamMarks() {
        return finalExamMarks;
    }
    
    public void setFinalExamMarks(double finalExamMarks) {
        this.finalExamMarks = finalExamMarks;
    }
    
    public double getTotalMarks() {
        return totalMarks;
    }
    
    public void setTotalMarks(double totalMarks) {
        this.totalMarks = totalMarks;
    }
    
    public double getMaxMarks() {
        return maxMarks;
    }
    
    public void setMaxMarks(double maxMarks) {
        this.maxMarks = maxMarks;
    }
    
    public String getGrade() {
        return grade;
    }
    
    public void setGrade(String grade) {
        this.grade = grade;
    }
    
    public double getCgpa() {
        return cgpa;
    }
    
    public void setCgpa(double cgpa) {
        this.cgpa = cgpa;
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
    
    public String getResultDate() {
        return resultDate;
    }
    
    public void setResultDate(String resultDate) {
        this.resultDate = resultDate;
    }
    
    @Override
    public String toString() {
        return "FinalResult{" +
                "resultId=" + resultId +
                ", studentId=" + studentId +
                ", courseId=" + courseId +
                ", internalMarks=" + internalMarks +
                ", practicalMarks=" + practicalMarks +
                ", finalExamMarks=" + finalExamMarks +
                ", totalMarks=" + totalMarks +
                ", maxMarks=" + maxMarks +
                ", grade='" + grade + '\'' +
                ", cgpa=" + cgpa +
                ", semester='" + semester + '\'' +
                ", academicYear='" + academicYear + '\'' +
                ", resultDate='" + resultDate + '\'' +
                '}';
    }
}