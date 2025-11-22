package com.qrcode;

import com.qrcode.model.User;
import com.qrcode.service.AuthenticationService;
import com.qrcode.service.AdminService;
import com.qrcode.service.TeacherService;
import com.qrcode.service.StudentService;
import com.qrcode.model.Admin;
import com.qrcode.model.Teacher;
import com.qrcode.model.Student;
import com.qrcode.util.DatabaseInitializer;
import com.qrcode.util.DatabaseConnection;
import com.google.zxing.NotFoundException;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;

import java.util.Scanner;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MainApp {
    private static Scanner scanner = new Scanner(System.in);
    private static AuthenticationService authService = new AuthenticationService();
    
    public static void main(String[] args) {
        // Initialize database and tables
        DatabaseInitializer.initializeDatabase();
        
        // Add sample courses if they don't exist
        addSampleCoursesIfNotExists();
        
        System.out.println("=========================================");
        System.out.println("    College Management System");
        System.out.println("=========================================");
        
        while (true) {
            System.out.println("\n1. Login as Admin");
            System.out.println("2. Login as Teacher");
            System.out.println("3. Login as Student");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    login("admin");
                    break;
                case 2:
                    login("teacher");
                    break;
                case 3:
                    login("student");
                    break;
                case 4:
                    System.out.println("Thank you for using College Management System!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    
    private static void login(String role) {
        User user = null;
        
        if ("student".equals(role)) {
            System.out.print("Enter student roll number: ");
            String rollNumber = scanner.nextLine();
            
            System.out.print("Enter student password: ");
            String password = scanner.nextLine();
            
            // Authenticate student by roll number
            user = authService.authenticateStudent(rollNumber, password);
        } else {
            System.out.print("Enter " + role + " username: ");
            String username = scanner.nextLine();
            
            System.out.print("Enter " + role + " password: ");
            String password = scanner.nextLine();
            
            // Authenticate user
            user = authService.authenticateUser(username, password);
        }
        
        if (user != null && user.getRole().equals(role)) {
            System.out.println("Login successful! Welcome, " + user.getUsername());
            
            switch (user.getRole()) {
                case "admin":
                    showAdminMenu(user);
                    break;
                case "teacher":
                    showTeacherMenu(user);
                    break;
                case "student":
                    showStudentMenu(user);
                    break;
                default:
                    System.out.println("Unknown role. Contact administrator.");
            }
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }
    
    private static void showAdminMenu(User user) {
        AdminService adminService = new AdminService();
        Admin admin = adminService.getAdminByUserId(user.getUserId());
        
        while (true) {
            System.out.println("\n=========================================");
            System.out.println("    Admin Dashboard - " + (admin != null ? admin.getName() : user.getUsername()));
            System.out.println("=========================================");
            System.out.println("1. View all students");
            System.out.println("2. View all teachers");
            System.out.println("3. View all events");
            System.out.println("4. Add new event");
            System.out.println("5. Add new student");
            System.out.println("6. Add new teacher");
            System.out.println("7. Logout");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    // View all students
                    System.out.println("\n--- All Students ---");
                    adminService.getAllStudents().forEach(student -> 
                        System.out.println("ID: " + student.getStudentId() + 
                                         ", Name: " + student.getName() + 
                                         ", Roll: " + student.getRollNumber() + 
                                         ", Batch: " + student.getBatch()));
                    break;
                case 2:
                    // View all teachers
                    System.out.println("\n--- All Teachers ---");
                    adminService.getAllTeachers().forEach(teacher -> 
                        System.out.println("ID: " + teacher.getTeacherId() + 
                                         ", Name: " + teacher.getName() + 
                                         ", Department: " + teacher.getDepartment()));
                    break;
                case 3:
                    // View all events
                    System.out.println("\n--- All Events ---");
                    adminService.getAllEvents().forEach(event -> 
                        System.out.println("Title: " + event.getTitle() + 
                                         ", Date: " + event.getEventDate() + 
                                         ", Venue: " + event.getVenue()));
                    break;
                case 4:
                    // Add new event
                    System.out.println("\n--- Add New Event ---");
                    System.out.print("Event Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Description: ");
                    String description = scanner.nextLine();
                    System.out.print("Event Date (YYYY-MM-DD): ");
                    String date = scanner.nextLine();
                    System.out.print("Event Time (HH:MM): ");
                    String time = scanner.nextLine();
                    System.out.print("Venue: ");
                    String venue = scanner.nextLine();
                    System.out.print("Organizer: ");
                    String organizer = scanner.nextLine();
                    
                    // In a real application, you would save this to the database
                    System.out.println("Event added successfully!");
                    break;
                case 5:
                    // Add new student
                    System.out.println("\n--- Add New Student ---");
                    System.out.print("Username: ");
                    String studentUsername = scanner.nextLine();
                    System.out.print("Password: ");
                    String studentPassword = scanner.nextLine();
                    System.out.print("Roll Number: ");
                    String rollNumber = scanner.nextLine();
                    System.out.print("Name: ");
                    String studentName = scanner.nextLine();
                    System.out.print("Email: ");
                    String studentEmail = scanner.nextLine();
                    System.out.print("Phone: ");
                    String studentPhone = scanner.nextLine();
                    System.out.print("Address: ");
                    String address = scanner.nextLine();
                    System.out.print("Date of Birth (YYYY-MM-DD): ");
                    String dob = scanner.nextLine();
                    System.out.print("Admission Date (YYYY-MM-DD): ");
                    String admissionDate = scanner.nextLine();
                    System.out.print("Batch: ");
                    String batch = scanner.nextLine();
                    System.out.print("Department: ");
                    String department = scanner.nextLine();
                    
                    // Create User and Student objects
                    User studentUser = new User();
                    studentUser.setUsername(studentUsername);
                    studentUser.setPassword(studentPassword);
                    studentUser.setRole("student");
                    
                    Student newStudent = new Student();
                    newStudent.setRollNumber(rollNumber);
                    newStudent.setName(studentName);
                    newStudent.setEmail(studentEmail);
                    newStudent.setPhone(studentPhone);
                    newStudent.setAddress(address);
                    newStudent.setDateOfBirth(dob);
                    newStudent.setAdmissionDate(admissionDate);
                    newStudent.setBatch(batch);
                    newStudent.setDepartment(department);
                    
                    // Save to database
                    if (adminService.addStudent(newStudent, studentUser)) {
                        System.out.println("Student added successfully!");
                    } else {
                        System.out.println("Failed to add student. Please try again.");
                    }
                    break;
                case 6:
                    // Add new teacher
                    System.out.println("\n--- Add New Teacher ---");
                    System.out.print("Username: ");
                    String teacherUsername = scanner.nextLine();
                    System.out.print("Password: ");
                    String teacherPassword = scanner.nextLine();
                    System.out.print("Name: ");
                    String teacherName = scanner.nextLine();
                    System.out.print("Email: ");
                    String teacherEmail = scanner.nextLine();
                    System.out.print("Phone: ");
                    String teacherPhone = scanner.nextLine();
                    System.out.print("Department: ");
                    String teacherDepartment = scanner.nextLine();
                    System.out.print("Hire Date (YYYY-MM-DD): ");
                    String hireDate = scanner.nextLine();
                    
                    // Create User and Teacher objects
                    User teacherUser = new User();
                    teacherUser.setUsername(teacherUsername);
                    teacherUser.setPassword(teacherPassword);
                    teacherUser.setRole("teacher");
                    
                    Teacher newTeacher = new Teacher();
                    newTeacher.setName(teacherName);
                    newTeacher.setEmail(teacherEmail);
                    newTeacher.setPhone(teacherPhone);
                    newTeacher.setDepartment(teacherDepartment);
                    newTeacher.setHireDate(hireDate);
                    
                    // Save to database
                    if (adminService.addTeacher(newTeacher, teacherUser)) {
                        System.out.println("Teacher added successfully!");
                    } else {
                        System.out.println("Failed to add teacher. Please try again.");
                    }
                    break;
                case 7:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    
    private static void showTeacherMenu(User user) {
        TeacherService teacherService = new TeacherService();
        Teacher teacher = teacherService.getTeacherByUserId(user.getUserId());
        
        while (true) {
            System.out.println("\n=========================================");
            System.out.println("    Teacher Dashboard - " + (teacher != null ? teacher.getName() : user.getUsername()));
            System.out.println("=========================================");
            System.out.println("1. View all students");
            System.out.println("2. View students by batch");
            System.out.println("3. Add internal result");
            System.out.println("4. Add final result");
            System.out.println("5. Mark Attendance");
            System.out.println("6. View Available Courses"); // New option
            System.out.println("7. Logout");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    // View all students
                    System.out.println("\n--- All Students ---");
                    teacherService.getAllStudents().forEach(student -> 
                        System.out.println("ID: " + student.getStudentId() + 
                                         ", Name: " + student.getName() + 
                                         ", Roll: " + student.getRollNumber() + 
                                         ", Batch: " + student.getBatch()));
                    break;
                case 2:
                    // View students by batch
                    System.out.print("Enter batch (e.g., 1st Year, 2nd Year): ");
                    String batch = scanner.nextLine();
                    System.out.println("\n--- Students in " + batch + " ---");
                    teacherService.getStudentsByBatch(batch).forEach(student -> 
                        System.out.println("ID: " + student.getStudentId() + 
                                         ", Name: " + student.getName() + 
                                         ", Roll: " + student.getRollNumber()));
                    break;
                case 3:
                    // Add internal result
                    System.out.println("\n--- Add Internal Result ---");
                    System.out.print("Student ID: ");
                    int studentId = scanner.nextInt();
                    System.out.print("Course ID: ");
                    int courseId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Exam Type (internal/practical/assignment): ");
                    String examType = scanner.nextLine();
                    System.out.print("Marks Obtained: ");
                    double marksObtained = scanner.nextDouble();
                    System.out.print("Maximum Marks: ");
                    double maxMarks = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Exam Date (YYYY-MM-DD): ");
                    String examDate = scanner.nextLine();
                    System.out.print("Semester: ");
                    String semester = scanner.nextLine();
                    System.out.print("Academic Year: ");
                    String academicYear = scanner.nextLine();
                    
                    // In a real application, you would save this to the database
                    System.out.println("Internal result added successfully!");
                    break;
                case 4:
                    // Add final result
                    System.out.println("\n--- Add Final Result ---");
                    System.out.print("Student ID: ");
                    int stdId = scanner.nextInt();
                    System.out.print("Course ID: ");
                    int crsId = scanner.nextInt();
                    System.out.print("Internal Marks: ");
                    double internalMarks = scanner.nextDouble();
                    System.out.print("Practical Marks: ");
                    double practicalMarks = scanner.nextDouble();
                    System.out.print("Final Exam Marks: ");
                    double finalExamMarks = scanner.nextDouble();
                    System.out.print("Total Marks: ");
                    double totalMarks = scanner.nextDouble();
                    System.out.print("Maximum Marks: ");
                    double maxMrks = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Grade: ");
                    String grade = scanner.nextLine();
                    System.out.print("CGPA: ");
                    double cgpa = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Semester: ");
                    String sem = scanner.nextLine();
                    System.out.print("Academic Year: ");
                    String acadYear = scanner.nextLine();
                    System.out.print("Result Date (YYYY-MM-DD): ");
                    String resultDate = scanner.nextLine();
                    
                    // In a real application, you would save this to the database
                    System.out.println("Final result added successfully!");
                    break;
                case 5:
                    // Mark Attendance
                    System.out.println("\n--- Mark Attendance ---");
                    
                    // Get all students and display them
                    List<Student> students = teacherService.getAllStudents();
                    if (students.isEmpty()) {
                        System.out.println("No students found.");
                        break;
                    }
                    
                    System.out.println("Select a student:");
                    for (int i = 0; i < students.size(); i++) {
                        Student student = students.get(i);
                        System.out.println((i + 1) + ". " + student.getName() + " (ID: " + student.getStudentId() + ", Roll: " + student.getRollNumber() + ")");
                    }
                    
                    System.out.print("Enter student number: ");
                    int studentChoice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    
                    if (studentChoice < 1 || studentChoice > students.size()) {
                        System.out.println("Invalid student selection.");
                        break;
                    }
                    
                    Student selectedStudent = students.get(studentChoice - 1);
                    int studId = selectedStudent.getStudentId();
                    
                    // Show available courses
                    System.out.println("\nAvailable Courses:");
                    try {
                        Connection connection = DatabaseConnection.getConnection();
                        String query = "SELECT course_id, course_code, course_name FROM courses";
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        ResultSet resultSet = preparedStatement.executeQuery();
                        
                        boolean hasCourses = false;
                        while (resultSet.next()) {
                            hasCourses = true;
                            int cId = resultSet.getInt("course_id");
                            String courseCode = resultSet.getString("course_code");
                            String courseName = resultSet.getString("course_name");
                            System.out.println(cId + ". " + courseCode + " - " + courseName);
                        }
                        
                        if (!hasCourses) {
                            System.out.println("No courses available in the database.");
                        }
                        
                        resultSet.close();
                        preparedStatement.close();
                        connection.close();
                    } catch (Exception e) {
                        System.err.println("Error fetching courses: " + e.getMessage());
                    }
                    
                    System.out.print("Enter Course ID: ");
                    int courseID = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    
                    // Get current date automatically
                    String date = java.time.LocalDate.now().toString();
                    System.out.println("Date (auto-generated): " + date);
                    
                    // Ask user to choose scanning method
                    System.out.println("\nSelect QR code scanning method:");
                    System.out.println("1. Scan from image file");
                    System.out.println("2. Scan using camera");
                    System.out.print("Choose an option: ");
                    
                    int scanMethod = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    
                    boolean attendanceMarked = false;
                    
                    if (scanMethod == 1) {
                        // Scan QR code from image file
                        System.out.print("Enter path to QR code image file: ");
                        String qrCodeImagePath = scanner.nextLine();
                        
                        try {
                            // Scan the QR code image
                            String scannedQRCode = com.qrcode.util.QRCodeScanner.scanQRCode(qrCodeImagePath);
                            System.out.println("Scanned QR Code: " + scannedQRCode);
                            
                            // Verify QR code and mark attendance
                            if (teacherService.verifyQRCode(studId, scannedQRCode)) {
                                if (teacherService.markAttendance(studId, courseID, date, scannedQRCode)) {
                                    System.out.println("Attendance marked successfully for " + selectedStudent.getName() + "!");
                                    attendanceMarked = true;
                                } else {
                                    System.out.println("Failed to mark attendance. Please try again.");
                                }
                            } else {
                                System.out.println("Invalid QR code. Attendance not marked.");
                            }
                        } catch (Exception e) {
                            System.err.println("Error scanning QR code: " + e.getMessage());
                            System.out.println("Failed to scan QR code. Please try again.");
                        }
                    } else if (scanMethod == 2) {
                        // Scan QR code using camera
                        System.out.println("Preparing to scan QR code using camera...");
                        System.out.println("A window will appear showing your camera feed.");
                        System.out.println("Position the student's QR code in the frame.");
                        System.out.println("The system will automatically detect and process the QR code.");
                        System.out.println("Scanning will timeout after 30 seconds if no QR code is detected.");
                        System.out.println("You can close the camera window to cancel scanning.");
                        System.out.println("\nPress Enter to start scanning...");
                        scanner.nextLine(); // Wait for user to press Enter
                        
                        if (teacherService.scanQRCodeAndMarkAttendance(studId, courseID)) {
                            System.out.println("Attendance marked successfully for " + selectedStudent.getName() + "!");
                            attendanceMarked = true;
                        } else {
                            System.out.println("Failed to mark attendance. Please try again.");
                        }
                    } else {
                        System.out.println("Invalid scanning method selected.");
                    }
                    
                    if (!attendanceMarked) {
                        System.out.println("Attendance was not marked successfully.");
                    }
                    break;
                case 6:
                    // View Available Courses
                    System.out.println("\n--- Available Courses ---");
                    try {
                        Connection conn = DatabaseConnection.getConnection();
                        String courseQuery = "SELECT course_id, course_code, course_name FROM courses";
                        PreparedStatement courseStmt = conn.prepareStatement(courseQuery);
                        ResultSet courseRs = courseStmt.executeQuery();
                        
                        if (!courseRs.isBeforeFirst()) {
                            System.out.println("No courses found in the database.");
                        } else {
                            System.out.println("ID\tCode\t\tName");
                            System.out.println("--\t----\t\t----");
                            while (courseRs.next()) {
                                int cId = courseRs.getInt("course_id");
                                String code = courseRs.getString("course_code");
                                String name = courseRs.getString("course_name");
                                System.out.println(cId + "\t" + code + "\t\t" + name);
                            }
                        }
                        
                        courseRs.close();
                        courseStmt.close();
                        conn.close();
                    } catch (Exception e) {
                        System.err.println("Error fetching courses: " + e.getMessage());
                        e.printStackTrace();
                    }
                    break;
                case 7:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    
    private static void showStudentMenu(User user) {
        StudentService studentService = new StudentService();
        Student student = studentService.getStudentByUserId(user.getUserId());
        
        while (true) {
            System.out.println("\n=========================================");
            System.out.println("    Student Dashboard - " + (student != null ? student.getName() : user.getUsername()));
            System.out.println("=========================================");
            System.out.println("1. View final results");
            System.out.println("2. View internal results");
            System.out.println("3. View fee payment history");
            System.out.println("4. Pay fees");
            System.out.println("5. View QR Code");
            System.out.println("6. Download QR Code");
            System.out.println("7. Logout");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    // View final results
                    System.out.println("\n--- Final Results ---");
                    if (student != null) {
                        studentService.getFinalResultsByStudent(student.getStudentId()).forEach(result -> 
                            System.out.println("Course ID: " + result.getCourseId() + 
                                             ", Semester: " + result.getSemester() + 
                                             ", Total Marks: " + result.getTotalMarks() + 
                                             ", Grade: " + result.getGrade() + 
                                             ", CGPA: " + result.getCgpa()));
                    }
                    break;
                case 2:
                    // View internal results
                    System.out.println("\n--- Internal Results ---");
                    if (student != null) {
                        studentService.getInternalResultsByStudent(student.getStudentId()).forEach(result -> 
                            System.out.println("Course ID: " + result.getCourseId() + 
                                             ", Exam Type: " + result.getExamType() + 
                                             ", Marks: " + result.getMarksObtained() + 
                                             "/" + result.getMaxMarks() + 
                                             ", Date: " + result.getExamDate()));
                    }
                    break;
                case 3:
                    // View fee payment history
                    System.out.println("\n--- Fee Payment History ---");
                    if (student != null) {
                        studentService.getFeePaymentsByStudent(student.getStudentId()).forEach(payment -> 
                            System.out.println("Amount: " + payment.getAmount() + 
                                             ", Date: " + payment.getPaymentDate() + 
                                             ", Method: " + payment.getPaymentMethod() + 
                                             ", Receipt: " + payment.getReceiptNumber() + 
                                             ", Status: " + payment.getStatus()));
                    }
                    break;
                case 4:
                    // Pay fees
                    System.out.println("\n--- Pay Fees ---");
                    if (student != null) {
                        System.out.print("Amount: ");
                        double amount = scanner.nextDouble();
                        scanner.nextLine(); // Consume newline
                        System.out.print("Payment Method (cash/card/online/cheque): ");
                        String method = scanner.nextLine();
                        System.out.print("Transaction ID (if applicable): ");
                        String transactionId = scanner.nextLine();
                        System.out.print("Semester: ");
                        String semester = scanner.nextLine();
                        System.out.print("Academic Year: ");
                        String academicYear = scanner.nextLine();
                        
                        // Generate receipt number (in real app, this would be more sophisticated)
                        String receiptNumber = "REC-" + System.currentTimeMillis();
                        
                        // In a real application, you would save this to the database
                        System.out.println("Fee payment successful! Receipt Number: " + receiptNumber);
                    }
                    break;
                case 5:
                    // View QR Code
                    System.out.println("\n--- Your QR Code ---");
                    if (student != null && student.getQrCode() != null) {
                        System.out.println("Your unique QR code identifier:");
                        System.out.println(student.getQrCode());
                        System.out.println("\nThis QR code can be used to mark your attendance.");
                    } else {
                        System.out.println("QR code not available. Please contact administrator.");
                    }
                    break;
                case 6:
                    // Download QR Code
                    System.out.println("\n--- Download QR Code ---");
                    if (student != null) {
                        try {
                            String outputPath = "qr_codes"; // Directory to save QR codes
                            String filePath = studentService.generateAndSaveStudentQRCode(student.getStudentId(), outputPath);
                            System.out.println("QR code saved successfully to: " + filePath);
                            System.out.println("You can find the QR code image in the '" + outputPath + "' directory.");
                        } catch (Exception e) {
                            System.err.println("Error generating QR code: " + e.getMessage());
                            System.out.println("Failed to generate QR code. Please try again.");
                        }
                    } else {
                        System.out.println("Student information not available.");
                    }
                    break;
                case 7:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    
    private static void addSampleCoursesIfNotExists() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            
            // Check if courses exist
            String countQuery = "SELECT COUNT(*) as count FROM courses";
            PreparedStatement countStmt = connection.prepareStatement(countQuery);
            ResultSet countRs = countStmt.executeQuery();
            countRs.next();
            int courseCount = countRs.getInt("count");
            countRs.close();
            countStmt.close();
            
            // If no courses exist, add sample courses
            if (courseCount == 0) {
                System.out.println("Adding sample courses to the database...");
                
                String insertQuery = "INSERT INTO courses (course_code, course_name, credits, department) VALUES (?, ?, ?, ?)";
                PreparedStatement insertStmt = connection.prepareStatement(insertQuery);
                
                // Add sample courses
                String[][] sampleCourses = {
                    {"CS101", "Introduction to Computer Science", "3", "Computer Science"},
                    {"MATH101", "Calculus I", "4", "Mathematics"},
                    {"ENG101", "English Composition", "3", "English"},
                    {"PHYS101", "General Physics I", "4", "Physics"}
                };
                
                for (String[] course : sampleCourses) {
                    insertStmt.setString(1, course[0]); // course_code
                    insertStmt.setString(2, course[1]); // course_name
                    insertStmt.setString(3, course[2]); // credits
                    insertStmt.setString(4, course[3]); // department
                    insertStmt.executeUpdate();
                }
                
                insertStmt.close();
                System.out.println("Sample courses added successfully!");
            }
            
            connection.close();
        } catch (Exception e) {
            System.err.println("Error adding sample courses: " + e.getMessage());
            e.printStackTrace();
        }
    }
}