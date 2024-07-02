package com.github.creme332.controller;

import java.awt.event.KeyEvent;
import javax.swing.KeyStroke;
import javax.swing.JComponent;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;

import com.github.creme332.controller.drawing.DrawCircle;
import com.github.creme332.controller.drawing.DrawController;
import com.github.creme332.controller.drawing.DrawEllipse;
import com.github.creme332.controller.drawing.DrawLine;
import com.github.creme332.controller.drawing.DrawRegularPolygon;
import com.github.creme332.model.AppState;
import com.github.creme332.model.CanvasModel;
import com.github.creme332.model.Mode;
import com.github.creme332.model.ShapeManager;
import com.github.creme332.model.ShapeWrapper;
import com.github.creme332.view.Canvas;

public class CanvasController implements PropertyChangeListener {
    private Canvas canvas;

    /**
     * Used to store coordinate where mouse drag started
     */
    private Point mouseDragStart;
    private AppState app;
    private CanvasModel model;

    private List<DrawController> drawControllers = new ArrayList<>();

    public CanvasController(AppState app, Canvas canvas) {
        this.app = app;
        this.canvas = canvas;
        this.model = app.getCanvasModel();

        // listen to model
        model.addPropertyChangeListener(this);
        model.getShapeManager().addPropertyChangeListener(this);
        app.addPropertyChangeListener(this);

        // initialize drawing controllers
        drawControllers.add(new DrawLine(app, canvas));
        drawControllers.add(new DrawCircle(app, canvas));
        drawControllers.add(new DrawEllipse(app, canvas));
        drawControllers.add(new DrawRegularPolygon(app, canvas));

        canvas.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                model.setCanvasDimension(new Dimension(canvas.getWidth(), canvas.getHeight()));
                model.toStandardView();
            }
        });

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleMousePressed(e);
            }
        });

        canvas.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Point2D polySpaceMousePosition = model.toPolySpace(e.getPoint());
                model.setUserMousePosition(polySpaceMousePosition);
                canvas.repaint();
            }
        });

        canvas.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                handleMouseDragged(e);
            }
        });

        canvas.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                handleCanvasZoom(e);
            }
        });

        initializeKeyBindings();
    }

    private void initializeKeyBindings() {
        // Export canvas
        canvas.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK), "exportCanvas");
        canvas.getActionMap().put("exportCanvas", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCanvasExport();
            }
        });

        // Zoom in
        canvas.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, KeyEvent.CTRL_DOWN_MASK), "zoomIn");
        canvas.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ADD, KeyEvent.CTRL_DOWN_MASK), "zoomIn");
        canvas.getActionMap().put("zoomIn", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.updateCanvasZoom(true);
                canvas.repaint();
            }
        });

        // Zoom out
        canvas.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, KeyEvent.CTRL_DOWN_MASK), "zoomOut");
        canvas.getActionMap().put("zoomOut", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.updateCanvasZoom(false);
                canvas.repaint();
            }
        });

        // Zoom 100%
        canvas.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_0, KeyEvent.CTRL_DOWN_MASK), "zoomReset");
        canvas.getActionMap().put("zoomReset", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.toStandardView();
                canvas.repaint();
            }
        });
    }

    private void handleCanvasZoom(MouseWheelEvent e) {
        model.updateCanvasZoom(e.getWheelRotation() != 1);
        canvas.repaint();
    }

    private void handleMouseDragged(MouseEvent e) {
        if (mouseDragStart == null) {
            mouseDragStart = e.getPoint();
            return;
        }

        if (app.getMode() == Mode.MOVE_GRAPHICS_VIEW || app.getMode() == Mode.MOVE_CANVAS) {
            Point currentDrag = e.getPoint();
            int deltaX = currentDrag.x - mouseDragStart.x;
            int deltaY = currentDrag.y - mouseDragStart.y;

            model.setYZero(model.getYZero() + deltaY);
            model.setXZero(model.getXZero() + deltaX);

            mouseDragStart = currentDrag;

            canvas.repaint();
        }
    }

    private void handleMousePressed(MouseEvent e) {
        if (app.getMode() == Mode.MOVE_GRAPHICS_VIEW || app.getMode() == Mode.MOVE_CANVAS) {
            mouseDragStart = e.getPoint();
            return;
        }

        Point2D polyspaceMousePosition = model.toPolySpace(e.getPoint());

        if (app.getMode() == Mode.DELETE) {
            List<ShapeWrapper> shapes = model.getShapeManager().getShapes();
            for (int i = 0; i < shapes.size(); i++) {
                ShapeWrapper wrapper = shapes.get(i);
                if (wrapper.getShape().contains(polyspaceMousePosition)) {
                    model.getShapeManager().deleteShape(i);
                    break; // delete only one shape at a time
                }
            }
        }

        canvas.repaint();
    }

    /**
     * Exports canvas to image.
     * <br>
     * <ol>
     * <li>https://stackoverflow.com/a/14369955/17627866</li>
     * <li>
     * https://stackoverflow.com/questions/17690275/exporting-a-jpanel-to-an-image
     * </li>
     * </ol>
     */
    private void handleCanvasExport() {
        // temporarily hide cursor position
        Point2D cursorPosition = model.getUserMousePosition();
        model.setUserMousePosition(null);
        canvas.repaint();

        // get canvas as a buffered image
        BufferedImage image = canvas.toImage();

        // display cursor again
        model.setUserMousePosition(cursorPosition);
        canvas.repaint();

        // let user choose file location
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose folder to save image");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false); // disable the "All files" option.

        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String imagePath = fileChooser.getSelectedFile() + "/canvas.png";
            try {
                ImageIO.write(image, "png", new File(imagePath));
                JOptionPane.showMessageDialog(canvas, "Image successfully saved at " + imagePath);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
        canvas.getTopLevelAncestor().requestFocus();
    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        final String propertyName = e.getPropertyName();
        /**
         * List of property names that should result only in a canvas repaint.
         */
        final Set<String> repaintProperties = Set.of(ShapeManager.STATE_CHANGE_PROPERTY_NAME, "standardView",
                "enableGuidelines",
                "cellSize", "axesVisible", "labelFontSize");

        if (repaintProperties.contains(propertyName)) {
            canvas.repaint();
            return;
        }

        // if mode from AppState has changed
        if ("mode".equals(propertyName)) {
            for (DrawController controller : drawControllers) {
                controller.disposePreview();
            }
            // update canvas to erase any possible incomplete shape
            canvas.repaint();
            return;
        }

        // if printingCanvas property has changed to true, handle export
        if ("printingCanvas".equals(propertyName) && (boolean) e.getNewValue()) {
            handleCanvasExport();
        }
    }
}
