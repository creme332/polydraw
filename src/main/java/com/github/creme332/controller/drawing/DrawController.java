package com.github.creme332.controller.drawing;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import com.github.creme332.model.AppState;
import com.github.creme332.model.CanvasModel;
import com.github.creme332.model.Mode;
import com.github.creme332.model.ShapeManager;
import com.github.creme332.model.ShapeWrapper;
import com.github.creme332.view.Canvas;

/**
 * Class for drawing on canvas.
 */
public abstract class DrawController {

    protected Canvas canvas;
    protected CanvasModel canvasModel;
    private AppState app;

    /**
     * Wrapper for shape currently being drawn.
     */
    protected ShapeWrapper preview;

    protected DrawController(AppState app, Canvas canvas) {
        this.app = app;
        this.canvas = canvas;
        this.canvasModel = app.getCanvasModel();

        canvas.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (!shouldDraw())
                    return;
                handleMouseMoved(canvasModel.toPolySpace(e.getPoint()));
            }
        });

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!shouldDraw())
                    return;
                handleMousePressed(canvasModel.toPolySpace(e.getPoint()));
            }
        });
    }

    /**
     * 
     * @return Current canvas mode
     */
    public Mode getCanvasMode() {
        return app.getMode();
    }

    /**
     * This method only gets called when shouldDraw() returns true and mouse is
     * moving.
     * 
     * @param polySpaceMousePosition
     */
    public abstract void handleMouseMoved(Point2D polySpaceMousePosition);

    /**
     * This method only gets called when shouldDraw() returns true and mouse
     * clicked.
     * 
     * @param polySpaceMousePosition
     */
    public abstract void handleMousePressed(Point2D polySpaceMousePosition);

    /**
     * Determines whether current controller should be allowed to draw or not.
     * 
     * @return
     */
    public abstract boolean shouldDraw();

    /**
     * Reset controller to its initial state. For example, this method will be
     * invoked when mode changes while drawing was ongoing.
     */
    public void disposePreview() {
        if (preview == null)
            return;

        final ShapeManager manager = canvasModel.getShapeManager();

        /**
         * Preview shape is the last shape inserted to the shapes array.
         */
        final int previewIndex = manager.getShapes().size() - 1;

        if (previewIndex < 0)
            return;

        // delete the preview
        canvasModel.getShapeManager().deleteShape(previewIndex);
        preview = null;
    }
}
