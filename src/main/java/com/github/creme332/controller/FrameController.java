package com.github.creme332.controller;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLayeredPane;

import com.github.creme332.model.AppState;
import com.github.creme332.model.MenuModel;
import com.github.creme332.model.Screen;
import com.github.creme332.view.Frame;
import com.github.creme332.view.MenuBar;
import com.github.creme332.view.SideMenuPanel;

/**
 * Controller responsible for switching screens and resizing components as
 * required.
 */
public class FrameController implements PropertyChangeListener {
    private Frame frame;
    private AppState app;

    public FrameController(AppState model, Frame frame) {
        this.frame = frame;
        this.app = model;

        // create controller for menubar of frame
        new MenuBarController(app, frame.getMyMenuBar());

        model.addPropertyChangeListener(this);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeEverything();

            }
        });

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                MenuModel[] menuModels = model.getMenuModels();

                // if Esc is pressed, select mode in first menu
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    model.setMode(menuModels[0].getActiveItem().getMode());
                    return;
                }

                // if key number k is pressed, select mode in k-th menu where k = 1, 2, ...

                for (int i = 0; i < menuModels.length; i++) {
                    if (e.getKeyCode() == (KeyEvent.VK_1 + i))

                        model.setMode(menuModels[i].getActiveItem().getMode());
                }
            }
        });
        // Set initial frame state
        if (model.isMaximizeFrame()) {
            frame.setExtendedState(frame.getExtendedState() |
                    java.awt.Frame.MAXIMIZED_BOTH);
        }
    }

    private void resizeEverything() {
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();
        System.out.format("Frame dimensions = %d x %d %n", frameWidth,
                frameHeight);

        Dimension mainDimension = new Dimension(frameWidth, frameHeight - MenuBar.HEIGHT);
        Rectangle mainBounds = new Rectangle(mainDimension);

        // update canvasScreen dimensions
        JLayeredPane canvasScreen = frame.getCanvasScreen();
        canvasScreen.setPreferredSize(mainDimension);
        System.out.format("canvasScreen dimensions = %d x %d %n", canvasScreen.getWidth(), canvasScreen.getHeight());

        Component canvasControl = canvasScreen.getComponent(0);
        Component canvas = canvasScreen.getComponent(1);

        // update canvas control dimensions]
        canvasControl.setPreferredSize(mainDimension);
        canvasControl.setBounds(mainBounds);

        // update sidebar dimensions
        System.out.format("Sidebar dimensions = %d x %d %n", SideMenuPanel.PREFERRED_WIDTH,
                frameHeight - MenuBar.HEIGHT);
        frame.getCanvasConsole().getSidebar().setPreferredSize(new Dimension(SideMenuPanel.PREFERRED_WIDTH,
                frameHeight - MenuBar.HEIGHT));

        // update canvas position
        canvas.setPreferredSize(mainDimension);
        canvas.setBounds(mainBounds);

        frame.repaint();
        frame.revalidate();
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
                resizeEverything();

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

            resizeEverything();
        }

        if ("maximizeFrame".equals(property)) {
            boolean maximizeFrame = (boolean) e.getNewValue();
            if (maximizeFrame) {
                frame.setExtendedState(frame.getExtendedState() | java.awt.Frame.MAXIMIZED_BOTH);
            } else {
                frame.setExtendedState(java.awt.Frame.NORMAL);
            }
        }
    }
}