package com.github.creme332.tests.algorithms;

import org.junit.Test;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import com.github.creme332.algorithms.CircleAlgorithm;

public class CircleAlgorithmTest {

    @Test
    public void testDrawCircle_positiveRadius() {
        int centerX = 5;
        int centerY = 5;
        int radius = 5;
        int[][] pixels = CircleAlgorithm.drawCircle(centerX, centerY, radius);

        Set<String> expectedPixels = new HashSet<>(Arrays.asList(
            "[10, 5]", "[5, 10]", "[0, 5]", "[5, 0]", 
            "[9, 6]", "[6, 9]", "[1, 6]", "[6, 1]",
            "[8, 7]", "[7, 8]", "[2, 7]", "[7, 2]",
            "[8, 3]", "[7, 4]", "[2, 3]", "[7, 6]",
            "[8, 1]", "[7, 0]", "[2, 1]", "[7, 10]", 
            "[9, 4]", "[6, 5]", "[1, 4]", "[6, 10]",
            "[10, 6]", "[5, 9]", "[0, 6]", "[5, 1]",
            "[10, 4]", "[5, 7]", "[0, 4]", "[5, 3]"
        ));

        Set<String> actualPixels = new HashSet<>();
        for (int[] pixel : pixels) {
            actualPixels.add(Arrays.toString(pixel));
        }

        // Debugging output for easier comparison
        if (!expectedPixels.equals(actualPixels)) {
            System.out.println("Expected Pixels: " + expectedPixels);
            System.out.println("Actual Pixels: " + actualPixels);
            for (int[] pixel : pixels) {
                System.out.println(Arrays.toString(pixel));
            }
        }

        assertEquals(expectedPixels, actualPixels);
    }

    @Test
    public void testDrawCircle_zeroRadius() {
        int centerX = 0;
        int centerY = 0;
        int radius = 0;
        int[][] pixels = CircleAlgorithm.drawCircle(centerX, centerY, radius);

        assertEquals(1, pixels.length); // Single pixel for radius 0
        assertArrayEquals(new int[]{0, 0}, pixels[0]); // Check center pixel
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDrawCircle_negativeRadius() {
        int centerX = 0;
        int centerY = 0;
        int radius = -5;
        CircleAlgorithm.drawCircle(centerX, centerY, radius);
    }
}
