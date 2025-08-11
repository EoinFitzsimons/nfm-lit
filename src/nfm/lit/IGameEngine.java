package nfm.lit;

/**
 * Game Engine Interface for further decoupling.
 * Defines the core contract for game engine implementations.
 * 
 * @author Generated for decoupling improvements
 */
public interface IGameEngine {
    
    /**
     * Initialize the game engine - loads resources, sets up graphics
     */
    void init();
    
    /**
     * Start the game engine - begins the main game loop
     */
    void start();
    
    /**
     * Stop the game engine - pauses execution
     */
    void stop();
    
    /**
     * Clean up resources and shutdown the game engine
     */
    void destroy();
    
    /**
     * Main game loop execution
     */
    void run();
    
    /**
     * Get the current game state as a descriptive string
     * @return Current game state description
     */
    String getGameState();
    
    /**
     * Get the current game state ID
     * @return Numeric identifier for current game state
     */
    int getGameStateID();
    
    /**
     * Check if the game engine is currently running
     * @return true if engine is active, false otherwise
     */
    boolean isRunning();
    
    /**
     * Get the current stage/level being played
     * @return Stage identifier
     */
    int getCurrentStage();
    
    /**
     * Save game progress and settings
     */
    void saveGameProgress();
    
    /**
     * Load game progress and settings
     */
    void loadGameProgress();
    
    /**
     * Handle input events from user
     * @param inputType Type of input (keyboard, mouse, etc.)
     * @param inputValue Value or code of the input
     */
    void handleInput(String inputType, int inputValue);
    
    /**
     * Update game logic for one frame
     * @param deltaTime Time elapsed since last update
     */
    void update(float deltaTime);
    
    /**
     * Render the current game state
     */
    void render();
}
