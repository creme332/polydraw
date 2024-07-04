package com.github.creme332.tests.model.calculator;

import org.junit.Test;

import com.github.creme332.model.calculator.CircleCalculator;
import com.github.creme332.tests.utils.TestHelper;

import static org.junit.Assert.*;

public class CircleCalculatorTest {

    @Test
    public void testValidCircleCenteredOrigin() {
        int centerX = 0;
        int centerY = 0;
        int radius = 10;
        int[][] actualPixels = CircleCalculator.getAllPoints(centerX, centerY, radius);

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

        // Using the helper method for assertion
        TestHelper.assert2DArrayEquals(expectedPixels, actualPixels);
    }

    @Test
    public void testZeroRadiusCenteredOrigin() {
        int centerX = 0;
        int centerY = 0;
        int radius = 0;

        try {
            CircleCalculator.getAllPoints(centerX, centerY, radius);
            fail("Expected IllegalArgumentException for zero radii");
        } catch (IllegalArgumentException e) {
            assertEquals("Radius must be positive", e.getMessage());
        }
    }

    @Test
    public void testNegativeRadiusCenteredOrigin() {
        int centerX = 0;
        int centerY = 0;
        int radius = -5;
        try {
            CircleCalculator.getAllPoints(centerX, centerY, radius);
            fail("Expected IllegalArgumentException for negative radii");
        } catch (IllegalArgumentException e) {
            assertEquals("Radius must be positive", e.getMessage());
        }
    }

    @Test
    public void testCircleCenteredOriginRadiusOne() {
        int centerX = 0;
        int centerY = 0;
        int radius = 1;
        int[][] actualPixels = CircleCalculator.getAllPoints(centerX, centerY, radius);

        int[][] expectedPixels = {
                { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 }
        };

        TestHelper.assert2DArrayEquals(expectedPixels, actualPixels);
    }

    @Test
    public void testCircleNotCenteredOriginRadiusOne() {
        int centerX = 5;
        int centerY = 5;
        int radius = 1;
        int[][] actualPixels = CircleCalculator.getAllPoints(centerX, centerY, radius);

        int[][] expectedPixels = {
                { 6, 5 }, { 4, 5 }, { 5, 6 }, { 5, 4 }
        };

        TestHelper.assert2DArrayEquals(expectedPixels, actualPixels);
    }
}
