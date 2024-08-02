package com.github.creme332.tests.model.calculator;

import com.github.creme332.model.calculator.PolygonCalculator;
import org.junit.Test;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class PolygonCalculatorTest {

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
        Polygon polygon = new Polygon(new int[] { 0, 1, 0 }, new int[] { 0, 0, 1 }, 3);
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
    public void testScanFillForRectangle() {
        int[] xPoints = { 0, 3, 3, 0 };
        int[] yPoints = { 0, 0, 3, 3 };
        List<Point> expectedPixels = Arrays.asList(
                new Point(0, 0), new Point(1, 0), new Point(2, 0), new Point(3, 0),
                new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1),
                new Point(0, 2), new Point(1, 2), new Point(2, 2), new Point(3, 2),
                new Point(0, 3), new Point(1, 3), new Point(2, 3), new Point(3, 3));

        List<Point> filledPixels = PolygonCalculator.scanFill(new Polygon(xPoints, yPoints, xPoints.length));
        assertEquals(expectedPixels, filledPixels);
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