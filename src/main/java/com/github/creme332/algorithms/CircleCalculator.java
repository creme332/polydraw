package com.github.creme332.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CircleCalculator {

    /**
     * A map that stores calculated coordinates of pixels in first octant of circles
     * of different radii
     * centered at origin.
     * 
     * The key is the radius and the value is a list with 2 elements. The first
     * element is a list of x-coordinates while the second element is a list of
     * y-coordinates.
     */
    private HashMap<Integer, List<List<Integer>>> firstOctantCache;

    public CircleCalculator() {
        firstOctantCache = new HashMap<>();
    }

    /**
     * Maps a point (x, y) in first octant to some other octant using 8-way symmetry
     * of circle.
     * 
     * @param x                 x-coordinate of a point in first octant.
     * @param y                 y-coordinate of a point in the first octant.
     * @param destinationOctant index of some octant (one-based index).
     * @return x and y coordinates of point in new octant.
     */
    public static int[] transformPoint(int x, int y, int destinationOctant) {
        switch (destinationOctant) {
            case 1:
                return new int[] { x, y };
            case 2:
                return new int[] { y, x };
            case 3:
                return new int[] { y, -x };
            case 4:
                return new int[] { x, -y };
            case 5:
                return new int[] { -x, -y };
            case 6:
                return new int[] { -y, -x };
            case 7:
                return new int[] { -y, x };
            case 8:
                return new int[] { -x, y };
            default:
                break;
        }
        throw new IllegalArgumentException("Octant must be between 1 and 8.");
    }

    /**
     * Calculates coordinates of circle starting from top and moving clockwise.
     * Points are ordered clockwise. Duplicate points may occur at x==0 and x==y.
     * 
     * @param centerX x-coordinate of circle center
     * @param centerY y-coordinate of circle center
     * @param radius  radius of circle
     * @return 2 arrays. The first array is the list of x-coordinates and the second
     *         array is a list of y-coordinates.
     */
    public int[][] getOrderedPoints(int centerX, int centerY, int radius) {
        /**
         * Pixels in first octant of circle centered at origin.
         */
        final List<List<Integer>> firstOctantList = getFirstOctantPoints(radius);

        /**
         * Number of pixels in first octant
         */
        final int firstOctantSize = firstOctantList.get(0).size();

        List<Integer> xPoints = new ArrayList<>();
        List<Integer> yPoints = new ArrayList<>();

        for (int octant = 1; octant <= 8; octant++) {
            for (int i = 0; i < firstOctantSize; i++) {
                int pixelIndex = (octant % 2 == 0) ? firstOctantSize - i - 1 : i;
                int x = firstOctantList.get(0).get(pixelIndex);
                int y = firstOctantList.get(1).get(pixelIndex);

                int[] transformedPoint = transformPoint(x, y, octant);
                xPoints.add(transformedPoint[0]);
                yPoints.add(transformedPoint[1]);
            }
        }

        // convert arrays to primitive arrays and apply translation based on circle
        // center coordinates
        int[] xArray = xPoints.stream().mapToInt(i -> i + centerX).toArray();
        int[] yArray = yPoints.stream().mapToInt(i -> i + centerY).toArray();

        return new int[][] { xArray, yArray };
    }

    /**
     * Calculates coordinates of pixels in first octant of circle centered at
     * origin. Bresenham algorithm is used.
     * 
     * @param radius Radius of circle
     * @return A 2D array where the first list contains all the x-coordinates and
     *         the second list contains all the y-coordinates.
     */
    public List<List<Integer>> getFirstOctantPoints(int radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius must be positive");
        }

        // check if result is already available in cache and return it
        if (firstOctantCache.containsKey(radius)) {
            return firstOctantCache.get(radius);
        }

        List<Integer> xPoints = new ArrayList<>();
        List<Integer> yPoints = new ArrayList<>();

        int x = 0;
        int y = radius;
        int decisionParameter = 1 - radius;

        while (x <= y) {
            xPoints.add(x);
            yPoints.add(y);
            x++;

            if (decisionParameter < 0) {
                decisionParameter += 2 * x + 1;
            } else {
                y--;
                decisionParameter += 2 * (x - y) + 1;
            }
        }

        // save result to cache
        List<List<Integer>> result = new ArrayList<>();
        result.add(xPoints);
        result.add(yPoints);
        firstOctantCache.put(radius, result);

        return result;
    }

    /**
     * Calculates all coordinates of pixels for circle. Points are not ordered in
     * any way.
     * 
     * @param centerX x-coordinate of center
     * @param centerY y-coordinate of center
     * @param radius  radius of circle
     * @return
     */
    public static int[][] getAllPoints(int centerX, int centerY, int radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius must be positive");
        }

        List<int[]> pixelList = new ArrayList<>();

        int x = 0;
        int y = radius;
        int decisionParameter = 1 - radius;

        while (x <= y) {
            plotCirclePoints(pixelList, centerX, centerY, x, y);
            x++;

            if (decisionParameter < 0) {
                decisionParameter += 2 * x + 1;
            } else {
                y--;
                decisionParameter += 2 * (x - y) + 1;
            }
        }

        return pixelList.toArray(new int[pixelList.size()][]);
    }

    /**
     * Adds a point (x, y) in first octant as well as its corresponding points in
     * other octants to an array. Duplicates are prevented.
     * 
     * 
     * @param pixelList Array storing points
     * @param centerX   x-coordinate of center
     * @param centerY   y-coordinate of center
     * @param x         x-coordinate of a point in first octant
     * @param y         y-coordinate of a point in first octant
     */
    private static void plotCirclePoints(List<int[]> pixelList, int centerX, int centerY, int x, int y) {
        if (x == 0) {
            addToArray(pixelList, centerX + x, centerY + y);
            addToArray(pixelList, centerX + y, centerY + x);
            addToArray(pixelList, centerX + x, centerY - y);
            addToArray(pixelList, centerX - y, centerY + x);
            return;
        }

        if (x == y) {
            addToArray(pixelList, centerX + x, centerY + y);
            addToArray(pixelList, centerX - x, centerY + y);
            addToArray(pixelList, centerX + x, centerY - y);
            addToArray(pixelList, centerX - x, centerY - y);
            return;
        }

        addToArray(pixelList, centerX + x, centerY + y);
        addToArray(pixelList, centerX - x, centerY + y);
        addToArray(pixelList, centerX + x, centerY - y);
        addToArray(pixelList, centerX - x, centerY - y);

        addToArray(pixelList, centerX + y, centerY + x);
        addToArray(pixelList, centerX - y, centerY + x);
        addToArray(pixelList, centerX + y, centerY - x);
        addToArray(pixelList, centerX - y, centerY - x);
    }

    private static void addToArray(List<int[]> pixelList, int x, int y) {
        pixelList.add(new int[] { x, y });
    }
}