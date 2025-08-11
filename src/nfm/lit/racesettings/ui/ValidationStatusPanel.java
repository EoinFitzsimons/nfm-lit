package nfm.lit.racesettings.ui;

import nfm.lit.racesettings.ValidationResult;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Panel that displays validation results for race settings.
 * Shows errors and warnings with appropriate colors and icons.
 */
public class ValidationStatusPanel extends JPanel {
    private JLabel statusLabel;
    private JList<String> messageList;
    private DefaultListModel<String> listModel;
    private JScrollPane scrollPane;
    private ValidationResult currentValidation;
    
    public ValidationStatusPanel() {
        initializeComponents();
        layoutComponents();
        setValidation(null); // Show empty state
    }
    
    private void initializeComponents() {
        statusLabel = new JLabel("No validation results");
        statusLabel.setFont(statusLabel.getFont().deriveFont(Font.BOLD));
        
        listModel = new DefaultListModel<>();
        messageList = new JList<>(listModel);
        messageList.setCellRenderer(new ValidationMessageRenderer());
        messageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        scrollPane = new JScrollPane(messageList);
        scrollPane.setPreferredSize(new Dimension(300, 100));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }
    
    private void layoutComponents() {
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createTitledBorder("Validation Status"));
        
        add(statusLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    public void setValidation(ValidationResult validation) {
        currentValidation = validation;
        updateDisplay();
    }
    
    private void updateDisplay() {
        listModel.clear();
        
        if (currentValidation == null) {
            statusLabel.setText("No validation results");
            statusLabel.setForeground(Color.GRAY);
            statusLabel.setIcon(null);
            return;
        }
        
        List<String> errors = currentValidation.getErrors();
        List<String> warnings = currentValidation.getWarnings();
        
        // Update status label
        if (!currentValidation.isValid()) {
            statusLabel.setText("❌ " + errors.size() + " error(s)");
            statusLabel.setForeground(Color.RED);
        } else if (currentValidation.hasWarnings()) {
            statusLabel.setText("⚠️ " + warnings.size() + " warning(s)");
            statusLabel.setForeground(Color.ORANGE);
        } else {
            statusLabel.setText("✅ All checks passed");
            statusLabel.setForeground(Color.GREEN);
        }
        
        // Add error messages
        for (String error : errors) {
            listModel.addElement("❌ " + error);
        }
        
        // Add warning messages
        for (String warning : warnings) {
            listModel.addElement("⚠️ " + warning);
        }
        
        // Auto-resize the panel if needed
        if (!listModel.isEmpty()) {
            messageList.ensureIndexIsVisible(0);
        }
    }
    
    /**
     * Custom renderer for validation messages with color coding.
     */
    private static class ValidationMessageRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            
            Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            
            if (component instanceof JLabel && value instanceof String) {
                JLabel label = (JLabel) component;
                String text = (String) value;
                
                if (!isSelected) {
                    if (text.startsWith("❌")) {
                        label.setForeground(Color.RED);
                    } else if (text.startsWith("⚠️")) {
                        label.setForeground(Color.ORANGE);
                    } else {
                        label.setForeground(Color.BLACK);
                    }
                }
                
                // Add some padding
                label.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
            }
            
            return component;
        }
    }
}
