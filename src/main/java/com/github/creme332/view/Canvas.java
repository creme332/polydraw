package com.github.creme332.view;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;

public class Canvas extends JPanel {
    private final int TICK_PADDING_TOP = 12; // spacing between top of canvas and tick label when axis is out of sight
    private final int TICK_PADDING_BOTTOM = 10; // spacing between bottom of canvas and tick label when axis is out of
                                                // sight
    private final int TICK_PADDING_LEFT = 12; // spacing between left border of canvas and tick label when axis is out
                                              // of
    // sight
    private final int TICK_PADDING_RIGHT = 30; // spacing between right border of canvas and tick label when axis is out
                                               // of
    // sight
    private final int MAX_CELL_SIZE = 500;
    private final int MIN_CELL_SIZE = 30;

    RedSquare redSquare = new RedSquare();
    private int width;
    private int height;

    private Point initialClick;
    int cellSize = 100; // distance in pixels between each unit on axes

    private double scaleFactor = 1;

    private int yZero; // vertical distance between top border of canvas and my cartesian origin
    private int xZero; // horizontal distance between left border of canvas and my cartesian origin

    public Canvas() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                width = getWidth();
                height = getHeight();

                yZero = height / 2;
                xZero = width / 2;

                System.out.println("Canvas size: " + width + " x " + height);
            }
        });

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
                System.out.format("Mouse pressed: %d, %d\n", e.getX(), e.getY());
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                System.out.format("Mouse dragged: %d, %d\n", e.getX(), e.getY());

                Point currentDrag = e.getPoint();
                int deltaX = currentDrag.x - initialClick.x;
                int deltaY = currentDrag.y - initialClick.y;
                System.out.format("Mouse dragged by: %d, %d\n", deltaX, deltaY);

                yZero += deltaY;
                xZero += deltaX;

                initialClick = currentDrag;

                repaint();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                // System.out.format("Mouse moved: %d, %d\n", e.getX(), e.getY());
            }
        });

        addMouseWheelListener(new MouseAdapter() {
            public void mouseWheelMoved(MouseWheelEvent e) {
                System.out.println("Mouse wheel moved " + e.getScrollAmount() + " " + e.getWheelRotation());

                if (e.getWheelRotation() == 1) {
                    // zoom out
                    cellSize = Math.max(MIN_CELL_SIZE, cellSize - 10);

                } else {
                    // zoom in
                    cellSize = Math.min(MAX_CELL_SIZE, cellSize + 10);
                }
                repaint();
            }
        });
    }

    public static void setAntialiasing(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    }

    private void moveSquare(int x, int y) {

        // Current square state, stored as final variables
        // to avoid repeat invocations of the same methods.
        final int CURR_X = redSquare.getX();
        final int CURR_Y = redSquare.getY();
        final int CURR_W = redSquare.getWidth();
        final int CURR_H = redSquare.getHeight();
        final int OFFSET = 100;

        if ((CURR_X != x) || (CURR_Y != y)) {

            // The square is moving, repaint background
            // over the old square location.
            repaint(CURR_X - 1, CURR_Y - 1, CURR_W + OFFSET + 1, CURR_H + OFFSET + 1);

            // Update coordinates.
            redSquare.setX(x);
            redSquare.setY(y);

            // Repaint the square at the new location.
            repaint(redSquare.getX(), redSquare.getY(),
                    redSquare.getWidth() + OFFSET,
                    redSquare.getHeight() + OFFSET);
            // repaint();

        }
    }

    private void drawHorizontalAxis(Graphics2D g2) {
        // calculate y position of tick label
        int labelYPos = Math.min(height - TICK_PADDING_BOTTOM, Math.max(TICK_PADDING_TOP, yZero));

        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2)); // Set line thickness

        // if axis is within canvas, draw horizontal line to represent horizontal
        // axis
        if (yZero >= 0 && yZero <= height)
            g2.drawLine(0, yZero, width, yZero);

        // label center of x axis
        g2.drawString(Integer.valueOf(0).toString(), xZero, labelYPos);

        // label ticks on positive horizontal axis
        for (int i = 1; i <= (width - xZero) / (cellSize); i++) {
            int labelX = xZero + i * cellSize;
            g2.drawString(Integer.valueOf(i).toString(), labelX, labelYPos);
        }

        // label ticks on negative horizontal axis
        for (int i = -1; i >= -xZero / (cellSize); i--) {
            int labelX = xZero + i * cellSize;
            g2.drawString(Integer.valueOf(i).toString(), labelX, labelYPos);
        }
    }

    private void drawGuidelines(Graphics2D g2) {
        g2.setColor(Color.gray);
        g2.setStroke(new BasicStroke(1));

        // draw horizontal guidelines above x-axis
        int lineCount = yZero / (cellSize);
        for (int i = 1; i <= lineCount; i++) {
            int y1 = yZero - i * cellSize;
            g2.drawLine(0, y1, width, y1); // draw guideline above x axis
        }

        // draw horizontal guidelines below x-axis
        lineCount = (height - yZero) / (cellSize);
        for (int i = 1; i <= lineCount; i++) {
            int y0 = yZero + i * cellSize;
            g2.drawLine(0, y0, width, y0); // draw guideline below x axis
        }

        // draw vertical guidelines before y-axis
        lineCount = xZero / (cellSize);
        for (int i = 1; i <= lineCount; i++) {
            int x0 = xZero - i * cellSize;
            g2.drawLine(x0, 0, x0, height); // line before y axis
        }

        // draw vertical guidelines after y-axis
        lineCount = (width - xZero) / (cellSize);
        for (int i = 1; i <= lineCount; i++) {
            int x1 = xZero + i * cellSize;
            g2.drawLine(x1, 0, x1, height); // line after y axis
        }

    }

    private void drawVerticalAxis(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2)); // Set line thickness

        int labelYPos = Math.min(width - TICK_PADDING_RIGHT, Math.max(TICK_PADDING_LEFT, xZero));

        if (xZero >= 0 || xZero <= width)
            g2.drawLine(xZero, 0, xZero, height); // vertical axis

        // label center of vertical axis
        g2.drawString(Integer.valueOf(0).toString(), labelYPos, yZero);

        // label ticks on positive vertical axis
        for (int i = 1; i <= yZero / (cellSize); i++) {
            int labelY = yZero - i * cellSize;
            g2.drawString(Integer.valueOf(i).toString(), labelYPos,
                    labelY);
        }

        // label ticks on negative vertical axis
        for (int i = 1; i <= (height - yZero) / (cellSize); i++) {
            int labelY = yZero + i * cellSize;

            String label = Integer.valueOf(-i).toString();

            g2.drawString(label, labelYPos, labelY);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.scale(scaleFactor, scaleFactor);
        drawGuidelines(g2);
        drawHorizontalAxis(g2);
        drawVerticalAxis(g2);
    }
}
