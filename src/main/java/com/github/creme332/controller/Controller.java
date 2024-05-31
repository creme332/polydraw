package com.github.creme332.controller;

import java.util.Timer;
import java.util.TimerTask;

import com.github.creme332.utils.exception.InvalidIconSizeException;
import com.github.creme332.utils.exception.InvalidPathException;
import com.github.creme332.view.*;

/**
 * Controls all the logic in the application by linking views and model.
 */
public class Controller {
    private Frame frame; // frame of app
    private MenuBar menuBar;
    private Canvas canvas;
    private Toolbar toolbar;

    private CanvasController canvasController;

    private void playStartAnimation() {
        Timer timer = new Timer();
        TimerTask showMainScreen;
        final long animationDuration = 800; // ms

        menuBar.setVisible(false);
        frame.showSplashScreen();

        // show main screen when timer has elapsed
        showMainScreen = new TimerTask() {
            @Override
            public void run() {
                menuBar.setVisible(true);
                frame.showMainScreen();
                timer.cancel();
                timer.purge();
            }
        };
        timer.schedule(showMainScreen, animationDuration);
    }

    public Controller() {
        try {
            toolbar = new Toolbar();
            menuBar = new MenuBar();

            canvas = new Canvas(toolbar);
            canvasController = new CanvasController(canvas);


            frame = new Frame(menuBar, canvas);
            frame.showMainScreen();

        } catch (InvalidIconSizeException e) {
            // Handle invalid icon size
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        } catch (InvalidPathException e) {
            // Handle invalid path
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            // Handle any other unexpected exceptions
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        playStartAnimation();
    }

}