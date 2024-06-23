package com.github.creme332.controller.drawing;

import java.awt.Polygon;
import java.awt.geom.Point2D;

import com.github.creme332.algorithms.CircleCalculator;
import com.github.creme332.model.AppState;
import com.github.creme332.model.Mode;
import com.github.creme332.model.ShapeWrapper;
import com.github.creme332.view.Canvas;

public class DrawCircle extends DrawController {
    private CircleCalculator circleCalculator = new CircleCalculator();

    public DrawCircle(AppState app, Canvas canvas) {
        super(app, canvas);
    }

    @Override
    public void handleMouseMoved(Point2D polySpaceMousePosition) {
        if (getCanvasMode() == Mode.DRAW_CIRCLE_DYNAMIC && currentWrapper != null
                && currentWrapper.getPlottedPoints().size() == 1) {

            Point2D center = currentWrapper.getPlottedPoints().get(0);
            double radius = polySpaceMousePosition.distance(center);
            int roundedRadius = (int) Math.round(radius);
            if (roundedRadius == 0)
                return;

            currentWrapper.setShape(getCircle((int) center.getX(), (int) center.getY(), roundedRadius));
            canvas.repaint();
        }

    }

    private Polygon getCircle(int x, int y, int radius) {
        int[][] coordinates = circleCalculator.getOrderedPoints(x, y, radius);
        return new Polygon(coordinates[0], coordinates[1], coordinates[0].length);
    }

    @Override
    public void handleMousePressed(Point2D polySpaceMousePosition) {
        if (getCanvasMode() == Mode.DRAW_CIRCLE_DYNAMIC) {
            if (currentWrapper == null) {
                // center has been selected

                // create a shape wrapper
                currentWrapper = new ShapeWrapper(canvasModel.getFillColor(), canvasModel.getFillColor(),
                        canvasModel.getLineType(),
                        canvasModel.getLineThickness());
                currentWrapper.getPlottedPoints().add(polySpaceMousePosition);

                // save wrapper
                canvasModel.getShapes().add(currentWrapper);

            } else {
                // second point has now been selected
                currentWrapper.getPlottedPoints().add(polySpaceMousePosition);

                // create a circle
                Point2D center = currentWrapper.getPlottedPoints().get(0);
                double radius = polySpaceMousePosition.distance(center);
                int roundedRadius = (int) Math.round(radius);
                if (roundedRadius == 0)
                    return;

                currentWrapper.setShape(getCircle((int) center.getX(), (int) center.getY(), roundedRadius));

                currentWrapper = null;
            }
        }
    }

    @Override
    public boolean shouldDraw() {
        return getCanvasMode() == Mode.DRAW_CIRCLE_DYNAMIC || getCanvasMode() == Mode.DRAW_CIRCLE_FIXED;
    }

}
