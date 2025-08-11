package nfm.lit.racesettings.ui;

import nfm.lit.racesettings.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Main UI panel for configuring race settings including laps, AI count,
 * difficulty, and car variants. Validates settings against track metadata
 * and provides real-time feedback to the user.
 */
public class RaceSettingsUI extends JPanel {
    private static final int DEFAULT_AI_COUNT = 6;
    private static final int MIN_AI_COUNT = 0;
    private static final int MAX_AI_COUNT = 16; // Configurable cap
    
    // UI Components
    private JSpinner lapCountSpinner;
    private JSpinner aiCountSpinner;
    private JComboBox<AIDifficulty> difficultyCombo;
    private JCheckBox enableVariantsCheckbox;
    private JSlider variantStrengthSlider;
    private JLabel totalCarsLabel;
    private JLabel validationLabel;
    private JButton startRaceButton;
    private JButton resetDefaultsButton;
    
    // Validation Components
    private ValidationStatusPanel validationPanel;
    private CarAllocationPanel carAllocationPanel;
    
    // Data
    private TrackMetadata trackMetadata;
    private CarPoolManager carPoolManager;
    private RaceSettings currentSettings;
    private List<RaceSettingsListener> listeners;
    
    public RaceSettingsUI(TrackMetadata trackMetadata, CarPoolManager carPoolManager) {
        this.trackMetadata = trackMetadata != null ? trackMetadata : TrackMetadata.createFallback("Unknown Track");
        this.carPoolManager = carPoolManager;
        this.listeners = new ArrayList<>();
        
        initializeComponents();
        layoutComponents();
        setupEventListeners();
        updateSettings();
    }
    
    private void initializeComponents() {
        // Lap Count
        lapCountSpinner = new JSpinner(new SpinnerNumberModel(
            trackMetadata.getDefaultLaps(), 
            trackMetadata.getMinLaps(), 
            trackMetadata.getMaxLaps(), 
            1
        ));
        
        // AI Count  
        aiCountSpinner = new JSpinner(new SpinnerNumberModel(
            DEFAULT_AI_COUNT,
            MIN_AI_COUNT,
            Math.min(MAX_AI_COUNT, trackMetadata.getMaxConcurrentCars() - 1), // -1 for player
            1
        ));
        
        // AI Difficulty
        difficultyCombo = new JComboBox<>(AIDifficulty.values());
        difficultyCombo.setSelectedItem(AIDifficulty.NORMAL);
        
        // Car Variants
        enableVariantsCheckbox = new JCheckBox("Enable Car Variants", true);
        variantStrengthSlider = new JSlider(0, 100, 25); // 0-100% variant strength
        variantStrengthSlider.setMajorTickSpacing(25);
        variantStrengthSlider.setMinorTickSpacing(5);
        variantStrengthSlider.setPaintTicks(true);
        variantStrengthSlider.setPaintLabels(true);
        
        // Status Labels
        totalCarsLabel = new JLabel("Total Cars: 7");
        validationLabel = new JLabel(" "); // Space to maintain height
        
        // Action Buttons
        startRaceButton = new JButton("Start Race");
        resetDefaultsButton = new JButton("Reset to Defaults");
        
        // Advanced Panels
        validationPanel = new ValidationStatusPanel();
        carAllocationPanel = new CarAllocationPanel();
    }
    
    private void layoutComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createTitledBorder("Race Settings"));
        
        // Main settings panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Row 1: Lap Count
        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(new JLabel("Laps:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(lapCountSpinner, gbc);
        gbc.gridx = 2;
        mainPanel.add(new JLabel("(" + trackMetadata.getMinLaps() + "-" + trackMetadata.getMaxLaps() + ")"), gbc);
        
        // Row 2: AI Count
        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(new JLabel("AI Count:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(aiCountSpinner, gbc);
        gbc.gridx = 2;
        mainPanel.add(totalCarsLabel, gbc);
        
        // Row 3: AI Difficulty
        gbc.gridx = 0; gbc.gridy = 2;
        mainPanel.add(new JLabel("AI Difficulty:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        mainPanel.add(difficultyCombo, gbc);
        gbc.gridwidth = 1;
        
        // Row 4: Car Variants
        gbc.gridx = 0; gbc.gridy = 3;
        mainPanel.add(enableVariantsCheckbox, gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(createVariantPanel(), gbc);
        gbc.fill = GridBagConstraints.NONE; gbc.gridwidth = 1;
        
        // Row 5: Validation
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(validationLabel, gbc);
        gbc.fill = GridBagConstraints.NONE; gbc.gridwidth = 1;
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(resetDefaultsButton);
        buttonPanel.add(startRaceButton);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Advanced panels (collapsible)
        JPanel advancedPanel = new JPanel(new BorderLayout());
        advancedPanel.add(validationPanel, BorderLayout.NORTH);
        advancedPanel.add(carAllocationPanel, BorderLayout.CENTER);
        add(advancedPanel, BorderLayout.EAST);
    }
    
    private JPanel createVariantPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Strength:"), BorderLayout.WEST);
        panel.add(variantStrengthSlider, BorderLayout.CENTER);
        panel.add(new JLabel("%"), BorderLayout.EAST);
        return panel;
    }
    
    private void setupEventListeners() {
        // Settings change listeners
        lapCountSpinner.addChangeListener(e -> updateSettings());
        aiCountSpinner.addChangeListener(e -> updateSettings());
        difficultyCombo.addActionListener(e -> updateSettings());
        enableVariantsCheckbox.addActionListener(e -> {
            variantStrengthSlider.setEnabled(enableVariantsCheckbox.isSelected());
            updateSettings();
        });
        variantStrengthSlider.addChangeListener(e -> {
            if (!variantStrengthSlider.getValueIsAdjusting()) {
                updateSettings();
            }
        });
        
        // Action buttons
        startRaceButton.addActionListener(e -> startRace());
        resetDefaultsButton.addActionListener(e -> resetToDefaults());
    }
    
    private void updateSettings() {
        try {
            // Build settings
            RaceSettings.Builder builder = new RaceSettings.Builder()
                .lapCount((Integer) lapCountSpinner.getValue())
                .aiCount((Integer) aiCountSpinner.getValue())
                .aiDifficulty((AIDifficulty) difficultyCombo.getSelectedItem())
                .generateVariants(enableVariantsCheckbox.isSelected())
                .variantsToGenerate(variantStrengthSlider.getValue() / 4); // Convert % to count
            
            currentSettings = builder.build();
            
            // Update UI
            updateTotalCarsLabel();
            updateValidation();
            updateCarAllocation();
            
        } catch (Exception e) {
            // Handle validation errors
            validationLabel.setText("Error: " + e.getMessage());
            validationLabel.setForeground(Color.RED);
            startRaceButton.setEnabled(false);
        }
    }
    
    private void updateTotalCarsLabel() {
        int total = currentSettings.getTotalCars();
        int max = trackMetadata.getMaxConcurrentCars();
        totalCarsLabel.setText("Total Cars: " + total + "/" + max);
        
        if (total > max) {
            totalCarsLabel.setForeground(Color.RED);
        } else if (total > max * 0.75) {
            totalCarsLabel.setForeground(Color.ORANGE);
        } else {
            totalCarsLabel.setForeground(Color.BLACK);
        }
    }
    
    private void updateValidation() {
        ValidationResult validation = currentSettings.validate(trackMetadata, carPoolManager);
        validationPanel.setValidation(validation);
        
        if (!validation.isValid()) {
            validationLabel.setText("❌ " + validation.getErrors().get(0));
            validationLabel.setForeground(Color.RED);
            startRaceButton.setEnabled(false);
        } else if (validation.hasWarnings()) {
            validationLabel.setText("⚠️ " + validation.getWarnings().get(0));
            validationLabel.setForeground(Color.ORANGE);
            startRaceButton.setEnabled(true);
        } else {
            validationLabel.setText("✅ Settings valid");
            validationLabel.setForeground(Color.GREEN);
            startRaceButton.setEnabled(true);
        }
    }
    
    private void updateCarAllocation() {
        if (carPoolManager != null && currentSettings != null) {
            try {
                CarAllocation allocation = carPoolManager.reserveInstances(
                    currentSettings.getTotalCars() - 1, // Subtract player
                    currentSettings.isGenerateVariants(),
                    currentSettings.isAllowDuplicates()
                );
                carAllocationPanel.setAllocation(allocation);
            } catch (Exception e) {
                carAllocationPanel.setError("Allocation failed: " + e.getMessage());
            }
        }
    }
    
    private void startRace() {
        if (currentSettings == null || !startRaceButton.isEnabled()) {
            return;
        }
        
        // Notify listeners
        for (RaceSettingsListener listener : listeners) {
            listener.onStartRace(currentSettings);
        }
    }
    
    private void resetToDefaults() {
        lapCountSpinner.setValue(trackMetadata.getDefaultLaps());
        aiCountSpinner.setValue(DEFAULT_AI_COUNT);
        difficultyCombo.setSelectedItem(AIDifficulty.NORMAL);
        enableVariantsCheckbox.setSelected(true);
        variantStrengthSlider.setValue(25);
        
        updateSettings();
    }
    
    // Public API
    public RaceSettings getCurrentSettings() {
        return currentSettings;
    }
    
    public void setTrackMetadata(TrackMetadata metadata) {
        this.trackMetadata = metadata != null ? metadata : TrackMetadata.createFallback("Unknown Track");
        
        // Update spinner models
        SpinnerNumberModel lapModel = (SpinnerNumberModel) lapCountSpinner.getModel();
        lapModel.setMinimum(trackMetadata.getMinLaps());
        lapModel.setMaximum(trackMetadata.getMaxLaps());
        lapModel.setValue(trackMetadata.getDefaultLaps());
        
        SpinnerNumberModel aiModel = (SpinnerNumberModel) aiCountSpinner.getModel();
        aiModel.setMaximum(Math.min(MAX_AI_COUNT, trackMetadata.getMaxConcurrentCars() - 1));
        
        updateSettings();
    }
    
    public void addListener(RaceSettingsListener listener) {
        listeners.add(listener);
    }
    
    public void removeListener(RaceSettingsListener listener) {
        listeners.remove(listener);
    }
    
    // Listener interface
    public interface RaceSettingsListener {
        void onStartRace(RaceSettings settings);
    }
}
