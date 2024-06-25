package com.github.creme332.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Model for a JMenu.
 */
public class MenuModel {
    MenuItemModel[] items;
    MenuItemModel activeItem;
    private PropertyChangeSupport support;

    public MenuModel(MenuItemModel[] items) {
        this.items = items;

        activeItem = items[0];

        support = new PropertyChangeSupport(this);
    }

    public MenuItemModel[] getItems() {
        return items;
    }

    public MenuItemModel getActiveItem() {
        return activeItem;
    }

    public void setActiveItem(int i) {
        MenuItemModel newActiveItem = items[i];

        // Note: Keep OldValue = null to always fire property change when mode changes.
        // If OldValue = NewValue, no event is fired.
        support.firePropertyChange("modeChange", null, newActiveItem.getMode());

        activeItem = newActiveItem;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener("modeChange", listener);
    }
}
