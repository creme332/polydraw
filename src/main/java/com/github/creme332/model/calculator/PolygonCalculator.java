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
     * Rotates a vector in the xy plane counterclockwise through an angle radAngle
     * about a pivot.
     * 
     * @param point    Point to be rotated.
     * @param pivot    Point about which rotation takes place.
     * @param radAngle Rotation angle in radians.
     * @return
     */
    public static Point2D rotatePointAboutPivot(Point2D point, Point2D pivot, double radAngle) {
        // calculate translation vector from pivot to point
        Point2D translationVector = new Point2D.Double(point.getX() - pivot.getX(), point.getY() - pivot.getY());

        // rotate translation vector
        translationVector = rotateVector(translationVector, radAngle);

        // return new position of point
        return new Point2D.Double(translationVector.getX() + pivot.getX(), translationVector.getY() + pivot.getY());
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

    /**
     * Transforms a given Polygon using the specified AffineTransform.
     * 
     * @param polygon   the Polygon to be transformed
     * @param transform the AffineTransform to be applied
     * @return a new Polygon representing the transformed shape
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
    public List<Point> scanFill(Polygon polygon) {
        List<Edge> edgeTable = createEdgeTable(polygon);
        List<Point> filledPixels = new ArrayList<>();
        
        int minY = Arrays.stream(polygon.ypoints).min().orElseThrow();
        int maxY = Arrays.stream(polygon.ypoints).max().orElseThrow();
        
        List<Edge> activeEdgeTable = new ArrayList<>();
        
        for (int y = minY; y <= maxY; y++) {
            // Move edges from edge table to active edge table where y == minY
            Iterator<Edge> edgeIterator = edgeTable.iterator();
            while (edgeIterator.hasNext()) {
                Edge edge = edgeIterator.next();
                if (edge.yMin == y) {
                    activeEdgeTable.add(edge);
                    edgeIterator.remove();
                }
            }
            
            // Remove edges from active edge table where y == maxY
            edgeIterator = activeEdgeTable.iterator();
            while (edgeIterator.hasNext()) {
                Edge edge = edgeIterator.next();
                if (edge.yMax == y) {
                    edgeIterator.remove();
                }
            }
            
            // Sort active edge table by xMin
            activeEdgeTable.sort(Comparator.comparingDouble(e -> e.xMin));
            
            // Fill pixels between pairs of intersections
            for (int i = 0; i < activeEdgeTable.size(); i += 2) {
                if (i + 1 >= activeEdgeTable.size()) break;
                Edge edge1 = activeEdgeTable.get(i);
                Edge edge2 = activeEdgeTable.get(i + 1);
                
                for (int x = (int) Math.ceil(edge1.xMin); x <= edge2.xMin; x++) {
                    filledPixels.add(new Point(x, y));
                }
            }
            
            // Update xMin for each edge in active edge table
            for (Edge edge : activeEdgeTable) {
                edge.xMin += edge.inverseSlope;
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
    private List<Edge> createEdgeTable(Polygon polygon) {
        List<Edge> edgeTable = new ArrayList<>();
        
        int n = polygon.npoints;
        for (int i = 0; i < n; i++) {
            int x1 = polygon.xpoints[i];
            int y1 = polygon.ypoints[i];
            int x2 = polygon.xpoints[(i + 1) % n];
            int y2 = polygon.ypoints[(i + 1) % n];
            
            if (y1 == y2) continue; // Skip horizontal edges
            
            Edge edge = new Edge();
            if (y1 < y2) {
                edge.yMin = y1;
                edge.yMax = y2;
                edge.xMin = x1;
                edge.inverseSlope = (double) (x2 - x1) / (y2 - y1);
            } else {
                edge.yMin = y2;
                edge.yMax = y1;
                edge.xMin = x2;
                edge.inverseSlope = (double) (x1 - x2) / (y1 - y2);
            }
            
            edgeTable.add(edge);
        }
        
        edgeTable.sort(Comparator.comparingInt(e -> e.yMin));
        return edgeTable;
    }
    
    /**
     * Private inner class representing an edge for the scan-line fill algorithm.
     */
    private static class Edge {
        int yMin;
        int yMax;
        double xMin;
        double inverseSlope;
    }
}

