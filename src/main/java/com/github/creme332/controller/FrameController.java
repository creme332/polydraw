package com.github.creme332.controller;

import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Timer;
import java.util.TimerTask;

import com.github.creme332.model.AppState;
import com.github.creme332.view.Frame;

public class FrameController implements PropertyChangeListener {
    private Frame frame;
    private AppState app;

    public FrameController(AppState app, Frame frame) {
        this.frame = frame;
        this.app = app;

        app.addPropertyChangeListener(this);

        app.setSideBarVisibility(false);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // get current frame dimensions
                int width = frame.getWidth();
                int height = frame.getHeight();

                // get menubar dimensions
                int menuWidth = frame.getMyMenuBar().getWidth();
                int menuHeight = frame.getMyMenuBar().getHeight();

                int sideWidth = Math.min(400, width / 3);
                System.out.println("Frame size: " + width + " x " + height);
                frame.getMainPanel().setPreferredSize(new Dimension(menuWidth, height - menuHeight));
                frame.setPreferredSize(new Dimension(sideWidth, height - menuHeight));
            }
        });
    }

    public void playStartAnimation() {
        Timer timer = new Timer();
        TimerTask showMainScreen;
        final long animationDuration = 800; // ms

        frame.toggleMenuBarVisibility(false);
        frame.showSplashScreen();

        // show main screen when timer has elapsed
        showMainScreen = new TimerTask() {
            @Override
            public void run() {
                frame.toggleMenuBarVisibility(true);
                frame.showMainScreen();
                timer.cancel();
                timer.purge();
            }
        };
        timer.schedule(showMainScreen, animationDuration);
    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        String propertyName = e.getPropertyName();
        System.out.println("received notf");
        if ("sidebarVisibility".equals(propertyName)) {
            frame.toggleSideBarVisibility((boolean) e.getNewValue());
        }
    }
}
