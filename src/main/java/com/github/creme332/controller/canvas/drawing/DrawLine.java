package com.github.creme332.controller.canvas.drawing;

import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

import com.github.creme332.model.AppState;
import com.github.creme332.model.Mode;
import com.github.creme332.model.ShapeWrapper;
import com.github.creme332.model.calculator.LineCalculator;
import com.github.creme332.view.Canvas;

public class DrawLine extends AbstractDrawer {

    public DrawLine(AppState app, Canvas canvas) {
        super(app, canvas);
        this.canvas = canvas;
    }

    @Override
    public boolean shouldDraw() {
        return getCanvasMode() == Mode.DRAW_LINE_BRESENHAM || getCanvasMode() == Mode.DRAW_LINE_DDA;
    }

    public static Path2D.Double createPolyline(int[] xPoints, int[] yPoints, int length) {
        if (xPoints == null || yPoints == null || xPoints.length != yPoints.length || length <= 0
                || length > xPoints.length) {
            throw new IllegalArgumentException("Invalid input parameters");
        }

        Path2D.Double polyline = new Path2D.Double();
        polyline.moveTo(xPoints[0], yPoints[0]);
        for (int i = 1; i < length; i++) {
            polyline.lineTo(xPoints[i], yPoints[i]);
        }

        return polyline;
    }

    @Override
    public void handleMouseMoved(Point2D polySpaceMousePosition) {
        if (preview != null && preview.getPlottedPoints().size() == 1) {
            // number of plotted points is 1
            Point2D lineStart = preview.getPlottedPoints().get(0);
            Shape shape;
            if (getCanvasMode() == Mode.DRAW_LINE_DDA) {
                int[][] coordinates = LineCalculator.dda((int) lineStart.getX(), (int) lineStart.getY(),
                        (int) polySpaceMousePosition.getX(),
                        (int) polySpaceMousePosition.getY());

                shape = createPolyline(coordinates[0], coordinates[1], coordinates[0].length);
            } else {
                int[][] coordinates = LineCalculator.bresenham((int) lineStart.getX(), (int) lineStart.getY(),
                        (int) polySpaceMousePosition.getX(),
                        (int) polySpaceMousePosition.getY());

                shape = new Polygon(coordinates[0], coordinates[1], coordinates[0].length);
            }
            preview.setShape(shape);
            canvas.repaint();
        }
    }

    @Override
    public void handleMousePressed(Point2D polySpaceMousePosition) {
        if (preview == null) {
            // first coordinate of line has just been selected

            // create a shape wrapper
            preview = new ShapeWrapper(canvasModel.getShapeColor(),
                    canvasModel.getLineType(),
                    canvasModel.getLineThickness());

            // line should not be fillable
            preview.setFillable(false);

            // save plotted point
            preview.getPlottedPoints().add(polySpaceMousePosition);

            // save preview
            canvasModel.getShapeManager().setShapePreview(preview);

            return;
        }

        // second coordinate has now been selected
        preview.getPlottedPoints().add(polySpaceMousePosition);

        // save preview as an actual shape
        canvasModel.getShapeManager().addShape(preview);

        disposePreview();
    }
}
