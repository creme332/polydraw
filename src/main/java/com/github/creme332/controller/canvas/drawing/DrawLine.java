package com.github.creme332.controller.canvas.drawing;

import java.awt.Polygon;
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
        this.canvasModel = app.getCanvasModel();
    }

    @Override
    public boolean shouldDraw() {
        return getCanvasMode() == Mode.DRAW_LINE_BRESENHAM || getCanvasMode() == Mode.DRAW_LINE_DDA;
    }

    @Override
    public void handleMouseMoved(Point2D polySpaceMousePosition) {
        if (preview != null && preview.getPlottedPoints().size() == 1) {
            // number of plotted points is 1
            Point2D lineStart = preview.getPlottedPoints().get(0);
            Polygon shape;
            if (getCanvasMode() == Mode.DRAW_LINE_DDA) {
                int[][] coordinates = LineCalculator.dda((int) lineStart.getX(), (int) lineStart.getY(),
                        (int) polySpaceMousePosition.getX(),
                        (int) polySpaceMousePosition.getY());

                shape = new Polygon(coordinates[0], coordinates[1], coordinates[0].length);
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

            // save plotted point
            preview.getPlottedPoints().add(polySpaceMousePosition);

            // save wrapper to canvas model
            canvasModel.getShapeManager().addShape(preview);
            return;
        }

        // second coordinate has now been selected
        preview.getPlottedPoints().add(polySpaceMousePosition);

        // reset wrapper
        preview = null;
    }
}
