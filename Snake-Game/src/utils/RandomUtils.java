/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import core.Point;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author Alex Liao
 * 
 * This class provides utility methods for generating random objects
 */
public final class RandomUtils {
    /**
     * The random object for generating random numbers
     */
    private static final Random RANDOM = new Random();
    
    private RandomUtils() {}
    
    /**
     * Get a random integer
     * 
     * @param max exclusive upper bound
     * @return the random number
     */
    public static final int randint(int max) {
        return RANDOM.nextInt(max);
    }
    
    /**
     * Generate a random point and avoid set of points
     * 
     * @param x
     * @param y
     * @param avoid
     * @return 
     */
    public static final Point randpoint(int x, int y, Set<Point> avoid) {
        // Get a random number from 0 up to the number of points not avoided
        int p = RANDOM.nextInt(x * y - avoid.size());
        // Go through the whole grid counting until we hit the pth non-avoided point
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                Point q = new Point(i, j);
                if (p == 0) return q;
                if (!avoid.contains(q)) p--;
            }
        }
        return null;
    }
}
