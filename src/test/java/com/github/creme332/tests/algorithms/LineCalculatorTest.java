package com.github.creme332.tests.algorithms;
import org.junit.Test;

import com.github.creme332.algorithms.LineCalculator;
import com.github.creme332.tests.utils.TestHelper;

public class LineCalculatorTest {

    @Test
    public void testDrawDDA() {
        int x0 = 2, y0 = 3, x1 = 10, y1 = 8;
        int[][] expected = {
            {2, 3},
            {3, 3},
            {4, 4},
            {5, 4},
            {6, 5},
            {7, 5},
            {8, 6},
            {9, 7},
            {10, 8}
        };

        int[][] result = LineCalculator.dda(x0, y0, x1, y1);
        TestHelper.assert2DArrayEquals(expected, result);
    }

    @Test
    public void testDrawBresenham() {
        int x0 = 2, y0 = 3, x1 = 10, y1 = 8;
        int[][] expected = {
            {2, 3},
            {3, 3},
            {4, 4},
            {5, 4},
            {6, 5},
            {7, 5},
            {8, 6},
            {9, 7},
            {10, 8}
        };
        int[][] result = LineCalculator.bresenham(x0, y0, x1, y1);
        TestHelper.assert2DArrayEquals(expected, result);
    }

    @Test
    public void testDrawDDAHorizontal() {
        int x0 = 1, y0 = 1, x1 = 5, y1 = 1;
        int[][] expected = {
            {1, 1},
            {2, 1},
            {3, 1},
            {4, 1},
            {5, 1}
    };

        int[][] result = LineCalculator.dda(x0, y0, x1, y1);
        TestHelper.assert2DArrayEquals(expected, result);
    }

    @Test
    public void testDrawBresenhamHorizontal() {
        int x0 = 1, y0 = 1, x1 = 5, y1 = 1;
        int[][] expected = {
            {1, 1},
            {2, 1},
            {3, 1},
            {4, 1},
            {5, 1}
    };

        int[][] result = LineCalculator.bresenham(x0, y0, x1, y1);
        TestHelper.assert2DArrayEquals(expected, result);
    }

    @Test
    public void testDrawDDAVertical() {
        int x0 = 1, y0 = 1, x1 = 1, y1 = 5;
        int[][] expected = {
            {1, 1},
            {1, 2},
            {1, 3},
            {1, 4},
            {1, 5}
    };

        int[][] result = LineCalculator.dda(x0, y0, x1, y1);
        TestHelper.assert2DArrayEquals(expected, result);
    }

    @Test
    public void testDrawBresenhamVertical() {
        int x0 = 1, y0 = 1, x1 = 1, y1 = 5;
        int[][] expected = {
            {1, 1},
            {1, 2},
            {1, 3},
            {1, 4},
            {1, 5}
    };

        int[][] result = LineCalculator.bresenham(x0, y0, x1, y1);
        TestHelper.assert2DArrayEquals(expected, result);
    }
    
    @Test
    public void testDrawDDANegativeGradient() {
        int x0 = 10, y0 = 10, x1 = 2, y1 = 3;
        int[][] expected = {
            {10, 10},
            {9, 9},
            {8, 8},
            {7, 7},
            {6, 6},
            {5, 5},
            {4, 4},
            {3, 3},
            {2, 3}
        };

        int[][] result = LineCalculator.dda(x0, y0, x1, y1);
        TestHelper.assert2DArrayEquals(expected, result);
    }

    @Test
    public void testDrawBresenhamNegativeGradient() {
        int x0 = 10, y0 = 10, x1 = 2, y1 = 3;
        int[][] expected = {
            {10, 10},
            {9, 9},
            {8, 8},
            {7, 7},
            {6, 6},
            {5, 5},
            {4, 4},
            {3, 3},
            {2, 3}
        };

        int[][] result = LineCalculator.bresenham(x0, y0, x1, y1);
        TestHelper.assert2DArrayEquals(expected, result);
    }

    @Test
    public void testDrawDDASecondQuadrant() {
        int x0 = -2, y0 = 3, x1 = -10, y1 = 8;
        int[][] expected = {
            {-2, 3},
            {-3, 3},
            {-4, 4},
            {-5, 4},
            {-6, 5},
            {-7, 5},
            {-8, 6},
            {-9, 7},
            {-10, 8}
        };

        int[][] result = LineCalculator.dda(x0, y0, x1, y1);
        TestHelper.assert2DArrayEquals(expected, result);
    }

    @Test
    public void testDrawBresenhamSecondQuadrant() {
        int x0 = -2, y0 = 3, x1 = -10, y1 = 8;
        int[][] expected = {
            {-2, 3},
            {-3, 3},
            {-4, 4},
            {-5, 4},
            {-6, 5},
            {-7, 5},
            {-8, 6},
            {-9, 7},
            {-10, 8}
        };

        int[][] result = LineCalculator.bresenham(x0, y0, x1, y1);
        TestHelper.assert2DArrayEquals(expected, result);
    }

    @Test
    public void testDrawDDAThirdQuadrant() {
        int x0 = -2, y0 = -3, x1 = -10, y1 = -8;
        int[][] expected = {
            {-2, -3},
            {-3, -3},
            {-4, -4},
            {-5, -4},
            {-6, -5},
            {-7, -5},
            {-8, -6},
            {-9, -7},
            {-10, -8}
        };

        int[][] result = LineCalculator.dda(x0, y0, x1, y1);
        TestHelper.assert2DArrayEquals(expected, result);
    }

    @Test
    public void testDrawBresenhamThirdQuadrant() {
        int x0 = -2, y0 = -3, x1 = -10, y1 = -8;
        int[][] expected = {
            {-2, -3},
            {-3, -3},
            {-4, -4},
            {-5, -4},
            {-6, -5},
            {-7, -5},
            {-8, -6},
            {-9, -7},
            {-10, -8}
        };

        int[][] result = LineCalculator.bresenham(x0, y0, x1, y1);
        TestHelper.assert2DArrayEquals(expected, result);
    }

    @Test
    public void testDrawDDAFourthQuadrant() {
        int x0 = 2, y0 = -3, x1 = 10, y1 = -8;
        int[][] expected = {
            {2, -3},
            {3, -4},
            {4, -4},
            {5, -5},
            {6, -5},
            {7, -6},
            {8, -6},
            {9, -7},
            {10, -8}
        };

        int[][] result = LineCalculator.dda(x0, y0, x1, y1);
        TestHelper.assert2DArrayEquals(expected, result);
    }

    @Test
    public void testDrawBresenhamFourthQuadrant() {
        int x0 = 2, y0 = -3, x1 = 10, y1 = -8;
        int[][] expected = {
            {2, -3},
            {3, -4},
            {4, -4},
            {5, -5},
            {6, -5},
            {7, -6},
            {8, -6},
            {9, -7},
            {10, -8}
        };

        int[][] result = LineCalculator.bresenham(x0, y0, x1, y1);
        TestHelper.assert2DArrayEquals(expected, result);
    }
}

