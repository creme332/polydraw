package com.github.creme332.model;

import java.awt.Color;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.creme332.model.calculator.PolygonCalculator;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class ShapeWrapper {
    private Shape shape;
    private Color lineColor = Color.BLACK;
    private LineType lineType = LineType.SOLID;
    private int lineThickness = 1;
    private boolean fillable = true;

    /**
     * Coordinates plotted by user to create shape. A plotted point is a one which
     * user has clicked one. A point on which user is hovering on is NOT a plotted
     * point.
     */
    private List<Point2D> plottedPoints = new ArrayList<>();

    /**
     * 
     * @return Original list of plotted points.
     */
    public List<Point2D> getPlottedPoints() {
        return plottedPoints;
    }

    public ShapeWrapper(Color lineColor, LineType lineType, int lineThickness) {
        this.lineColor = lineColor;
        this.lineType = lineType;
        this.lineThickness = lineThickness;
    }

    /**
     * Copy constructor.
     * 
     * @param wrapper
     */
    public ShapeWrapper(ShapeWrapper wrapper) {
        if (wrapper == null) {
            throw new NullPointerException("wrapper should not be null when passed to copy constructor.");
        }

        // save primitive attributes
        lineColor = wrapper.lineColor;
        lineType = wrapper.lineType;
        lineThickness = wrapper.lineThickness;
        fillable = wrapper.fillable;

        // create a new shape object
        if (wrapper.shape != null) {
            if (wrapper.shape instanceof Polygon) {
                Polygon original = (Polygon) wrapper.shape;
                Polygon copy = new Polygon(original.xpoints, original.ypoints, original.npoints);
                shape = copy;
            }
            if (wrapper.shape instanceof Path2D.Double) {
                Path2D.Double original = (Path2D.Double) wrapper.shape;
                shape = (Path2D.Double) original.clone();
            }
        }

        // create a new array for plotted points
        for (Point2D point : wrapper.getPlottedPoints()) {
            plottedPoints.add(new Point2D.Double(point.getX(), point.getY()));
        }
    }

    /**
     * Finds the center of a given shape.
     * 
     * @param shape the shape to find the center of
     * @return a Point2D representing the center of the shape
     */
    public Point2D findShapeCenter() {
        if (shape == null) {
            return null;
        }

        Rectangle2D bounds = shape.getBounds2D();
        double centerX = bounds.getCenterX();
        double centerY = bounds.getCenterY();

        return new Point2D.Double(centerX, centerY);
    }

    /**
     * Translates shape and plotted points by a given translation vector.
     * 
     * @param translationVector translation vector
     * @return
     */
    public void translate(final Point2D translationVector) {
        // create transformation for shape
        final AffineTransform transform = new AffineTransform();
        transform.translate(translationVector.getX(), translationVector.getY());

        final Shape oldShape = shape;
        Shape transformedShape;

        if (oldShape instanceof Polygon) {
            // if selected shape is of type polygon, ensure that the transformed shape is of
            // type Polygon as well
            transformedShape = PolygonCalculator.transformPolygon((Polygon) oldShape,
                    transform);
        } else {
            transformedShape = transform.createTransformedShape(oldShape);
        }

        // replace old shape with transformed shape
        setShape(transformedShape);

        // translate plotted points
        for (int i = 0; i < plottedPoints.size(); i++) {
            Point2D oldPoint = plottedPoints.get(i);
            plottedPoints.set(i,
                    new Point2D.Double(oldPoint.getX() + translationVector.getX(),
                            oldPoint.getY() + translationVector.getY()));
        }
    }

    public Shape getShape() {
        return shape;
    }

    public Polygon toPolygon() {
        Polygon original = (Polygon) shape;
        return new Polygon(original.xpoints, original.ypoints, original.npoints);
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public Color getLineColor() {
        return lineColor;
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }

    public boolean isFillable() {
        return fillable;
    }

    public void setFillable(boolean fillable) {
        this.fillable = fillable;
    }

    /**
     * Returns a fill color for a shape if it is fillable. The fill color of a
     * fillable shape is a transparent version of the line color.
     */
    public Color getFillColor() {
        if (!fillable)
            return null;
        Color a = lineColor;
        return new Color(a.getRed() / 255f, a.getGreen() / 255f, a.getBlue() / 255f, .2f);
    }

    public void setLineThickness(int lineThickness) {
        this.lineThickness = lineThickness;
    }

    public int getLineThickness() {
        return lineThickness;
    }

    public LineType getLineType() {
        return lineType;
    }

    public void setLineType(LineType lineType) {
        this.lineType = lineType;
    }

    @Override
    public String toString() {
        // create a list of plotted point coordinates
        StringBuilder plottedPointString = new StringBuilder();
        plottedPointString.append("[");
        for (Point2D point2d : plottedPoints) {
            plottedPointString.append(String.format("[%f, %f], ", point2d.getX(), point2d.getY()));
        }
        plottedPointString.append("]");

        return String.format("""
                ShapeWrapper{
                    plottedPoints: %s
                    xpoints: %s
                    ypoints: %s
                    lineColor: %s
                    lineType: %s
                    lineThickness: %d
                }
                """, plottedPointString, Arrays.toString(toPolygon().xpoints), Arrays.toString(toPolygon().ypoints),
                lineColor, lineType, lineThickness);
    }
}
