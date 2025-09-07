package nfm.lit;

/**
 * Enhanced CarConfig that supports dynamic car addition and demonstrates 
 * extensibility of the race settings system.
 * 
 * This demonstrates how to add custom cars to NFM-Lit while maintaining
 * compatibility with the existing race settings system.
 */
public class EnhancedCarConfig {
    
    // Extended car list with custom cars added
    public static final String[] EXTENDED_CAR_MODELS = {
        // Original 16 NFM cars
        "2000tornados", "formula7", "canyenaro", "lescrab", "nimi", "maxrevenge", 
        "leadoxide", "koolkat", "drifter", "policecops", "mustang", "king", 
        "audir8", "masheen", "radicalone", "drmonster",
        
        // Custom cars (17-20)
        "hypersonic",    // High-speed prototype car
        "tankzilla",     // Heavy armored car
        "bouncemaster",  // Extreme stunt car
        "shadowracer"    // Stealth racing car
    };
    
    /**
     * Enhanced stats for extended car roster including custom cars.
     * These arrays extend the original StatList arrays with new car data.
     */
    public static class ExtendedStats {
        
        // Acceleration stats for custom cars [start accel, mid accel, high accel]
        public static final float[][] CUSTOM_ACELF = {
            {15.0F, 8.0F, 6.0F},   // hypersonic - ultra fast acceleration
            {8.0F, 4.0F, 2.0F},    // tankzilla - slow but steady  
            {12.0F, 6.0F, 4.0F},   // bouncemaster - balanced
            {13.0F, 7.5F, 5.0F}    // shadowracer - quick acceleration
        };
        
        // Top speed stats [gear1, gear2, gear3]
        public static final int[][] CUSTOM_SWITS = {
            {120, 250, 350},  // hypersonic - highest top speed
            {40, 120, 200},   // tankzilla - lowest top speed
            {80, 190, 290},   // bouncemaster - moderate speed
            {100, 220, 320}   // shadowracer - high speed
        };
        
        // Handbrake power
        public static final int[] CUSTOM_HANDB = {
            5,   // hypersonic - light braking (speed car)
            15,  // tankzilla - powerful braking (heavy car)
            8,   // bouncemaster - balanced
            7    // shadowracer - quick stops
        };
        
        // Aerial rotation control
        public static final float[] CUSTOM_AIRS = {
            0.8F,  // hypersonic - less aerial control (speed focused)
            0.3F,  // tankzilla - minimal aerial control (heavy)
            2.5F,  // bouncemaster - extreme aerial control (stunt car)
            1.1F   // shadowracer - good control
        };
        
        // Aerial power
        public static final int[] CUSTOM_AIRC = {
            30,   // hypersonic - low aerial power
            10,   // tankzilla - minimal aerial power  
            95,   // bouncemaster - maximum aerial power
            60    // shadowracer - good aerial power
        };
        
        // Turning ability
        public static final int[] CUSTOM_TURN = {
            5,    // hypersonic - poor turning (speed car)
            15,   // tankzilla - excellent turning (despite weight)
            10,   // bouncemaster - good turning
            8     // shadowracer - balanced turning
        };
        
        // Grip level
        public static final float[] CUSTOM_GRIP = {
            0.6F,  // hypersonic - low grip (slide prone)
            1.2F,  // tankzilla - high grip (stable)
            0.8F,  // bouncemaster - moderate grip
            0.9F   // shadowracer - good grip
        };
        
        // Bounce factor
        public static final float[] CUSTOM_BOUNCE = {
            0.5F,  // hypersonic - low bounce
            0.2F,  // tankzilla - minimal bounce (heavy)
            1.8F,  // bouncemaster - extreme bounce
            0.7F   // shadowracer - moderate bounce
        };
        
        // Strength/mass factor
        public static final float[] CUSTOM_MOMENT = {
            0.8F,  // hypersonic - light car
            2.2F,  // tankzilla - heavy car
            1.1F,  // bouncemaster - moderate weight
            0.9F   // shadowracer - light-moderate
        };
        
        // Damage output multiplier  
        public static final float[] CUSTOM_DAMMULT = {
            0.7F,  // hypersonic - low damage (light car)
            1.8F,  // tankzilla - high damage (heavy hitter)
            1.0F,  // bouncemaster - normal damage
            0.8F   // shadowracer - moderate damage
        };
        
        // Endurance/damage resistance
        public static final float[] CUSTOM_OUTDAM = {
            0.6F,  // hypersonic - fragile (speed car)
            0.9F,  // tankzilla - very durable
            0.7F,  // bouncemaster - moderate durability
            0.8F   // shadowracer - good durability
        };
    }
    
    /**
     * Get total number of cars including custom ones
     */
    public static int getTotalCarCount() {
        return EXTENDED_CAR_MODELS.length;
    }
    
    /**
     * Get car name by index, supporting extended car roster
     */
    public static String getCarName(int index) {
        if (index >= 0 && index < EXTENDED_CAR_MODELS.length) {
            return EXTENDED_CAR_MODELS[index];
        }
        return "Unknown Car";
    }
    
    /**
     * Check if a car index is a custom car (beyond original 16)
     */
    public static boolean isCustomCar(int index) {
        return index >= CarConfig.CAR_MODELS.length && index < EXTENDED_CAR_MODELS.length;
    }
    
    /**
     * Get custom car index (0-based within custom cars)
     */
    public static int getCustomCarIndex(int globalIndex) {
        return globalIndex - CarConfig.CAR_MODELS.length;
    }
    
    private EnhancedCarConfig() {}
}
