package com.github.creme332.controller.canvas.transform;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.List;

import com.github.creme332.model.AppState;
import com.github.creme332.model.CanvasModel;
import com.github.creme332.model.Mode;
import com.github.creme332.model.ShapeWrapper;
import com.github.creme332.view.Canvas;

/**
 * A template class for performing transformations on shapes.
 */
public abstract class AbstractTransformer {
    protected Canvas canvas;
    protected CanvasModel canvasModel;
    private AppState app;

    protected AbstractTransformer(AppState app, Canvas canvas) {
        this.app = app;
        this.canvas = canvas;
        this.canvasModel = app.getCanvasModel();

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!shouldDraw())
                    return;
                Point2D polyspaceMousePosition = canvasModel.toPolySpace(e.getPoint());
                int selectedShapeIndex = getSelectedShapeIndex(polyspaceMousePosition);
                if (selectedShapeIndex < 0)
                    return;
                handleShapeSelection(selectedShapeIndex);
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
     * Called when user clicks on a coordinate on the canvas. You should override
     * this class if you want to handle mouse clicks.
     * 
     * @param polySpaceMousePosition Coordinate of mouse click in polyspace.
     */
    protected void handleMousePressed(Point2D polySpaceMousePosition) {
        // do nothing by default
    }

    /**
     * 
     * @param polyspaceMousePosition Coordinate of point lying inside shape
     * @return Index of first shape that contains polyspaceMousePosition. -1 if no
     *         such shape found.
     */
    private int getSelectedShapeIndex(Point2D polyspaceMousePosition) {
        List<ShapeWrapper> shapes = canvasModel.getShapeManager().getShapes();
        for (int i = 0; i < shapes.size(); i++) {
            ShapeWrapper wrapper = shapes.get(i);
            if (wrapper.getShape().contains(polyspaceMousePosition)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 
     * @return Current canvas mode
     */
    public Mode getCanvasMode() {
        return app.getMode();
    }

    /**
     * This method only gets called when shouldDraw() returns true and mouse
     * clicked.
     * 
     * @param shapeIndex A valid index representing selected shape
     */
    public abstract void handleShapeSelection(int shapeIndex);

    /**
     * Determines whether current controller should be allowed to handle event or
     * not.
     * 
     * @return
     */
    public abstract boolean shouldDraw();

    /**
     * Reset current controller to its initial state. For example, this method will
     * be invoked when mode changes while drawing was ongoing.
     */
    public void disposePreview() {
        // delete any preview shape
        canvasModel.getShapeManager().setShapePreview(null);
    }
}
