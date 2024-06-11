package com.github.creme332.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;

import com.github.creme332.model.AppState;
import com.github.creme332.model.CanvasModel;
import com.github.creme332.model.Mode;
import com.github.creme332.model.ShapeWrapper;
import com.github.creme332.view.Canvas;

public class CanvasController {
    private Canvas canvas;

    /**
     * Used to store coordinate where mouse drag started
     */
    private Point mouseDragStart;
    private AppState app;
    private CanvasModel model;

    private static final int MAX_CELL_SIZE = 500;
    private static final int DEFAULT_CELL_SIZE = 100;
    private static final int MIN_CELL_SIZE = 30;
    private Point currentMousePosition;

    /**
     * Wrapper for shape currently being drawn.
     */
    private ShapeWrapper currentWrapper;

    public CanvasController(AppState app, Canvas canvas) {
        this.app = app;
        this.canvas = canvas;
        this.model = app.getCanvasModel();

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
                currentMousePosition = e.getPoint();

                if ((app.getMode() == Mode.DRAW_LINE_BRESENHAM || app.getMode() == Mode.DRAW_LINE_DDA)
                        && currentWrapper != null && currentWrapper.getPlottedPoints().size() == 1) {

                    Line2D line = new Line2D.Double(currentWrapper.getPlottedPoints().get(0),
                            toMySpace(currentMousePosition));
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
        canvas.getZoomInButton().addActionListener(e -> updateCanvasZoom(true));
        canvas.getZoomOutButton().addActionListener(e -> updateCanvasZoom(false));
    }

    private void handleCanvasZoom(MouseWheelEvent e) {
        updateCanvasZoom(e.getWheelRotation() != 1);
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

    private Point2D toMySpace(Point userSpaceCoord) {
        double x = (userSpaceCoord.getX() - model.getXZero()) / model.getCellSize();
        double y = (model.getYZero() - userSpaceCoord.getY()) / model.getCellSize();
        return new Point2D.Double(x, y);
    }

    private void handleMousePressed(MouseEvent e) {
        if (app.getMode() == Mode.MOVE_GRAPHICS_VIEW || app.getMode() == Mode.MOVE_CANVAS) {
            mouseDragStart = e.getPoint();
        }

        if (app.getMode() == Mode.DRAW_LINE_BRESENHAM || app.getMode() == Mode.DRAW_LINE_DDA) {

            if (currentWrapper == null) {
                // first coordinate of line has been selected

                // create a shape wrapper
                currentWrapper = new ShapeWrapper();
                currentWrapper.getPlottedPoints().add(toMySpace(e.getPoint()));
                currentWrapper.setPreview(true);

                // save wrapper
                model.getShapes().add(currentWrapper);

            } else {
                // second coordinate has now been selected

                // create a line
                Point2D lineStart = currentWrapper.getPlottedPoints().get(0);
                Point2D lineEnd = toMySpace(e.getPoint());
                Line2D line = new Line2D.Double();
                line.setLine(lineStart, lineEnd);

                currentWrapper.getPlottedPoints().add(lineEnd);
                currentWrapper.setShape(line);
                currentWrapper.setPreview(false);

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
        model.setCellSize(DEFAULT_CELL_SIZE);
        canvas.repaint();
    }

    /**
     * Either zooms in or out of canvas.
     * 
     * @param zoomIn Zoom in if true, zoom out otherwise
     */
    private void updateCanvasZoom(boolean zoomIn) {
        final int ZOOM_INCREMENT = 10;

        if (zoomIn) {
            model.setCellSize(Math.min(MAX_CELL_SIZE, model.getCellSize() + ZOOM_INCREMENT));
        } else {
            model.setCellSize(Math.max(MIN_CELL_SIZE, model.getCellSize() - ZOOM_INCREMENT));
        }
        canvas.repaint();
    }
}
