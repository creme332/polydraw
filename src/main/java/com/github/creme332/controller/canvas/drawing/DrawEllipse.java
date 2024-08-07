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
    protected void handleMouseMoved(Point2D polySpaceMousePosition) {
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
    protected void handleMousePressed(Point2D polySpaceMousePosition) {
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

                // Ask user for the radii
                int[] radii = inputRadii();

                if (radii == null || radii[0] <= 0 || radii[1] <= 0) {
                    disposePreview();
                    return;
                }

                final Point2D firstFocus = preview.getPlottedPoints().get(0);
                final Point2D secondFocus = preview.getPlottedPoints().get(1);
                int[][] coordinates = ellipseCalculator.getOrderedPointsWithRadius(firstFocus, secondFocus, radii[0],
                        radii[1]);

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
    protected boolean shouldDraw() {
        return getCanvasMode() == Mode.DRAW_ELLIPSE || getCanvasMode() == Mode.DRAW_ELLIPSE_FIXED;
    }

    /**
     * Asks user to enter the radii for the ellipse. If input values are invalid
     * or if the operation is cancelled, null is returned.
     * 
     * @return array with radii [rx, ry]
     */
    private int[] inputRadii() {
        JTextField rxField = new JTextField(5);
        JTextField ryField = new JTextField(5);
        JPanel panel = new JPanel();
        panel.add(new JLabel("Radius X:"));
        panel.add(rxField);
        panel.add(new JLabel("Radius Y:"));
        panel.add(ryField);

        int result = JOptionPane.showConfirmDialog(canvas, panel, "Ellipse: Foci & Radius", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        // Request focus again otherwise keyboard shortcuts will not work
        canvas.getTopLevelAncestor().requestFocus();

        if (result == JOptionPane.OK_OPTION) {
            try {
                int rx = Integer.parseInt(rxField.getText());
                int ry = Integer.parseInt(ryField.getText());
                if (rx <= 0 || ry <= 0) {
                    throw new NumberFormatException("Radii must be positive.");
                }
                return new int[] { rx, ry };
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(canvas, "Invalid input. Please enter positive integers for the radii.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }
        return null;
    }
}
