package com.github.creme332.algorithms;

public class EllipseAlgorithm {
    
   /**
   * This method draws an ellipse using the Midpoint Ellipse Algorithm.
   *
   * @param centerX  X-coordinate of the ellipse's center
   * @param centerY  Y-coordinate of the ellipse's center
   * @param rx        Radius of the ellipse along the X-axis (horizontal radius)
   * @param ry        Radius of the ellipse along the Y-axis (vertical radius)
   * @return A 2D integer array representing the pixels of the ellipse
   */
  public static int[][] drawEllipse(int centerX, int centerY, int rx, int ry) {
    int width = 2 * rx + 1;
    int height = 2 * ry + 1;
    int[][] pixels = new int[height][width];

    // Handle special cases: zero radius or negative radius
    if (rx <= 0 || ry <= 0) {
      return pixels; // Empty array for invalid radius
    }

    int x = 0;
    int y = ry;

    int p = ry * ry - rx * rx * ry + rx * rx / 2; // Initial decision parameter

    // Region 1 - Iterate based on y until decision parameter changes sign
    while (ry * ry >= x * x) {
      pixels[centerY + y][centerX + x] = 1; // Set pixel
      pixels[centerY - y][centerX + x] = 1; // Reflect across y-axis
      pixels[centerY + y][centerX - x] = 1; // Reflect across x-axis
      pixels[centerY - y][centerX - x] = 1; // Reflect across both axes

      x++;
      if (p < 0) {
        p += 2 * ry * x;
      } else {
        p += 2 * ry * x - 2 * rx * rx;
        y--;
      }
    }

    // Region 2 - Iterate based on x until decision parameter changes sign again
    while (x < rx) {
      pixels[centerY + y][centerX + x] = 1; // Set pixel
      pixels[centerY - y][centerX + x] = 1; // Reflect across y-axis
      pixels[centerY + y][centerX - x] = 1; // Reflect across x-axis
      pixels[centerY - y][centerX - x] = 1; // Reflect across both axes

      y--;
      if (p > 0) {
        p -= 2 * rx * x;
      } else {
        p -= 2 * rx * x + 2 * ry * ry;
        x++;
      }
    }
    return pixels;
  }
}
