package com.github.creme332.tests.utils;

import static org.junit.Assert.assertEquals;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

public class TestHelper {
    private TestHelper() {
    }

    /**
     * Compares two arrays of pixel coordinates and outputs the missing or extra
     * pixels in the actualPixels array.
     * Additionally, it identifies any duplicate pixels in the actualPixels array.
     * 
     * This method does NOT perform JUnit assertions.
     * 
     * @param expectedPixels The expected array of pixel coordinates. 2D array of
     *                       pixels where each element is in the form {x, y}
     * @param actualPixels   The actual array of pixel coordinates to be compared
     *                       with the expected array.2D array of pixels where each
     *                       element is in the form {x, y}
     */
    public static void compare2DArraysDebug(int[][] expectedPixels, int[][] actualPixels) {
        // Set to store the expected pixel coordinates
        Set<Point> expectedSet = new HashSet<>();
        // Populate the set with expected pixel coordinates
        for (int[] coord : expectedPixels) {
            expectedSet.add(new Point(coord[0], coord[1]));
        }

        // Set to store the actual pixel coordinates
        Set<Point> actualSet = new HashSet<>();

        // Set to store duplicate actual pixel coordinates
        Set<Point> duplicatePixels = new HashSet<>();
        // Populate the set with actual pixel coordinates
        for (int[] coord : actualPixels) {
            Point pixel = new Point(coord[0], coord[1]);
            // Check if the pixel already exists in the actualSet
            if (!actualSet.add(pixel)) {
                duplicatePixels.add(pixel);
            }
        }

        // Set to store missing pixels in actualPixels
        Set<Point> missingPixels = new HashSet<>(expectedSet);
        missingPixels.removeAll(actualSet);

        // Set to store extra pixels in actualPixels
        Set<Point> extraPixels = new HashSet<>(actualSet);
        extraPixels.removeAll(expectedSet);

        // Check if the sets are equal or not
        if (missingPixels.isEmpty() && extraPixels.isEmpty() && duplicatePixels.isEmpty()) {
            System.out.println("The pixel sets are equal.");
        } else {
            // Print missing pixels if any
            if (!missingPixels.isEmpty()) {
                System.out.println("Missing elements in actualPixels:");
                for (Point p : missingPixels) {
                    System.out.printf("[%d, %d]%n", p.x, p.y);
                }
            }
            // Print extra pixels if any
            if (!extraPixels.isEmpty()) {
                System.out.println("Extra elements in actualPixels:");
                for (Point p : extraPixels) {
                    System.out.printf("[%d, %d]%n", p.x, p.y);
                }
            }
            // Print duplicate pixels if any
            if (!duplicatePixels.isEmpty()) {
                System.out.println("Duplicate elements in actualPixels:");
                for (Point p : duplicatePixels) {
                    System.out.printf("[%d, %d]%n", p.x, p.y);
                }
            }
        }
    }

    /**
     * Asserts that 2 arrays of pixels are identical. Order of pixels is ignored.
     * 
     * @param expectedPixels 2D array of pixels where each element is in the form
     *                       {x, y}
     * @param actualPixels   2D array of pixels where each element is in the form
     *                       {x, y}
     */
    public static void assert2DArrayEquals(int[][] expectedPixels, int[][] actualPixels) {
        // Set to store the expected pixel coordinates
        Set<Point> expectedSet = new HashSet<>();
        // Populate the set with expected pixel coordinates
        for (int[] coord : expectedPixels) {
            expectedSet.add(new Point(coord[0], coord[1]));
        }

        // Set to store the actual pixel coordinates
        Set<Point> actualSet = new HashSet<>();
        // Populate the set with actual pixel coordinates
        for (int[] coord : actualPixels) {
            actualSet.add(new Point(coord[0], coord[1]));
        }

        assertEquals(expectedSet, actualSet);
    }
}