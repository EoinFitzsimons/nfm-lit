package nfm.lit;

import java.io.IOException;

/**
 * Simple runner to generate VelocityX car model for testing
 */
public class GenerateVelocityX {
    public static void main(String[] args) {
        System.out.println("Generating VelocityX car model...");
        
        try {
            // Generate VelocityX using static method
            EnhancedCarSystem.createVelocityX();
            
            System.out.println("VelocityX model generated successfully!");
            System.out.println("Now packing VelocityX into models.radq archive...");
            
            // Pack the new model into the archive
            EnhancedCarSystem.packModelsIntoArchive();
            
            System.out.println("VelocityX successfully added to models.radq!");
            System.out.println("Ready to test in game!");
        } catch (IOException e) {
            System.err.println("Error generating VelocityX: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
