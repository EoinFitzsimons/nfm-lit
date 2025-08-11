package nfm.lit;

/**
 * Game Engine Factory for creating game engine instances.
 * Demonstrates how the IGameEngine interface enables better decoupling.
 * 
 * @author Generated for decoupling improvements
 */
public class GameEngineFactory {
    
    /**
     * Create a default game engine instance
     * @return New game engine implementation
     */
    public static IGameEngine createGameEngine() {
        return new GameSparker();
    }
    
    /**
     * Create a game engine with specific configuration
     * @param engineType Type of engine to create (currently only "default" supported)
     * @return New game engine implementation
     */
    public static IGameEngine createGameEngine(String engineType) {
        switch (engineType.toLowerCase()) {
            case "default":
            case "gamesparker":
                return new GameSparker();
            default:
                throw new IllegalArgumentException("Unknown engine type: " + engineType);
        }
    }
    
    /**
     * Check if a specific engine type is supported
     * @param engineType Type to check
     * @return true if supported, false otherwise
     */
    public static boolean isEngineTypeSupported(String engineType) {
        switch (engineType.toLowerCase()) {
            case "default":
            case "gamesparker":
                return true;
            default:
                return false;
        }
    }
    
    private GameEngineFactory() {
        // Utility class - prevent instantiation
    }
}
