package com.github.creme332.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.github.creme332.model.AppState;
import com.github.creme332.view.SideMenuPanel;

/**
 * Controller responsible for managing sidebar in CanvasConsole.
 */
public class SideMenuController implements PropertyChangeListener {
    private SideMenuPanel sidebar;

    public SideMenuController(AppState app, SideMenuPanel sidebar) {
        this.sidebar = sidebar;

        app.addPropertyChangeListener(this);

        sidebar.setVisible(app.getSideBarVisibility());
    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        String propertyName = e.getPropertyName();
        if ("sidebarVisibility".equals(propertyName)) {
            sidebar.setVisible((boolean) e.getNewValue());
        }
    }
}
