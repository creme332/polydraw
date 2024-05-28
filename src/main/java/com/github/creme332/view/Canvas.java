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
    RedSquare redSquare = new RedSquare();
    private int width;
    private int height;

    private Point initialClick;
    final int cellSize = 100;
    private int xCenter = 0; // displayed x-coordinate of canvas center
    private int yCenter = 0; // displayed y-coordinate of canvas center
    private double scaleFactor = 1;

    private int xAxisPos; // y-coordinate of x-axis relative to canvas top left
    private int yAxisPos; // x-coordinate of y-axis relative to canvas top left

    public Canvas() {
        // add border
        // Border border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        // this.setBorder(border);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                width = getWidth();
                height = getHeight();

                xAxisPos = height / 2;
                yAxisPos = width / 2;

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

                xAxisPos += deltaY;
                yAxisPos += deltaX;

                initialClick = currentDrag;

                repaint();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                // System.out.format("Mouse moved: %d, %d\n", e.getX(), e.getY());
            }
        });

        // addMouseMotionListener(new MouseAdapter() {
        // public void mouseDragged(MouseEvent e) {
        // moveSquare(e.getX(), e.getY());
        // }
        // });

        addMouseWheelListener(new MouseAdapter() {
            public void mouseWheelMoved(MouseWheelEvent e) {
                System.out.println("Mouse wheel moved " + e.getScrollAmount() + " " + e.getWheelRotation());

                if (e.getWheelRotation() == 1) {
                    // zoom out
                    scaleFactor = Math.max(1, scaleFactor - 0.1);

                } else {
                    // zoom in
                    scaleFactor = Math.min(30, scaleFactor + 0.1);
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

        // calculate yposition of floating label
        int labelYPos = Math.min(height - 10, Math.max(12, xAxisPos));

        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2)); // Set line thickness

        // if axis is within canvas, draw horizontal line to represent horizontal
        // axis
        if (xAxisPos >= 0 && xAxisPos <= height)
            g2.drawLine(0, xAxisPos, width, xAxisPos);

        // label center of x axis
        g2.drawString(Integer.valueOf(xCenter).toString(), yAxisPos, labelYPos);

        // label ticks on positive horizontal axis
        for (int i = 1; i <= (width - yAxisPos) / (cellSize); i++) {
            int labelX = yAxisPos + i * cellSize;
            g2.drawString(Integer.valueOf(xCenter + i).toString(), labelX, labelYPos);
            // g2.drawLine(labelX, 0, labelX, height);
        }

        // label ticks on negative horizontal axis
        for (int i = -1; i >= -yAxisPos / (cellSize); i--) {
            int labelX = yAxisPos + i * cellSize;
            g2.drawString(Integer.valueOf(xCenter + i).toString(), labelX, labelYPos);
            // g2.drawLine(labelX, 0, labelX, height);
        }
    }

    private void drawGuidelines(Graphics2D g2) {
        g2.setColor(Color.gray);
        g2.setStroke(new BasicStroke(1));

        // draw horizontal guidelines above x-axis
        int lineCount = xAxisPos / (cellSize);
        for (int i = 1; i <= lineCount; i++) {
            int y1 = xAxisPos - i * cellSize;
            g2.drawLine(0, y1, width, y1); // draw guideline above x axis
        }

        // draw horizontal guidelines below x-axis
        lineCount = (height - xAxisPos) / (cellSize);
        for (int i = 1; i <= lineCount; i++) {
            int y0 = xAxisPos + i * cellSize;
            g2.drawLine(0, y0, width, y0); // draw guideline below x axis
        }

        // draw vertical guidelines before y-axis
        lineCount = yAxisPos / (cellSize);
        for (int i = 1; i <= lineCount; i++) {
            int x0 = yAxisPos - i * cellSize;
            g2.drawLine(x0, 0, x0, height); // line before y axis
        }

        // draw vertical guidelines after y-axis
        lineCount = (width - yAxisPos) / (cellSize);
        for (int i = 1; i <= lineCount; i++) {
            int x1 = yAxisPos + i * cellSize;
            g2.drawLine(x1, 0, x1, height); // line after y axis
        }

    }

    private void drawVerticalAxis(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2)); // Set line thickness

        int labelYPos = Math.min(width - 30, Math.max(12, yAxisPos));

        if (yAxisPos >= 0 || yAxisPos <= width)
            g2.drawLine(yAxisPos, 0, yAxisPos, height); // vertical axis

        // label center of vertical axis
        g2.drawString(Integer.valueOf(yCenter).toString(), labelYPos, xAxisPos);

        // label ticks on positive vertical axis
        for (int i = 1; i <= xAxisPos / (cellSize); i++) {
            int labelY = xAxisPos - i * cellSize;
            g2.drawString(Integer.valueOf(yCenter + i).toString(), labelYPos,
                    labelY);
        }

        // label ticks on negative vertical axis
        for (int i = 1; i <= (height - xAxisPos) / (cellSize); i++) {
            int labelY = xAxisPos + i * cellSize;

            String label = Integer.valueOf(yCenter - i).toString();

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
