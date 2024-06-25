package com.github.creme332.controller.drawing;

import java.awt.Polygon;
import java.awt.geom.Point2D;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import com.github.creme332.algorithms.CircleCalculator;
import com.github.creme332.model.AppState;
import com.github.creme332.model.Mode;
import com.github.creme332.model.ShapeWrapper;
import com.github.creme332.view.Canvas;

public class DrawCircle extends DrawController {
    private CircleCalculator circleCalculator = new CircleCalculator();

    public DrawCircle(AppState app, Canvas canvas) {
        super(app, canvas);
    }

    @Override
    public void handleMouseMoved(Point2D polySpaceMousePosition) {
        if (getCanvasMode() == Mode.DRAW_CIRCLE_DYNAMIC && preview != null
                && preview.getPlottedPoints().size() == 1) {

            Point2D center = preview.getPlottedPoints().get(0);
            double radius = polySpaceMousePosition.distance(center);
            int roundedRadius = (int) Math.round(radius);
            if (roundedRadius == 0)
                return;

            preview.setShape(getCircle((int) center.getX(), (int) center.getY(), roundedRadius));
            canvas.repaint();
        }

    }

    private Polygon getCircle(int x, int y, int radius) {
        int[][] coordinates = circleCalculator.getOrderedPoints(x, y, radius);
        return new Polygon(coordinates[0], coordinates[1], coordinates[0].length);
    }

    @Override
    public void handleMousePressed(Point2D polySpaceMousePosition) {
        if (getCanvasMode() == Mode.DRAW_CIRCLE_FIXED) {
            // center has been selected

            // create preview and plot center
            preview = new ShapeWrapper(canvasModel.getFillColor(), canvasModel.getFillColor(),
                    canvasModel.getLineType(),
                    canvasModel.getLineThickness());
            preview.getPlottedPoints().add(polySpaceMousePosition);

            // Ask user for radius
            int radius = inputRadius();

            if (radius <= 0) {
                disposePreview();
                return;
            }

            preview.setShape(
                    getCircle((int) polySpaceMousePosition.getX(), (int) polySpaceMousePosition.getY(), radius));

            // save wrapper
            canvasModel.getShapes().add(preview);

            preview = null;
            return;
        }

        if (getCanvasMode() == Mode.DRAW_CIRCLE_DYNAMIC) {
            if (preview == null) {
                // center has been selected

                // create a shape wrapper
                preview = new ShapeWrapper(canvasModel.getFillColor(), canvasModel.getFillColor(),
                        canvasModel.getLineType(),
                        canvasModel.getLineThickness());
                preview.getPlottedPoints().add(polySpaceMousePosition);

                // save wrapper
                canvasModel.getShapes().add(preview);

            } else {
                // second point has now been selected
                preview.getPlottedPoints().add(polySpaceMousePosition);

                preview = null;
            }
        }
    }

    @Override
    public boolean shouldDraw() {
        return getCanvasMode() == Mode.DRAW_CIRCLE_DYNAMIC || getCanvasMode() == Mode.DRAW_CIRCLE_FIXED;
    }

    /**
     * Asks user to enter the radius for the circle. If input value is invalid
     * or if the operation is cancelled, -1 is returned.
     * 
     * @return radius
     */
    private int inputRadius() {
        JTextField radiusField = new JTextField(5);
        JPanel panel = new JPanel();
        panel.add(new JLabel("Radius:"));
        panel.add(radiusField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Circle: Center & Radius", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        // request focus again otherwise keyboard shortcuts will not work
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
