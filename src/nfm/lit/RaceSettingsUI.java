package nfm.lit;

import java.awt.*;

/**
 * Enhanced car selection UI that integrates race settings with the existing NFM-Lit car selection.
 * Extends the existing carselect() functionality with race configuration options.
 * 
 * @author GitHub Copilot
 */
public class RaceSettingsUI {
    
    // UI Layout constants
    private static final int SETTINGS_PANEL_X = 50;
    private static final int SETTINGS_PANEL_Y = 300;  // Moved up for stage select
    private static final int SETTINGS_PANEL_WIDTH = 320;
    private static final int SETTINGS_PANEL_HEIGHT = 220;
    
    private static final int BUTTON_WIDTH = 80;
    private static final int BUTTON_HEIGHT = 25;
    private static final int BUTTON_SPACING = 10;
    
    // UI State
    private RaceSettingsManager settingsManager;
    private boolean showRaceSettings;
    private int selectedTrackIndex;
    private int selectedLaps;
    private int selectedAiCount;
    private RaceSettings.Difficulty selectedDifficulty;
    
    // UI Elements positioning
    private Rectangle trackLeftButton;
    private Rectangle trackRightButton;
    private Rectangle lapsLeftButton;
    private Rectangle lapsRightButton;
    private Rectangle aiLeftButton;
    private Rectangle aiRightButton;
    private Rectangle difficultyButton;
    private Rectangle startRaceButton;
    private Rectangle toggleSettingsButton;
    
    public RaceSettingsUI() {
        this.settingsManager = RaceSettingsManager.getInstance();
        this.showRaceSettings = false;
        
        // Initialize from current settings
        RaceSettings current = settingsManager.getCurrentSettings();
        this.selectedTrackIndex = current.getTrackIndex();
        this.selectedLaps = current.getLaps();
        this.selectedAiCount = current.getAiCount();
        this.selectedDifficulty = current.getDifficulty();
        
        initializeUIElements();
    }
    
    /**
     * Initialize UI element positions
     */
    private void initializeUIElements() {
        int baseX = SETTINGS_PANEL_X + 10;
        int baseY = SETTINGS_PANEL_Y + 30;
        
        // Track selection buttons
        trackLeftButton = new Rectangle(baseX, baseY, 20, 20);
        trackRightButton = new Rectangle(baseX + 250, baseY, 20, 20);
        
        // Laps buttons
        lapsLeftButton = new Rectangle(baseX, baseY + 30, 20, 20);
        lapsRightButton = new Rectangle(baseX + 100, baseY + 30, 20, 20);
        
        // AI count buttons
        aiLeftButton = new Rectangle(baseX, baseY + 60, 20, 20);
        aiRightButton = new Rectangle(baseX + 100, baseY + 60, 20, 20);
        
        // Difficulty button
        difficultyButton = new Rectangle(baseX, baseY + 90, 120, 25);
        
        // Start race button
        startRaceButton = new Rectangle(baseX + 150, baseY + 90, 100, 35);
        
        // Toggle settings button (positioned in car selection area)
        toggleSettingsButton = new Rectangle(550, 50, 120, 30);
    }
    
    /**
     * Render the race settings UI overlay
     */
    public void render(Graphics rd, XtGraphics xtGraphics) {
        // Draw toggle button
        drawButton(rd, toggleSettingsButton, showRaceSettings ? "Hide Settings" : "Race Settings", 
                   showRaceSettings ? Color.ORANGE : Color.LIGHT_GRAY);
        
        if (!showRaceSettings) {
            return;
        }
        
        // Draw settings panel background
        rd.setColor(new Color(0, 0, 0, 200)); // More opaque black background
        rd.fillRect(SETTINGS_PANEL_X, SETTINGS_PANEL_Y, SETTINGS_PANEL_WIDTH, SETTINGS_PANEL_HEIGHT);
        
        rd.setColor(Color.WHITE);
        rd.drawRect(SETTINGS_PANEL_X, SETTINGS_PANEL_Y, SETTINGS_PANEL_WIDTH, SETTINGS_PANEL_HEIGHT);
        
        // Title
        rd.setColor(Color.YELLOW);
        rd.setFont(new Font("Arial", Font.BOLD, 14));
        rd.drawString("Race Settings", SETTINGS_PANEL_X + 10, SETTINGS_PANEL_Y + 20);
        
        rd.setFont(new Font("Arial", Font.PLAIN, 12));
        rd.setColor(Color.WHITE);
        
        int textX = SETTINGS_PANEL_X + 10;
        int textY = SETTINGS_PANEL_Y + 50;
        
        // Track selection
        TrackMetadata selectedTrack = getSelectedTrack();
        String trackName = selectedTrack != null ? selectedTrack.getDisplayName() : "Unknown Track";
        if (trackName.length() > 25) {
            trackName = trackName.substring(0, 22) + "...";
        }
        
        drawButton(rd, trackLeftButton, "<", Color.CYAN);
        drawButton(rd, trackRightButton, ">", Color.CYAN);
        rd.drawString("Track: " + trackName, textX + 30, textY);
        
        // Laps
        textY += 30;
        drawButton(rd, lapsLeftButton, "<", Color.GREEN);
        drawButton(rd, lapsRightButton, ">", Color.GREEN);
        rd.drawString("Laps: " + selectedLaps, textX + 30, textY);
        
        // AI Count
        textY += 30;
        drawButton(rd, aiLeftButton, "<", Color.MAGENTA);
        drawButton(rd, aiRightButton, ">", Color.MAGENTA);
        rd.drawString("AI Cars: " + selectedAiCount, textX + 30, textY);
        
        // Difficulty
        textY += 30;
        drawButton(rd, difficultyButton, selectedDifficulty.getDisplayName(), Color.RED);
        
        // Start Race button
        drawButton(rd, startRaceButton, "START RACE", Color.GREEN);
        
        // Show track info
        if (selectedTrack != null && selectedTrack.isValid()) {
            rd.setColor(Color.LIGHT_GRAY);
            rd.setFont(new Font("Arial", Font.PLAIN, 10));
            textY = SETTINGS_PANEL_Y + SETTINGS_PANEL_HEIGHT + 10;
            rd.drawString("Default Laps: " + selectedTrack.getDefaultLaps(), textX, textY);
            rd.drawString("Checkpoints: " + selectedTrack.getCheckpointCount(), textX, textY + 12);
        }
    }
    
    /**
     * Draw a button with text
     */
    private void drawButton(Graphics rd, Rectangle button, String text, Color color) {
        // Draw button background with transparency
        Color bgColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), 180);
        rd.setColor(bgColor);
        rd.fillRect(button.x, button.y, button.width, button.height);
        
        // Draw button border
        rd.setColor(Color.WHITE);
        rd.drawRect(button.x, button.y, button.width, button.height);
        
        // Draw text with better visibility
        rd.setColor(Color.WHITE);
        rd.setFont(new Font("Arial", Font.BOLD, 12));
        FontMetrics fm = rd.getFontMetrics();
        int textX = button.x + (button.width - fm.stringWidth(text)) / 2;
        int textY = button.y + (button.height + fm.getAscent()) / 2 - 2;
        
        // Draw text shadow for better readability
        rd.setColor(Color.BLACK);
        rd.drawString(text, textX + 1, textY + 1);
        rd.setColor(Color.WHITE);
        rd.drawString(text, textX, textY);
    }
    
    /**
     * Handle mouse clicks on race settings UI
     */
    public boolean handleMouseClick(int mouseX, int mouseY) {
        // Toggle settings panel
        if (toggleSettingsButton.contains(mouseX, mouseY)) {
            showRaceSettings = !showRaceSettings;
            return true;
        }
        
        if (!showRaceSettings) {
            return false;
        }
        
        // Track selection
        if (trackLeftButton.contains(mouseX, mouseY)) {
            changeTrack(-1);
            return true;
        }
        if (trackRightButton.contains(mouseX, mouseY)) {
            changeTrack(1);
            return true;
        }
        
        // Laps
        if (lapsLeftButton.contains(mouseX, mouseY)) {
            changeLaps(-1);
            return true;
        }
        if (lapsRightButton.contains(mouseX, mouseY)) {
            changeLaps(1);
            return true;
        }
        
        // AI Count
        if (aiLeftButton.contains(mouseX, mouseY)) {
            changeAiCount(-1);
            return true;
        }
        if (aiRightButton.contains(mouseX, mouseY)) {
            changeAiCount(1);
            return true;
        }
        
        // Difficulty
        if (difficultyButton.contains(mouseX, mouseY)) {
            cycleDifficulty();
            return true;
        }
        
        // Start Race
        if (startRaceButton.contains(mouseX, mouseY)) {
            return startRace();
        }
        
        return false;
    }
    
    /**
     * Change selected track
     */
    private void changeTrack(int direction) {
        var unlockedTracks = settingsManager.getUnlockedTracks();
        if (unlockedTracks.isEmpty()) return;
        
        selectedTrackIndex += direction;
        if (selectedTrackIndex < 0) {
            selectedTrackIndex = unlockedTracks.size() - 1;
        } else if (selectedTrackIndex >= unlockedTracks.size()) {
            selectedTrackIndex = 0;
        }
        
        // Update track metadata
        settingsManager.selectTrack(selectedTrackIndex);
        TrackMetadata track = getSelectedTrack();
        if (track != null && track.getDefaultLaps() > 0) {
            selectedLaps = track.getDefaultLaps();
        }
    }
    
    /**
     * Change lap count
     */
    private void changeLaps(int direction) {
        selectedLaps += direction;
        if (selectedLaps < RaceSettings.MIN_LAPS) {
            selectedLaps = RaceSettings.MIN_LAPS;
        } else if (selectedLaps > RaceSettings.MAX_LAPS) {
            selectedLaps = RaceSettings.MAX_LAPS;
        }
    }
    
    /**
     * Change AI count
     */
    private void changeAiCount(int direction) {
        selectedAiCount += direction;
        if (selectedAiCount < 0) {
            selectedAiCount = 0;
        } else if (selectedAiCount > RaceSettings.MAX_AI_COUNT) {
            selectedAiCount = RaceSettings.MAX_AI_COUNT;
        }
    }
    
    /**
     * Cycle through difficulty levels
     */
    private void cycleDifficulty() {
        RaceSettings.Difficulty[] difficulties = RaceSettings.Difficulty.values();
        int currentIndex = selectedDifficulty.ordinal();
        int nextIndex = (currentIndex + 1) % difficulties.length;
        selectedDifficulty = difficulties[nextIndex];
    }
    
    /**
     * Get currently selected track metadata
     */
    private TrackMetadata getSelectedTrack() {
        var unlockedTracks = settingsManager.getUnlockedTracks();
        if (selectedTrackIndex >= 0 && selectedTrackIndex < unlockedTracks.size()) {
            return unlockedTracks.get(selectedTrackIndex);
        }
        return null;
    }
    
    /**
     * Start race with current settings
     */
    private boolean startRace() {
        try {
            // Create race settings from UI state
            RaceSettings settings = new RaceSettings();
            settings.setTrackIndex(selectedTrackIndex);
            settings.setLaps(selectedLaps);
            settings.setAiCount(selectedAiCount);
            settings.setDifficulty(selectedDifficulty);
            
            TrackMetadata track = getSelectedTrack();
            if (track != null) {
                settings.setTrackName(track.getTrackName());
                settings.setTrackMetadata(track);
            }
            
            // Apply settings
            return settingsManager.applySettings(settings);
            
        } catch (Exception e) {
            System.err.println("Error starting race: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Update UI state from current settings manager
     */
    public void syncWithSettings() {
        RaceSettings current = settingsManager.getCurrentSettings();
        this.selectedTrackIndex = current.getTrackIndex();
        this.selectedLaps = current.getLaps();
        this.selectedAiCount = current.getAiCount();
        this.selectedDifficulty = current.getDifficulty();
    }
    
    /**
     * Check if race settings UI is currently visible
     */
    public boolean isShowingSettings() {
        return showRaceSettings;
    }
    
    /**
     * Hide race settings panel
     */
    public void hideSettings() {
        showRaceSettings = false;
    }
}
