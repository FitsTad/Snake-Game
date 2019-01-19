package core;

import gui.SnakeGUI;

/**
 *
 * @author Alex Liao
 * 
 * Core parent class overlooking all game events
 */
public class Game {
    /**
     * The state controller of the game backend
     */
    private final Grid grid;
    /**
     * The x and y offset per turn
     */
    private int dx, dy;
    
    /**
     * Construct a new game given specified mechanics settings
     * 
     * @param w the width of the game
     * @param h the height of the game
     * @param l the number of cells to add to the snake upon level up
     */
    public Game(int w, int h, int l) {
        this.grid = new Grid(l, w, h);
        this.dx = 0;
        this.dy = 0;
    }
    
    /**
     * Draw the current game configuration given the frame graphics
     * 
     * @param gui the GUI to update
     */
    public final void draw(SnakeGUI gui) {
        
    }
    
    /**
     * Advance the game state forward by one
     */
    public final void advanceState() {
        
    }
    
    /**
     * Register a key press event from the GUI
     * 
     * @param code the numerical ID of the pressed key
     */
    public final void registerKeypress(int code) {
        
    }
}
