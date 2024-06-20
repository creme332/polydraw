package com.github.creme332.controller;

import com.github.creme332.model.AppState;
import com.github.creme332.model.Mode;
import com.github.creme332.view.Toast;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Timer;
import java.util.TimerTask;

public class ToastController implements PropertyChangeListener {
    private Toast toast;
    private AppState appState;
    private Timer timer;

    public ToastController(AppState appState, Toast toast) {
        this.appState = appState;
        this.toast = toast;
        appState.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("mode".equals(evt.getPropertyName())) {
            Mode newMode = (Mode) evt.getNewValue();

            toast.setTitleText(newMode.getTitle());
            toast.setInstructionText(newMode.getInstructions());

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

        toast.setVisible(true);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Hide toast after 10 seconds
                toast.setVisible(false);

                // cancel timer
                timer.cancel();
                timer.purge();
                timer = null;
            }
        }, TOAST_VISIBILITY_DURATION_SECONDS * 1000);
    }
}
