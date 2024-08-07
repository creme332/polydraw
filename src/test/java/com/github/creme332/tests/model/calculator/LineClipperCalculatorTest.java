package com.github.creme332.tests.model.calculator;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import com.github.creme332.model.calculator.LineClipperCalculator;

public class LineClipperCalculatorTest {

    @Test
    public void testClipLineCompletelyInside() {
        double[][] result = LineClipperCalculator.clip(2, 2, 4, 4, 1, 1, 5, 5);
        assertArrayEquals(new double[][] { { 2, 2 }, { 4, 4 } }, result);
    }

    @Test
    public void testClipLinePartiallyInside() {
        double[][] result = LineClipperCalculator.clip(0, 0, 5, 5, 1, 1, 3, 3);
        assertArrayEquals(new double[][] { { 1, 1 }, { 3, 3 } }, result);
    }

    @Test
    public void testClipLineOutside() {
        double[][] result = LineClipperCalculator.clip(-1, -1, 0, 0, 1, 1, 5, 5);
        assertArrayEquals(result, new double[0][0]);
    }

    @Test
    public void testClipLineIntersectingBorder() {
        double[][] result = LineClipperCalculator.clip(0, 0, 6, 6, 1, 1, 5, 5);
        assertArrayEquals(new double[][] { { 1, 1 }, { 5, 5 } }, result);
    }

    @Test
    public void testClipLineVertical() {
        double[][] result = LineClipperCalculator.clip(2, 0, 2, 6, 1, 1, 5, 5);
        assertArrayEquals(new double[][] { { 2, 1 }, { 2, 5 } }, result);
    }

    @Test
    public void testClipLineHorizontal() {
        double[][] result = LineClipperCalculator.clip(0, 2, 6, 2, 1, 1, 5, 5);
        assertArrayEquals(new double[][] { { 1, 2 }, { 5, 2 } }, result);
    }
}
