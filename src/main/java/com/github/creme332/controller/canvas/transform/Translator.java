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
 * Controller responsible for translation mode.
 */
public class Translator extends AbstractTransformer {

    public Translator(AppState app, Canvas canvas) {
        super(app, canvas);
    }

    @Override
    public void handleShapeSelection(int shapeIndex) {
        /**
         * A copy of the shape selected
         */
        final ShapeWrapper selectedWrapperCopy = canvasModel.getShapeManager().getShapeByIndex(shapeIndex);

        // request user for translation vector
        final Point2D translationVector = requestTranslationVector();

        // translate wrapper
        selectedWrapperCopy.translate(translationVector);

        // replace old shape with new one
        canvasModel.getShapeManager().editShape(shapeIndex, selectedWrapperCopy);

        // repaint canvas
        canvas.repaint();
    }

    @Override
    public boolean shouldDraw() {
        return getCanvasMode() == Mode.TRANSLATION;
    }

    /**
     * Asks user to enter the radii for the ellipse. If input values are invalid
     * or if the operation is cancelled, null is returned.
     * 
     * @return array with radii [rx, ry]
     */
    private Point2D requestTranslationVector() {
        final Point2D zeroVector = new Point2D.Double(0, 0);

        JTextField rxField = new JTextField(5);
        JTextField ryField = new JTextField(5);
        JPanel panel = new JPanel();
        panel.add(new JLabel("X:"));
        panel.add(rxField);
        panel.add(new JLabel("Y:"));
        panel.add(ryField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Enter translation vector",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        // Request focus again otherwise keyboard shortcuts will not work
        canvas.getTopLevelAncestor().requestFocus();

        if (result == JOptionPane.OK_OPTION) {
            try {
                return new Point2D.Double(Integer.parseInt(rxField.getText()), Integer.parseInt(ryField.getText()));
            } catch (NumberFormatException e) {
                return zeroVector;
            }
        }
        return zeroVector;
    }

}
