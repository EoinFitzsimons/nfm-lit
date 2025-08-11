package nfm.lit.racesettings;

/**
 * Represents metadata for a race track including constraints and defaults.
 * This ensures every track exposes proper limits for race configuration.
 */
public class TrackMetadata {
    private final String trackName;
    private final int defaultLaps;
    private final int minLaps; 
    private final int maxLaps;
    private final int maxConcurrentCars;
    
    // Fallback defaults when metadata is missing
    public static final int FALLBACK_DEFAULT_LAPS = 3;
    public static final int FALLBACK_MIN_LAPS = 1;
    public static final int FALLBACK_MAX_LAPS = 20;
    public static final int FALLBACK_MAX_CONCURRENT_CARS = 8;
    
    public TrackMetadata(String trackName, int defaultLaps, int minLaps, int maxLaps, int maxConcurrentCars) {
        this.trackName = trackName;
        this.defaultLaps = Math.max(1, defaultLaps);
        this.minLaps = Math.max(1, minLaps);
        this.maxLaps = Math.max(minLaps, maxLaps);
        this.maxConcurrentCars = Math.max(2, maxConcurrentCars); // Need at least player + 1 opponent
    }
    
    /**
     * Creates TrackMetadata with fallback values when track metadata is missing.
     */
    public static TrackMetadata createFallback(String trackName) {
        return new TrackMetadata(
            trackName,
            FALLBACK_DEFAULT_LAPS,
            FALLBACK_MIN_LAPS, 
            FALLBACK_MAX_LAPS,
            FALLBACK_MAX_CONCURRENT_CARS
        );
    }
    
    /**
     * Validates that the given lap count is within track limits.
     */
    public boolean isValidLapCount(int laps) {
        return laps >= minLaps && laps <= maxLaps;
    }
    
    /**
     * Validates that the given car count is within track limits.
     */
    public boolean isValidCarCount(int cars) {
        return cars >= 1 && cars <= maxConcurrentCars;
    }
    
    /**
     * Clamps the lap count to valid range.
     */
    public int clampLaps(int laps) {
        return Math.max(minLaps, Math.min(maxLaps, laps));
    }
    
    /**
     * Clamps the car count to valid range.
     */
    public int clampCarCount(int cars) {
        return Math.max(1, Math.min(maxConcurrentCars, cars));
    }
    
    // Getters
    public String getTrackName() { return trackName; }
    public int getDefaultLaps() { return defaultLaps; }
    public int getMinLaps() { return minLaps; }
    public int getMaxLaps() { return maxLaps; }
    public int getMaxConcurrentCars() { return maxConcurrentCars; }
    
    @Override
    public String toString() {
        return String.format("TrackMetadata{name='%s', laps=%d-%d (default=%d), maxCars=%d}",
            trackName, minLaps, maxLaps, defaultLaps, maxConcurrentCars);
    }
}
