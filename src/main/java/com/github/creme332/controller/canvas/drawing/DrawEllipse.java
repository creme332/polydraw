package com.github.creme332.controller.canvas.drawing;

import java.awt.Polygon;
import java.awt.geom.Point2D;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.github.creme332.model.AppState;
import com.github.creme332.model.Mode;
import com.github.creme332.model.ShapeWrapper;
import com.github.creme332.model.calculator.EllipseCalculator;
import com.github.creme332.view.Canvas;

public class DrawEllipse extends AbstractDrawer {
    private EllipseCalculator ellipseCalculator = new EllipseCalculator();

    public DrawEllipse(AppState app, Canvas canvas) {
        super(app, canvas);
    }

    @Override
    public void handleMouseMoved(Point2D polySpaceMousePosition) {
        if (getCanvasMode() == Mode.DRAW_ELLIPSE && preview != null
                && preview.getPlottedPoints().size() == 2) {

            final Point2D firstFocus = preview.getPlottedPoints().get(0);
            final Point2D secondFocus = preview.getPlottedPoints().get(1);

            int[][] coordinates = ellipseCalculator.getOrderedPoints(firstFocus, secondFocus, polySpaceMousePosition);
            if (coordinates.length == 2) {
                Polygon ellipse = new Polygon(coordinates[0], coordinates[1], coordinates[0].length);
                preview.setShape(ellipse);
                canvas.repaint();
            }
        }
    }

    @Override
    public void handleMousePressed(Point2D polySpaceMousePosition) {
        if (getCanvasMode() == Mode.DRAW_ELLIPSE) {
            if (preview == null) {
                // First focus has been selected

                // Create a shape wrapper
                preview = new ShapeWrapper(canvasModel.getShapeColor(),
                        canvasModel.getLineType(),
                        canvasModel.getLineThickness());
                preview.getPlottedPoints().add(polySpaceMousePosition);

                // Save preview
                canvasModel.getShapeManager().setShapePreview(preview);
                return;
            }

            if (preview.getPlottedPoints().size() == 1) {
                // Second focus has now been selected
                preview.getPlottedPoints().add(polySpaceMousePosition);
            } else {
                // Third point has been selected
                preview.getPlottedPoints().add(polySpaceMousePosition);

                // Save preview as an actual shape
                canvasModel.getShapeManager().addShape(preview);

                disposePreview();
            }
        } else if (getCanvasMode() == Mode.DRAW_ELLIPSE_FIXED) {
            if (preview == null) {
                // First focus has been selected

                // Create preview and plot the first focus
                preview = new ShapeWrapper(canvasModel.getShapeColor(),
                        canvasModel.getLineType(),
                        canvasModel.getLineThickness());
                preview.getPlottedPoints().add(polySpaceMousePosition);

                // Save preview
                canvasModel.getShapeManager().setShapePreview(preview);
                return;
            }

            if (preview.getPlottedPoints().size() == 1) {
                // Second focus has been selected
                preview.getPlottedPoints().add(polySpaceMousePosition);

                // Ask user for the radius
                int radius = inputRadius();

                if (radius <= 0) {
                    disposePreview();
                    return;
                }

                final Point2D firstFocus = preview.getPlottedPoints().get(0);
                final Point2D secondFocus = preview.getPlottedPoints().get(1);
                int[][] coordinates = ellipseCalculator.getOrderedPointsWithRadius(firstFocus, secondFocus, radius);

                if (coordinates.length == 2) {
                    Polygon ellipse = new Polygon(coordinates[0], coordinates[1], coordinates[0].length);
                    preview.setShape(ellipse);

                    // Save preview as an actual shape
                    canvasModel.getShapeManager().addShape(preview);

                    disposePreview();
                }
            }
        }
    }

    @Override
    public boolean shouldDraw() {
        return getCanvasMode() == Mode.DRAW_ELLIPSE || getCanvasMode() == Mode.DRAW_ELLIPSE_FIXED;
    }

    /**
     * Asks user to enter the radius for the ellipse. If input value is invalid
     * or if the operation is cancelled, -1 is returned.
     * 
     * @return radius
     */
    private int inputRadius() {
        JTextField radiusField = new JTextField(5);
        JPanel panel = new JPanel();
        panel.add(new JLabel("Radius:"));
        panel.add(radiusField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Ellipse: Foci & Radius", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        // Request focus again otherwise keyboard shortcuts will not work
        canvas.getTopLevelAncestor().requestFocus();

        if (result == JOptionPane.OK_OPTION) {
            try {
                return Integer.parseInt(radiusField.getText());
            } catch (NumberFormatException e) {
                return -1;
            }
        }
        return -1;
    }
}
