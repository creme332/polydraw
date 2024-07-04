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
        // check if drawing is in progress
        if (preview != null) {
            // draw a new polygon using plotted points and current mouse position
            preview.setShape(createPolygonPreview(polySpaceMousePosition));
            canvas.repaint();
        }
    }

    @Override
    public void handleMousePressed(Point2D polySpaceMousePosition) {
        if (preview == null) {
            // First point selected, initialize the wrapper
            initWrapper(polySpaceMousePosition);
            return;
        }

        // if the user clicked on the first vertex again
        if (preview.getPlottedPoints().get(0).equals(polySpaceMousePosition)) {
            // draw final closed polygon
            preview.setShape(createPolygonFromPlottedPoints());

            // reset preview
            preview = null;
        } else {
            // Add new plotted vertex to the plotted points array
            // Note: We want all plotted points to be unique.
            preview.getPlottedPoints().add(polySpaceMousePosition);
        }

        canvas.repaint();
    }

    private void initWrapper(Point2D firstPoint) {
        // Create a shape wrapper
        preview = new ShapeWrapper(canvasModel.getShapeColor(), canvasModel.getLineType(),
                canvasModel.getLineThickness());

        // Save the first plotted point
        preview.getPlottedPoints().add(firstPoint);

        // Save the wrapper to the canvas model
        canvasModel.getShapeManager().addShape(preview);
    }

    /**
     * Creates a polygon using plotted points and last vertex.
     * 
     * @param lastVertex last vertex may or may not be a duplicate of a plotted
     *                   point
     */
    private Polygon createPolygonPreview(Point2D lastVertex) {
        int plottedPointsCount = preview.getPlottedPoints().size();
        int[] xPoints = new int[plottedPointsCount + 1];
        int[] yPoints = new int[plottedPointsCount + 1];

        for (int i = 0; i < plottedPointsCount; i++) {
            Point2D point = preview.getPlottedPoints().get(i);
            xPoints[i] = (int) point.getX();
            yPoints[i] = (int) point.getY();
        }

        // add last vertex
        xPoints[plottedPointsCount] = (int) lastVertex.getX();
        yPoints[plottedPointsCount] = (int) lastVertex.getY();

        return new Polygon(xPoints, yPoints, plottedPointsCount + 1);
    }

    /**
     * 
     * @return A closed polygon generated only from plotted points. Plotted points
     *         are guaranteed to not contain duplicates.
     */
    private Polygon createPolygonFromPlottedPoints() {
        int plottedPointsCount = preview.getPlottedPoints().size();
        int[] xPoints = new int[plottedPointsCount];
        int[] yPoints = new int[plottedPointsCount];

        for (int i = 0; i < plottedPointsCount; i++) {
            Point2D point = preview.getPlottedPoints().get(i);
            xPoints[i] = (int) point.getX();
            yPoints[i] = (int) point.getY();
        }

        return new Polygon(xPoints, yPoints, plottedPointsCount);
    }
}
