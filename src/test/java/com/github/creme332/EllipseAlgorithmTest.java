package com.github.creme332;

import org.junit.Test;
import static org.junit.Assert.*;
import com.github.creme332.algorithms.EllipseAlgorithm;


public class EllipseAlgorithmTest {

  @Test
  public void testValidEllipse() {
    int[][] pixels = EllipseAlgorithm.drawEllipse(10, 10, 5, 3);

    // Verify pixels along the major axis (horizontal in this case)
    for (int y = 7; y <= 13; y++) {
      assertEquals(1, pixels[y][10]); // Check pixels at center X
    }

    // Verify pixels along the minor axis (vertical)
    for (int x = 8; x <= 12; x++) {
      assertEquals(1, pixels[10][x]); // Check pixels at center Y
    }

  }

  @Test
  public void testZeroRadii() {
    int[][] pixels = EllipseAlgorithm.drawEllipse(10, 10, 0, 0);
    assertEquals(0, pixels.length);
  }

  @Test
  public void testNegativeRadii() {
    int[][] pixels = EllipseAlgorithm.drawEllipse(10, 10, -5, -3);
    assertEquals(0, pixels.length);
  }

  @Test
  public void testHorizontalEllipse() {
    int[][] pixels = EllipseAlgorithm.drawEllipse(10, 10, 7, 3);

    // Verify wider spread along X-axis compared to Y-axis
    int xSpread = 0;
    for (int y = 7; y <= 13; y++) {
      if (pixels[y][10] == 1) {
        xSpread++;
      }
    }
    assertTrue(xSpread > pixels.length / 2); // More pixels set on X-axis
  }

  @Test
  public void testVerticalEllipse() {
    int[][] pixels = EllipseAlgorithm.drawEllipse(10, 10, 3, 7);

    // Verify wider spread along Y-axis compared to X-axis
    int ySpread = 0;
    for (int x = 8; x <= 12; x++) {
      if (pixels[10][x] == 1) {
        ySpread++;
      }
    }
    assertTrue(ySpread > pixels.length / 2); // More pixels set on Y-axis
  }

  @Test
  public void testCenteredEllipse() {
    int[][] pixels = EllipseAlgorithm.drawEllipse(5, 7, 3, 2);

    // Verify pixels are set relative to the given center coordinates
    assertEquals(1, pixels[9][5]); // Check pixel at (5, 7)
    assertEquals(1, pixels[7][7]); // Check pixel at (5, 7) with Y offset
  }

  @Test
  public void testSmallRadii() {
    int[][] pixels = EllipseAlgorithm.drawEllipse(10, 10, 1, 1);

    // Verify single pixel set at the center
    assertEquals(1, pixels[10][10]);
    assertEquals(1, pixels.length); // Ensure single row and column
  }

}
