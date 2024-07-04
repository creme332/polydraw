package com.github.creme332.controller.canvas.drawing;

import java.awt.Polygon;
import java.awt.geom.Point2D;

import com.github.creme332.model.AppState;
import com.github.creme332.model.Mode;
import com.github.creme332.model.ShapeWrapper;
import com.github.creme332.model.calculator.EllipseCalculator;
import com.github.creme332.view.Canvas;

public class DrawEllipse extends DrawController {
    private EllipseCalculator ellipseCalculator = new EllipseCalculator();

    public DrawEllipse(AppState app, Canvas canvas) {
        super(app, canvas);
    }

    @Override
    public void handleMouseMoved(Point2D polySpaceMousePosition) {
        if (getCanvasMode() == Mode.DRAW_ELLIPSE && preview != null
                && preview.getPlottedPoints().size() == 2) {
            // create an ellipse
            Point2D center = preview.getPlottedPoints().get(0);
            int ry = (int) Math.abs(preview.getPlottedPoints().get(1).distance(center));
            int rx = (int) Math.abs(polySpaceMousePosition.distance(center));

            if (rx == 0 || ry == 0) {
                return;
            }

            int[][] coordinates = ellipseCalculator.getOrderedPoints((int) center.getX(), (int) center.getY(),
                    rx, ry);
            Polygon ellipse = new Polygon(coordinates[0], coordinates[1], coordinates[0].length);
            preview.setShape(ellipse);
            canvas.repaint();
        }
    }

    @Override
    public void handleMousePressed(Point2D polySpaceMousePosition) {
        if (preview == null) {
            // center of ellipse has been selected

            // create a shape wrapper
            preview = new ShapeWrapper(canvasModel.getShapeColor(),
                    canvasModel.getLineType(),
                    canvasModel.getLineThickness());
            preview.getPlottedPoints().add(polySpaceMousePosition);

            // save wrapper
            canvasModel.getShapeManager().addShape(preview);

        } else {
            if (preview.getPlottedPoints().size() == 1) {
                // second point has now been selected
                preview.getPlottedPoints().add(polySpaceMousePosition);
            } else {
                // third point has been selected
                preview.getPlottedPoints().add(polySpaceMousePosition);

                preview = null;
            }
        }

    }

    @Override
    public boolean shouldDraw() {
        return getCanvasMode() == Mode.DRAW_ELLIPSE;
    }

}
