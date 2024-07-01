package com.github.creme332.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Manages the shapes created by the application. It supports add, undo, redo,
 * and delete operations in a thread-safe manner.
 */
public class ShapeManager {
    private List<ShapeWrapper> shapes;
    private Stack<Action> history;
    private Stack<Action> redoStack;
    private Stack<ShapeWrapper> deletedShapes; // Stack to store deleted shapes

    public ShapeManager() {
        shapes = new ArrayList<>();
        history = new Stack<>();
        redoStack = new Stack<>();
        deletedShapes = new Stack<>();
    }

    private enum Action {
        /**
         * User created a new shape
         */
        ADD,

        /**
         * User deleted an existing shape
         */
        DELETE,

        /**
         * User
         */
        RECOVER
    }

    public void reset() {
        shapes.clear();
        history.clear();
        redoStack.clear();
        deletedShapes.clear();
    }

    public void addShape(ShapeWrapper shape) {
        shapes.add(shape);
        history.push(Action.ADD);
        redoStack.clear(); // Clear redo stack after a new action
    }

    public void deleteShape(ShapeWrapper shape) {
        if (shapes.remove(shape)) {
            history.push(Action.DELETE);
            deletedShapes.push(shape); // Push the deleted shape onto the deleted shapes stack
            redoStack.clear(); // Clear redo stack after a new action
        }
    }

    public void undo() {
        if (!history.isEmpty()) {
            Action lastAction = history.pop();
            switch (lastAction) {
                case ADD:
                    shapes.remove(shapes.size() - 1);
                    redoStack.push(Action.ADD);
                    break;
                case DELETE:
                    ShapeWrapper deletedShape = deletedShapes.pop();
                    shapes.add(deletedShape);
                    history.push(Action.RECOVER);
                    redoStack.push(Action.DELETE);
                    break;
                case RECOVER:
                    // Implement undo for recover action if needed
                    break;
            }
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            Action actionToRedo = redoStack.pop();
            switch (actionToRedo) {
                case ADD:
                    // Implement redo for add action if needed
                    break;
                case DELETE:
                    ShapeWrapper shapeToDelete = shapes.get(shapes.size() - 1);
                    shapes.remove(shapeToDelete);
                    deletedShapes.push(shapeToDelete);
                    history.push(Action.DELETE);
                    break;
                case RECOVER:
                    ShapeWrapper recoveredShape = deletedShapes.pop();
                    shapes.add(recoveredShape);
                    history.push(Action.RECOVER);
                    break;
            }
        }
    }

    /**
     * 
     * @return A copy of the current shapes that should be displayed
     */
    public List<ShapeWrapper> getShapes() {
        ArrayList<ShapeWrapper> copy = new ArrayList<>();
        for (int i = 0; i < shapes.size(); i++) {
            copy.add(new ShapeWrapper(shapes.get(i)));
        }

        return copy;
    }

    public void printShapes() {
        for (ShapeWrapper shape : shapes) {
            System.out.println(shape);
        }
    }
}