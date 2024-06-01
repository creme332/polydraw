package com.github.creme332.controller;

import com.github.creme332.model.AppState;
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

    private FrameController frameController;
    private CanvasController canvasController;
    private MenuBarController menuController;
    private SideMenuController sideController;
    private ToolBarController toolbarController;

    public Controller() {
        try {
            toolbar = new Toolbar();
            toolbarController = new ToolBarController(toolbar);

            canvas = new Canvas(toolbar);
            canvasController = new CanvasController(canvas);

            frame = new Frame(canvas);
            frameController = new FrameController(app, frame);

            menuBar = frame.getMyMenuBar();
            menuController = new MenuBarController(app, menuBar);

            sideController = new SideMenuController(app, frame.getSideMenuPanel());
        } catch (InvalidIconSizeException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        } catch (InvalidPathException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        frameController.playStartAnimation();
    }

}