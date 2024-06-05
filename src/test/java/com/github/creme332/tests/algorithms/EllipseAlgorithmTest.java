package com.github.creme332.tests.algorithms;

import org.junit.Test;
import static org.junit.Assert.*;
import com.github.creme332.algorithms.EllipseAlgorithm;
import java.util.List;

public class EllipseAlgorithmTest {

  @Test
  public void testValidEllipse() {
    List<int[]> pixels = EllipseAlgorithm.drawEllipse(0, 0, 8, 6);

    int[][] expectedArray = {
      {0, 6}, {1, 6}, {2, 6}, {3, 6}, {4, 5}, {5, 5}, {6, 4}, {7, 3},
      {8, 2}, {8, 1}, {8, 0}, {0, -6}, {-1, 6}, {-2, 6}, {-3, 6},
      {-4, 5}, {-5, 5}, {-6, 4}, {-7, 3}, {-8, 2}, {-8, 1}, {-8, 0},
      {0, -6}, {1, -6}, {2, -6}, {3, -6}, {4, -5}, {5, -5}, {6, -4},
      {7, -3}, {8, -2}, {8, -1}, {-8, -2}, {-8, -1}, {-8, -0}
    };

    assertArrayEquals(expectedArray, pixels.toArray(new int[pixels.size()][]));
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
    List<int[]> pixels = EllipseAlgorithm.drawEllipse(0, 0, 7, 3);
    int[][] expectedArray = {
      {0, 3}, {1, 3}, {2, 3}, {3, 3}, {4, 2}, {5, 1}, {6, 0}, {-7, 0},
      {7, 0}, {-6, 0}, {6, 0}, {-5, 1}, {5, 1}, {-4, 2}, {4, 2},
      {-3, 3}, {3, 3}, {-2, 3}, {2, 3}, {-1, 3}, {1, 3}, {0, -3}
    };

    assertArrayEquals(expectedArray, pixels.toArray(new int[pixels.size()][]));
  }

  @Test
  public void testVerticalEllipse() {
    List<int[]> pixels = EllipseAlgorithm.drawEllipse(0, 0, 3, 7);
    int[][] expectedArray = {
      {0, 7}, {1, 6}, {2, 6}, {3, 5}, {-3, 5}, {3, 5}, {-2, 6}, {2, 6},
      {-1, 6}, {1, 6}, {-0, 7}, {0, -7}, {1, -6}, {2, -6}, {3, -5},
      {-3, -5}, {3, -5}, {-2, -6}, {2, -6}, {-1, -6}, {1, -6}, {0, -7}
    };

    assertArrayEquals(expectedArray, pixels.toArray(new int[pixels.size()][]));
  }

  @Test
  public void testCenteredEllipse() {
    List<int[]> pixels = EllipseAlgorithm.drawEllipse(0, 0, 3, 2);
    int[][] expectedArray = {
      {0, 2}, {1, 2}, {2, 1}, {3, 0}, {3, 0}, {3, 0}, {-3, 0},
      {2, -1}, {1, -2}, {0, -2}, {0, 2}, {-1, 2}, {-2, 1},
      {-3, 0}, {-2, -1}, {-1, -2}, {0, -2}
    };

    assertArrayEquals(expectedArray, pixels.toArray(new int[pixels.size()][]));
  }
  
}
