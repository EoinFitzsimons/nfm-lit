package nfm.lit;

/**
 * Enhanced Stat Lists for 50 Cars
 * 
 * This class extends StatList to support 50 total cars with professionally
 * balanced statistics. Each new car has unique characteristics while
 * maintaining game balance.
 * 
 * @author GitHub Copilot
 */
public class EnhancedStatList {
    
    /**
     * Enhanced Acceleration stats for 50 cars
     * Original 16 + 34 new professionally balanced cars
     */
    public static final float[][] ENHANCED_ACELF = {
        // Original 16 cars
        {11.0F, 5.0F, 3.0F}, {14.0F, 7.0F, 5.0F}, {10.0F, 5.0F, 3.5F}, {11.0F, 6.0F, 3.5F},
        {10.0F, 5.0F, 3.5F}, {12.0F, 6.0F, 3.0F}, {7.0F, 9.0F, 4.0F}, {11.0F, 5.0F, 3.0F},
        {12.0F, 7.0F, 4.0F}, {12.0F, 7.0F, 3.5F}, {11.5F, 6.5F, 3.5F}, {9.0F, 5.0F, 3.0F},
        {13.0F, 7.0F, 4.5F}, {7.5F, 3.5F, 3.0F}, {11.0F, 7.5F, 4.0F}, {12.0F, 6.0F, 3.5F},
        
        // Speed demons (4 cars)
        {15.0F, 9.0F, 6.0F}, {16.0F, 10.0F, 6.5F}, {14.5F, 8.5F, 5.5F}, {15.5F, 9.5F, 6.2F},
        
        // Heavy tanks (4 cars)
        {6.0F, 4.0F, 2.5F}, {5.5F, 3.5F, 2.0F}, {6.5F, 4.5F, 3.0F}, {5.8F, 3.8F, 2.3F},
        
        // Balanced racers (4 cars)
        {11.5F, 6.0F, 3.8F}, {12.0F, 6.5F, 4.0F}, {11.8F, 6.2F, 3.9F}, {11.3F, 5.8F, 3.7F},
        
        // Agile drifters (4 cars)
        {10.5F, 7.0F, 4.5F}, {9.8F, 6.8F, 4.3F}, {10.2F, 7.2F, 4.7F}, {9.5F, 6.5F, 4.0F},
        
        // Stunt specialists (4 cars)
        {9.0F, 8.0F, 5.0F}, {8.5F, 7.5F, 4.8F}, {9.3F, 8.3F, 5.2F}, {8.8F, 7.8F, 4.9F},
        
        // Unique exotics (4 cars)
        {13.5F, 5.5F, 3.2F}, {12.8F, 8.0F, 4.2F}, {10.5F, 9.0F, 5.5F}, {14.2F, 6.8F, 3.8F},
        
        // Professional racers (6 cars)
        {13.2F, 7.2F, 4.3F}, {12.5F, 6.8F, 4.1F}, {13.8F, 7.8F, 4.6F}, {13.0F, 7.0F, 4.2F}, {14.5F, 8.0F, 5.0F}, {15.2F, 8.5F, 5.5F},
        
        // Elite cars (4 cars)
        {16.0F, 9.0F, 6.0F}, {14.8F, 8.2F, 5.2F}, {15.5F, 8.8F, 5.8F}, {15.0F, 8.5F, 5.5F}
    };
    
    /**
     * Enhanced Top Speed stats for 50 cars
     */
    public static final int[][] ENHANCED_SWITS = {
        // Original 16 cars
        {50, 185, 282}, {100, 200, 310}, {60, 180, 275}, {76, 195, 298},
        {70, 170, 275}, {70, 202, 293}, {60, 170, 289}, {70, 206, 291},
        {90, 210, 295}, {90, 190, 276}, {70, 200, 295}, {50, 160, 270},
        {90, 200, 305}, {50, 130, 210}, {80, 200, 300}, {70, 210, 290},
        
        // Speed demons (4 cars - ultra-high top speeds)
        {120, 230, 350}, {130, 240, 360}, {115, 225, 340}, {125, 235, 355},
        
        // Heavy tanks (4 cars - lower top speeds but good acceleration)
        {40, 140, 220}, {35, 130, 210}, {45, 150, 235}, {38, 135, 215},
        
        // Balanced racers (4 cars)
        {80, 195, 300}, {85, 200, 305}, {82, 198, 302}, {78, 192, 298},
        
        // Agile drifters (4 cars)
        {75, 185, 285}, {72, 182, 280}, {77, 188, 290}, {70, 180, 275},
        
        // Stunt specialists (4 cars)
        {65, 175, 270}, {62, 172, 265}, {68, 178, 275}, {64, 174, 268},
        
        // Unique exotics (4 cars)
        {95, 215, 320}, {88, 208, 315}, {75, 190, 285}, {105, 220, 330},
        
        // Professional racers (6 cars)
        {92, 212, 318}, {87, 207, 312}, {98, 218, 325}, {90, 210, 315}, {110, 235, 340}, {120, 245, 360},
        
        // Elite cars (4 cars)
        {125, 250, 370}, {115, 240, 350}, {118, 242, 355}, {122, 248, 365}
    };
    
    /**
     * Enhanced Handbrake power for 50 cars
     */
    public static final int[] ENHANCED_HANDB = {
        // Original 16 cars
        7, 10, 7, 15, 12, 8, 9, 10, 5, 7, 8, 10, 8, 12, 7, 7,
        
        // Speed demons (4 cars - lower handbrake for speed focus)
        5, 4, 6, 5,
        
        // Heavy tanks (4 cars - higher handbrake power)
        15, 18, 14, 16,
        
        // Balanced racers (4 cars)
        9, 10, 9, 8,
        
        // Agile drifters (4 cars - excellent handbrake for drifting)
        12, 13, 11, 14,
        
        // Stunt specialists (4 cars)
        8, 9, 7, 8,
        
        // Unique exotics (4 cars)
        6, 11, 13, 5,
        
        // Professional racers (6 cars)
        9, 10, 8, 9, 7, 8,
        
        // Elite cars (4 cars)
        6, 9, 7, 8
    };
    
    /**
     * Enhanced Aerial rotation for 50 cars
     */
    public static final float[] ENHANCED_AIRS = {
        // Original 16 cars
        1.0F, 1.2F, 0.95F, 1.0F, 2.2F, 1.0F, 0.9F, 0.8F, 1.0F, 0.9F, 1.15F, 0.8F, 1.0F, 0.3F, 1.3F, 1.0F,
        
        // Speed demons (moderate aerial control)
        0.8F, 0.7F, 0.9F, 0.8F, 0.85F,
        
        // Heavy tanks (low aerial rotation)
        0.4F, 0.3F, 0.5F, 0.35F, 0.45F,
        
        // Balanced racers
        1.1F, 1.15F, 1.05F, 1.0F, 1.1F,
        
        // Agile drifters (high aerial control)
        1.5F, 1.6F, 1.4F, 1.7F, 1.45F,
        
        // Stunt specialists (excellent aerial rotation)
        2.0F, 2.2F, 1.9F, 2.1F, 2.0F,
        
        // Unique exotics (variable aerial control)
        1.3F, 0.9F, 1.8F, 0.6F, 1.4F,
        
        // Professional racers
        1.2F, 1.25F, 1.1F, 1.2F,
        
        // Final 5 elite cars
        1.1F, 1.0F, 0.9F, 1.2F, 1.05F
    };
    
    /**
     * Enhanced Aerial control for 50 cars
     */
    public static final int[] ENHANCED_AIRC = {
        // Original 16 cars
        70, 30, 40, 40, 30, 50, 40, 90, 40, 50, 75, 10, 50, 0, 100, 60,
        
        // Speed demons
        35, 30, 40, 35, 38,
        
        // Heavy tanks
        20, 15, 25, 18, 22,
        
        // Balanced racers
        65, 70, 68, 62, 67,
        
        // Agile drifters
        85, 90, 80, 95, 85,
        
        // Stunt specialists (maximum aerial control)
        120, 130, 115, 125, 120,
        
        // Unique exotics
        55, 75, 110, 45, 80,
        
        // Professional racers
        72, 75, 68, 70,
        
        // Final 5 elite cars
        65, 70, 75, 68, 72
    };
    
    /**
     * Enhanced Turning responsiveness for 50 cars
     */
    public static final int[] ENHANCED_TURN = {
        // Original 16 cars
        6, 9, 5, 7, 8, 7, 5, 5, 9, 7, 7, 4, 6, 5, 7, 6,
        
        // Speed demons (lower turning for stability)
        4, 3, 5, 4, 4,
        
        // Heavy tanks (low turning)
        3, 2, 4, 3, 3,
        
        // Balanced racers
        7, 8, 7, 6, 7,
        
        // Agile drifters (excellent turning)
        10, 11, 9, 12, 10,
        
        // Stunt specialists
        8, 9, 7, 8, 8,
        
        // Unique exotics
        6, 8, 9, 5, 7,
        
        // Professional racers
        8, 9, 7, 8,
        
        // Final 5 elite cars
        7, 8, 6, 9, 7
    };
    
    /**
     * Enhanced Grip for 50 cars
     */
    public static final float[] ENHANCED_GRIP = {
        // Original 16 cars
        20.0F, 27.0F, 18.0F, 22.0F, 19.0F, 20.0F, 25.0F, 20.0F, 19.0F, 24.0F, 22.5F, 25.0F, 30.0F, 27.0F, 25.0F, 27.0F,
        
        // Speed demons (moderate grip)
        22.0F, 20.0F, 24.0F, 21.0F, 23.0F,
        
        // Heavy tanks (high grip)
        32.0F, 35.0F, 30.0F, 33.0F, 31.0F,
        
        // Balanced racers
        25.0F, 26.0F, 25.5F, 24.5F, 25.2F,
        
        // Agile drifters (lower grip for drifting)
        16.0F, 15.0F, 17.0F, 14.0F, 16.5F,
        
        // Stunt specialists
        21.0F, 22.0F, 20.0F, 21.5F, 20.5F,
        
        // Unique exotics
        28.0F, 19.0F, 23.0F, 26.0F, 24.0F,
        
        // Professional racers
        27.0F, 28.0F, 26.0F, 27.5F,
        
        // Final 5 elite cars
        26.0F, 28.0F, 29.0F, 27.0F, 27.5F
    };
    
    /**
     * Enhanced Bounce for 50 cars
     */
    public static final float[] ENHANCED_BOUNCE = {
        // Original 16 cars
        1.2F, 1.05F, 1.3F, 1.15F, 1.3F, 1.2F, 1.15F, 1.1F, 1.2F, 1.1F, 1.15F, 0.8F, 1.05F, 0.8F, 1.1F, 1.15F,
        
        // Speed demons
        1.0F, 0.95F, 1.05F, 1.0F, 1.02F,
        
        // Heavy tanks (low bounce)
        0.7F, 0.6F, 0.75F, 0.65F, 0.7F,
        
        // Balanced racers
        1.15F, 1.2F, 1.18F, 1.12F, 1.16F,
        
        // Agile drifters
        1.25F, 1.3F, 1.22F, 1.35F, 1.28F,
        
        // Stunt specialists (high bounce for tricks)
        1.4F, 1.5F, 1.35F, 1.45F, 1.4F,
        
        // Unique exotics
        1.1F, 1.25F, 1.6F, 0.9F, 1.3F,
        
        // Professional racers
        1.08F, 1.12F, 1.06F, 1.1F,
        
        // Final 5 elite cars
        1.05F, 1.0F, 0.95F, 1.08F, 1.02F
    };
    
    /**
     * Enhanced track tolerance for 50 cars
     */
    public static final float[] ENHANCED_SIMAG = {
        // Original 16 cars
        0.9F, 0.85F, 1.05F, 0.9F, 0.85F, 0.9F, 1.05F, 0.9F, 1.0F, 1.05F, 0.9F, 1.1F, 0.9F, 1.3F, 0.9F, 1.15F,
        
        // Speed demons (good track tolerance)
        0.8F, 0.75F, 0.85F, 0.8F, 0.82F,
        
        // Heavy tanks (excellent track tolerance)
        1.2F, 1.3F, 1.15F, 1.25F, 1.2F,
        
        // Balanced racers
        0.95F, 0.92F, 0.94F, 0.96F, 0.93F,
        
        // Agile drifters
        0.88F, 0.85F, 0.9F, 0.83F, 0.87F,
        
        // Stunt specialists
        1.0F, 1.05F, 0.98F, 1.02F, 1.0F,
        
        // Unique exotics
        0.92F, 1.08F, 0.87F, 0.95F, 1.0F,
        
        // Professional racers
        0.87F, 0.89F, 0.85F, 0.88F
    };
    
    /**
     * Enhanced car strength for 50 cars
     */
    public static final float[] ENHANCED_MOMENT = {
        // Original 16 cars
        1.3F, 0.75F, 1.4F, 1.2F, 1.1F, 1.38F, 1.43F, 1.48F, 1.35F, 1.7F, 1.42F, 2.0F, 1.26F, 3.0F, 1.5F, 2.0F,
        
        // Speed demons (low strength for speed)
        0.8F, 0.7F, 0.85F, 0.75F, 0.82F,
        
        // Heavy tanks (maximum strength)
        3.5F, 4.0F, 3.2F, 3.8F, 3.5F,
        
        // Balanced racers
        1.5F, 1.6F, 1.55F, 1.45F, 1.52F,
        
        // Agile drifters
        1.1F, 1.0F, 1.15F, 0.95F, 1.05F,
        
        // Stunt specialists
        1.3F, 1.4F, 1.25F, 1.35F, 1.3F,
        
        // Unique exotics
        1.8F, 1.2F, 1.6F, 2.2F, 1.4F,
        
        // Professional racers
        1.65F, 1.7F, 1.6F, 1.68F
    };
    
    /**
     * Enhanced collision radius for other cars for 50 cars
     */
    public static final float[] ENHANCED_COMPRAD = {
        // Original 16 cars
        0.5F, 0.4F, 0.8F, 0.5F, 0.4F, 0.5F, 0.5F, 0.5F, 0.5F, 0.8F, 0.5F, 1.5F, 0.5F, 0.8F, 0.5F, 0.8F,
        
        // Speed demons
        0.4F, 0.35F, 0.45F, 0.4F, 0.42F,
        
        // Heavy tanks (larger collision radius)
        1.2F, 1.5F, 1.0F, 1.3F, 1.2F,
        
        // Balanced racers
        0.6F, 0.65F, 0.62F, 0.58F, 0.6F,
        
        // Agile drifters
        0.45F, 0.4F, 0.5F, 0.38F, 0.47F,
        
        // Stunt specialists
        0.55F, 0.6F, 0.52F, 0.58F, 0.55F,
        
        // Unique exotics
        0.7F, 0.5F, 0.9F, 0.6F, 0.65F,
        
        // Professional racers
        0.52F, 0.55F, 0.5F, 0.53F
    };
    
    /**
     * Enhanced push power for 50 cars
     */
    public static final int[] ENHANCED_PUSH = {
        // Original 16 cars
        2, 2, 3, 3, 2, 2, 2, 4, 2, 2, 2, 4, 2, 2, 2, 2,
        
        // Speed demons (low push)
        1, 1, 2, 1, 1,
        
        // Heavy tanks (maximum push)
        6, 7, 5, 6, 6,
        
        // Balanced racers
        3, 3, 3, 3, 3,
        
        // Agile drifters
        2, 2, 2, 2, 2,
        
        // Stunt specialists
        2, 3, 2, 2, 2,
        
        // Unique exotics
        4, 2, 3, 5, 3,
        
        // Professional racers
        3, 3, 3, 3
    };
    
    /**
     * Enhanced reverse push for 50 cars
     */
    public static final int[] ENHANCED_REVPUSH = {
        // Original 16 cars
        2, 3, 2, 2, 2, 2, 2, 1, 2, 1, 2, 1, 2, 2, 2, 1,
        
        // Speed demons (high reverse push - lightweight)
        3, 4, 3, 3, 3,
        
        // Heavy tanks (low reverse push)
        1, 1, 1, 1, 1,
        
        // Balanced racers
        2, 2, 2, 2, 2,
        
        // Agile drifters
        2, 3, 2, 3, 2,
        
        // Stunt specialists
        2, 2, 2, 2, 2,
        
        // Unique exotics
        1, 2, 2, 1, 2,
        
        // Professional racers
        2, 2, 2, 2
    };
    
    /**
     * Enhanced lift power for 50 cars
     */
    public static final int[] ENHANCED_LIFT = {
        // Original 16 cars
        0, 30, 0, 20, 0, 30, 0, 0, 20, 0, 0, 0, 10, 0, 30, 0,
        
        // Speed demons
        10, 5, 15, 10, 12,
        
        // Heavy tanks (high lift power)
        40, 50, 35, 45, 40,
        
        // Balanced racers
        20, 25, 22, 18, 20,
        
        // Agile drifters
        15, 10, 20, 8, 15,
        
        // Stunt specialists
        25, 30, 22, 28, 25,
        
        // Unique exotics
        35, 15, 40, 45, 20,
        
        // Professional racers
        22, 25, 20, 23
    };
    
    /**
     * Enhanced reverse lift for 50 cars
     */
    public static final int[] ENHANCED_REVLIFT = {
        // Original 16 cars
        0, 0, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 32,
        
        // Speed demons (low reverse lift)
        5, 8, 3, 5, 4,
        
        // Heavy tanks (very low reverse lift)
        0, 0, 2, 0, 1,
        
        // Balanced racers
        8, 10, 9, 7, 8,
        
        // Agile drifters
        12, 15, 10, 18, 12,
        
        // Stunt specialists
        6, 8, 5, 7, 6,
        
        // Unique exotics
        2, 12, 5, 0, 10,
        
        // Professional racers
        8, 6, 10, 7
    };
    
    /**
     * Enhanced power loss for 50 cars
     */
    public static final int[] ENHANCED_POWERLOSS = {
        // Original 16 cars
        2500000, 2500000, 3500000, 2500000, 4000000, 2500000, 3200000, 3200000, 2750000, 5500000, 2750000, 
        4500000, 3500000, 16700000, 3000000, 5500000,
        
        // Speed demons (faster power loss)
        2000000, 1800000, 2200000, 2000000, 2100000,
        
        // Heavy tanks (slower power loss)
        8000000, 10000000, 7500000, 9000000, 8500000,
        
        // Balanced racers
        3000000, 3200000, 3100000, 2900000, 3000000,
        
        // Agile drifters
        2800000, 2600000, 3000000, 2500000, 2700000,
        
        // Stunt specialists
        3500000, 3800000, 3300000, 3600000, 3500000,
        
        // Unique exotics
        4000000, 2800000, 5000000, 6000000, 3500000,
        
        // Professional racers
        3200000, 3400000, 3000000, 3300000
    };
    
    /**
     * Enhanced flip Y for 50 cars
     */
    public static final int[] ENHANCED_FLIPY = {
        // Original 16 cars
        -50, -60, -92, -44, -60, -57, -54, -60, -77, -57, -82, -85, -28, -100, -63, -127,
        
        // Speed demons
        -45, -40, -50, -45, -47,
        
        // Heavy tanks (lower flip Y)
        -120, -140, -110, -130, -125,
        
        // Balanced racers
        -65, -70, -68, -62, -66,
        
        // Agile drifters
        -55, -50, -60, -48, -57,
        
        // Stunt specialists
        -75, -80, -72, -78, -75,
        
        // Unique exotics
        -90, -55, -105, -95, -70,
        
        // Professional racers
        -68, -72, -65, -70
    };
    
    /**
     * Enhanced maximum squash for 50 cars
     */
    public static final int[] ENHANCED_MSQUASH = {
        // Original 16 cars
        7, 4, 7, 2, 8, 4, 6, 4, 3, 8, 4, 10, 3, 20, 3, 8,
        
        // Speed demons (low squash resistance)
        3, 2, 4, 3, 3,
        
        // Heavy tanks (high squash resistance)
        15, 18, 12, 16, 15,
        
        // Balanced racers
        6, 7, 6, 5, 6,
        
        // Agile drifters
        4, 3, 5, 2, 4,
        
        // Stunt specialists
        5, 6, 4, 5, 5,
        
        // Unique exotics
        9, 5, 12, 11, 6,
        
        // Professional racers
        6, 7, 5, 6
    };
    
    /**
     * Enhanced collision radius for 50 cars
     */
    public static final int[] ENHANCED_CLRAD = {
        // Original 16 cars
        3300, 1700, 4700, 3000, 2000, 4500, 3500, 5000, 10000, 15000, 4000, 7000, 10000, 15000, 5500, 5000,
        
        // Speed demons (smaller collision radius)
        2500, 2000, 2800, 2500, 2600,
        
        // Heavy tanks (larger collision radius)
        8000, 10000, 7500, 9000, 8500,
        
        // Balanced racers
        4000, 4200, 4100, 3900, 4000,
        
        // Agile drifters (smaller for agility)
        2800, 2500, 3000, 2200, 2700,
        
        // Stunt specialists
        3500, 3800, 3300, 3600, 3500,
        
        // Unique exotics
        6000, 3200, 7500, 8000, 4500,
        
        // Professional racers
        4200, 4500, 4000, 4300
    };
    
    /**
     * Enhanced damage multiplier for 50 cars
     */
    public static final float[] ENHANCED_DAMMULT = {
        // Original 16 cars
        0.75F, 0.8F, 0.45F, 0.8F, 0.42F, 0.7F, 0.72F, 0.6F, 0.58F, 0.41F, 0.67F, 0.45F, 0.61F, 0.25F, 0.38F, 0.52F,
        
        // Speed demons (higher damage multiplier - fragile)
        0.85F, 0.9F, 0.8F, 0.85F, 0.82F,
        
        // Heavy tanks (lower damage multiplier)
        0.2F, 0.15F, 0.25F, 0.18F, 0.22F,
        
        // Balanced racers
        0.6F, 0.65F, 0.62F, 0.58F, 0.6F,
        
        // Agile drifters
        0.7F, 0.75F, 0.68F, 0.78F, 0.72F,
        
        // Stunt specialists
        0.55F, 0.5F, 0.58F, 0.52F, 0.55F,
        
        // Unique exotics
        0.4F, 0.68F, 0.35F, 0.3F, 0.65F,
        
        // Professional racers
        0.58F, 0.55F, 0.62F, 0.57F
    };
    
    /**
     * Enhanced maximum magnitude for 50 cars
     */
    public static final int[] ENHANCED_MAXMAG = {
        // Original 16 cars
        7600, 4200, 7200, 6000, 6000, 15000, 17200, 17000, 18000, 11000, 19000, 10700, 13000, 45000, 5800, 18000,
        
        // Speed demons (lower durability)
        5000, 4500, 5500, 5000, 5200,
        
        // Heavy tanks (maximum durability)
        30000, 35000, 28000, 32000, 30000,
        
        // Balanced racers
        12000, 13000, 12500, 11500, 12200,
        
        // Agile drifters
        8000, 7500, 8500, 7000, 8000,
        
        // Stunt specialists
        10000, 11000, 9500, 10500, 10000,
        
        // Unique exotics
        20000, 9000, 25000, 22000, 11000,
        
        // Professional racers
        14000, 15000, 13000, 14500
    };
    
    /**
     * Enhanced disable handle for 50 cars
     */
    public static final float[] ENHANCED_DISHANDLE = {
        // Original 16 cars
        0.65F, 0.6F, 0.55F, 0.77F, 0.62F, 0.9F, 0.6F, 0.72F, 0.45F, 0.8F, 0.95F, 0.4F, 0.87F, 0.42F, 1.0F, 0.95F,
        
        // Speed demons
        0.5F, 0.45F, 0.55F, 0.5F, 0.52F,
        
        // Heavy tanks
        0.85F, 0.9F, 0.8F, 0.88F, 0.85F,
        
        // Balanced racers
        0.7F, 0.75F, 0.72F, 0.68F, 0.7F,
        
        // Agile drifters
        0.4F, 0.35F, 0.45F, 0.3F, 0.42F,
        
        // Stunt specialists
        0.6F, 0.65F, 0.58F, 0.62F, 0.6F,
        
        // Unique exotics
        0.8F, 0.55F, 0.9F, 0.95F, 0.65F,
        
        // Professional racers
        0.72F, 0.75F, 0.7F, 0.73F
    };
    
    /**
     * Enhanced output damage for 50 cars
     */
    public static final float[] ENHANCED_OUTDAM = {
        // Original 16 cars
        0.67F, 0.35F, 0.8F, 0.5F, 0.42F, 0.76F, 0.82F, 0.76F, 0.72F, 0.62F, 0.79F, 0.95F, 0.77F, 1.0F, 0.85F, 1.0F,
        
        // Speed demons
        0.3F, 0.25F, 0.35F, 0.3F, 0.32F,
        
        // Heavy tanks
        0.9F, 1.0F, 0.85F, 0.95F, 0.9F,
        
        // Balanced racers
        0.65F, 0.7F, 0.68F, 0.62F, 0.65F,
        
        // Agile drifters
        0.45F, 0.4F, 0.5F, 0.38F, 0.47F,
        
        // Stunt specialists
        0.55F, 0.6F, 0.52F, 0.58F, 0.55F,
        
        // Unique exotics
        0.8F, 0.5F, 0.9F, 0.85F, 0.6F,
        
        // Professional racers
        0.7F, 0.75F, 0.68F, 0.72F
    };
    
    /**
     * Enhanced engine for 50 cars
     */
    public static final int[] ENHANCED_ENGINE = {
        // Original 16 cars
        0, 1, 2, 1, 0, 3, 2, 2, 1, 0, 3, 4, 1, 4, 0, 3,
        
        // Speed demons (high-end engines)
        4, 4, 4, 4, 4,
        
        // Heavy tanks (powerful engines)
        3, 4, 3, 3, 3,
        
        // Balanced racers (mid-range engines)
        2, 2, 2, 2, 2,
        
        // Agile drifters (light engines)
        1, 1, 1, 1, 1,
        
        // Stunt specialists (specialized engines)
        2, 2, 2, 2, 2,
        
        // Unique exotics (varied engines)
        3, 1, 4, 4, 2,
        
        // Professional racers (premium engines)
        3, 3, 3, 3
    };
}
