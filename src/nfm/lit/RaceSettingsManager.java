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
        
        // Update available tracks (17 total tracks in NFM)
        initializeAllTracks();
        
        // Unlock cars based on stage progression
        updateCarUnlocks();
    }
    
    /**
     * Initialize all 17 NFM tracks
     */
    private void initializeAllTracks() {
        availableTracks.clear();
        
        // Load actual track data from stage files
        List<TrackMetadata> loadedTracks = TrackMetadata.StageLoader.loadAllTracks();
        
        if (!loadedTracks.isEmpty()) {
            availableTracks.addAll(loadedTracks);
        } else {
            // Fallback: create a default track if files can't be loaded
            TrackMetadata fallbackTrack = new TrackMetadata();
            availableTracks.add(fallbackTrack);
        }
    }
    
    /**
     * Update car unlocks based on NFM progression system:
     * - Cars 0-7 available from start (2000tornados through koolkat)
     * - Boss cars unlock after completing their second track:
     *   - Car 8 (drifter) after track 2
     *   - Car 9 (policecops) after track 4  
     *   - Car 10 (mustang) after track 6
     *   - Car 11 (king) after track 8
     *   - Car 12 (audir8) after track 10
     *   - Car 13 (masheen) after track 12
     *   - Car 14 (radicalone) after track 14
     *   - Car 15 (drmonster) after track 16
     * - Track 17 is playground with all cars
     */
    private void updateCarUnlocks() {
        unlockedCars.clear();
        
        // Cars 0-7 are available from the start
        for (int i = 0; i < 8; i++) {
            unlockedCars.add(i);
        }
        
        // Boss cars unlock after completing their second track
        // Car 8 (drifter) unlocks after track 2
        if (unlockedStages >= 2) unlockedCars.add(8);
        // Car 9 (policecops) unlocks after track 4
        if (unlockedStages >= 4) unlockedCars.add(9);
        // Car 10 (mustang) unlocks after track 6
        if (unlockedStages >= 6) unlockedCars.add(10);
        // Car 11 (king) unlocks after track 8
        if (unlockedStages >= 8) unlockedCars.add(11);
        // Car 12 (audir8) unlocks after track 10
        if (unlockedStages >= 10) unlockedCars.add(12);
        // Car 13 (masheen) unlocks after track 12
        if (unlockedStages >= 12) unlockedCars.add(13);
        // Car 14 (radicalone) unlocks after track 14
        if (unlockedStages >= 14) unlockedCars.add(14);
        // Car 15 (drmonster) unlocks after track 16
        if (unlockedStages >= 16) unlockedCars.add(15);
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
