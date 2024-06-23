package com.github.creme332.view;

import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Menu extends JMenu implements MenuListener, ChangeListener {
    private boolean mouseOver = false;
    private boolean selected = false;

    private static final Color BUTTON_COLOR = new Color(240, 240, 240); // Light gray button color
    private static final Color BORDER_COLOR = new Color(200, 200, 200); // Default border color
    private static final Color SELECTED_BORDER_COLOR = Color.BLUE; // Border color when selected
    private static final int BORDER_THICKNESS = 1; // Border thickness

    public Menu(String text) {
        super(text);
        setHorizontalAlignment(SwingConstants.CENTER); // Center align text
        init();
    }

    public Menu() {
        super();
        setHorizontalAlignment(SwingConstants.CENTER); // Center align text
        init();
    }

    private void init() {
        setOpaque(true); // Ensure component paints every pixel within its bounds
        setBackground(BUTTON_COLOR);
        setBorder(BorderFactory.createLineBorder(BORDER_COLOR, BORDER_THICKNESS)); // Add initial border
        setPreferredSize(new Dimension(150, 30)); // Adjust size as needed

        // Add menu listener to track selection changes
        addMenuListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Cast to Graphics2D for more advanced rendering options
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fill background based on mouse over state
        if (mouseOver) {
            g2.setColor(Color.LIGHT_GRAY); // Example hover color
        } else {
            g2.setColor(getBackground());
        }
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Cleanup resources
        g2.dispose();

        // Paint text and other content
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Paint border based on selected state
        Graphics2D g2 = (Graphics2D) g.create();
        if (selected) {
            g2.setColor(SELECTED_BORDER_COLOR);
        } else {
            g2.setColor(BORDER_COLOR);
        }
        g2.setStroke(new BasicStroke(BORDER_THICKNESS));
        g2.drawRect(0, 0, getWidth() - 1, getHeight() - 1); // Adjust for border thickness
        g2.dispose();
    }

    @Override
    public void menuSelected(MenuEvent e) {
        selected = true;
        repaint();
    }

    @Override
    public void menuDeselected(MenuEvent e) {
        selected = false;
        repaint();
    }

    @Override
    public void menuCanceled(MenuEvent e) {
        // No action needed
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        // Handle state changes in the model if needed
    }

    @Override
    public void setArmed(boolean b) {
        // Track mouse over state
        mouseOver = b;
        super.setArmed(b);
        repaint();
    }
}
