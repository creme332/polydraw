package com.github.creme332.controller.canvas.transform;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.github.creme332.model.AppState;
import com.github.creme332.model.Mode;
import com.github.creme332.model.ShapeWrapper;
import com.github.creme332.view.Canvas;

/**
 * Controller responsible for shear mode.
 */
public class Shearer extends AbstractTransformer {

    public Shearer(AppState app, Canvas canvas) {
        super(app, canvas);
    }

    @Override
    public void handleShapeSelection(int shapeIndex) {
        // A copy of the shape selected
        final ShapeWrapper selectedWrapperCopy = canvasModel.getShapeManager().getShapeByIndex(shapeIndex);

        // request user for shear factors
        final double[] shearFactors = requestShearFactors();

        // shear wrapper
        selectedWrapperCopy.shear(shearFactors);

        // replace old shape with new one
        canvasModel.getShapeManager().editShape(shapeIndex, selectedWrapperCopy);

        // repaint canvas
        canvas.repaint();
    }

    @Override
    public boolean shouldDraw() {
        return getCanvasMode() == Mode.SHEAR;
    }

    /**
     * Asks user to enter the shear factors. If input values are invalid
     * or if the operation is cancelled, null is returned.
     * 
     * @return array with shear factors [shx, shy]
     */
    private double[] requestShearFactors() {
        final double[] shearFactors = { 0, 0 };

        JTextField sxField = new JTextField(5);
        JTextField syField = new JTextField(5);
        JPanel panel = new JPanel();
        panel.add(new JLabel("Shear X:"));
        panel.add(sxField);
        panel.add(new JLabel("Shear Y:"));
        panel.add(syField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Enter shear factors",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        // Request focus again otherwise keyboard shortcuts will not work
        canvas.getTopLevelAncestor().requestFocus();

        if (result == JOptionPane.OK_OPTION) {
            try {
                double sx = Double.parseDouble(sxField.getText());
                double sy = Double.parseDouble(syField.getText());
                shearFactors[0] = sx;
                shearFactors[1] = sy;

                return shearFactors;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(canvas, "Invalid input! Please enter valid numbers.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return shearFactors;
            }
        }
        return shearFactors;
    }
}
