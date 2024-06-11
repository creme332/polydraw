package com.github.creme332.algorithms;

import java.util.ArrayList;
import java.util.List;

public class CircleAlgorithm {

    private CircleAlgorithm() {
    }

    public static int[][] drawCircle(int centerX, int centerY, int radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius must be positive");
        }

        List<int[]> pixelList = new ArrayList<>();

        int x = 0;
        int y = radius;
        int decisionParameter = 1 - radius;

        while (y >= x) {
            plotCirclePoints(pixelList, centerX, centerY, x, y);
            x++;

            if (decisionParameter <= 0) {
                decisionParameter += 2 * x + 1;
            } else {
                y--;
                decisionParameter += 2 * (x - y) + 1;
            }
        }

        return pixelList.toArray(new int[pixelList.size()][]);
    }

    private static void plotCirclePoints(List<int[]> pixelList, int centerX, int centerY, int x, int y) {
        // For radius 1, only plot the points exactly at distance 1 from the center
        if (x == 0 && y == 1) {
            addUniquePixel(pixelList, centerX + y, centerY + x);
            addUniquePixel(pixelList, centerX - y, centerY + x);
            addUniquePixel(pixelList, centerX + y, centerY - x);
            addUniquePixel(pixelList, centerX - y, centerY - x);

            addUniquePixel(pixelList, centerX + x, centerY + y);
            addUniquePixel(pixelList, centerX - x, centerY + y);
            addUniquePixel(pixelList, centerX + x, centerY - y);
            addUniquePixel(pixelList, centerX - x, centerY - y);
            return;
        }

        addUniquePixel(pixelList, centerX + y, centerY + x);
        addUniquePixel(pixelList, centerX - y, centerY + x);
        addUniquePixel(pixelList, centerX + y, centerY - x);
        addUniquePixel(pixelList, centerX - y, centerY - x);

        addUniquePixel(pixelList, centerX + x, centerY + y);
        addUniquePixel(pixelList, centerX - x, centerY + y);
        addUniquePixel(pixelList, centerX + x, centerY - y);
        addUniquePixel(pixelList, centerX - x, centerY - y);
    }

    private static void addUniquePixel(List<int[]> pixelList, int x, int y) {
        pixelList.add(new int[] { x, y });
    }
}