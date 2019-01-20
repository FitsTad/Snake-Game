package core;

import gui.SnakeGUI;
import java.awt.Dimension;
import java.awt.event.KeyEvent;

/**
 *
 * @author Alex Liao
 * 
 * Core parent class overlooking all game events
 */
public final class Game {
    /**
     * The state controller of the game backend
     */
    public final Grid grid;
    /**
     * The x and y offset per turn
     */
    private int dx, dy;
    /**
     * The last move made (to prevent folding)
     */
    private int mx, my;
    /**
     * The dimensions of the grid
     */
    private final Dimension size;
    /**
     * Allow pausing of the game
     */
    public boolean paused;
    
    /**
     * Check if the game is over
     */
    private boolean gameover;
    
    /**
     * Construct a new game given specified mechanics settings
     * 
     * @param w the width of the game
     * @param h the height of the game
     * @param l the number of cells to add to the snake upon level up
     */
    public Game(int w, int h, int l) {
        this.grid = new Grid(this, l, w, h);
        // Snake starts out moving to the right
        this.dx = 1;
        this.dy = 0;
        this.mx = 1;
        this.my = 0;
        this.size = new Dimension(w, h);
        this.paused = false;
        this.gameover = false;
        
        this.grid.generateApple();
    }
    
    /**
     * Draw the current game configuration given the frame graphics
     * 
     * @param gui the GUI to update
     */
    public final void draw(SnakeGUI gui) {
        // GUI's game over should match the game's state
        gui.gameover = this.gameover;
        // Zero the grid
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 60; j++) {
                gui.grid[i][j] = 0;
            }
        }
        // For each block in the snake, if it's on the grid, set the grid space to green (1)
        this.grid.snake.body.forEach((p) -> {
            if (p.x >= 0 && p.x < gui.grid.length && p.y >= 0 && p.y < gui.grid[p.x].length) {
                gui.grid[p.x][p.y] = 1;
            }
        });
        // If the apple exists (always, except on the first frame), set the grid space to red (2)
        if (this.grid.apple != null) gui.grid[this.grid.apple.x][this.grid.apple.y] = 2;
    }
    
    /**
     * Advance the game state forward by one
     */
    public final void advanceState() {
        if (this.paused) return;
        // Advance the grid to advance the state, and if the game is over, set game over and pause
        this.gameover = this.paused = this.grid.advanceState(this.dx, this.dy);
        // MX and MY exist to prevent the user from pressing a perpendicular key and then the opposite key and inverting the snake through itself
        this.mx = this.dx;
        this.my = this.dy;
    }
    
    /**
     * Get the width
     * 
     * @return the width
     */
    public final int getWidth() {
        return (int) this.size.getWidth();
    }
    
    /**
     * Get the height
     * 
     * @return the height
     */
    public final int getHeight() {
        return (int) this.size.getHeight();
    }
    
    /**
     * Register a key press event from the GUI
     * 
     * @param code the numerical ID of the pressed key
     */
    public final void registerKeypress(int code) {
        // Given an arrow key, adjust the direction (if it is not parallel), and on space, toggle pause
        switch (code) {
            case KeyEvent.VK_UP:
                if (this.my == 0) {
                    this.dy = -1;
                    this.dx = 0;
                }
                break;
            case KeyEvent.VK_DOWN:
                if (this.my == 0) {
                    this.dy = 1;
                    this.dx = 0;
                }
                break;
            case KeyEvent.VK_LEFT:
                if (this.mx == 0) {
                    this.dy = 0;
                    this.dx = -1;
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (this.mx == 0) {
                    this.dy = 0;
                    this.dx = 1;
                }
                break;
            case KeyEvent.VK_SPACE:
                this.paused ^= true; // Toggle whether the game is paused
                break;
        }
    }
}
