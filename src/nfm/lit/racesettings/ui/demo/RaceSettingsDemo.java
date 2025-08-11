package nfm.lit.racesettings.ui.demo;

import nfm.lit.racesettings.*;
import nfm.lit.racesettings.ui.RaceSettingsUI;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * Demo application to showcase the Race Settings UI system.
 * Creates sample car models and track metadata to test the UI components.
 */
public class RaceSettingsDemo extends JFrame {
    private RaceSettingsUI raceSettingsUI;
    private CarPoolManager carPoolManager;
    
    public RaceSettingsDemo() {
        setupDemo();
        createUI();
        setupEventHandlers();
    }
    
    private void setupDemo() {
        // Create sample car models
        List<CarModel> carModels = Arrays.asList(
            new CarModel("ferrari_f1", "Ferrari F1", new int[]{255, 0, 0}, "models/ferrari.obj"),
            new CarModel("porsche_gt", "Porsche GT", new int[]{255, 255, 0}, "models/porsche.obj"),
            new CarModel("lamborghini_av", "Lamborghini Aventador", new int[]{128, 255, 0}, "models/lambo.obj"),
            new CarModel("mclaren_p1", "McLaren P1", new int[]{255, 128, 0}, "models/mclaren.obj"),
            new CarModel("bugatti_vey", "Bugatti Veyron", new int[]{0, 0, 255}, "models/bugatti.obj"),
            new CarModel("aston_db11", "Aston Martin DB11", new int[]{128, 128, 128}, "models/aston.obj")
        );
        
        // Create car pool manager
        carPoolManager = new CarPoolManager(carModels);
        
        // Create sample track metadata
        TrackMetadata trackMetadata = new TrackMetadata("Demo Track", 5, 1, 10, 8);
        
        // Create the race settings UI
        raceSettingsUI = new RaceSettingsUI(trackMetadata, carPoolManager);
    }
    
    private void createUI() {
        setTitle("NFM-Lit Race Settings Demo");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Add the race settings panel
        add(raceSettingsUI, BorderLayout.CENTER);
        
        // Add control buttons
        JPanel controlPanel = new JPanel(new FlowLayout());
        
        JButton loadTrackButton = new JButton("Load Different Track");
        loadTrackButton.addActionListener(e -> loadDifferentTrack());
        
        JButton resetButton = new JButton("Reset Demo");
        resetButton.addActionListener(e -> resetDemo());
        
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        
        controlPanel.add(loadTrackButton);
        controlPanel.add(resetButton);
        controlPanel.add(exitButton);
        
        add(controlPanel, BorderLayout.SOUTH);
        
        // Add info panel
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBorder(BorderFactory.createTitledBorder("Demo Information"));
        
        JTextArea infoText = new JTextArea(
            "NFM-Lit Race Settings Demo\\n\\n" +
            "This demo showcases the race configuration system:\\n" +
            "• Adjust lap count, AI count, and difficulty\\n" +
            "• Enable car variants for variety\\n" +
            "• Real-time validation against track limits\\n" +
            "• Car allocation preview with duplicate detection\\n\\n" +
            "The system respects track metadata and provides\\n" +
            "warnings when limits are exceeded."
        );
        infoText.setEditable(false);
        infoText.setBackground(getBackground());
        infoPanel.add(new JScrollPane(infoText), BorderLayout.CENTER);
        
        add(infoPanel, BorderLayout.WEST);
        
        pack();
        setLocationRelativeTo(null);
    }
    
    private void setupEventHandlers() {
        raceSettingsUI.addListener(settings ->
            JOptionPane.showMessageDialog(this,
                "Race would start with these settings:\\n\\n" + settings.toString(),
                "Start Race",
                JOptionPane.INFORMATION_MESSAGE));
    }
    
    private void loadDifferentTrack() {
        // Demo different track configurations
        String[] trackOptions = {
            "Short Circuit (3-5 laps, max 6 cars)",
            "Standard Track (5-12 laps, max 8 cars)", 
            "Endurance Track (10-50 laps, max 12 cars)",
            "Arena Track (1-3 laps, max 16 cars)"
        };
        
        String selected = (String) JOptionPane.showInputDialog(this,
            "Select a track configuration:",
            "Load Track",
            JOptionPane.QUESTION_MESSAGE,
            null,
            trackOptions,
            trackOptions[1]);
            
        if (selected != null) {
            TrackMetadata metadata;
            if (selected.contains("Short Circuit")) {
                metadata = new TrackMetadata("Short Circuit", 3, 3, 5, 6);
            } else if (selected.contains("Endurance")) {
                metadata = new TrackMetadata("Endurance Track", 25, 10, 50, 12);
            } else if (selected.contains("Arena")) {
                metadata = new TrackMetadata("Arena Track", 2, 1, 3, 16);
            } else {
                metadata = new TrackMetadata("Standard Track", 8, 5, 12, 8);
            }
            
            raceSettingsUI.setTrackMetadata(metadata);
        }
    }
    
    private void resetDemo() {
        TrackMetadata defaultTrack = new TrackMetadata("Demo Track", 5, 1, 10, 8);
        raceSettingsUI.setTrackMetadata(defaultTrack);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Use default look and feel
            } catch (Exception e) {
                // Fallback to default L&F
            }
            
            new RaceSettingsDemo().setVisible(true);
        });
    }
}
