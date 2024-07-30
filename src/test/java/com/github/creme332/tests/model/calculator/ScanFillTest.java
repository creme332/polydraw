package com.github.creme332.tests.model.calculator;

import com.github.creme332.model.calculator.PolygonCalculator;
import com.github.creme332.tests.utils.TestHelper;
import org.junit.Test;

import java.awt.*;
import java.util.List;


public class ScanFillTest {

    @Test
    public void testTriangle() {
        Polygon triangle = new Polygon(
                new int[]{10, 20, 15},
                new int[]{10, 10, 20},
                3
        );

        PolygonCalculator calculator = new PolygonCalculator();
        List<Point> filledPixels = calculator.scanFill(triangle);

        int[][] expectedPixels = {
                {15, 10}, {15, 11}, {15, 12}, {15, 13}, {15, 14}, {15, 15}, {15, 16}, {15, 17}, {15, 18}, {15, 19},
                {14, 11}, {14, 12}, {14, 13}, {14, 14}, {14, 15}, {14, 16}, {14, 17}, {14, 18},
                {13, 12}, {13, 13}, {13, 14}, {13, 15}, {13, 16}, {13, 17},
                {12, 13}, {12, 14}, {12, 15}, {12, 16},
                {11, 14}, {11, 15}
        };

        TestHelper.assert2DArrayEquals(expectedPixels, convertToIntArray(filledPixels));
    }

    @Test
    public void testSquare() {
        Polygon square = new Polygon(
                new int[]{10, 20, 20, 10},
                new int[]{10, 10, 20, 20},
                4
        );

        PolygonCalculator calculator = new PolygonCalculator();
        List<Point> filledPixels = calculator.scanFill(square);

        int[][] expectedPixels = new int[121][2];
        int index = 0;
        for (int y = 10; y <= 20; y++) {
            for (int x = 10; x <= 20; x++) {
                expectedPixels[index++] = new int[]{x, y};
            }
        }

        TestHelper.assert2DArrayEquals(expectedPixels, convertToIntArray(filledPixels));
    }

    @Test
    public void testConcavePolygon() {
        Polygon concave = new Polygon(
                new int[]{10, 20, 15, 20, 10},
                new int[]{10, 10, 15, 20, 20},
                5
        );

        PolygonCalculator calculator = new PolygonCalculator();
        List<Point> filledPixels = calculator.scanFill(concave);

        int[][] expectedPixels = {
                {15, 10}, {15, 11}, {15, 12}, {15, 13}, {15, 14}, {15, 15}, {15, 16}, {15, 17}, {15, 18}, {15, 19},
                {14, 11}, {14, 12}, {14, 13}, {14, 14}, {14, 15}, {14, 16}, {14, 17}, {14, 18},
                {13, 12}, {13, 13}, {13, 14}, {13, 15}, {13, 16}, {13, 17},
                {12, 13}, {12, 14}, {12, 15}, {12, 16},
                {11, 14}, {11, 15},
                {16, 16}, {16, 17}, {16, 18}, {16, 19},
                {17, 17}, {17, 18}, {17, 19},
                {18, 18}, {18, 19}
        };

        TestHelper.assert2DArrayEquals(expectedPixels, convertToIntArray(filledPixels));
    }

    private int[][] convertToIntArray(List<Point> points) {
        int[][] result = new int[points.size()][2];
        for (int i = 0; i < points.size(); i++) {
            Point point = points.get(i);
            result[i][0] = point.x;
            result[i][1] = point.y;
        }
        return result;
    }
}


