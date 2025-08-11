package nfm.lit.racesettings;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Loads track metadata from JSON or properties files.
 * Falls back to sensible defaults if metadata is missing.
 */
public class TrackMetadataLoader {
    private final Map<String, TrackMetadata> metadataCache = new HashMap<>();
    private final String metadataDirectory;
    
    // Property keys
    private static final String PROP_DEFAULT_LAPS = "defaultLaps";
    private static final String PROP_MIN_LAPS = "minLaps";
    private static final String PROP_MAX_LAPS = "maxLaps";
    private static final String PROP_MAX_CARS = "maxConcurrentCars";
    
    public TrackMetadataLoader(String metadataDirectory) {
        this.metadataDirectory = metadataDirectory;
    }
    
    /**
     * Loads metadata for the specified track.
     * Returns fallback metadata if file is missing or invalid.
     */
    public TrackMetadata loadMetadata(String trackName) {
        if (metadataCache.containsKey(trackName)) {
            return metadataCache.get(trackName);
        }
        
        TrackMetadata metadata = loadFromFile(trackName);
        metadataCache.put(trackName, metadata);
        return metadata;
    }
    
    private TrackMetadata loadFromFile(String trackName) {
        // Try loading from properties file first
        TrackMetadata metadata = loadFromPropertiesFile(trackName);
        if (metadata != null) {
            return metadata;
        }
        
        // Try loading from JSON file
        metadata = loadFromJsonFile(trackName);
        if (metadata != null) {
            return metadata;
        }
        
        // Fall back to defaults
        return TrackMetadata.createFallback(trackName);
    }
    
    private TrackMetadata loadFromPropertiesFile(String trackName) {
        Path propertiesFile = Paths.get(metadataDirectory, trackName + ".properties");
        
        if (!Files.exists(propertiesFile)) {
            return null;
        }
        
        try (InputStream input = Files.newInputStream(propertiesFile)) {
            Properties props = new Properties();
            props.load(input);
            
            int defaultLaps = Integer.parseInt(props.getProperty(PROP_DEFAULT_LAPS, "3"));
            int minLaps = Integer.parseInt(props.getProperty(PROP_MIN_LAPS, "1"));
            int maxLaps = Integer.parseInt(props.getProperty(PROP_MAX_LAPS, "20"));
            int maxCars = Integer.parseInt(props.getProperty(PROP_MAX_CARS, "8"));
            
            return new TrackMetadata(trackName, defaultLaps, minLaps, maxLaps, maxCars);
            
        } catch (IOException | NumberFormatException e) {
            // Log error in a real implementation
            return null;
        }
    }
    
    private TrackMetadata loadFromJsonFile(String trackName) {
        Path jsonFile = Paths.get(metadataDirectory, trackName + ".json");
        
        if (!Files.exists(jsonFile)) {
            return null;
        }
        
        try {
            String content = Files.readString(jsonFile);
            return parseJsonMetadata(trackName, content);
        } catch (IOException e) {
            // Log error in a real implementation
            return null;
        }
    }
    
    private TrackMetadata parseJsonMetadata(String trackName, String jsonContent) {
        // Simple JSON parsing for basic metadata
        // In a full implementation, you'd use a proper JSON library
        try {
            int defaultLaps = extractJsonInt(jsonContent, PROP_DEFAULT_LAPS, 3);
            int minLaps = extractJsonInt(jsonContent, PROP_MIN_LAPS, 1);
            int maxLaps = extractJsonInt(jsonContent, PROP_MAX_LAPS, 20);
            int maxCars = extractJsonInt(jsonContent, PROP_MAX_CARS, 8);
            
            return new TrackMetadata(trackName, defaultLaps, minLaps, maxLaps, maxCars);
        } catch (Exception e) {
            return null;
        }
    }
    
    private int extractJsonInt(String json, String key, int defaultValue) {
        String pattern = "\"" + key + "\"\\s*:\\s*(\\d+)";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher m = p.matcher(json);
        if (m.find()) {
            return Integer.parseInt(m.group(1));
        }
        return defaultValue;
    }
    
    /**
     * Saves metadata to a properties file.
     * This can be used by level editors or admin tools.
     */
    public void saveMetadata(TrackMetadata metadata) throws IOException {
        Path propertiesFile = Paths.get(metadataDirectory, metadata.getTrackName() + ".properties");
        
        Properties props = new Properties();
        props.setProperty(PROP_DEFAULT_LAPS, String.valueOf(metadata.getDefaultLaps()));
        props.setProperty(PROP_MIN_LAPS, String.valueOf(metadata.getMinLaps()));
        props.setProperty(PROP_MAX_LAPS, String.valueOf(metadata.getMaxLaps()));
        props.setProperty(PROP_MAX_CARS, String.valueOf(metadata.getMaxConcurrentCars()));
        
        try (OutputStream output = Files.newOutputStream(propertiesFile)) {
            props.store(output, "Track metadata for " + metadata.getTrackName());
        }
        
        // Update cache
        metadataCache.put(metadata.getTrackName(), metadata);
    }
    
    /**
     * Clears the metadata cache.
     */
    public void clearCache() {
        metadataCache.clear();
    }
    
    /**
     * Gets all cached metadata.
     */
    public Map<String, TrackMetadata> getCachedMetadata() {
        return new HashMap<>(metadataCache);
    }
}
