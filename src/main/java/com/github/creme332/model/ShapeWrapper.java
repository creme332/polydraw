package com.github.creme332.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Point2D;

public class ShapeWrapper {
    private Shape shape;
    private Color lineColor = Color.BLACK;
    private Color fillColor = Color.BLACK;
    private Stroke stroke = new BasicStroke(1);
    private List<Point2D> plottedPoints = new ArrayList<>();

    public List<Point2D> getPlottedPoints() {
        return plottedPoints;
    }

    public ShapeWrapper() {

    }

    public ShapeWrapper(Shape shape) {
        this.shape = shape;
    }

    public ShapeWrapper(Shape shape, Color lineColor, Color fillColor, Stroke stroke) {
        this.shape = shape;
        this.lineColor = lineColor;
        this.fillColor = fillColor;
        this.stroke = stroke;
    }

    public ShapeWrapper(ShapeWrapper wrapper) {
        shape = wrapper.shape;
        lineColor = wrapper.lineColor;
        fillColor = wrapper.fillColor;
        stroke = wrapper.stroke;
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

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public Stroke getStroke() {
        return stroke;
    }

    public void setStroke(Stroke stroke) {
        this.stroke = stroke;
    }
}
