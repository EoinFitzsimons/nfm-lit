package nfm.lit;

/**
 * Test to verify the correct NFM car unlock progression
 */
public class NFMCorrectUnlockTest {
    
    public static void main(String[] args) {
        System.out.println("NFM Correct Car Unlock Progression Test");
        System.out.println("=======================================");
        
        RaceSettingsManager manager = RaceSettingsManager.getInstance();
        
        // Test different stages of progression
        testStage(manager, 0, "Game Start");
        testStage(manager, 2, "After Track 2 (Drifter unlocked)");
        testStage(manager, 4, "After Track 4 (Policecops unlocked)");
        testStage(manager, 8, "After Track 8 (King unlocked)");
        testStage(manager, 16, "After Track 16 (Dr Monstaa unlocked)");
        testStage(manager, 17, "Track 17 (Playground)");
    }
    
    private static void testStage(RaceSettingsManager manager, int stage, String description) {
        System.out.println("\n" + description + " (Stage " + stage + "):");
        
        // Simulate progression
        int[] sc = {7 + stage, 1, 2, 3, 4, 5, 6};
        manager.updateUnlockProgression(sc);
        
        var unlockedCars = manager.getUnlockedCars();
        System.out.println("  Unlocked cars (" + unlockedCars.size() + "/16):");
        
        for (int i = 0; i < CarConfig.CAR_MODELS.length; i++) {
            String status = manager.isCarUnlocked(i) ? "✓" : "✗";
            System.out.println("    " + status + " " + CarConfig.CAR_MODELS[i] + " (index " + i + ")");
        }
    }
}
