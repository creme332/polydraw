package com.github.creme332.view;

import java.awt.Shape;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import javax.swing.border.AbstractBorder;

public class RoundedBorder extends AbstractBorder {
    private static final long serialVersionUID = 1L;

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the rounded border
        Shape border = new RoundRectangle2D.Double(x, y, width - 1, height - 1, 20, 20);
        g2.setColor(new Color(200, 200, 200));
        g2.draw(border);

        g2.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(5, 10, 5, 10);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.set(5, 10, 5, 10);
        return insets;
    }
}
