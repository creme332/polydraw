package com.github.creme332.controller.canvas.transform;

import java.awt.geom.Point2D;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.github.creme332.model.AppState;
import com.github.creme332.model.Mode;
import com.github.creme332.model.ShapeWrapper;
import com.github.creme332.view.Canvas;

public class Rotator extends AbstractTransformer {

    public Rotator(AppState app, Canvas canvas) {
        super(app, canvas);
    }

    @Override
    public void handleShapeSelection(int shapeIndex) {
        // Get copy of selected shape
        ShapeWrapper selectedWrapperCopy = canvasModel.getShapeManager().getShapeByIndex(shapeIndex);

        // Request rotation details from the user
        RotationDetails rotationDetails = requestRotationDetails();

        // Perform rotation
        double radAngle = Math.toRadians(rotationDetails.angle * (rotationDetails.isClockwise ? -1 : 1));
        selectedWrapperCopy.rotate(radAngle, rotationDetails.pivot);

        // Replace old shape with new one
        canvasModel.getShapeManager().editShape(shapeIndex, selectedWrapperCopy);

        // Repaint canvas
        canvas.repaint();
    }

    @Override
    public boolean shouldDraw() {
        return getCanvasMode() == Mode.ROTATE_ABOUT_POINT;
    }

    private RotationDetails requestRotationDetails() {
        JTextField angleField = new JTextField(5);
        JTextField pivotXField = new JTextField(5);
        JTextField pivotYField = new JTextField(5);

        JRadioButton clockwiseButton = new JRadioButton("clockwise");
        JRadioButton counterClockwiseButton = new JRadioButton("counterclockwise");
        counterClockwiseButton.setSelected(true);

        ButtonGroup directionGroup = new ButtonGroup();
        directionGroup.add(clockwiseButton);
        directionGroup.add(counterClockwiseButton);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Angle"));
        panel.add(angleField);
        panel.add(new JLabel("Pivot X"));
        panel.add(pivotXField);
        panel.add(new JLabel("Pivot Y"));
        panel.add(pivotYField);
        panel.add(counterClockwiseButton);
        panel.add(clockwiseButton);

        int result = JOptionPane.showConfirmDialog(canvas, panel, "Rotate About Point",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        // Request focus again otherwise keyboard shortcuts will not work
        canvas.getTopLevelAncestor().requestFocus();

        if (result == JOptionPane.OK_OPTION) {
            try {
                double angle = Double.parseDouble(angleField.getText());
                double pivotX = Double.parseDouble(pivotXField.getText());
                double pivotY = Double.parseDouble(pivotYField.getText());
                boolean isClockwise = clockwiseButton.isSelected();
                return new RotationDetails(angle, new Point2D.Double(pivotX, pivotY), isClockwise);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(canvas, "Invalid input! Please enter valid numbers.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                // Handle invalid input gracefully, return default rotation details
                return new RotationDetails(0, new Point2D.Double(0, 0), false);
            }
        }
        return new RotationDetails(0, new Point2D.Double(0, 0), false);
    }

    private static class RotationDetails {
        double angle;
        Point2D pivot;
        boolean isClockwise;

        RotationDetails(double angle, Point2D pivot, boolean isClockwise) {
            this.angle = angle;
            this.pivot = pivot;
            this.isClockwise = isClockwise;
        }
    }
}
