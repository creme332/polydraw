package com.github.creme332.controller.canvas.transform;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.Timer;

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

        // Calculate rotation angle in radians
        double radAngle = Math.toRadians(rotationDetails.angle * (rotationDetails.isClockwise ? -1 : 1));

        startRotationAnimation(selectedWrapperCopy, shapeIndex, radAngle, rotationDetails.pivot);
    }

    /**
     * Animates rotation of a given shape
     * 
     * @param selectedWrapperCopy Shape to be rotated
     * @param shapeIndex          ID of shape in canvas
     * @param radAngle            Rotation angle
     * @param pivot               Rotation pivot
     */
    public void startRotationAnimation(final ShapeWrapper selectedWrapperCopy, final int shapeIndex,
            final double radAngle,
            final Point2D pivot) {
        if (radAngle == 0)
            return;

        final int animationDelay = 10; // Delay in milliseconds between updates
        final double stepAngle = Math.toRadians(1.0) * Math.signum(radAngle); // Step size for each update (1 degree)
        final double totalSteps = Math.abs(radAngle) / Math.abs(stepAngle); // Total number of steps

        // Timer to handle the animation
        Timer timer = new Timer(animationDelay, new ActionListener() {
            private int stepCount = 0;
            ShapeWrapper copyPreview;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (stepCount < totalSteps) {
                    copyPreview = new ShapeWrapper(selectedWrapperCopy);
                    copyPreview.rotate(stepAngle * stepCount, pivot); // Rotate by the step angle
                    canvasModel.getShapeManager().setShapePreview(copyPreview);
                    canvas.repaint();

                    stepCount++;
                } else {
                    ((Timer) e.getSource()).stop(); // Stop the timer when done
                    canvasModel.getShapeManager().setShapePreview(null);
                    // Replace old shape with new one so that transformation can be undo-ed
                    canvasModel.getShapeManager().editShape(shapeIndex, copyPreview);
                    canvas.repaint();
                }
            }
        });

        timer.start();
    }

    @Override
    public boolean shouldDraw() {
        return getCanvasMode() == Mode.ROTATE_ABOUT_POINT;
    }

    private RotationDetails requestRotationDetails() {
        JTextField angleField = new JTextField(5);
        JTextField pivotXField = new JTextField("0", 5);
        JTextField pivotYField = new JTextField("0", 5);

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
