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
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class Canvas extends JPanel {
    private JButton homeButton = new CircularButton();
    private JButton zoomInButton = new CircularButton();
    private JButton zoomOutButton = new CircularButton();
    private Toolbar toolbar;

    private CanvasModel model;

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

    /**
     * Place toolbar at middle top of canvas
     */
    public void positionToolbar() {
        final int MARGIN_TOP = 25; // distance between toolbar and canvas top
        final int canvasWidth = getWidth();

        // position toolbar such that center of toolbar coincides with center of canvas
        Dimension buttonSize = toolbar.getPreferredSize();
        Rectangle r = new Rectangle();
        r.x = canvasWidth / 2 - (int) (buttonSize.getWidth() / 2);
        r.y = MARGIN_TOP;

        r.width = (int) buttonSize.getWidth();
        r.height = (int) buttonSize.getHeight();

        toolbar.setBounds(r);
    }

    public JButton createZoomPanelButton(Ikon ikon) {
        final int ICON_SIZE = 25;
        final Color ICON_COLOR = new Color(116, 116, 116);

        JButton btn = new CircularButton();
        btn.setPreferredSize(new Dimension(50, 50));
        FontIcon icon = FontIcon.of(ikon, ICON_SIZE);
        icon.setIconColor(ICON_COLOR);
        btn.setIcon(icon);
        return btn;
    }

    public Canvas(CanvasModel model, Toolbar toolbar) {
        setLayout(null); // Use no layout manager

        this.model = model;
        this.toolbar = toolbar;
        add(toolbar);

        // create buttons for zoom panel
        homeButton = createZoomPanelButton(BootstrapIcons.HOUSE);
        zoomInButton = createZoomPanelButton(BootstrapIcons.ZOOM_IN);
        zoomOutButton = createZoomPanelButton(BootstrapIcons.ZOOM_OUT);
        add(homeButton);
        add(zoomInButton);
        add(zoomOutButton);

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

    public void drawShapeExample(Graphics2D g2) {

        double x[] = {
                1, 1, 1, 1, -1, -1, -1, -1,
                0, 0, 0, 0,
                0.618, -0.618, 0.618, -0.618,
                1.618, 1.618, -1.618, -1.618
        };

        // y coordinates of vertices
        double y[] = {
                1, 1, -1, -1, 1, 1, -1, -1,
                1.618, 1.618, -1.618, -1.618,
                0, 0, 0, 0,
                0.618, -0.618, 0.618, -0.618
        };

        // number of vertices
        int numberofpoints = x.length;

        // Polygon originalPolygon = new Polygon(x, y, numberofpoints);

        Polygon transformedPolygon = new Polygon();
        for (int i = 0; i < numberofpoints; i++) {
            transformedPolygon.addPoint((int) (model.getXZero() + x[i] * model.getCellSize()),
                    (int) (model.getYZero() - y[i] * model.getCellSize()));
        }
        g2.drawPolygon(transformedPolygon);
        g2.setColor(Color.red);
        g2.fill(transformedPolygon);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * model.getLabelFontSizeSF());
        g.setFont(newFont);

        Graphics2D g2 = (Graphics2D) g;
        setAntialiasing(g2);
        drawGuidelines(g2);
        drawHorizontalAxis(g2);
        drawVerticalAxis(g2);

        for (ShapeWrapper s : model.getShapes()) {
            if (s.getPreview()) {
                g2.setColor(Color.gray);
            } else {
                g2.setColor(s.getLineColor());
            }

            if (s.getShape() != null) {
                Shape s1 = s.getTransformedShape(model.getTransform());
                g2.draw(s1);
            }

            // plot points
            for (Point2D p : s.getPlottedPoints()) {
                Shape point = createPointAsShape(toUserSpace(p));

                g2.draw(point);
                g2.fill(point);
            }

        }
    }

    private Point2D toUserSpace(Point2D mySpaceCoord) {
        double x = mySpaceCoord.getX() * model.getCellSize() + model.getXZero();
        double y = -mySpaceCoord.getY() * model.getCellSize() + model.getYZero();
        return new Point2D.Double(x, y);
    }

    private Shape createPointAsShape(Point2D mySpaceCoord) {
        double radius = 15;
        Ellipse2D ellipse = new Ellipse2D.Double(mySpaceCoord.getX() - radius / 2, mySpaceCoord.getY() - radius / 2,
                radius,
                radius);

        return ellipse;
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
}
