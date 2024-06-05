package com.github.creme332.algorithms;

public class CircleAlgorithm {

    public static int[][] drawCircle(int centerX, int centerY, int radius) {
        if (radius < 0) {
            throw new IllegalArgumentException("Radius cannot be negative");
        }

        int diameter = 2 * radius + 1;
        int[][] pixels = new int[diameter][diameter];

        int x = radius;
        int y = 0;
        int decisionParameter = 1 - radius;

        while (x >= y) {
            plotCirclePoints(pixels, centerX, centerY, x, y);
            y++;

            if (decisionParameter <= 0) {
                decisionParameter += 2 * y + 1;
            } else {
                x--;
                decisionParameter += 2 * (y - x) + 1;
            }
        }

        return pixels;
    }

    private static void plotCirclePoints(int[][] pixels, int centerX, int centerY, int x, int y) {
        int diameter = pixels.length;
        plotPoint(pixels, centerX + x, centerY + y, diameter);
        plotPoint(pixels, centerX - x, centerY + y, diameter);
        plotPoint(pixels, centerX + x, centerY - y, diameter);
        plotPoint(pixels, centerX - x, centerY - y, diameter);
        plotPoint(pixels, centerX + y, centerY + x, diameter);
        plotPoint(pixels, centerX - y, centerY + x, diameter);
        plotPoint(pixels, centerX + y, centerY - x, diameter);
        plotPoint(pixels, centerX - y, centerY - x, diameter);
    }

    private static void plotPoint(int[][] pixels, int x, int y, int diameter) {
        if (x >= 0 && x < diameter && y >= 0 && y < diameter) {
            pixels[x][y] = 1;
        }
    }
}
