package com.github.creme332.controller.canvas.drawing;

import java.awt.Polygon;
import java.awt.geom.Point2D;

import com.github.creme332.model.AppState;
import com.github.creme332.model.Mode;
import com.github.creme332.model.ShapeWrapper;
import com.github.creme332.model.calculator.EllipseCalculator;
import com.github.creme332.view.Canvas;

public class DrawEllipse extends AbstractDrawer {
    private EllipseCalculator ellipseCalculator = new EllipseCalculator();

    public DrawEllipse(AppState app, Canvas canvas) {
        super(app, canvas);
    }

    @Override
    public void handleMouseMoved(Point2D polySpaceMousePosition) {
        if (getCanvasMode() == Mode.DRAW_ELLIPSE && preview != null
                && preview.getPlottedPoints().size() == 2) {

            final Point2D firstFocus = preview.getPlottedPoints().get(0);
            final Point2D secondFocus = preview.getPlottedPoints().get(1);

            int[][] coordinates = ellipseCalculator.getOrderedPoints(firstFocus, secondFocus, polySpaceMousePosition);
            if (coordinates.length == 2) {
                Polygon ellipse = new Polygon(coordinates[0], coordinates[1], coordinates[0].length);
                preview.setShape(ellipse);
                canvas.repaint();
            }
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

            // save preview
            canvasModel.getShapeManager().setShapePreview(preview);

            return;
        }

        if (preview.getPlottedPoints().size() == 1) {
            // second point has now been selected
            preview.getPlottedPoints().add(polySpaceMousePosition);
        } else {
            // third point has been selected
            preview.getPlottedPoints().add(polySpaceMousePosition);

            // save preview as an actual shape
            canvasModel.getShapeManager().addShape(preview);

            disposePreview();
        }

    }

    @Override
    public boolean shouldDraw() {
        return getCanvasMode() == Mode.DRAW_ELLIPSE;
    }

}
