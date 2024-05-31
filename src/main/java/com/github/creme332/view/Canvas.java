package com.github.creme332.view;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.swing.FontIcon;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

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


    int cellSize = 100; // distance in pixels between each unit on axes

    private int yZero; // vertical distance between top border of canvas and my cartesian origin
    private int xZero; // horizontal distance between left border of canvas and my cartesian origin

    private JButton homeButton = new CircularButton();
    private JButton zoomInButton = new CircularButton();
    private JButton zoomOutButton = new CircularButton();
    private Toolbar toolbar;

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

    public Canvas(Toolbar toolbar) {
        setLayout(null); // Use no layout manager

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

    public static void setAntialiasing(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    }

    private void drawHorizontalAxis(Graphics2D g2) {
        final int canvasWidth = getWidth();
        final int canvasHeight = getHeight();

        // calculate y position of tick label
        int labelYPos = Math.min(canvasHeight - TICK_PADDING_BOTTOM, Math.max(TICK_PADDING_TOP, yZero));

        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2)); // Set line thickness

        // if axis is within canvas, draw horizontal line to represent horizontal
        // axis
        if (yZero >= 0 && yZero <= canvasHeight)
            g2.drawLine(0, yZero, canvasWidth, yZero);

        // set tick label color
        g2.setColor(Color.GRAY);

        // label center of x axis
        g2.drawString(Integer.valueOf(0).toString(), xZero, labelYPos);

        // label ticks on positive horizontal axis
        for (int i = 1; i <= (canvasWidth - xZero) / (cellSize); i++) {
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
        final int canvasWidth = getWidth();
        final int canvasHeight = getHeight();

        g2.setColor(Color.gray);
        g2.setStroke(new BasicStroke(1));

        // draw horizontal guidelines above x-axis
        int lineCount = yZero / (cellSize);
        for (int i = 1; i <= lineCount; i++) {
            int y1 = yZero - i * cellSize;
            g2.drawLine(0, y1, canvasWidth, y1); // draw guideline above x axis
        }

        // draw horizontal guidelines below x-axis
        lineCount = (canvasHeight - yZero) / (cellSize);
        for (int i = 1; i <= lineCount; i++) {
            int y0 = yZero + i * cellSize;
            g2.drawLine(0, y0, canvasWidth, y0); // draw guideline below x axis
        }

        // draw vertical guidelines before y-axis
        lineCount = xZero / (cellSize);
        for (int i = 1; i <= lineCount; i++) {
            int x0 = xZero - i * cellSize;
            g2.drawLine(x0, 0, x0, canvasHeight); // line before y axis
        }

        // draw vertical guidelines after y-axis
        lineCount = (canvasWidth - xZero) / (cellSize);
        for (int i = 1; i <= lineCount; i++) {
            int x1 = xZero + i * cellSize;
            g2.drawLine(x1, 0, x1, canvasHeight); // line after y axis
        }

    }

    private void drawVerticalAxis(Graphics2D g2) {
        final int canvasWidth = getWidth();
        final int canvasHeight = getHeight();

        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2)); // Set line thickness

        int labelYPos = Math.min(canvasWidth - TICK_PADDING_RIGHT, Math.max(TICK_PADDING_LEFT, xZero));

        if (xZero >= 0 || xZero <= canvasWidth)
            g2.drawLine(xZero, 0, xZero, canvasHeight); // vertical axis

        // set tick label color
        g2.setColor(Color.GRAY);

        // label center of vertical axis
        g2.drawString(Integer.valueOf(0).toString(), labelYPos, yZero);

        // label ticks on positive vertical axis
        for (int i = 1; i <= yZero / (cellSize); i++) {
            int labelY = yZero - i * cellSize;
            g2.drawString(Integer.valueOf(i).toString(), labelYPos,
                    labelY);
        }

        // label ticks on negative vertical axis
        for (int i = 1; i <= (canvasHeight - yZero) / (cellSize); i++) {
            int labelY = yZero + i * cellSize;
            String label = Integer.valueOf(-i).toString();

            g2.drawString(label, labelYPos, labelY);
        }
    }

    public int getCellSize() {
        return cellSize;
    }

    public void setCellSize(int newCellSize) {
        cellSize = newCellSize;
    }

    public int getXZero() {
        return xZero;
    }

    public int getYZero() {
        return yZero;
    }

    public void setXZero(int newXZero) {
        xZero = newXZero;
    }

    public void setYZero(int newYZero) {
        yZero = newYZero;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 1.4F);
        g.setFont(newFont);

        Graphics2D g2 = (Graphics2D) g;
        drawGuidelines(g2);
        drawHorizontalAxis(g2);
        drawVerticalAxis(g2);
    }
}
