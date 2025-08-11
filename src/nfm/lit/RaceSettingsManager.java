package nfm.lit;

import java.util.*;

/**
 * Manages race configuration and integrates with existing NFM-Lit game systems.
 * Handles car unlock progression, track selection, and race parameter validation.
 * 
 * @author GitHub Copilot
 */
public class RaceSettingsManager {
    
    private static RaceSettingsManager instance;
    
    // Current race configuration
    private RaceSettings currentSettings;
    
    // Available tracks cache
    private List<TrackMetadata> availableTracks;
    
    // Car unlock progression (integrates with existing game progression)
    private int unlockedStages;
    private Set<Integer> unlockedCars;
    
    private RaceSettingsManager() {
        this.currentSettings = new RaceSettings();
        this.availableTracks = new ArrayList<>();
        this.unlockedStages = 1; // Start with first stage unlocked
        this.unlockedCars = new HashSet<>();
        
        // Initialize with first car unlocked
        this.unlockedCars.add(0);
        
        loadAvailableTracks();
    }
    
    public static RaceSettingsManager getInstance() {
        if (instance == null) {
            instance = new RaceSettingsManager();
        }
        return instance;
    }
    
    /**
     * Load available tracks from stage files
     */
    private void loadAvailableTracks() {
        availableTracks = TrackMetadata.StageLoader.loadAllTracks();
    }
    
    /**
     * Get current race settings
     */
    public RaceSettings getCurrentSettings() {
        return currentSettings;
    }
    
    /**
     * Apply new race settings with validation
     */
    public boolean applySettings(RaceSettings newSettings) {
        if (newSettings == null || !newSettings.validate()) {
            return false;
        }
        
        // Check car unlock requirements
        for (int carIndex : newSettings.getSelectedCarIndices()) {
            if (!isCarUnlocked(carIndex)) {
                return false; // Car not unlocked yet
            }
        }
        
        // Check track unlock requirements
        if (!isTrackUnlocked(newSettings.getTrackIndex())) {
            return false;
        }
        
        // Apply track metadata
        if (newSettings.getTrackIndex() < availableTracks.size()) {
            TrackMetadata metadata = availableTracks.get(newSettings.getTrackIndex());
            newSettings.setTrackMetadata(metadata);
            
            // Auto-adjust laps to track default if not set
            if (metadata.isValid() && metadata.getDefaultLaps() > 0) {
                newSettings.setLaps(metadata.getDefaultLaps());
            }
        }
        
        this.currentSettings = newSettings.copy();
        return true;
    }
    
    /**
     * Update unlock progression based on existing game state (integrates with XtGraphics sc[] array)
     */
    public void updateUnlockProgression(int[] sc) {
        if (sc == null || sc.length == 0) return;
        
        // Extract unlock information from existing game state
        // Based on XtGraphics carselect() logic: "(sc[0] - 7) * 2 < unlocked"
        this.unlockedStages = Math.max(1, sc[0] - 7);
        
        // Unlock cars based on stage progression
        updateCarUnlocks();
    }
    
    /**
     * Update car unlocks based on stage progression
     */
    private void updateCarUnlocks() {
        unlockedCars.clear();
        
        // Always unlock first car
        unlockedCars.add(0);
        
        // Unlock cars based on stages completed
        // This follows the existing NFM unlock pattern
        int carsToUnlock = Math.min(GameFacts.numberOfCars, unlockedStages * 2);
        for (int i = 0; i < carsToUnlock; i++) {
            unlockedCars.add(i);
        }
    }
    
    /**
     * Check if car is unlocked
     */
    public boolean isCarUnlocked(int carIndex) {
        return unlockedCars.contains(carIndex);
    }
    
    /**
     * Check if track is unlocked
     */
    public boolean isTrackUnlocked(int trackIndex) {
        return trackIndex < unlockedStages;
    }
    
    /**
     * Get available tracks for current unlock level
     */
    public List<TrackMetadata> getUnlockedTracks() {
        List<TrackMetadata> unlockedTracks = new ArrayList<>();
        for (int i = 0; i < Math.min(availableTracks.size(), unlockedStages); i++) {
            unlockedTracks.add(availableTracks.get(i));
        }
        return unlockedTracks;
    }
    
    /**
     * Get unlocked car indices
     */
    public Set<Integer> getUnlockedCars() {
        return new HashSet<>(unlockedCars);
    }
    
    /**
     * Get all available tracks
     */
    public List<TrackMetadata> getAllTracks() {
        return new ArrayList<>(availableTracks);
    }
    
    /**
     * Select track by index
     */
    public boolean selectTrack(int trackIndex) {
        if (trackIndex < 0 || trackIndex >= availableTracks.size() || !isTrackUnlocked(trackIndex)) {
            return false;
        }
        
        TrackMetadata track = availableTracks.get(trackIndex);
        currentSettings.setTrackIndex(trackIndex);
        currentSettings.setTrackName(track.getTrackName());
        currentSettings.setTrackMetadata(track);
        
        // Apply track defaults
        if (track.getDefaultLaps() > 0) {
            currentSettings.setLaps(track.getDefaultLaps());
        }
        
        // Adjust AI count recommendation
        int recommendedAi = track.getRecommendedAiCount();
        if (recommendedAi != currentSettings.getAiCount()) {
            currentSettings.setAiCount(recommendedAi);
        }
        
        return true;
    }
    
    /**
     * Configure race for quick start (uses current settings)
     */
    public RaceConfiguration createRaceConfiguration() {
        return new RaceConfiguration(currentSettings);
    }
    
    /**
     * Create race configuration with specific settings
     */
    public RaceConfiguration createRaceConfiguration(RaceSettings settings) {
        return new RaceConfiguration(settings);
    }
    
    /**
     * Reset to default settings
     */
    public void resetToDefaults() {
        currentSettings = new RaceSettings();
        
        // Apply first unlocked track
        if (!availableTracks.isEmpty()) {
            selectTrack(0);
        }
    }
    
    /**
     * Get car name by index (integrates with CarConfig.CAR_MODELS)
     */
    public String getCarName(int carIndex) {
        if (carIndex < 0 || carIndex >= CarConfig.CAR_MODELS.length) {
            return "Unknown Car";
        }
        return CarConfig.CAR_MODELS[carIndex];
    }
    
    /**
     * Configuration result for starting a race
     */
    public static class RaceConfiguration {
        private final RaceSettings settings;
        private final List<Integer> playerCars;
        private final List<Integer> aiCars;
        
        public RaceConfiguration(RaceSettings settings) {
            this.settings = settings.copy();
            this.playerCars = new ArrayList<>(settings.getSelectedCarIndices());
            this.aiCars = settings.generateAiCarSelection();
        }
        
        public RaceSettings getSettings() { return settings; }
        public List<Integer> getPlayerCars() { return playerCars; }
        public List<Integer> getAiCars() { return aiCars; }
        public int getTotalCars() { return playerCars.size() + aiCars.size(); }
        
        /**
         * Convert to sc[] array format used by existing game code
         */
        public int[] toScArray() {
            int[] sc = new int[getTotalCars()];
            int index = 0;
            
            // Add player cars first
            for (int carIndex : playerCars) {
                sc[index++] = carIndex;
            }
            
            // Add AI cars
            for (int carIndex : aiCars) {
                sc[index++] = carIndex;
            }
            
            return sc;
        }
        
        /**
         * Check if this configuration is compatible with existing game state
         */
        public boolean isCompatible(int[] currentSc) {
            if (currentSc == null) return true;
            
            int[] newSc = toScArray();
            return newSc.length <= GameFacts.numberOfPlayers;
        }
    }
}
