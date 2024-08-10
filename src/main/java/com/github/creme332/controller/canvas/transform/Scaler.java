package com.github.creme332.controller.canvas.transform;

import java.awt.geom.Point2D;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.github.creme332.model.AppState;
import com.github.creme332.model.Mode;
import com.github.creme332.model.ShapeWrapper;
import com.github.creme332.utils.RequestFocusListener;
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
        ShapeWrapper selectedShape = canvasModel.getShapeManager().getShapeByIndex(shapeIndex);

        // Request user for the scaling point and scaling factors
        double[] scaleData = requestScaleData();
        if (scaleData.length != 4) {
            return; // User cancelled the dialog
        }

        // Extract scaling point and scaling factors
        Point2D scalingPoint = new Point2D.Double(scaleData[0], scaleData[1]);
        double sx = scaleData[2];
        double sy = scaleData[3];

        // Scale the shape
        selectedShape.scale(scalingPoint, sx, sy);

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
     * Requests user to enter the coordinates of the scaling point and scaling
     * factors.
     * 
     * @return An array containing [x, y, sx, sy] or null if the input is invalid or
     *         cancelled.
     */
    private double[] requestScaleData() {
        JTextField xField = new JTextField("0", 5);
        JTextField yField = new JTextField("0", 5);
        JTextField sxField = new JTextField(5);
        JTextField syField = new JTextField(5);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Scale X"));
        panel.add(sxField);
        panel.add(new JLabel("Scale Y"));
        panel.add(syField);
        panel.add(new JLabel("X"));
        panel.add(xField);
        panel.add(new JLabel("Y"));
        panel.add(yField);

        // Request focus on the textfield when dialog is displayed
        sxField.addHierarchyListener(new RequestFocusListener());

        int result = JOptionPane.showConfirmDialog(canvas, panel, "Enter scaling data",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                double x = Double.parseDouble(xField.getText());
                double y = Double.parseDouble(yField.getText());
                double sx = Double.parseDouble(sxField.getText());
                double sy = Double.parseDouble(syField.getText());
                return new double[] { x, y, sx, sy };
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(canvas, "Invalid input! Please enter valid numbers.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return new double[] {};
            }
        }
        return new double[] {};
    }
}