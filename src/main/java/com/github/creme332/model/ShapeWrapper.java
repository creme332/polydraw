package com.github.creme332.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.awt.Shape;
import java.awt.geom.Point2D;

public class ShapeWrapper {
    private Shape shape;
    private Color lineColor = Color.BLACK;
    private Color fillColor = Color.BLACK;
    private LineType lineType = LineType.SOLID;
    /**
     * Coordinates plotted by user.
     */
    private List<Point2D> plottedPoints = new ArrayList<>();
    private int lineThickness;

    public List<Point2D> getPlottedPoints() {
        return plottedPoints;
    }

    public ShapeWrapper() {

    }

    public ShapeWrapper(Shape shape) {
        this.shape = shape;
    }

    public ShapeWrapper(Color lineColor, Color fillColor, LineType lineType, int lineThickness) {
        this.lineColor = lineColor;
        this.fillColor = fillColor;
        this.lineType = lineType;
        this.lineThickness = lineThickness;
    }

    public ShapeWrapper(Shape shape, Color lineColor, Color fillColor, LineType lineType) {
        this.shape = shape;
        this.lineColor = lineColor;
        this.fillColor = fillColor;
        this.lineType = lineType;
    }

    public ShapeWrapper(ShapeWrapper wrapper) {
        shape = wrapper.shape;
        lineColor = wrapper.lineColor;
        fillColor = wrapper.fillColor;
        lineType = wrapper.lineType;
        plottedPoints = wrapper.plottedPoints;
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
