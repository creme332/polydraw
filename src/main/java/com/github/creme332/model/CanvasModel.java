package com.github.creme332.model;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.awt.Color;
import java.awt.Dimension;

public class CanvasModel {
    private Dimension canvasDimension;

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

    /**
     * Font size of labels on canvas in pixels.
     */
    private int labelFontSize = 28;

    // define attributes for next shape to be drawn
    private LineType lineType = LineType.SOLID;
    private int lineThickness = 3;
    private Color shapeColor = Color.BLACK;
    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    /**
     * Vertical distance between top border of canvas and the polydraw origin.
     */
    private int yZero;

    /**
     * Horizontal distance between left border of canvas and the polydraw origin.
     */
    private int xZero;

    /**
     * Whether guidelines should be displayed on canvas
     */
    private boolean enableGuidelines = true;

    /**
     * Whether axes and labels should be displayed on canvas
     */
    private boolean axesVisible = true;

    /**
     * Mouse position of user on canvas in polyspace coordinates.
     */
    Point2D userMousePosition;

    ShapeManager shapeManager = new ShapeManager();

    /**
     * Index of shape on which user clicked when in Mode.MOVE_CANVAS.
     */
    int selectedShapeIndex = -1;

    public ShapeManager getShapeManager() {
        return shapeManager;
    }

    public void setSelectedShape(int shapeIndex) {
        selectedShapeIndex = shapeIndex;
    }

    public int getSelectedShape() {
        return selectedShapeIndex;
    }

    /**
     * 
     * @return Coordinates of user cursor in polyspace.
     */
    public Point2D getUserMousePosition() {
        return userMousePosition;
    }

    public void setCanvasDimension(Dimension canvasDimension) {
        this.canvasDimension = canvasDimension;
    }

    public Dimension getCanvasDimension() {
        return canvasDimension;
    }

    /**
     * 
     * @param newPosition New coordinates of user cursor in polyspace.
     */
    public void setUserMousePosition(Point2D newPosition) {
        userMousePosition = newPosition;
    }

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
     * Converts a shape in user space to polydraw space.
     * 
     * @param shape A shape defined in user space.
     * @return
     */
    public Shape toPolySpace(Shape shape) {
        return getPolySpaceTransform().createTransformedShape(shape);
    }

    /**
     * Converts a point in user space to polydraw space.
     * 
     * @param point A point defined in user space.
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

    /**
     * Checks if canvas is its standard view. To be in standard view, the origin
     * must be at center of screen and cell size must have its default value.
     * 
     * @return
     */
    public boolean isStandardView() {
        if (cellSize != DEFAULT_CELL_SIZE)
            return false;
        if (yZero != (int) canvasDimension.getHeight() / 2)
            return false;
        return (xZero == (int) canvasDimension.getWidth() / 2);
    }

    /**
     * Converts canvas to standard view.
     */
    public void toStandardView() {
        if (isStandardView())
            return;

        setYZero((int) canvasDimension.getHeight() / 2);
        setXZero((int) canvasDimension.getWidth() / 2);
        cellSize = DEFAULT_CELL_SIZE;

        support.firePropertyChange("standardView", false, true);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener("enableGuidelines", listener);
        support.addPropertyChangeListener("axesVisible", listener);
        support.addPropertyChangeListener("cellSize", listener);
        support.addPropertyChangeListener("standardView", listener);
        support.addPropertyChangeListener("labelFontSize", listener);
    }

    public int getCellSize() {
        return cellSize;
    }

    public void setLabelFontSize(int newFontSize) {
        support.firePropertyChange("labelFontSize", this.labelFontSize, newFontSize);
        labelFontSize = newFontSize;
    }

    public int getLabelFontSize() {
        return labelFontSize;
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

    public Color getShapeColor() {
        return shapeColor;
    }

    public void setShapeColor(Color fillColor) {
        this.shapeColor = fillColor;
    }

    public boolean isAxesVisible() {
        return axesVisible;
    }

    public void setAxesVisible(boolean axesVisible) {
        support.firePropertyChange("axesVisible", this.axesVisible, axesVisible);
        this.axesVisible = axesVisible;
    }
}
