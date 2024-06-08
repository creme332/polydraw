package com.github.creme332.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Shape;
import java.awt.Stroke;

public class ShapeWrapper {
    private Shape shape;
    private Color lineColor;
    private Color fillColor;
    private Stroke stroke;

    public ShapeWrapper() {
        this.shape = null;
        this.lineColor = null;
        this.fillColor = null;
        this.stroke = null;
    }

    public ShapeWrapper(Shape shape) {
        this.shape = shape;
        this.lineColor = Color.BLACK;
        this.fillColor = Color.BLACK;
        this.stroke = new BasicStroke(1);
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
