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

    public Controller() {
        try {
            toolbar = new Toolbar();
            canvas = new Canvas(app.getCanvasModel(), toolbar);
            CanvasController canvasController = new CanvasController(app, canvas);

            menuBar = new MenuBar(app.getMenuModels(), canvasController);
            new MenuBarController(app, menuBar);

            new ToolBarController(toolbar);

            canvas = new Canvas(app.getCanvasModel(), toolbar);
            new CanvasController(app, canvas);

            TutorialCenter tutorialCenter = new TutorialCenter();
            new TutorialController(tutorialCenter);

            frame = new Frame(canvas, menuBar, tutorialCenter);
            frameController = new FrameController(app, frame);

            new SideMenuController(app, frame.getSideMenuPanel());
        } catch (InvalidIconSizeException | InvalidPathException e) {
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
