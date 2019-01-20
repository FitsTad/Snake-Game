package core;

import java.util.Deque;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Alex Liao
 * 
 * Core snake class overlooking manipulation of data structures of the snake
 */
public final class Snake {
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
     * Advance the snake forward one space
     * 
     * @param dx the displacement in the left-right direction
     * @param dy the displacement in the up-down direction
     */
    public final void advance(int dx, int dy) {
        // Get the point to which the current head will move
        Point next = new Point(this.head().x + dx, this.head().y + dy);
        // Add to the front of the deque
        this.body.addFirst(next);
        if (this.hiddenLength == 0) {
            // Remove from the back of the deque if the snake is not coiled
            this.body.removeLast();
        } else {
            // Otherwise, uncoil one point and do not remove the current tail end
            this.hiddenLength--;
        }
    }
    
    /**
     * Increase the length of the snake
     * 
     * @param add the number of cells to add to the tail
     */
    public final void lengthen(int add) {
        this.hiddenLength += add;
    }
    
    /**
     * Get the front of the snake
     * 
     * @return the front of the snake
     */
    public final Point head() {
        return this.body.getFirst();
    }
    
    /**
     * Check whether or not the snake has intersected itself
     * 
     * @return if the snake killed itself
     */
    public final boolean hitSelf() {
        return new HashSet<>(this.body).size() != this.body.size();
    }
    
    @Override
    public final String toString() {
        return this.body.toString();
    }
}