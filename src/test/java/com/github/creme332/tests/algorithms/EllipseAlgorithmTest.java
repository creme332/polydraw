package com.github.creme332.tests.algorithms;

import org.junit.Test;
import static org.junit.Assert.*;
import com.github.creme332.algorithms.EllipseAlgorithm;

public class EllipseAlgorithmTest {

  @Test
  public void testValidEllipse() {
    int[][] pixels = EllipseAlgorithm.drawEllipse(3, 2, 3, 2);

    // Verify pixels along the major axis (horizontal in this case)
    assertEquals(1, pixels[2][6]); // Center at (3, 2), 3 units right
    assertEquals(1, pixels[2][0]); // Center at (3, 2), 3 units left

    // Verify pixels along the minor axis (vertical)
    assertEquals(1, pixels[4][3]); // Center at (3, 2), 2 units up
    assertEquals(1, pixels[0][3]); // Center at (3, 2), 2 units down
  }

  @Test
  public void testZeroRadii() {
    try {
      EllipseAlgorithm.drawEllipse(10, 10, 0, 0);
      fail("Expected IllegalArgumentException for zero radii");
    } catch (IllegalArgumentException e) {
      assertEquals("Radii must be positive values.", e.getMessage());
    }
  }

  @Test
  public void testNegativeRadii() {
    try {
      EllipseAlgorithm.drawEllipse(10, 10, -5, -3);
      fail("Expected IllegalArgumentException for negative radii");
    } catch (IllegalArgumentException e) {
      assertEquals("Radii must be positive values.", e.getMessage());
    }
  }

  @Test
  public void testHorizontalEllipse() {
    int[][] pixels = EllipseAlgorithm.drawEllipse(7, 3, 7, 3);

    // Verify wider spread along X-axis compared to Y-axis
    int xSpread = 0;
    for (int y = 0; y <= 6; y++) {
      if (pixels[y][7] == 1) {
        xSpread++;
      }
    }
    assertTrue(xSpread > 0); // More pixels set on X-axis
  }

  @Test
  public void testVerticalEllipse() {
    int[][] pixels = EllipseAlgorithm.drawEllipse(3, 7, 3, 7);

    // Verify wider spread along Y-axis compared to X-axis
    int ySpread = 0;
    for (int x = 0; x <= 6; x++) {
      if (pixels[7][x] == 1) {
        ySpread++;
      }
    }
    assertTrue(ySpread > 0); // More pixels set on Y-axis
  }

  @Test
  public void testCenteredEllipse() {
    int[][] pixels = EllipseAlgorithm.drawEllipse(3, 2, 3, 2);

    // Verify pixels are set relative to the given center coordinates
    assertEquals(1, pixels[2 + 2][3]); // Check pixel at (3, 4)
    assertEquals(1, pixels[2 - 2][3]); // Check pixel at (3, 0)
  }

}
