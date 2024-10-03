package com.github.creme332.model.calculator;

import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.Point;

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

    static class Edge {
        int yMax; // Maximum y value for the edge
        double xCurrent; // Current x value along the edge
        double inverseSlope; // Slope of the edge (1/m) for calculating x

        public Edge(int yMax, double xCurrent, double inverseSlope) {
            this.yMax = yMax;
            this.xCurrent = xCurrent;
            this.inverseSlope = inverseSlope;
        }

        @Override
        public String toString() {
            return String.format("(%d, %.3f, %.3f)", yMax, xCurrent, inverseSlope);
        }
    }

    public static List<Point> scanFill(Polygon polygon) {
        int[] xPoints = polygon.xpoints;
        int[] yPoints = polygon.ypoints;
        int verticesCount = polygon.npoints;

        // Initialize the edge table as a HashMap
        HashMap<Integer, List<Edge>> edgeTable = new HashMap<>();
        int maxY = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;

        // Create an edge table for each scanline
        for (int i = 0; i < verticesCount; i++) {
            Point p1 = new Point(xPoints[i], yPoints[i]);
            Point p2 = new Point(xPoints[(i + 1) % verticesCount], yPoints[(i + 1) % verticesCount]);

            // Ensure p1.y < p2.y for correct edge handling
            if (p1.y > p2.y) {
                Point temp = p1;
                p1 = p2;
                p2 = temp;
            }

            if (p1.y != p2.y) { // Ignore horizontal edges
                int yMin = p1.y;
                int yMax = p2.y;
                float xCurrent = p1.x;
                float inverseSlope = (float) (p2.x - p1.x) / (p2.y - p1.y);

                maxY = Math.max(maxY, yMax);
                minY = Math.min(minY, yMin);

                // Add the edge to the corresponding edge list in the edge table
                edgeTable.putIfAbsent(yMin, new ArrayList<>());
                edgeTable.get(yMin).add(new Edge(yMax, xCurrent, inverseSlope));
            }
        }

        // After the edge table is built, sort the edges for each scanline
        for (List<Edge> edges : edgeTable.values()) {
            edges.sort(Comparator
                    .comparingInt((Edge edge) -> edge.yMax) // Primary: yMax
                    .thenComparingDouble(edge -> edge.xCurrent) // Secondary: xCurrent
                    .thenComparingDouble(edge -> edge.inverseSlope) // Tertiary: inverseSlope
            );
        }

        // System.out.println(edgeTable);

        // List to store filled points (can be thought of as the output)
        List<Point> filledPoints = new ArrayList<>();

        // Active Edge Table (AET)
        List<Edge> activeEdgeTable = new ArrayList<>();

        // Process each scanline
        for (int scanline = minY; scanline <= maxY; scanline += 1) {
            // System.out.println("\ny = " + scanline);

            // 1. Move edges from edgeTable to AET where the current scanline starts
            if (edgeTable.containsKey(scanline)) {
                activeEdgeTable.addAll(edgeTable.get(scanline));
            }

            // 2. Remove edges from AET where scanline >= yMax
            final int scanlineNumberCopy = scanline;
            activeEdgeTable.removeIf(edge -> scanlineNumberCopy >= edge.yMax);

            // 3. Sort AET by xCurrent
            activeEdgeTable.sort(Comparator.comparingDouble(edge -> edge.xCurrent));

            // System.out.println(activeEdgeTable);

            // 4. Fill the pixels between pairs of x-coordinates
            for (int i = 0; i < activeEdgeTable.size() - 1; i += 2) {
                Edge e1 = activeEdgeTable.get(i);
                Edge e2 = activeEdgeTable.get(i + 1);

                // System.out.println(
                //         String.format("Plot [%d, %d]", (int) Math.ceil(e1.xCurrent), (int) Math.floor(e2.xCurrent)));

                // Add points between the two x coordinates
                for (int x = (int) Math.ceil(e1.xCurrent); x <= (int) Math.floor(e2.xCurrent); x++) {
                    filledPoints.add(new Point(x, scanline));
                }
            }

            // 5. Update xCurrent for all edges in the AET
            for (Edge edge : activeEdgeTable) {
                edge.xCurrent += edge.inverseSlope;
            }
        }

        return filledPoints;
    }
}
