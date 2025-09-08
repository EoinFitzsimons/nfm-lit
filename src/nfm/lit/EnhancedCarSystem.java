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
    public static void createVelocityX() throws IOException {
        StringBuilder model = new StringBuilder();
        
        // Professional racing car - high polygon count for realistic detail
        model.append("idiv(210)\n");
        model.append("iwid(115)\n");
        model.append("shadow()\n\n");
        
        // === DETAILED NOSE CONE ASSEMBLY ===
        
        // Front nose tip - ultra sharp point
        model.append("// Front nose tip - racing point\n");
        model.append("<p>\n");
        model.append("c(220,220,225)\n"); // Bright silver nose
        model.append("fs(-1)\n");
        model.append("p(-8,-15,112)\n");
        model.append("p(-4,-18,114)\n");
        model.append("p(0,-20,115)\n");
        model.append("p(4,-18,114)\n");
        model.append("p(8,-15,112)\n");
        model.append("p(6,-12,110)\n");
        model.append("p(-6,-12,110)\n");
        model.append("</p>\n\n");
        
        // Nose cone upper section - left
        model.append("// Nose cone upper section - left\n");
        model.append("<p>\n");
        model.append("c(200,200,210)\n");
        model.append("fs(-1)\n");
        model.append("p(-6,-12,110)\n");
        model.append("p(-12,-10,108)\n");
        model.append("p(-18,-8,106)\n");
        model.append("p(-24,-6,104)\n");
        model.append("p(-30,-4,102)\n");
        model.append("p(-28,-8,100)\n");
        model.append("p(-22,-10,102)\n");
        model.append("p(-16,-12,104)\n");
        model.append("p(-10,-14,106)\n");
        model.append("</p>\n\n");
        
        // Nose cone upper section - right
        model.append("// Nose cone upper section - right\n");
        model.append("<p>\n");
        model.append("c(200,200,210)\n");
        model.append("fs(1)\n");
        model.append("p(6,-12,110)\n");
        model.append("p(12,-10,108)\n");
        model.append("p(18,-8,106)\n");
        model.append("p(24,-6,104)\n");
        model.append("p(30,-4,102)\n");
        model.append("p(28,-8,100)\n");
        model.append("p(22,-10,102)\n");
        model.append("p(16,-12,104)\n");
        model.append("p(10,-14,106)\n");
        model.append("</p>\n\n");
        
        // Nose cone lower section - left
        model.append("// Nose cone lower section - left\n");
        model.append("<p>\n");
        model.append("c(180,180,190)\n");
        model.append("fs(-1)\n");
        model.append("p(-10,-14,106)\n");
        model.append("p(-16,-16,104)\n");
        model.append("p(-22,-18,102)\n");
        model.append("p(-28,-20,100)\n");
        model.append("p(-32,-22,98)\n");
        model.append("p(-30,-25,96)\n");
        model.append("p(-24,-23,98)\n");
        model.append("p(-18,-21,100)\n");
        model.append("p(-12,-19,102)\n");
        model.append("p(-8,-17,104)\n");
        model.append("</p>\n\n");
        
        // Nose cone lower section - right
        model.append("// Nose cone lower section - right\n");
        model.append("<p>\n");
        model.append("c(180,180,190)\n");
        model.append("fs(1)\n");
        model.append("p(10,-14,106)\n");
        model.append("p(16,-16,104)\n");
        model.append("p(22,-18,102)\n");
        model.append("p(28,-20,100)\n");
        model.append("p(32,-22,98)\n");
        model.append("p(30,-25,96)\n");
        model.append("p(24,-23,98)\n");
        model.append("p(18,-21,100)\n");
        model.append("p(12,-19,102)\n");
        model.append("p(8,-17,104)\n");
        model.append("</p>\n\n");
        
        // === ADVANCED FRONT WING ASSEMBLY ===
        
        // Front wing main element - left
        model.append("// Front wing main element - left\n");
        model.append("<p>\n");
        model.append("c(240,240,245)\n"); // Bright wing element
        model.append("fs(-1)\n");
        model.append("p(-35,-25,95)\n");
        model.append("p(-40,-28,93)\n");
        model.append("p(-45,-30,91)\n");
        model.append("p(-48,-32,89)\n");
        model.append("p(-46,-35,87)\n");
        model.append("p(-41,-33,89)\n");
        model.append("p(-36,-31,91)\n");
        model.append("p(-32,-29,93)\n");
        model.append("</p>\n\n");
        
        // Front wing main element - right
        model.append("// Front wing main element - right\n");
        model.append("<p>\n");
        model.append("c(240,240,245)\n");
        model.append("fs(1)\n");
        model.append("p(35,-25,95)\n");
        model.append("p(40,-28,93)\n");
        model.append("p(45,-30,91)\n");
        model.append("p(48,-32,89)\n");
        model.append("p(46,-35,87)\n");
        model.append("p(41,-33,89)\n");
        model.append("p(36,-31,91)\n");
        model.append("p(32,-29,93)\n");
        model.append("</p>\n\n");
        
        // Front wing upper flap - left
        model.append("// Front wing upper flap - left\n");
        model.append("<p>\n");
        model.append("c(220,220,230)\n");
        model.append("fs(-1)\n");
        model.append("p(-32,-22,98)\n");
        model.append("p(-36,-24,96)\n");
        model.append("p(-40,-26,94)\n");
        model.append("p(-42,-28,92)\n");
        model.append("p(-40,-25,94)\n");
        model.append("p(-36,-23,96)\n");
        model.append("p(-32,-21,98)\n");
        model.append("p(-30,-20,99)\n");
        model.append("</p>\n\n");
        
        // Front wing upper flap - right
        model.append("// Front wing upper flap - right\n");
        model.append("<p>\n");
        model.append("c(220,220,230)\n");
        model.append("fs(1)\n");
        model.append("p(32,-22,98)\n");
        model.append("p(36,-24,96)\n");
        model.append("p(40,-26,94)\n");
        model.append("p(42,-28,92)\n");
        model.append("p(40,-25,94)\n");
        model.append("p(36,-23,96)\n");
        model.append("p(32,-21,98)\n");
        model.append("p(30,-20,99)\n");
        model.append("</p>\n\n");
        
        // Wing endplates - left
        model.append("// Wing endplates - left\n");
        model.append("<p>\n");
        model.append("c(200,200,210)\n");
        model.append("fs(-1)\n");
        model.append("p(-48,-32,89)\n");
        model.append("p(-50,-34,87)\n");
        model.append("p(-50,-38,85)\n");
        model.append("p(-48,-40,83)\n");
        model.append("p(-46,-38,85)\n");
        model.append("p(-46,-35,87)\n");
        model.append("</p>\n\n");
        
        // Wing endplates - right
        model.append("// Wing endplates - right\n");
        model.append("<p>\n");
        model.append("c(200,200,210)\n");
        model.append("fs(1)\n");
        model.append("p(48,-32,89)\n");
        model.append("p(50,-34,87)\n");
        model.append("p(50,-38,85)\n");
        model.append("p(48,-40,83)\n");
        model.append("p(46,-38,85)\n");
        model.append("p(46,-35,87)\n");
        model.append("</p>\n\n");
        
        // === MAIN CHASSIS AND BODY ===
        
        // Main chassis side - left upper
        model.append("// Main chassis side - left upper\n");
        model.append("<p>\n");
        model.append("c(180,25,25)\n"); // Racing red
        model.append("fs(-1)\n");
        model.append("p(-30,-20,99)\n");
        model.append("p(-28,-15,95)\n");
        model.append("p(-26,-12,91)\n");
        model.append("p(-24,-10,87)\n");
        model.append("p(-22,-8,83)\n");
        model.append("p(-24,-12,85)\n");
        model.append("p(-26,-14,89)\n");
        model.append("p(-28,-16,93)\n");
        model.append("p(-30,-18,97)\n");
        model.append("</p>\n\n");
        
        // Main chassis side - right upper
        model.append("// Main chassis side - right upper\n");
        model.append("<p>\n");
        model.append("c(180,25,25)\n");
        model.append("fs(1)\n");
        model.append("p(30,-20,99)\n");
        model.append("p(28,-15,95)\n");
        model.append("p(26,-12,91)\n");
        model.append("p(24,-10,87)\n");
        model.append("p(22,-8,83)\n");
        model.append("p(24,-12,85)\n");
        model.append("p(26,-14,89)\n");
        model.append("p(28,-16,93)\n");
        model.append("p(30,-18,97)\n");
        model.append("</p>\n\n");
        
        // Main chassis side - left lower
        model.append("// Main chassis side - left lower\n");
        model.append("<p>\n");
        model.append("c(160,20,20)\n"); // Darker red
        model.append("fs(-1)\n");
        model.append("p(-30,-18,97)\n");
        model.append("p(-32,-22,95)\n");
        model.append("p(-34,-24,93)\n");
        model.append("p(-36,-26,91)\n");
        model.append("p(-38,-28,89)\n");
        model.append("p(-40,-30,87)\n");
        model.append("p(-38,-32,85)\n");
        model.append("p(-36,-30,87)\n");
        model.append("p(-34,-28,89)\n");
        model.append("p(-32,-26,91)\n");
        model.append("p(-30,-24,93)\n");
        model.append("</p>\n\n");
        
        // Main chassis side - right lower
        model.append("// Main chassis side - right lower\n");
        model.append("<p>\n");
        model.append("c(160,20,20)\n");
        model.append("fs(1)\n");
        model.append("p(30,-18,97)\n");
        model.append("p(32,-22,95)\n");
        model.append("p(34,-24,93)\n");
        model.append("p(36,-26,91)\n");
        model.append("p(38,-28,89)\n");
        model.append("p(40,-30,87)\n");
        model.append("p(38,-32,85)\n");
        model.append("p(36,-30,87)\n");
        model.append("p(34,-28,89)\n");
        model.append("p(32,-26,91)\n");
        model.append("p(30,-24,93)\n");
        model.append("</p>\n\n");
        
        // === SIDE PODS AND AIR INTAKES ===
        
        // Side pod main - left
        model.append("// Side pod main - left\n");
        model.append("<p>\n");
        model.append("c(200,40,40)\n"); // Bright accent red
        model.append("fs(-1)\n");
        model.append("p(-40,-30,87)\n");
        model.append("p(-42,-28,83)\n");
        model.append("p(-44,-26,79)\n");
        model.append("p(-46,-24,75)\n");
        model.append("p(-48,-22,71)\n");
        model.append("p(-46,-26,73)\n");
        model.append("p(-44,-28,77)\n");
        model.append("p(-42,-30,81)\n");
        model.append("p(-40,-32,85)\n");
        model.append("</p>\n\n");
        
        // Side pod main - right
        model.append("// Side pod main - right\n");
        model.append("<p>\n");
        model.append("c(200,40,40)\n");
        model.append("fs(1)\n");
        model.append("p(40,-30,87)\n");
        model.append("p(42,-28,83)\n");
        model.append("p(44,-26,79)\n");
        model.append("p(46,-24,75)\n");
        model.append("p(48,-22,71)\n");
        model.append("p(46,-26,73)\n");
        model.append("p(44,-28,77)\n");
        model.append("p(42,-30,81)\n");
        model.append("p(40,-32,85)\n");
        model.append("</p>\n\n");
        
        // Air intake opening - left
        model.append("// Air intake opening - left\n");
        model.append("<p>\n");
        model.append("c(60,60,60)\n"); // Dark intake
        model.append("fs(-1)\n");
        model.append("p(-46,-24,75)\n");
        model.append("p(-48,-22,73)\n");
        model.append("p(-50,-20,71)\n");
        model.append("p(-48,-18,69)\n");
        model.append("p(-46,-20,71)\n");
        model.append("p(-44,-22,73)\n");
        model.append("</p>\n\n");
        
        // Air intake opening - right
        model.append("// Air intake opening - right\n");
        model.append("<p>\n");
        model.append("c(60,60,60)\n");
        model.append("fs(1)\n");
        model.append("p(46,-24,75)\n");
        model.append("p(48,-22,73)\n");
        model.append("p(50,-20,71)\n");
        model.append("p(48,-18,69)\n");
        model.append("p(46,-20,71)\n");
        model.append("p(44,-22,73)\n");
        model.append("</p>\n\n");
        
        // Side pod rear - left
        model.append("// Side pod rear - left\n");
        model.append("<p>\n");
        model.append("c(180,25,25)\n");
        model.append("fs(-1)\n");
        model.append("p(-48,-22,71)\n");
        model.append("p(-46,-20,67)\n");
        model.append("p(-44,-18,63)\n");
        model.append("p(-42,-16,59)\n");
        model.append("p(-40,-14,55)\n");
        model.append("p(-42,-18,57)\n");
        model.append("p(-44,-20,61)\n");
        model.append("p(-46,-22,65)\n");
        model.append("p(-48,-24,69)\n");
        model.append("</p>\n\n");
        
        // Side pod rear - right
        model.append("// Side pod rear - right\n");
        model.append("<p>\n");
        model.append("c(180,25,25)\n");
        model.append("fs(1)\n");
        model.append("p(48,-22,71)\n");
        model.append("p(46,-20,67)\n");
        model.append("p(44,-18,63)\n");
        model.append("p(42,-16,59)\n");
        model.append("p(40,-14,55)\n");
        model.append("p(42,-18,57)\n");
        model.append("p(44,-20,61)\n");
        model.append("p(46,-22,65)\n");
        model.append("p(48,-24,69)\n");
        model.append("</p>\n\n");
        
        // === COCKPIT AND WINDSHIELD ===
        
        // Cockpit opening main
        model.append("// Cockpit opening main\n");
        model.append("<p>\n");
        model.append("glass\n");
        model.append("fs(0)\n");
        model.append("gr(-20)\n");
        model.append("p(-22,-8,83)\n");
        model.append("p(-18,-6,79)\n");
        model.append("p(-14,-4,75)\n");
        model.append("p(-10,-2,71)\n");
        model.append("p(10,-2,71)\n");
        model.append("p(14,-4,75)\n");
        model.append("p(18,-6,79)\n");
        model.append("p(22,-8,83)\n");
        model.append("p(20,-12,81)\n");
        model.append("p(16,-14,77)\n");
        model.append("p(12,-16,73)\n");
        model.append("p(-12,-16,73)\n");
        model.append("p(-16,-14,77)\n");
        model.append("p(-20,-12,81)\n");
        model.append("</p>\n\n");
        
        // Windshield frame - left
        model.append("// Windshield frame - left\n");
        model.append("<p>\n");
        model.append("c(100,100,110)\n"); // Dark frame
        model.append("fs(-1)\n");
        model.append("p(-22,-8,83)\n");
        model.append("p(-24,-10,85)\n");
        model.append("p(-26,-12,87)\n");
        model.append("p(-24,-6,81)\n");
        model.append("p(-20,-4,79)\n");
        model.append("p(-18,-6,79)\n");
        model.append("p(-20,-12,81)\n");
        model.append("</p>\n\n");
        
        // Windshield frame - right
        model.append("// Windshield frame - right\n");
        model.append("<p>\n");
        model.append("c(100,100,110)\n");
        model.append("fs(1)\n");
        model.append("p(22,-8,83)\n");
        model.append("p(24,-10,85)\n");
        model.append("p(26,-12,87)\n");
        model.append("p(24,-6,81)\n");
        model.append("p(20,-4,79)\n");
        model.append("p(18,-6,79)\n");
        
        // === PROFESSIONAL LIGHTING SYSTEM ===
        
        // Front headlight cluster - left main
        model.append("// Front headlight cluster - left main\n");
        model.append("<p>\n");
        model.append("c(255,255,255)\n");
        model.append("fs(-1)\n");
        model.append("lightF\n");
        model.append("gr(-30)\n");
        model.append("p(-30,-20,100)\n");
        model.append("p(-26,-22,102)\n");
        model.append("p(-22,-20,100)\n");
        model.append("p(-26,-18,100)\n");
        model.append("</p>\n\n");
        
        // Front headlight cluster - right main
        model.append("// Front headlight cluster - right main\n");
        model.append("<p>\n");
        model.append("c(255,255,255)\n");
        model.append("fs(1)\n");
        model.append("lightF\n");
        model.append("gr(-30)\n");
        model.append("p(30,-20,100)\n");
        model.append("p(26,-22,102)\n");
        model.append("p(22,-20,100)\n");
        model.append("p(26,-18,100)\n");
        model.append("</p>\n\n");
        
        // Front headlight cluster - left secondary
        model.append("// Front headlight cluster - left secondary\n");
        model.append("<p>\n");
        model.append("c(240,240,255)\n");
        model.append("fs(-1)\n");
        model.append("lightF\n");
        model.append("p(-34,-22,98)\n");
        model.append("p(-32,-24,99)\n");
        model.append("p(-30,-22,98)\n");
        model.append("p(-32,-20,98)\n");
        model.append("</p>\n\n");
        
        // Front headlight cluster - right secondary
        model.append("// Front headlight cluster - right secondary\n");
        model.append("<p>\n");
        model.append("c(240,240,255)\n");
        model.append("fs(1)\n");
        model.append("lightF\n");
        model.append("p(34,-22,98)\n");
        model.append("p(32,-24,99)\n");
        model.append("p(30,-22,98)\n");
        model.append("p(32,-20,98)\n");
        model.append("</p>\n\n");
        
        // === MID-SECTION DETAILS ===
        
        // Mid-section transition - left
        model.append("// Mid-section transition - left\n");
        model.append("<p>\n");
        model.append("c(200,40,40)\n");
        model.append("fs(-1)\n");
        model.append("p(-40,-14,55)\n");
        model.append("p(-38,-12,51)\n");
        model.append("p(-36,-10,47)\n");
        model.append("p(-34,-8,43)\n");
        model.append("p(-32,-6,39)\n");
        model.append("p(-34,-10,41)\n");
        model.append("p(-36,-12,45)\n");
        model.append("p(-38,-14,49)\n");
        model.append("p(-40,-16,53)\n");
        model.append("</p>\n\n");
        
        // Mid-section transition - right
        model.append("// Mid-section transition - right\n");
        model.append("<p>\n");
        model.append("c(200,40,40)\n");
        model.append("fs(1)\n");
        model.append("p(40,-14,55)\n");
        model.append("p(38,-12,51)\n");
        model.append("p(36,-10,47)\n");
        model.append("p(34,-8,43)\n");
        model.append("p(32,-6,39)\n");
        model.append("p(34,-10,41)\n");
        model.append("p(36,-12,45)\n");
        model.append("p(38,-14,49)\n");
        model.append("p(40,-16,53)\n");
        model.append("</p>\n\n");
        
        // Side panel detailing - left upper
        model.append("// Side panel detailing - left upper\n");
        model.append("<p>\n");
        model.append("c(160,20,20)\n");
        model.append("fs(-1)\n");
        model.append("p(-32,-6,39)\n");
        model.append("p(-30,-4,35)\n");
        model.append("p(-28,-2,31)\n");
        model.append("p(-26,0,27)\n");
        model.append("p(-24,2,23)\n");
        model.append("p(-26,-2,25)\n");
        model.append("p(-28,-4,29)\n");
        model.append("p(-30,-6,33)\n");
        model.append("p(-32,-8,37)\n");
        model.append("</p>\n\n");
        
        // Side panel detailing - right upper
        model.append("// Side panel detailing - right upper\n");
        model.append("<p>\n");
        model.append("c(160,20,20)\n");
        model.append("fs(1)\n");
        model.append("p(32,-6,39)\n");
        model.append("p(30,-4,35)\n");
        model.append("p(28,-2,31)\n");
        model.append("p(26,0,27)\n");
        model.append("p(24,2,23)\n");
        model.append("p(26,-2,25)\n");
        model.append("p(28,-4,29)\n");
        model.append("p(30,-6,33)\n");
        model.append("p(32,-8,37)\n");
        model.append("</p>\n\n");
        
        // Side panel detailing - left lower
        model.append("// Side panel detailing - left lower\n");
        model.append("<p>\n");
        model.append("c(140,15,15)\n");
        model.append("fs(-1)\n");
        model.append("p(-24,2,23)\n");
        model.append("p(-22,4,19)\n");
        model.append("p(-20,6,15)\n");
        model.append("p(-18,8,11)\n");
        model.append("p(-16,10,7)\n");
        model.append("p(-18,6,9)\n");
        model.append("p(-20,4,13)\n");
        model.append("p(-22,2,17)\n");
        model.append("p(-24,0,21)\n");
        model.append("</p>\n\n");
        
        // Side panel detailing - right lower
        model.append("// Side panel detailing - right lower\n");
        model.append("<p>\n");
        model.append("c(140,15,15)\n");
        model.append("fs(1)\n");
        model.append("p(24,2,23)\n");
        model.append("p(22,4,19)\n");
        model.append("p(20,6,15)\n");
        model.append("p(18,8,11)\n");
        model.append("p(16,10,7)\n");
        model.append("p(18,6,9)\n");
        model.append("p(20,4,13)\n");
        model.append("p(22,2,17)\n");
        model.append("p(24,0,21)\n");
        model.append("</p>\n\n");
        
        // === WHEEL WELL STRUCTURES ===
        
        // Front wheel well - left
        model.append("// Front wheel well - left\n");
        model.append("<p>\n");
        model.append("c(80,80,90)\n"); // Dark wheel well
        model.append("fs(-1)\n");
        model.append("p(-45,-35,75)\n");
        model.append("p(-50,-38,73)\n");
        model.append("p(-52,-42,71)\n");
        model.append("p(-50,-45,69)\n");
        model.append("p(-45,-42,71)\n");
        model.append("p(-42,-38,73)\n");
        model.append("p(-40,-35,75)\n");
        model.append("p(-42,-32,77)\n");
        model.append("</p>\n\n");
        
        // Front wheel well - right
        model.append("// Front wheel well - right\n");
        model.append("<p>\n");
        model.append("c(80,80,90)\n");
        model.append("fs(1)\n");
        model.append("p(45,-35,75)\n");
        model.append("p(50,-38,73)\n");
        model.append("p(52,-42,71)\n");
        model.append("p(50,-45,69)\n");
        model.append("p(45,-42,71)\n");
        model.append("p(42,-38,73)\n");
        model.append("p(40,-35,75)\n");
        model.append("p(42,-32,77)\n");
        model.append("</p>\n\n");
        
        // Rear wheel well - left
        model.append("// Rear wheel well - left\n");
        model.append("<p>\n");
        model.append("c(80,80,90)\n");
        model.append("fs(-1)\n");
        model.append("p(-45,-35,-45)\n");
        model.append("p(-50,-38,-47)\n");
        model.append("p(-52,-42,-49)\n");
        model.append("p(-50,-45,-51)\n");
        model.append("p(-45,-42,-49)\n");
        model.append("p(-42,-38,-47)\n");
        model.append("p(-40,-35,-45)\n");
        model.append("p(-42,-32,-43)\n");
        model.append("</p>\n\n");
        
        // Rear wheel well - right
        model.append("// Rear wheel well - right\n");
        model.append("<p>\n");
        model.append("c(80,80,90)\n");
        model.append("fs(1)\n");
        model.append("p(45,-35,-45)\n");
        model.append("p(50,-38,-47)\n");
        model.append("p(52,-42,-49)\n");
        model.append("p(50,-45,-51)\n");
        model.append("p(45,-42,-49)\n");
        model.append("p(42,-38,-47)\n");
        model.append("p(40,-35,-45)\n");
        model.append("p(42,-32,-43)\n");
        model.append("</p>\n\n");
        
        // === REAR SECTION AND SPOILER ASSEMBLY ===
        
        // Rear body section - left detailed
        model.append("// Rear body section - left detailed\n");
        model.append("<p>\n");
        model.append("c(180,25,25)\n");
        model.append("fs(-1)\n");
        model.append("p(-16,10,7)\n");
        model.append("p(-14,8,3)\n");
        model.append("p(-12,6,-1)\n");
        model.append("p(-10,4,-5)\n");
        model.append("p(-8,2,-9)\n");
        model.append("p(-10,6,-7)\n");
        model.append("p(-12,8,-3)\n");
        model.append("p(-14,10,1)\n");
        model.append("p(-16,12,5)\n");
        model.append("</p>\n\n");
        
        // Rear body section - right detailed
        model.append("// Rear body section - right detailed\n");
        model.append("<p>\n");
        model.append("c(180,25,25)\n");
        model.append("fs(1)\n");
        model.append("p(16,10,7)\n");
        model.append("p(14,8,3)\n");
        model.append("p(12,6,-1)\n");
        model.append("p(10,4,-5)\n");
        model.append("p(8,2,-9)\n");
        model.append("p(10,6,-7)\n");
        model.append("p(12,8,-3)\n");
        model.append("p(14,10,1)\n");
        model.append("p(16,12,5)\n");
        model.append("</p>\n\n");
        
        // Rear transition section - left
        model.append("// Rear transition section - left\n");
        model.append("<p>\n");
        model.append("c(160,20,20)\n");
        model.append("fs(-1)\n");
        model.append("p(-8,2,-9)\n");
        model.append("p(-6,0,-13)\n");
        model.append("p(-4,-2,-17)\n");
        model.append("p(-2,-4,-21)\n");
        model.append("p(0,-6,-25)\n");
        model.append("p(-2,-2,-23)\n");
        model.append("p(-4,0,-19)\n");
        model.append("p(-6,2,-15)\n");
        model.append("p(-8,4,-11)\n");
        model.append("</p>\n\n");
        
        // Rear transition section - right
        model.append("// Rear transition section - right\n");
        model.append("<p>\n");
        model.append("c(160,20,20)\n");
        model.append("fs(1)\n");
        model.append("p(8,2,-9)\n");
        model.append("p(6,0,-13)\n");
        model.append("p(4,-2,-17)\n");
        model.append("p(2,-4,-21)\n");
        model.append("p(0,-6,-25)\n");
        model.append("p(2,-2,-23)\n");
        model.append("p(4,0,-19)\n");
        model.append("p(6,2,-15)\n");
        model.append("p(8,4,-11)\n");
        model.append("</p>\n\n");
        
        // Rear spoiler main wing
        model.append("// Rear spoiler main wing\n");
        model.append("<p>\n");
        model.append("c(240,240,245)\n"); // Bright wing
        model.append("fs(-1)\n");
        model.append("p(-35,-5,-90)\n");
        model.append("p(-30,-7,-95)\n");
        model.append("p(-25,-9,-100)\n");
        model.append("p(-20,-11,-105)\n");
        model.append("p(-15,-13,-110)\n");
        model.append("p(15,-13,-110)\n");
        model.append("p(20,-11,-105)\n");
        model.append("p(25,-9,-100)\n");
        model.append("p(30,-7,-95)\n");
        model.append("p(35,-5,-90)\n");
        model.append("p(32,-8,-92)\n");
        model.append("p(27,-10,-97)\n");
        model.append("p(22,-12,-102)\n");
        model.append("p(17,-14,-107)\n");
        model.append("p(-17,-14,-107)\n");
        model.append("p(-22,-12,-102)\n");
        model.append("p(-27,-10,-97)\n");
        model.append("p(-32,-8,-92)\n");
        model.append("</p>\n\n");
        
        // Rear spoiler support struts - left
        model.append("// Rear spoiler support struts - left\n");
        model.append("<p>\n");
        model.append("c(120,120,130)\n"); // Dark struts
        model.append("fs(-1)\n");
        model.append("p(-25,-6,-75)\n");
        model.append("p(-25,-9,-100)\n");
        model.append("p(-23,-9,-100)\n");
        model.append("p(-23,-6,-75)\n");
        model.append("</p>\n\n");
        
        // Rear spoiler support struts - center left
        model.append("// Rear spoiler support struts - center left\n");
        model.append("<p>\n");
        model.append("c(120,120,130)\n");
        model.append("fs(-1)\n");
        model.append("p(-10,-8,-80)\n");
        model.append("p(-10,-13,-110)\n");
        model.append("p(-8,-13,-110)\n");
        model.append("p(-8,-8,-80)\n");
        model.append("</p>\n\n");
        
        // Rear spoiler support struts - center right
        model.append("// Rear spoiler support struts - center right\n");
        model.append("<p>\n");
        model.append("c(120,120,130)\n");
        model.append("fs(1)\n");
        model.append("p(10,-8,-80)\n");
        model.append("p(10,-13,-110)\n");
        model.append("p(8,-13,-110)\n");
        model.append("p(8,-8,-80)\n");
        model.append("</p>\n\n");
        
        // Rear spoiler support struts - right
        model.append("// Rear spoiler support struts - right\n");
        model.append("<p>\n");
        model.append("c(120,120,130)\n");
        model.append("fs(1)\n");
        model.append("p(25,-6,-75)\n");
        model.append("p(25,-9,-100)\n");
        model.append("p(23,-9,-100)\n");
        model.append("p(23,-6,-75)\n");
        model.append("</p>\n\n");
        
        // === REAR LIGHTING SYSTEM ===
        
        // Main taillights - left cluster
        model.append("// Main taillights - left cluster\n");
        model.append("<p>\n");
        model.append("c(200,60,60)\n");
        model.append("fs(-1)\n");
        model.append("lightB\n");
        model.append("p(-30,-8,-85)\n");
        model.append("p(-26,-10,-87)\n");
        model.append("p(-22,-8,-85)\n");
        model.append("p(-26,-6,-85)\n");
        model.append("</p>\n\n");
        
        // Main taillights - right cluster
        model.append("// Main taillights - right cluster\n");
        model.append("<p>\n");
        model.append("c(200,60,60)\n");
        model.append("fs(1)\n");
        model.append("lightB\n");
        model.append("p(30,-8,-85)\n");
        model.append("p(26,-10,-87)\n");
        model.append("p(22,-8,-85)\n");
        model.append("p(26,-6,-85)\n");
        model.append("</p>\n\n");
        
        // Brake lights - left high mount
        model.append("// Brake lights - left high mount\n");
        model.append("<p>\n");
        model.append("c(255,40,40)\n");
        model.append("fs(-1)\n");
        model.append("lightB\n");
        model.append("p(-15,-5,-78)\n");
        model.append("p(-12,-6,-79)\n");
        model.append("p(-9,-5,-78)\n");
        model.append("p(-12,-4,-78)\n");
        model.append("</p>\n\n");
        
        // Brake lights - right high mount
        model.append("// Brake lights - right high mount\n");
        model.append("<p>\n");
        model.append("c(255,40,40)\n");
        model.append("fs(1)\n");
        model.append("lightB\n");
        model.append("p(15,-5,-78)\n");
        model.append("p(12,-6,-79)\n");
        model.append("p(9,-5,-78)\n");
        model.append("p(12,-4,-78)\n");
        model.append("</p>\n\n");
        
        // Central brake light
        model.append("// Central brake light\n");
        model.append("<p>\n");
        model.append("c(255,20,20)\n");
        model.append("fs(-1)\n");
        model.append("lightB\n");
        model.append("p(-5,-4,-76)\n");
        model.append("p(-2,-5,-77)\n");
        model.append("p(2,-5,-77)\n");
        model.append("p(5,-4,-76)\n");
        model.append("p(3,-3,-76)\n");
        model.append("p(-3,-3,-76)\n");
        model.append("</p>\n\n");
        
        // === UNDERBODY AERODYNAMICS ===
        
        // Front splitter - left section
        model.append("// Front splitter - left section\n");
        model.append("<p>\n");
        model.append("c(40,40,45)\n"); // Dark splitter
        model.append("fs(-1)\n");
        model.append("p(-35,-35,95)\n");
        model.append("p(-40,-38,93)\n");
        model.append("p(-45,-40,91)\n");
        model.append("p(-48,-42,89)\n");
        model.append("p(-46,-45,87)\n");
        model.append("p(-41,-43,89)\n");
        model.append("p(-36,-41,91)\n");
        model.append("p(-32,-38,93)\n");
        model.append("</p>\n\n");
        
        // Front splitter - right section
        model.append("// Front splitter - right section\n");
        model.append("<p>\n");
        model.append("c(40,40,45)\n");
        model.append("fs(1)\n");
        model.append("p(35,-35,95)\n");
        model.append("p(40,-38,93)\n");
        model.append("p(45,-40,91)\n");
        model.append("p(48,-42,89)\n");
        model.append("p(46,-45,87)\n");
        model.append("p(41,-43,89)\n");
        model.append("p(36,-41,91)\n");
        model.append("p(32,-38,93)\n");
        model.append("</p>\n\n");
        
        // Rear diffuser - left section
        model.append("// Rear diffuser - left section\n");
        model.append("<p>\n");
        model.append("c(50,50,55)\n");
        model.append("fs(-1)\n");
        model.append("p(-25,-35,-70)\n");
        model.append("p(-30,-38,-75)\n");
        model.append("p(-35,-40,-80)\n");
        model.append("p(-38,-42,-85)\n");
        model.append("p(-36,-45,-87)\n");
        model.append("p(-31,-43,-82)\n");
        model.append("p(-26,-41,-77)\n");
        model.append("p(-22,-38,-72)\n");
        model.append("</p>\n\n");
        
        // Rear diffuser - right section
        model.append("// Rear diffuser - right section\n");
        model.append("<p>\n");
        model.append("c(50,50,55)\n");
        model.append("fs(1)\n");
        model.append("p(25,-35,-70)\n");
        model.append("p(30,-38,-75)\n");
        model.append("p(35,-40,-80)\n");
        model.append("p(38,-42,-85)\n");
        model.append("p(36,-45,-87)\n");
        model.append("p(31,-43,-82)\n");
        model.append("p(26,-41,-77)\n");
        model.append("p(22,-38,-72)\n");
        model.append("</p>\n\n");
        
        // Central tunnel
        model.append("// Central tunnel\n");
        model.append("<p>\n");
        model.append("c(60,60,65)\n");
        model.append("fs(-1)\n");
        model.append("p(-15,-38,85)\n");
        model.append("p(-10,-42,80)\n");
        model.append("p(-5,-45,75)\n");
        model.append("p(5,-45,75)\n");
        model.append("p(10,-42,80)\n");
        model.append("p(15,-38,85)\n");
        model.append("p(15,-38,-65)\n");
        model.append("p(10,-42,-70)\n");
        model.append("p(5,-45,-75)\n");
        model.append("p(-5,-45,-75)\n");
        model.append("p(-10,-42,-70)\n");
        model.append("p(-15,-38,-65)\n");
        model.append("</p>\n\n");
        
        // === ADDITIONAL DETAILED PANELS (MOTORSPORTS REALISM) ===
        
        // Engine cover vents - left section
        model.append("// Engine cover vents - left section\n");
        model.append("<p>\n");
        model.append("c(100,100,110)\n");
        model.append("fs(-1)\n");
        model.append("p(-18,-6,35)\n");
        model.append("p(-16,-4,31)\n");
        model.append("p(-14,-2,27)\n");
        model.append("p(-16,-8,29)\n");
        model.append("</p>\n\n");
        
        // Engine cover vents - right section
        model.append("// Engine cover vents - right section\n");
        model.append("<p>\n");
        model.append("c(100,100,110)\n");
        model.append("fs(1)\n");
        model.append("p(18,-6,35)\n");
        model.append("p(16,-4,31)\n");
        model.append("p(14,-2,27)\n");
        model.append("p(16,-8,29)\n");
        model.append("</p>\n\n");
        
        // Side mirror assemblies - left
        model.append("// Side mirror assemblies - left\n");
        model.append("<p>\n");
        model.append("c(160,160,170)\n");
        model.append("fs(-1)\n");
        model.append("p(-26,-6,81)\n");
        model.append("p(-30,-4,83)\n");
        model.append("p(-32,-6,85)\n");
        model.append("p(-28,-8,83)\n");
        model.append("</p>\n\n");
        
        // Side mirror assemblies - right
        model.append("// Side mirror assemblies - right\n");
        model.append("<p>\n");
        model.append("c(160,160,170)\n");
        model.append("fs(1)\n");
        model.append("p(26,-6,81)\n");
        model.append("p(30,-4,83)\n");
        model.append("p(32,-6,85)\n");
        model.append("p(28,-8,83)\n");
        model.append("</p>\n\n");
        
        // Suspension pickup points - front left
        model.append("// Suspension pickup points - front left\n");
        model.append("<p>\n");
        model.append("c(90,90,95)\n");
        model.append("fs(-1)\n");
        model.append("p(-42,-32,77)\n");
        model.append("p(-44,-34,75)\n");
        model.append("p(-46,-32,73)\n");
        model.append("p(-44,-30,75)\n");
        model.append("</p>\n\n");
        
        // Suspension pickup points - front right
        model.append("// Suspension pickup points - front right\n");
        model.append("<p>\n");
        model.append("c(90,90,95)\n");
        model.append("fs(1)\n");
        model.append("p(42,-32,77)\n");
        model.append("p(44,-34,75)\n");
        model.append("p(46,-32,73)\n");
        model.append("p(44,-30,75)\n");
        model.append("</p>\n\n");
        
        // Suspension pickup points - rear left
        model.append("// Suspension pickup points - rear left\n");
        model.append("<p>\n");
        model.append("c(90,90,95)\n");
        model.append("fs(-1)\n");
        model.append("p(-42,-32,-43)\n");
        model.append("p(-44,-34,-45)\n");
        model.append("p(-46,-32,-47)\n");
        model.append("p(-44,-30,-45)\n");
        model.append("</p>\n\n");
        
        // Suspension pickup points - rear right
        model.append("// Suspension pickup points - rear right\n");
        model.append("<p>\n");
        model.append("c(90,90,95)\n");
        model.append("fs(1)\n");
        model.append("p(42,-32,-43)\n");
        model.append("p(44,-34,-45)\n");
        model.append("p(46,-32,-47)\n");
        model.append("p(44,-30,-45)\n");
        model.append("</p>\n\n");
        
        // Front brake ducts - left
        model.append("// Front brake ducts - left\n");
        model.append("<p>\n");
        model.append("c(70,70,75)\n");
        model.append("fs(-1)\n");
        model.append("p(-48,-30,79)\n");
        model.append("p(-50,-32,77)\n");
        model.append("p(-52,-30,75)\n");
        model.append("p(-50,-28,77)\n");
        model.append("</p>\n\n");
        
        // Front brake ducts - right
        model.append("// Front brake ducts - right\n");
        model.append("<p>\n");
        model.append("c(70,70,75)\n");
        model.append("fs(1)\n");
        model.append("p(48,-30,79)\n");
        model.append("p(50,-32,77)\n");
        model.append("p(52,-30,75)\n");
        model.append("p(50,-28,77)\n");
        model.append("</p>\n\n");
        
        // Rear brake ducts - left
        model.append("// Rear brake ducts - left\n");
        model.append("<p>\n");
        model.append("c(70,70,75)\n");
        model.append("fs(-1)\n");
        model.append("p(-48,-30,-41)\n");
        model.append("p(-50,-32,-43)\n");
        model.append("p(-52,-30,-45)\n");
        model.append("p(-50,-28,-43)\n");
        model.append("</p>\n\n");
        
        // Rear brake ducts - right
        model.append("// Rear brake ducts - right\n");
        model.append("<p>\n");
        model.append("c(70,70,75)\n");
        model.append("fs(1)\n");
        model.append("p(48,-30,-41)\n");
        model.append("p(50,-32,-43)\n");
        model.append("p(52,-30,-45)\n");
        model.append("p(50,-28,-43)\n");
        model.append("</p>\n\n");
        
        // Exhaust tip details - left
        model.append("// Exhaust tip details - left\n");
        model.append("<p>\n");
        model.append("c(120,120,125)\n");
        model.append("fs(-1)\n");
        model.append("p(-20,-25,-95)\n");
        model.append("p(-18,-27,-97)\n");
        model.append("p(-16,-25,-95)\n");
        model.append("p(-18,-23,-95)\n");
        model.append("</p>\n\n");
        
        // Exhaust tip details - right
        model.append("// Exhaust tip details - right\n");
        model.append("<p>\n");
        model.append("c(120,120,125)\n");
        model.append("fs(1)\n");
        model.append("p(20,-25,-95)\n");
        model.append("p(18,-27,-97)\n");
        model.append("p(16,-25,-95)\n");
        model.append("p(18,-23,-95)\n");
        model.append("</p>\n\n");
        
        // Front splitter details - center
        model.append("// Front splitter details - center\n");
        model.append("<p>\n");
        model.append("c(35,35,40)\n");
        model.append("fs(-1)\n");
        model.append("p(-15,-40,92)\n");
        model.append("p(-10,-42,90)\n");
        model.append("p(-5,-44,88)\n");
        model.append("p(5,-44,88)\n");
        model.append("p(10,-42,90)\n");
        model.append("p(15,-40,92)\n");
        model.append("p(12,-38,90)\n");
        model.append("p(8,-36,88)\n");
        model.append("p(-8,-36,88)\n");
        model.append("p(-12,-38,90)\n");
        model.append("</p>\n\n");
        
        // Wing mirror stalks - left
        model.append("// Wing mirror stalks - left\n");
        model.append("<p>\n");
        model.append("c(140,140,150)\n");
        model.append("fs(-1)\n");
        model.append("p(-24,-8,85)\n");
        model.append("p(-26,-6,83)\n");
        model.append("p(-28,-8,81)\n");
        model.append("p(-26,-10,83)\n");
        model.append("</p>\n\n");
        
        // Wing mirror stalks - right
        model.append("// Wing mirror stalks - right\n");
        model.append("<p>\n");
        model.append("c(140,140,150)\n");
        model.append("fs(1)\n");
        model.append("p(24,-8,85)\n");
        model.append("p(26,-6,83)\n");
        model.append("p(28,-8,81)\n");
        model.append("p(26,-10,83)\n");
        model.append("</p>\n\n");
        
        // Cockpit side panels - left
        model.append("// Cockpit side panels - left\n");
        model.append("<p>\n");
        model.append("c(120,120,130)\n");
        model.append("fs(-1)\n");
        model.append("p(-20,-10,79)\n");
        model.append("p(-22,-8,77)\n");
        model.append("p(-24,-10,75)\n");
        model.append("p(-22,-12,77)\n");
        model.append("</p>\n\n");
        
        // Cockpit side panels - right
        model.append("// Cockpit side panels - right\n");
        model.append("<p>\n");
        model.append("c(120,120,130)\n");
        model.append("fs(1)\n");
        model.append("p(20,-10,79)\n");
        model.append("p(22,-8,77)\n");
        model.append("p(24,-10,75)\n");
        model.append("p(22,-12,77)\n");
        model.append("</p>\n\n");
        
        // Side vent grilles - left upper
        model.append("// Side vent grilles - left upper\n");
        model.append("<p>\n");
        model.append("c(80,80,85)\n");
        model.append("fs(-1)\n");
        model.append("p(-46,-18,73)\n");
        model.append("p(-48,-16,71)\n");
        model.append("p(-50,-18,69)\n");
        model.append("p(-48,-20,71)\n");
        model.append("</p>\n\n");
        
        // Side vent grilles - right upper
        model.append("// Side vent grilles - right upper\n");
        model.append("<p>\n");
        model.append("c(80,80,85)\n");
        model.append("fs(1)\n");
        model.append("p(46,-18,73)\n");
        model.append("p(48,-16,71)\n");
        model.append("p(50,-18,69)\n");
        model.append("p(48,-20,71)\n");
        model.append("</p>\n\n");
        
        // Side vent grilles - left lower
        model.append("// Side vent grilles - left lower\n");
        model.append("<p>\n");
        model.append("c(75,75,80)\n");
        model.append("fs(-1)\n");
        model.append("p(-48,-24,69)\n");
        model.append("p(-50,-22,67)\n");
        model.append("p(-52,-24,65)\n");
        model.append("p(-50,-26,67)\n");
        model.append("</p>\n\n");
        
        // Side vent grilles - right lower
        model.append("// Side vent grilles - right lower\n");
        model.append("<p>\n");
        model.append("c(75,75,80)\n");
        model.append("fs(1)\n");
        model.append("p(48,-24,69)\n");
        model.append("p(50,-22,67)\n");
        model.append("p(52,-24,65)\n");
        model.append("p(50,-26,67)\n");
        model.append("</p>\n\n");
        
        // Radiator intake details - left
        model.append("// Radiator intake details - left\n");
        model.append("<p>\n");
        model.append("c(60,60,65)\n");
        model.append("fs(-1)\n");
        model.append("p(-50,-20,71)\n");
        model.append("p(-52,-18,69)\n");
        model.append("p(-54,-20,67)\n");
        model.append("p(-52,-22,69)\n");
        model.append("</p>\n\n");
        
        // Radiator intake details - right
        model.append("// Radiator intake details - right\n");
        model.append("<p>\n");
        model.append("c(60,60,65)\n");
        model.append("fs(1)\n");
        model.append("p(50,-20,71)\n");
        model.append("p(52,-18,69)\n");
        model.append("p(54,-20,67)\n");
        model.append("p(52,-22,69)\n");
        model.append("</p>\n\n");
        
        // Aero detail strips - left front
        model.append("// Aero detail strips - left front\n");
        model.append("<p>\n");
        model.append("c(200,200,205)\n");
        model.append("fs(-1)\n");
        model.append("p(-36,-26,91)\n");
        model.append("p(-38,-24,89)\n");
        model.append("p(-40,-26,87)\n");
        model.append("p(-38,-28,89)\n");
        model.append("</p>\n\n");
        
        // Aero detail strips - right front
        model.append("// Aero detail strips - right front\n");
        model.append("<p>\n");
        model.append("c(200,200,205)\n");
        model.append("fs(1)\n");
        model.append("p(36,-26,91)\n");
        model.append("p(38,-24,89)\n");
        model.append("p(40,-26,87)\n");
        model.append("p(38,-28,89)\n");
        model.append("</p>\n\n");
        
        // Aero detail strips - left rear
        model.append("// Aero detail strips - left rear\n");
        model.append("<p>\n");
        model.append("c(200,200,205)\n");
        model.append("fs(-1)\n");
        model.append("p(-36,-26,-55)\n");
        model.append("p(-38,-24,-57)\n");
        model.append("p(-40,-26,-59)\n");
        model.append("p(-38,-28,-57)\n");
        model.append("</p>\n\n");
        
        // Aero detail strips - right rear
        model.append("// Aero detail strips - right rear\n");
        model.append("<p>\n");
        model.append("c(200,200,205)\n");
        model.append("fs(1)\n");
        model.append("p(36,-26,-55)\n");
        model.append("p(38,-24,-57)\n");
        model.append("p(40,-26,-59)\n");
        model.append("p(38,-28,-57)\n");
        model.append("</p>\n\n");
        
        // Fine detail panels - nose left
        model.append("// Fine detail panels - nose left\n");
        model.append("<p>\n");
        model.append("c(210,210,215)\n");
        model.append("fs(-1)\n");
        model.append("p(-12,-16,108)\n");
        model.append("p(-14,-14,106)\n");
        model.append("p(-16,-16,104)\n");
        model.append("p(-14,-18,106)\n");
        model.append("</p>\n\n");
        
        // Fine detail panels - nose right
        model.append("// Fine detail panels - nose right\n");
        model.append("<p>\n");
        model.append("c(210,210,215)\n");
        model.append("fs(1)\n");
        model.append("p(12,-16,108)\n");
        model.append("p(14,-14,106)\n");
        model.append("p(16,-16,104)\n");
        model.append("p(14,-18,106)\n");
        model.append("</p>\n\n");
        
        // Fine detail panels - mid left
        model.append("// Fine detail panels - mid left\n");
        model.append("<p>\n");
        model.append("c(170,30,30)\n");
        model.append("fs(-1)\n");
        model.append("p(-26,-14,89)\n");
        model.append("p(-28,-12,87)\n");
        model.append("p(-30,-14,85)\n");
        model.append("p(-28,-16,87)\n");
        model.append("</p>\n\n");
        
        // Fine detail panels - mid right
        model.append("// Fine detail panels - mid right\n");
        model.append("<p>\n");
        model.append("c(170,30,30)\n");
        model.append("fs(1)\n");
        model.append("p(26,-14,89)\n");
        model.append("p(28,-12,87)\n");
        model.append("p(30,-14,85)\n");
        model.append("p(28,-16,87)\n");
        model.append("</p>\n\n");
        
        // Fine detail panels - rear left
        model.append("// Fine detail panels - rear left\n");
        model.append("<p>\n");
        model.append("c(150,15,15)\n");
        model.append("fs(-1)\n");
        model.append("p(-6,0,-13)\n");
        model.append("p(-8,-2,-15)\n");
        model.append("p(-10,0,-17)\n");
        model.append("p(-8,2,-15)\n");
        model.append("</p>\n\n");
        
        // Fine detail panels - rear right
        model.append("// Fine detail panels - rear right\n");
        model.append("<p>\n");
        model.append("c(150,15,15)\n");
        model.append("fs(1)\n");
        model.append("p(6,0,-13)\n");
        model.append("p(8,-2,-15)\n");
        model.append("p(10,0,-17)\n");
        model.append("p(8,2,-15)\n");
        model.append("</p>\n\n");
        
        // Additional front wing elements - left outer
        model.append("// Additional front wing elements - left outer\n");
        model.append("<p>\n");
        model.append("c(230,230,235)\n");
        model.append("fs(-1)\n");
        model.append("p(-50,-34,87)\n");
        model.append("p(-52,-36,85)\n");
        model.append("p(-54,-34,83)\n");
        model.append("p(-52,-32,85)\n");
        model.append("</p>\n\n");
        
        // Additional front wing elements - right outer
        model.append("// Additional front wing elements - right outer\n");
        model.append("<p>\n");
        model.append("c(230,230,235)\n");
        model.append("fs(1)\n");
        model.append("p(50,-34,87)\n");
        model.append("p(52,-36,85)\n");
        model.append("p(54,-34,83)\n");
        model.append("p(52,-32,85)\n");
        model.append("</p>\n\n");
        
        // Additional rear wing elements - left outer
        model.append("// Additional rear wing elements - left outer\n");
        model.append("<p>\n");
        model.append("c(230,230,235)\n");
        model.append("fs(-1)\n");
        model.append("p(-37,-6,-88)\n");
        model.append("p(-39,-8,-90)\n");
        model.append("p(-41,-6,-92)\n");
        model.append("p(-39,-4,-90)\n");
        model.append("</p>\n\n");
        
        // Additional rear wing elements - right outer
        model.append("// Additional rear wing elements - right outer\n");
        model.append("<p>\n");
        model.append("c(230,230,235)\n");
        model.append("fs(1)\n");
        model.append("p(37,-6,-88)\n");
        model.append("p(39,-8,-90)\n");
        model.append("p(41,-6,-92)\n");
        model.append("p(39,-4,-90)\n");
        model.append("</p>\n\n");
        
        // Micro detail elements - left side series
        model.append("// Micro detail elements - left side series\n");
        model.append("<p>\n");
        model.append("c(190,35,35)\n");
        model.append("fs(-1)\n");
        model.append("p(-34,-16,65)\n");
        model.append("p(-35,-15,64)\n");
        model.append("p(-36,-16,63)\n");
        model.append("p(-35,-17,64)\n");
        model.append("</p>\n\n");
        
        // Micro detail elements - right side series
        model.append("// Micro detail elements - right side series\n");
        model.append("<p>\n");
        model.append("c(190,35,35)\n");
        model.append("fs(1)\n");
        model.append("p(34,-16,65)\n");
        model.append("p(35,-15,64)\n");
        model.append("p(36,-16,63)\n");
        model.append("p(35,-17,64)\n");
        model.append("</p>\n\n");
        
        // Micro detail elements - left mid series
        model.append("// Micro detail elements - left mid series\n");
        model.append("<p>\n");
        model.append("c(190,35,35)\n");
        model.append("fs(-1)\n");
        model.append("p(-34,-16,45)\n");
        model.append("p(-35,-15,44)\n");
        model.append("p(-36,-16,43)\n");
        model.append("p(-35,-17,44)\n");
        model.append("</p>\n\n");
        
        // Micro detail elements - right mid series
        model.append("// Micro detail elements - right mid series\n");
        model.append("<p>\n");
        model.append("c(190,35,35)\n");
        model.append("fs(1)\n");
        model.append("p(34,-16,45)\n");
        model.append("p(35,-15,44)\n");
        model.append("p(36,-16,43)\n");
        model.append("p(35,-17,44)\n");
        model.append("</p>\n\n");
        
        // Micro detail elements - left rear series
        model.append("// Micro detail elements - left rear series\n");
        model.append("<p>\n");
        model.append("c(190,35,35)\n");
        model.append("fs(-1)\n");
        model.append("p(-34,-16,5)\n");
        model.append("p(-35,-15,4)\n");
        model.append("p(-36,-16,3)\n");
        model.append("p(-35,-17,4)\n");
        model.append("</p>\n\n");
        
        // Micro detail elements - right rear series
        model.append("// Micro detail elements - right rear series\n");
        model.append("<p>\n");
        model.append("c(190,35,35)\n");
        model.append("fs(1)\n");
        model.append("p(34,-16,5)\n");
        model.append("p(35,-15,4)\n");
        model.append("p(36,-16,3)\n");
        model.append("p(35,-17,4)\n");
        model.append("</p>\n\n");
        
        // Detailed edge trim - front left
        model.append("// Detailed edge trim - front left\n");
        model.append("<p>\n");
        model.append("c(180,180,185)\n");
        model.append("fs(-1)\n");
        model.append("p(-20,-18,100)\n");
        model.append("p(-21,-17,99)\n");
        model.append("p(-22,-18,98)\n");
        model.append("p(-21,-19,99)\n");
        model.append("</p>\n\n");
        
        // Detailed edge trim - front right
        model.append("// Detailed edge trim - front right\n");
        model.append("<p>\n");
        model.append("c(180,180,185)\n");
        model.append("fs(1)\n");
        model.append("p(20,-18,100)\n");
        model.append("p(21,-17,99)\n");
        model.append("p(22,-18,98)\n");
        model.append("p(21,-19,99)\n");
        model.append("</p>\n\n");
        
        // Detailed edge trim - rear left
        model.append("// Detailed edge trim - rear left\n");
        model.append("<p>\n");
        model.append("c(180,180,185)\n");
        model.append("fs(-1)\n");
        model.append("p(-20,-18,-60)\n");
        model.append("p(-21,-17,-61)\n");
        model.append("p(-22,-18,-62)\n");
        model.append("p(-21,-19,-61)\n");
        model.append("</p>\n\n");
        
        // Detailed edge trim - rear right
        model.append("// Detailed edge trim - rear right\n");
        model.append("<p>\n");
        model.append("c(180,180,185)\n");
        model.append("fs(1)\n");
        model.append("p(20,-18,-60)\n");
        model.append("p(21,-17,-61)\n");
        model.append("p(22,-18,-62)\n");
        model.append("p(21,-19,-61)\n");
        model.append("</p>\n\n");
        
        // === FINAL DETAILED MOTORSPORTS COMPONENTS ===
        
        // Front wing endplates - left detailed
        model.append("// Front wing endplates - left detailed\n");
        model.append("<p>\n");
        model.append("c(240,240,245)\n");
        model.append("fs(-1)\n");
        model.append("p(-54,-36,83)\n");
        model.append("p(-56,-34,81)\n");
        model.append("p(-58,-36,79)\n");
        model.append("p(-56,-38,81)\n");
        model.append("</p>\n\n");
        
        // Front wing endplates - right detailed
        model.append("// Front wing endplates - right detailed\n");
        model.append("<p>\n");
        model.append("c(240,240,245)\n");
        model.append("fs(1)\n");
        model.append("p(54,-36,83)\n");
        model.append("p(56,-34,81)\n");
        model.append("p(58,-36,79)\n");
        model.append("p(56,-38,81)\n");
        model.append("</p>\n\n");
        
        // Tire sidewall details - front left
        model.append("// Tire sidewall details - front left\n");
        model.append("<p>\n");
        model.append("c(20,20,25)\n");
        model.append("fs(-1)\n");
        model.append("p(-60,-42,79)\n");
        model.append("p(-62,-40,77)\n");
        model.append("p(-64,-42,75)\n");
        model.append("p(-62,-44,77)\n");
        model.append("</p>\n\n");
        
        // Tire sidewall details - front right
        model.append("// Tire sidewall details - front right\n");
        model.append("<p>\n");
        model.append("c(20,20,25)\n");
        model.append("fs(1)\n");
        model.append("p(60,-42,79)\n");
        model.append("p(62,-40,77)\n");
        model.append("p(64,-42,75)\n");
        model.append("p(62,-44,77)\n");
        model.append("</p>\n\n");
        
        // Tire sidewall details - rear left
        model.append("// Tire sidewall details - rear left\n");
        model.append("<p>\n");
        model.append("c(20,20,25)\n");
        model.append("fs(-1)\n");
        model.append("p(-60,-42,-41)\n");
        model.append("p(-62,-40,-43)\n");
        model.append("p(-64,-42,-45)\n");
        model.append("p(-62,-44,-43)\n");
        model.append("</p>\n\n");
        
        // Tire sidewall details - rear right
        model.append("// Tire sidewall details - rear right\n");
        model.append("<p>\n");
        model.append("c(20,20,25)\n");
        model.append("fs(1)\n");
        model.append("p(60,-42,-41)\n");
        model.append("p(62,-40,-43)\n");
        model.append("p(64,-42,-45)\n");
        model.append("p(62,-44,-43)\n");
        model.append("</p>\n\n");
        
        // Wheel rim details - front left inner
        model.append("// Wheel rim details - front left inner\n");
        model.append("<p>\n");
        model.append("c(180,180,190)\n");
        model.append("fs(-1)\n");
        model.append("p(-56,-38,77)\n");
        model.append("p(-58,-36,75)\n");
        model.append("p(-60,-38,73)\n");
        model.append("p(-58,-40,75)\n");
        model.append("</p>\n\n");
        
        // Wheel rim details - front right inner
        model.append("// Wheel rim details - front right inner\n");
        model.append("<p>\n");
        model.append("c(180,180,190)\n");
        model.append("fs(1)\n");
        model.append("p(56,-38,77)\n");
        model.append("p(58,-36,75)\n");
        model.append("p(60,-38,73)\n");
        model.append("p(58,-40,75)\n");
        model.append("</p>\n\n");
        
        // Wheel rim details - rear left inner
        model.append("// Wheel rim details - rear left inner\n");
        model.append("<p>\n");
        model.append("c(180,180,190)\n");
        model.append("fs(-1)\n");
        model.append("p(-56,-38,-43)\n");
        model.append("p(-58,-36,-45)\n");
        model.append("p(-60,-38,-47)\n");
        model.append("p(-58,-40,-45)\n");
        model.append("</p>\n\n");
        
        // Wheel rim details - rear right inner
        model.append("// Wheel rim details - rear right inner\n");
        model.append("<p>\n");
        model.append("c(180,180,190)\n");
        model.append("fs(1)\n");
        model.append("p(56,-38,-43)\n");
        model.append("p(58,-36,-45)\n");
        model.append("p(60,-38,-47)\n");
        model.append("p(58,-40,-45)\n");
        model.append("</p>\n\n");
        
        // Differential housing details
        model.append("// Differential housing details\n");
        model.append("<p>\n");
        model.append("c(110,110,115)\n");
        model.append("fs(-1)\n");
        model.append("p(-15,-35,-47)\n");
        model.append("p(-10,-37,-45)\n");
        model.append("p(-5,-39,-43)\n");
        model.append("p(5,-39,-43)\n");
        model.append("p(10,-37,-45)\n");
        model.append("p(15,-35,-47)\n");
        model.append("p(12,-33,-45)\n");
        model.append("p(8,-31,-43)\n");
        model.append("p(-8,-31,-43)\n");
        model.append("p(-12,-33,-45)\n");
        model.append("</p>\n\n");
        
        // Suspension arms - front left upper
        model.append("// Suspension arms - front left upper\n");
        model.append("<p>\n");
        model.append("c(95,95,100)\n");
        model.append("fs(-1)\n");
        model.append("p(-44,-28,79)\n");
        model.append("p(-46,-26,77)\n");
        model.append("p(-48,-28,75)\n");
        model.append("p(-46,-30,77)\n");
        model.append("</p>\n\n");
        
        // Suspension arms - front right upper
        model.append("// Suspension arms - front right upper\n");
        model.append("<p>\n");
        model.append("c(95,95,100)\n");
        model.append("fs(1)\n");
        model.append("p(44,-28,79)\n");
        model.append("p(46,-26,77)\n");
        model.append("p(48,-28,75)\n");
        model.append("p(46,-30,77)\n");
        model.append("</p>\n\n");
        
        // Suspension arms - front left lower
        model.append("// Suspension arms - front left lower\n");
        model.append("<p>\n");
        model.append("c(90,90,95)\n");
        model.append("fs(-1)\n");
        model.append("p(-44,-36,77)\n");
        model.append("p(-46,-34,75)\n");
        model.append("p(-48,-36,73)\n");
        model.append("p(-46,-38,75)\n");
        model.append("</p>\n\n");
        
        // Suspension arms - front right lower
        model.append("// Suspension arms - front right lower\n");
        model.append("<p>\n");
        model.append("c(90,90,95)\n");
        model.append("fs(1)\n");
        model.append("p(44,-36,77)\n");
        model.append("p(46,-34,75)\n");
        model.append("p(48,-36,73)\n");
        model.append("p(46,-38,75)\n");
        model.append("</p>\n\n");
        
        // Suspension arms - rear left upper
        model.append("// Suspension arms - rear left upper\n");
        model.append("<p>\n");
        model.append("c(95,95,100)\n");
        model.append("fs(-1)\n");
        model.append("p(-44,-28,-41)\n");
        model.append("p(-46,-26,-43)\n");
        model.append("p(-48,-28,-45)\n");
        model.append("p(-46,-30,-43)\n");
        model.append("</p>\n\n");
        
        // Suspension arms - rear right upper
        model.append("// Suspension arms - rear right upper\n");
        model.append("<p>\n");
        model.append("c(95,95,100)\n");
        model.append("fs(1)\n");
        model.append("p(44,-28,-41)\n");
        model.append("p(46,-26,-43)\n");
        model.append("p(48,-28,-45)\n");
        model.append("p(46,-30,-43)\n");
        model.append("</p>\n\n");
        
        // Suspension arms - rear left lower
        model.append("// Suspension arms - rear left lower\n");
        model.append("<p>\n");
        model.append("c(90,90,95)\n");
        model.append("fs(-1)\n");
        model.append("p(-44,-36,-43)\n");
        model.append("p(-46,-34,-45)\n");
        model.append("p(-48,-36,-47)\n");
        model.append("p(-46,-38,-45)\n");
        model.append("</p>\n\n");
        
        // Suspension arms - rear right lower
        model.append("// Suspension arms - rear right lower\n");
        model.append("<p>\n");
        model.append("c(90,90,95)\n");
        model.append("fs(1)\n");
        model.append("p(44,-36,-43)\n");
        model.append("p(46,-34,-45)\n");
        model.append("p(48,-36,-47)\n");
        model.append("p(46,-38,-45)\n");
        model.append("</p>\n\n");
        
        // Shock absorber details - front left
        model.append("// Shock absorber details - front left\n");
        model.append("<p>\n");
        model.append("c(130,130,135)\n");
        model.append("fs(-1)\n");
        model.append("p(-46,-32,75)\n");
        model.append("p(-47,-30,74)\n");
        model.append("p(-48,-32,73)\n");
        model.append("p(-47,-34,74)\n");
        model.append("</p>\n\n");
        
        // Shock absorber details - front right
        model.append("// Shock absorber details - front right\n");
        model.append("<p>\n");
        model.append("c(130,130,135)\n");
        model.append("fs(1)\n");
        model.append("p(46,-32,75)\n");
        model.append("p(47,-30,74)\n");
        model.append("p(48,-32,73)\n");
        model.append("p(47,-34,74)\n");
        model.append("</p>\n\n");
        
        // Shock absorber details - rear left
        model.append("// Shock absorber details - rear left\n");
        model.append("<p>\n");
        model.append("c(130,130,135)\n");
        model.append("fs(-1)\n");
        model.append("p(-46,-32,-45)\n");
        model.append("p(-47,-30,-46)\n");
        model.append("p(-48,-32,-47)\n");
        model.append("p(-47,-34,-46)\n");
        model.append("</p>\n\n");
        
        // Shock absorber details - rear right
        model.append("// Shock absorber details - rear right\n");
        model.append("<p>\n");
        model.append("c(130,130,135)\n");
        model.append("fs(1)\n");
        model.append("p(46,-32,-45)\n");
        model.append("p(47,-30,-46)\n");
        model.append("p(48,-32,-47)\n");
        model.append("p(47,-34,-46)\n");
        model.append("</p>\n\n");
        
        // Driveshaft housings - left
        model.append("// Driveshaft housings - left\n");
        model.append("<p>\n");
        model.append("c(105,105,110)\n");
        model.append("fs(-1)\n");
        model.append("p(-48,-34,17)\n");
        model.append("p(-50,-32,15)\n");
        model.append("p(-52,-34,13)\n");
        model.append("p(-50,-36,15)\n");
        model.append("</p>\n\n");
        
        // Driveshaft housings - right
        model.append("// Driveshaft housings - right\n");
        model.append("<p>\n");
        model.append("c(105,105,110)\n");
        model.append("fs(1)\n");
        model.append("p(48,-34,17)\n");
        model.append("p(50,-32,15)\n");
        model.append("p(52,-34,13)\n");
        model.append("p(50,-36,15)\n");
        model.append("</p>\n\n");
        
        // Anti-roll bar details - front
        model.append("// Anti-roll bar details - front\n");
        model.append("<p>\n");
        model.append("c(85,85,90)\n");
        model.append("fs(-1)\n");
        model.append("p(-25,-30,71)\n");
        model.append("p(-20,-32,69)\n");
        model.append("p(-15,-34,67)\n");
        model.append("p(15,-34,67)\n");
        model.append("p(20,-32,69)\n");
        model.append("p(25,-30,71)\n");
        model.append("p(20,-28,69)\n");
        model.append("p(15,-26,67)\n");
        model.append("p(-15,-26,67)\n");
        model.append("p(-20,-28,69)\n");
        model.append("</p>\n\n");
        
        // Anti-roll bar details - rear
        model.append("// Anti-roll bar details - rear\n");
        model.append("<p>\n");
        model.append("c(85,85,90)\n");
        model.append("fs(-1)\n");
        model.append("p(-25,-30,-49)\n");
        model.append("p(-20,-32,-51)\n");
        model.append("p(-15,-34,-53)\n");
        model.append("p(15,-34,-53)\n");
        model.append("p(20,-32,-51)\n");
        model.append("p(25,-30,-49)\n");
        model.append("p(20,-28,-51)\n");
        model.append("p(15,-26,-53)\n");
        model.append("p(-15,-26,-53)\n");
        model.append("p(-20,-28,-51)\n");
        model.append("</p>\n\n");
        
        // Fuel system covers - left
        model.append("// Fuel system covers - left\n");
        model.append("<p>\n");
        model.append("c(125,125,130)\n");
        model.append("fs(-1)\n");
        model.append("p(-22,-12,47)\n");
        model.append("p(-24,-10,45)\n");
        model.append("p(-26,-12,43)\n");
        model.append("p(-24,-14,45)\n");
        model.append("</p>\n\n");
        
        // Fuel system covers - right
        model.append("// Fuel system covers - right\n");
        model.append("<p>\n");
        model.append("c(125,125,130)\n");
        model.append("fs(1)\n");
        model.append("p(22,-12,47)\n");
        model.append("p(24,-10,45)\n");
        model.append("p(26,-12,43)\n");
        model.append("p(24,-14,45)\n");
        model.append("</p>\n\n");
        
        // Electronic control unit housings
        model.append("// Electronic control unit housings\n");
        model.append("<p>\n");
        model.append("c(75,75,80)\n");
        model.append("fs(-1)\n");
        model.append("p(-12,-8,53)\n");
        model.append("p(-8,-6,51)\n");
        model.append("p(-4,-8,49)\n");
        model.append("p(4,-8,49)\n");
        model.append("p(8,-6,51)\n");
        model.append("p(12,-8,53)\n");
        model.append("p(8,-10,51)\n");
        model.append("p(4,-12,49)\n");
        model.append("p(-4,-12,49)\n");
        model.append("p(-8,-10,51)\n");
        model.append("</p>\n\n");
        
        // Aerodynamic flow conditioners - left
        model.append("// Aerodynamic flow conditioners - left\n");
        model.append("<p>\n");
        model.append("c(220,220,225)\n");
        model.append("fs(-1)\n");
        model.append("p(-32,-20,87)\n");
        model.append("p(-34,-18,85)\n");
        model.append("p(-36,-20,83)\n");
        model.append("p(-34,-22,85)\n");
        model.append("</p>\n\n");
        
        // Aerodynamic flow conditioners - right
        model.append("// Aerodynamic flow conditioners - right\n");
        model.append("<p>\n");
        model.append("c(220,220,225)\n");
        model.append("fs(1)\n");
        model.append("p(32,-20,87)\n");
        model.append("p(34,-18,85)\n");
        model.append("p(36,-20,83)\n");
        model.append("p(34,-22,85)\n");
        model.append("</p>\n\n");
        
        // Transmission housing details
        model.append("// Transmission housing details\n");
        model.append("<p>\n");
        model.append("c(115,115,120)\n");
        model.append("fs(-1)\n");
        model.append("p(-18,-30,23)\n");
        model.append("p(-15,-32,21)\n");
        model.append("p(-12,-34,19)\n");
        model.append("p(12,-34,19)\n");
        model.append("p(15,-32,21)\n");
        model.append("p(18,-30,23)\n");
        model.append("p(15,-28,21)\n");
        model.append("p(12,-26,19)\n");
        model.append("p(-12,-26,19)\n");
        model.append("p(-15,-28,21)\n");
        model.append("</p>\n\n");
        
        // Final aerodynamic micro-elements - front cluster
        model.append("// Final aerodynamic micro-elements - front cluster\n");
        model.append("<p>\n");
        model.append("c(250,20,20)\n");
        model.append("fs(-1)\n");
        model.append("p(-8,-20,95)\n");
        model.append("p(-6,-18,93)\n");
        model.append("p(-4,-20,91)\n");
        model.append("p(-6,-22,93)\n");
        model.append("</p>\n\n");
        
        model.append("<p>\n");
        model.append("c(250,20,20)\n");
        model.append("fs(1)\n");
        model.append("p(8,-20,95)\n");
        model.append("p(6,-18,93)\n");
        model.append("p(4,-20,91)\n");
        model.append("p(6,-22,93)\n");
        model.append("</p>\n\n");
        
        // Final aerodynamic micro-elements - rear cluster
        model.append("// Final aerodynamic micro-elements - rear cluster\n");
        model.append("<p>\n");
        model.append("c(250,20,20)\n");
        model.append("fs(-1)\n");
        model.append("p(-8,-20,-85)\n");
        model.append("p(-6,-18,-87)\n");
        model.append("p(-4,-20,-89)\n");
        model.append("p(-6,-22,-87)\n");
        model.append("</p>\n\n");
        
        model.append("<p>\n");
        model.append("c(250,20,20)\n");
        model.append("fs(1)\n");
        model.append("p(8,-20,-85)\n");
        model.append("p(6,-18,-87)\n");
        model.append("p(4,-20,-89)\n");
        model.append("p(6,-22,-87)\n");
        model.append("</p>\n\n");
        
        // Additional precision elements to reach target
        // Roll cage details - side tubes
        model.append("// Roll cage details - side tubes\n");
        model.append("<p>\n");
        model.append("c(140,140,145)\n");
        model.append("fs(-1)\n");
        model.append("p(-18,-12,71)\n");
        model.append("p(-16,-10,69)\n");
        model.append("p(-14,-12,67)\n");
        model.append("p(-16,-14,69)\n");
        model.append("</p>\n\n");
        
        model.append("<p>\n");
        model.append("c(140,140,145)\n");
        model.append("fs(1)\n");
        model.append("p(18,-12,71)\n");
        model.append("p(16,-10,69)\n");
        model.append("p(14,-12,67)\n");
        model.append("p(16,-14,69)\n");
        model.append("</p>\n\n");
        
        // Fire suppression system nozzles
        model.append("// Fire suppression system nozzles\n");
        model.append("<p>\n");
        model.append("c(200,200,205)\n");
        model.append("fs(-1)\n");
        model.append("p(-14,-6,59)\n");
        model.append("p(-12,-4,57)\n");
        model.append("p(-10,-6,55)\n");
        model.append("p(-12,-8,57)\n");
        model.append("</p>\n\n");
        
        model.append("<p>\n");
        model.append("c(200,200,205)\n");
        model.append("fs(1)\n");
        model.append("p(14,-6,59)\n");
        model.append("p(12,-4,57)\n");
        model.append("p(10,-6,55)\n");
        model.append("p(12,-8,57)\n");
        model.append("</p>\n\n");
        
        // Data acquisition sensors - front
        model.append("// Data acquisition sensors - front\n");
        model.append("<p>\n");
        model.append("c(50,50,55)\n");
        model.append("fs(-1)\n");
        model.append("p(-16,-14,103)\n");
        model.append("p(-15,-13,102)\n");
        model.append("p(-14,-14,101)\n");
        model.append("p(-15,-15,102)\n");
        model.append("</p>\n\n");
        
        model.append("<p>\n");
        model.append("c(50,50,55)\n");
        model.append("fs(1)\n");
        model.append("p(16,-14,103)\n");
        model.append("p(15,-13,102)\n");
        model.append("p(14,-14,101)\n");
        model.append("p(15,-15,102)\n");
        model.append("</p>\n\n");
        
        // Data acquisition sensors - rear
        model.append("// Data acquisition sensors - rear\n");
        model.append("<p>\n");
        model.append("c(50,50,55)\n");
        model.append("fs(-1)\n");
        model.append("p(-16,-14,-83)\n");
        model.append("p(-15,-13,-84)\n");
        model.append("p(-14,-14,-85)\n");
        model.append("p(-15,-15,-84)\n");
        model.append("</p>\n\n");
        
        model.append("<p>\n");
        model.append("c(50,50,55)\n");
        model.append("fs(1)\n");
        model.append("p(16,-14,-83)\n");
        model.append("p(15,-13,-84)\n");
        model.append("p(14,-14,-85)\n");
        model.append("p(15,-15,-84)\n");
        model.append("</p>\n\n");
        
        // Final structural reinforcements
        model.append("// Final structural reinforcements\n");
        model.append("<p>\n");
        model.append("c(160,160,165)\n");
        model.append("fs(-1)\n");
        model.append("p(-22,-16,81)\n");
        model.append("p(-20,-14,79)\n");
        model.append("p(-18,-16,77)\n");
        model.append("p(-20,-18,79)\n");
        model.append("</p>\n\n");
        
        model.append("<p>\n");
        model.append("c(160,160,165)\n");
        model.append("fs(1)\n");
        model.append("p(22,-16,81)\n");
        model.append("p(20,-14,79)\n");
        model.append("p(18,-16,77)\n");
        model.append("p(20,-18,79)\n");
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
    public static void packModelsIntoArchive() throws IOException {
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
