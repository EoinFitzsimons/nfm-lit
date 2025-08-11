package nfm.lit.racesettings;

/**
 * Represents an instance of a car model allocated for a race.
 * This tracks whether it's a duplicate and which duplicate number it is.
 */
public class CarInstance {
    private final CarModel model;
    private final boolean isDuplicate;
    private final int duplicateNumber;
    private final String driverName;
    
    public CarInstance(CarModel model, boolean isDuplicate, int duplicateNumber) {
        this.model = model;
        this.isDuplicate = isDuplicate;
        this.duplicateNumber = duplicateNumber;
        this.driverName = generateDriverName(model, isDuplicate, duplicateNumber);
    }
    
    private static String generateDriverName(CarModel model, boolean isDuplicate, int duplicateNumber) {
        if (!isDuplicate || duplicateNumber == 1) {
            return model.getName() + " Driver";
        } else {
            return model.getName() + " Driver " + duplicateNumber;
        }
    }
    
    public CarModel getModel() { return model; }
    public boolean isDuplicate() { return isDuplicate; }
    public int getDuplicateNumber() { return duplicateNumber; }
    public String getDriverName() { return driverName; }
    
    /**
     * Returns a unique identifier for this car instance.
     */
    public String getInstanceId() {
        if (!isDuplicate || duplicateNumber == 1) {
            return model.getId();
        } else {
            return model.getId() + "_dup_" + duplicateNumber;
        }
    }
    
    @Override
    public String toString() {
        if (!isDuplicate || duplicateNumber == 1) {
            return model.toString();
        } else {
            return String.format("%s (Copy %d)", model.toString(), duplicateNumber);
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CarInstance that = (CarInstance) obj;
        return isDuplicate == that.isDuplicate &&
               duplicateNumber == that.duplicateNumber &&
               model.equals(that.model);
    }
    
    @Override
    public int hashCode() {
        return model.hashCode() * 31 + duplicateNumber;
    }
}
