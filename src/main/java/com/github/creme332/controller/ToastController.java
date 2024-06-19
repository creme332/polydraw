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
        final long TOAST_VISIBILITY_DURATION_SECONDS = 3;
        toast.setVisible(true);
        timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Hide toast after 10 seconds
                toast.setVisible(false);

                timer.cancel();
                timer.purge();
            }
        }, TOAST_VISIBILITY_DURATION_SECONDS * 1000);
    }
}
