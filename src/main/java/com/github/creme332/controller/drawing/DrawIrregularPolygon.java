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
        if (preview != null && !preview.getPlottedPoints().isEmpty()) {
            // Optionally update the preview with a line segment to the current mouse position
            Point2D lastPoint = preview.getPlottedPoints().get(preview.getPlottedPoints().size() - 1);
            preview.getPlottedPoints().set(preview.getPlottedPoints().size() - 1, polySpaceMousePosition);

            canvas.repaint();
            preview.getPlottedPoints().set(preview.getPlottedPoints().size() - 1, lastPoint);
        }
    }

    @Override
    public void handleMousePressed(Point2D polySpaceMousePosition) {
        if (preview == null) {
            // First point selected, initialize the wrapper
            initWrapper(polySpaceMousePosition);
            return;
        }

        // Add point to the preview
        preview.getPlottedPoints().add(polySpaceMousePosition);

        // Check if the user clicked on the first point again to close the polygon
        if (isFirstPointClickedAgain(polySpaceMousePosition)) {
            finishPolygon();
        }

        canvas.repaint();
    }

    private void initWrapper(Point2D firstPoint) {
        // Create a shape wrapper
        preview = new ShapeWrapper(canvasModel.getShapeColor(), canvasModel.getLineType(), canvasModel.getLineThickness());

        // Save the first plotted point
        preview.getPlottedPoints().add(firstPoint);

        // Save the wrapper to the canvas model
        canvasModel.addShape(preview);
    }

    private boolean isFirstPointClickedAgain(Point2D currentPoint) {
        Point2D firstPoint = preview.getPlottedPoints().get(0);
        double distance = firstPoint.distance(currentPoint);
        // Check if the distance between the current point and the first point is very small
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
