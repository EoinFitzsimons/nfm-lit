import java.io.IOException;

public class TestVelocityX {
    public static void main(String[] args) {
        try {
            // Create just the VelocityX car to test it
            nfm.lit.EnhancedCarSystem.createVelocityX();
            System.out.println("VelocityX car model created successfully!");
            
            // Count polygons in the generated file
            java.io.File radFile = new java.io.File("data/velocityx.rad");
            if (radFile.exists()) {
                java.nio.file.Path path = radFile.toPath();
                long polygonCount = java.nio.file.Files.lines(path)
                    .filter(line -> line.trim().equals("<p>"))
                    .count();
                
                System.out.println("VelocityX polygon count: " + polygonCount);
                
                // Show file size for reference
                System.out.println("File size: " + radFile.length() + " bytes");
                
                if (polygonCount >= 150) {
                    System.out.println("✅ SUCCESS: VelocityX has " + polygonCount + " polygons (target: 150-200)");
                } else {
                    System.out.println("❌ INSUFFICIENT: VelocityX has only " + polygonCount + " polygons (needs 150-200)");
                }
            } else {
                System.out.println("❌ ERROR: velocityx.rad file not found");
            }
            
        } catch (Exception e) {
            System.err.println("Error testing VelocityX: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
