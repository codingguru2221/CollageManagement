package com.qrcode;

import com.qrcode.util.QRCodeUtil;
import com.google.zxing.WriterException;
import java.io.IOException;

public class QRCodeTest {
    
    public static void main(String[] args) {
        try {
            // Test generating a QR code
            String qrCodeText = QRCodeUtil.generateUniqueQRCodeString(1, "STU001");
            System.out.println("Generated QR Code Text: " + qrCodeText);
            
            // Test generating and saving QR code image
            String filePath = QRCodeUtil.generateAndSaveStudentQRCode(1, "STU001", "test_qr_codes");
            System.out.println("QR Code saved to: " + filePath);
            
            System.out.println("QR Code generation test completed successfully!");
        } catch (WriterException | IOException e) {
            System.err.println("Error during QR code test: " + e.getMessage());
            e.printStackTrace();
        }
    }
}