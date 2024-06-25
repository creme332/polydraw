package com.github.creme332.model;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

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

    public static final int MAX_CELL_SIZE = 500;
    public static final int MIN_CELL_SIZE = 1;
    public static final int DEFAULT_CELL_SIZE = 100;
    public static final int ZOOM_INCREMENT = 1;

    /**
     * Distance in pixels between each unit on axes. It guaranteed to be within
     * MIN_CELL_SIZEa and MAX_CELL_SIZE
     */
    int cellSize = Math.max(MIN_CELL_SIZE, Math.min(DEFAULT_CELL_SIZE, MAX_CELL_SIZE));

    private float labelFontSizeScaleFactor = 1.4F;

    private LineType lineType = LineType.SOLID;
    private int lineThickness = 3;
    private Color fillColor = Color.BLACK;
    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    /**
     * Vertical distance between top border of canvas and the polydraw origin.
     */
    private int yZero;

    /**
     * Horizontal distance between left border of canvas and the polydraw origin.
     */
    private int xZero;

    private List<ShapeWrapper> shapes = new ArrayList<>();

    private boolean enableGuidelines = true; // Variable to track guidelines visibility
    private boolean axesVisible = true; // Variable to track axes visibility

    /**
     * 
     * @return Transformation required to convert a coordinate in the polydraw
     *         coordinate system to the user space coordinate system.
     * 
     *         Let X be the x-coordinate of a point in the polydraw space. The
     *         corresponding
     *         coordinate in the user space coordinate system is X * cellSize +
     *         xAxisOrigin.
     * 
     *         Let Y be the y-coordinate of a point in the polydraw space. The
     *         corresponding
     *         coordinate in the user space coordinate system is -Y * cellSize +
     *         yAxisOrigin.
     */
    public AffineTransform getUserSpaceTransform() {
        AffineTransform transform = new AffineTransform();
        transform.translate(xZero, yZero); // applied second

        transform.scale(cellSize, -cellSize); // applied first

        return transform;
    }

    /**
     * 
     * @return Transformation required to convert a coordinate in the user space
     *         coordinate system to the polydraw coordinate system.
     */
    private AffineTransform getPolySpaceTransform() {
        AffineTransform transform = null;
        try {
            transform = getUserSpaceTransform().createInverse();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        return transform;
    }

    public void clearCanvas() {
        int oldSize = shapes.size();
        shapes.clear();
        // notify listeners that number of shapes on canvas has changed
        support.firePropertyChange("clearCanvas", oldSize, shapes.size());
    }

    /**
     * 
     * @param shape Shape in polydraw space
     * @return New shape in user space
     */
    public Shape toUserSpace(Shape shape) {
        return getUserSpaceTransform().createTransformedShape(shape);
    }

    /**
     * 
     * @param point Point in polydraw space
     * @return New shape in user space
     */
    public Point2D toUserSpace(Point2D point) {
        return getUserSpaceTransform().transform(point, null);
    }

    /**
     * 
     * @param shape
     * @return
     */
    public Shape toPolySpace(Shape shape) {
        return getPolySpaceTransform().createTransformedShape(shape);
    }

    /**
     * 
     * @param point
     * @return
     */
    public Point2D toPolySpace(Point2D point) {
        Point2D polySpaceMousePosition = getPolySpaceTransform().transform(point, null);

        return new Point((int) Math.round(polySpaceMousePosition.getX()),
                (int) Math.round(polySpaceMousePosition.getY()));
    }

    /**
     * Either zooms in or out of canvas, assuming zoom level is within accepted
     * range.
     * 
     * @param zoomIn Zoom in if true, zoom out otherwise
     */
    public void updateCanvasZoom(boolean zoomIn) {
        int newCellSize;
        if (zoomIn) {
            newCellSize = (Math.min(CanvasModel.MAX_CELL_SIZE, getCellSize() + CanvasModel.ZOOM_INCREMENT));
        } else {
            newCellSize = (Math.max(CanvasModel.MIN_CELL_SIZE, getCellSize() - CanvasModel.ZOOM_INCREMENT));
        }
        support.firePropertyChange("cellSize", cellSize, newCellSize);
        cellSize = newCellSize;
    }

    public void resetZoom() {
        support.firePropertyChange("cellSize", cellSize, DEFAULT_CELL_SIZE);
        cellSize = DEFAULT_CELL_SIZE;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener("enableGuidelines", listener);
        support.addPropertyChangeListener("axesVisible", listener);
        support.addPropertyChangeListener("cellSize", listener);
        support.addPropertyChangeListener("clearCanvas", listener);
    }

    /**
     * 
     * @return A copy of the current shapes that should be displayed
     */
    public List<ShapeWrapper> getShapesCopy() {
        ArrayList<ShapeWrapper> copy = new ArrayList<>();

        for (ShapeWrapper wrapper : shapes) {
            copy.add(new ShapeWrapper(wrapper));
        }

        return copy;
    }

    public void addShape(ShapeWrapper wrapper) {
        shapes.add(wrapper);
    }

    public void removeShape(ShapeWrapper wrapper) {
        shapes.remove(wrapper);
    }

    public void removeShape(int i) {
        shapes.remove(i);
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

    public boolean isGuidelinesEnabled() {
        return enableGuidelines;
    }

    public void setGuidelinesEnabled(boolean enableGuidelines) {
        support.firePropertyChange("enableGuidelines", this.enableGuidelines, enableGuidelines);
        this.enableGuidelines = enableGuidelines;
    }

    public LineType getLineType() {
        return lineType;
    }

    public void setLineType(LineType lineType) {
        this.lineType = lineType;
    }

    public int getLineThickness() {
        return lineThickness;
    }

    public void setLineThickness(int lineThickness) {
        this.lineThickness = lineThickness;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public boolean isAxesVisible() {
        return axesVisible;
    }

    public void setAxesVisible(boolean axesVisible) {
        support.firePropertyChange("axesVisible", this.axesVisible, axesVisible);
        this.axesVisible = axesVisible;
    }
}
