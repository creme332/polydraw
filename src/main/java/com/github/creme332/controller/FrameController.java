package com.github.creme332.controller;

import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Timer;
import java.util.TimerTask;

import com.github.creme332.model.AppState;
import com.github.creme332.model.MenuModel;
import com.github.creme332.model.Screen;
import com.github.creme332.view.Frame;

public class FrameController implements PropertyChangeListener {
    private Frame frame;
    private AppState app;

    public FrameController(AppState app, Frame frame) {
        this.frame = frame;
        this.app = app;

        app.addPropertyChangeListener(this);

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
                frame.getMainPanel().setPreferredSize(new Dimension(menuWidth, height - menuHeight));
                frame.setPreferredSize(new Dimension(sideWidth, height - menuHeight));
            }
        });

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                MenuModel[] menuModels = app.getMenuModels();

                // if Esc is pressed, select mode in first menu
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    app.setMode(menuModels[0].getActiveItem().getMode());
                    return;
                }

                // if key number k is pressed, select mode in k-th menu where k = 1, 2, ...
                for (int i = 0; i < menuModels.length; i++) {
                    if (e.getKeyCode() == (KeyEvent.VK_1 + i))
                        app.setMode(menuModels[i].getActiveItem().getMode());
                }
            }
        });
    }

    public void playStartAnimation() {
        Timer timer = new Timer();
        TimerTask showNextScreen;
        final long animationDuration = 800; // ms

        frame.setMenuBarVisibility(false);
        frame.showScreen(Screen.SPLASH_SCREEN);

        // show next screen when timer has elapsed
        showNextScreen = new TimerTask() {
            @Override
            public void run() {
                if (app.getCurrentScreen().equals(Screen.MAIN_SCREEN)) {
                    frame.showScreen(Screen.MAIN_SCREEN);
                }

                if (app.getCurrentScreen().equals(Screen.TUTORIAL_SCREEN)) {
                    frame.showScreen(Screen.TUTORIAL_SCREEN);
                }

                timer.cancel();
                timer.purge();
            }
        };
        timer.schedule(showNextScreen, animationDuration);
    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        String property = e.getPropertyName();
        if ("screen".equals(property)) {
            if (Screen.MAIN_SCREEN.equals(e.getNewValue()))
                frame.showScreen(Screen.MAIN_SCREEN);

            if (Screen.TUTORIAL_SCREEN.equals(e.getNewValue()))
                frame.showScreen(Screen.TUTORIAL_SCREEN);
        }
    }

}
