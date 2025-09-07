package nfm.lit.racesettings;

/**
 * Represents a base car model with core attributes.
 */
public class CarModel {
    private final String id;
    private final String name;
    private final int[] color; // RGB color values
    private final String modelFile; // Path to 3D model file
    
    public CarModel(String id, String name, int[] color, String modelFile) {
        this.id = id;
        this.name = name;
        this.color = color.clone();
        this.modelFile = modelFile;
    }
    
    public String getId() { return id; }
    public String getName() { return name; }
    public int[] getColor() { return color.clone(); }
    public String getModelFile() { return modelFile; }
    
    @Override
    public String toString() {
        return String.format("CarModel{id='%s', name='%s'}", id, name);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CarModel carModel = (CarModel) obj;
        return id.equals(carModel.id);
    }
    
    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
