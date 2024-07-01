package com.github.creme332.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Model for a JMenu. Each menu will store some modes.
 */
public class MenuModel {
    Mode[] items;
    Mode activeItem;
    private PropertyChangeSupport support;

    public MenuModel(Mode[] items) {
        this.items = items;

        activeItem = items[0];

        support = new PropertyChangeSupport(this);
    }

    public Mode[] getItems() {
        return items;
    }

    public Mode getActiveItem() {
        return activeItem;
    }

    public void setActiveItem(Mode newMode) {
        // Note: Keep OldValue = null to always fire property change when mode changes.
        // If OldValue = NewValue, no event is fired.
        support.firePropertyChange("modeChange", null, newMode);

        activeItem = newMode;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener("modeChange", listener);
    }
}
