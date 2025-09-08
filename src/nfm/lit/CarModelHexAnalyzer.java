package nfm.lit;

import java.io.*;
import java.util.zip.*;

/**
 * Hex Dump Analyzer for Car Model Files
 * Analyzes the raw binary structure of car model files
 */
public class CarModelHexAnalyzer {
    
    public static void main(String[] args) throws Exception {
        System.out.println("=== Car Model Binary Structure Analysis ===");
        
        // Extract a few car models and examine their binary structure
        String[] carNames = {"formula7", "drmonster", "audir8"};
        
        try (ZipFile zipFile = new ZipFile("data/models.radq")) {
            for (String carName : carNames) {
                ZipEntry entry = zipFile.getEntry(carName + ".rad");
                if (entry != null) {
                    System.out.println("\n--- Analyzing: " + carName + ".rad ---");
                    analyzeBinaryStructure(carName, zipFile.getInputStream(entry));
                }
            }
        }
    }
    
    private static void analyzeBinaryStructure(String name, InputStream inputStream) throws Exception {
        byte[] data = readAllBytes(inputStream);
        
        System.out.println("File size: " + data.length + " bytes");
        System.out.println("First 256 bytes (hex dump):");
        
        // Print hex dump of first 256 bytes
        for (int i = 0; i < Math.min(256, data.length); i += 16) {
            System.out.printf("%04X: ", i);
            
            // Print hex bytes
            for (int j = 0; j < 16 && (i + j) < data.length; j++) {
                System.out.printf("%02X ", data[i + j] & 0xFF);
            }
            
            // Pad if needed
            for (int j = data.length - i; j < 16; j++) {
                System.out.print("   ");
            }
            
            System.out.print(" ");
            
            // Print ASCII representation
            for (int j = 0; j < 16 && (i + j) < data.length; j++) {
                byte b = data[i + j];
                if (b >= 32 && b <= 126) {
                    System.out.print((char) b);
                } else {
                    System.out.print(".");
                }
            }
            
            System.out.println();
        }
        
        // Try to interpret as integers
        System.out.println("\nFirst 20 integers (32-bit, big-endian):");
        try (DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data))) {
            for (int i = 0; i < 20 && dis.available() >= 4; i++) {
                int value = dis.readInt();
                System.out.printf("Int[%d]: %d (0x%08X)\n", i, value, value);
            }
        }
        
        System.out.println("\nFirst 20 integers (32-bit, little-endian):");
        for (int i = 0; i < 20 && (i * 4 + 3) < data.length; i++) {
            int value = ((data[i * 4] & 0xFF)) |
                       ((data[i * 4 + 1] & 0xFF) << 8) |
                       ((data[i * 4 + 2] & 0xFF) << 16) |
                       ((data[i * 4 + 3] & 0xFF) << 24);
            System.out.printf("Int[%d]: %d (0x%08X)\n", i, value, value);
        }
    }
    
    private static byte[] readAllBytes(InputStream inputStream) throws Exception {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }
            return baos.toByteArray();
        }
    }
}
