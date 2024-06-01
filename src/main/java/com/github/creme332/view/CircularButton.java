package com.github.creme332.view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;

public class CircularButton extends JButton {
    public CircularButton(String label) {
        super(label);
        setContentAreaFilled(false);
        // setFocusPainted(false);
        setBorderPainted(false);
    }

    public CircularButton() {
        super("");
        setContentAreaFilled(false);
        // setFocusPainted(false);
        setBorderPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(Color.LIGHT_GRAY);
        } else {
            g.setColor(getBackground());
        }
        g.fillOval(0, 0, getSize().width, getSize().height);
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(new Color(228, 228, 228));
        g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
    }

    @Override
    public boolean contains(int x, int y) {
        int radius = getWidth() / 2;
        return (Math.pow(x - radius, 2) + Math.pow(y - radius, 2)) <= Math.pow(radius, 2);
    }
}
