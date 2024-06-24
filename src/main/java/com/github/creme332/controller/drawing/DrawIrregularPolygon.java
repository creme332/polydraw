package com.github.creme332.controller.drawing;

import java.awt.Polygon;
import java.awt.geom.Point2D;

import com.github.creme332.model.AppState;
import com.github.creme332.model.Mode;
import com.github.creme332.model.ShapeWrapper;
import com.github.creme332.view.Canvas;

public class DrawIrregularPolygon extends DrawController {

    public DrawIrregularPolygon(AppState app, Canvas canvas) {
        super(app, canvas);
    }

    @Override
    public boolean shouldDraw() {
        return getCanvasMode() == Mode.DRAW_POLYGON_DYNAMIC;
    }

    @Override
    public void handleMouseMoved(Point2D polySpaceMousePosition) {
        // no preview for irregular polygon while moving the mouse
    }

    @Override
    public void handleMousePressed(Point2D polySpaceMousePosition) {
        if (preview == null) {
            // first point selected, initialize the wrapper
            initWrapper(polySpaceMousePosition);
            return;
        }

        // add point to the preview
        preview.getPlottedPoints().add(polySpaceMousePosition);

        // check if the user clicked on the first point again to close the polygon
        if (isFirstPointClickedAgain(polySpaceMousePosition)) {
            finishPolygon();
        }

        canvas.repaint();
    }

    private void initWrapper(Point2D firstPoint) {
        // create a shape wrapper
        preview = new ShapeWrapper(canvasModel.getFillColor(), canvasModel.getFillColor(),
                canvasModel.getLineType(),
                canvasModel.getLineThickness());

        // save first plotted point
        preview.getPlottedPoints().add(firstPoint);

        // save the wrapper to the canvas model
        canvasModel.getShapes().add(preview);
    }

    private boolean isFirstPointClickedAgain(Point2D currentPoint) {
        Point2D firstPoint = preview.getPlottedPoints().get(0);
        double distance = firstPoint.distance(currentPoint);
        // check if the distance between the current point and the first point is very small
        return distance < 5.0; // You can adjust this threshold value
    }

    private void finishPolygon() {
        int size = preview.getPlottedPoints().size();
        int[] xPoints = new int[size];
        int[] yPoints = new int[size];

        for (int i = 0; i < size; i++) {
            Point2D point = preview.getPlottedPoints().get(i);
            xPoints[i] = (int) point.getX();
            yPoints[i] = (int) point.getY();
        }

        Polygon polygon = new Polygon(xPoints, yPoints, size);
        preview.setShape(polygon);

        // Reset the preview
        preview = null;
    }
}
