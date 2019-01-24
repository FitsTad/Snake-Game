package core;

import gui.SnakeGUI;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.ArrayDeque;
import java.util.Deque;
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
     * Queue of key presses in case two keys are pressed within one frame update
     */
    private final Deque<Integer> keyqueue;
    /**
     * Array of DX given a code
     */
    private static final int[] DX = {-1, 0, 1, 0};
    /**
     * Array of DY given a code
     */
    private static final int[] DY = {0, -1, 0, 1};
    
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
        this.size = new Dimension(w, h);
        this.paused = false;
        this.gameover = false;
        this.keyqueue = new ArrayDeque<>();
        
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
        // Register the next key press, if any
        if (!this.keyqueue.isEmpty()) {
            int code = this.keyqueue.pop();
            this.dx = DX[code];
            this.dy = DY[code];
        }
        // Advance the grid to advance the state, and if the game is over, set game over and pause
        this.gameover = this.paused = this.grid.advanceState(this.dx, this.dy);
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
    public synchronized final void registerKeypress(int code) {
        // If it's a space, pause and clear the key queue so the snake doesn't turn after pausing
        if (code == KeyEvent.VK_SPACE) {
            // Toggle pause
            this.paused ^= true;
            // Clear the key queue
            this.keyqueue.clear();
        } else {
            int dir = code - 37; // 0 left | 1 up | 2 right | 3 down
            int cdx = DX[dir]; // queued x direction change
            if (this.keyqueue.isEmpty()) {
                // If the key queue is empty, compare to the current direction to prevent folding
                if ((cdx == 0) ^ (this.dx == 0)) { // Fails if either both are an x-dir or neither is
                    this.keyqueue.addLast(dir);
                }
            } else {
                // Otherwise, compare to the next queued element
                if (((dir & 1) ^ (this.keyqueue.getFirst() & 1)) == 1) { // Fails if either both or neither is an x-dir
                    this.keyqueue.addLast(dir);
                }
            }
        }
    }
}
