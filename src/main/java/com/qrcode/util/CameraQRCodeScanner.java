package com.qrcode.util;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicBoolean;

public class CameraQRCodeScanner {
    
    private Webcam webcam;
    private WebcamPanel panel;
    private AtomicBoolean scanning = new AtomicBoolean(false);
    
    /**
     * Initializes the webcam for QR code scanning
     */
    public void initializeCamera() {
        try {
            // Get default webcam and set resolution
            webcam = Webcam.getDefault();
            if (webcam == null) {
                throw new RuntimeException("No webcam detected");
            }
            
            Dimension resolution = WebcamResolution.VGA.getSize();
            webcam.setViewSize(resolution);
            webcam.open();
            
            System.out.println("Camera initialized successfully");
        } catch (Exception e) {
            System.err.println("Error initializing camera: " + e.getMessage());
            throw new RuntimeException("Failed to initialize camera", e);
        }
    }
    
    /**
     * Scans QR code using the camera until a valid QR code is found or timeout occurs
     * @param timeoutSeconds Maximum time to scan in seconds
     * @return The decoded text from the QR code, or null if no QR code was found
     */
    public String scanQRCodeFromCamera(int timeoutSeconds) {
        if (webcam == null || !webcam.isOpen()) {
            throw new IllegalStateException("Camera not initialized or not open");
        }
        
        // Create a window to display the camera feed
        JFrame window = new JFrame("QR Code Scanner - Align QR code in frame");
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        window.setPreferredSize(new Dimension(640, 480));
        
        // Create webcam panel
        panel = new WebcamPanel(webcam);
        panel.setFPSDisplayed(true);
        panel.setDisplayDebugInfo(true);
        panel.setImageSizeDisplayed(true);
        panel.setMirrored(false);
        
        window.add(panel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
        scanning.set(true);
        long startTime = System.currentTimeMillis();
        long timeoutMillis = timeoutSeconds * 1000;
        
        System.out.println("Scanning for QR code... Close the window or wait for timeout to stop");
        
        // Create QR code reader
        QRCodeReader qrCodeReader = new QRCodeReader();
        
        try {
            while (scanning.get() && (System.currentTimeMillis() - startTime) < timeoutMillis && window.isDisplayable()) {
                // Capture image from webcam
                BufferedImage image = webcam.getImage();
                
                if (image != null) {
                    try {
                        // Convert image to luminance source
                        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
                        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                        
                        // Decode QR code
                        Result result = qrCodeReader.decode(bitmap);
                        
                        if (result != null) {
                            String qrCodeText = result.getText();
                            System.out.println("QR Code detected: " + qrCodeText);
                            window.dispose();
                            return qrCodeText;
                        }
                    } catch (NotFoundException e) {
                        // No QR code found in this frame, continue scanning
                    } catch (ChecksumException | FormatException e) {
                        // QR code found but couldn't be decoded properly
                        System.err.println("Error decoding QR code: " + e.getMessage());
                    }
                }
                
                // Small delay to prevent excessive CPU usage
                Thread.sleep(100);
            }
        } catch (Exception e) {
            System.err.println("Error during QR code scanning: " + e.getMessage());
            e.printStackTrace();
        } finally {
            window.dispose();
        }
        
        System.out.println("QR code scanning timed out or was cancelled");
        return null;
    }
    
    /**
     * Stops the scanning process
     */
    public void stopScanning() {
        scanning.set(false);
    }
    
    /**
     * Closes the webcam
     */
    public void closeCamera() {
        scanning.set(false);
        if (webcam != null && webcam.isOpen()) {
            webcam.close();
            System.out.println("Camera closed");
        }
    }
    
    /**
     * Checks if a camera is available
     * @return true if a camera is available, false otherwise
     */
    public static boolean isCameraAvailable() {
        try {
            Webcam webcam = Webcam.getDefault();
            return webcam != null;
        } catch (Exception e) {
            return false;
        }
    }
}