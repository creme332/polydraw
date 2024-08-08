package com.github.creme332.controller.canvas.transform;

import java.awt.Color;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.Polygon;
import java.util.List;

import com.github.creme332.controller.canvas.drawing.DrawLine;
import com.github.creme332.model.AppState;
import com.github.creme332.model.LineType;
import com.github.creme332.model.Mode;
import com.github.creme332.model.ShapeManager;
import com.github.creme332.model.ShapeWrapper;
import com.github.creme332.model.calculator.LineClipperCalculator;
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

    private ShapeManager shapeManager;

    public Clipper(AppState app, Canvas canvas) {
        super(app, canvas);
        shapeManager = canvasModel.getShapeManager();

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!shouldDraw()) {
                    return;
                }

                if (corner1 == null) {
                    // first corner of clipping region has been selected
                    corner1 = app.getCanvasModel().toPolySpace(e.getPoint());
                    preview = new ShapeWrapper(Color.gray, LineType.DOTTED, 2);
                    shapeManager.setShapePreview(preview);
                    return;
                }
                // second corner of clipping region has been selected

                // hide preview
                disposePreview();

                // create clipping rectangle
                final Rectangle2D clipRect = (Rectangle2D) preview.getShape();

                // find lines affected by clip
                final List<ShapeWrapper> allShapes = shapeManager.getShapes();

                // loop through all shapes except from last one which is the preview
                for (int i = 0; i < allShapes.size(); i++) {
                    final ShapeWrapper wrapper = allShapes.get(i);
                    if (!wrapper.isLine())
                        continue;
                    clipLine(wrapper, clipRect);
                    shapeManager.editShape(i, wrapper);
                }
                canvas.repaint();
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
     * Updates shape and plotted points of a line.
     * 
     * @param wrapper  A line
     * @param clipRect Clipping rectangle
     */
    public void clipLine(final ShapeWrapper wrapper, final Rectangle2D clipRect) {
        if (!wrapper.isLine())
            return;

        final double xmin = clipRect.getMinX();
        final double ymin = clipRect.getMinY();
        final double xmax = clipRect.getMaxX();
        final double ymax = clipRect.getMaxY();

        final double[] endpoints = getEndPoints((Path2D.Double) wrapper.getShape());

        double[][] clippedLineEndpoints = LineClipperCalculator.clip(endpoints[0], endpoints[1],
                endpoints[2], endpoints[3], xmin, ymin, xmax, ymax);

        if (clippedLineEndpoints.length == 0 || clippedLineEndpoints[0].length == 0) {
            return;
        }

        // create a new polyline using clipped endpoints
        Shape clippedShape = DrawLine.createPolyline((int) clippedLineEndpoints[0][0],
                (int) clippedLineEndpoints[0][1],
                (int) clippedLineEndpoints[1][0], (int) clippedLineEndpoints[1][1]);
        wrapper.setShape(clippedShape);

        // update plotted points of clipped line
        List<Point2D> plottedPoints = wrapper.getPlottedPoints();
        plottedPoints.clear();
        plottedPoints.add(new Point2D.Double((int) clippedLineEndpoints[0][0], (int) clippedLineEndpoints[0][1]));
        plottedPoints.add(new Point2D.Double((int) clippedLineEndpoints[1][0], (int) clippedLineEndpoints[1][1]));
    }

    public double[] getEndPoints(Path2D.Double line) {

        // Assuming line is your Path2D.Double and it contains a single line segment
        PathIterator iterator = line.getPathIterator(null);
        double[] coords = new double[6];
        double x0 = 0, y0 = 0, x1 = 0, y1 = 0;
        int index = 0;

        while (!iterator.isDone()) {
            int segmentType = iterator.currentSegment(coords);
            if (segmentType == PathIterator.SEG_MOVETO || segmentType == PathIterator.SEG_LINETO) {
                if (index == 0) {
                    x0 = coords[0];
                    y0 = coords[1];
                } else {
                    x1 = coords[0];
                    y1 = coords[1];
                }
                index++;
            }
            iterator.next();
        }

        return new double[] { x0, y0, x1, y1 };
    }

    /**
     * Checks if a given shape is affected by the clipping rectangle.
     * 
     * @param shape    the shape to check
     * @param clipRect the clipping rectangle
     * @return true if the shape is affected, false otherwise
     */
    public static boolean isShapeAffectedByClip(Shape shape, Rectangle2D clipRect) {
        Area shapeArea = new Area(shape);
        Area clipArea = new Area(clipRect);
        shapeArea.intersect(clipArea);
        return !shapeArea.isEmpty();
    }

    /**
     * Clips a given shape to the specified rectangular clipping region.
     * 
     * @param shape    the shape to be clipped
     * @param clipRect the clipping rectangle
     * @return a new Shape representing the clipped area
     */
    public static Shape clipShape(Shape shape, Rectangle2D clipRect) {
        // Create an Area from the original shape
        Area shapeArea = new Area(shape);

        // Intersect the Area with the clipping rectangle
        shapeArea.intersect(new Area(clipRect));

        // Convert the clipped Area back to the appropriate shape type
        if (shape instanceof Path2D) {
            GeneralPath path = new GeneralPath();
            path.append(shapeArea.getPathIterator(null), false);
            return path;
        } else if (shape instanceof Polygon) {
            // Polygon cannot be directly derived from Area. We can use bounds as an
            // approximation
            Rectangle2D bounds = shapeArea.getBounds2D();
            return new Polygon(
                    new int[] { (int) bounds.getX(), (int) (bounds.getX() + bounds.getWidth()),
                            (int) (bounds.getX() + bounds.getWidth()), (int) bounds.getX() },
                    new int[] { (int) bounds.getY(), (int) bounds.getY(), (int) (bounds.getY() + bounds.getHeight()),
                            (int) (bounds.getY() + bounds.getHeight()) },
                    4);
        } else {
            // For other shapes, return as-is if type cannot be determined or not directly
            // convertible
            return shapeArea;
        }
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
        shapeManager.setShapePreview(null);
    }
}
