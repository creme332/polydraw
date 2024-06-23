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
        currentWrapper = new ShapeWrapper(canvasModel.getFillColor(), canvasModel.getFillColor(),
                canvasModel.getLineType(),
                canvasModel.getLineThickness());

        // save plotted point
        currentWrapper.getPlottedPoints().add(firstPoint);
    }

    private void drawShapePreview(Point2D to) {
        Point2D from = currentWrapper.getPlottedPoints().get(0);

        Line2D line = new Line2D.Double(from, to);
        currentWrapper.setShape(line);
        canvas.repaint();
    }

    @Override
    public boolean shouldDraw() {
        return getCanvasMode() == Mode.DRAW_LINE_BRESENHAM || getCanvasMode() == Mode.DRAW_LINE_DDA;
    }

    @Override
    public void handleMouseMoved(Point2D polySpaceMousePosition) {
        if (currentWrapper != null && currentWrapper.getPlottedPoints().size() == 1) {
            // number of plotted points is 1
            drawShapePreview(polySpaceMousePosition);
        }
    }

    @Override
    public void handleMousePressed(Point2D polySpaceMousePosition) {
        if (currentWrapper == null) {
            // first coordinate of line has just been selected

            // initialize wrapper with plotted point
            initWrapper(polySpaceMousePosition);

            // save wrapper to canvas model
            canvasModel.getShapes().add(currentWrapper);
            return;
        }

        // second coordinate has now been selected
        Point2D lineStart = currentWrapper.getPlottedPoints().get(0);

        Line2D line = new Line2D.Double();
        line.setLine(lineStart, polySpaceMousePosition);

        currentWrapper.getPlottedPoints().add(polySpaceMousePosition);
        currentWrapper.setShape(line);

        canvas.repaint();

        // reset wrapper
        currentWrapper = null;

    }
}
