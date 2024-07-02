package com.github.creme332.tests.model;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.github.creme332.model.LineType;
import com.github.creme332.model.ShapeManager;
import com.github.creme332.model.ShapeWrapper;

public class ShapeManagerTest {

    ShapeManager shapeManager;

    private static int[] generateRandomArray(int size, int min, int max) {
        Random random = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt((max - min) + 1) + min;
        }
        return array;
    }

    private static ShapeWrapper generateRandomShape() {
        final int minVerticesCount = 2;
        final int maxVerticesCount = 10;

        ShapeWrapper newShape = new ShapeWrapper(Color.red, LineType.SOLID, 1);
        final int verticesCount = (int) (Math.random() * (maxVerticesCount - minVerticesCount + 1)) + minVerticesCount;

        newShape.setShape(new Polygon(generateRandomArray(verticesCount, -10, 10),
                generateRandomArray(verticesCount, -10, 10), verticesCount));

        return newShape;
    }

    public static boolean arePolygonsEqual(Polygon p1, Polygon p2) {
        if (p1.npoints != p2.npoints) {
            return false;
        }

        // Compare x coordinates
        if (!Arrays.equals(p1.xpoints, p2.xpoints)) {
            return false;
        }

        // Compare y coordinates
        if (!Arrays.equals(p1.ypoints, p2.ypoints)) {
            return false;
        }

        return true;
    }

    private static boolean compareArrays(List<ShapeWrapper> expectedShapes, List<ShapeWrapper> actualShapes) {
        if (expectedShapes.size() != actualShapes.size())
            return false;

        for (int i = 0; i < expectedShapes.size(); i++) {
            ShapeWrapper exp = expectedShapes.get(i);
            ShapeWrapper o = actualShapes.get(i);

            // compare shape coordinates
            if (!arePolygonsEqual((Polygon) exp.getShape(), (Polygon) o.getShape())) {
                return false;
            }
        }

        return true;
    }

    @Before
    public void beforeEachTestMethod() {
        shapeManager = new ShapeManager();
    }

    @Test
    public void testAddShape() {
        ShapeWrapper shape1 = generateRandomShape();
        ShapeWrapper shape2 = generateRandomShape();
        List<ShapeWrapper> expectedShapes = new ArrayList<>();

        shapeManager.addShape(shape1);
        expectedShapes.add(shape1);

        shapeManager.addShape(shape2);
        expectedShapes.add(shape2);

        assertFalse(shapeManager.isRedoPossible());
        assertTrue(shapeManager.isUndoPossible());
        assertTrue(compareArrays(expectedShapes, shapeManager.getShapes()));
    }

    @Test
    public void testDeleteShape() {
        ShapeWrapper shape1 = generateRandomShape();

        shapeManager.addShape(shape1);
        shapeManager.deleteShape(0);

        assertFalse(shapeManager.isRedoPossible());
        assertTrue(shapeManager.isUndoPossible());
        assertTrue(compareArrays(new ArrayList<>(), shapeManager.getShapes()));
    }

    @Test
    public void testComplexSequence() {
        ShapeWrapper shape1 = generateRandomShape();
        ShapeWrapper shape2 = generateRandomShape();
        ShapeWrapper shape3 = generateRandomShape();

        /**
         * Add shape 1.
         * 
         * New state of ShapeManager:
         * 
         * shapes:
         * [1]
         * 
         * undoStack:
         * <<1, add>>
         * 
         * redoStack:
         * <>
         */
        shapeManager.addShape(shape1);
        assertFalse(shapeManager.isRedoPossible());
        assertTrue(shapeManager.isUndoPossible());
        assertTrue(compareArrays(new ArrayList<>(Arrays.asList(shape1)), shapeManager.getShapes()));

        /**
         * Undo.
         * 
         * New state of ShapeManager:
         * 
         * shapes:
         * []
         * 
         * undoStack:
         * <>
         * 
         * redoStack:
         * <<1, add>>
         */
        shapeManager.undo();
        assertTrue(shapeManager.isRedoPossible());
        assertFalse(shapeManager.isUndoPossible());
        assertTrue(compareArrays(new ArrayList<>(), shapeManager.getShapes()));

        /**
         * Redo.
         * 
         * New state of ShapeManager:
         * 
         * shapes:
         * [1]
         * 
         * undoStack:
         * <<1, add>>
         * 
         * redoStack:
         * <>
         */
        shapeManager.redo();
        assertFalse(shapeManager.isRedoPossible());
        assertTrue(shapeManager.isUndoPossible());
        assertTrue(compareArrays(new ArrayList<>(Arrays.asList(shape1)), shapeManager.getShapes()));

        /**
         * Add shape 2.
         * 
         * New state of ShapeManager:
         * 
         * shapes:
         * [1, 2]
         * 
         * undoStack:
         * <<2, add>, <1, add>>
         * 
         * redoStack:
         * <>
         */
        shapeManager.addShape(shape2);
        assertFalse(shapeManager.isRedoPossible());
        assertTrue(shapeManager.isUndoPossible());
        assertTrue(compareArrays(new ArrayList<>(Arrays.asList(shape1, shape2)), shapeManager.getShapes()));

        /**
         * Delete shape 1.
         * 
         * New state of ShapeManager:
         * 
         * shapes:
         * [2]
         * 
         * undoStack:
         * <<1, delete>, <2, add>, <1, add>>
         * 
         * redoStack:
         * <>
         */
        shapeManager.deleteShape(0);
        assertFalse(shapeManager.isRedoPossible());
        assertTrue(shapeManager.isUndoPossible());
        assertTrue(compareArrays(new ArrayList<>(Arrays.asList(shape2)), shapeManager.getShapes()));

        /**
         * Undo => Restore shape 1.
         * 
         * New state of ShapeManager:
         * 
         * shapes:
         * [2, 1]
         * 
         * undoStack:
         * <<2, add>, <1, add>>
         * 
         * redoStack:
         * <<1, delete>>
         */
        shapeManager.undo();
        assertTrue(shapeManager.isRedoPossible());
        assertTrue(shapeManager.isUndoPossible());
        assertTrue(compareArrays(new ArrayList<>(Arrays.asList(shape2, shape1)), shapeManager.getShapes()));

        /**
         * Undo.
         * 
         * New state of ShapeManager:
         * 
         * shapes:
         * [1]
         * 
         * undoStack:
         * <<1, add>>
         * 
         * redoStack:
         * <<2, add>, <1, delete>>
         */
        shapeManager.undo();
        assertTrue(shapeManager.isRedoPossible());
        assertTrue(shapeManager.isUndoPossible());
        assertTrue(compareArrays(new ArrayList<>(Arrays.asList(shape1)), shapeManager.getShapes()));

        /**
         * Add shape 3.
         * 
         * New state of ShapeManager:
         * 
         * shapes:
         * [1, 3]
         * 
         * undoStack:
         * <<3, add>, <1, add>>
         * 
         * redoStack:
         * <>
         */
        shapeManager.addShape(shape3);
        assertFalse(shapeManager.isRedoPossible());
        assertTrue(shapeManager.isUndoPossible());
        assertTrue(compareArrays(new ArrayList<>(Arrays.asList(shape1, shape3)), shapeManager.getShapes()));
    }
}
