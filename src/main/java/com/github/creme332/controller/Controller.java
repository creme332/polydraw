package com.github.creme332.controller;

import com.github.creme332.model.AppState;
import com.github.creme332.model.CanvasModel;
import com.github.creme332.utils.exception.InvalidIconSizeException;
import com.github.creme332.utils.exception.InvalidPathException;
import com.github.creme332.view.*;

/**
 * Controls all the logic in the application by linking views and model.
 */
public class Controller {
    AppState app = new AppState();

    private Frame frame; // frame of app
    private MenuBar menuBar;
    private Canvas canvas;
    private Toolbar toolbar;
    private CanvasModel model;

    private FrameController frameController;

    public Controller() {
        try {
            toolbar = new Toolbar();
            new ToolBarController(toolbar);

            canvas = new Canvas(toolbar,model);
            new CanvasController(canvas);

            frame = new Frame(canvas);
            frameController = new FrameController(app, frame);

            menuBar = frame.getMyMenuBar();
            new MenuBarController(app, menuBar);

            new SideMenuController(app, frame.getSideMenuPanel());
        } catch (InvalidIconSizeException | InvalidPathException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }  catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        frameController.playStartAnimation();
    }

}