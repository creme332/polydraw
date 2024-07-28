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
     * Finds the center of the shape.
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
     * Shears the shape by the given shear factors.
     *
     * @param shearFactors the shear factors [shx, shy]
     */
    public void shear(double[] shearFactors) {
        if (shape == null || shearFactors == null) {
            return;
        }

        // Create an affine transform for shearing
        AffineTransform transform = new AffineTransform();
        transform.shear(shearFactors[0], shearFactors[1]);

        // Transform the shape
        Shape transformedShape;

        if (shape instanceof Polygon) {
            // Ensure that the transformed shape is of type Polygon if the original was
            // Polygon
            transformedShape = PolygonCalculator.transformPolygon((Polygon) shape, transform);
        } else {
            transformedShape = transform.createTransformedShape(shape);
        }

        // Set the new transformed shape
        setShape(transformedShape);

        // Shear the plotted points
        for (int i = 0; i < plottedPoints.size(); i++) {
            Point2D oldPoint = plottedPoints.get(i);
            Point2D newPoint = transform.transform(oldPoint, null);
            plottedPoints.set(i, newPoint);
        }
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

    /**
     * Rotates shape and plotted points clockwise about a specified point.
     * 
     * @param radAngle the angle of rotation in radians
     * @param pivot    the x-y coordinates of the rotation point
     */
    public void rotate(double radAngle, Point2D pivot) {
        AffineTransform transform = new AffineTransform();

        // Step 1: Translate the shape to the origin (negative of the rotation point)
        transform.translate(pivot.getX(), pivot.getY());

        // Step 2: Rotate the shape
        transform.rotate(radAngle);

        // Step 3: Translate the shape back to its original position
        transform.translate(-pivot.getX(), -pivot.getY());

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

        // rotate plotted points
        for (int i = 0; i < plottedPoints.size(); i++) {
            Point2D oldPoint = plottedPoints.get(i);
            Point2D rotatedPoint = PolygonCalculator.rotatePointAboutPivot(oldPoint, pivot, radAngle);
            plottedPoints.set(i, rotatedPoint);
        }
    }

    /**
     * 
     * @param point
     * @return True if point is inside shape or on its border
     */
    public boolean isPointOnShape(Point2D point) {
        return shape.contains(point) || isPointOnShapeBorder(shape, point);
    }

    /**
     * Checks if a point is on the border of the shape, within a specified
     * tolerance.
     * 
     * @param shape     the shape to check
     * @param point     the point to check
     * @param tolerance the tolerance within which to consider the point on the
     *                  border
     * @return true if the point is on the shape's border, false otherwise
     */
    public static boolean isPointOnShapeBorder(Shape shape, Point2D point) {
        final double TOLERANCE = 3.0;

        if (shape == null) {
            return false;
        }
        // Create a small rectangle around the clicked point
        Rectangle2D.Double clickArea = new Rectangle2D.Double(
                point.getX() - TOLERANCE, point.getY() - TOLERANCE,
                2 * TOLERANCE, 2 * TOLERANCE);
        // Check if the clickArea intersects with the shape's outline
        return shape.intersects(clickArea);
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

    /**
     * Scales the shape and plotted points with respect to a given point.
     * 
     * @param scalingPoint The point to scale with respect to.
     * @param sx           The scaling factor along the x-axis.
     * @param sy           The scaling factor along the y-axis.
     */
    public void scale(Point2D scalingPoint, double sx, double sy) {
        AffineTransform transform = new AffineTransform();

        // Translate shape to origin
        transform.translate(scalingPoint.getX(), scalingPoint.getY());

        // Apply scaling
        transform.scale(sx, sy);

        // Translate shape back to original position
        transform.translate(-scalingPoint.getX(), -scalingPoint.getY());

        final Shape oldShape = shape;
        Shape transformedShape;

        if (oldShape instanceof Polygon) {
            // If the shape is a polygon, ensure the transformed shape is also a polygon
            transformedShape = PolygonCalculator.transformPolygon((Polygon) oldShape, transform);
        } else {
            transformedShape = transform.createTransformedShape(oldShape);
        }

        // Replace old shape with transformed shape
        setShape(transformedShape);

        // Scale plotted points
        for (int i = 0; i < plottedPoints.size(); i++) {
            Point2D oldPoint = plottedPoints.get(i);
            double newX = scalingPoint.getX() + (oldPoint.getX() - scalingPoint.getX()) * sx;
            double newY = scalingPoint.getY() + (oldPoint.getY() - scalingPoint.getY()) * sy;
            plottedPoints.set(i, new Point2D.Double(newX, newY));
        }
    }
}
