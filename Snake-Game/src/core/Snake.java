package core;

import java.util.Deque;
import java.util.ArrayDeque;

/**
 *
 * @author Alex Liao
 * 
 * Core snake class overlooking manipulation of data structures of the snake
 */
public class Snake {
    /**
     * The points in the body
     */
    final Deque<Point> body;
    /**
     * The number of cells collapsed on the tail of the snake after level up
     */
    private int hiddenLength;
    
    /**
     * Construct a new snake given a set of points
     * 
     * @param body the current point set
     */
    public Snake(Deque<Point> body) {
        this.body = body;
        this.hiddenLength = 0;
    }
    
    /**
     * Construct an empty snake
     */
    public Snake() {
        this(new ArrayDeque<>());
    }
    
    /**
     * Advance the snake forward one space
     * 
     * @param dx the displacement in the left-right direction
     * @param dy the displacement in the up-down direction
     */
    public final void advance(int dx, int dy) {
        
    }
    
    /**
     * Increase the length of the snake
     * 
     * @param add the number of cells to add to the tail
     */
    public final void lengthen(int add) {
        
    }
}