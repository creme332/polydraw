package com.github.creme332.tests.model.calculator;

import com.github.creme332.model.calculator.PolygonCalculator;
import org.junit.Test;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.List;

import static org.junit.Assert.*;

public class ScanFillTest {

    private final PolygonCalculator calculator = new PolygonCalculator();

    @Test
    public void testGetOrderedPoints() {
        int sidesCount = 5;
        int length = 50;
        int centerX = 100;
        int centerY = 100;

        int[][] orderedPoints = calculator.getOrderedPoints(sidesCount, length, centerX, centerY);

        assertNotNull(orderedPoints);
        assertEquals(2, orderedPoints.length);
        assertEquals(sidesCount, orderedPoints[0].length);
        assertEquals(sidesCount, orderedPoints[1].length);
    }

    @Test
    public void testTransformPolygon() {
        Polygon polygon = new Polygon(new int[]{0, 1, 0}, new int[]{0, 0, 1}, 3);
        AffineTransform transform = AffineTransform.getScaleInstance(2, 2);
        Polygon transformedPolygon = PolygonCalculator.transformPolygon(polygon, transform);

        assertEquals(3, transformedPolygon.npoints);
        assertEquals(0, transformedPolygon.xpoints[0]);
        assertEquals(2, transformedPolygon.xpoints[1]);
        assertEquals(0, transformedPolygon.xpoints[2]);
        assertEquals(0, transformedPolygon.ypoints[0]);
        assertEquals(0, transformedPolygon.ypoints[1]);
        assertEquals(2, transformedPolygon.ypoints[2]);
    }

    @Test
    public void testScanFill() {
        int[] xPoints = {50, 100, 150};
        int[] yPoints = {50, 150, 50};
        Polygon polygon = new Polygon(xPoints, yPoints, 3);

        List<Point> filledPixels = calculator.scanFill(polygon);

        assertNotNull(filledPixels);
        assertFalse(filledPixels.isEmpty());

        // A simple check to see if the filled pixels are within the bounding box of the triangle
        for (Point p : filledPixels) {
            assertTrue(p.x >= 50 && p.x <= 150);
            assertTrue(p.y >= 50 && p.y <= 150);
        }
    }

    @Test
    public void testRotateVector() {
        Point2D vector = new Point2D.Double(1, 0);
        double angle = Math.PI / 2; // 90 degrees

        Point2D rotatedVector = PolygonCalculator.rotateVector(vector, angle);

        assertEquals(0, rotatedVector.getX(), 0.0001);
        assertEquals(1, rotatedVector.getY(), 0.0001);
    }

    @Test
    public void testRotatePointAboutPivot() {
        Point2D point = new Point2D.Double(1, 0);
        Point2D pivot = new Point2D.Double(0, 0);
        double angle = Math.PI / 2; // 90 degrees

        Point2D rotatedPoint = PolygonCalculator.rotatePointAboutPivot(point, pivot, angle);

        assertEquals(0, rotatedPoint.getX(), 0.0001);
        assertEquals(1, rotatedPoint.getY(), 0.0001);
    }
}