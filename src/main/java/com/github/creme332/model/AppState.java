package com.github.creme332.model;

import java.beans.*;

public class AppState {
    private PropertyChangeSupport support;

    private Mode mode;

    private boolean visibleSidebar = false;

    private CanvasModel canvasModel;

    public AppState() {
        canvasModel = new CanvasModel();
        support = new PropertyChangeSupport(this);
    }

    public CanvasModel getCanvasModel() {
        return canvasModel;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener("sidebarVisibility", listener);
    }

    public boolean getSideBarVisibility() {
        return visibleSidebar;
    }

    public void setSideBarVisibility(boolean newValue) {
        support.firePropertyChange("sidebarVisibility", visibleSidebar, newValue);
        visibleSidebar = newValue;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode newMode) {
        System.out.println("Mode: " + mode + " -> " + newMode);
        support.firePropertyChange("mode", mode, newMode);
        mode = newMode;
    }
}