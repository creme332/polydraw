package com.github.creme332.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;

import com.github.creme332.algorithms.CircleAlgorithm;
import com.github.creme332.model.AppState;
import com.github.creme332.model.CanvasModel;
import com.github.creme332.model.Mode;
import com.github.creme332.model.ShapeWrapper;
import com.github.creme332.view.Canvas;

public class CanvasController implements PropertyChangeListener {
    private Canvas canvas;

    private CircleAlgorithm circleCalculator = new CircleAlgorithm();
    /**
     * Used to store coordinate where mouse drag started
     */
    private Point mouseDragStart;
    private AppState app;
    private CanvasModel model;

    /**
     * Wrapper for shape currently being drawn.
     */
    private ShapeWrapper currentWrapper;

    public CanvasController(AppState app, Canvas canvas) {
        this.app = app;
        this.canvas = canvas;
        this.model = app.getCanvasModel();
        model.addPropertyChangeListener(this);

        app.addPropertyChangeListener(this);

        canvas.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                handleCanvasResize();
            }
        });

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleMousePressed(e);
            }
        });

        canvas.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Point2D polySpaceMousePosition = model.toPolySpace(e.getPoint());

                if ((app.getMode() == Mode.DRAW_LINE_BRESENHAM || app.getMode() == Mode.DRAW_LINE_DDA)
                        && currentWrapper != null && currentWrapper.getPlottedPoints().size() == 1) {

                    Line2D line = new Line2D.Double(currentWrapper.getPlottedPoints().get(0),
                            polySpaceMousePosition);
                    currentWrapper.setShape(line);
                    canvas.repaint();
                    return;
                }
                if (app.getMode() == Mode.DRAW_CIRCLE_DYNAMIC && currentWrapper != null
                        && currentWrapper.getPlottedPoints().size() == 1) {

                    Point2D center = currentWrapper.getPlottedPoints().get(0);
                    double radius = polySpaceMousePosition.distance(center);
                    int roundedRadius = (int) Math.round(radius);
                    if (roundedRadius == 0)
                        return;

                    // System.out.format("plotting center center (%d, %d) radius %d %n", (int) center.getX(),
                    //         (int) center.getY(), roundedRadius);

                    currentWrapper.setShape(getCircle((int) center.getX(), (int) center.getY(), roundedRadius));
                    canvas.repaint();
                    return;

                }

            }
        });

        canvas.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                handleMouseDragged(e);
            }
        });

        canvas.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                handleCanvasZoom(e);
            }
        });

        // Add action listeners for the zoom panel buttons
        canvas.getHomeButton().addActionListener(e -> resetCanvasView());
        canvas.getZoomInButton().addActionListener(e -> model.updateCanvasZoom(true));
        canvas.getZoomOutButton().addActionListener(e -> model.updateCanvasZoom(false));
    }

    private void handleCanvasZoom(MouseWheelEvent e) {
        model.updateCanvasZoom(e.getWheelRotation() != 1);
        canvas.repaint();
    }

    private Polygon getCircle(int x, int y, int radius) {
        int[][] coordinates = circleCalculator.calculateOrderedPoints(x, y, radius);
        return new Polygon(coordinates[0], coordinates[1], coordinates[0].length);
    }

    private void handleMouseDragged(MouseEvent e) {
        if (mouseDragStart == null) {
            mouseDragStart = e.getPoint();
            return;
        }

        if (app.getMode() == Mode.MOVE_GRAPHICS_VIEW || app.getMode() == Mode.MOVE_CANVAS) {
            Point currentDrag = e.getPoint();
            int deltaX = currentDrag.x - mouseDragStart.x;
            int deltaY = currentDrag.y - mouseDragStart.y;

            model.setYZero(model.getYZero() + deltaY);
            model.setXZero(model.getXZero() + deltaX);

            mouseDragStart = currentDrag;

            canvas.repaint();
        }
    }

    private void handleCanvasResize() {
        if (model == null)
            return;
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        // place origin at center of canvas
        model.setYZero(height / 2);
        model.setXZero(width / 2);

        canvas.repaint();
    }

    private void handleMousePressed(MouseEvent e) {
        /**
         * Coordinates of mouse pressed in the polydraw coordinate system
         */
        Point2D polySpaceMousePosition = model.toPolySpace(e.getPoint());

        if (app.getMode() == Mode.MOVE_GRAPHICS_VIEW || app.getMode() == Mode.MOVE_CANVAS) {
            mouseDragStart = e.getPoint();
            return;
        }

        if (app.getMode() == Mode.DRAW_LINE_BRESENHAM || app.getMode() == Mode.DRAW_LINE_DDA) {
            if (currentWrapper == null) {
                // first coordinate of line has been selected

                // create a shape wrapper
                currentWrapper = new ShapeWrapper();
                currentWrapper.getPlottedPoints().add(polySpaceMousePosition);

                // save wrapper
                model.getShapes().add(currentWrapper);

            } else {
                // second coordinate has now been selected

                // create a line
                Point2D lineStart = currentWrapper.getPlottedPoints().get(0);
                Point2D lineEnd = polySpaceMousePosition;
                Line2D line = new Line2D.Double();
                line.setLine(lineStart, lineEnd);

                currentWrapper.getPlottedPoints().add(lineEnd);
                currentWrapper.setShape(line);

                currentWrapper = null;
            }
        }

        if (app.getMode() == Mode.DRAW_CIRCLE_DYNAMIC) {
            if (currentWrapper == null) {
                // center has been selected

                // create a shape wrapper
                currentWrapper = new ShapeWrapper();
                currentWrapper.getPlottedPoints().add(polySpaceMousePosition);

                // save wrapper
                model.getShapes().add(currentWrapper);

            } else {
                // second point has now been selected
                currentWrapper.getPlottedPoints().add(polySpaceMousePosition);

                // create a circle
                Point2D center = currentWrapper.getPlottedPoints().get(0);
                double radius = polySpaceMousePosition.distance(center);
                int roundedRadius = (int) Math.round(radius);
                if (roundedRadius == 0)
                    return;

                currentWrapper.setShape(getCircle((int) center.getX(), (int) center.getY(), roundedRadius));

                currentWrapper = null;
            }
        }

        if (app.getMode() == Mode.DRAW_ELLIPSE) {
            if (currentWrapper == null) {
                // first foci has been selected

                // create a shape wrapper
                currentWrapper = new ShapeWrapper();
                currentWrapper.getPlottedPoints().add(polySpaceMousePosition);

                // save wrapper
                model.getShapes().add(currentWrapper);

            } else {
                // second foci has now been selected
                if (currentWrapper.getPlottedPoints().size() == 1) {
                    currentWrapper.getPlottedPoints().add(polySpaceMousePosition);
                } else {
                    // third point has been selected
                    currentWrapper.getPlottedPoints().add(polySpaceMousePosition);

                    // create an ellipse
                    Polygon ellipse = new Polygon();

                    currentWrapper.setShape(ellipse);

                    currentWrapper = null;
                }
            }
        }

        canvas.repaint();
    }

    private void resetCanvasView() {
        // show origin at center of canvas
        model.setXZero(canvas.getWidth() / 2);
        model.setYZero(canvas.getHeight() / 2);

        // reset zoom level
        model.setCellSize(CanvasModel.DEFAULT_CELL_SIZE);
        canvas.repaint();
    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        final String propertyName = e.getPropertyName();
        // if mode has changed while a shape is being drawn
        if ("mode".equals(propertyName) && currentWrapper != null) {
            // erase incomplete shape
            model.getShapes().remove(currentWrapper);
            currentWrapper = null;
            canvas.repaint();
        }
        if ("guidelines".equals(propertyName) || "zoomChange".equals(propertyName)) {
            canvas.repaint();
        }
    }
}
