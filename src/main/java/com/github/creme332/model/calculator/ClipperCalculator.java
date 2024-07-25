package com.github.creme332.model.calculator;

/**
 * The Clipper class implements the Cohen-Sutherland line clipping algorithm.
 */
public class ClipperCalculator {
    
    private ClipperCalculator() {
        // Private constructor to prevent instantiation
    }

    // Region codes
    private static final int INSIDE = 0; // 0000
    private static final int LEFT = 1;   // 0001
    private static final int RIGHT = 2;  // 0010
    private static final int BOTTOM = 4; // 0100
    private static final int TOP = 8;    // 1000

    /**
     * Computes the region code for a point (x, y) using the given clipping rectangle.
     * 
     * @param x x-coordinate of the point
     * @param y y-coordinate of the point
     * @param xmin x-coordinate of the rectangle's minimum boundary
     * @param ymin y-coordinate of the rectangle's minimum boundary
     * @param xmax x-coordinate of the rectangle's maximum boundary
     * @param ymax y-coordinate of the rectangle's maximum boundary
     * @return The region code of the point.
     */
    private static int computeCode(double x, double y, double xmin, double ymin, double xmax, double ymax) {
        int code = INSIDE;

        if (x < xmin) code |= LEFT;
        else if (x > xmax) code |= RIGHT;
        if (y < ymin) code |= BOTTOM;
        else if (y > ymax) code |= TOP;

        return code;
    }

    /**
     * Clips a line from (x0, y0) to (x1, y1) using the Cohen-Sutherland algorithm
     * against a rectangular clipping area defined by (xmin, ymin) and (xmax, ymax).
     * 
     * @param x0 x-coordinate of the line's start point
     * @param y0 y-coordinate of the line's start point
     * @param x1 x-coordinate of the line's end point
     * @param y1 y-coordinate of the line's end point
     * @param xmin x-coordinate of the rectangle's minimum boundary
     * @param ymin y-coordinate of the rectangle's minimum boundary
     * @param xmax x-coordinate of the rectangle's maximum boundary
     * @param ymax y-coordinate of the rectangle's maximum boundary
     * @return The clipped line's start and end points, or null if the line is outside the clipping area.
     */
    public static double[][] clip(double x0, double y0, double x1, double y1,
                                  double xmin, double ymin, double xmax, double ymax) {
        int code0 = computeCode(x0, y0, xmin, ymin, xmax, ymax);
        int code1 = computeCode(x1, y1, xmin, ymin, xmax, ymax);
        boolean accept = false;

        while (true) {
            if ((code0 | code1) == 0) {
                accept = true;
                break;
            } else if ((code0 & code1) != 0) {
                break;
            } else {
                double x = 0, y = 0;
                int outcode = (code0 != 0) ? code0 : code1;

                if ((outcode & TOP) != 0) {
                    x = x0 + (x1 - x0) * (ymax - y0) / (y1 - y0);
                    y = ymax;
                } else if ((outcode & BOTTOM) != 0) {
                    x = x0 + (x1 - x0) * (ymin - y0) / (y1 - y0);
                    y = ymin;
                } else if ((outcode & RIGHT) != 0) {
                    y = y0 + (y1 - y0) * (xmax - x0) / (x1 - x0);
                    x = xmax;
                } else if ((outcode & LEFT) != 0) {
                    y = y0 + (y1 - y0) * (xmin - x0) / (x1 - x0);
                    x = xmin;
                }

                if (outcode == code0) {
                    x0 = x;
                    y0 = y;
                    code0 = computeCode(x0, y0, xmin, ymin, xmax, ymax);
                } else {
                    x1 = x;
                    y1 = y;
                    code1 = computeCode(x1, y1, xmin, ymin, xmax, ymax);
                }
            }
        }

        if (accept) {
            return new double[][]{{x0, y0}, {x1, y1}};
        } else {
            return null; // Line is completely outside
        }
    }
}
