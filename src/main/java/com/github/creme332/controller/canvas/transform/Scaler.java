package com.github.creme332.controller.canvas.transform;

import java.awt.geom.Point2D;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.github.creme332.model.AppState;
import com.github.creme332.model.Mode;
import com.github.creme332.model.ShapeWrapper;
import com.github.creme332.view.Canvas;

/**
 * Controller responsible for scaling mode.
 */
public class Scaler extends AbstractTransformer {

    public Scaler(AppState app, Canvas canvas) {
        super(app, canvas);
    }

    @Override
    public void handleShapeSelection(int shapeIndex) {
        // Get the selected shape
        ShapeWrapper selectedShape = canvasModel.getShapeManager().getShapes().get(shapeIndex);

        // Request user for the scaling point and scaling factors
        Point2D scalingPoint = requestScalingPoint();
        double[] scalingFactors = requestScalingFactors();

        // Scale the shape
        selectedShape.scale(scalingPoint, scalingFactors[0], scalingFactors[1]);

        // Replace old shape with new one
        canvasModel.getShapeManager().editShape(shapeIndex, selectedShape);

        // Repaint canvas
        canvas.repaint();
    }

    @Override
    public boolean shouldDraw() {
        return getCanvasMode() == Mode.SCALING;
    }

    /**
     * Requests user to enter the coordinates of the scaling point.
     * 
     * @return The scaling point.
     */
    private Point2D requestScalingPoint() {
        JTextField xField = new JTextField(5);
        JTextField yField = new JTextField(5);
        JPanel panel = new JPanel();
        panel.add(new JLabel("X:"));
        panel.add(xField);
        panel.add(new JLabel("Y:"));
        panel.add(yField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Enter coordinates of scaling point",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                double x = Double.parseDouble(xField.getText());
                double y = Double.parseDouble(yField.getText());
                return new Point2D.Double(x, y);
            } catch (NumberFormatException e) {
                return new Point2D.Double(0, 0); // Default value if input is invalid
            }
        }
        return new Point2D.Double(0, 0); // Default value if canceled
    }

    /**
     * Requests user to enter the scaling factors.
     * 
     * @return The scaling factors [sx, sy].
     */
    private double[] requestScalingFactors() {
        JTextField sxField = new JTextField(5);
        JTextField syField = new JTextField(5);
        JPanel panel = new JPanel();
        panel.add(new JLabel("Scale X:"));
        panel.add(sxField);
        panel.add(new JLabel("Scale Y:"));
        panel.add(syField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Enter scaling factors",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                double sx = Double.parseDouble(sxField.getText());
                double sy = Double.parseDouble(syField.getText());
                return new double[] { sx, sy };
            } catch (NumberFormatException e) {
                return new double[] { 1.0, 1.0 }; // Default scaling factors if input is invalid
            }
        }
        return new double[] { 1.0, 1.0 }; // Default scaling factors if canceled
    }
}
