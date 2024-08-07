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
import com.github.creme332.model.calculator.PolygonCalculator;
import com.github.creme332.view.Canvas;

public class DrawRegularPolygon extends AbstractDrawer {

    PolygonCalculator calculator = new PolygonCalculator();
    private int numSides = 5; // default to 5 sides (pentagon)
    private Point2D firstVertex = null;
    private Point2D secondVertex = null;

    public DrawRegularPolygon(AppState app, Canvas canvas) {
        super(app, canvas);
    }

    @Override
    protected void handleMouseMoved(Point2D polySpaceMousePosition) {
        // do nothing
    }

    @Override
    protected void handleMousePressed(Point2D polySpaceMousePosition) {
        if (firstVertex == null) {
            // user entered first vertex of polygon
            firstVertex = polySpaceMousePosition;

            // initialize shape preview
            preview = new ShapeWrapper(canvasModel.getShapeColor(),
                    canvasModel.getLineType(),
                    canvasModel.getLineThickness());

            // save first plotted point
            preview.getPlottedPoints().add(polySpaceMousePosition);

            // update preview on canvas
            canvasModel.getShapeManager().setShapePreview(preview);
            return;
        }

        if (secondVertex == null) {
            // user entered second vertex of polygon
            secondVertex = polySpaceMousePosition;

            // save second plotted point
            preview.getPlottedPoints().add(polySpaceMousePosition);

            // ask user to enter number of sides
            numSides = inputVertices();

            if (numSides < 3) {
                // invalid input => reset state and cancel operation
                disposePreview();
                return;
            }

            // generate new polygon
            Polygon polygon = calculator.getRegularPolygon(firstVertex, secondVertex, numSides);
            preview.setShape(polygon);

            // save shape
            canvasModel.getShapeManager().addShape(preview);

            canvas.repaint();

            // discard preview
            disposePreview();
        }
    }

    @Override
    protected boolean shouldDraw() {
        return getCanvasMode() == Mode.DRAW_REGULAR_POLYGON;
    }

    /**
     * Asks user to enter number of vertices for polygon. If input value is invalid
     * or if operation is cancelled, -1 is returned.
     * 
     * @return The number of vertices entered by the user or -1 if invalid or
     *         cancelled.
     */
    private int inputVertices() {
        JTextField numSidesField = new JTextField(5);
        JPanel panel = new JPanel();
        panel.add(new JLabel("Vertices:"));
        panel.add(numSidesField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Regular Polygon", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        // request focus again otherwise keyboard shortcuts will not work
        canvas.getTopLevelAncestor().requestFocus();

        if (result == JOptionPane.OK_OPTION) {
            try {
                int input = Integer.parseInt(numSidesField.getText());
                if (input < 3) {
                    throw new NumberFormatException("Number of sides must be at least 3.");
                }
                return input;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(canvas,
                        "Invalid input. Please enter an integer greater than or equal to 3 for the number of vertices.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return -1;
            }
        }
        return -1;
    }

    @Override
    public void disposePreview() {
        // delete any preview shape
        firstVertex = null;
        secondVertex = null;
        preview = null;
        canvasModel.getShapeManager().setShapePreview(preview);
    }
}
