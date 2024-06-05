package com.github.creme332.algorithms;

import java.util.ArrayList;
import java.util.List;

public class EllipseAlgorithm {

  /**
   * This method draws an ellipse using the Midpoint Ellipse Algorithm.
   *
   * @param centerX  X-coordinate of the ellipse's center
   * @param centerY  Y-coordinate of the ellipse's center
   * @param rx       Radius of the ellipse along the X-axis (horizontal radius)
   * @param ry       Radius of the ellipse along the Y-axis (vertical radius)
   * @return A list of integer arrays representing the pixels of the ellipse
   * @throws IllegalArgumentException if rx or ry are non-positive
   */
  public static List<int[]> drawEllipse(int centerX, int centerY, int rx, int ry) {
    if (rx <= 0 || ry <= 0) {
      throw new IllegalArgumentException("Radii must be positive values.");
    }

    List<int[]> pixels = new ArrayList<>();

    int x = 0;
    int y = ry;

    int p = ry * ry - rx * rx * ry + (rx * rx) / 4; // Initial decision parameter

    // Region 1 - Iterate based on y until decision parameter changes sign
    while (ry * ry * x <= rx * rx * y) {
      setPixel(pixels, centerX, centerY, x, y);
      x++;
      if (p < 0) {
        p += 2 * ry * ry * x + ry * ry;
      } else {
        y--;
        p += 2 * ry * ry * x - 2 * rx * rx * y + ry * ry;
      }
    }

    // Region 2 - Iterate based on x until decision parameter changes sign again
    p = (int) Math.round(ry * ry * (x + 0.5) * (x + 0.5) + rx * rx * (y - 1) * (y - 1) - rx * rx * ry * ry);
    while (y >= 0) {
      setPixel(pixels, centerX, centerY, x, y);
      y--;
      if (p > 0) {
        p -= 2 * rx * rx * y + rx * rx;
      } else {
        x++;
        p += 2 * ry * ry * x - 2 * rx * rx * y + rx * rx;
      }
    }
    return pixels;
  }

  private static void setPixel(List<int[]> pixels, int centerX, int centerY, int x, int y) {
    pixels.add(new int[] {centerX + x, centerY + y});
    pixels.add(new int[] {centerX - x, centerY + y});
    pixels.add(new int[] {centerX + x, centerY - y});
    pixels.add(new int[] {centerX - x, centerY - y});
  }
}
