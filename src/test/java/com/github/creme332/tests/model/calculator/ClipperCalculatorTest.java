package com.github.creme332.tests.model.calculator;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.github.creme332.model.calculator.ClipperCalculator;

public class ClipperCalculatorTest {

    @Test
    public void testClipLineCompletelyInside() {
        double[][] result = ClipperCalculator.clip(2, 2, 4, 4, 1, 1, 5, 5);
        assertArrayEquals(new double[][]{{2, 2}, {4, 4}}, result);
    }

    @Test
    public void testClipLinePartiallyInside() {
        double[][] result = ClipperCalculator.clip(0, 0, 5, 5, 1, 1, 3, 3);
        assertArrayEquals(new double[][]{{1, 1}, {3, 3}}, result);
    }

    @Test
    public void testClipLineOutside() {
        double[][] result = ClipperCalculator.clip(-1, -1, 0, 0, 1, 1, 5, 5);
        assertNull(result);
    }

    @Test
    public void testClipLineIntersectingBorder() {
        double[][] result = ClipperCalculator.clip(0, 0, 6, 6, 1, 1, 5, 5);
        assertArrayEquals(new double[][]{{1, 1}, {5, 5}}, result);
    }

    @Test
    public void testClipLineVertical() {
        double[][] result = ClipperCalculator.clip(2, 0, 2, 6, 1, 1, 5, 5);
        assertArrayEquals(new double[][]{{2, 1}, {2, 5}}, result);
    }

    @Test
    public void testClipLineHorizontal() {
        double[][] result = ClipperCalculator.clip(0, 2, 6, 2, 1, 1, 5, 5);
        assertArrayEquals(new double[][]{{1, 2}, {5, 2}}, result);
    }
}
