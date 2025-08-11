package nfm.lit;

/**
 * Demonstration and test class for the NFM-Lit Race Settings system.
 * Shows how to use the race settings with real game assets.
 * 
 * @author GitHub Copilot
 */
public class RaceSettingsDemo {
    
    public static void main(String[] args) {
        System.out.println("NFM-Lit Race Settings System Demo");
        System.out.println("==================================");
        
        // Initialize race settings manager
        RaceSettingsManager manager = RaceSettingsManager.getInstance();
        
        // Simulate unlock progression (normally comes from XtGraphics sc[] array)
        int[] sc = {7, 1, 2, 3, 4, 5, 6}; // Sample sc array (stage 0, 6 AI cars)
        manager.updateUnlockProgression(sc);
        
        System.out.println("\n1. Available Cars:");
        var unlockedCars = manager.getUnlockedCars();
        for (int carIndex : unlockedCars) {
            System.out.println("  - " + manager.getCarName(carIndex) + " (index " + carIndex + ")");
        }
        
        System.out.println("\n2. Available Tracks:");
        var unlockedTracks = manager.getUnlockedTracks();
        for (int i = 0; i < unlockedTracks.size(); i++) {
            TrackMetadata track = unlockedTracks.get(i);
            System.out.println("  - " + track.getDisplayName() + 
                " (laps: " + track.getDefaultLaps() + 
                ", checkpoints: " + track.getCheckpointCount() + ")");
        }
        
        // Configure a race
        System.out.println("\n3. Configuring Race:");
        RaceSettings settings = new RaceSettings();
        settings.setTrackIndex(0); // First track
        settings.setLaps(5);
        settings.setAiCount(4);
        settings.setDifficulty(RaceSettings.Difficulty.HARD);
        settings.addSelectedCar(0); // Select first unlocked car for player
        
        // Apply track metadata
        if (!unlockedTracks.isEmpty()) {
            TrackMetadata firstTrack = unlockedTracks.get(0);
            settings.setTrackName(firstTrack.getTrackName());
            settings.setTrackMetadata(firstTrack);
        }
        
        System.out.println("  Track: " + settings.getTrackName());
        System.out.println("  Laps: " + settings.getLaps());
        System.out.println("  AI Count: " + settings.getAiCount());
        System.out.println("  Difficulty: " + settings.getDifficulty().getDisplayName());
        System.out.println("  Total Cars: " + settings.getTotalCars());
        
        // Validate settings
        System.out.println("  Settings Valid: " + settings.validate());
        
        // Apply settings to manager
        boolean applied = manager.applySettings(settings);
        System.out.println("  Applied Successfully: " + applied);
        
        if (applied) {
            // Generate race configuration
            System.out.println("\n4. Race Configuration:");
            var config = manager.createRaceConfiguration();
            
            System.out.println("  Player Cars: " + config.getPlayerCars());
            System.out.println("  AI Cars: " + config.getAiCars());
            System.out.println("  Total Cars: " + config.getTotalCars());
            
            // Show sc[] array format (for existing game integration)
            int[] raceScArray = config.toScArray();
            System.out.print("  sc[] Array: [");
            for (int i = 0; i < raceScArray.length; i++) {
                System.out.print(raceScArray[i]);
                if (i < raceScArray.length - 1) System.out.print(", ");
            }
            System.out.println("]");
            
            // Show car names
            System.out.println("  Race Lineup:");
            for (int i = 0; i < raceScArray.length; i++) {
                String role = i == 0 ? "Player" : "AI " + i;
                String carName = manager.getCarName(raceScArray[i]);
                System.out.println("    " + role + ": " + carName);
            }
        }
        
        // Test different difficulties
        System.out.println("\n5. Difficulty Settings:");
        for (RaceSettings.Difficulty difficulty : RaceSettings.Difficulty.values()) {
            System.out.println("  " + difficulty.getDisplayName() + 
                ": AI Speed x" + difficulty.getAiSpeedMultiplier());
        }
        
        // Show track metadata details
        if (!unlockedTracks.isEmpty()) {
            System.out.println("\n6. Sample Track Metadata:");
            TrackMetadata track = unlockedTracks.get(0);
            System.out.println("  Name: " + track.getTrackName());
            System.out.println("  Default Laps: " + track.getDefaultLaps());
            System.out.println("  Checkpoints: " + track.getCheckpointCount());
            System.out.println("  Recommended AI: " + track.getRecommendedAiCount());
            System.out.println("  Estimated Difficulty: " + track.getEstimatedDifficulty().getDisplayName());
            System.out.println("  Valid: " + track.isValid());
            
            if (track.getSkyColor() != null) {
                int[] sky = track.getSkyColor();
                System.out.println("  Sky Color: RGB(" + sky[0] + ", " + sky[1] + ", " + sky[2] + ")");
            }
        }
        
        System.out.println("\nDemo completed successfully!");
    }
}
