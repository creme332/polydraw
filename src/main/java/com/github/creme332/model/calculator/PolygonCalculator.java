package com.github.creme332.model.calculator;

import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;

public class PolygonCalculator {

    /**
     * Caches coordinates regular polygons previously calculated.
     */
    private final Map<Map.Entry<Integer, Integer>, Shape> polygonCache = new HashMap<>();

    /**
     * Rotates a vector in the xy plane counterclockwise through an angle radAngle
     * about the origin.
     * 
     * @param vector   A point in the x-y plane.
     * @param radAngle Rotation angle in radians.
     * @return
     */
    public static Point2D rotateVector(Point2D vector, double radAngle) {
        return new Point2D.Double(
                vector.getX() * Math.cos(radAngle) - vector.getY() * Math.sin(radAngle),
                vector.getX() * Math.sin(radAngle) + vector.getY() * Math.cos(radAngle));
    }

    /**
     * Calculates coordinates of a regular polygon centered at origin.
     * 
     * @param sidesCount Number of sides in polygon
     * @param length     Side length of polygon
     * @return
     */
    private Shape getRegularPolygon(int sidesCount, int length) {
        if (polygonCache.containsKey(Map.entry(sidesCount, length))) {
            return polygonCache.get(Map.entry(sidesCount, length));
        }

        final double rotationAngleInRad = Math.toRadians(360.0 / sidesCount);
        Point2D.Double[] points = new Point2D.Double[sidesCount];

        points[0] = new Point2D.Double(
                length * Math.sin(rotationAngleInRad / 2),
                length * Math.cos(rotationAngleInRad / 2));

        for (int i = 1; i < sidesCount; i++) {
            // rotate the previous point by the required angle
            Point2D.Double vector = new Point2D.Double(points[i - 1].x, points[i - 1].y);
            Point2D rotatedVector = rotateVector(vector, rotationAngleInRad);
            points[i] = new Point2D.Double(rotatedVector.getX(), rotatedVector.getY());
        }

        int[] x = new int[sidesCount];
        int[] y = new int[sidesCount];
        for (int i = 0; i < sidesCount; i++) {
            x[i] = (int) Math.round(points[i].x);
            y[i] = (int) Math.round(points[i].y);
        }

        Polygon polygon = new Polygon(x, y, sidesCount);
        polygonCache.put(Map.entry(sidesCount, length), polygon);
        return polygon;
    }

    /**
     * Calculates integer coordinates of a regular polygon.
     * 
     * @param sidesCount number of sides
     * @param length     side length of polygon
     * @param centerX    x-coordinate of center of polygon
     * @param centerY    y-coordinate of center of polygon
     * @return
     */
    public int[][] getOrderedPoints(int sidesCount, int length, int centerX, int centerY) {
        Shape polygon = getRegularPolygon(sidesCount, length);
        Polygon poly = (Polygon) polygon;

        int[] xPoints = poly.xpoints;
        int[] yPoints = poly.ypoints;
        int nPoints = poly.npoints;

        int[] xOrdered = new int[nPoints];
        int[] yOrdered = new int[nPoints];
        for (int i = 0; i < nPoints; i++) {
            xOrdered[i] = xPoints[i] + centerX;
            yOrdered[i] = yPoints[i] + centerY;
        }

        return new int[][] { xOrdered, yOrdered };
    }
}
