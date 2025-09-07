package nfm.lit;

/**
 * Test demonstrating the extensibility of the NFM-Lit race settings system
 * for handling custom cars and larger races.
 */
public class ExtensibilityTest {
    
    public static void main(String[] args) {
        System.out.println("NFM-Lit Race Settings System - Extensibility Test");
        System.out.println("=================================================");
        
        testOriginalCarSupport();
        testCustomCarAddition();
        testLargerRaceSupport();
        testSystemLimitations();
    }
    
    /**
     * Test that original car system still works
     */
    private static void testOriginalCarSupport() {
        System.out.println("\n1. Original Car System Support:");
        System.out.println("   Original car count: " + CarConfig.CAR_MODELS.length);
        
        for (int i = 0; i < CarConfig.CAR_MODELS.length; i++) {
            System.out.println("   - " + CarConfig.CAR_MODELS[i] + " (index " + i + ")");
        }
        
        RaceSettingsManager manager = RaceSettingsManager.getInstance();
        int[] sc = {23, 1, 2, 3, 4, 5, 6}; // All original cars unlocked
        manager.updateUnlockProgression(sc);
        
        System.out.println("   Unlocked cars: " + manager.getUnlockedCars().size() + "/16");
    }
    
    /**
     * Test custom car addition capability
     */
    private static void testCustomCarAddition() {
        System.out.println("\n2. Custom Car Addition:");
        System.out.println("   Extended car count: " + EnhancedCarConfig.getTotalCarCount());
        
        // Show all cars including custom ones
        for (int i = 0; i < EnhancedCarConfig.getTotalCarCount(); i++) {
            String carName = EnhancedCarConfig.getCarName(i);
            String type = EnhancedCarConfig.isCustomCar(i) ? "[CUSTOM]" : "[ORIGINAL]";
            System.out.println("   - " + carName + " (index " + i + ") " + type);
        }
        
        // Show custom car details
        System.out.println("\n   Custom Car Specifications:");
        showCustomCarStats();
    }
    
    /**
     * Show custom car statistics
     */
    private static void showCustomCarStats() {
        String[] customCars = {"hypersonic", "tankzilla", "bouncemaster", "shadowracer"};
        
        for (int i = 0; i < customCars.length; i++) {
            System.out.println("   " + customCars[i] + ":");
            
            float[] accel = EnhancedCarConfig.ExtendedStats.CUSTOM_ACELF[i];
            int[] speed = EnhancedCarConfig.ExtendedStats.CUSTOM_SWITS[i];
            float aerial = EnhancedCarConfig.ExtendedStats.CUSTOM_AIRS[i];
            float strength = EnhancedCarConfig.ExtendedStats.CUSTOM_MOMENT[i];
            
            System.out.println("     Acceleration: " + accel[0] + "/" + accel[1] + "/" + accel[2]);
            System.out.println("     Top Speed: " + speed[0] + "/" + speed[1] + "/" + speed[2]);
            System.out.println("     Aerial Control: " + aerial);
            System.out.println("     Strength: " + strength);
        }
    }
    
    /**
     * Test larger race support
     */
    private static void testLargerRaceSupport() {
        System.out.println("\n3. Larger Race Support:");
        
        // Current system limitations
        System.out.println("   Current GameFacts.numberOfPlayers: " + GameFacts.numberOfPlayers);
        System.out.println("   Current GameFacts.numberOfCars: " + GameFacts.numberOfCars);
        
        // Test if we can increase race size
        System.out.println("\n   Testing increased race sizes:");
        
        // Test with more AI
        for (int aiCount = 7; aiCount <= 15; aiCount++) {
            boolean possible = testRaceConfiguration(aiCount);
            String status = possible ? "✓ POSSIBLE" : "✗ LIMITED";
            System.out.println("   - " + (aiCount + 1) + " total racers (1 player + " + aiCount + " AI): " + status);
        }
    }
    
    /**
     * Test if a race configuration is possible
     */
    private static boolean testRaceConfiguration(int aiCount) {
        try {
            RaceSettings settings = new RaceSettings();
            settings.setAiCount(aiCount);
            settings.setTrackIndex(0);
            settings.setLaps(3);
            settings.addSelectedCar(0);
            
            // Check if enough cars are available
            int totalCarsNeeded = aiCount + 1; // AI + player
            boolean enoughCars = totalCarsNeeded <= EnhancedCarConfig.getTotalCarCount();
            
            // Check if game engine supports it
            boolean withinGameLimits = totalCarsNeeded <= GameFacts.numberOfPlayers;
            
            return enoughCars && withinGameLimits && settings.validate();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Analyze system limitations
     */
    private static void testSystemLimitations() {
        System.out.println("\n4. System Limitations Analysis:");
        
        System.out.println("   Current Hard Limits:");
        System.out.println("   - Max players per race: " + GameFacts.numberOfPlayers + " (GameFacts.numberOfPlayers)");
        System.out.println("   - Original car count: " + CarConfig.CAR_MODELS.length + " (CarConfig.CAR_MODELS)");
        System.out.println("   - StatList arrays: 16 entries (hardcoded in StatList.java)");
        
        System.out.println("\n   Extensibility Assessment:");
        System.out.println("   ✓ Car models: DYNAMIC (can add via EnhancedCarConfig)");
        System.out.println("   ✓ Car stats: DYNAMIC (can extend with custom stat arrays)");
        System.out.println("   ✓ Race settings: DYNAMIC (uses MAX_PLAYERS from GameFacts)");
        System.out.println("   ✗ StatList arrays: STATIC (hardcoded 16-element arrays)");
        System.out.println("   ✗ UI limits: STATIC (car selection UI assumes 16 cars)");
        System.out.println("   ✗ AI limits: STATIC (numberOfPlayers=7 in GameFacts)");
        
        System.out.println("\n   Recommendations for Full Extensibility:");
        System.out.println("   1. Convert StatList to dynamic ArrayList-based system");
        System.out.println("   2. Increase GameFacts.numberOfPlayers for larger races");
        System.out.println("   3. Update car selection UI to handle dynamic car counts");
        System.out.println("   4. Add car loading system for custom car models");
        
        System.out.println("\n   Current Status: PARTIALLY EXTENSIBLE");
        System.out.println("   - Can add cars with custom stats");
        System.out.println("   - Limited by original game constraints");
        System.out.println("   - Race settings system is ready for expansion");
    }
}
