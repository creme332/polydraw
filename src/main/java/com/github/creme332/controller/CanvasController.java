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

    public final int MAX_CELL_SIZE = 500;
    public final int MIN_CELL_SIZE = 30;

    public CanvasController(Canvas canvas) {
        this.canvas = canvas;

        canvas.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                handleCanvasResize();
            }
        });

        canvas.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                handleMousePressed(e);
            }
        });

        canvas.addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                // System.out.format("Mouse moved: %d, %d\n", e.getX(), e.getY());
            }
        });

        canvas.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                handleMouseDragged(e);
            }
        });

        canvas.addMouseWheelListener(new MouseAdapter() {
            public void mouseWheelMoved(MouseWheelEvent e) {
                handleCanvasZoom(e);
            }
        });
    }

    private void handleCanvasZoom(MouseWheelEvent e) {
        // System.out.println("Mouse wheel moved " + e.getScrollAmount() + " " +
        // e.getWheelRotation());

        if (e.getWheelRotation() == 1) {
            // zoom out
            canvas.setCellSize(Math.max(MIN_CELL_SIZE, canvas.getCellSize() - 10));

        } else {
            // zoom in
            canvas.setCellSize(Math.min(MAX_CELL_SIZE, canvas.getCellSize() + 10));
        }

        canvas.repaint();
    }

    private void handleMouseDragged(MouseEvent e) {
        // System.out.format("Mouse dragged: %d, %d\n", e.getX(), e.getY());

        if (initialClick == null) {
            initialClick = e.getPoint();
            return;
        }

        Point currentDrag = e.getPoint();
        int deltaX = currentDrag.x - initialClick.x;
        int deltaY = currentDrag.y - initialClick.y;
        // System.out.format("Mouse dragged by: %d, %d\n", deltaX, deltaY);

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

        System.out.println("Canvas size: " + width + " x " + height);

        canvas.repaint();
    }

    private void handleMousePressed(MouseEvent e) {
        initialClick = e.getPoint();

        System.out.format("Mouse pressed: %d, %d\n", e.getX(), e.getY());
    }
}
