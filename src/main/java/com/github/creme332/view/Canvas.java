package com.github.creme332.view;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.swing.FontIcon;

import com.github.creme332.model.CanvasModel;
import com.github.creme332.model.ShapeWrapper;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class Canvas extends JPanel {
    private JButton homeButton = new CircularButton();
    private JButton zoomInButton = new CircularButton();
    private JButton zoomOutButton = new CircularButton();
    private JButton toggleAxesButton = new CircularButton(); // New toggle button
    private Toolbar toolbar;

    private transient CanvasModel model;
    private Toast toast = new Toast();

    /**
     * Place zoom panel in bottom right corner of canvas.
     */
    public void positionZoomPanel() {
        final int MARGIN_RIGHT = 20;
        final int MARGIN_BOTTOM = 200;

        final int canvasWidth = getWidth();
        final int canvasHeight = getHeight();

        Dimension buttonSize = homeButton.getPreferredSize();
        int x = canvasWidth - buttonSize.width - MARGIN_RIGHT;
        int y = canvasHeight - buttonSize.height - MARGIN_BOTTOM;

        homeButton.setBounds(x, y, buttonSize.width, buttonSize.height);
        zoomInButton.setBounds(x, y + 60, buttonSize.width, buttonSize.height);
        zoomOutButton.setBounds(x, y + 120, buttonSize.width, buttonSize.height);
    }

    public void positionToast() {
        // position toast
        Dimension toastSize = toast.getPreferredSize();

        Rectangle r = new Rectangle();
        r.x = 30;
        r.y = (int) (this.getHeight() - toastSize.getHeight() - 30);

        r.width = (int) toastSize.getWidth();
        r.height = (int) toastSize.getHeight();

        toast.setBounds(r);
    }

    /**
     * Place toolbar at middle top of canvas
     */
    public void positionToolbar() {
        final int MARGIN_TOP = 25; // distance between toolbar and canvas top
        final int canvasWidth = getWidth();

        // position toolbar such that center of toolbar coincides with center of canvas
        Dimension toolbarSize = toolbar.getPreferredSize();
        Rectangle r = new Rectangle();
        r.x = canvasWidth / 2 - (int) (toolbarSize.getWidth() / 2);
        r.y = MARGIN_TOP;

        r.width = (int) toolbarSize.getWidth();
        r.height = (int) toolbarSize.getHeight();

        toolbar.setBounds(r);
    }

    public JButton createZoomPanelButton(Ikon ikon) {
        final int ICON_SIZE = 25;
        final Color gray = new Color(116, 116, 116);

        JButton btn = new CircularButton();
        btn.setPreferredSize(new Dimension(50, 50));
        FontIcon icon = FontIcon.of(ikon, ICON_SIZE);
        icon.setIconColor(gray);
        btn.setIcon(icon);
        return btn;
    }

    public Canvas(CanvasModel model, Toolbar toolbar) {
        setLayout(null); // Use no layout manager

        this.model = model;
        this.toolbar = toolbar;
        add(toolbar);
        add(toast);

        // create buttons for zoom panel
        homeButton = createZoomPanelButton(BootstrapIcons.HOUSE);
        zoomInButton = createZoomPanelButton(BootstrapIcons.ZOOM_IN);
        zoomOutButton = createZoomPanelButton(BootstrapIcons.ZOOM_OUT);
        toggleAxesButton = createZoomPanelButton(BootstrapIcons.EYE); // New button

        add(homeButton);
        add(zoomInButton);
        add(zoomOutButton);
        add(toggleAxesButton); // Add new button to the canvas

        // Add action listener to toggle button
        toggleAxesButton.addActionListener(e -> toggleAxesVisibility());
    }

    public void setAntialiasing(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    }

    private void drawHorizontalAxis(Graphics2D g2) {
        final int canvasWidth = getWidth();
        final int canvasHeight = getHeight();

        // calculate y position of tick label
        int labelYPos = Math.min(canvasHeight - CanvasModel.TICK_PADDING_BOTTOM,
                Math.max(CanvasModel.TICK_PADDING_TOP, model.getYZero()));

        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2)); // Set line thickness

        // if axis is within canvas, draw horizontal line to represent horizontal
        // axis
        if (model.getYZero() >= 0 && model.getYZero() <= canvasHeight)
            g2.drawLine(0, model.getYZero(), canvasWidth, model.getYZero());

        // set tick label color
        g2.setColor(Color.GRAY);

        // label center of x axis
        g2.drawString(Integer.toString(0), model.getXZero(), labelYPos);

        // label ticks on positive horizontal axis
        for (int i = 1; i <= (canvasWidth - model.getXZero()) / (model.getCellSize()); i++) {
            int labelX = model.getXZero() + i * model.getCellSize();
            g2.drawString(Integer.toString(i), labelX, labelYPos);
        }

        // label ticks on negative horizontal axis
        for (int i = -1; i >= -model.getXZero() / (model.getCellSize()); i--) {
            int labelX = model.getXZero() + i * model.getCellSize();
            g2.drawString(Integer.toString(i), labelX, labelYPos);
        }
    }

    private void drawGuidelines(Graphics2D g2) {
        final int canvasWidth = getWidth();
        final int canvasHeight = getHeight();

        g2.setColor(Color.gray);
        g2.setStroke(new BasicStroke(1));

        // draw horizontal guidelines above x-axis
        int lineCount = model.getYZero() / (model.getCellSize());
        for (int i = 1; i <= lineCount; i++) {
            int y1 = model.getYZero() - i * model.getCellSize();
            g2.drawLine(0, y1, canvasWidth, y1); // draw guideline above x axis
        }

        // draw horizontal guidelines below x-axis
        lineCount = (canvasHeight - model.getYZero()) / (model.getCellSize());
        for (int i = 1; i <= lineCount; i++) {
            int y0 = model.getYZero() + i * model.getCellSize();
            g2.drawLine(0, y0, canvasWidth, y0); // draw guideline below x axis
        }

        // draw vertical guidelines before y-axis
        lineCount = model.getXZero() / (model.getCellSize());
        for (int i = 1; i <= lineCount; i++) {
            int x0 = model.getXZero() - i * model.getCellSize();
            g2.drawLine(x0, 0, x0, canvasHeight); // line before y axis
        }

        // draw vertical guidelines after y-axis
        lineCount = (canvasWidth - model.getXZero()) / (model.getCellSize());
        for (int i = 1; i <= lineCount; i++) {
            int x1 = model.getXZero() + i * model.getCellSize();
            g2.drawLine(x1, 0, x1, canvasHeight); // line after y axis
        }

    }

    private void drawVerticalAxis(Graphics2D g2) {
        // TODO: move calculation to model
        final int canvasWidth = getWidth();
        final int canvasHeight = getHeight();

        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2)); // Set line thickness

        int labelYPos = Math.min(canvasWidth - CanvasModel.TICK_PADDING_RIGHT,
                Math.max(CanvasModel.TICK_PADDING_LEFT, model.getXZero()));

        if (model.getXZero() >= 0 || model.getXZero() <= canvasWidth)
            g2.drawLine(model.getXZero(), 0, model.getXZero(), canvasHeight); // vertical axis

        // set tick label color
        g2.setColor(Color.GRAY);

        // label center of vertical axis
        g2.drawString(Integer.toString(0), labelYPos, model.getYZero());

        // label ticks on positive vertical axis
        for (int i = 1; i <= model.getYZero() / (model.getCellSize()); i++) {
            int labelY = model.getYZero() - i * model.getCellSize();
            g2.drawString(Integer.toString(i), labelYPos,
                    labelY);
        }

        // label ticks on negative vertical axis
        for (int i = 1; i <= (canvasHeight - model.getYZero()) / (model.getCellSize()); i++) {
            int labelY = model.getYZero() + i * model.getCellSize();
            String label = Integer.toString(-i);

            g2.drawString(label, labelYPos, labelY);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * model.getLabelFontSizeSF());
        g.setFont(newFont);

        Graphics2D g2 = (Graphics2D) g;
        setAntialiasing(g2);

        if (model.isGuidelinesEnabled()) {
            drawGuidelines(g2d);
        }

        positionZoomPanel();
        positionToolbar();
        positionToast();

        if (model.isAxesVisible()) { // Check if axes should be drawn
            drawGuidelines(g2);
            drawHorizontalAxis(g2);
            drawVerticalAxis(g2);
        }

        for (ShapeWrapper wrapper : model.getShapes()) {
            g2.setColor(wrapper.getLineColor());

            if (wrapper.getShape() != null) {
                Shape s1 = model.toUserSpace(wrapper.getShape());
                g2.draw(s1);
            }

            // plot points
            g2.setColor(Color.BLUE);
            for (Point2D p : wrapper.getPlottedPoints()) {
                Shape point = createPointAsShape(model.toUserSpace(p));

                g2.draw(point);
                g2.fill(point);
            }

        }
    }

    /**
     * 
     * @param mySpaceCoord
     * @return A point with a fixed radius irrespective of zoom level
     */
    private Shape createPointAsShape(Point2D mySpaceCoord) {
        double radius = 15;
        return new Ellipse2D.Double(
                mySpaceCoord.getX() - radius / 2,
                mySpaceCoord.getY() - radius / 2,
                radius,
                radius);
    }

    private void toggleAxesVisibility() {
        model.setAxesVisible(!model.isAxesVisible());
        repaint();
    }

    public JButton getHomeButton() {
        return homeButton;
    }

    public JButton getZoomInButton() {
        return zoomInButton;
    }

    public JButton getZoomOutButton() {
        return zoomOutButton;
    }

    public JButton getToggleAxesButton() {
        return toggleAxesButton;
    }
}
