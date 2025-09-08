package nfm.lit;

import java.io.*;
import java.util.*;
import java.util.zip.*;
import java.util.regex.*;

/**
 * Professional Text-Based Car Model Creator
 * Analyzes and creates NFM car models using the discovered text format
 */
public class ProfessionalTextCarModelCreator {
    
    static class Point3D {
        int x, y, z;
        public Point3D(int x, int y, int z) {
            this.x = x; this.y = y; this.z = z;
        }
        
        public Point3D transform(double scaleX, double scaleY, double scaleZ, double offsetX, double offsetY, double offsetZ) {
            return new Point3D(
                (int)(x * scaleX + offsetX),
                (int)(y * scaleY + offsetY),
                (int)(z * scaleZ + offsetZ)
            );
        }
        
        @Override
        public String toString() {
            return "p(" + x + "," + y + "," + z + ")";
        }
    }
    
    static class CarModelData {
        String name;
        int idiv, iwid;
        boolean hasShadow;
        List<String> polygons;
        List<Point3D> allPoints;
        Set<String> colors;
        
        public CarModelData(String name) {
            this.name = name;
            this.polygons = new ArrayList<>();
            this.allPoints = new ArrayList<>();
            this.colors = new HashSet<>();
        }
    }
    
    public static void main(String[] args) throws Exception {
        System.out.println("=== Professional Text-Based Car Model Creator ===");
        
        // Step 1: Parse original car models
        Map<String, CarModelData> originalModels = parseOriginalModels();
        
        // Step 2: Display analysis
        displayModelAnalysis(originalModels);
        
        // Step 3: Create professional new models
        createProfessionalModels(originalModels);
        
        // Step 4: Rebuild models.radq
        rebuildModelsFile();
        
        System.out.println("\n=== Professional Car Model Creation Complete ===");
    }
    
    private static Map<String, CarModelData> parseOriginalModels() throws Exception {
        System.out.println("\n--- Parsing Original Car Models ---");
        
        Map<String, CarModelData> models = new HashMap<>();
        String[] originalCars = {
            "2000tornados", "audir8", "canyenaro", "drifter", "drmonster", "formula7",
            "king", "koolkat", "leadoxide", "lescrab", "masheen", "maxrevenge",
            "mustang", "nimi", "policecops", "radicalone"
        };
        
        try (ZipFile zipFile = new ZipFile("data/models.radq")) {
            for (String carName : originalCars) {
                ZipEntry entry = zipFile.getEntry(carName + ".rad");
                if (entry != null) {
                    CarModelData model = parseCarModel(carName, zipFile.getInputStream(entry));
                    models.put(carName, model);
                    System.out.println("Parsed: " + carName + " - " + model.allPoints.size() + " points, " + model.polygons.size() + " polygons, " + model.colors.size() + " colors");
                }
            }
        }
        
        return models;
    }
    
    private static CarModelData parseCarModel(String name, InputStream inputStream) throws Exception {
        CarModelData model = new CarModelData(name);
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            boolean inPolygon = false;
            StringBuilder currentPolygon = new StringBuilder();
            
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                
                // Parse header parameters
                if (line.startsWith("idiv(")) {
                    model.idiv = extractNumber(line);
                } else if (line.startsWith("iwid(")) {
                    model.iwid = extractNumber(line);
                } else if (line.contains("shadow()")) {
                    model.hasShadow = true;
                }
                
                // Parse colors
                if (line.startsWith("c(") && !line.startsWith("c(")) {
                    model.colors.add(line);
                }
                
                // Parse polygons
                if (line.equals("<p>")) {
                    inPolygon = true;
                    currentPolygon = new StringBuilder();
                } else if (line.equals("</p>")) {
                    inPolygon = false;
                    model.polygons.add(currentPolygon.toString());
                } else if (inPolygon) {
                    currentPolygon.append(line).append("\n");
                    
                    // Extract points
                    if (line.startsWith("p(")) {
                        Point3D point = parsePoint(line);
                        if (point != null) {
                            model.allPoints.add(point);
                        }
                    }
                }
            }
        }
        
        return model;
    }
    
    private static int extractNumber(String line) {
        Pattern pattern = Pattern.compile("\\((\\d+)\\)");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return 0;
    }
    
    private static Point3D parsePoint(String line) {
        Pattern pattern = Pattern.compile("p\\((-?\\d+),(-?\\d+),(-?\\d+)\\)");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            int x = Integer.parseInt(matcher.group(1));
            int y = Integer.parseInt(matcher.group(2));
            int z = Integer.parseInt(matcher.group(3));
            return new Point3D(x, y, z);
        }
        return null;
    }
    
    private static void displayModelAnalysis(Map<String, CarModelData> models) {
        System.out.println("\n--- Model Analysis Results ---");
        
        for (CarModelData model : models.values()) {
            System.out.println("\n" + model.name.toUpperCase() + ":");
            System.out.println("  idiv: " + model.idiv + ", iwid: " + model.iwid);
            System.out.println("  Shadow: " + model.hasShadow);
            System.out.println("  Points: " + model.allPoints.size());
            System.out.println("  Polygons: " + model.polygons.size());
            System.out.println("  Colors: " + model.colors.size());
            
            // Calculate bounding box
            if (!model.allPoints.isEmpty()) {
                int minX = model.allPoints.stream().mapToInt(p -> p.x).min().orElse(0);
                int maxX = model.allPoints.stream().mapToInt(p -> p.x).max().orElse(0);
                int minY = model.allPoints.stream().mapToInt(p -> p.y).min().orElse(0);
                int maxY = model.allPoints.stream().mapToInt(p -> p.y).max().orElse(0);
                int minZ = model.allPoints.stream().mapToInt(p -> p.z).min().orElse(0);
                int maxZ = model.allPoints.stream().mapToInt(p -> p.z).max().orElse(0);
                
                System.out.printf("  Bounding Box: X[%d,%d] Y[%d,%d] Z[%d,%d]\n", minX, maxX, minY, maxY, minZ, maxZ);
            }
        }
    }
    
    private static void createProfessionalModels(Map<String, CarModelData> originalModels) throws Exception {
        System.out.println("\n--- Creating Professional New Car Models ---");
        
        new File("extracted_models/professional").mkdirs();
        
        // Templates for different car types
        CarModelData sportsTemplate = originalModels.get("formula7");
        CarModelData tankTemplate = originalModels.get("drmonster");
        CarModelData balancedTemplate = originalModels.get("audir8");
        CarModelData drifterTemplate = originalModels.get("drifter");
        CarModelData stuntTemplate = originalModels.get("masheen");
        CarModelData luxuryTemplate = originalModels.get("king");
        CarModelData racerTemplate = originalModels.get("radicalone");
        
        // Create Speed Demons (5) - sleek, aerodynamic
        createSpeedDemonCars(sportsTemplate);
        
        // Create Heavy Tanks (5) - bulky, armored
        createHeavyTankCars(tankTemplate);
        
        // Create Balanced Racers (5) - proportioned
        createBalancedRacerCars(balancedTemplate);
        
        // Create Agile Drifters (5) - lightweight
        createAgileDrifterCars(drifterTemplate);
        
        // Create Stunt Specialists (5) - unique geometry
        createStuntSpecialistCars(stuntTemplate);
        
        // Create Unique Exotics (5) - luxury
        createUniqueExoticCars(luxuryTemplate);
        
        // Create Professional Racers (4) - high-performance
        createProfessionalRacerCars(racerTemplate);
    }
    
    private static void createSpeedDemonCars(CarModelData template) throws Exception {
        String[] names = {"velocityx", "hypersonic", "lightningbolt", "plasmawing", "ironbeast"};
        String[] colors = {"c(255,50,50)", "c(255,100,0)", "c(255,255,50)", "c(150,50,255)", "c(50,50,50)"};
        
        for (int i = 0; i < names.length; i++) {
            // Speed demons: elongated, lowered, aerodynamic
            double scaleX = 1.1 + (i * 0.04);  // Wider for stability
            double scaleY = 0.85 - (i * 0.02); // Lower profile
            double scaleZ = 1.15 + (i * 0.03); // Longer for speed
            
            double offsetX = i * 30;
            double offsetY = -15 - (i * 3); // Lower to ground
            double offsetZ = 0;
            
            createTransformedModel(names[i], template, scaleX, scaleY, scaleZ, offsetX, offsetY, offsetZ, colors[i], "Speed Demon");
        }
    }
    
    private static void createHeavyTankCars(CarModelData template) throws Exception {
        String[] names = {"tankzilla", "steelwall", "fortress", "perfectbalance", "goldenmean"};
        String[] colors = {"c(100,100,100)", "c(80,80,120)", "c(120,100,80)", "c(90,110,90)", "c(110,110,70)"};
        
        for (int i = 0; i < names.length; i++) {
            // Heavy tanks: wider, taller, more massive
            double scaleX = 1.25 + (i * 0.06); // Much wider
            double scaleY = 1.35 + (i * 0.05); // Taller
            double scaleZ = 1.2 + (i * 0.04);  // Longer
            
            double offsetX = i * 25;
            double offsetY = 20 + (i * 6); // Higher off ground
            double offsetZ = i * 15;
            
            createTransformedModel(names[i], template, scaleX, scaleY, scaleZ, offsetX, offsetY, offsetZ, colors[i], "Heavy Tank");
        }
    }
    
    private static void createBalancedRacerCars(CarModelData template) throws Exception {
        String[] names = {"harmonyx", "equilibrium", "nimblewing", "shadowdancer", "quicksilver"};
        String[] colors = {"c(100,150,200)", "c(150,100,200)", "c(200,150,100)", "c(50,50,100)", "c(200,200,200)"};
        
        for (int i = 0; i < names.length; i++) {
            // Balanced: proportional variations
            double scaleX = 1.0 + (i * 0.03);
            double scaleY = 1.0 + (i * 0.025);
            double scaleZ = 1.0 + (i * 0.035);
            
            double offsetX = i * 20;
            double offsetY = i * 8;
            double offsetZ = i * 12;
            
            createTransformedModel(names[i], template, scaleX, scaleY, scaleZ, offsetX, offsetY, offsetZ, colors[i], "Balanced Racer");
        }
    }
    
    private static void createAgileDrifterCars(CarModelData template) throws Exception {
        String[] names = {"ghostrider", "airmaster", "skydancer", "cloudripper", "acrobat"};
        String[] colors = {"c(200,200,255)", "c(255,200,200)", "c(200,255,200)", "c(255,255,200)", "c(255,200,255)"};
        
        for (int i = 0; i < names.length; i++) {
            // Agile: compact, lightweight
            double scaleX = 0.9 + (i * 0.025);
            double scaleY = 0.95 + (i * 0.02);
            double scaleZ = 0.95 + (i * 0.03);
            
            double offsetX = i * 35;
            double offsetY = -8 - (i * 2);
            double offsetZ = i * 22;
            
            createTransformedModel(names[i], template, scaleX, scaleY, scaleZ, offsetX, offsetY, offsetZ, colors[i], "Agile Drifter");
        }
    }
    
    private static void createStuntSpecialistCars(CarModelData template) throws Exception {
        String[] names = {"crystalcar", "neonflash", "hologram", "prismatic", "championship"};
        String[] colors = {"c(150,255,255)", "c(255,150,255)", "c(255,255,150)", "c(150,150,255)", "c(255,215,0)"};
        
        for (int i = 0; i < names.length; i++) {
            // Stunt: unique proportions
            double scaleX = 1.05 + (i * 0.04);
            double scaleY = 1.15 + (i * 0.03); // Taller for stunts
            double scaleZ = 1.0 + (i * 0.04);
            
            double offsetX = i * 28;
            double offsetY = 12 + (i * 4);
            double offsetZ = i * 26;
            
            createTransformedModel(names[i], template, scaleX, scaleY, scaleZ, offsetX, offsetY, offsetZ, colors[i], "Stunt Specialist");
        }
    }
    
    private static void createUniqueExoticCars(CarModelData template) throws Exception {
        String[] names = {"grandprix", "victorious", "legendary", "supremacy", "ultrabeast"};
        String[] colors = {"c(255,215,0)", "c(255,140,0)", "c(220,20,60)", "c(138,43,226)", "c(25,25,112)"};
        
        for (int i = 0; i < names.length; i++) {
            // Exotic: luxury proportions
            double scaleX = 1.08 + (i * 0.05);
            double scaleY = 1.05 + (i * 0.04);
            double scaleZ = 1.12 + (i * 0.05); // Longer, elegant
            
            double offsetX = i * 32;
            double offsetY = 8 + (i * 5);
            double offsetZ = i * 30;
            
            createTransformedModel(names[i], template, scaleX, scaleY, scaleZ, offsetX, offsetY, offsetZ, colors[i], "Unique Exotic");
        }
    }
    
    private static void createProfessionalRacerCars(CarModelData template) throws Exception {
        String[] names = {"omegaforce", "infinitum", "transcendent", "zenithrace"};
        String[] colors = {"c(255,69,0)", "c(30,144,255)", "c(50,205,50)", "c(255,20,147)"};
        
        for (int i = 0; i < names.length; i++) {
            // Professional: high-performance
            double scaleX = 1.06 + (i * 0.04);
            double scaleY = 0.92 + (i * 0.03); // Lower for aerodynamics
            double scaleZ = 1.1 + (i * 0.04);  // Longer for speed
            
            double offsetX = i * 36;
            double offsetY = -12 - (i * 3);
            double offsetZ = i * 25;
            
            createTransformedModel(names[i], template, scaleX, scaleY, scaleZ, offsetX, offsetY, offsetZ, colors[i], "Professional Racer");
        }
    }
    
    private static void createTransformedModel(String name, CarModelData template, 
                                             double scaleX, double scaleY, double scaleZ,
                                             double offsetX, double offsetY, double offsetZ,
                                             String primaryColor, String category) throws Exception {
        
        System.out.println("Creating " + category + ": " + name);
        
        StringBuilder model = new StringBuilder();
        
        // Header with professional parameters
        model.append("idiv(").append((int)(template.idiv * Math.max(scaleX, scaleZ))).append(")\r\n");
        model.append("iwid(").append((int)(template.iwid * scaleY)).append(")\r\n");
        if (template.hasShadow) {
            model.append("shadow()\r\n");
        }
        model.append("\r\n");
        
        // Add primary color
        model.append("// ").append(category).append(" - ").append(name).append("\r\n");
        model.append(primaryColor).append("\r\n");
        model.append("\r\n");
        
        // Transform and add polygons
        for (String polygon : template.polygons) {
            model.append("<p>\r\n");
            
            String[] lines = polygon.split("\n");
            for (String line : lines) {
                line = line.trim();
                if (line.isEmpty()) continue;
                
                if (line.startsWith("p(")) {
                    Point3D point = parsePoint(line);
                    if (point != null) {
                        Point3D transformed = point.transform(scaleX, scaleY, scaleZ, offsetX, offsetY, offsetZ);
                        model.append(transformed.toString()).append("\r\n");
                    }
                } else {
                    model.append(line).append("\r\n");
                }
            }
            
            model.append("</p>\r\n\r\n");
        }
        
        // Write professional model file
        try (FileOutputStream fos = new FileOutputStream("extracted_models/professional/" + name + ".rad")) {
            fos.write(model.toString().getBytes());
        }
        
        System.out.println("  ✓ " + name + " created with " + template.allPoints.size() + " transformed points");
    }
    
    private static void rebuildModelsFile() throws Exception {
        System.out.println("\n--- Rebuilding models.radq with Professional Models ---");
        
        // Backup original
        new File("data/models.radq").renameTo(new File("data/models_backup.radq"));
        
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream("data/models.radq"))) {
            
            // Copy all original models from backup
            try (ZipFile originalZip = new ZipFile("data/models_backup.radq")) {
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
                int addedCount = 0;
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
                        addedCount++;
                    }
                }
                
                System.out.println("✓ Added " + addedCount + " professional car models to models.radq");
            }
        }
        
        System.out.println("✓ Professional models.radq rebuilt successfully!");
    }
}
