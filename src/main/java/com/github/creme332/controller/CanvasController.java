package com.github.creme332.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;

import com.github.creme332.model.AppState;
import com.github.creme332.model.CanvasModel;
import com.github.creme332.model.Mode;
import com.github.creme332.model.ShapeWrapper;
import com.github.creme332.view.Canvas;

public class CanvasController implements PropertyChangeListener {
    private Canvas canvas;

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
        if (app.getMode() == Mode.MOVE_GRAPHICS_VIEW || app.getMode() == Mode.MOVE_CANVAS) {
            mouseDragStart = e.getPoint();
        }

        /**
         * Coordinates of mouse pressed in the polydraw coordinate system
         */
        Point2D polySpaceMousePosition = model.toPolySpace(e.getPoint());
        System.out.println(polySpaceMousePosition);

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

        // if mode from AppState has changed while a shape is being drawn
        if ("mode".equals(propertyName) && currentWrapper != null) {
            // erase incomplete shape
            model.getShapes().remove(currentWrapper);
            currentWrapper = null;
            canvas.repaint();
            return;
        }

        // if guidelines were toggled or zoom was changed or axes were toggled
        if ("enableGuidelines".equals(propertyName) || "zoomChange".equals(propertyName) || "axesVisible".equals(propertyName)) {
            canvas.repaint();
            return;
        }
    }
}
