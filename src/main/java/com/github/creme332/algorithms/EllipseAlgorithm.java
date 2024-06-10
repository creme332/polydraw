package com.github.creme332.algorithms;

import java.util.ArrayList;
import java.util.List;

public class EllipseAlgorithm {

  private EllipseAlgorithm() {

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
  public static List<int[]> drawEllipse(int centerX, int centerY, int rx, int ry) {
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
