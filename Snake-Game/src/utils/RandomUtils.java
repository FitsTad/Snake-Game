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
     * @param x maximum x
     * @param y maximum y
     * @param avoid points to avoid
     * @return a random point in range and not avoided
     */
    public static final Point randpoint(int x, int y, Set<Point> avoid) {
        Point rp;
        do {
            rp = new Point(randint(x), randint(y));
        } while (avoid.contains(rp));
        return rp;
    }
}
