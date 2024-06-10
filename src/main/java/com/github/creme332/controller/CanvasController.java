package com.github.creme332.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
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
    private Point initialClick;
    private AppState app;
    private CanvasModel model;

    private static final int MAX_CELL_SIZE = 500;
    private static final int DEFAULT_CELL_SIZE = 100;
    private static final int MIN_CELL_SIZE = 30;
    private Point currentMousePosition;

    private List<ShapeWrapper> shapes = new ArrayList<>();

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
        if (initialClick == null) {
            initialClick = e.getPoint();
            return;
        }

        if (app.getMode() == Mode.MOVE_GRAPHICS_VIEW || app.getMode() == Mode.MOVE_CANVAS) {
            Point currentDrag = e.getPoint();
            int deltaX = currentDrag.x - initialClick.x;
            int deltaY = currentDrag.y - initialClick.y;

            model.setYZero(model.getYZero() + deltaY);
            model.setXZero(model.getXZero() + deltaX);

            initialClick = currentDrag;

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

        canvas.positionZoomPanel();
        canvas.positionToolbar();

        canvas.repaint();
    }

    private void handleMousePressed(MouseEvent e) {
        initialClick = e.getPoint();

        if (app.getMode() == Mode.DRAW_LINE_BRESENHAM || app.getMode() == Mode.DRAW_LINE_DDA) {
            Line2D line = new Line2D.Double();
            line.setLine(e.getX(), e.getY(), 0, 0);

            ShapeWrapper shape = new ShapeWrapper();
            shape.setShape(line);

            System.out.println("Added shape");
            shapes.add(shape);
        }

        model.setShapes(shapes);
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
