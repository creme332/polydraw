package com.github.creme332.controller.canvas.drawing;

import java.awt.Polygon;
import java.awt.geom.Point2D;

import com.github.creme332.model.AppState;
import com.github.creme332.model.Mode;
import com.github.creme332.model.ShapeWrapper;
import com.github.creme332.view.Canvas;

public class DrawIrregularPolygon extends AbstractDrawer {

    public DrawIrregularPolygon(AppState app, Canvas canvas) {
        super(app, canvas);
    }

    @Override
    protected boolean shouldDraw() {
        return getCanvasMode() == Mode.DRAW_POLYGON_DYNAMIC;
    }

    @Override
    protected void handleMouseMoved(Point2D polySpaceMousePosition) {
        // check if drawing is in progress
        if (preview != null) {
            // create a new polygon using plotted points
            Polygon newPolygon = createPolygonFromPlottedPoints();

            // add current mouse position as vertex
            newPolygon.addPoint((int) polySpaceMousePosition.getX(), (int) polySpaceMousePosition.getY());

            // draw new preview
            preview.setShape(newPolygon);
            canvas.repaint();
        }
    }

    @Override
    protected void handleMousePressed(Point2D polySpaceMousePosition) {
        if (preview == null) {
            // First point selected, initialize the wrapper
            // Create a shape wrapper
            preview = new ShapeWrapper(canvasModel.getShapeColor(), canvasModel.getLineType(),
                    canvasModel.getLineThickness());

            // Save the first plotted point
            preview.getPlottedPoints().add(polySpaceMousePosition);

            // Save preview
            canvasModel.getShapeManager().setShapePreview(preview);

            return;
        }

        // if the user clicked on the first vertex again
        if (preview.getPlottedPoints().get(0).equals(polySpaceMousePosition)) {
            // draw final closed polygon
            preview.setShape(createPolygonFromPlottedPoints());

            // NOTE: Do not add the current mouse position as vertex again as it is a
            // duplicate of the first vertex

            // save preview as an actual shape
            canvasModel.getShapeManager().addShape(preview);

            disposePreview();
        } else {
            // Add new plotted vertex to the plotted points array
            // Note: We want all plotted points to be unique.
            preview.getPlottedPoints().add(polySpaceMousePosition);
        }

        canvas.repaint();
    }

    /**
     * 
     * @return A closed polygon generated only from plotted points. Plotted points
     *         should not contain any duplicates.
     */
    private Polygon createPolygonFromPlottedPoints() {
        int plottedPointsCount = preview.getPlottedPoints().size();
        Polygon newPolygon = new Polygon();

        for (int i = 0; i < plottedPointsCount; i++) {
            Point2D point = preview.getPlottedPoints().get(i);
            newPolygon.addPoint((int) point.getX(), (int) point.getY());
        }

        return newPolygon;
    }
}
