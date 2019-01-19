package core;

/**
 *
 * @author Alex Liao
 * 
 * Core grid class overlooking mechanics and game play
 */
public class Grid {
    /**
     * The snake on the grid, storing all of the body points
     */
    private final Snake snake;
    /**
     * Mechanics settings
     */
    private final int lengthen, w, h;
    /**
     * The current location of the target
     */
    private Point apple;
    
    /**
     * Construct a new game grid given specified mechanics settings
     * 
     * @param lengthen the number of cells to add to the snake each time
     * @param w the width of the board
     * @param h the height of the board
     */
    public Grid(int lengthen, int w, int h) {
        this.snake = new Snake();
        this.lengthen = lengthen;
        this.w = w;
        this.h = h;
        this.generateApple();
    }
    
    /**
     * Advance the state of the snake forward
     * 
     * @param dx the displacement in the left-right direction
     * @param dy the displacement in the up-down direction
     */
    private final void moveSnake(int dx, int dy) {
        
    }
    
    /**
     * Generate a new apple
     */
    private final void generateApple() {
        
    }
    
    /**
     * Increase the length of the snake by <code>lengthen</code>
     */
    private final void lengthenSnake() {
        
    }
    
    /**
     * Check the snake for collisions
     * 
     * @return whether or not the snake has died
     */
    private final boolean snakeCollisionCheck() {
        return false;
    }
    
    /**
     * Advance the state of the game
     * 
     * @param dx the displacement in the left-right direction
     * @param dy the displacement in the up-down direction
     * 
     * @return whether or not the snake has died
     */
    public final boolean advanceState(int dx, int dy) {
        return false;
    }
}
