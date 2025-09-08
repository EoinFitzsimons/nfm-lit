package nfm.lit;

import java.io.*;
import java.util.zip.*;

/**
 * Tool to extract and rebuild RADQ model files
 */
public class RadqModelManager {
    
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java RadqModelManager <extract|rebuild>");
            return;
        }
        
        String command = args[0];
        
        if ("extract".equals(command)) {
            extractModels();
        } else if ("rebuild".equals(command)) {
            rebuildModels();
        } else {
            System.out.println("Unknown command: " + command);
        }
    }
    
    /**
     * Extract all models from models.radq to individual files
     */
    public static void extractModels() {
        try {
            System.out.println("Extracting models from data/models.radq...");
            
            // Create extraction directory
            File extractDir = new File("extracted_models");
            if (!extractDir.exists()) {
                extractDir.mkdirs();
            }
            
            // Open the RADQ file
            ZipInputStream zipStream = new ZipInputStream(new FileInputStream("data/models.radq"));
            ZipEntry entry;
            
            int count = 0;
            while ((entry = zipStream.getNextEntry()) != null) {
                String filename = entry.getName();
                System.out.println("Extracting: " + filename);
                
                // Read the model data
                int size = (int) entry.getSize();
                byte[] data = new byte[size];
                int bytesRead = 0;
                while (bytesRead < size) {
                    int read = zipStream.read(data, bytesRead, size - bytesRead);
                    if (read == -1) break;
                    bytesRead += read;
                }
                
                // Write to file
                FileOutputStream fos = new FileOutputStream(new File(extractDir, filename));
                fos.write(data);
                fos.close();
                
                count++;
            }
            
            zipStream.close();
            System.out.println("Extracted " + count + " model files to extracted_models/");
            
        } catch (IOException e) {
            System.err.println("Error extracting models: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Rebuild models.radq from extracted files and new car models
     */
    public static void rebuildModels() {
        try {
            System.out.println("Rebuilding data/models.radq with enhanced car models...");
            
            // Backup original
            File original = new File("data/models.radq");
            File backup = new File("data/models.radq.backup");
            if (!backup.exists()) {
                copyFile(original, backup);
                System.out.println("Created backup: data/models.radq.backup");
            }
            
            // Create new RADQ file
            ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream("data/models.radq"));
            
            File extractDir = new File("extracted_models");
            if (!extractDir.exists()) {
                System.err.println("Error: extracted_models directory not found. Run extract first.");
                return;
            }
            
            // Add all existing model files
            File[] files = extractDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    addFileToZip(zipOut, file, file.getName());
                }
            }
            
            // Create missing model files for new cars
            createMissingCarModels(zipOut);
            
            zipOut.close();
            System.out.println("Successfully rebuilt data/models.radq with " + EnhancedCarSystem.ENHANCED_CAR_MODELS.length + " car models");
            
        } catch (IOException e) {
            System.err.println("Error rebuilding models: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Create model files for the new enhanced cars
     */
    private static void createMissingCarModels(ZipOutputStream zipOut) throws IOException {
        String[] carModels = EnhancedCarSystem.ENHANCED_CAR_MODELS;
        File extractDir = new File("extracted_models");
        
        // Find an existing car model to use as template
        File templateFile = null;
        String[] originalCars = {"2000tornados", "formula7", "canyenaro", "lescrab", "nimi", "maxrevenge", 
                                "leadoxide", "koolkat", "drifter", "policecops", "mustang", "king", 
                                "audir8", "masheen", "radicalone", "drmonster"};
        
        for (String originalCar : originalCars) {
            File carFile = new File(extractDir, originalCar + ".rad");
            if (carFile.exists()) {
                templateFile = carFile;
                break;
            }
        }
        
        if (templateFile == null) {
            System.err.println("Warning: No template car model found!");
            return;
        }
        
        System.out.println("Using template: " + templateFile.getName());
        byte[] templateData = readFileBytes(templateFile);
        
        // Create models for each new car
        for (int i = 16; i < carModels.length; i++) { // Start from index 16 (after original cars)
            String carName = carModels[i];
            File existingFile = new File(extractDir, carName + ".rad");
            
            if (!existingFile.exists()) {
                System.out.println("Creating model for new car: " + carName);
                
                // Create a variant of the template data for this car
                byte[] carData = createCarVariant(templateData, carName, i);
                
                // Add to ZIP
                ZipEntry entry = new ZipEntry(carName + ".rad");
                zipOut.putNextEntry(entry);
                zipOut.write(carData);
                zipOut.closeEntry();
            }
        }
    }
    
    /**
     * Create a car model variant by modifying template data
     */
    private static byte[] createCarVariant(byte[] templateData, String carName, int carIndex) {
        // Create a copy of the template
        byte[] carData = templateData.clone();
        
        // Apply variations based on car category and index
        int variation = carIndex % 4; // Create 4 different variants
        
        // Modify vertices slightly to create unique shapes
        for (int i = 0; i < carData.length - 8; i += 4) {
            if (i % 12 == 0) { // Every 3rd vertex
                // Apply small modifications based on car type
                int value = ((carData[i] & 0xFF) << 24) | ((carData[i+1] & 0xFF) << 16) | 
                           ((carData[i+2] & 0xFF) << 8) | (carData[i+3] & 0xFF);
                
                // Modify based on variation
                switch (variation) {
                    case 0: value += (carIndex * 10); break;      // Slightly longer
                    case 1: value -= (carIndex * 8); break;       // Slightly shorter  
                    case 2: value += (carIndex * 15); break;      // Wider
                    case 3: value -= (carIndex * 12); break;      // Narrower
                }
                
                // Write back
                carData[i] = (byte)(value >> 24);
                carData[i+1] = (byte)(value >> 16);
                carData[i+2] = (byte)(value >> 8);
                carData[i+3] = (byte)value;
            }
        }
        
        return carData;
    }
    
    // Helper methods
    private static void copyFile(File source, File dest) throws IOException {
        FileInputStream fis = new FileInputStream(source);
        FileOutputStream fos = new FileOutputStream(dest);
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = fis.read(buffer)) != -1) {
            fos.write(buffer, 0, bytesRead);
        }
        fis.close();
        fos.close();
    }
    
    private static void addFileToZip(ZipOutputStream zipOut, File file, String entryName) throws IOException {
        byte[] data = readFileBytes(file);
        ZipEntry entry = new ZipEntry(entryName);
        zipOut.putNextEntry(entry);
        zipOut.write(data);
        zipOut.closeEntry();
    }
    
    private static byte[] readFileBytes(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int)file.length()];
        fis.read(data);
        fis.close();
        return data;
    }
}
