package com.github.creme332.controller;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import com.github.creme332.model.AppState;
import com.github.creme332.model.CanvasModel;
import com.github.creme332.model.Mode;
import com.github.creme332.view.CanvasConsole;
import com.github.creme332.view.SideMenuPanel;
import com.github.creme332.view.Toast;

public class CanvasConsoleController implements PropertyChangeListener {
    private Timer timer;
    private Toast toast;
    private CanvasConsole console;
    private CanvasModel model;

    public CanvasConsoleController(AppState app, CanvasConsole console) {
        this.console = console;
        this.model = app.getCanvasModel();
        toast = console.getToast();
        app.addPropertyChangeListener(this);

        new ToolBarController(console.getToolbar(), app.getCanvasModel());
        new SideMenuController(app, console.getSidebar());
        new ZoomPanelController(app, console.getZoomPanel());

        if (!app.getSideBarVisibility()) {
            System.out.println(console.getPreferredSize());
            console.setPreferredSize(console.getPreferredSize());
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        String propertyName = e.getPropertyName();
        if ("activateToast".equals(propertyName)) {
            Mode currentMode = (Mode) e.getNewValue();

            toast.setTitleText(currentMode.getTitle());
            toast.setInstructionText(currentMode.getInstructions());
            showTemporaryToast();
        }

        if ("sidebarVisibility".equals(propertyName)) {
            boolean openSidebar = (boolean) e.getNewValue();
            if (openSidebar) {
                animateSidebarOpen();
            } else {
                animateSidebarClose();
            }
        }
    }

    /**
     * Animates sidebar opening. It decreases the width of the canvas console so
     * that the sidebar which was initially out of frame becomes visible.
     */
    private void animateSidebarOpen() {
        /**
         * Speed at which sidebar opens in pixels per nanosecond.
         */
        final int openingSpeed = 5;
        /**
         * Initial width of canvas console such that sidebar is hidden.
         */
        final int initialWidth = console.getRootPane().getWidth() + SideMenuPanel.PREFERRED_WIDTH;

        /**
         * Final width of canvas console such that sidebar is visible.
         */
        final int finalWidth = model.getCanvasDimension().width;

        Thread th = new Thread() {
            @Override
            public void run() {
                for (int i = initialWidth; i >= finalWidth; i -= openingSpeed) {
                    try {
                        TimeUnit.NANOSECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int newWidth = Math.max(finalWidth, i);
                    console.setSize(new Dimension(newWidth, console.getHeight()));
                    console.revalidate();
                    console.repaint();
                }

            }
        };
        th.start();
    }

    /**
     * Animates sidebar closing. It increases the width of the canvas console so
     * that the sidebar becomes hidden (since sidebar moves out of frame).
     */
    private void animateSidebarClose() {
        /**
         * Speed at which sidebar closes in pixels per nanosecond.
         */
        final int closingSpeed = 6;

        /**
         * Initial width of canvas console such that sidebar is visible.
         */
        final int initialWidth = console.getRootPane().getWidth();

        /**
         * Final width of canvas console such that sidebar is hidden.
         */
        final int finalWidth = initialWidth + SideMenuPanel.PREFERRED_WIDTH;

        Thread th = new Thread() {
            @Override
            public void run() {
                for (int i = initialWidth; i <= finalWidth; i += closingSpeed) {
                    try {
                        TimeUnit.NANOSECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int newWidth = Math.min(finalWidth, i);
                    console.setSize(new Dimension(newWidth, console.getHeight()));
                    console.revalidate();
                    console.repaint();
                }
            }

        };
        th.start();
    }

    private void showTemporaryToast() {
        final long TOAST_VISIBILITY_DURATION_SECONDS = 5;
        if (timer != null) {
            // if a previous timer is still running, cancel it.
            timer.cancel();
            timer.purge();
        }

        // create a new timer
        timer = new Timer();

        console.toggleToastVisibility(true);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                console.toggleToastVisibility(false);

                // cancel timer
                timer.cancel();
                timer.purge();
                timer = null;
            }
        }, TOAST_VISIBILITY_DURATION_SECONDS * 1000);
    }
}
