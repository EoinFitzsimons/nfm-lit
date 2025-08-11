package nfm.lit.racesettings;

/**
 * Result of a performance stress test with high AI counts.
 * Used for developer testing and performance analysis.
 */
public class PerformanceTestResult {
    private final int aiCount;
    private final long executionTimeMs;
    private final long memoryUsageBytes;
    private final int carsAllocated;
    private final int allocationShortfall;
    
    public PerformanceTestResult(int aiCount, long executionTimeMs, long memoryUsageBytes,
                                int carsAllocated, int allocationShortfall) {
        this.aiCount = aiCount;
        this.executionTimeMs = executionTimeMs;
        this.memoryUsageBytes = memoryUsageBytes;
        this.carsAllocated = carsAllocated;
        this.allocationShortfall = allocationShortfall;
    }
    
    public int getAiCount() { return aiCount; }
    public long getExecutionTimeMs() { return executionTimeMs; }
    public long getMemoryUsageBytes() { return memoryUsageBytes; }
    public int getCarsAllocated() { return carsAllocated; }
    public int getAllocationShortfall() { return allocationShortfall; }
    
    /**
     * Returns true if the test completed successfully within reasonable limits.
     */
    public boolean isSuccessful() {
        return allocationShortfall == 0 && 
               executionTimeMs < 5000 && // 5 second limit
               memoryUsageBytes < 500 * 1024 * 1024; // 500MB limit
    }
    
    /**
     * Gets a human-readable performance summary.
     */
    public String getSummary() {
        return String.format("AI Count: %d, Time: %dms, Memory: %.2fMB, Cars: %d/%d, Success: %s",
            aiCount, executionTimeMs, memoryUsageBytes / (1024.0 * 1024.0),
            carsAllocated, aiCount, isSuccessful() ? "Yes" : "No");
    }
    
    @Override
    public String toString() {
        return getSummary();
    }
}
