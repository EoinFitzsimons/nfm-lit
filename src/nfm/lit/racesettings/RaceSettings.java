package nfm.lit.racesettings;

/**
 * Immutable value object representing all race configuration parameters.
 * This is consumed by the RaceController to set up races.
 */
public class RaceSettings {
    // Basic race parameters
    private final int aiCount;
    private final int lapCount;
    private final AIDifficulty aiDifficulty;
    
    // Human players
    private final int humanPlayers;
    
    // Car pool management
    private final boolean generateVariants;
    private final int variantsToGenerate;
    private final boolean allowDuplicates;
    
    // Performance settings
    private final boolean performanceMode;
    
    // Default values
    public static final int DEFAULT_AI_COUNT = 6;
    public static final AIDifficulty DEFAULT_AI_DIFFICULTY = AIDifficulty.NORMAL;
    public static final int DEFAULT_HUMAN_PLAYERS = 1;
    public static final boolean DEFAULT_GENERATE_VARIANTS = false;
    public static final boolean DEFAULT_ALLOW_DUPLICATES = false;
    public static final boolean DEFAULT_PERFORMANCE_MODE = false;
    
    private RaceSettings(Builder builder) {
        this.aiCount = Math.max(0, builder.aiCount);
        this.lapCount = Math.max(1, builder.lapCount);
        this.aiDifficulty = builder.aiDifficulty != null ? builder.aiDifficulty : DEFAULT_AI_DIFFICULTY;
        this.humanPlayers = Math.max(1, builder.humanPlayers);
        this.generateVariants = builder.generateVariants;
        this.variantsToGenerate = Math.max(0, builder.variantsToGenerate);
        this.allowDuplicates = builder.allowDuplicates;
        this.performanceMode = builder.performanceMode;
    }
    
    /**
     * Creates default race settings with track's default lap count.
     */
    public static RaceSettings createDefault(int trackDefaultLaps) {
        return new Builder()
            .aiCount(DEFAULT_AI_COUNT)
            .lapCount(trackDefaultLaps)
            .aiDifficulty(DEFAULT_AI_DIFFICULTY)
            .humanPlayers(DEFAULT_HUMAN_PLAYERS)
            .build();
    }
    
    /**
     * Gets the total number of cars in the race (human + AI).
     */
    public int getTotalCars() {
        return humanPlayers + aiCount;
    }
    
    /**
     * Validates this configuration against track metadata and car pool constraints.
     */
    public ValidationResult validate(TrackMetadata trackMetadata, CarPoolManager carPool) {
        ValidationResult.Builder result = new ValidationResult.Builder();
        
        // Check lap count
        if (!trackMetadata.isValidLapCount(lapCount)) {
            result.addError(String.format("Lap count %d is outside track limits (%d-%d)",
                lapCount, trackMetadata.getMinLaps(), trackMetadata.getMaxLaps()));
        }
        
        // Check total cars vs track capacity
        int totalCars = getTotalCars();
        if (totalCars > trackMetadata.getMaxConcurrentCars()) {
            result.addError(String.format("Total cars %d exceeds track capacity %d",
                totalCars, trackMetadata.getMaxConcurrentCars()));
        }
        
        // Check car pool capacity
        int maxAllowed = Math.min(trackMetadata.getMaxConcurrentCars(), carPool.getTotalInstancesAllowed());
        if (totalCars > maxAllowed) {
            String suggestion;
            if (!generateVariants && !allowDuplicates) {
                suggestion = "Enable 'Generate Variants' or 'Allow Duplicates'";
            } else {
                suggestion = String.format("Reduce to %d total cars", maxAllowed);
            }
            result.addWarning(String.format("Requested %d cars but only %d available. %s",
                totalCars, maxAllowed, suggestion));
        }
        
        return result.build();
    }
    
    // Getters
    public int getAiCount() { return aiCount; }
    public int getLapCount() { return lapCount; }
    public AIDifficulty getAiDifficulty() { return aiDifficulty; }
    public int getHumanPlayers() { return humanPlayers; }
    public boolean isGenerateVariants() { return generateVariants; }
    public int getVariantsToGenerate() { return variantsToGenerate; }
    public boolean isAllowDuplicates() { return allowDuplicates; }
    public boolean isPerformanceMode() { return performanceMode; }
    
    /**
     * Builder pattern for creating RaceSettings instances.
     */
    public static class Builder {
        private int aiCount = DEFAULT_AI_COUNT;
        private int lapCount = TrackMetadata.FALLBACK_DEFAULT_LAPS;
        private AIDifficulty aiDifficulty = DEFAULT_AI_DIFFICULTY;
        private int humanPlayers = DEFAULT_HUMAN_PLAYERS;
        private boolean generateVariants = DEFAULT_GENERATE_VARIANTS;
        private int variantsToGenerate = 0;
        private boolean allowDuplicates = DEFAULT_ALLOW_DUPLICATES;
        private boolean performanceMode = DEFAULT_PERFORMANCE_MODE;
        
        public Builder aiCount(int aiCount) {
            this.aiCount = aiCount;
            return this;
        }
        
        public Builder lapCount(int lapCount) {
            this.lapCount = lapCount;
            return this;
        }
        
        public Builder aiDifficulty(AIDifficulty aiDifficulty) {
            this.aiDifficulty = aiDifficulty;
            return this;
        }
        
        public Builder humanPlayers(int humanPlayers) {
            this.humanPlayers = humanPlayers;
            return this;
        }
        
        public Builder generateVariants(boolean generateVariants) {
            this.generateVariants = generateVariants;
            return this;
        }
        
        public Builder variantsToGenerate(int variantsToGenerate) {
            this.variantsToGenerate = variantsToGenerate;
            return this;
        }
        
        public Builder allowDuplicates(boolean allowDuplicates) {
            this.allowDuplicates = allowDuplicates;
            return this;
        }
        
        public Builder performanceMode(boolean performanceMode) {
            this.performanceMode = performanceMode;
            return this;
        }
        
        public RaceSettings build() {
            return new RaceSettings(this);
        }
    }
    
    @Override
    public String toString() {
        return String.format("RaceSettings{AI=%d, laps=%d, difficulty=%s, humans=%d, variants=%s, duplicates=%s}",
            aiCount, lapCount, aiDifficulty, humanPlayers, generateVariants, allowDuplicates);
    }
}
