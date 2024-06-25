package com.github.creme332.model;

import java.awt.Color;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;
import java.awt.Shape;
import java.awt.geom.Point2D;

public class ShapeWrapper {
    private Shape shape;
    private Color lineColor = Color.BLACK;
    private Color fillColor = Color.BLACK;
    private LineType lineType = LineType.SOLID;
    private int lineThickness = 1;

    /**
     * Coordinates plotted by user to create shape.
     */
    private List<Point2D> plottedPoints = new ArrayList<>();

    public List<Point2D> getPlottedPoints() {
        return plottedPoints;
    }

    public ShapeWrapper(Color lineColor, Color fillColor, LineType lineType, int lineThickness) {
        this.lineColor = lineColor;
        this.fillColor = fillColor;
        this.lineType = lineType;
        this.lineThickness = lineThickness;
    }

    /**
     * Copy constructor.
     * 
     * @param wrapper
     */
    public ShapeWrapper(ShapeWrapper wrapper) {
        // save primitive attributes
        lineColor = wrapper.lineColor;
        fillColor = wrapper.fillColor;
        lineType = wrapper.lineType;
        lineThickness = wrapper.lineThickness;

        // create a new shape object
        Polygon original = (Polygon) wrapper.shape;
        Polygon copy = new Polygon(original.xpoints, original.ypoints, original.npoints);
        shape = copy;

        // create a new array for plotted points
        for (Point2D point : wrapper.getPlottedPoints()) {
            plottedPoints.add(new Point2D.Double(point.getX(), point.getY()));
        }
    }

    public Shape getShape() {
        return shape;
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

    public Color getFillColor() {
        return fillColor;
    }

    public void setLineThickness(int lineThickness) {
        this.lineThickness = lineThickness;
    }

    public int getLineThickness() {
        return lineThickness;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public LineType getLineType() {
        return lineType;
    }

    public void setLineType(LineType lineType) {
        this.lineType = lineType;
    }
}
