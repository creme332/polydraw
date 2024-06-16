package com.github.creme332.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CircleAlgorithm {

    private HashMap<Integer, List<List<Integer>>> cache;

    public CircleAlgorithm() {
        cache = new HashMap<>();
    }

    /**
     * Calculates coordinates of circle starting from top and moving clockwise.
     * 
     * @param centerX x-coordinate of circle center
     * @param centerY y-coordinate of circle center
     * @param radius  radius of circle
     * @return 2 arrays. The first array is the list of x-coordinates and the second
     *         array is a list of y-coordinates.
     */
    public int[][] calculateOrderedPoints(int centerX, int centerY, int radius) {
        // check if result is already available and return it
        if (cache.containsKey(radius)) {
            List<Integer> xPoints = cache.get(radius).get(0);
            List<Integer> yPoints = cache.get(radius).get(1);
            int[] xArray = xPoints.stream().mapToInt(i -> i + centerX).toArray();
            int[] yArray = yPoints.stream().mapToInt(i -> i + centerY).toArray();
            return new int[][] { xArray, yArray };
        }

        List<int[]> pixelList = calculateFirstOctant(radius);
        List<Integer> xPoints = new ArrayList<>();
        List<Integer> yPoints = new ArrayList<>();

        // add first octant
        for (int i = 0; i < pixelList.size(); i++) {
            int x = pixelList.get(i)[0];
            int y = pixelList.get(i)[1];
            xPoints.add(x);
            yPoints.add(y);
        }

        // add second octant
        for (int i = pixelList.size() - 1; i > -1; i--) {
            int x = pixelList.get(i)[0];
            int y = pixelList.get(i)[1];
            if (x == y)
                continue;
            xPoints.add(y);
            yPoints.add(x);
        }

        // add third octant
        for (int i = 0; i < pixelList.size(); i++) {
            int x = pixelList.get(i)[0];
            int y = pixelList.get(i)[1];
            if (x == 0 || x == y)
                continue;
            xPoints.add(y);
            yPoints.add(-x);
        }

        // add fourth octant
        for (int i = pixelList.size() - 1; i > -1; i--) {
            int x = pixelList.get(i)[0];
            int y = pixelList.get(i)[1];
            xPoints.add(x);
            yPoints.add(-y);
        }

        // add 5th octant
        for (int i = 0; i < pixelList.size(); i++) {
            int x = pixelList.get(i)[0];
            int y = pixelList.get(i)[1];
            if (x == 0)
                continue;
            xPoints.add(-x);
            yPoints.add(-y);
        }

        // add 6th octant
        for (int i = pixelList.size() - 1; i > -1; i--) {
            int x = pixelList.get(i)[0];
            int y = pixelList.get(i)[1];
            if (x == 0 || x == y)
                continue;
            xPoints.add(-y);
            yPoints.add(-x);
        }

        // add 7th octant
        for (int i = 0; i < pixelList.size(); i++) {
            int x = pixelList.get(i)[0];
            int y = pixelList.get(i)[1];
            if (x == y)
                continue;
            xPoints.add(-y);
            yPoints.add(x);
        }

        // add 8th octant
        for (int i = pixelList.size() - 1; i > -1; i--) {
            int x = pixelList.get(i)[0];
            int y = pixelList.get(i)[1];
            if (x == 0)
                continue;
            xPoints.add(-x);
            yPoints.add(y);
        }

        List<List<Integer>> res = new ArrayList<>();
        res.add(xPoints);
        res.add(yPoints);
        cache.put(radius, res);

        return calculateOrderedPoints(centerX, centerY, radius);
    }

    /**
     * Calculates coordinates of pixels in first octant of circle centered at
     * origin. Bresenham algorithm is used.
     * 
     * @param radius Radius of circle
     * @return A 2D array where each element is an array {x, y} representing the x
     *         and y coordinates of a pixel.
     */
    private static List<int[]> calculateFirstOctant(int radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius must be positive");
        }

        List<int[]> pixelList = new ArrayList<>();

        int x = 0;
        int y = radius;
        int decisionParameter = 1 - radius;

        while (x <= y) {
            pixelList.add(new int[] { x, y });
            x++;

            if (decisionParameter < 0) {
                decisionParameter += 2 * x + 1;
            } else {
                y--;
                decisionParameter += 2 * (x - y) + 1;
            }
        }

        return pixelList;
    }

    public static int[][] drawCircle(int centerX, int centerY, int radius) {
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

    private static void plotCirclePoints(List<int[]> pixelList, int centerX, int centerY, int x, int y) {
        if (x == 0) {
            addUniquePixel(pixelList, centerX + x, centerY + y);
            addUniquePixel(pixelList, centerX + y, centerY + x);
            addUniquePixel(pixelList, centerX + x, centerY - y);
            addUniquePixel(pixelList, centerX - y, centerY + x);
            return;
        }

        if (x == y) {
            addUniquePixel(pixelList, centerX + x, centerY + y);
            addUniquePixel(pixelList, centerX - x, centerY + y);
            addUniquePixel(pixelList, centerX + x, centerY - y);
            addUniquePixel(pixelList, centerX - x, centerY - y);
            return;
        }

        addUniquePixel(pixelList, centerX + x, centerY + y);
        addUniquePixel(pixelList, centerX - x, centerY + y);
        addUniquePixel(pixelList, centerX + x, centerY - y);
        addUniquePixel(pixelList, centerX - x, centerY - y);

        addUniquePixel(pixelList, centerX + y, centerY + x);
        addUniquePixel(pixelList, centerX - y, centerY + x);
        addUniquePixel(pixelList, centerX + y, centerY - x);
        addUniquePixel(pixelList, centerX - y, centerY - x);
    }

    private static void addUniquePixel(List<int[]> pixelList, int x, int y) {
        pixelList.add(new int[] { x, y });
    }
}