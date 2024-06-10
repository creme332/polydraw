package com.github.creme332.model;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class CanvasModel {
    /**
     * Spacing (in pixels) between top of canvas and tick label when axis is out of
     * sight.
     */
    public static final int TICK_PADDING_TOP = 20;

    /**
     * Spacing (in pixels) between bottom of canvas and tick label when axis is out
     * of sight.
     */
    public static final int TICK_PADDING_BOTTOM = 10;

    /**
     * Spacing (in pixels) between left border of canvas and tick label when axis is
     * out of sight.
     */
    public static final int TICK_PADDING_LEFT = 12;

    /**
     * Spacing (in pixels) between right border of canvas and tick label when axis
     * is out of sight.
     */
    public static final int TICK_PADDING_RIGHT = 30;

    /**
     * Distance in pixels between each unit on axes is out of sight.
     */
    int cellSize = 100;

    private float labelFontSizeScaleFactor = 1.4F;

    /**
     * Vertical distance between top border of canvas and my cartesian origin.
     */
    private int yZero;

    /**
     * Horizontal distance between left border of canvas and my cartesian origin.
     */
    private int xZero;

    private List<ShapeWrapper> shapes = new ArrayList<>();
    private Point2D firstPointClicked;
    private Point2D secondPointClicked;

    public Point2D getFirstClick() {
        return firstPointClicked;
    }

    public Point2D getSecondClick() {
        return secondPointClicked;
    }

    public void setFirstClick(Point2D p) {
        firstPointClicked = p;
    }

    public void setSecondClick(Point2D p) {
        secondPointClicked = p;
    }

    public AffineTransform getTransform() {
        AffineTransform transform = new AffineTransform();
        transform.translate(xZero, yZero);

        transform.scale(cellSize, -cellSize);

        return transform;
    }

    public List<ShapeWrapper> getShapes() {
        return shapes;
    }

    public void setShapes(List<ShapeWrapper> shapes) {
        this.shapes = shapes;
    }

    public int getCellSize() {
        return cellSize;
    }

    public float getLabelFontSizeSF() {
        return labelFontSizeScaleFactor;
    }

    public void setCellSize(int newCellSize) {
        cellSize = newCellSize;
    }

    public int getXZero() {
        return xZero;
    }

    public int getYZero() {
        return yZero;
    }

    public void setXZero(int newXZero) {
        xZero = newXZero;
    }

    public void setYZero(int newYZero) {
        yZero = newYZero;
    }
}
