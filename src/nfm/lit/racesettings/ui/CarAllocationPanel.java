package nfm.lit.racesettings.ui;

import nfm.lit.racesettings.CarAllocation;
import nfm.lit.racesettings.CarInstance;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.List;

/**
 * Panel that displays car allocation results including car models,
 * variants, and driver assignments. Shows duplicate detection and
 * allocation statistics.
 */
public class CarAllocationPanel extends JPanel {
    private JLabel statusLabel;
    private JTable carTable;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    private JLabel statsLabel;
    private CarAllocation currentAllocation;
    
    private static final String[] COLUMN_NAMES = {
        "Driver", "Car Model", "Type", "Status"
    };
    
    public CarAllocationPanel() {
        initializeComponents();
        layoutComponents();
        setAllocation(null); // Show empty state
    }
    
    private void initializeComponents() {
        statusLabel = new JLabel("No allocation results");
        statusLabel.setFont(statusLabel.getFont().deriveFont(Font.BOLD));
        
        tableModel = new DefaultTableModel(COLUMN_NAMES, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Read-only table
            }
        };
        
        carTable = new JTable(tableModel);
        carTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        carTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        // Custom renderer for variant stats
        carTable.setDefaultRenderer(Object.class, new CarTableRenderer());
        
        scrollPane = new JScrollPane(carTable);
        scrollPane.setPreferredSize(new Dimension(500, 200));
        
        statsLabel = new JLabel(" ");
    }
    
    private void layoutComponents() {
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createTitledBorder("Car Allocation"));
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(statusLabel, BorderLayout.NORTH);
        headerPanel.add(statsLabel, BorderLayout.SOUTH);
        
        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    public void setAllocation(CarAllocation allocation) {
        currentAllocation = allocation;
        updateDisplay();
    }
    
    public void setError(String errorMessage) {
        currentAllocation = null;
        statusLabel.setText("❌ " + errorMessage);
        statusLabel.setForeground(Color.RED);
        tableModel.setRowCount(0);
        statsLabel.setText(" ");
    }
    
    private void updateDisplay() {
        tableModel.setRowCount(0); // Clear existing data
        
        if (currentAllocation == null) {
            statusLabel.setText("No allocation results");
            statusLabel.setForeground(Color.GRAY);
            statsLabel.setText(" ");
            return;
        }
        
        List<CarInstance> allocatedCars = currentAllocation.getAllocatedCars();
        
        // Update status
        int totalCars = allocatedCars.size();
        int shortfall = currentAllocation.getShortfall();
        
        if (shortfall > 0) {
            statusLabel.setText("⚠️ Allocation incomplete (" + shortfall + " cars short)");
            statusLabel.setForeground(Color.ORANGE);
        } else if (currentAllocation.hasDuplicates()) {
            statusLabel.setText("⚠️ Allocation complete (duplicates used)");
            statusLabel.setForeground(Color.ORANGE);
        } else {
            statusLabel.setText("✅ Allocation successful");
            statusLabel.setForeground(Color.GREEN);
        }
        
        // Update stats
        long uniqueModels = allocatedCars.stream()
            .map(car -> car.getModel().getId())
            .distinct()
            .count();
        
        statsLabel.setText(String.format("Total: %d cars, Unique models: %d, Duplicates: %s, Variants: %s", 
            totalCars, uniqueModels, currentAllocation.hasDuplicates() ? "Yes" : "No", 
            currentAllocation.hasVariants() ? "Yes" : "No"));
        
        // Add player car (first car is assumed to be player)
        if (!allocatedCars.isEmpty()) {
            addCarRow(allocatedCars.get(0), true);
        }
        
        // Add AI cars (remaining cars)
        for (int i = 1; i < allocatedCars.size(); i++) {
            addCarRow(allocatedCars.get(i), false);
        }
        
        // Adjust column widths
        carTable.getColumnModel().getColumn(0).setPreferredWidth(120); // Driver
        carTable.getColumnModel().getColumn(1).setPreferredWidth(200); // Car Model
        carTable.getColumnModel().getColumn(2).setPreferredWidth(100); // Type
        carTable.getColumnModel().getColumn(3).setPreferredWidth(80);  // Status
    }
    
    private void addCarRow(CarInstance car, boolean isPlayer) {
        String driverName = isPlayer ? "Player" : car.getDriverName();
        String carModel = car.getModel().getName();
        String type = "Standard"; // Simplified for now
        String status = car.isDuplicate() ? "Duplicate #" + car.getDuplicateNumber() : "Original";
        
        tableModel.addRow(new Object[]{driverName, carModel, type, status});
    }
    
    /**
     * Custom table cell renderer for car allocation display.
     */
    private static class CarTableRenderer extends JLabel implements TableCellRenderer {
        
        public CarTableRenderer() {
            setOpaque(true);
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            setText(value != null ? value.toString() : "");
            
            if (isSelected) {
                setBackground(table.getSelectionBackground());
                setForeground(table.getSelectionForeground());
            } else {
                // Alternate row colors
                Color bg = (row % 2 == 0) ? Color.WHITE : new Color(245, 245, 245);
                setBackground(bg);
                
                // Player vs AI color coding
                String driver = (String) table.getValueAt(row, 0);
                if ("Player".equals(driver)) {
                    setForeground(new Color(0, 100, 0)); // Dark green for player
                } else {
                    setForeground(Color.BLACK); // Black for AI
                }
                
                // Highlight variants (non-standard cars)
                if (column == 2) { // Variant column
                    String variant = (String) value;
                    if (variant != null && !variant.equals("Standard")) {
                        setForeground(new Color(128, 0, 128)); // Purple for variants
                    }
                }
            }
            
            // Add padding
            setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
            
            return this;
        }
    }
}
