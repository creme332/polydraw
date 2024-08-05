package com.github.creme332.tests.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;

import com.github.creme332.model.ShapeWrapper;
import com.github.creme332.model.LineType;

public class ShapeWrapperTest {

    private ShapeWrapper originalShapeWrapper;
    private Polygon polygon;

    @Before
    public void setUp() {
        polygon = new Polygon(new int[] { 0, 0, 1 }, new int[] { 0, 1, 1 }, 3);
        originalShapeWrapper = new ShapeWrapper(Color.BLUE, LineType.DASHED, 2);
        originalShapeWrapper.setShape(polygon);
        originalShapeWrapper.getPlottedPoints().add(new Point2D.Double(0, 0));
        originalShapeWrapper.getPlottedPoints().add(new Point2D.Double(0, 1));
        originalShapeWrapper.getPlottedPoints().add(new Point2D.Double(1, 1));
    }

    @Test
    public void testCopyConstructor() {
        ShapeWrapper copiedShapeWrapper = new ShapeWrapper(originalShapeWrapper);

        // Check if the primitive attributes are copied correctly
        assertEquals(originalShapeWrapper.getLineColor(), copiedShapeWrapper.getLineColor());
        assertEquals(originalShapeWrapper.getLineType(), copiedShapeWrapper.getLineType());
        assertEquals(originalShapeWrapper.getLineThickness(), copiedShapeWrapper.getLineThickness());
        assertEquals(originalShapeWrapper.isFillable(), copiedShapeWrapper.isFillable());

        // Check if the shape is copied correctly
        assertTrue(copiedShapeWrapper.getShape() instanceof Polygon);
        Polygon copiedPolygon = (Polygon) copiedShapeWrapper.getShape();
        assertArrayEquals(polygon.xpoints, copiedPolygon.xpoints);
        assertArrayEquals(polygon.ypoints, copiedPolygon.ypoints);
        assertEquals(polygon.npoints, copiedPolygon.npoints);

        // Check if the plotted points are copied correctly
        assertEquals(originalShapeWrapper.getPlottedPoints().size(), copiedShapeWrapper.getPlottedPoints().size());
        for (int i = 0; i < originalShapeWrapper.getPlottedPoints().size(); i++) {
            Point2D originalPoint = originalShapeWrapper.getPlottedPoints().get(i);
            Point2D copiedPoint = copiedShapeWrapper.getPlottedPoints().get(i);
            assertEquals(originalPoint.getX(), copiedPoint.getX(), 0.001);
            assertEquals(originalPoint.getY(), copiedPoint.getY(), 0.001);
        }
    }

    @Test
    public void testTranslate() {
        Point2D translationVector = new Point2D.Double(2, 2);
        originalShapeWrapper.translate(translationVector);

        Polygon translatedPolygon = (Polygon) originalShapeWrapper.getShape();
        assertArrayEquals(new int[] { 2, 2, 3 }, translatedPolygon.xpoints);
        assertArrayEquals(new int[] { 2, 3, 3 }, translatedPolygon.ypoints);

        assertEquals(new Point2D.Double(2, 2), originalShapeWrapper.getPlottedPoints().get(0));
        assertEquals(new Point2D.Double(2, 3), originalShapeWrapper.getPlottedPoints().get(1));
        assertEquals(new Point2D.Double(3, 3), originalShapeWrapper.getPlottedPoints().get(2));
    }

    @Test
    public void testRotate() {
        // Define a square shape
        Polygon square = new Polygon(new int[] { 0, 1, 1, 0 }, new int[] { 0, 0, 1, 1 }, 4);
        ShapeWrapper shapeWrapper = new ShapeWrapper(Color.BLACK, LineType.SOLID, 1);
        shapeWrapper.setShape(square);
        shapeWrapper.getPlottedPoints().addAll(Arrays.asList(
                new Point2D.Double(0, 0),
                new Point2D.Double(1, 0),
                new Point2D.Double(1, 1),
                new Point2D.Double(0, 1)));

        Point2D pivot = new Point2D.Double(0.5, 0.5);
        shapeWrapper.rotate(Math.PI / 2, pivot); // Rotate 90 degrees

        List<Point2D> expectedPoints = Arrays.asList(
                new Point2D.Double(1, 0),
                new Point2D.Double(1, 1),
                new Point2D.Double(0, 1),
                new Point2D.Double(0, 0));

        for (int i = 0; i < shapeWrapper.getPlottedPoints().size(); i++) {
            Point2D actual = shapeWrapper.getPlottedPoints().get(i);
            Point2D expected = expectedPoints.get(i);
            assertEquals(expected.getX(), actual.getX(), 0.0001);
            assertEquals(expected.getY(), actual.getY(), 0.0001);
        }
    }

    @Test
    public void testIsPointOnShape() {
        // Define a simple square shape
        Polygon square = new Polygon(new int[] { 0, 1, 1, 0 }, new int[] { 0, 0, 1, 1 }, 4);
        ShapeWrapper shapeWrapper = new ShapeWrapper(Color.BLACK, LineType.SOLID, 1);
        shapeWrapper.setShape(square);

        // Inside the shape
        assertTrue(shapeWrapper.isPointOnShape(new Point2D.Double(0.5, 0.5)));

        // Outside the shape but within tolerance region
        assertTrue(shapeWrapper.isPointOnShape(new Point2D.Double(1.5, 1.5)));

        // Outside the shape but not within tolerance region
        assertFalse(shapeWrapper.isPointOnShape(new Point2D.Double(3, 3)));

        // On the border of the shape
        assertTrue(shapeWrapper.isPointOnShape(new Point2D.Double(1, 0)));
        assertTrue(shapeWrapper.isPointOnShape(new Point2D.Double(0, 0)));
        assertTrue(shapeWrapper.isPointOnShape(new Point2D.Double(1, 1)));
        assertTrue(shapeWrapper.isPointOnShape(new Point2D.Double(0, 1)));
    }

}
