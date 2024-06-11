package com.github.creme332.tests.algorithms;

import org.junit.Test;
import static org.junit.Assert.*;

import com.github.creme332.algorithms.EllipseAlgorithm;
import com.github.creme332.tests.utils.TestHelper;

import java.util.List;

public class EllipseAlgorithmTest {

    @Test
    public void testEllipseCenteredAtOrigin() {
        List<int[]> pixels = EllipseAlgorithm.drawEllipse(0, 0, 3, 2);

        int[][] expectedArray = {
                // Quadrant 1
                { 0, 2 }, { 1, 2 }, { 2, 1 },
                // Quadrant 2
                { 3, 0 }, { 2, -1 }, { 1, -2 },
                // Quadrant 3
                { 0, -2 }, { -1, -2 }, { -2, -1 },
                // Quadrant 4
                { -3, 0 }, { -2, 1 }, { -1, 2 }
        };

        TestHelper.assert2DArrayEquals(expectedArray, pixels.toArray(new int[pixels.size()][]));
    }

    @Test
    public void testEllipseNotAtOrigin() {
        int centerX = 5;
        int centerY = 7;

        // coordinates if ellipse with rx = 8 and ry = 6 was centered at origin
        int[][] expectedArray = {
                // Quadrant 1
                { 0, 6 }, { 1, 6 }, { 2, 6 }, { 3, 6 }, { 4, 5 }, { 5, 5 }, { 6, 4 }, { 7, 3 }, { 8, 2 }, { 8, 1 },
                { 8, 0 },
                // Quadrant 2
                { 8, -1 }, { 8, -2 }, { 7, -3 }, { 6, -4 }, { 5, -5 }, { 4, -5 }, { 3, -6 }, { 2, -6 }, { 1, -6 },
                { 0, -6 },
                // Quadrant 3
                { -1, -6 }, { -2, -6 }, { -3, -6 }, { -4, -5 }, { -5, -5 }, { -6, -4 }, { -7, -3 }, { -8, -2 },
                { -8, -1 }, { -8, 0 },
                // Quadrant 4
                { -8, 1 }, { -8, 2 }, { -7, 3 }, { -6, 4 }, { -5, 5 }, { -4, 5 }, { -3, 6 }, { -2, 6 }, { -1, 6 }
        };
        // perform translation
        for (int i = 0; i < expectedArray.length; i++) {
            expectedArray[i][0] += centerX;
            expectedArray[i][1] += centerY;
        }

        List<int[]> pixels = EllipseAlgorithm.drawEllipse(centerX, centerY, 8, 6);
        TestHelper.assert2DArrayEquals(expectedArray, pixels.toArray(new int[pixels.size()][]));
    }

    @Test
    public void testZeroRadii() {
        try {
            EllipseAlgorithm.drawEllipse(10, 10, 0, 0);
            fail("Expected IllegalArgumentException for zero radii");
        } catch (IllegalArgumentException e) {
            assertEquals("Radii must be positive values.", e.getMessage());
        }
    }

    @Test
    public void testNegativeRadii() {
        try {
            EllipseAlgorithm.drawEllipse(10, 10, -5, -3);
            fail("Expected IllegalArgumentException for negative radii");
        } catch (IllegalArgumentException e) {
            assertEquals("Radii must be positive values.", e.getMessage());
        }
    }

    @Test
    public void testHorizontalEllipseAtOrigin() {
        List<int[]> pixels = EllipseAlgorithm.drawEllipse(0, 0, 7, 3);

        int[][] expectedArray = {
                // Quadrant 1
                { 0, 3 }, { 1, 3 }, { 2, 3 }, { 3, 3 }, { 4, 2 }, { 5, 2 }, { 6, 2 }, { 7, 1 },
                // Quadrant 2
                { 7, 0 }, { 7, -1 }, { 6, -2 }, { 5, -2 }, { 4, -2 }, { 3, -3 }, { 2, -3 }, { 1, -3 }, { 0, -3 },
                // Quadrant 3
                { -1, -3 }, { -2, -3 }, { -3, -3 }, { -4, -2 }, { -5, -2 }, { -6, -2 }, { -7, -1 },
                // Quadrant 4
                { -7, 0 }, { -7, 1 }, { -6, 2 }, { -5, 2 }, { -4, 2 }, { -3, 3 }, { -2, 3 }, { -1, 3 }
        };

        TestHelper.assert2DArrayEquals(expectedArray, pixels.toArray(new int[pixels.size()][]));
    }

    @Test
    public void testVerticalEllipseAtOrigin() {
        List<int[]> pixels = EllipseAlgorithm.drawEllipse(0, 0, 3, 7);

        int[][] expectedArray = {
                // Quadrant 1
                { 0, 7 }, { 1, 7 }, { 2, 6 }, { 2, 5 }, { 2, 4 }, { 3, 3 }, { 3, 2 }, { 3, 1 }, { 3, 0 },
                // Quadrant 2
                { 3, -1 }, { 3, -2 }, { 3, -3 }, { 2, -4 }, { 2, -5 }, { 2, -6 }, { 1, -7 }, { 0, -7 },
                // Quadrant 3
                { -1, -7 }, { -2, -6 }, { -2, -5 }, { -2, -4 }, { -3, -3 }, { -3, -2 }, { -3, -1 }, { -3, 0 },
                // Quadrant 4
                { -3, 1 }, { -3, 2 }, { -3, 3 }, { -2, 4 }, { -2, 5 }, { -2, 6 }, { -1, 7 }
        };

        TestHelper.assert2DArrayEquals(expectedArray, pixels.toArray(new int[pixels.size()][]));
    }
}
