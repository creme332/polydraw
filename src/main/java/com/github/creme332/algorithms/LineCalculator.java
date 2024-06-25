package com.github.creme332.algorithms;

public class LineCalculator {
    private LineCalculator() {

    }

    public static int[][] DDA(int x0, int y0, int x1, int y1) {
        int dx = x1 - x0;
        int dy = y1 - y0;
        int steps = Math.max(Math.abs(dx), Math.abs(dy));

        float xInc = (float) dx / steps;
        float yInc = (float) dy / steps;

        float x = x0;
        float y = y0;

        int[][] pixelCoords = new int[steps + 1][2];

        for (int i = 0; i <= steps; i++) {
            pixelCoords[i][0] = Math.round(x);
            pixelCoords[i][1] = Math.round(y);
            x += xInc;
            y += yInc;
        }

        return pixelCoords;
    }

    public static int[][] bresenham(int x0, int y0, int x1, int y1) {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);

        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;

        int err = dx - dy;

        int[][] pixelCoords = new int[dx + dy + 1][2];
        int index = 0;

        while (true) {
            pixelCoords[index][0] = x0;
            pixelCoords[index][1] = y0;
            index++;

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

        return pixelCoords;
    }
}
