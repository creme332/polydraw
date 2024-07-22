package com.github.creme332.controller.canvas.transform;

import java.awt.geom.Point2D;

import com.github.creme332.model.AppState;
import com.github.creme332.model.Mode;
import com.github.creme332.model.ShapeWrapper;
import com.github.creme332.view.Canvas;

public class Rotator extends AbstractTransformer {

    public Rotator(AppState app, Canvas canvas) {
        super(app, canvas);
    }

    @Override
    public void handleShapeSelection(int shapeIndex) {
        // get copy of selected shape
        ShapeWrapper selectedWrapperCopy = canvasModel.getShapeManager().getShapes().get(shapeIndex);

        // TODO: Get rotation details

        // perform rotation
        selectedWrapperCopy.rotate(30, new Point2D.Double(0, 0));

        // replace old shape with new one
        canvasModel.getShapeManager().editShape(shapeIndex, selectedWrapperCopy);

        // repaint canvas
        canvas.repaint();
    }

    @Override
    public boolean shouldDraw() {
        return getCanvasMode() == Mode.ROTATE_ABOUT_POINT;
    }

}
