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
    private int xCenter = 0; // x-translation of canvas center
    private int yCenter = 0; // y-translation of canvas center

    public Canvas() {
        // add border
        // Border border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        // this.setBorder(border);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                width = getWidth();
                height = getHeight();
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
                System.out.format("Mouse dragged by: %d, %d\n", deltaX / cellSize, deltaY / cellSize);
                xCenter += deltaX / cellSize;
                initialClick = e.getPoint();

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

                } else {
                    // zoom in

                }
                repaint();
            }
        });
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
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2)); // Set line thickness

        int yPosition = height / 2; // y-coordinate of position of horizontal axis relative to canvas top left

        // draw horizontal line to represent horizontal axis
        g2.drawLine(0, yPosition, width, yPosition);

        // label origin
        g2.drawString(Integer.valueOf(xCenter).toString(), width / 2, yPosition);

        // label ticks on positive horizontal axis
        for (int i = 1; i <= width / (2 * cellSize); i++) {
            g2.drawString(Integer.valueOf(xCenter + i).toString(), xCenter + width / 2 + i * cellSize,
                    yCenter + height / 2);
        }

        // label ticks on negative horizontal axis
        for (int i = -1; i >= -width / (2 * cellSize); i--) {
            g2.drawString(Integer.valueOf(xCenter + i).toString(), xCenter + width / 2 + i * cellSize,
                    yCenter + height / 2);
        }
    }

    private void drawGuidelines(Graphics2D g2) {
        g2.setColor(Color.gray);
        g2.setStroke(new BasicStroke(1));

        // label ticks on positive vertical axis

        // calculate number of horizontal lines above x-axis
        int lineCount = height / (2 * cellSize);

        // draw horizontal guidelines
        for (int i = 1; i <= lineCount; i++) {
            int y0 = height / 2 + i * cellSize;
            int y1 = height / 2 - i * cellSize;
            g2.drawLine(0, y0, width, y0); // draw guideline below x axis
            g2.drawLine(0, y1, width, y1); // draw guideline above x axis
        }

        // draw vertical guidelines
        lineCount = width / (2 * cellSize);
        for (int i = 1; i <= lineCount; i++) {
            int x0 = width / 2 + i * cellSize; 
            int x1 = width / 2 - i * cellSize;
            g2.drawLine(x0, 0, x0, height);
            g2.drawLine(x1, 0, x1, height);
        }

    }

    private void drawVerticalAxis(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2)); // Set line thickness

        g2.drawLine(width / 2 + xCenter * cellSize, 0, width / 2 + xCenter * cellSize, height); // vertical axis

        // label ticks on positive vertical axis
        for (int i = 1; i <= width / (2 * cellSize); i++) {
            g2.drawString(Integer.valueOf(xCenter + i).toString(), xCenter + width / 2,
                    yCenter + height / 2 - i * cellSize);
        }

        // label ticks on negative vertical axis
        for (int i = 1; i <= width / (2 * cellSize); i++) {
            String label = Integer.valueOf(-i).toString();

            g2.drawString(label, xCenter + width / 2,
                    yCenter + height / 2 + i * cellSize);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        drawHorizontalAxis(g2);
        drawVerticalAxis(g2);
        drawGuidelines(g2);
    }
}
