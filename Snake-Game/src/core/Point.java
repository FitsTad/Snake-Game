package core;

/**
 *
 * @author Alex Liao
 * 
 * Core utility class for storing a coordinate pair
 */
public final class Point {
    /**
     * The coordinates
     */
    public final int x, y;
    
    /**
     * Construct point (x, y)
     * 
     * @param x x
     * @param y y
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public final boolean equals(Object other) {
        // Standard object.equals implementation
        if (other == null) return false;
        if (!(other instanceof Point)) return false;
        Point point = (Point) other;
        return this.x == point.x && this.y == point.y;
    }

    @Override
    public final int hashCode() {
        // Auto-generated hash algorithm
        int hash = 7;
        hash = 79 * hash + this.x;
        hash = 79 * hash + this.y;
        return hash;
    }
    
    @Override
    public final String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }
}
