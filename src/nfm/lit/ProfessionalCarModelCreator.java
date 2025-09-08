package nfm.lit;

import java.io.*;
import java.util.*;
import java.util.zip.*;

/**
 * Professional Car Model Analyzer and Creator
 * Analyzes original NFM car models to understand their structure
 * and creates professional new car models based on that analysis
 */
public class ProfessionalCarModelCreator {
    
    static class CarModelAnalysis {
        String name;
        int vertexCount;
        int planeCount;
        List<Integer> planeSizes;
        double[] boundingBox; // min/max x,y,z
        List<String> planeData;
        
        public CarModelAnalysis(String name) {
            this.name = name;
            this.planeSizes = new ArrayList<>();
            this.planeData = new ArrayList<>();
            this.boundingBox = new double[6]; // minX, maxX, minY, maxY, minZ, maxZ
        }
    }
    
    public static void main(String[] args) throws Exception {
        System.out.println("=== Professional Car Model Analysis & Creation ===");
        
        // Step 1: Extract and analyze original models
        Map<String, CarModelAnalysis> originalModels = extractAndAnalyzeOriginalModels();
        
        // Step 2: Display analysis results
        displayAnalysisResults(originalModels);
        
        // Step 3: Create professional new car models
        createProfessionalNewModels(originalModels);
        
        // Step 4: Rebuild models.radq with all cars
        rebuildModelsFile();
        
        System.out.println("\n=== Professional Car Model Creation Complete ===");
    }
    
    private static Map<String, CarModelAnalysis> extractAndAnalyzeOriginalModels() throws Exception {
        System.out.println("\n--- Analyzing Original Car Models ---");
        
        Map<String, CarModelAnalysis> analyses = new HashMap<>();
        String[] originalCars = {
            "2000tornados", "audir8", "canyenaro", "drifter", "drmonster", "formula7",
            "king", "koolkat", "leadoxide", "lescrab", "masheen", "maxrevenge", 
            "mustang", "nimi", "policecops", "radicalone"
        };
        
        // Extract original models.radq
        try (ZipFile zipFile = new ZipFile("data/models.radq")) {
            for (String carName : originalCars) {
                ZipEntry entry = zipFile.getEntry(carName + ".rad");
                if (entry != null) {
                    CarModelAnalysis analysis = analyzeCarModel(carName, zipFile.getInputStream(entry));
                    analyses.put(carName, analysis);
                    System.out.println("Analyzed: " + carName + " - " + analysis.vertexCount + " vertices, " + analysis.planeCount + " planes");
                }
            }
        }
        
        return analyses;
    }
    
    private static CarModelAnalysis analyzeCarModel(String name, InputStream inputStream) throws Exception {
        CarModelAnalysis analysis = new CarModelAnalysis(name);
        
        try (DataInputStream dis = new DataInputStream(inputStream)) {
            // Read vertex count
            analysis.vertexCount = dis.readInt();
            
            // Initialize bounding box
            analysis.boundingBox[0] = analysis.boundingBox[2] = analysis.boundingBox[4] = Double.MAX_VALUE; // min values
            analysis.boundingBox[1] = analysis.boundingBox[3] = analysis.boundingBox[5] = Double.MIN_VALUE; // max values
            
            // Read vertices and calculate bounding box
            for (int i = 0; i < analysis.vertexCount; i++) {
                int x = dis.readInt();
                int y = dis.readInt(); 
                int z = dis.readInt();
                
                // Update bounding box
                analysis.boundingBox[0] = Math.min(analysis.boundingBox[0], x); // minX
                analysis.boundingBox[1] = Math.max(analysis.boundingBox[1], x); // maxX
                analysis.boundingBox[2] = Math.min(analysis.boundingBox[2], y); // minY
                analysis.boundingBox[3] = Math.max(analysis.boundingBox[3], y); // maxY
                analysis.boundingBox[4] = Math.min(analysis.boundingBox[4], z); // minZ
                analysis.boundingBox[5] = Math.max(analysis.boundingBox[5], z); // maxZ
            }
            
            // Read plane count
            analysis.planeCount = dis.readInt();
            
            // Read planes and analyze structure
            for (int i = 0; i < analysis.planeCount; i++) {
                int planeVertexCount = dis.readInt();
                analysis.planeSizes.add(planeVertexCount);
                
                StringBuilder planeInfo = new StringBuilder();
                planeInfo.append("Plane ").append(i).append(" (").append(planeVertexCount).append(" vertices): ");
                
                for (int j = 0; j < planeVertexCount; j++) {
                    int vertexIndex = dis.readInt();
                    planeInfo.append(vertexIndex).append(" ");
                }
                
                // Read plane properties
                int stat = dis.readInt();
                int roadable = dis.readInt();
                int collideWidth = dis.readInt();
                
                planeInfo.append("| stat=").append(stat).append(" roadable=").append(roadable).append(" width=").append(collideWidth);
                analysis.planeData.add(planeInfo.toString());
            }
        }
        
        return analysis;
    }
    
    private static void displayAnalysisResults(Map<String, CarModelAnalysis> analyses) {
        System.out.println("\n--- Analysis Results ---");
        
        int totalVertices = 0;
        int totalPlanes = 0;
        Map<Integer, Integer> planeSizeDistribution = new HashMap<>();
        
        for (CarModelAnalysis analysis : analyses.values()) {
            System.out.println("\n" + analysis.name.toUpperCase() + ":");
            System.out.printf("  Vertices: %d\n", analysis.vertexCount);
            System.out.printf("  Planes: %d\n", analysis.planeCount);
            System.out.printf("  Bounding Box: [%.1f,%.1f] [%.1f,%.1f] [%.1f,%.1f]\n", 
                analysis.boundingBox[0], analysis.boundingBox[1],
                analysis.boundingBox[2], analysis.boundingBox[3], 
                analysis.boundingBox[4], analysis.boundingBox[5]);
            
            // Count plane sizes
            for (int size : analysis.planeSizes) {
                planeSizeDistribution.put(size, planeSizeDistribution.getOrDefault(size, 0) + 1);
            }
            
            totalVertices += analysis.vertexCount;
            totalPlanes += analysis.planeCount;
        }
        
        System.out.println("\n--- SUMMARY STATISTICS ---");
        System.out.printf("Average vertices per car: %.1f\n", (double) totalVertices / analyses.size());
        System.out.printf("Average planes per car: %.1f\n", (double) totalPlanes / analyses.size());
        
        System.out.println("\nPlane Size Distribution:");
        for (Map.Entry<Integer, Integer> entry : planeSizeDistribution.entrySet()) {
            System.out.printf("  %d vertices: %d planes (%.1f%%)\n", 
                entry.getKey(), entry.getValue(), 
                100.0 * entry.getValue() / totalPlanes);
        }
    }
    
    private static void createProfessionalNewModels(Map<String, CarModelAnalysis> originalModels) throws Exception {
        System.out.println("\n--- Creating Professional New Car Models ---");
        
        // Get template models for different car types
        CarModelAnalysis sportsTemplate = originalModels.get("formula7"); // Fast sports car
        CarModelAnalysis tankTemplate = originalModels.get("drmonster"); // Heavy tank
        CarModelAnalysis balancedTemplate = originalModels.get("audir8"); // Balanced car
        CarModelAnalysis drifterTemplate = originalModels.get("drifter"); // Agile car
        
        String[] newCars = {
            // Speed Demons (5) - based on formula7
            "velocityx", "hypersonic", "lightningbolt", "plasmawing", "ironbeast",
            // Heavy Tanks (5) - based on drmonster 
            "tankzilla", "steelwall", "fortress", "perfectbalance", "goldenmean",
            // Balanced Racers (5) - based on audir8
            "harmonyx", "equilibrium", "nimblewing", "shadowdancer", "quicksilver",
            // Agile Drifters (5) - based on drifter
            "ghostrider", "airmaster", "skydancer", "cloudripper", "acrobat",
            // Stunt Specialists (5) - based on masheen
            "crystalcar", "neonflash", "hologram", "prismatic", "championship",
            // Unique Exotics (5) - based on king
            "grandprix", "victorious", "legendary", "supremacy", "ultrabeast",
            // Professional Racers (4) - based on radicalone
            "omegaforce", "infinitum", "transcendent", "zenithrace"
        };
        
        // Create professional models directory
        new File("extracted_models/professional").mkdirs();
        
        int carIndex = 0;
        
        // Create Speed Demons (sleek, aerodynamic)
        for (int i = 0; i < 5; i++) {
            createProfessionalSpeedDemon(newCars[carIndex], sportsTemplate, i);
            carIndex++;
        }
        
        // Create Heavy Tanks (bulky, armored)
        for (int i = 0; i < 5; i++) {
            createProfessionalHeavyTank(newCars[carIndex], tankTemplate, i);
            carIndex++;
        }
        
        // Create Balanced Racers (proportioned, versatile)
        for (int i = 0; i < 5; i++) {
            createProfessionalBalancedRacer(newCars[carIndex], balancedTemplate, i);
            carIndex++;
        }
        
        // Create Agile Drifters (lightweight, nimble)
        for (int i = 0; i < 5; i++) {
            createProfessionalAgileDrifter(newCars[carIndex], drifterTemplate, i);
            carIndex++;
        }
        
        // Create Stunt Specialists (unique geometry)
        for (int i = 0; i < 5; i++) {
            createProfessionalStuntSpecialist(newCars[carIndex], originalModels.get("masheen"), i);
            carIndex++;
        }
        
        // Create Unique Exotics (luxury, distinctive)
        for (int i = 0; i < 5; i++) {
            createProfessionalUniqueExotic(newCars[carIndex], originalModels.get("king"), i);
            carIndex++;
        }
        
        // Create Professional Racers (high-performance)
        for (int i = 0; i < 4; i++) {
            createProfessionalRacer(newCars[carIndex], originalModels.get("radicalone"), i);
            carIndex++;
        }
        
        System.out.println("Created " + carIndex + " professional new car models");
    }
    
    private static void createProfessionalSpeedDemon(String name, CarModelAnalysis template, int variant) throws Exception {
        System.out.println("Creating Speed Demon: " + name);
        
        // Speed demons: elongated, lowered, aerodynamic modifications
        double scaleX = 1.1 + (variant * 0.05); // Slightly longer
        double scaleY = 0.85 - (variant * 0.02); // Lower profile
        double scaleZ = 1.0 + (variant * 0.03); // Slightly wider for stability
        
        double offsetX = variant * 50; // Slight variations in positioning
        double offsetY = -20 - (variant * 5); // Lower to ground
        double offsetZ = 0;
        
        createVariationModel(name, template, scaleX, scaleY, scaleZ, offsetX, offsetY, offsetZ, "speed");
    }
    
    private static void createProfessionalHeavyTank(String name, CarModelAnalysis template, int variant) throws Exception {
        System.out.println("Creating Heavy Tank: " + name);
        
        // Heavy tanks: wider, taller, more massive
        double scaleX = 1.2 + (variant * 0.08); // Much wider
        double scaleY = 1.3 + (variant * 0.06); // Taller
        double scaleZ = 1.15 + (variant * 0.05); // Longer
        
        double offsetX = variant * 30;
        double offsetY = 25 + (variant * 8); // Higher off ground
        double offsetZ = variant * 20;
        
        createVariationModel(name, template, scaleX, scaleY, scaleZ, offsetX, offsetY, offsetZ, "tank");
    }
    
    private static void createProfessionalBalancedRacer(String name, CarModelAnalysis template, int variant) throws Exception {
        System.out.println("Creating Balanced Racer: " + name);
        
        // Balanced: proportional modifications
        double scaleX = 1.0 + (variant * 0.04); // Slight width variation
        double scaleY = 1.0 + (variant * 0.03); // Slight height variation  
        double scaleZ = 1.0 + (variant * 0.04); // Slight length variation
        
        double offsetX = variant * 25;
        double offsetY = variant * 10;
        double offsetZ = variant * 15;
        
        createVariationModel(name, template, scaleX, scaleY, scaleZ, offsetX, offsetY, offsetZ, "balanced");
    }
    
    private static void createProfessionalAgileDrifter(String name, CarModelAnalysis template, int variant) throws Exception {
        System.out.println("Creating Agile Drifter: " + name);
        
        // Agile: compact, lightweight appearance
        double scaleX = 0.9 + (variant * 0.03); // Narrower
        double scaleY = 0.95 + (variant * 0.02); // Slightly lower
        double scaleZ = 0.95 + (variant * 0.03); // Slightly shorter
        
        double offsetX = variant * 40;
        double offsetY = -10 - (variant * 3);
        double offsetZ = variant * 25;
        
        createVariationModel(name, template, scaleX, scaleY, scaleZ, offsetX, offsetY, offsetZ, "agile");
    }
    
    private static void createProfessionalStuntSpecialist(String name, CarModelAnalysis template, int variant) throws Exception {
        System.out.println("Creating Stunt Specialist: " + name);
        
        // Stunt: unique proportions for stunts
        double scaleX = 1.05 + (variant * 0.06);
        double scaleY = 1.1 + (variant * 0.04); // Taller for stunts
        double scaleZ = 0.98 + (variant * 0.04);
        
        double offsetX = variant * 35;
        double offsetY = 15 + (variant * 6);
        double offsetZ = variant * 30;
        
        createVariationModel(name, template, scaleX, scaleY, scaleZ, offsetX, offsetY, offsetZ, "stunt");
    }
    
    private static void createProfessionalUniqueExotic(String name, CarModelAnalysis template, int variant) throws Exception {
        System.out.println("Creating Unique Exotic: " + name);
        
        // Exotic: distinctive, luxury proportions
        double scaleX = 1.08 + (variant * 0.07);
        double scaleY = 1.05 + (variant * 0.05);
        double scaleZ = 1.12 + (variant * 0.06); // Longer, more elegant
        
        double offsetX = variant * 45;
        double offsetY = 5 + (variant * 8);
        double offsetZ = variant * 35;
        
        createVariationModel(name, template, scaleX, scaleY, scaleZ, offsetX, offsetY, offsetZ, "exotic");
    }
    
    private static void createProfessionalRacer(String name, CarModelAnalysis template, int variant) throws Exception {
        System.out.println("Creating Professional Racer: " + name);
        
        // Professional: high-performance proportions
        double scaleX = 1.06 + (variant * 0.05);
        double scaleY = 0.92 + (variant * 0.03); // Lower for aerodynamics
        double scaleZ = 1.08 + (variant * 0.05); // Longer for speed
        
        double offsetX = variant * 38;
        double offsetY = -15 - (variant * 4);
        double offsetZ = variant * 28;
        
        createVariationModel(name, template, scaleX, scaleY, scaleZ, offsetX, offsetY, offsetZ, "professional");
    }
    
    private static void createVariationModel(String name, CarModelAnalysis template, 
                                           double scaleX, double scaleY, double scaleZ,
                                           double offsetX, double offsetY, double offsetZ,
                                           String category) throws Exception {
        
        // Read original template model data
        byte[] originalData = readOriginalModelData(template.name);
        
        // Create modified model with professional transformations
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             DataOutputStream dos = new DataOutputStream(baos)) {
            
            DataInputStream dis = new DataInputStream(new ByteArrayInputStream(originalData));
            
            // Read and write vertex count
            int vertexCount = dis.readInt();
            dos.writeInt(vertexCount);
            
            // Transform vertices with professional scaling and positioning
            for (int i = 0; i < vertexCount; i++) {
                int x = dis.readInt();
                int y = dis.readInt();
                int z = dis.readInt();
                
                // Apply professional transformations
                int newX = (int) (x * scaleX + offsetX);
                int newY = (int) (y * scaleY + offsetY);
                int newZ = (int) (z * scaleZ + offsetZ);
                
                dos.writeInt(newX);
                dos.writeInt(newY);
                dos.writeInt(newZ);
            }
            
            // Read and write plane count
            int planeCount = dis.readInt();
            dos.writeInt(planeCount);
            
            // Copy planes with validation (ensure all triangles)
            for (int i = 0; i < planeCount; i++) {
                int planeVertexCount = dis.readInt();
                
                // Ensure all planes are valid triangles (3 vertices minimum)
                if (planeVertexCount < 3) {
                    System.out.println("WARNING: Invalid plane with " + planeVertexCount + " vertices in " + name + ", converting to triangle");
                    planeVertexCount = 3;
                }
                
                dos.writeInt(planeVertexCount);
                
                // Write vertex indices
                for (int j = 0; j < planeVertexCount; j++) {
                    int vertexIndex = dis.readInt();
                    // Ensure vertex index is valid
                    if (vertexIndex >= vertexCount) {
                        vertexIndex = vertexIndex % vertexCount;
                    }
                    dos.writeInt(vertexIndex);
                }
                
                // Copy plane properties
                int stat = dis.readInt();
                int roadable = dis.readInt(); 
                int collideWidth = dis.readInt();
                
                dos.writeInt(stat);
                dos.writeInt(roadable);
                dos.writeInt(collideWidth);
            }
            
            // Write the professional model file
            byte[] modelData = baos.toByteArray();
            try (FileOutputStream fos = new FileOutputStream("extracted_models/professional/" + name + ".rad")) {
                fos.write(modelData);
            }
            
            System.out.println("  Created " + category + " car: " + name + " (" + vertexCount + " vertices, " + planeCount + " planes)");
        }
    }
    
    private static byte[] readOriginalModelData(String templateName) throws Exception {
        try (ZipFile zipFile = new ZipFile("data/models.radq")) {
            ZipEntry entry = zipFile.getEntry(templateName + ".rad");
            if (entry != null) {
                try (InputStream is = zipFile.getInputStream(entry);
                     ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                    
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = is.read(buffer)) != -1) {
                        baos.write(buffer, 0, length);
                    }
                    return baos.toByteArray();
                }
            }
        }
        throw new RuntimeException("Template model not found: " + templateName);
    }
    
    private static void rebuildModelsFile() throws Exception {
        System.out.println("\n--- Rebuilding models.radq with Professional Models ---");
        
        // Backup original
        new File("data/models.radq").renameTo(new File("data/models_original.radq"));
        
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream("data/models.radq"))) {
            
            // Copy all original models
            try (ZipFile originalZip = new ZipFile("data/models_original.radq")) {
                Enumeration<? extends ZipEntry> entries = originalZip.entries();
                while (entries.hasMoreElements()) {
                    ZipEntry entry = entries.nextElement();
                    
                    zos.putNextEntry(new ZipEntry(entry.getName()));
                    
                    try (InputStream is = originalZip.getInputStream(entry)) {
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = is.read(buffer)) != -1) {
                            zos.write(buffer, 0, length);
                        }
                    }
                    
                    zos.closeEntry();
                }
            }
            
            // Add all professional new models
            File professionalDir = new File("extracted_models/professional");
            if (professionalDir.exists()) {
                for (File modelFile : professionalDir.listFiles()) {
                    if (modelFile.getName().endsWith(".rad")) {
                        zos.putNextEntry(new ZipEntry(modelFile.getName()));
                        
                        try (FileInputStream fis = new FileInputStream(modelFile)) {
                            byte[] buffer = new byte[1024];
                            int length;
                            while ((length = fis.read(buffer)) != -1) {
                                zos.write(buffer, 0, length);
                            }
                        }
                        
                        zos.closeEntry();
                        System.out.println("Added professional model: " + modelFile.getName());
                    }
                }
            }
        }
        
        System.out.println("Professional models.radq rebuilt successfully!");
    }
}
