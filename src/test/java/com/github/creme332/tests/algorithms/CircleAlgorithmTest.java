package com.github.creme332.tests.algorithms;

import org.junit.Test;

import static org.junit.Assert.*;
import com.github.creme332.algorithms.CircleAlgorithm;

public class CircleAlgorithmTest {

    @Test
    public void testDrawCircle_positiveRadius() {
        int centerX = 5;
        int centerY = 5;
        int radius = 5;
        int[][] pixels = CircleAlgorithm.drawCircle(centerX, centerY, radius);

        assertEquals(2 * radius + 1, pixels.length); // Check array size
        assertEquals(2 * radius + 1, pixels[0].length);

        int[][] expectedPixels = new int[][] {
            {0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0},
            {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1},
            {1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1},
            {1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1},
            {0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0},
            {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0}
        };

        for (int i = 0; i < pixels.length; i++) {
            assertArrayEquals(expectedPixels[i], pixels[i]);
        }
    }

    @Test
    public void testDrawCircle_zeroRadius() {
        int centerX = 0;
        int centerY = 0;
        int radius = 0;
        int[][] pixels = CircleAlgorithm.drawCircle(centerX, centerY, radius);

        assertEquals(1, pixels.length); // Single pixel for radius 0
        assertEquals(1, pixels[0].length);
        assertTrue(pixels[0][0] == 1); // Check center pixel
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDrawCircle_negativeRadius() {
        int centerX = 0;
        int centerY = 0;
        int radius = -5;
        CircleAlgorithm.drawCircle(centerX, centerY, radius);
    }
}
