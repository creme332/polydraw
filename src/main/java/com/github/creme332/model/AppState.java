package com.github.creme332.model;

import java.beans.*;

public class AppState {
    private PropertyChangeSupport support;

    private boolean visibleSidebar = false;

    public AppState() {
        support = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener("sidebarVisibility", listener);
    }

    public boolean getSideBarVisibility() {
        return visibleSidebar;
    }

    public void setSideBarVisibility(boolean newValue) {
        System.out.println("dsad");
        support.firePropertyChange("sidebarVisibility", visibleSidebar, newValue);
        visibleSidebar = newValue;
    }
}