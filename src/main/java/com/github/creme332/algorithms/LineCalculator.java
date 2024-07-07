package com.github.creme332.algorithms;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class LineCalculator {
    private LineCalculator() {
    }

    /**
     * Calculates pixels between any 2 points (x0, y0) and (x1, y1) using the DDA
     * line algorithm. High precision calculations are used to reduce floating point
     * errors.
     * 
     * @param x0 x-coordinate of start point
     * @param y0 y-coordinate of start point
     * @param x1 x-coordinate of end point
     * @param y1 y-coordinate of end point
     * @return A 2D array with 2 elements. The first element is the array of
     *         x-coordinates and the second element is the array of y-coordinates.
     */
    public static int[][] dda(int x0, int y0, int x1, int y1) {
        final int dx = x1 - x0;
        final int dy = y1 - y0;
        final int steps = Math.max(Math.abs(dx), Math.abs(dy));

        final BigDecimal xInc = BigDecimal.valueOf(dx).divide(BigDecimal.valueOf(steps), MathContext.DECIMAL128);
        final BigDecimal yInc = BigDecimal.valueOf(dy).divide(BigDecimal.valueOf(steps), MathContext.DECIMAL128);

        BigDecimal x = BigDecimal.valueOf(x0);
        BigDecimal y = BigDecimal.valueOf(y0);

        int[] xpoints = new int[steps + 1];
        int[] ypoints = new int[steps + 1];

        for (int i = 0; i <= steps; i++) {
            xpoints[i] = x.setScale(0, RoundingMode.HALF_UP).intValue();
            ypoints[i] = y.setScale(0, RoundingMode.HALF_UP).intValue();

            x = x.add(xInc);
            y = y.add(yInc);
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
