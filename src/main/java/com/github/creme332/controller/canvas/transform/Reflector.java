package com.github.creme332.controller.canvas.transform;

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
 * Controller responsible for reflection mode.
 */
public class Reflector extends AbstractTransformer {

    public Reflector(AppState app, Canvas canvas) {
        super(app, canvas);
    }

    @Override
    public void handleShapeSelection(int shapeIndex) {
        // A copy of the shape selected
        final ShapeWrapper selectedWrapperCopy = canvasModel.getShapeManager().getShapeByIndex(shapeIndex);

        // Request user for line of reflection (gradient and y-intercept)
        final double[] data = requestReflectionLine();

        if (data.length != 2) {
            return; // Cancel operation if user input is invalid
        }

        double gradient = data[0];
        double yIntercept = data[1];

        // Reflect the shape using the gradient and y-intercept
        selectedWrapperCopy.reflect(gradient, yIntercept);

        // Replace old shape with the new one
        canvasModel.getShapeManager().editShape(shapeIndex, selectedWrapperCopy);

        // Repaint canvas
        canvas.repaint();
    }

    @Override
    public boolean shouldDraw() {
        return getCanvasMode() == Mode.REFLECT_ABOUT_LINE;
    }

    /**
     * Asks user to enter the gradient and y-intercept of the line of reflection.
     * If input values are invalid or if the operation is canceled, null is
     * returned.
     * 
     * @return array with gradient and y-intercept [m, b]
     */
    private double[] requestReflectionLine() {
        JTextField gradientField = new JTextField(5);
        JTextField yInterceptField = new JTextField(5);
        JPanel panel = new JPanel();
        panel.add(new JLabel("Gradient"));
        panel.add(gradientField);
        panel.add(new JLabel("Y-Intercept"));
        panel.add(yInterceptField);

        // Request focus on the textfield when dialog is displayed
        gradientField.addHierarchyListener(new RequestFocusListener());

        int result = JOptionPane.showConfirmDialog(canvas, panel, "Enter Line of Reflection",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        // Request focus again otherwise keyboard shortcuts will not work
        canvas.getTopLevelAncestor().requestFocus();

        if (result == JOptionPane.OK_OPTION) {
            try {
                double m = Double.parseDouble(gradientField.getText());
                double b = Double.parseDouble(yInterceptField.getText());
                return new double[] { m, b };
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter numeric values.");
            }
        }
        return new double[] {};
    }
}
