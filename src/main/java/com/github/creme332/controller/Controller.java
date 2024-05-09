package com.github.creme332.controller;

import java.util.Timer;
import java.util.TimerTask;

import com.github.creme332.view.*;

/**
 * Controls all the logic in the application by linking views and model.
 */
public class Controller {
    private Frame frame; // frame of app
    private MenuBar menuBar;
    private Canvas canvas;

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
            menuBar = new MenuBar();
        } catch (Exception e) {
            System.out.println(e);
        }
        canvas = new Canvas();
        frame = new Frame(menuBar, canvas);

        playStartAnimation();
    }

}