package com.qrcode.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class QRCodeUtil {
    
    private static final int QR_CODE_SIZE = 300;
    
    /**
     * Generates a unique QR code string for a student
     * @param studentId The student ID
     * @param rollNumber The student roll number
     * @return A unique QR code string
     */
    public static String generateUniqueQRCodeString(int studentId, String rollNumber) {
        // Create a unique identifier combining student ID, roll number and a UUID
        String uniqueId = studentId + "-" + rollNumber + "-" + UUID.randomUUID().toString();
        return uniqueId;
    }
    
    /**
     * Generates a QR code image from the given text
     * @param text The text to encode in the QR code
     * @return A BufferedImage representing the QR code
     * @throws WriterException If there's an error generating the QR code
     */
    public static BufferedImage generateQRCodeImage(String text) throws WriterException {
        // Set up encoding hints
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 1);
        
        // Create the QR code writer
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, QR_CODE_SIZE, QR_CODE_SIZE, hints);
        
        // Convert BitMatrix to BufferedImage
        BufferedImage qrCodeImage = new BufferedImage(QR_CODE_SIZE, QR_CODE_SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = qrCodeImage.createGraphics();
        
        // Fill background with white
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, QR_CODE_SIZE, QR_CODE_SIZE);
        
        // Draw QR code in black
        graphics.setColor(Color.BLACK);
        for (int x = 0; x < QR_CODE_SIZE; x++) {
            for (int y = 0; y < QR_CODE_SIZE; y++) {
                if (bitMatrix.get(x, y)) {
                    graphics.fillRect(x, y, 1, 1);
                }
            }
        }
        
        graphics.dispose();
        return qrCodeImage;
    }
    
    /**
     * Saves a QR code image to a file
     * @param qrCodeImage The QR code image to save
     * @param filePath The file path where to save the image
     * @throws IOException If there's an error saving the image
     */
    public static void saveQRCodeImage(BufferedImage qrCodeImage, String filePath) throws IOException {
        File qrCodeFile = new File(filePath);
        ImageIO.write(qrCodeImage, "PNG", qrCodeFile);
    }
    
    /**
     * Generates and saves a QR code for a student
     * @param studentId The student ID
     * @param rollNumber The student roll number
     * @param outputPath The directory where to save the QR code image
     * @return The file path of the saved QR code image
     * @throws WriterException If there's an error generating the QR code
     * @throws IOException If there's an error saving the image
     */
    public static String generateAndSaveStudentQRCode(int studentId, String rollNumber, String outputPath) 
            throws WriterException, IOException {
        // Generate unique QR code string
        String qrCodeText = generateUniqueQRCodeString(studentId, rollNumber);
        
        // Generate QR code image
        BufferedImage qrCodeImage = generateQRCodeImage(qrCodeText);
        
        // Create output directory if it doesn't exist
        File outputDir = new File(outputPath);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }
        
        // Save QR code image
        String fileName = "student_" + studentId + "_" + rollNumber + ".png";
        String filePath = outputPath + File.separator + fileName;
        saveQRCodeImage(qrCodeImage, filePath);
        
        return filePath;
    }
}