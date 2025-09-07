package nfm.lit;

/**
 * Test demonstration showing race settings for an early game player
 * with limited car unlocks.
 */
public class RaceSettingsTestEarlyGame {
    
    public static void main(String[] args) {
        System.out.println("NFM-Lit Race Settings - Early Game Player");
        System.out.println("==========================================");
        
        // Initialize race settings manager
        RaceSettingsManager manager = RaceSettingsManager.getInstance();
        
        // Simulate early game progression - player just completed stage 2
        int[] sc = {9, 1, 2, 3, 4, 5, 6}; // Stage 2 completed (9-7=2), unlocks 4 cars (2*2=4)
        manager.updateUnlockProgression(sc);
        
        System.out.println("Game Progression: Stage " + (sc[0] - 7) + " completed");
        System.out.println("Cars unlocked: " + ((sc[0] - 7) * 2) + " out of " + CarConfig.CAR_MODELS.length);
        
        System.out.println("\nAll Cars Status:");
        for (int i = 0; i < CarConfig.CAR_MODELS.length; i++) {
            String status = manager.isCarUnlocked(i) ? "UNLOCKED" : "locked";
            System.out.println("  - " + CarConfig.CAR_MODELS[i] + " (index " + i + ") [" + status + "]");
        }
        
        System.out.println("\nAvailable for Selection:");
        var unlockedCars = manager.getUnlockedCars();
        for (int carIndex : unlockedCars) {
            System.out.println("  - " + manager.getCarName(carIndex) + " (index " + carIndex + ")");
        }
        
        System.out.println("\nEarly Game Race Configuration:");
        RaceSettings settings = new RaceSettings();
        settings.setTrackIndex(0);
        settings.setLaps(3);
        settings.setAiCount(3); // Smaller race for early game
        settings.setDifficulty(RaceSettings.Difficulty.EASY);
        settings.addSelectedCar(0); // Player uses first unlocked car
        
        if (manager.applySettings(settings)) {
            var config = manager.createRaceConfiguration();
            System.out.println("  Player: " + manager.getCarName(config.getPlayerCars().get(0)));
            System.out.print("  AI Cars: ");
            for (int aiCar : config.getAiCars()) {
                System.out.print(manager.getCarName(aiCar) + " ");
            }
            System.out.println();
        }
        
        System.out.println("\nDemo completed!");
    }
}
