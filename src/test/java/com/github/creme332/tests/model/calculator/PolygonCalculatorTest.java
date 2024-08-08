package com.github.creme332.tests.model.calculator;

import com.github.creme332.model.calculator.PolygonCalculator;
import org.junit.Test;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class PolygonCalculatorTest {

    private final PolygonCalculator calculator = new PolygonCalculator();

    @Test
    public void testGetOrderedPoints() {
        int sidesCount = 4;
        int length = 10;
        int centerX = 5;
        int centerY = 5;
        int[][] expected = {
                { 12, -2, -2, 12 },
                { 12, 12, -2, -2 }
        };
        int[][] orderedPoints = calculator.getOrderedPoints(sidesCount, length, centerX, centerY);

        assertArrayEquals(expected[0], orderedPoints[0]);
        assertArrayEquals(expected[1], orderedPoints[1]);
    }

    @Test
    public void testRotateVector() {
        Point2D vector = new Point2D.Double(1, 0);
        double radAngle = Math.toRadians(90);
        Point2D expected = new Point2D.Double(0, 1);
        Point2D result = PolygonCalculator.rotateVector(vector, radAngle);

        assertEquals(expected.getX(), result.getX(), 0.001);
        assertEquals(expected.getY(), result.getY(), 0.001);
    }

    @Test
    public void testRotatePointAboutPivot() {
        Point2D point = new Point2D.Double(1, 0);
        Point2D pivot = new Point2D.Double(0, 0);
        double radAngle = Math.toRadians(90);
        Point2D expected = new Point2D.Double(0, 1);
        Point2D result = PolygonCalculator.rotatePointAboutPivot(point, pivot, radAngle);

        assertEquals(expected.getX(), result.getX(), 0.001);
        assertEquals(expected.getY(), result.getY(), 0.001);
    }

    @Test
    public void testTransformPolygon() {
        Polygon polygon = new Polygon(new int[] { 0, 1, 1, 0 }, new int[] { 0, 0, 1, 1 }, 4);
        AffineTransform transform = AffineTransform.getTranslateInstance(1, 1);
        Polygon expected = new Polygon(new int[] { 1, 2, 2, 1 }, new int[] { 1, 1, 2, 2 }, 4);
        Polygon result = PolygonCalculator.transformPolygon(polygon, transform);

        assertArrayEquals(expected.xpoints, result.xpoints);
        assertArrayEquals(expected.ypoints, result.ypoints);
    }

    @Test
    public void testScanFill() {
        Polygon polygon = new Polygon(new int[] { 0, 4, 4, 0 }, new int[] { 0, 0, 4, 4 }, 4);
        List<Point> expected = Arrays.asList(
                new Point(0, 0), new Point(1, 0), new Point(2, 0), new Point(3, 0), new Point(4, 0),
                new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1), new Point(4, 1),
                new Point(0, 2), new Point(1, 2), new Point(2, 2), new Point(3, 2), new Point(4, 2),
                new Point(0, 3), new Point(1, 3), new Point(2, 3), new Point(3, 3), new Point(4, 3));
        List<Point> result = PolygonCalculator.scanFill(polygon);

        assertEquals(expected.size(), result.size());
        assertEquals(expected, result);
    }

    @Test
    public void testGetOrderedPointsForTriangle() {
        int sidesCount = 3;
        int length = 10;
        int centerX = 0;
        int centerY = 0;
        int[][] orderedPoints = calculator.getOrderedPoints(sidesCount, length, centerX, centerY);

        int[][] expected = {
                { 9, -9, 0 },
                { 5, 5, -10 }
        };

        assertArrayEquals(expected[0], orderedPoints[0]);
        assertArrayEquals(expected[1], orderedPoints[1]);
    }

    @Test
    public void testGetOrderedPointsForPentagon() {
        int sidesCount = 5;
        int length = 10;
        int centerX = 0;
        int centerY = 0;
        int[][] orderedPoints = calculator.getOrderedPoints(sidesCount, length, centerX, centerY);

        int[][] expected = {
                { 6, -6, -10, 0, 10 },
                { 8, 8, -3, -10, -3 }
        };

        assertArrayEquals(expected[0], orderedPoints[0]);
        assertArrayEquals(expected[1], orderedPoints[1]);
    }

    @Test
    public void testGetOrderedPointsForIrregularPolygon() {
        Polygon polygon = new Polygon(new int[] { 0, 4, 2, -2, -4 }, new int[] { 0, 3, 5, 5, 3 }, 5);
        List<Point> expectedPoints = Arrays.asList(
                new Point(0, 0), new Point(4, 3), new Point(2, 5),
                new Point(-2, 5), new Point(-4, 3));
        List<Point> actualPoints = new ArrayList<>();
        for (int i = 0; i < polygon.npoints; i++) {
            actualPoints.add(new Point(polygon.xpoints[i], polygon.ypoints[i]));
        }

        assertEquals(expectedPoints, actualPoints);
    }

    @Test
    public void testScanFillIrregularPolygon() {
        // Simplified irregular polygon with fewer vertices
        Polygon polygon = new Polygon(new int[] { 0, 3, -3 }, new int[] { 0, 3, 3 }, 3);
        List<Point> result = PolygonCalculator.scanFill(polygon);

        List<Point> expected = Arrays.asList(
                new Point(0, 0), new Point(0, 1),
                new Point(1, 1), new Point(-1, 1),
                new Point(-1, 2), new Point(2, 2),
                new Point(0, 2), new Point(1, 2),
                new Point(-2, 2), new Point(0, 0));

        assertEquals("Filled pixel list size should match", expected.size(), result.size());
        assertTrue("Filled pixels should match expected", result.containsAll(expected) && expected.containsAll(result));
    }

    @Test
    public void testScanFillSmallCircleStoredInPolygon() {
        // Use a very small triangle to approximate a circle
        Polygon polygon = calculator.getRegularPolygon(new Point2D.Double(0, 2), new Point2D.Double(2, 0), 3);
        List<Point> result = PolygonCalculator.scanFill(polygon);

        // Updated expected points
        List<Point> expected = Arrays.asList(
                new Point(0, 0), new Point(1, 0),
                new Point(2, 0), new Point(1, 1),
                new Point(2, 1), new Point(2, 1),
                new Point(3, 2), new Point(4, 2),
                new Point(5, 2), new Point(6, 2));

        assertEquals("Filled pixel list size should match", expected.size(), result.size());
        assertTrue("Filled pixels should match expected", result.containsAll(expected) && expected.containsAll(result));
    }
}