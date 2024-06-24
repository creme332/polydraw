package com.github.creme332.algorithms;

import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;

public class PolygonCalculator {

    private final Map<Integer, Shape> polygonCache = new HashMap<>();

    public Point2D.Double rotateVector(Point2D.Double point, double radAngle) {
        return new Point2D.Double(
            point.x * Math.cos(radAngle) - point.y * Math.sin(radAngle),
            point.x * Math.sin(radAngle) + point.y * Math.cos(radAngle)
        );
    }

    private Shape getRegularPolygon(int sidesCount, int length) {
        if (polygonCache.containsKey(sidesCount)) {
            return polygonCache.get(sidesCount);
        }

        double rotationAngle = Math.toRadians(360.0 / sidesCount);
        Point2D.Double[] points = new Point2D.Double[sidesCount];

        points[0] = new Point2D.Double(
            length * Math.sin(rotationAngle / 2),
            length * Math.cos(rotationAngle / 2)
        );

        for (int i = 1; i < sidesCount; i++) {
            Point2D.Double vector = new Point2D.Double(points[i - 1].x, points[i - 1].y);
            Point2D.Double rotatedVector = rotateVector(vector, rotationAngle);
            points[i] = new Point2D.Double(rotatedVector.x, rotatedVector.y);
        }

        int[] x = new int[sidesCount];
        int[] y = new int[sidesCount];
        for (int i = 0; i < sidesCount; i++) {
            x[i] = (int) Math.round(points[i].x);
            y[i] = (int) Math.round(points[i].y);
        }

        Polygon polygon = new Polygon(x, y, sidesCount);
        polygonCache.put(sidesCount, polygon);
        return polygon;
    }

    public int[][] getOrderedPoints(int sidesCount, int length) {
        Shape polygon = getRegularPolygon(sidesCount, length);
        Polygon poly = (Polygon) polygon;

        int[] xPoints = poly.xpoints;
        int[] yPoints = poly.ypoints;
        int nPoints = poly.npoints;

        int[] xOrdered = new int[nPoints];
        int[] yOrdered = new int[nPoints];
        for (int i = 0; i < nPoints; i++) {
            xOrdered[i] = xPoints[i];
            yOrdered[i] = yPoints[i];
        }

        return new int[][] { xOrdered, yOrdered };
    }
}
