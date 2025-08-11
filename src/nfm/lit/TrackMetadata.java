package nfm.lit;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses and represents metadata from NFM-Lit stage files.
 * Extracts track information like name, default laps, colors, and checkpoints.
 * 
 * @author GitHub Copilot
 */
public class TrackMetadata {
    
    // Stage file parsing patterns
    private static final Pattern NAME_PATTERN = Pattern.compile("name\\(([^)]+)\\)");
    private static final Pattern NLAPS_PATTERN = Pattern.compile("nlaps\\((\\d+)\\)");
    private static final Pattern SKY_PATTERN = Pattern.compile("sky\\((\\d+),(\\d+),(\\d+)\\)");
    private static final Pattern GROUND_PATTERN = Pattern.compile("ground\\((\\d+),(\\d+),(\\d+)\\)");
    private static final Pattern SOUNDTRACK_PATTERN = Pattern.compile("soundtrack\\(([^)]+)\\)");
    private static final Pattern CHK_PATTERN = Pattern.compile("chk\\(([^)]+)\\)");
    
    // Track metadata fields
    private String trackName;
    private int defaultLaps;
    private int[] skyColor;
    private int[] groundColor;
    private String soundtrack;
    private List<String> checkpoints;
    private File stageFile;
    private boolean isValid;
    
    /**
     * Create empty track metadata
     */
    public TrackMetadata() {
        this.trackName = "Unknown Track";
        this.defaultLaps = 3;
        this.skyColor = new int[]{200, 227, 255}; // Default sky blue
        this.groundColor = new int[]{195, 210, 210}; // Default ground
        this.soundtrack = "";
        this.checkpoints = new ArrayList<>();
        this.isValid = false;
    }
    
    /**
     * Create track metadata by parsing stage file
     */
    public TrackMetadata(File stageFile) {
        this();
        this.stageFile = stageFile;
        parseStageFile();
    }
    
    /**
     * Create track metadata from stage file path
     */
    public TrackMetadata(String stageFilePath) {
        this(new File(stageFilePath));
    }
    
    /**
     * Parse stage file to extract metadata
     */
    private void parseStageFile() {
        if (stageFile == null || !stageFile.exists()) {
            return;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(stageFile))) {
            String line;
            checkpoints = new ArrayList<>();
            
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                
                // Parse track name
                Matcher nameMatcher = NAME_PATTERN.matcher(line);
                if (nameMatcher.find()) {
                    trackName = nameMatcher.group(1);
                    continue;
                }
                
                // Parse default lap count
                Matcher nlapsMatcher = NLAPS_PATTERN.matcher(line);
                if (nlapsMatcher.find()) {
                    try {
                        defaultLaps = Integer.parseInt(nlapsMatcher.group(1));
                    } catch (NumberFormatException e) {
                        defaultLaps = 3; // Fallback
                    }
                    continue;
                }
                
                // Parse sky color
                Matcher skyMatcher = SKY_PATTERN.matcher(line);
                if (skyMatcher.find()) {
                    try {
                        skyColor = new int[]{
                            Integer.parseInt(skyMatcher.group(1)),
                            Integer.parseInt(skyMatcher.group(2)),
                            Integer.parseInt(skyMatcher.group(3))
                        };
                    } catch (NumberFormatException e) {
                        // Keep default sky color
                    }
                    continue;
                }
                
                // Parse ground color
                Matcher groundMatcher = GROUND_PATTERN.matcher(line);
                if (groundMatcher.find()) {
                    try {
                        groundColor = new int[]{
                            Integer.parseInt(groundMatcher.group(1)),
                            Integer.parseInt(groundMatcher.group(2)),
                            Integer.parseInt(groundMatcher.group(3))
                        };
                    } catch (NumberFormatException e) {
                        // Keep default ground color
                    }
                    continue;
                }
                
                // Parse soundtrack
                Matcher soundtrackMatcher = SOUNDTRACK_PATTERN.matcher(line);
                if (soundtrackMatcher.find()) {
                    soundtrack = soundtrackMatcher.group(1);
                    continue;
                }
                
                // Parse checkpoints
                Matcher chkMatcher = CHK_PATTERN.matcher(line);
                if (chkMatcher.find()) {
                    checkpoints.add(chkMatcher.group(1));
                    continue;
                }
            }
            
            isValid = true;
            
        } catch (IOException e) {
            System.err.println("Error parsing stage file: " + stageFile.getPath() + " - " + e.getMessage());
            isValid = false;
        }
    }
    
    /**
     * Get track display name for UI
     */
    public String getDisplayName() {
        if (trackName != null && !trackName.isEmpty()) {
            return trackName;
        }
        if (stageFile != null) {
            String fileName = stageFile.getName();
            return fileName.substring(0, fileName.lastIndexOf('.'));
        }
        return "Unknown Track";
    }
    
    /**
     * Check if track is suitable for given number of cars
     */
    public boolean supportsCars(int carCount) {
        // Most NFM tracks support up to 16 cars
        // Could be enhanced to check actual spawn points
        return carCount >= 1 && carCount <= GameFacts.numberOfCars;
    }
    
    /**
     * Get recommended AI count based on track size
     */
    public int getRecommendedAiCount() {
        // Analyze track complexity to suggest AI count
        int recommendedAi = 6; // Default
        
        if (checkpoints.size() > 10) {
            recommendedAi = 5; // Complex track, fewer AI
        } else if (checkpoints.size() < 3) {
            recommendedAi = 7; // Simple track, more AI
        }
        
        return Math.min(recommendedAi, GameFacts.numberOfPlayers - 1);
    }
    
    /**
     * Check if track has valid lap configuration
     */
    public boolean isValidLapCount(int laps) {
        if (laps < 1 || laps > 10) return false;
        
        // Some tracks might be too short/long for certain lap counts
        if (defaultLaps > 0) {
            // Allow reasonable variation from default
            return Math.abs(laps - defaultLaps) <= 5;
        }
        
        return true;
    }
    
    // Getters
    
    public String getTrackName() { return trackName; }
    public int getDefaultLaps() { return defaultLaps; }
    public int[] getSkyColor() { return skyColor != null ? skyColor.clone() : null; }
    public int[] getGroundColor() { return groundColor != null ? groundColor.clone() : null; }
    public String getSoundtrack() { return soundtrack; }
    public List<String> getCheckpoints() { return new ArrayList<>(checkpoints); }
    public int getCheckpointCount() { return checkpoints.size(); }
    public File getStageFile() { return stageFile; }
    public boolean isValid() { return isValid; }
    
    /**
     * Get track difficulty estimate based on checkpoint count and complexity
     */
    public RaceSettings.Difficulty getEstimatedDifficulty() {
        int complexity = checkpoints.size();
        
        if (complexity <= 3) {
            return RaceSettings.Difficulty.EASY;
        } else if (complexity <= 8) {
            return RaceSettings.Difficulty.NORMAL;
        } else if (complexity <= 15) {
            return RaceSettings.Difficulty.HARD;
        } else {
            return RaceSettings.Difficulty.NIGHTMARE;
        }
    }
    
    @Override
    public String toString() {
        return String.format("TrackMetadata[name='%s', laps=%d, checkpoints=%d, valid=%s]",
            trackName, defaultLaps, checkpoints.size(), isValid);
    }
    
    /**
     * Load track metadata from standard NFM stage directories
     */
    public static class StageLoader {
        private static final String STAGES_DIR = "data/stages/";
        private static final String NFM1_DIR = STAGES_DIR + "nfm1/";
        private static final String NFM2_DIR = STAGES_DIR + "nfm2/";
        private static final String CUSTOM_DIR = STAGES_DIR;
        
        /**
         * Load all available tracks
         */
        public static List<TrackMetadata> loadAllTracks() {
            List<TrackMetadata> tracks = new ArrayList<>();
            
            // Load NFM1 stages (1.txt to 11.txt)
            for (int i = 1; i <= 11; i++) {
                File stageFile = new File(NFM1_DIR + i + ".txt");
                if (stageFile.exists()) {
                    tracks.add(new TrackMetadata(stageFile));
                }
            }
            
            // Load NFM2 stages (1.txt to 17.txt)  
            for (int i = 1; i <= 17; i++) {
                File stageFile = new File(NFM2_DIR + i + ".txt");
                if (stageFile.exists()) {
                    tracks.add(new TrackMetadata(stageFile));
                }
            }
            
            // Load custom stages
            loadCustomTracks(tracks);
            
            return tracks;
        }
        
        private static void loadCustomTracks(List<TrackMetadata> tracks) {
            File customDir = new File(CUSTOM_DIR);
            if (customDir.exists() && customDir.isDirectory()) {
                File[] files = customDir.listFiles((dir, name) -> 
                    name.endsWith(".txt") && !name.equals("info.txt"));
                
                if (files != null) {
                    for (File file : files) {
                        // Skip NFM1/NFM2 subdirectories already processed
                        if (!file.getName().matches("\\d+\\.txt")) {
                            tracks.add(new TrackMetadata(file));
                        }
                    }
                }
            }
        }
        
        /**
         * Load specific track by index (for compatibility with existing game code)
         */
        public static TrackMetadata loadTrack(int trackIndex) {
            if (trackIndex < 0 || trackIndex >= GameFacts.numberOfStages) {
                return new TrackMetadata(); // Return invalid metadata
            }
            
            // Map track index to stage files
            if (trackIndex < 11) {
                // NFM1 stages 0-10 map to 1.txt-11.txt
                File stageFile = new File(NFM1_DIR + (trackIndex + 1) + ".txt");
                return new TrackMetadata(stageFile);
            } else {
                // NFM2 stages 11-16 map to 1.txt-6.txt (assuming 17 total stages)
                int nfm2Index = trackIndex - 10;
                File stageFile = new File(NFM2_DIR + nfm2Index + ".txt");
                return new TrackMetadata(stageFile);
            }
        }
    }
}
