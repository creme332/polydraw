package com.github.creme332.tests.algorithms;

import org.junit.Test;
import com.github.creme332.algorithms.CircleAlgorithm;
import com.github.creme332.tests.utils.TestHelper;

import static org.junit.Assert.*;

public class CircleAlgorithmTest {

    @Test
    public void testDrawCircle_positiveRadius() {
        int centerX = 0;
        int centerY = 0;
        int radius = 10;
        int[][] actualPixels = CircleAlgorithm.drawCircle(centerX, centerY, radius);

        int[][] expectedPixels = {
                // Octant 1: (x, y)
                { 0, 10 }, { 1, 10 }, { 2, 10 }, { 3, 10 }, { 4, 9 }, { 5, 9 }, { 6, 8 }, { 7, 7 },
                // Octant 2: (y, x)
                { 10, 0 }, { 10, 1 }, { 10, 2 }, { 10, 3 }, { 9, 4 }, { 9, 5 }, { 8, 6 },
                // Octant 3: (y, -x)
                { 10, -1 }, { 10, -2 }, { 10, -3 }, { 9, -4 }, { 9, -5 }, { 8, -6 }, { 7, -7 },
                // Octant 4: (x, -y)
                { 0, -10 }, { 1, -10 }, { 2, -10 }, { 3, -10 }, { 4, -9 }, { 5, -9 }, { 6, -8 },
                // Octant 5: (-x, -y)
                { -1, -10 }, { -2, -10 }, { -3, -10 }, { -4, -9 }, { -5, -9 }, { -6, -8 }, { -7, -7 },
                // Octant 6: (-y, -x)
                { -10, 0 }, { -10, -1 }, { -10, -2 }, { -10, -3 }, { -9, -4 }, { -9, -5 }, { -8, -6 },
                // Octant 7: (-y, x)
                { -10, 1 }, { -10, 2 }, { -10, 3 }, { -9, 4 }, { -9, 5 }, { -8, 6 }, { -7, 7 },
                // Octant 8: (-x, y)
                { -1, 10 }, { -2, 10 }, { -3, 10 }, { -4, 9 }, { -5, 9 }, { -6, 8 },
        };

        // Debugging with detailed output
        TestHelper.compare2DArraysDebug(expectedPixels, actualPixels);

        // Using the helper method for assertion
        TestHelper.assert2DArrayEquals(expectedPixels, actualPixels);
    }

    @Test
    public void testDrawCircle_zeroRadius() {
        int centerX = 0;
        int centerY = 0;
        int radius = 0;
        int[][] pixels = CircleAlgorithm.drawCircle(centerX, centerY, radius);

        assertEquals(1, pixels.length); // Single pixel for radius 0
        assertArrayEquals(new int[] { 0, 0 }, pixels[0]); // Check center pixel
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDrawCircle_negativeRadius() {
        int centerX = 0;
        int centerY = 0;
        int radius = -5;
        CircleAlgorithm.drawCircle(centerX, centerY, radius);
    }
}
