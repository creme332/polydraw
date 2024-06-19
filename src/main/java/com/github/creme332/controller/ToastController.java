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

            // Show toast for 10 seconds
            showTemporaryToast();
        }
    }

    private void showTemporaryToast() {
        // Cancel previous timer if exists
        if (timer != null) {
            timer.cancel();
        }

        // Schedule a new timer to hide the toast after 10 seconds
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Clear toast after 10 seconds
                clearToast();
            }
        }, 10000); // 10 seconds
    }

    private void clearToast() {
        toast.setTitleText("");
        toast.setInstructionText("");
    }
}
