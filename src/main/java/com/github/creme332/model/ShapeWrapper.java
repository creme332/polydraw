package com.github.creme332.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class ShapeWrapper {
    private Shape shape;
    private Color lineColor;
    private Color fillColor;
    private Stroke stroke;
    private List<Point2D> plottedPoints;
    private boolean preview;

    public ShapeWrapper() {
        preview = false;
        plottedPoints = new ArrayList<>();
        this.shape = null;
        this.lineColor = null;
        this.fillColor = null;
        this.stroke = null;
    }

    public List<Point2D> getPlottedPoints() {
        return plottedPoints;
    }

    public ShapeWrapper(Shape shape) {
        preview = false;
        plottedPoints = new ArrayList<>();
        this.shape = shape;
        this.lineColor = Color.BLACK;
        this.fillColor = Color.BLACK;
        this.stroke = new BasicStroke(1);
    }

    public ShapeWrapper(Shape shape, Color lineColor, Color fillColor, Stroke stroke) {
        preview = false;
        plottedPoints = new ArrayList<>();
        this.shape = shape;
        this.lineColor = lineColor;
        this.fillColor = fillColor;
        this.stroke = stroke;
    }

    public ShapeWrapper(ShapeWrapper wrapper) {
        preview = false;
        plottedPoints = new ArrayList<>();
        shape = wrapper.shape;
        lineColor = wrapper.lineColor;
        fillColor = wrapper.fillColor;
        stroke = wrapper.stroke;
    }

    public boolean getPreview() {
        return preview;
    }

    public void setPreview(boolean b) {
        preview = b;
    }

    public Shape getTransformedShape(AffineTransform transform) {
        return transform.createTransformedShape(this.shape);
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
