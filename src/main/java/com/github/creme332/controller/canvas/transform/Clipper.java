package com.github.creme332.controller.canvas.transform;

import java.awt.Color;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import com.github.creme332.model.AppState;
import com.github.creme332.model.LineType;
import com.github.creme332.model.Mode;
import com.github.creme332.model.ShapeWrapper;
import com.github.creme332.view.Canvas;

/**
 * Controller responsible for reflection mode.
 */
public class Clipper extends AbstractTransformer {
    /**
     * Stores coordinates of top left corner of clipping region.
     */
    private Point2D corner1;
    private Point2D corner2;
    private ShapeWrapper preview;

    public Clipper(AppState app, Canvas canvas) {
        super(app, canvas);

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!shouldDraw()) {
                    return;
                }

                if (corner1 == null) {
                    // first corner has been selected
                    corner1 = app.getCanvasModel().toPolySpace(e.getPoint());
                    preview = new ShapeWrapper(Color.gray, LineType.DOTTED, 2);
                    canvasModel.getShapeManager().setShapePreview(preview);
                    return;
                }

                // second corner has been finalized
                disposePreview();
            }

            @Override
            public void mouseReleased(MouseEvent arg0) {
                // do nothing
            }
        });

        canvas.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (!shouldDraw() || corner1 == null) {
                    return;
                }
                corner2 = app.getCanvasModel().toPolySpace(e.getPoint());

                // display clipping region
                preview.setShape(createRectangle(corner1, corner2));

                canvas.repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                // do nothing
            }
        });
    }

    /**
     * Creates a Rectangle2D shape from two diagonally opposite corners.
     * 
     * @param p1 the first corner point
     * @param p2 the second corner point
     * @return a Rectangle2D representing the rectangle
     */
    public static Shape createRectangle(Point2D p1, Point2D p2) {
        double x1 = p1.getX();
        double y1 = p1.getY();
        double x2 = p2.getX();
        double y2 = p2.getY();

        // Calculate the top-left corner (smallest x and y) and the dimensions of the
        // rectangle
        double x = Math.min(x1, x2);
        double y = Math.min(y1, y2);
        double width = Math.abs(x2 - x1);
        double height = Math.abs(y2 - y1);

        // Create and return the Rectangle2D
        return new Rectangle2D.Double(x, y, width, height);
    }

    @Override
    public void handleShapeSelection(int shapeIndex) {
        // do nothing
    }

    @Override
    public boolean shouldDraw() {
        return getCanvasMode() == Mode.CLIP;
    }

    /**
     * Reset current controller to its initial state. For example, this method will
     * be invoked when mode changes while drawing was ongoing.
     */
    @Override
    public void disposePreview() {
        corner1 = null;
        corner2 = null;
        // delete any preview shape
        canvasModel.getShapeManager().setShapePreview(null);
    }
}
