package com.github.creme332.model.calculator;

import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.Point;
import java.util.Iterator;

public class PolygonCalculator {

    /**
     * Caches coordinates of regular polygons previously calculated.
     */
    private final Map<Map.Entry<Integer, Integer>, Shape> polygonCache = new HashMap<>();

    /**
     * Rotates a vector in the xy plane counterclockwise through an angle radAngle
     * about the origin.
     * 
     * @param vector   A point in the x-y plane.
     * @param radAngle Rotation angle in radians.
     * @return The rotated vector as a Point2D.
     */
    public static Point2D rotateVector(Point2D vector, double radAngle) {
        return new Point2D.Double(
                vector.getX() * Math.cos(radAngle) - vector.getY() * Math.sin(radAngle),
                vector.getX() * Math.sin(radAngle) + vector.getY() * Math.cos(radAngle));
    }

    /**
     * Rotates a vector in the xy plane counterclockwise through an angle radAngle
     * about a pivot.
     * 
     * @param point    Point to be rotated.
     * @param pivot    Point about which rotation takes place.
     * @param radAngle Anti-clockwise rotation angle in radians.
     * @return The rotated point as a Point2D.
     */
    public static Point2D rotatePointAboutPivot(Point2D point, Point2D pivot, double radAngle) {
        // Calculate translation vector from pivot to point
        Point2D translationVector = new Point2D.Double(point.getX() - pivot.getX(), point.getY() - pivot.getY());

        // Rotate translation vector
        translationVector = rotateVector(translationVector, radAngle);

        // Return new position of point
        return new Point2D.Double(translationVector.getX() + pivot.getX(), translationVector.getY() + pivot.getY());
    }

    /**
     * Calculates coordinates of a regular polygon centered at the origin.
     * 
     * @param sidesCount Number of sides in polygon.
     * @param length     Side length of polygon.
     * @return The shape representing the polygon.
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
            // Rotate the previous point by the required angle
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
     * Calculates a regular polygon given two adjacent vertices and the number of
     * sides.
     * 
     * @param pointA     First vertex.
     * @param pointB     Second vertex (adjacent to the first).
     * @param sidesCount Number of sides in polygon.
     * @return The Polygon object representing the regular polygon.
     */
    public Polygon getRegularPolygon(Point2D pointA, Point2D pointB, int sidesCount) {
        final Point2D.Double[] points = new Point2D.Double[sidesCount];
        points[0] = new Point2D.Double(pointA.getX(), pointA.getY());
        points[1] = new Point2D.Double(pointB.getX(), pointB.getY());

        /**
         * Size of 1 interior angle of polygon.
         */
        final double interiorAngle = (180 * (sidesCount - 2)) / (double) sidesCount;

        /**
         * Anticlockwise rotation angle mapping one edge to another.
         */
        final double rotationAngleInRad = Math.toRadians(360 - interiorAngle);

        for (int i = 2; i < sidesCount; i++) {
            Point2D pivot = points[i - 1];
            Point2D vectorStart = points[i - 2];

            // rotate vector about pivot
            points[i] = (Point2D.Double) rotatePointAboutPivot(vectorStart, pivot, rotationAngleInRad);
        }

        /// round off pixel coordinates to the nearest integer
        int[] x = new int[sidesCount];
        int[] y = new int[sidesCount];
        for (int i = 0; i < sidesCount; i++) {
            x[i] = (int) Math.round(points[i].x);
            y[i] = (int) Math.round(points[i].y);
        }

        return new Polygon(x, y, sidesCount);
    }

    /**
     * Retrieves the ordered points of a regular polygon with specified parameters.
     * 
     * @param sidesCount Number of sides.
     * @param length     Side length.
     * @param centerX    X-coordinate of the polygon center.
     * @param centerY    Y-coordinate of the polygon center.
     * @return A 2D array containing ordered x and y points.
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

    /**
     * Transforms a polygon using the given affine transformation.
     * 
     * @param polygon   The polygon to be transformed.
     * @param transform The affine transformation to apply.
     * @return The transformed polygon.
     */
    public static Polygon transformPolygon(Polygon polygon, AffineTransform transform) {
        Shape transformedShape = transform.createTransformedShape(polygon);
        PathIterator pathIterator = transformedShape.getPathIterator(null);

        ArrayList<Integer> xPointsList = new ArrayList<>();
        ArrayList<Integer> yPointsList = new ArrayList<>();
        double[] coordinates = new double[6];

        while (!pathIterator.isDone()) {
            int segmentType = pathIterator.currentSegment(coordinates);
            if (segmentType == PathIterator.SEG_MOVETO || segmentType == PathIterator.SEG_LINETO) {
                xPointsList.add((int) coordinates[0]);
                yPointsList.add((int) coordinates[1]);
            }
            pathIterator.next();
        }

        int[] xPoints = xPointsList.stream().mapToInt(i -> i).toArray();
        int[] yPoints = yPointsList.stream().mapToInt(i -> i).toArray();

        return new Polygon(xPoints, yPoints, xPoints.length);
    }

    /**
     * Fills the polygon using the scan-line fill algorithm.
     * 
     * @param polygon The polygon to be filled.
     * @return A list of points representing the filled pixels.
     */
    public static List<Point> scanFill(Polygon polygon) {
        List<Edge> edgeTable = createEdgeTable(polygon);
        List<Point> filledPixels = new ArrayList<>();

        if (edgeTable.isEmpty()) {
            return filledPixels;
        }

        int minY = Arrays.stream(polygon.ypoints).min().orElseThrow();
        int maxY = Arrays.stream(polygon.ypoints).max().orElseThrow();

        List<Edge> activeEdgeTable = new ArrayList<>();

        for (int y = minY; y <= maxY; y++) {
            // Move edges from edge table to active edge table where y == yMin
            Iterator<Edge> edgeIterator = edgeTable.iterator();
            while (edgeIterator.hasNext()) {
                Edge edge = edgeIterator.next();
                if (edge.maxY >= y) {
                    if (edge.currentX == (int) edge.currentX) {
                        activeEdgeTable.add(edge);
                        edgeIterator.remove();
                    }
                }
            }

            // Remove edges from the active edge table where y == maxY
            edgeIterator = activeEdgeTable.iterator();
            while (edgeIterator.hasNext()) {
                Edge edge = edgeIterator.next();
                if (edge.maxY == y) {
                    edgeIterator.remove();
                }
            }

            // Sort the active edge table by currentX
            activeEdgeTable.sort(Comparator.comparingDouble(e -> e.currentX));

            // Fill pixels between pairs of intersections
            for (int i = 0; i < activeEdgeTable.size(); i += 2) {
                if (i + 1 >= activeEdgeTable.size())
                    break;
                Edge edge1 = activeEdgeTable.get(i);
                Edge edge2 = activeEdgeTable.get(i + 1);

                for (int x = (int) Math.ceil(edge1.currentX); x < edge2.currentX; x++) {
                    filledPixels.add(new Point(x, y));
                }
            }

            // Update x for each edge in the active edge table
            for (Edge edge : activeEdgeTable) {
                edge.currentX += edge.inverseSlope;
            }
        }
        return filledPixels;
    }

    /**
     * Creates the edge table from the given polygon.
     * 
     * @param polygon The polygon to create the edge table from.
     * @return A list of edges representing the edge table.
     */
    private static List<Edge> createEdgeTable(Polygon polygon) {
        List<Edge> edgeTable = new ArrayList<>();

        int n = polygon.npoints;
        for (int i = 0; i < n; i++) {
            int x1 = polygon.xpoints[i];
            int y1 = polygon.ypoints[i];
            int x2 = polygon.xpoints[(i + 1) % n];
            int y2 = polygon.ypoints[(i + 1) % n];

            if (y1 == y2)
                continue; // Skip horizontal edges

            Edge edge = new Edge();
            if (y1 < y2) {
                edge.currentX = x1;
                edge.maxY = y2;
                edge.inverseSlope = (double) (x2 - x1) / (y2 - y1);
            } else {
                edge.currentX = x2;
                edge.maxY = y1;
                edge.inverseSlope = (double) (x1 - x2) / (y1 - y2);
            }

            edgeTable.add(edge);
        }

        edgeTable.sort(Comparator.comparingDouble(e -> e.currentX));
        return edgeTable;
    }

    /**
     * Private inner class representing an edge for the scan-line fill algorithm.
     */
    private static class Edge {
        int maxY;
        double currentX;
        double inverseSlope;
    }
}
