package com.github.creme332.tests.algorithms;

import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.github.creme332.algorithms.LineCalculator;

@RunWith(Parameterized.class)
public class LineCalculatorTest {
    private String description;
    private int x0;
    private int y0;
    private int x1;
    private int y1;
    private int[][] expected;

    public LineCalculatorTest(String description, int x0, int y0, int x1, int y1, int[][] expected) {
        this.description = description;
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;
        this.expected = expected;
    }

    @Parameters(name = "{index}: {0}")
    public static Collection<Object[]> dataBasedOnGradient() {
        return Arrays.asList(new Object[][] {
                // line with gradient 1
                { "0 < m < 1", 1, 1, 5, 5, new int[][] {
                        { 1, 2, 3, 4, 5 },
                        { 1, 2, 3, 4, 5 },
                } },

                // line with gradient between 0 and 1
                { "0 < m < 1", 2, 1, 8, 5, new int[][] {
                        { 2, 3, 4, 5, 6, 7, 8 },
                        { 1, 2, 2, 3, 4, 4, 5 }
                } },

                // line with gradient > 1
                { "m > 1", 3, 2, 7, 8, new int[][] {
                        { 3, 4, 4, 5, 6, 6, 7 },
                        { 2, 3, 4, 5, 6, 7, 8 }
                } },

                // line with gradient < -1
                { "m < -1", 2, 8, 5, 3, new int[][] {
                        { 2, 3, 3, 4, 4, 5 },
                        { 8, 7, 6, 5, 4, 3 }
                } },

                // horizontal line
                { "m = 0", 1, 1, 5, 1, new int[][] {
                        { 1, 2, 3, 4, 5 },
                        { 1, 1, 1, 1, 1 }
                } },

                // vertical line starting at origin
                { "m = INF", 0, 0, 0, 4, new int[][] {
                        { 0, 0, 0, 0, 0 },
                        { 0, 1, 2, 3, 4 }
                } },
        });
    }

    @Test
    public void testDDA() {
        System.out.println("DDA: " + description);
        int[][] result = LineCalculator.dda(x0, y0, x1, y1);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testBresenham() {
        System.out.println("Bresenham: " + description);
        int[][] result = LineCalculator.bresenham(x0, y0, x1, y1);
        assertArrayEquals(expected, result);
    }
}
