package com.github.creme332.controller;

import com.github.creme332.model.AppState;
import com.github.creme332.utils.exception.InvalidIconSizeException;
import com.github.creme332.utils.exception.InvalidPathException;
import com.github.creme332.view.*;

/**
 * Main controller for application responsible for creating application model
 * and controllers of screens.
 */
public class Controller {

    public Controller() {
        AppState app = new AppState();
        FrameController frameController;
        try {
            Frame frame = new Frame(app);
            frameController = new FrameController(app, frame);

            // create controllers for views
            new MenuBarController(app, frame.getMyMenuBar());
            new ZoomPanelController(app.getCanvasModel(), frame.getCanvasConsole().getZoomPanel());
            new CanvasController(app, frame.getMyCanvas());
            new TutorialScreenController(app, frame.getTutorialCenter());
            new ToolBarController(frame.getCanvasConsole().getToolbar(), app.getCanvasModel());
            new SideMenuController(app, frame.getCanvasConsole().getSidebar());

            // play start animation
            frameController.playStartAnimation();
        } catch (InvalidIconSizeException | InvalidPathException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }
}
