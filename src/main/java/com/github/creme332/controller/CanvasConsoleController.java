package com.github.creme332.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Timer;
import java.util.TimerTask;

import com.github.creme332.model.AppState;
import com.github.creme332.model.Mode;
import com.github.creme332.view.CanvasConsole;
import com.github.creme332.view.Toast;

public class CanvasConsoleController implements PropertyChangeListener {
    private Timer timer;
    private Toast toast;
    private CanvasConsole console;

    public CanvasConsoleController(AppState app, CanvasConsole console) {
        this.console = console;
        toast = console.getToast();
        app.addPropertyChangeListener(this);

        new ToolBarController(console.getToolbar(), app.getCanvasModel());
        new SideMenuController(app, console.getSidebar());
        new ZoomPanelController(app, console.getZoomPanel());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("activateToast".equals(evt.getPropertyName())) {
            Mode currentMode = (Mode) evt.getNewValue();

            toast.setTitleText(currentMode.getTitle());
            toast.setInstructionText(currentMode.getInstructions());
            showTemporaryToast();
        }
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
