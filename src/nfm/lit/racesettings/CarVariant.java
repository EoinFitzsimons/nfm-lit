package nfm.lit.racesettings;

import java.util.Map;

/**
 * Represents a procedurally generated variant of a base car model.
 * Variants have different colors, decals, and minor stat modifications.
 */
public class CarVariant extends CarModel {
    private final String baseModelId;
    private final Map<String, Float> statModifiers;
    
    public CarVariant(String id, String name, String baseModelId, int[] color, Map<String, Float> statModifiers) {
        super(id, name, color, lookupModelFile(baseModelId));
        this.baseModelId = baseModelId;
        this.statModifiers = Map.copyOf(statModifiers); // Immutable copy
    }
    
    private static String lookupModelFile(String baseModelId) {
        // Variants use the same 3D model as their base
        // In a full implementation, this would look up the base model's file
        return baseModelId;
    }
    
    public String getBaseModelId() { return baseModelId; }
    public Map<String, Float> getStatModifiers() { return statModifiers; }
    
    /**
     * Gets the modifier for a specific stat, or 1.0f if not present.
     */
    public float getStatModifier(String statName) {
        return statModifiers.getOrDefault(statName, 1.0f);
    }
    
    @Override
    public String toString() {
        return String.format("CarVariant{id='%s', name='%s', base='%s'}", 
            getId(), getName(), baseModelId);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        CarVariant that = (CarVariant) obj;
        return baseModelId.equals(that.baseModelId);
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() * 31 + baseModelId.hashCode();
    }
}
