package com.github.creme332.view;

import javax.swing.JMenu;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

public class RoundedMenu extends JMenu implements MenuListener, ChangeListener {
    public RoundedMenu() {
        super();
        setHorizontalAlignment(SwingConstants.CENTER); // Center align text
        init();
    }

    private void init() {
        setOpaque(true); // Ensure component paints every pixel within its bounds
        setPreferredSize(new Dimension(60, 50)); // Adjust size as needed
        setBorder(new RoundedBorder());

        // Add menu listener to track selection changes
        addMenuListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Cast to Graphics2D for more advanced rendering options
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Paint the background with rounded corners
        Shape border = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20);
        g2.setColor(getBackground());
        g2.fill(border);

        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    public void menuSelected(MenuEvent e) {
        repaint();
    }

    @Override
    public void menuDeselected(MenuEvent e) {
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
        super.setArmed(b);
        repaint();
    }
}
