package com.github.creme332.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EllipseCalculator {

  /**
   * A map that stores calculated coordinates of pixels in first quadrant of
   * ellipse of different radii centered at origin.
   * 
   */
  private HashMap<Map.Entry<Integer, Integer>, List<List<Integer>>> firstQuadrantCache;

  public EllipseCalculator() {
    firstQuadrantCache = new HashMap<>();
  }

  /**
   * Maps a point (x, y) in first quadrant to some other quadrant using 4-way
   * symmetry of ellipse.
   * 
   * @param x                   x-coordinate of a point in first quadrant.
   * @param y                   y-coordinate of a point in the first quadrant.
   * @param destinationQuadrant index of some other quadrant (one-based index).
   * @return x and y coordinates of point in new quadrant.
   */
  public static int[] transformPoint(int x, int y, int destinationQuadrant) {
    switch (destinationQuadrant) {
      case 1:
        return new int[] { x, y };
      case 2:
        return new int[] { x, -y };
      case 3:
        return new int[] { -x, -y };
      case 4:
        return new int[] { -x, y };
      default:
        break;
    }
    throw new IllegalArgumentException("Quadrant must be between 1 and 4.");
  }

  /**
   * Calculates coordinates of ellipse starting from top and moving clockwise.
   * Points are ordered clockwise. Duplicate points may occur at x==0 and y==0.
   * 
   * @param centerX x-coordinate of circle center
   * @param centerY y-coordinate of circle center
   * @param radius  radius of circle
   * @return 2 arrays. The first array is the list of x-coordinates and the second
   *         array is a list of y-coordinates.
   */
  public int[][] getOrderedPoints(int centerX, int centerY, int rx, int ry) {
    /**
     * Pixels in first octant of circle centered at origin.
     */
    final List<List<Integer>> firstQuadrantList = getFirstQuadrantPoints(rx, ry);

    /**
     * Number of pixels in first quadrant
     */
    final int firstQuadrantSize = firstQuadrantList.get(0).size();

    List<Integer> xPoints = new ArrayList<>();
    List<Integer> yPoints = new ArrayList<>();

    for (int quadrant = 1; quadrant <= 4; quadrant++) {
      for (int i = 0; i < firstQuadrantSize; i++) {
        int pixelIndex = (quadrant % 2 == 0) ? firstQuadrantSize - i - 1 : i;
        int x = firstQuadrantList.get(0).get(pixelIndex);
        int y = firstQuadrantList.get(1).get(pixelIndex);

        int[] transformedPoint = transformPoint(x, y, quadrant);
        xPoints.add(transformedPoint[0]);
        yPoints.add(transformedPoint[1]);
      }
    }

    // convert arrays to primitive arrays and apply translation based on circle
    // center coordinates
    int[] xArray = xPoints.stream().mapToInt(i -> i + centerX).toArray();
    int[] yArray = yPoints.stream().mapToInt(i -> i + centerY).toArray();

    return new int[][] { xArray, yArray };
  }

  /**
   * Calculates coordinates of circle starting from top and moving clockwise.
   * Points are ordered clockwise. Duplicate points may occur at x==0 and x==y.
   * 
   * @param centerX x-coordinate of circle center
   * @param centerY y-coordinate of circle center
   * @param radius  radius of circle
   * @return 2 arrays. The first array is the list of x-coordinates and the second
   *         array is a list of y-coordinates.
   */
  public List<List<Integer>> getFirstQuadrantPoints(int rx, int ry) {
    if (rx <= 0 || ry <= 0) {
      throw new IllegalArgumentException("Radii must be positive values.");
    }

    if (firstQuadrantCache.containsKey(Map.entry(rx, ry))) {
      return firstQuadrantCache.get(Map.entry(rx, ry));
    }

    List<Integer> xPoints = new ArrayList<>();
    List<Integer> yPoints = new ArrayList<>();

    int x = 0;
    int y = ry;

    int rx2 = rx * rx;
    int ry2 = ry * ry;
    int tworx2 = 2 * rx2;
    int twory2 = 2 * ry2;

    int p = (int) (ry2 - (rx2 * ry) + (0.25 * rx2)); // Initial decision parameter

    int px = 0;
    int py = tworx2 * y;

    // Region 1
    while (px < py) {
      xPoints.add(x);
      yPoints.add(y);

      x++;
      px += twory2;
      if (p < 0) {
        p += ry2 + px;
      } else {
        y--;
        py -= tworx2;
        p += ry2 + px - py;
      }
    }

    // Region 2
    p = (int) (ry2 * (x + 0.5) * (x + 0.5) + rx2 * (y - 1) * (y - 1) - rx2 * ry2);
    while (y >= 0) {
      xPoints.add(x);
      yPoints.add(y);

      y--;
      py -= tworx2;
      if (p > 0) {
        p += rx2 - py;
      } else {
        x++;
        px += twory2;
        p += rx2 - py + px;
      }
    }

    // save result to cache
    List<List<Integer>> result = new ArrayList<>();
    result.add(xPoints);
    result.add(yPoints);
    firstQuadrantCache.put(Map.entry(rx, ry), result);

    return result;
  }

  /**
   * This method calculates the pixel coordinates of an ellipse using the Midpoint
   * Ellipse Algorithm.
   *
   * @param centerX X-coordinate of the ellipse's center
   * @param centerY Y-coordinate of the ellipse's center
   * @param rx      Radius of the ellipse along the X-axis (horizontal radius)
   * @param ry      Radius of the ellipse along the Y-axis (vertical radius)
   * @return A list of integer arrays representing the x-y coordinates of each
   *         pixel of the ellipse
   * @throws IllegalArgumentException if rx or ry are non-positive
   */
  public static List<int[]> getAllPoints(int centerX, int centerY, int rx, int ry) {
    if (rx <= 0 || ry <= 0) {
      throw new IllegalArgumentException("Radii must be positive values.");
    }

    List<int[]> pixels = new ArrayList<>();

    int x = 0;
    int y = ry;

    int rx2 = rx * rx;
    int ry2 = ry * ry;
    int tworx2 = 2 * rx2;
    int twory2 = 2 * ry2;

    int p = (int) (ry2 - (rx2 * ry) + (0.25 * rx2)); // Initial decision parameter

    int px = 0;
    int py = tworx2 * y;

    // Region 1
    while (px < py) {
      addPixels(pixels, centerX, centerY, x, y);
      x++;
      px += twory2;
      if (p < 0) {
        p += ry2 + px;
      } else {
        y--;
        py -= tworx2;
        p += ry2 + px - py;
      }
    }

    // Region 2
    p = (int) (ry2 * (x + 0.5) * (x + 0.5) + rx2 * (y - 1) * (y - 1) - rx2 * ry2);
    while (y >= 0) {
      addPixels(pixels, centerX, centerY, x, y);
      y--;
      py -= tworx2;
      if (p > 0) {
        p += rx2 - py;
      } else {
        x++;
        px += twory2;
        p += rx2 - py + px;
      }
    }
    return pixels;
  }

  private static void addPixels(List<int[]> pixels, int centerX, int centerY, int x, int y) {

    // when x = 0, (-x, y)= (x, y) and (-x, -y) = (x, -y)
    // plot only pixels in 2 quadrants
    if (x == 0) {
      addPixel(pixels, centerX + x, centerY + y);
      addPixel(pixels, centerX + x, centerY - y);
      return;
    }

    // when y = 0, (x, -y)= (x, y) and (-x, y) = (-x, y)
    // plot only pixels in 2 quadrants
    if (y == 0) {
      addPixel(pixels, centerX + x, centerY + y);
      addPixel(pixels, centerX - x, centerY + y);
      return;
    }

    // else plot a pixel in each quadrant
    addPixel(pixels, centerX + x, centerY + y);
    addPixel(pixels, centerX + x, centerY - y);
    addPixel(pixels, centerX - x, centerY + y);
    addPixel(pixels, centerX - x, centerY - y);
  }

  private static void addPixel(List<int[]> pixels, int x, int y) {
    pixels.add(new int[] { x, y });
  }
}
