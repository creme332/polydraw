package com.github.creme332.controller.drawing;

import java.awt.Polygon;
import java.awt.geom.Point2D;

import com.github.creme332.algorithms.EllipseCalculator;
import com.github.creme332.model.AppState;
import com.github.creme332.model.Mode;
import com.github.creme332.model.ShapeWrapper;
import com.github.creme332.view.Canvas;

public class DrawEllipse extends DrawController {
    private EllipseCalculator ellipseCalculator = new EllipseCalculator();

    public DrawEllipse(AppState app, Canvas canvas) {
        super(app, canvas);
    }

    @Override
    public void handleMouseMoved(Point2D polySpaceMousePosition) {
        if (getCanvasMode() == Mode.DRAW_ELLIPSE && currentWrapper != null
                && currentWrapper.getPlottedPoints().size() == 2) {
            // create an ellipse
            Point2D center = currentWrapper.getPlottedPoints().get(0);
            int ry = (int) Math.abs(currentWrapper.getPlottedPoints().get(1).distance(center));
            int rx = (int) Math.abs(polySpaceMousePosition.distance(center));

            if (rx == 0 || ry == 0) {
                return;
            }

            int[][] coordinates = ellipseCalculator.getOrderedPoints((int) center.getX(), (int) center.getY(),
                    rx, ry);
            Polygon ellipse = new Polygon(coordinates[0], coordinates[1], coordinates[0].length);
            currentWrapper.setShape(ellipse);
            canvas.repaint();
        }
    }

    @Override
    public void handleMousePressed(Point2D polySpaceMousePosition) {
        if (currentWrapper == null) {
            // center of ellipse has been selected

            // create a shape wrapper
            currentWrapper = new ShapeWrapper(canvasModel.getFillColor(), canvasModel.getFillColor(),
                    canvasModel.getLineType(),
                    canvasModel.getLineThickness());
            currentWrapper.getPlottedPoints().add(polySpaceMousePosition);

            // save wrapper
            canvasModel.getShapes().add(currentWrapper);

        } else {
            if (currentWrapper.getPlottedPoints().size() == 1) {
                // second point has now been selected
                currentWrapper.getPlottedPoints().add(polySpaceMousePosition);
            } else {
                // third point has been selected
                currentWrapper.getPlottedPoints().add(polySpaceMousePosition);

                // create an ellipse
                Point2D center = currentWrapper.getPlottedPoints().get(0);
                int ry = (int) Math.abs(currentWrapper.getPlottedPoints().get(1).distance(center));
                int rx = (int) Math.abs(currentWrapper.getPlottedPoints().get(2).distance(center));

                if (rx == 0 || ry == 0) {
                    return;
                }

                int[][] coordinates = ellipseCalculator.getOrderedPoints((int) center.getX(), (int) center.getY(),
                        rx, ry);
                Polygon ellipse = new Polygon(coordinates[0], coordinates[1], coordinates[0].length);

                currentWrapper.setShape(ellipse);

                currentWrapper = null;
            }
        }

    }

    @Override
    public boolean shouldDraw() {
        return getCanvasMode() == Mode.DRAW_ELLIPSE;
    }

}
