package nfm.lit.racesettings;

import java.util.*;

/**
 * Manages the pool of available car models and instances.
 * Handles procedural variant generation and duplicate management.
 */
public class CarPoolManager {
    private final List<CarModel> baseModels;
    private final List<CarVariant> variants;
    private final Map<String, Integer> reservedCounts;
    private final int maxVariantsPercentage = 50; // Only generate up to 50% of base pool size as variants
    
    // Hard limits for safety
    public static final int HARD_CAP_TOTAL_CARS = 32;
    public static final int RECOMMENDED_MAX_CARS = 16;
    
    public CarPoolManager(List<CarModel> baseModels) {
        this.baseModels = new ArrayList<>(baseModels);
        this.variants = new ArrayList<>();
        this.reservedCounts = new HashMap<>();
        
        // Initialize reservation counts
        for (CarModel model : baseModels) {
            reservedCounts.put(model.getId(), 0);
        }
    }
    
    /**
     * Gets the number of unique car models available (base + variants).
     */
    public int getAvailableModels() {
        return baseModels.size() + variants.size();
    }
    
    /**
     * Gets the maximum total instances allowed considering duplicates.
     * This is limited by either car pool size or safety caps.
     */
    public int getTotalInstancesAllowed() {
        return Math.min(HARD_CAP_TOTAL_CARS, getAvailableModels() * getMaxDuplicatesPerModel());
    }
    
    /**
     * Gets the maximum number of duplicate instances per model.
     */
    public int getMaxDuplicatesPerModel() {
        // Allow up to 3 duplicates per model to prevent visual repetition
        return 3;
    }
    
    /**
     * Checks if the requested number of cars can be accommodated.
     * 
     * @param requestedCars Total cars needed
     * @param allowVariants Whether variant generation is allowed
     * @param allowDuplicates Whether duplicate models are allowed
     * @return true if the request can be satisfied
     */
    public boolean canAccommodate(int requestedCars, boolean allowVariants, boolean allowDuplicates) {
        if (requestedCars > HARD_CAP_TOTAL_CARS) {
            return false;
        }
        
        int availableUnique = getAvailableModels();
        
        if (allowDuplicates) {
            // With duplicates, we can use each model multiple times
            int maxWithDuplicates = availableUnique * getMaxDuplicatesPerModel();
            return requestedCars <= maxWithDuplicates;
        }
        
        if (requestedCars <= availableUnique) {
            return true; // Can satisfy with existing models
        }
        
        if (allowVariants) {
            // Calculate how many variants we can generate
            int maxVariants = (baseModels.size() * maxVariantsPercentage) / 100;
            int totalPossible = availableUnique + maxVariants;
            return requestedCars <= totalPossible;
        }
        
        return false; // Not enough unique models and variants/duplicates not allowed
    }
    
    /**
     * Reserves instances for a race. This should be called during race setup.
     * 
     * @param count Number of instances to reserve
     * @param allowVariants Whether to generate variants if needed
     * @param allowDuplicates Whether to allow duplicate models
     * @return CarAllocation describing what cars were allocated
     */
    public CarAllocation reserveInstances(int count, boolean allowVariants, boolean allowDuplicates) {
        if (count > HARD_CAP_TOTAL_CARS) {
            throw new IllegalArgumentException("Requested " + count + " cars exceeds hard cap of " + HARD_CAP_TOTAL_CARS);
        }
        
        List<CarInstance> allocated = new ArrayList<>();
        Set<String> usedModelIds = new HashSet<>();
        
        // First pass: Use base models
        for (CarModel model : baseModels) {
            if (allocated.size() >= count) break;
            
            allocated.add(new CarInstance(model, false, 1));
            usedModelIds.add(model.getId());
        }
        
        // Second pass: Use existing variants
        for (CarVariant variant : variants) {
            if (allocated.size() >= count) break;
            if (!usedModelIds.contains(variant.getBaseModelId())) {
                allocated.add(new CarInstance(variant, false, 1));
                usedModelIds.add(variant.getId());
            }
        }
        
        // Third pass: Generate new variants if needed and allowed
        if (allocated.size() < count && allowVariants) {
            int variantsNeeded = Math.min(count - allocated.size(), 
                (baseModels.size() * maxVariantsPercentage) / 100 - variants.size());
            
            for (int i = 0; i < variantsNeeded && allocated.size() < count; i++) {
                CarModel baseModel = baseModels.get(i % baseModels.size());
                CarVariant newVariant = generateVariant(baseModel, variants.size() + 1);
                variants.add(newVariant);
                allocated.add(new CarInstance(newVariant, false, 1));
            }
        }
        
        // Fourth pass: Use duplicates if needed and allowed
        if (allocated.size() < count && allowDuplicates) {
            int duplicatesNeeded = count - allocated.size();
            List<CarModel> allModels = new ArrayList<>(baseModels);
            
            for (int i = 0; i < duplicatesNeeded; i++) {
                CarModel model = allModels.get(i % allModels.size());
                int duplicateNumber = (i / allModels.size()) + 2; // Start at 2 since 1 is original
                
                if (duplicateNumber <= getMaxDuplicatesPerModel()) {
                    allocated.add(new CarInstance(model, true, duplicateNumber));
                }
            }
        }
        
        return new CarAllocation(allocated, count - allocated.size());
    }
    
    /**
     * Generates a procedural variant of a car model.
     * Variants use different colors, decals, and minor stat tweaks.
     */
    private CarVariant generateVariant(CarModel baseModel, int variantNumber) {
        // Use deterministic seed for consistent generation
        Random random = new Random(baseModel.getId().hashCode() + variantNumber);
        
        // Generate color palette variations
        int[] baseColor = baseModel.getColor();
        int[] variantColor = new int[3];
        for (int i = 0; i < 3; i++) {
            int shift = random.nextInt(120) - 60; // +/- 60 color shift
            variantColor[i] = Math.max(0, Math.min(255, baseColor[i] + shift));
        }
        
        // Generate stat perturbations (small changes to avoid gameplay impact)
        Map<String, Float> statModifiers = new HashMap<>();
        statModifiers.put("topSpeed", 0.95f + random.nextFloat() * 0.1f); // ±5% top speed
        statModifiers.put("handling", 0.95f + random.nextFloat() * 0.1f); // ±5% handling
        statModifiers.put("acceleration", 0.98f + random.nextFloat() * 0.04f); // ±2% acceleration
        
        return new CarVariant(
            baseModel.getId() + "_variant_" + variantNumber,
            baseModel.getName() + " Variant " + variantNumber,
            baseModel.getId(),
            variantColor,
            statModifiers
        );
    }
    
    /**
     * Runs a stress test simulation to measure performance with high AI counts.
     * This is a developer feature for performance testing.
     */
    public PerformanceTestResult runStressTest(int aiCount) {
        long startTime = System.currentTimeMillis();
        
        // Simulate memory usage
        Runtime runtime = Runtime.getRuntime();
        long startMemory = runtime.totalMemory() - runtime.freeMemory();
        
        // Simulate car allocation
        CarAllocation allocation = reserveInstances(aiCount, true, true);
        
        long endTime = System.currentTimeMillis();
        long endMemory = runtime.totalMemory() - runtime.freeMemory();
        
        return new PerformanceTestResult(
            aiCount,
            endTime - startTime,
            endMemory - startMemory,
            allocation.getAllocatedCount(),
            allocation.getShortfall()
        );
    }
    
    /**
     * Gets a list of all base models.
     */
    public List<CarModel> getBaseModels() {
        return Collections.unmodifiableList(baseModels);
    }
    
    /**
     * Gets a list of all generated variants.
     */
    public List<CarVariant> getVariants() {
        return Collections.unmodifiableList(variants);
    }
    
    /**
     * Clears all generated variants (useful for cleanup).
     */
    public void clearVariants() {
        variants.clear();
    }
}
