package com.github.creme332.controller.canvas.transform;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

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

        startTranslationAnimation(selectedWrapperCopy, shapeIndex, translationVector);
    }

    /**
     * Animates the translation of a given shape using linear interpolation.
     */
    public void startTranslationAnimation(final ShapeWrapper selectedWrapperCopy, final int shapeIndex,
            Point2D translationVector) {
        final int totalSteps = 60; // 60 frames
        final int animationDuration = 1000; // 1 second
        final int animationDelay = animationDuration / totalSteps; // Delay in milliseconds between updates

        // Timer to handle the animation
        Timer timer = new Timer(animationDelay, new ActionListener() {
            private int stepCount = 0;
            ShapeWrapper copyPreview;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (stepCount <= totalSteps) {
                    copyPreview = new ShapeWrapper(selectedWrapperCopy);
                    Point2D newTranslationVector = new Point2D.Double(translationVector.getX() * stepCount / totalSteps,
                            translationVector.getY() * stepCount / totalSteps);
                    copyPreview.translate(newTranslationVector);
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
                double x = Double.parseDouble(rxField.getText());
                double y = Double.parseDouble(ryField.getText());
                return new Point2D.Double(x, y);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(canvas, "Invalid input! Please enter valid numbers.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return zeroVector;
            }
        }
        return zeroVector;
    }

}
