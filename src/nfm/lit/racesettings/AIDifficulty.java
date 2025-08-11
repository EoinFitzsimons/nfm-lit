package nfm.lit.racesettings;

/**
 * Enumeration of AI difficulty levels affecting speed, aggression, and recovery.
 */
public enum AIDifficulty {
    EASY("Easy", 0.7f, 0.5f, 1.2f),
    NORMAL("Normal", 1.0f, 0.75f, 1.0f), 
    HARD("Hard", 1.3f, 1.0f, 0.8f),
    INSANE("Insane", 1.6f, 1.5f, 0.6f);
    
    private final String displayName;
    private final float speedMultiplier;    // AI speed multiplier 
    private final float aggressionFactor;   // How aggressive AI is (higher = more risky driving)
    private final float recoveryMultiplier; // How fast AI recovers from crashes (higher = faster)
    
    AIDifficulty(String displayName, float speedMultiplier, float aggressionFactor, float recoveryMultiplier) {
        this.displayName = displayName;
        this.speedMultiplier = speedMultiplier;
        this.aggressionFactor = aggressionFactor;
        this.recoveryMultiplier = recoveryMultiplier;
    }
    
    public String getDisplayName() { return displayName; }
    public float getSpeedMultiplier() { return speedMultiplier; }
    public float getAggressionFactor() { return aggressionFactor; }
    public float getRecoveryMultiplier() { return recoveryMultiplier; }
    
    /**
     * Gets the next difficulty level (cycles back to EASY after INSANE).
     */
    public AIDifficulty getNext() {
        AIDifficulty[] values = values();
        return values[(this.ordinal() + 1) % values.length];
    }
    
    /**
     * Gets the previous difficulty level (cycles back to INSANE before EASY).
     */
    public AIDifficulty getPrevious() {
        AIDifficulty[] values = values();
        return values[(this.ordinal() + values.length - 1) % values.length];
    }
    
    @Override
    public String toString() {
        return displayName;
    }
}
