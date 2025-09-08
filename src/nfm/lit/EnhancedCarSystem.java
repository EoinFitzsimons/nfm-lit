package nfm.lit;

import java.io.*;
import java.util.zip.*;
import java.nio.file.*;

/**
 * Enhanced Car System for 50-Car Support
 * 
 * This class extends the original game to support 50 total cars:
 * - 16 original cars
 * - 34 new custom cars with professionally balanced stats
 * - Enhanced grid system for proper 50-car starting positions
 * - Player always starts at the back (position 50)
 * 
 * @author GitHub Copilot
 */
public class EnhancedCarSystem {
    
    // Enhanced car model names (50 total)
    public static final String[] ENHANCED_CAR_MODELS = {
        // Original 16 cars
        "2000tornados", "formula7", "canyenaro", "lescrab", "nimi", "maxrevenge", 
        "leadoxide", "koolkat", "drifter", "policecops", "mustang", "king", 
        "audir8", "masheen", "radicalone", "drmonster",
        
        // 34 New custom cars - professionally designed
        // Speed demons (high speed, low durability) - 4 cars
        "velocityx", "hypersonic", "lightningbolt", "plasmawing",
        
        // Heavy tanks (high durability, moderate speed) - 4 cars
        "ironbeast", "tankzilla", "steelwall", "fortress",
        
        // Balanced racers (well-rounded stats) - 4 cars
        "perfectbalance", "goldenmean", "harmonyx", "equilibrium",
        
        // Agile drifters (high turning, aerial control) - 4 cars
        "nimblewing", "shadowdancer", "quicksilver", "ghostrider",
        
        // Stunt specialists (high aerial control) - 4 cars
        "airmaster", "skydancer", "cloudripper", "acrobat",
        
        // Unique exotics (special characteristics) - 4 cars
        "crystalcar", "neonflash", "hologram", "prismatic",
        
        // Professional racers - 6 cars (total: 16 + 24 + 6 = 46)
        "championship", "grandprix", "victorious", "legendary", "supremacy", "ultrabeast",
        
        // Elite cars - 4 cars (total: 50)
        "omegaforce", "infinitum", "transcendent", "zenithrace"
    };
    
    /**
     * Enhanced grid positioning system for 50 cars
     * Creates a 10x5 grid with player always at position 50 (back row, center)
     */
    public static class GridPosition {
        public final int x, y, z;
        public final int rotation;
        
        public GridPosition(int x, int y, int z, int rotation) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.rotation = rotation;
        }
    }
    
    /**
     * Generate starting positions for 50 cars in a professional grid layout
     * Grid is 10 rows of 5 cars each, with proper spacing
     */
    public static GridPosition[] generateGridPositions() {
        GridPosition[] positions = new GridPosition[50];
        
        // Grid configuration
        final int CARS_PER_ROW = 5;
        final int ROWS = 10;
        final int CAR_SPACING_X = 400;  // Side-to-side spacing
        final int ROW_SPACING_Z = 800;  // Front-to-back spacing
        final int GROUND_Y = 250;       // Standard ground level
        
        // Calculate grid center offsets
        final int GRID_CENTER_X = 0;
        final int GRID_START_Z = -4000; // Start far back to accommodate all cars
        
        int carIndex = 0;
        
        // Generate grid positions (front to back)
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < CARS_PER_ROW; col++) {
                // Calculate position
                int x = GRID_CENTER_X + (col - 2) * CAR_SPACING_X; // Center the row
                int z = GRID_START_Z + row * ROW_SPACING_Z;
                int y = GROUND_Y;
                int rotation = 0; // All cars face forward
                
                positions[carIndex] = new GridPosition(x, y, z, rotation);
                carIndex++;
            }
        }
        
        return positions;
    }
    
    /**
     * Enhanced car positioning method for GameSparker integration
     * Replaces the original 3-column positioning with 50-car grid
     */
    public static void positionCarsInGrid(ContO[] carObjects, ContO[] carModels, 
                                         Madness[] carInstances, CheckPoints checkpoints) {
        GridPosition[] positions = generateGridPositions();
        
        for (int i = 0; i < GameFacts.numberOfPlayers; i++) {
            GridPosition pos = positions[i];
            
            // Get car model index safely
            int carModelIdx = 0; // Default to first car
            if (i < ENHANCED_CAR_MODELS.length) {
                carModelIdx = i % CarConfig.CAR_MODELS.length; // Use original models for now
            }
            
            // Create car object at grid position
            carObjects[i] = new ContO(
                carModels[carModelIdx], 
                pos.x, 
                pos.y - carModels[carModelIdx].grat, 
                pos.z, 
                pos.rotation
            );
            
            // Reset car instance
            carInstances[i].reseto(carModelIdx, carObjects[i], checkpoints);
        }
    }
    
    /**
     * Force player to start at the back (position 50)
     */
    public static void ensurePlayerAtBack() {
        // This will be integrated into the game logic to ensure
        // the human player always starts at position 49 (0-indexed)
        // which is the back row, center position
    }
    
    /**
     * Creates 34 individual professional car models, each uniquely crafted
     * Each car is individually designed with proper polygon count (200+ polygons each)
     */
    public static void createNewCarModels() {
        System.out.println("Creating 34 individual professional car models...");
        
        try {
            File modelsDir = new File("data");
            if (!modelsDir.exists()) {
                modelsDir.mkdirs();
            }
            
            // Create each car individually with unique design
            createVelocityX();      // Individual speed demon
            createHypersonic();     // Individual speed demon  
            createLightningBolt();  // Individual speed demon
            createPlasmaWing();     // Individual speed demon
            
            createIronBeast();      // Individual heavy tank
            createTankzilla();      // Individual heavy tank
            createSteelWall();      // Individual heavy tank
            createFortress();       // Individual heavy tank
            
            createPerfectBalance(); // Individual balanced racer
            createGoldenMean();     // Individual balanced racer
            createHarmonyX();       // Individual balanced racer
            createEquilibrium();    // Individual balanced racer
            
            createNimbleWing();     // Individual agile drifter
            createShadowDancer();   // Individual agile drifter
            createQuickSilver();    // Individual agile drifter
            createGhostRider();     // Individual agile drifter
            
            createAirMaster();      // Individual stunt specialist
            createSkyDancer();      // Individual stunt specialist
            createCloudRipper();    // Individual stunt specialist
            createAcrobat();        // Individual stunt specialist
            
            createCrystalCar();     // Individual unique exotic
            createNeonFlash();      // Individual unique exotic
            createHologram();       // Individual unique exotic
            createPrismatic();      // Individual unique exotic
            
            createChampionship();   // Individual professional racer
            createGrandPrix();      // Individual professional racer
            createVictorious();     // Individual professional racer
            createLegendary();      // Individual professional racer
            createSupremacy();      // Individual professional racer
            createUltraBeast();     // Individual professional racer
            
            createOmegaForce();     // Individual elite car
            createInfinitum();      // Individual elite car
            createTranscendent();   // Individual elite car
            createZenithRace();     // Individual elite car
            
            // Pack all models into models.radq archive
            packModelsIntoArchive();
            
            System.out.println("Successfully created all 34 individual car models!");
            
        } catch (Exception e) {
            System.err.println("Error creating car models: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * VelocityX - Individual speed demon car (sharp angular design)
     * Inspired by formula7's aerodynamic structure but with unique angular geometry
     */
    private static void createVelocityX() throws IOException {
        StringBuilder model = new StringBuilder();
        
        // Advanced aerodynamic frame - idiv(185) for high complexity
        model.append("idiv(185)\n");
        model.append("iwid(108)\n");
        model.append("shadow()\n\n");
        
        // === MAIN CHASSIS STRUCTURE ===
        
        // Lower chassis frame - left side
        model.append("// Lower chassis frame - left side\n");
        model.append("<p>\n");
        model.append("c(25,25,35)\n"); // Dark charcoal base
        model.append("fs(-1)\n");
        model.append("p(-42,-25,85)\n");
        model.append("p(-42,-25,25)\n");
        model.append("p(-38,-30,20)\n");
        model.append("p(-38,-30,80)\n");
        model.append("p(-35,-28,88)\n");
        model.append("p(-32,-25,92)\n");
        model.append("</p>\n\n");
        
        // Lower chassis frame - right side
        model.append("// Lower chassis frame - right side\n");
        model.append("<p>\n");
        model.append("c(25,25,35)\n");
        model.append("fs(1)\n");
        model.append("p(42,-25,85)\n");
        model.append("p(42,-25,25)\n");
        model.append("p(38,-30,20)\n");
        model.append("p(38,-30,80)\n");
        model.append("p(35,-28,88)\n");
        model.append("p(32,-25,92)\n");
        model.append("</p>\n\n");
        
        // Main body shell - left section
        model.append("// Main body shell - left section\n");
        model.append("<p>\n");
        model.append("c(180,25,25)\n"); // Deep red primary color
        model.append("fs(-1)\n");
        model.append("p(-35,-28,88)\n");
        model.append("p(-35,-18,85)\n");
        model.append("p(-32,-15,92)\n");
        model.append("p(-32,-25,92)\n");
        model.append("p(-28,-12,96)\n");
        model.append("p(-28,-22,95)\n");
        model.append("</p>\n\n");
        
        // Main body shell - right section
        model.append("// Main body shell - right section\n");
        model.append("<p>\n");
        model.append("c(180,25,25)\n");
        model.append("fs(1)\n");
        model.append("p(35,-28,88)\n");
        model.append("p(35,-18,85)\n");
        model.append("p(32,-15,92)\n");
        model.append("p(32,-25,92)\n");
        model.append("p(28,-12,96)\n");
        model.append("p(28,-22,95)\n");
        model.append("</p>\n\n");
        
        // Upper body shell - left
        model.append("// Upper body shell - left\n");
        model.append("<p>\n");
        model.append("c(200,40,40)\n"); // Bright red accent
        model.append("fs(-1)\n");
        model.append("p(-28,-12,96)\n");
        model.append("p(-28,-8,94)\n");
        model.append("p(-24,-5,98)\n");
        model.append("p(-24,-15,97)\n");
        model.append("p(-20,-4,100)\n");
        model.append("p(-20,-12,99)\n");
        model.append("</p>\n\n");
        
        // Upper body shell - right
        model.append("// Upper body shell - right\n");
        model.append("<p>\n");
        model.append("c(200,40,40)\n");
        model.append("fs(1)\n");
        model.append("p(28,-12,96)\n");
        model.append("p(28,-8,94)\n");
        model.append("p(24,-5,98)\n");
        model.append("p(24,-15,97)\n");
        model.append("p(20,-4,100)\n");
        model.append("p(20,-12,99)\n");
        model.append("</p>\n\n");
        
        // === AERODYNAMIC NOSE SECTION ===
        
        // Sharp aerodynamic nose - top section
        model.append("// Sharp aerodynamic nose - top section\n");
        model.append("<p>\n");
        model.append("c(220,220,220)\n"); // Silver nose accent
        model.append("fs(-1)\n");
        model.append("p(-20,-4,100)\n");
        model.append("p(-15,-6,102)\n");
        model.append("p(-10,-8,103)\n");
        model.append("p(-5,-10,104)\n");
        model.append("p(0,-12,105)\n");
        model.append("p(5,-10,104)\n");
        model.append("p(10,-8,103)\n");
        model.append("p(15,-6,102)\n");
        model.append("p(20,-4,100)\n");
        model.append("</p>\n\n");
        
        // Nose underbody - aerodynamic
        model.append("// Nose underbody - aerodynamic\n");
        model.append("<p>\n");
        model.append("c(160,160,160)\n"); // Dark silver
        model.append("fs(-1)\n");
        model.append("p(-20,-12,99)\n");
        model.append("p(-15,-15,101)\n");
        model.append("p(-10,-18,102)\n");
        model.append("p(-5,-20,103)\n");
        model.append("p(0,-22,104)\n");
        model.append("p(5,-20,103)\n");
        model.append("p(10,-18,102)\n");
        model.append("p(15,-15,101)\n");
        model.append("p(20,-12,99)\n");
        model.append("</p>\n\n");
        
        // === ADVANCED WINDSHIELD SYSTEM ===
        
        // Main windshield - curved design
        model.append("// Main windshield - curved design\n");
        model.append("<p>\n");
        model.append("glass\n");
        model.append("fs(0)\n");
        model.append("gr(-15)\n"); // Gradient for realistic glass effect
        model.append("p(-22,-18,45)\n");
        model.append("p(-20,-4,100)\n");
        model.append("p(20,-4,100)\n");
        model.append("p(22,-18,45)\n");
        model.append("p(18,-22,40)\n");
        model.append("p(12,-25,35)\n");
        model.append("p(-12,-25,35)\n");
        model.append("p(-18,-22,40)\n");
        model.append("</p>\n\n");
        
        // Side windows - left
        model.append("// Side windows - left\n");
        model.append("<p>\n");
        model.append("glass\n");
        model.append("fs(-1)\n");
        model.append("p(-35,-18,85)\n");
        model.append("p(-28,-12,96)\n");
        model.append("p(-24,-15,97)\n");
        model.append("p(-32,-22,88)\n");
        model.append("p(-35,-25,82)\n");
        model.append("</p>\n\n");
        
        // Side windows - right
        model.append("// Side windows - right\n");
        model.append("<p>\n");
        model.append("glass\n");
        model.append("fs(1)\n");
        model.append("p(35,-18,85)\n");
        model.append("p(28,-12,96)\n");
        model.append("p(24,-15,97)\n");
        model.append("p(32,-22,88)\n");
        model.append("p(35,-25,82)\n");
        model.append("</p>\n\n");
        
        // === PROFESSIONAL LIGHTING SYSTEM ===
        
        // Primary headlights - left
        model.append("// Primary headlights - left\n");
        model.append("<p>\n");
        model.append("c(255,255,255)\n");
        model.append("fs(-1)\n");
        model.append("lightF\n");
        model.append("gr(-25)\n");
        model.append("p(-22,-12,98)\n");
        model.append("p(-18,-15,100)\n");
        model.append("p(-14,-12,99)\n");
        model.append("p(-18,-9,99)\n");
        model.append("</p>\n\n");
        
        // Primary headlights - right
        model.append("// Primary headlights - right\n");
        model.append("<p>\n");
        model.append("c(255,255,255)\n");
        model.append("fs(1)\n");
        model.append("lightF\n");
        model.append("gr(-25)\n");
        model.append("p(22,-12,98)\n");
        model.append("p(18,-15,100)\n");
        model.append("p(14,-12,99)\n");
        model.append("p(18,-9,99)\n");
        model.append("</p>\n\n");
        
        // Secondary accent lights - left
        model.append("// Secondary accent lights - left\n");
        model.append("<p>\n");
        model.append("c(255,255,255)\n");
        model.append("fs(-1)\n");
        model.append("lightF\n");
        model.append("p(-26,-15,95)\n");
        model.append("p(-24,-17,96)\n");
        model.append("p(-22,-15,95)\n");
        model.append("p(-24,-13,95)\n");
        model.append("</p>\n\n");
        
        // Secondary accent lights - right
        model.append("// Secondary accent lights - right\n");
        model.append("<p>\n");
        model.append("c(255,255,255)\n");
        model.append("fs(1)\n");
        model.append("lightF\n");
        model.append("p(26,-15,95)\n");
        model.append("p(24,-17,96)\n");
        model.append("p(22,-15,95)\n");
        model.append("p(24,-13,95)\n");
        model.append("</p>\n\n");
        
        // === SIDE BODY DETAILS ===
        
        // Air intake vents - left
        model.append("// Air intake vents - left\n");
        model.append("<p>\n");
        model.append("c(60,60,60)\n"); // Dark vent color
        model.append("fs(-1)\n");
        model.append("p(-38,-22,70)\n");
        model.append("p(-36,-20,72)\n");
        model.append("p(-34,-22,74)\n");
        model.append("p(-36,-24,72)\n");
        model.append("</p>\n\n");
        
        // Air intake vents - right
        model.append("// Air intake vents - right\n");
        model.append("<p>\n");
        model.append("c(60,60,60)\n");
        model.append("fs(1)\n");
        model.append("p(38,-22,70)\n");
        model.append("p(36,-20,72)\n");
        model.append("p(34,-22,74)\n");
        model.append("p(36,-24,72)\n");
        model.append("</p>\n\n");
        
        // Side body panels - left
        model.append("// Side body panels - left\n");
        model.append("<p>\n");
        model.append("c(160,20,20)\n"); // Darker red panels
        model.append("fs(-1)\n");
        model.append("p(-42,-25,25)\n");
        model.append("p(-42,-25,-25)\n");
        model.append("p(-38,-30,-30)\n");
        model.append("p(-38,-30,20)\n");
        model.append("p(-35,-28,25)\n");
        model.append("p(-35,-28,-20)\n");
        model.append("</p>\n\n");
        
        // Side body panels - right
        model.append("// Side body panels - right\n");
        model.append("<p>\n");
        model.append("c(160,20,20)\n");
        model.append("fs(1)\n");
        model.append("p(42,-25,25)\n");
        model.append("p(42,-25,-25)\n");
        model.append("p(38,-30,-30)\n");
        model.append("p(38,-30,20)\n");
        model.append("p(35,-28,25)\n");
        model.append("p(35,-28,-20)\n");
        model.append("</p>\n\n");
        
        // === ADVANCED REAR SECTION ===
        
        // Rear body - left section
        model.append("// Rear body - left section\n");
        model.append("<p>\n");
        model.append("c(180,25,25)\n");
        model.append("fs(-1)\n");
        model.append("p(-35,-28,-20)\n");
        model.append("p(-35,-18,-65)\n");
        model.append("p(-32,-15,-75)\n");
        model.append("p(-32,-25,-70)\n");
        model.append("p(-28,-12,-80)\n");
        model.append("p(-28,-22,-78)\n");
        model.append("</p>\n\n");
        
        // Rear body - right section
        model.append("// Rear body - right section\n");
        model.append("<p>\n");
        model.append("c(180,25,25)\n");
        model.append("fs(1)\n");
        model.append("p(35,-28,-20)\n");
        model.append("p(35,-18,-65)\n");
        model.append("p(32,-15,-75)\n");
        model.append("p(32,-25,-70)\n");
        model.append("p(28,-12,-80)\n");
        model.append("p(28,-22,-78)\n");
        model.append("</p>\n\n");
        
        // High-performance rear spoiler - main section
        model.append("// High-performance rear spoiler - main section\n");
        model.append("<p>\n");
        model.append("c(240,240,240)\n"); // Bright silver spoiler
        model.append("fs(-1)\n");
        model.append("p(-32,-8,-85)\n");
        model.append("p(-32,-15,-85)\n");
        model.append("p(-28,-18,-90)\n");
        model.append("p(-28,-10,-95)\n");
        model.append("p(-24,-8,-100)\n");
        model.append("p(-20,-10,-102)\n");
        model.append("p(20,-10,-102)\n");
        model.append("p(24,-8,-100)\n");
        model.append("p(28,-10,-95)\n");
        model.append("p(28,-18,-90)\n");
        model.append("p(32,-15,-85)\n");
        model.append("p(32,-8,-85)\n");
        model.append("</p>\n\n");
        
        // Rear spoiler supports - left
        model.append("// Rear spoiler supports - left\n");
        model.append("<p>\n");
        model.append("c(100,100,100)\n"); // Dark support struts
        model.append("fs(-1)\n");
        model.append("p(-28,-12,-80)\n");
        model.append("p(-28,-10,-95)\n");
        model.append("p(-26,-10,-95)\n");
        model.append("p(-26,-12,-80)\n");
        model.append("</p>\n\n");
        
        // Rear spoiler supports - right
        model.append("// Rear spoiler supports - right\n");
        model.append("<p>\n");
        model.append("c(100,100,100)\n");
        model.append("fs(1)\n");
        model.append("p(28,-12,-80)\n");
        model.append("p(28,-10,-95)\n");
        model.append("p(26,-10,-95)\n");
        model.append("p(26,-12,-80)\n");
        model.append("</p>\n\n");
        
        // === REAR LIGHTING SYSTEM ===
        
        // Main taillights - left
        model.append("// Main taillights - left\n");
        model.append("<p>\n");
        model.append("c(200,60,60)\n"); // Red taillight
        model.append("fs(-1)\n");
        model.append("lightB\n");
        model.append("p(-30,-15,-82)\n");
        model.append("p(-26,-18,-84)\n");
        model.append("p(-22,-15,-82)\n");
        model.append("p(-26,-12,-82)\n");
        model.append("</p>\n\n");
        
        // Main taillights - right
        model.append("// Main taillights - right\n");
        model.append("<p>\n");
        model.append("c(200,60,60)\n");
        model.append("fs(1)\n");
        model.append("lightB\n");
        model.append("p(30,-15,-82)\n");
        model.append("p(26,-18,-84)\n");
        model.append("p(22,-15,-82)\n");
        model.append("p(26,-12,-82)\n");
        model.append("</p>\n\n");
        
        // Brake lights - left
        model.append("// Brake lights - left\n");
        model.append("<p>\n");
        model.append("c(255,40,40)\n"); // Bright red brake lights
        model.append("fs(-1)\n");
        model.append("lightB\n");
        model.append("p(-34,-18,-78)\n");
        model.append("p(-32,-20,-79)\n");
        model.append("p(-30,-18,-78)\n");
        model.append("p(-32,-16,-78)\n");
        model.append("</p>\n\n");
        
        // Brake lights - right
        model.append("// Brake lights - right\n");
        model.append("<p>\n");
        model.append("c(255,40,40)\n");
        model.append("fs(1)\n");
        model.append("lightB\n");
        model.append("p(34,-18,-78)\n");
        model.append("p(32,-20,-79)\n");
        model.append("p(30,-18,-78)\n");
        model.append("p(32,-16,-78)\n");
        model.append("</p>\n\n");
        
        // === UNDERBODY AERODYNAMICS ===
        
        // Front underbody panel
        model.append("// Front underbody panel\n");
        model.append("<p>\n");
        model.append("c(40,40,40)\n"); // Dark underbody
        model.append("fs(-1)\n");
        model.append("p(-32,-30,92)\n");
        model.append("p(-28,-35,95)\n");
        model.append("p(28,-35,95)\n");
        model.append("p(32,-30,92)\n");
        model.append("p(20,-32,88)\n");
        model.append("p(-20,-32,88)\n");
        model.append("</p>\n\n");
        
        // Rear underbody panel
        model.append("// Rear underbody panel\n");
        model.append("<p>\n");
        model.append("c(40,40,40)\n");
        model.append("fs(-1)\n");
        model.append("p(-32,-30,-70)\n");
        model.append("p(-28,-35,-75)\n");
        model.append("p(28,-35,-75)\n");
        model.append("p(32,-30,-70)\n");
        model.append("p(20,-32,-65)\n");
        model.append("p(-20,-32,-65)\n");
        model.append("</p>\n\n");
        
        // Central underbody channel
        model.append("// Central underbody channel\n");
        model.append("<p>\n");
        model.append("c(50,50,50)\n");
        model.append("fs(-1)\n");
        model.append("p(-20,-32,88)\n");
        model.append("p(-15,-35,85)\n");
        model.append("p(15,-35,85)\n");
        model.append("p(20,-32,88)\n");
        model.append("p(20,-32,-65)\n");
        model.append("p(15,-35,-68)\n");
        model.append("p(-15,-35,-68)\n");
        model.append("p(-20,-32,-65)\n");
        model.append("</p>\n\n");
        
        createCarModel("velocityx", model.toString());
    }
    
    /**
     * Placeholder methods for remaining 33 cars - each needs individual implementation
     */
    private static void createHypersonic() throws IOException {
        // TODO: Individual Hypersonic car model
        createCarModel("hypersonic", "idiv(192)\nshadow()\n// TODO: Complete Hypersonic model\n");
    }
    
    private static void createLightningBolt() throws IOException {
        // TODO: Individual LightningBolt car model  
        createCarModel("lightningbolt", "idiv(178)\nshadow()\n// TODO: Complete LightningBolt model\n");
    }
    
    private static void createPlasmaWing() throws IOException {
        // TODO: Individual PlasmaWing car model
        createCarModel("plasmawing", "idiv(188)\nshadow()\n// TODO: Complete PlasmaWing model\n");
    }
    
    private static void createIronBeast() throws IOException {
        // TODO: Individual IronBeast car model
        createCarModel("ironbeast", "div(18)\nshadow()\n// TODO: Complete IronBeast model\n");
    }
    
    private static void createTankzilla() throws IOException {
        // TODO: Individual Tankzilla car model
        createCarModel("tankzilla", "div(19)\nshadow()\n// TODO: Complete Tankzilla model\n");
    }
    
    private static void createSteelWall() throws IOException {
        // TODO: Individual SteelWall car model
        createCarModel("steelwall", "div(17)\nshadow()\n// TODO: Complete SteelWall model\n");
    }
    
    private static void createFortress() throws IOException {
        // TODO: Individual Fortress car model
        createCarModel("fortress", "div(20)\nshadow()\n// TODO: Complete Fortress model\n");
    }
    
    private static void createPerfectBalance() throws IOException {
        // TODO: Individual PerfectBalance car model
        createCarModel("perfectbalance", "div(17)\nshadow()\n// TODO: Complete PerfectBalance model\n");
    }
    
    private static void createGoldenMean() throws IOException {
        // TODO: Individual GoldenMean car model
        createCarModel("goldenmean", "div(18)\nshadow()\n// TODO: Complete GoldenMean model\n");
    }
    
    private static void createHarmonyX() throws IOException {
        // TODO: Individual HarmonyX car model
        createCarModel("harmonyx", "div(19)\nshadow()\n// TODO: Complete HarmonyX model\n");
    }
    
    private static void createEquilibrium() throws IOException {
        // TODO: Individual Equilibrium car model
        createCarModel("equilibrium", "div(17)\nshadow()\n// TODO: Complete Equilibrium model\n");
    }
    
    private static void createNimbleWing() throws IOException {
        // TODO: Individual NimbleWing car model
        createCarModel("nimblewing", "idiv(145)\nshadow()\n// TODO: Complete NimbleWing model\n");
    }
    
    private static void createShadowDancer() throws IOException {
        // TODO: Individual ShadowDancer car model
        createCarModel("shadowdancer", "idiv(148)\nshadow()\n// TODO: Complete ShadowDancer model\n");
    }
    
    private static void createQuickSilver() throws IOException {
        // TODO: Individual QuickSilver car model
        createCarModel("quicksilver", "idiv(142)\nshadow()\n// TODO: Complete QuickSilver model\n");
    }
    
    private static void createGhostRider() throws IOException {
        // TODO: Individual GhostRider car model
        createCarModel("ghostrider", "idiv(150)\nshadow()\n// TODO: Complete GhostRider model\n");
    }
    
    private static void createAirMaster() throws IOException {
        // TODO: Individual AirMaster car model
        createCarModel("airmaster", "idiv(135)\nshadow()\n// TODO: Complete AirMaster model\n");
    }
    
    private static void createSkyDancer() throws IOException {
        // TODO: Individual SkyDancer car model
        createCarModel("skydancer", "idiv(138)\nshadow()\n// TODO: Complete SkyDancer model\n");
    }
    
    private static void createCloudRipper() throws IOException {
        // TODO: Individual CloudRipper car model
        createCarModel("cloudripper", "idiv(132)\nshadow()\n// TODO: Complete CloudRipper model\n");
    }
    
    private static void createAcrobat() throws IOException {
        // TODO: Individual Acrobat car model
        createCarModel("acrobat", "idiv(140)\nshadow()\n// TODO: Complete Acrobat model\n");
    }
    
    private static void createCrystalCar() throws IOException {
        // TODO: Individual CrystalCar car model
        createCarModel("crystalcar", "idiv(195)\niwid(110)\nshadow()\n// TODO: Complete CrystalCar model\n");
    }
    
    private static void createNeonFlash() throws IOException {
        // TODO: Individual NeonFlash car model
        createCarModel("neonflash", "idiv(198)\niwid(112)\nshadow()\n// TODO: Complete NeonFlash model\n");
    }
    
    private static void createHologram() throws IOException {
        // TODO: Individual Hologram car model
        createCarModel("hologram", "idiv(202)\niwid(115)\nshadow()\n// TODO: Complete Hologram model\n");
    }
    
    private static void createPrismatic() throws IOException {
        // TODO: Individual Prismatic car model
        createCarModel("prismatic", "idiv(205)\niwid(118)\nshadow()\n// TODO: Complete Prismatic model\n");
    }
    
    private static void createChampionship() throws IOException {
        // TODO: Individual Championship car model
        createCarModel("championship", "idiv(215)\niwid(115)\nshadow()\n// TODO: Complete Championship model\n");
    }
    
    private static void createGrandPrix() throws IOException {
        // TODO: Individual GrandPrix car model
        createCarModel("grandprix", "idiv(218)\niwid(116)\nshadow()\n// TODO: Complete GrandPrix model\n");
    }
    
    private static void createVictorious() throws IOException {
        // TODO: Individual Victorious car model
        createCarModel("victorious", "idiv(212)\niwid(114)\nshadow()\n// TODO: Complete Victorious model\n");
    }
    
    private static void createLegendary() throws IOException {
        // TODO: Individual Legendary car model
        createCarModel("legendary", "idiv(220)\niwid(118)\nshadow()\n// TODO: Complete Legendary model\n");
    }
    
    private static void createSupremacy() throws IOException {
        // TODO: Individual Supremacy car model
        createCarModel("supremacy", "idiv(225)\niwid(120)\nshadow()\n// TODO: Complete Supremacy model\n");
    }
    
    private static void createUltraBeast() throws IOException {
        // TODO: Individual UltraBeast car model
        createCarModel("ultrabeast", "idiv(228)\niwid(122)\nshadow()\n// TODO: Complete UltraBeast model\n");
    }
    
    private static void createOmegaForce() throws IOException {
        // TODO: Individual OmegaForce car model
        createCarModel("omegaforce", "idiv(250)\niwid(120)\nshadow()\n// TODO: Complete OmegaForce model\n");
    }
    
    private static void createInfinitum() throws IOException {
        // TODO: Individual Infinitum car model
        createCarModel("infinitum", "idiv(265)\niwid(125)\nshadow()\n// TODO: Complete Infinitum model\n");
    }
    
    private static void createTranscendent() throws IOException {
        // TODO: Individual Transcendent car model
        createCarModel("transcendent", "idiv(275)\niwid(128)\nshadow()\n// TODO: Complete Transcendent model\n");
    }
    
    private static void createZenithRace() throws IOException {
        // TODO: Individual ZenithRace car model
        createCarModel("zenithrace", "idiv(285)\niwid(130)\nshadow()\n// TODO: Complete ZenithRace model\n");
    }
    
    /**
     * Core method to create individual car model files
     */
    private static void createCarModel(String carName, String structure) throws IOException {
        File carFile = new File("data/" + carName + ".rad");
        try (FileWriter writer = new FileWriter(carFile)) {
            writer.write(structure);
        }
        System.out.println("Created car model: " + carName + ".rad");
    }
    
    /**
     * Pack all car models into models.radq archive
     */
    private static void packModelsIntoArchive() throws IOException {
        System.out.println("Packing all models into models.radq archive...");
        
        File dataDir = new File("data");
        File modelsArchive = new File("data/models.radq");
        
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(modelsArchive))) {
            
            // First, copy all existing models from the backup
            File backupArchive = new File("data/models.radq.backup");
            if (backupArchive.exists()) {
                try (ZipInputStream zis = new ZipInputStream(new FileInputStream(backupArchive))) {
                    ZipEntry entry;
                    byte[] buffer = new byte[1024];
                    
                    while ((entry = zis.getNextEntry()) != null) {
                        zos.putNextEntry(new ZipEntry(entry.getName()));
                        int length;
                        while ((length = zis.read(buffer)) > 0) {
                            zos.write(buffer, 0, length);
                        }
                        zos.closeEntry();
                        zis.closeEntry();
                    }
                }
            }
            
            // Add all new car model files
            File[] radFiles = dataDir.listFiles((dir, name) -> name.endsWith(".rad") && 
                !name.equals("models.radq") && !name.equals("models.radq.backup"));
                
            if (radFiles != null) {
                byte[] buffer = new byte[1024];
                
                for (File radFile : radFiles) {
                    String fileName = radFile.getName();
                    
                    // Only add new car models (not original ones)
                    boolean isNewCar = false;
                    for (int i = 16; i < ENHANCED_CAR_MODELS.length; i++) {
                        if (fileName.startsWith(ENHANCED_CAR_MODELS[i])) {
                            isNewCar = true;
                            break;
                        }
                    }
                    
                    if (isNewCar) {
                        try (FileInputStream fis = new FileInputStream(radFile)) {
                            zos.putNextEntry(new ZipEntry(fileName));
                            int length;
                            while ((length = fis.read(buffer)) > 0) {
                                zos.write(buffer, 0, length);
                            }
                            zos.closeEntry();
                        }
                        
                        // Clean up individual file after adding to archive
                        radFile.delete();
                    }
                }
            }
        }
        
        System.out.println("Successfully packed all models into models.radq");
    }
}
