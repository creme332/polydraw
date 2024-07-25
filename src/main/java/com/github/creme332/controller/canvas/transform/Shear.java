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
 * Controller responsible for shear mode.
 */
public class Shear extends AbstractTransformer {

    public Shear(AppState app, Canvas canvas) {
        super(app, canvas);
    }

    @Override
    public void handleShapeSelection(int shapeIndex) {
        // A copy of the shape selected
        final ShapeWrapper selectedWrapperCopy = canvasModel.getShapeManager().getShapes().get(shapeIndex);

        // request user for shear factors
        final Point2D shearFactors = requestShearFactors();

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
    private Point2D requestShearFactors() {
        final Point2D zeroFactors = new Point2D.Double(0, 0);

        JTextField shxField = new JTextField(5);
        JTextField shyField = new JTextField(5);
        JPanel panel = new JPanel();
        panel.add(new JLabel("Shear X:"));
        panel.add(shxField);
        panel.add(new JLabel("Shear Y:"));
        panel.add(shyField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Enter shear factors",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        // Request focus again otherwise keyboard shortcuts will not work
        canvas.getTopLevelAncestor().requestFocus();

        if (result == JOptionPane.OK_OPTION) {
            try {
                return new Point2D.Double(Double.parseDouble(shxField.getText()), Double.parseDouble(shyField.getText()));
            } catch (NumberFormatException e) {
                return zeroFactors;
            }
        }
        return zeroFactors;
    }
}
