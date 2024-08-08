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
        assertEquals(expected,  result);
    }
}