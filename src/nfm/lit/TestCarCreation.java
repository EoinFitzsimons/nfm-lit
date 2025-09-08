package nfm.lit;

/**
 * Test class to create the 34 new car models
 */
public class TestCarCreation {
    
    public static void main(String[] args) {
        System.out.println("=== NFM-Lit Professional Car Model Creation ===");
        System.out.println("Creating 34 new professional car models...");
        
        try {
            // Create all new car models
            EnhancedCarSystem.createNewCarModels();
            
            System.out.println("\n=== Car Model Creation Complete ===");
            System.out.println("Successfully created all 34 new car models!");
            System.out.println("All models have been packed into data/models.radq");
            System.out.println("The game now supports 50 cars total (16 original + 34 new)");
            
        } catch (Exception e) {
            System.err.println("Error during car model creation: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
