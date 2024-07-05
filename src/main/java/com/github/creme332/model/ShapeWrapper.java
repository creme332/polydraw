package com.github.creme332.model;

import java.awt.Color;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.Shape;
import java.awt.geom.Point2D;

public class ShapeWrapper {
    private Shape shape;
    private Color lineColor = Color.BLACK;
    private LineType lineType = LineType.SOLID;
    private int lineThickness = 1;

    /**
     * Coordinates plotted by user to create shape. A plotted point is a one which
     * user has clicked one. A point on which user is hovering on is NOT a plotted
     * point.
     */
    private List<Point2D> plottedPoints = new ArrayList<>();

    /**
     * 
     * @return Original list of plotted points.
     */
    public List<Point2D> getPlottedPoints() {
        return plottedPoints;
    }

    public ShapeWrapper(Color lineColor, LineType lineType, int lineThickness) {
        this.lineColor = lineColor;
        this.lineType = lineType;
        this.lineThickness = lineThickness;
    }

    /**
     * Copy constructor.
     * 
     * @param wrapper
     */
    public ShapeWrapper(ShapeWrapper wrapper) {
        if (wrapper == null) {
            throw new NullPointerException("wrapper should not be null when passed to copy constructor.");
        }

        // save primitive attributes
        lineColor = wrapper.lineColor;
        lineType = wrapper.lineType;
        lineThickness = wrapper.lineThickness;

        // create a new shape object
        if (wrapper.shape != null) {
            Polygon original = (Polygon) wrapper.shape;
            Polygon copy = new Polygon(original.xpoints, original.ypoints, original.npoints);
            shape = copy;
        }

        // create a new array for plotted points
        for (Point2D point : wrapper.getPlottedPoints()) {
            plottedPoints.add(new Point2D.Double(point.getX(), point.getY()));
        }
    }

    public Shape getShape() {
        return shape;
    }

    public Polygon toPolygon() {
        Polygon original = (Polygon) shape;
        return new Polygon(original.xpoints, original.ypoints, original.npoints);
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public Color getLineColor() {
        return lineColor;
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }

    /**
     * Fill color is a transparent version of the line color.
     */
    public Color getFillColor() {
        Color a = lineColor;
        return new Color(a.getRed() / 255f, a.getGreen() / 255f, a.getBlue() / 255f, .2f);
    }

    public void setLineThickness(int lineThickness) {
        this.lineThickness = lineThickness;
    }

    public int getLineThickness() {
        return lineThickness;
    }

    public LineType getLineType() {
        return lineType;
    }

    public void setLineType(LineType lineType) {
        this.lineType = lineType;
    }

    @Override
    public String toString() {
        // create a list of plotted point coordinates
        StringBuilder plottedPointString = new StringBuilder();
        plottedPointString.append("[");
        for (Point2D point2d : plottedPoints) {
            plottedPointString.append(String.format("[%f, %f], ", point2d.getX(), point2d.getY()));
        }
        plottedPointString.append("]");

        return String.format("""
                ShapeWrapper{
                    plottedPoints: %s
                    xpoints: %s
                    ypoints: %s
                    lineColor: %s
                    lineType: %s
                    lineThickness: %d
                }
                """, plottedPointString, Arrays.toString(toPolygon().xpoints), Arrays.toString(toPolygon().ypoints),
                lineColor, lineType, lineThickness);
    }
}
