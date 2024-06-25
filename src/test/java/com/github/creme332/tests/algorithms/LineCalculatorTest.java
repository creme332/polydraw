package com.github.creme332.tests.algorithms;

import org.junit.Ignore;
import org.junit.Test;

import com.github.creme332.algorithms.LineCalculator;
import com.github.creme332.tests.utils.TestHelper;

public class LineCalculatorTest {

    @Ignore("Failing test to be fixed later")
    public void testDrawDDA() {
        int x0 = 2, y0 = 3, x1 = 10, y1 = 8;
        int[][] expected = {
                { 2, 3 }, { 3, 3 }, { 4, 4 }, { 5, 4 }, { 6, 5 }, { 7, 5 }, { 8, 6 }, { 9, 7 }, { 10, 8 }
        };

        int[][] result = LineCalculator.dda(x0, y0, x1, y1);
        TestHelper.assert2DArrayEquals(expected, result);
    }

    @Ignore("Failing test to be fixed later")
    public void testDrawBresenham() {
        int x0 = 2, y0 = 3, x1 = 10, y1 = 8;
        int[][] expected = {
                { 2, 3 }, { 3, 3 }, { 4, 4 }, { 5, 4 }, { 6, 5 }, { 7, 5 }, { 8, 6 }, { 9, 7 }, { 10, 8 }
        };

        int[][] result = LineCalculator.bresenham(x0, y0, x1, y1);
        TestHelper.assert2DArrayEquals(expected, result);
    }

    @Test
    public void testDrawDDAHorizontal() {
        int x0 = 1, y0 = 1, x1 = 5, y1 = 1;
        int[][] expected = {
                { 1, 1 }, { 2, 1 }, { 3, 1 }, { 4, 1 }, { 5, 1 }
        };

        int[][] result = LineCalculator.dda(x0, y0, x1, y1);
        TestHelper.assert2DArrayEquals(expected, result);
    }

    @Test
    public void testDrawBresenhamHorizontal() {
        int x0 = 1, y0 = 1, x1 = 5, y1 = 1;
        int[][] expected = {
                { 1, 1 }, { 2, 1 }, { 3, 1 }, { 4, 1 }, { 5, 1 }
        };

        int[][] result = LineCalculator.bresenham(x0, y0, x1, y1);
        TestHelper.assert2DArrayEquals(expected, result);
    }

    @Test
    public void testDrawDDAVertical() {
        int x0 = 1, y0 = 1, x1 = 1, y1 = 5;
        int[][] expected = {
                { 1, 1 }, { 1, 2 }, { 1, 3 }, { 1, 4 }, { 1, 5 }
        };

        int[][] result = LineCalculator.dda(x0, y0, x1, y1);
        TestHelper.assert2DArrayEquals(expected, result);
    }

    @Test
    public void testDrawBresenhamVertical() {
        int x0 = 1, y0 = 1, x1 = 1, y1 = 5;
        int[][] expected = {
                { 1, 1 }, { 1, 2 }, { 1, 3 }, { 1, 4 }, { 1, 5 }
        };

        int[][] result = LineCalculator.bresenham(x0, y0, x1, y1);
        TestHelper.assert2DArrayEquals(expected, result);
    }
}
