package com.github.creme332.algorithms;

import java.util.ArrayList;
import java.util.List;

public class CircleAlgorithm {

    public static int[][] drawCircle(int centerX, int centerY, int radius) {
        if (radius < 0) {
            throw new IllegalArgumentException("Radius cannot be negative");
        }

        List<int[]> pixelList = new ArrayList<>();

        if (radius == 0) {
            pixelList.add(new int[] { centerX, centerY });
        } else {
            int x = radius;
            int y = 0;
            int decisionParameter = 1 - radius;

            while (x >= y) {
                plotCirclePoints(pixelList, centerX, centerY, x, y);
                y++;

                if (decisionParameter <= 0) {
                    decisionParameter += 2 * y + 1;
                } else {
                    x--;
                    decisionParameter += 2 * (y - x) + 1;
                }
            }
        }

        return pixelList.toArray(new int[pixelList.size()][]);
    }

    private static void plotCirclePoints(List<int[]> pixelList, int centerX, int centerY, int x, int y) {
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
        for (int[] pixel : pixelList) {
            if (pixel[0] == x && pixel[1] == y) {
                return; // Pixel already exists, don't add it again
            }
        }
        pixelList.add(new int[] { x, y });
    }
}