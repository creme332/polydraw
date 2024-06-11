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