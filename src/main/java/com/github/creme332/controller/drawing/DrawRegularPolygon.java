package com.github.creme332.controller.drawing;

import java.awt.Polygon;
import java.awt.geom.Point2D;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import com.github.creme332.algorithms.PolygonCalculator;
import com.github.creme332.model.AppState;
import com.github.creme332.model.Mode;
import com.github.creme332.model.ShapeWrapper;
import com.github.creme332.view.Canvas;

public class DrawRegularPolygon extends DrawController {

    PolygonCalculator calculator = new PolygonCalculator();
    private int numSides = 5; // default to 5 sides (pentagon)

    public DrawRegularPolygon(AppState app, Canvas canvas) {
        super(app, canvas);
    }

    @Override
    public void handleMouseMoved(Point2D polySpaceMousePosition) {
        if (preview != null) {
            // calculate side length of polygon
            Point2D center = preview.getPlottedPoints().get(0);
            int side = (int) Math.abs(center.distance(polySpaceMousePosition));

            int[][] res = calculator.getOrderedPoints(numSides, side, (int) center.getX(), (int) center.getY());
            Polygon p = new Polygon(res[0], res[1], res[0].length);
            preview.setShape(p);
            canvas.repaint();
        }
    }

    @Override
    public void handleMousePressed(Point2D polySpaceMousePosition) {
        if (preview == null) {
            // center of polygon has been input

            // initialize shape preview
            preview = new ShapeWrapper(canvasModel.getFillColor(), canvasModel.getFillColor(),
                    canvasModel.getLineType(),
                    canvasModel.getLineThickness());
            preview.getPlottedPoints().add(polySpaceMousePosition);

            // add preview to model
            canvasModel.getShapes().add(preview);

            // ask user to enter number of sides
            numSides = inputVertices();

            if (numSides < 3) {
                // invalid input => cancel operation
                disposePreview();
            }
            return;
        }

        // second time clicked
        preview.getPlottedPoints().add(polySpaceMousePosition);
        preview = null;
    }

    @Override
    public boolean shouldDraw() {
        return getCanvasMode() == Mode.DRAW_REGULAR_POLYGON;
    }

    /**
     * Asks user to enter number of vertices for polygon. If input value is invalid
     * or if operation is cancelled, -1 is returned.
     * 
     * @return
     */
    private int inputVertices() {
        JTextField numSidesField = new JTextField(5);
        JPanel panel = new JPanel();
        panel.add(new JLabel("Vertices:"));
        panel.add(numSidesField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Regular Polygon", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                return Integer.parseInt(numSidesField.getText());
            } catch (NumberFormatException e) {
                return -1;
            }
        }
        return -1;
    }
}
