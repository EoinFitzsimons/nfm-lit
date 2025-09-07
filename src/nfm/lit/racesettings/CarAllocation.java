package nfm.lit.racesettings;

import java.util.Collections;
import java.util.List;

/**
 * Result of allocating car instances for a race.
 * Contains the cars that were successfully allocated and any shortfall.
 */
public class CarAllocation {
    private final List<CarInstance> allocatedCars;
    private final int shortfall;
    
    public CarAllocation(List<CarInstance> allocatedCars, int shortfall) {
        this.allocatedCars = Collections.unmodifiableList(allocatedCars);
        this.shortfall = Math.max(0, shortfall);
    }
    
    /**
     * Gets all allocated car instances.
     */
    public List<CarInstance> getAllocatedCars() {
        return allocatedCars;
    }
    
    /**
     * Gets the number of cars successfully allocated.
     */
    public int getAllocatedCount() {
        return allocatedCars.size();
    }
    
    /**
     * Gets the number of cars that couldn't be allocated.
     */
    public int getShortfall() {
        return shortfall;
    }
    
    /**
     * Returns true if all requested cars were allocated.
     */
    public boolean isComplete() {
        return shortfall == 0;
    }
    
    /**
     * Returns true if any duplicates were used.
     */
    public boolean hasDuplicates() {
        return allocatedCars.stream().anyMatch(CarInstance::isDuplicate);
    }
    
    /**
     * Returns true if any variants were used.
     */
    public boolean hasVariants() {
        return allocatedCars.stream()
            .map(CarInstance::getModel)
            .anyMatch(CarVariant.class::isInstance);
    }
    
    @Override
    public String toString() {
        return String.format("CarAllocation{allocated=%d, shortfall=%d, duplicates=%s, variants=%s}",
            getAllocatedCount(), shortfall, hasDuplicates(), hasVariants());
    }
}
