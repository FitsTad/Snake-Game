package core;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;
import utils.RandomUtils;

/**
 *
 * @author Alex Liao
 * 
 * Core grid class overlooking mechanics and game play
 */
public final class Grid {
    /**
     * The snake on the grid, storing all of the body points
     */
    public final Snake snake;
    /**
     * Mechanics settings
     */
    private final int lengthen, w, h;
    /**
     * The current location of the target
     */
    public Point apple;
    /**
     * The current game (the one of which this grid is a composition)
     */
    private final Game game;
    /**
     * The player's current score
     */
    private int score;
    
    /**
     * Construct a new game grid given specified mechanics settings
     * 
     * @param game the game
     * @param lengthen the number of cells to add to the snake each time
     * @param w the width of the board
     * @param h the height of the board
     */
    public Grid(Game game, int lengthen, int w, int h) {
        Deque<Point> body = new ArrayDeque<>();
        // Snake starts out centered around the center of the grid
        body.add(new Point(w / 2 + 1, h / 2));
        body.add(new Point(w / 2, h / 2));
        body.add(new Point(w / 2 - 1, h / 2));
        this.snake = new Snake(body);
        this.game = game;
        this.lengthen = lengthen;
        this.w = w;
        this.h = h;
        this.score = 0;
    }
    
    /**
     * Advance the state of the snake forward
     * 
     * @param dx the displacement in the left-right direction
     * @param dy the displacement in the up-down direction
     */
    private void moveSnake(int dx, int dy) {
        // Delegate the advance functionality to the snake
        this.snake.advance(dx, dy);
    }
    
    /**
     * Generate a new apple
     */
    public final void generateApple() {
        Set<Point> avoid = new HashSet<>(this.snake.body);
        // Avoid generating the apple in the apple too, though this theoretically doesn't matter
        if (this.apple != null) avoid.add(this.apple);
        // Generate a random point from RandomUtils
        this.apple = RandomUtils.randpoint(this.game.getWidth(), this.game.getHeight(), avoid);
    }
    
    /**
     * Increase the length of the snake by <code>lengthen</code>
     */
    private void lengthenSnake() {
        // Add <code>lengthen</code> to the snake's length
        this.snake.lengthen(this.lengthen);
    }
    
    /**
     * Check the snake for collisions including the apple
     * 
     * @return whether or not the snake has died
     */
    private boolean snakeCollisionCheck() {
        Point head = this.snake.head();
        // If the head hit an edge or itself, die
        if (head.x < 0 || head.x >= this.game.getWidth()
         || head.y < 0 || head.y >= this.game.getHeight()
         || this.snake.hitSelf()) {
            return true;
        }
        // If the head hit the apple, level up the snake and generate a new apple
        if (head.equals(this.apple)) {
            this.lengthenSnake();
            this.generateApple();
            this.score++;
        }
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
        // Move the snake
        this.moveSnake(dx, dy);
        // Run a collision check
        return this.snakeCollisionCheck();
    }
    
    @Override
    public final String toString() {
        return "<Grid " + this.snake + ">";
    }
    
    /**
     * Get the player's current score
     * 
     * @return the score
     */
    public final int getScore() {
        return this.score;
    }
}
