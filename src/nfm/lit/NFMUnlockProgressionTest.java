package nfm.lit;

/**
 * Test demonstration showing the correct NFM unlock progression system:
 * - 17 tracks total
 * - 1 track unlocked at a time  
 * - Each car is boss of 2 tracks
 * - Dr Monstaa unlocked after track 16
 * - Track 17 is playground with all cars
 */
public class NFMUnlockProgressionTest {
    
    public static void main(String[] args) {
        System.out.println("NFM-Lit Correct Unlock Progression Test");
        System.out.println("=======================================");
        
        testUnlockProgression();
    }
    
    private static void testUnlockProgression() {
        RaceSettingsManager manager = RaceSettingsManager.getInstance();
        
        // Test various game progression states
        int[] testStages = {8, 9, 10, 11, 12, 15, 19, 23, 24}; // sc[0] values (stage + 7)
        
        for (int sc0 : testStages) {
            int stage = sc0 - 7;
            System.out.println("\n=== STAGE " + stage + " COMPLETED ===");
            
            int[] sc = {sc0, 1, 2, 3, 4, 5, 6};
            manager.updateUnlockProgression(sc);
            
            // Show track progression
            System.out.println("Tracks unlocked: " + stage + " out of 17");
            var unlockedTracks = manager.getUnlockedTracks();
            System.out.println("Available tracks: " + unlockedTracks.size());
            
            // Show car progression with boss information
            System.out.println("Car unlock progression:");
            var unlockedCars = manager.getUnlockedCars();
            
            // Show which cars are unlocked with their unlock tracks
            String[] carNames = CarConfig.CAR_MODELS;
            String[] bossTrackPairs = {
                "2000tornados (starting car)",
                "formula7 (boss of tracks 1-2)",
                "canyenaro (boss of tracks 3-4)", 
                "lescrab (boss of tracks 5-6)",
                "nimi (boss of tracks 7-8)",
                "maxrevenge (boss of tracks 9-10)",
                "leadoxide (boss of tracks 11-12)",
                "koolkat (boss of tracks 13-14)",
                "drifter (boss of tracks 15-16)",
                "policecops (unlocked after track 16)",
                "mustang (unlocked after track 16)",
                "king (unlocked after track 16)",
                "audir8 (unlocked after track 16)",
                "masheen (unlocked after track 16)",
                "radicalone (unlocked after track 16)",
                "drmonster (Dr Monstaa - unlocked after track 16)"
            };
            
            for (int i = 0; i < carNames.length; i++) {
                String status = manager.isCarUnlocked(i) ? "UNLOCKED" : "locked";
                System.out.println("  " + (i+1) + ". " + bossTrackPairs[i] + " [" + status + "]");
            }
            
            System.out.println("Total cars unlocked: " + unlockedCars.size() + "/" + carNames.length);
            
            // Show special track 17 status
            if (stage >= 17) {
                System.out.println("*** PLAYGROUND (Track 17) UNLOCKED - All cars available! ***");
            }
        }
        
        System.out.println("\n=== SUMMARY ===");
        System.out.println("NFM Unlock System:");
        System.out.println("- Tracks 1-2: Unlock formula7");
        System.out.println("- Tracks 3-4: Unlock canyenaro");
        System.out.println("- Tracks 5-6: Unlock lescrab");
        System.out.println("- Tracks 7-8: Unlock nimi");
        System.out.println("- Tracks 9-10: Unlock maxrevenge");
        System.out.println("- Tracks 11-12: Unlock leadoxide");
        System.out.println("- Tracks 13-14: Unlock koolkat");
        System.out.println("- Tracks 15-16: Unlock drifter");
        System.out.println("- After Track 16: Unlock all remaining cars including Dr Monstaa");
        System.out.println("- Track 17: The Playground with all cars available");
    }
}
