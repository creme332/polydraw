package com.github.creme332.controller.drawing;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import com.github.creme332.model.AppState;
import com.github.creme332.model.Mode;
import com.github.creme332.model.ShapeWrapper;
import com.github.creme332.view.Canvas;

public class DrawLine extends DrawController {

    public DrawLine(AppState app, Canvas canvas) {
        super(app, canvas);
        this.canvas = canvas;
        this.canvasModel = app.getCanvasModel();
    }

    private void initWrapper(Point2D firstPoint) {
        // create a shape wrapper
        preview = new ShapeWrapper(canvasModel.getFillColor(), canvasModel.getFillColor(),
                canvasModel.getLineType(),
                canvasModel.getLineThickness());

        // save plotted point
        preview.getPlottedPoints().add(firstPoint);
    }

    private void drawShapePreview(Point2D to) {
        Point2D from = preview.getPlottedPoints().get(0);

        Line2D line = new Line2D.Double(from, to);
        preview.setShape(line);
        canvas.repaint();
    }

    @Override
    public boolean shouldDraw() {
        return getCanvasMode() == Mode.DRAW_LINE_BRESENHAM || getCanvasMode() == Mode.DRAW_LINE_DDA;
    }

    @Override
    public void handleMouseMoved(Point2D polySpaceMousePosition) {
        if (preview != null && preview.getPlottedPoints().size() == 1) {
            // number of plotted points is 1
            drawShapePreview(polySpaceMousePosition);
        }
    }

    @Override
    public void handleMousePressed(Point2D polySpaceMousePosition) {
        if (preview == null) {
            // first coordinate of line has just been selected

            // initialize wrapper with plotted point
            initWrapper(polySpaceMousePosition);

            // save wrapper to canvas model
            canvasModel.getShapes().add(preview);
            return;
        }

        // second coordinate has now been selected
        preview.getPlottedPoints().add(polySpaceMousePosition);

        // reset wrapper
        preview = null;
    } 
}
