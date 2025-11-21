package com.qrcode.util;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class QRCodeScanner {
    
    /**
     * Scans a QR code image file and returns the decoded text
     * @param filePath The path to the QR code image file
     * @return The decoded text from the QR code
     * @throws IOException If there's an error reading the image file
     * @throws NotFoundException If no QR code is found in the image
     * @throws ChecksumException If there's a checksum error
     * @throws FormatException If the QR code format is invalid
     */
    public static String scanQRCode(String filePath) throws IOException, NotFoundException, ChecksumException, FormatException {
        File qrCodeFile = new File(filePath);
        BufferedImage qrCodeImage = ImageIO.read(qrCodeFile);
        
        // Create a luminance source from the image
        BufferedImageLuminanceSource luminanceSource = new BufferedImageLuminanceSource(qrCodeImage);
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(luminanceSource));
        
        // Create a QR code reader
        QRCodeReader qrCodeReader = new QRCodeReader();
        
        // Decode the QR code
        Result result = qrCodeReader.decode(binaryBitmap);
        
        return result.getText();
    }
    
    /**
     * Validates if a QR code belongs to a specific student
     * @param qrCodeText The decoded QR code text
     * @param studentId The student ID to validate against
     * @param rollNumber The student roll number to validate against
     * @return true if the QR code is valid for the student, false otherwise
     */
    public static boolean validateStudentQRCode(String qrCodeText, int studentId, String rollNumber) {
        if (qrCodeText == null || qrCodeText.isEmpty()) {
            return false;
        }
        
        // The QR code format is: studentId-rollNumber-UUID
        String expectedPrefix = studentId + "-" + rollNumber;
        return qrCodeText.startsWith(expectedPrefix);
    }
}