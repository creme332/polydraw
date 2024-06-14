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
import com.github.creme332.model.Mode;
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
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_1:
                    case KeyEvent.VK_ESCAPE:
                        app.setMode(Mode.MOVE_CANVAS);
                        break;
                    case KeyEvent.VK_2:
                        app.setMode(Mode.DRAW_LINE_DDA);
                        break;
                    case KeyEvent.VK_3:
                        app.setMode(Mode.DRAW_CIRCLE_DYNAMIC); // Chosen dynamic circle drawing mode
                        break;
                    case KeyEvent.VK_4:
                        app.setMode(Mode.DRAW_ELLIPSE);
                        break;
                    case KeyEvent.VK_5:
                        app.setMode(Mode.DRAW_POLYGON_DYNAMIC); // Chosen dynamic polygon drawing mode
                        break;
                    case KeyEvent.VK_6:
                        app.setMode(Mode.REFLECT_ABOUT_LINE); // Chosen line reflection mode
                        break;
                    case KeyEvent.VK_7:
                        app.setMode(Mode.MOVE_GRAPHICS_VIEW);
                        break;
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
