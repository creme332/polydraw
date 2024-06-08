package com.github.creme332.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import com.github.creme332.view.Canvas;

public class CanvasController {
    private Canvas canvas;
    private Point initialClick;

    public static final int MAX_CELL_SIZE = 500;
    public static final int DEFAULT_CELL_SIZE = 100;
    public static final int MIN_CELL_SIZE = 30;
    public static final int ZOOM_INCREMENT = 10;

    public CanvasController(Canvas canvas) {
        this.canvas = canvas;

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
                // TODO document why this method is empty
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
        canvas.getHomeButton().addActionListener(e -> handleHomeButton());
        canvas.getZoomInButton().addActionListener(e -> handleZoomInButton());
        canvas.getZoomOutButton().addActionListener(e -> handleZoomOutButton());
    }

    private void handleCanvasZoom(MouseWheelEvent e) {
        if (e.getWheelRotation() == 1) {
            // zoom out
            canvas.setCellSize(Math.max(MIN_CELL_SIZE, canvas.getCellSize() - ZOOM_INCREMENT));

        } else {
            // zoom in
            canvas.setCellSize(Math.min(MAX_CELL_SIZE, canvas.getCellSize() + ZOOM_INCREMENT));
        }

        canvas.repaint();
    }

    private void handleMouseDragged(MouseEvent e) {
        if (initialClick == null) {
            initialClick = e.getPoint();
            return;
        }

        Point currentDrag = e.getPoint();
        int deltaX = currentDrag.x - initialClick.x;
        int deltaY = currentDrag.y - initialClick.y;

        canvas.setYZero(canvas.getYZero() + deltaY);
        canvas.setXZero(canvas.getXZero() + deltaX);

        initialClick = currentDrag;

        canvas.repaint();
    }

    private void handleCanvasResize() {
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        // place origin at center of canvas
        canvas.setYZero(height / 2);
        canvas.setXZero(width / 2);

        canvas.positionZoomPanel();
        canvas.positionToolbar();

        canvas.repaint();
    }

    private void handleMousePressed(MouseEvent e) {
        initialClick = e.getPoint();
    }

    private void handleHomeButton() {
        canvas.setCellSize(DEFAULT_CELL_SIZE);
        canvas.repaint();
    }

    private void handleZoomInButton() {
        canvas.setCellSize(Math.min(MAX_CELL_SIZE, canvas.getCellSize() + ZOOM_INCREMENT));
        canvas.repaint();
    }

    private void handleZoomOutButton() {
        canvas.setCellSize(Math.max(MIN_CELL_SIZE, canvas.getCellSize() - ZOOM_INCREMENT));
        canvas.repaint();
    }
}
