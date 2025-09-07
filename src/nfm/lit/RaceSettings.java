package nfm.lit;

import java.util.*;

/**
 * Comprehensive race configuration system for NFM-Lit.
 * Handles race parameters while respecting track metadata and game engine limits.
 * 
 * @author GitHub Copilot
 */
public class RaceSettings {
    
    // Default configuration constants from GameFacts
    public static final int MAX_CARS = GameFacts.numberOfCars; // 16
    public static final int MAX_PLAYERS = GameFacts.numberOfPlayers; // 7
    public static final int MAX_AI_COUNT = MAX_PLAYERS - 1; // 6 (assuming 1 human player)
    public static final int MIN_LAPS = 1;
    public static final int MAX_LAPS = 10;
    
    // Difficulty levels
    public enum Difficulty {
        EASY("Easy", 0.7f),
        NORMAL("Normal", 1.0f),
        HARD("Hard", 1.3f),
        NIGHTMARE("Nightmare", 1.6f);
        
        private final String displayName;
        private final float aiSpeedMultiplier;
        
        Difficulty(String displayName, float aiSpeedMultiplier) {
            this.displayName = displayName;
            this.aiSpeedMultiplier = aiSpeedMultiplier;
        }
        
        public String getDisplayName() { return displayName; }
        public float getAiSpeedMultiplier() { return aiSpeedMultiplier; }
    }
    
    // Race configuration fields
    private String trackName;
    private int trackIndex;
    private int laps;
    private int aiCount;
    private Difficulty difficulty;
    private Set<Integer> selectedCarIndices;
    private boolean randomizeAiCars;
    private boolean enableCollisions;
    private boolean enablePowerups;
    
    // Track metadata
    private TrackMetadata trackMetadata;
    
    /**
     * Create race settings with default values
     */
    public RaceSettings() {
        // Default to first NFM1 stage
        this.trackIndex = 0;
        this.trackName = "The Introductory Stage";
        this.laps = 4; // Default from stage file
        this.aiCount = 6; // Default AI count (7 total - 1 human)
        this.difficulty = Difficulty.NORMAL;
        this.selectedCarIndices = new HashSet<>();
        this.randomizeAiCars = true;
        this.enableCollisions = true;
        this.enablePowerups = true;
        
        // Add default human player car (first unlocked car)
        this.selectedCarIndices.add(0);
    }
    
    /**
     * Create race settings for specific track
     */
    public RaceSettings(int trackIndex, String trackName) {
        this();
        this.trackIndex = trackIndex;
        this.trackName = trackName;
    }
    
    // Getters and setters with validation
    
    public String getTrackName() { return trackName; }
    
    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }
    
    public int getTrackIndex() { return trackIndex; }
    
    public void setTrackIndex(int trackIndex) {
        if (trackIndex < 0 || trackIndex >= GameFacts.numberOfStages) {
            throw new IllegalArgumentException("Track index must be between 0 and " + (GameFacts.numberOfStages - 1));
        }
        this.trackIndex = trackIndex;
    }
    
    public int getLaps() { return laps; }
    
    public void setLaps(int laps) {
        if (laps < MIN_LAPS || laps > MAX_LAPS) {
            throw new IllegalArgumentException("Laps must be between " + MIN_LAPS + " and " + MAX_LAPS);
        }
        this.laps = laps;
    }
    
    public int getAiCount() { return aiCount; }
    
    public void setAiCount(int aiCount) {
        if (aiCount < 0 || aiCount > MAX_AI_COUNT) {
            throw new IllegalArgumentException("AI count must be between 0 and " + MAX_AI_COUNT);
        }
        this.aiCount = aiCount;
    }
    
    public int getTotalCars() {
        return 1 + aiCount; // Human player + AI cars
    }
    
    public Difficulty getDifficulty() { return difficulty; }
    
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
    
    public Set<Integer> getSelectedCarIndices() { 
        return new HashSet<>(selectedCarIndices); 
    }
    
    public void addSelectedCar(int carIndex) {
        if (carIndex < 0 || carIndex >= MAX_CARS) {
            throw new IllegalArgumentException("Car index must be between 0 and " + (MAX_CARS - 1));
        }
        selectedCarIndices.add(carIndex);
    }
    
    public void removeSelectedCar(int carIndex) {
        selectedCarIndices.remove(carIndex);
    }
    
    public void clearSelectedCars() {
        selectedCarIndices.clear();
    }
    
    public boolean isRandomizeAiCars() { return randomizeAiCars; }
    
    public void setRandomizeAiCars(boolean randomizeAiCars) {
        this.randomizeAiCars = randomizeAiCars;
    }
    
    public boolean isEnableCollisions() { return enableCollisions; }
    
    public void setEnableCollisions(boolean enableCollisions) {
        this.enableCollisions = enableCollisions;
    }
    
    public boolean isEnablePowerups() { return enablePowerups; }
    
    public void setEnablePowerups(boolean enablePowerups) {
        this.enablePowerups = enablePowerups;
    }
    
    public TrackMetadata getTrackMetadata() { return trackMetadata; }
    
    public void setTrackMetadata(TrackMetadata trackMetadata) {
        this.trackMetadata = trackMetadata;
    }
    
    /**
     * Validate race settings and apply track metadata constraints
     */
    public boolean validate() {
        try {
            // Basic validation
            if (trackIndex < 0 || trackIndex >= GameFacts.numberOfStages) return false;
            if (laps < MIN_LAPS || laps > MAX_LAPS) return false;
            if (aiCount < 0 || aiCount > MAX_AI_COUNT) return false;
            if (getTotalCars() > MAX_CARS) return false;
            
            // Must have at least one car selected for human player
            if (selectedCarIndices.isEmpty()) return false;
            
            // All selected car indices must be valid
            for (int carIndex : selectedCarIndices) {
                if (carIndex < 0 || carIndex >= MAX_CARS) return false;
            }
            
            // Apply track metadata constraints if available
            if (trackMetadata != null) {
                // Use track's default lap count if not customized
                if (trackMetadata.getDefaultLaps() > 0) {
                    // Allow override but warn if very different
                    int defaultLaps = trackMetadata.getDefaultLaps();
                    if (Math.abs(laps - defaultLaps) > 3) {
                        // This is just a warning - still valid
                        System.out.println("Warning: Lap count " + laps + " differs significantly from track default " + defaultLaps);
                    }
                }
            }
            
            return true;
            
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Create a copy of these race settings
     */
    public RaceSettings copy() {
        RaceSettings copy = new RaceSettings();
        copy.trackIndex = this.trackIndex;
        copy.trackName = this.trackName;
        copy.laps = this.laps;
        copy.aiCount = this.aiCount;
        copy.difficulty = this.difficulty;
        copy.selectedCarIndices = new HashSet<>(this.selectedCarIndices);
        copy.randomizeAiCars = this.randomizeAiCars;
        copy.enableCollisions = this.enableCollisions;
        copy.enablePowerups = this.enablePowerups;
        copy.trackMetadata = this.trackMetadata; // Shared reference is OK
        return copy;
    }
    
    /**
     * Generate AI car selection based on settings
     */
    public List<Integer> generateAiCarSelection() {
        List<Integer> aiCars = new ArrayList<>();
        
        if (randomizeAiCars) {
            // Random selection from all available cars
            List<Integer> availableCars = new ArrayList<>();
            for (int i = 0; i < MAX_CARS; i++) {
                if (!selectedCarIndices.contains(i)) { // Don't duplicate human player car
                    availableCars.add(i);
                }
            }
            
            Collections.shuffle(availableCars);
            for (int i = 0; i < Math.min(aiCount, availableCars.size()); i++) {
                aiCars.add(availableCars.get(i));
            }
        } else {
            // Use predefined selection or sequential selection
            for (int i = 0; i < aiCount && aiCars.size() < aiCount; i++) {
                int carIndex = (i + 1) % MAX_CARS; // Start from car 1, avoid car 0 if it's human
                if (!selectedCarIndices.contains(carIndex)) {
                    aiCars.add(carIndex);
                }
            }
        }
        
        return aiCars;
    }
    
    @Override
    public String toString() {
        return String.format("RaceSettings[track='%s', laps=%d, AI=%d, difficulty=%s, cars=%d]",
            trackName, laps, aiCount, difficulty.getDisplayName(), selectedCarIndices.size());
    }
}
