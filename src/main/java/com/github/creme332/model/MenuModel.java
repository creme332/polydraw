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
        support.firePropertyChange("modeChange", activeItem.getMode(), newActiveItem.getMode());

        activeItem = newActiveItem;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener("modeChange", listener);
    }
}
