package com.github.creme332.algorithms;

import java.util.ArrayList;
import java.util.List;

public class LineCalculator {
    private LineCalculator() {
    }

    /**
     * Rounds the given value to the nearest integer, mimicking the behavior of the
     * C round function.
     * This method rounds ties away from zero unlike Math.round().
     *
     * <p>
     * Examples:
     * 
     * <pre>
     * round(-5.5); // returns -6
     * round(-2.5); // returns -3
     * round(-1.4); // returns -1
     * round(0.5); // returns 1
     * round(1.5); // returns 2
     * round(2.4); // returns 2
     * round(3.5); // returns 4
     * </pre>
     *
     * @param value the value to be rounded
     * @return the value rounded to the nearest integer
     */
    public static int round(double value) {
        if (value >= 0) {
            return (int) Math.floor(value + 0.5);
        } else {
            return (int) Math.ceil(value - 0.5);
        }
    }

    /**
     * Calculates pixels between any 2 points (x0, y0) and (x1, y1) using the DDA
     * line algorithm.
     * 
     * @param x0 x-coordinate of start point
     * @param y0 y-coordinate of tart point
     * @param x1 x-coordinate of end point
     * @param y1 y-coordinate of end point
     * @return A 2D array with 2 elements. The first element is the array of
     *         x-coordinates and the second element is the array of y-coordinates.
     */
    public static int[][] dda(int x0, int y0, int x1, int y1) {
        int dx = x1 - x0;
        int dy = y1 - y0;
        int steps = Math.max(Math.abs(dx), Math.abs(dy));

        double xInc = (double) dx / steps;
        double yInc = (double) dy / steps;

        double x = x0;
        double y = y0;

        int[] xpoints = new int[steps + 1];
        int[] ypoints = new int[steps + 1];

        for (int i = 0; i <= steps; i++) {
            xpoints[i] = round(x);
            ypoints[i] = round(y);

            x += xInc;
            y += yInc;
        }

        return new int[][] { xpoints, ypoints };
    }

    /**
     * Calculates pixels between any 2 points (x0, y0) and (x1, y1) using the
     * Bresenham line algorithm.
     * 
     * @param x0 x-coordinate of start point
     * @param y0 y-coordinate of tart point
     * @param x1 x-coordinate of end point
     * @param y1 y-coordinate of end point
     * @return A 2D array with 2 elements. The first element is the array of
     *         x-coordinates and the second element is the array of y-coordinates.
     */
    public static int[][] bresenham(int x0, int y0, int x1, int y1) {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);

        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;

        int err = dx - dy;

        List<Integer> xpoints = new ArrayList<>();
        List<Integer> ypoints = new ArrayList<>();

        while (true) {
            xpoints.add(x0);
            ypoints.add(y0);

            if (x0 == x1 && y0 == y1)
                break;

            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x0 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y0 += sy;
            }
        }

        return new int[][] { xpoints.stream().mapToInt(i -> i).toArray(), ypoints.stream().mapToInt(i -> i).toArray() };
    }
}
