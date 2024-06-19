package com.github.creme332.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.github.creme332.model.AppState;
import com.github.creme332.view.SideMenuPanel;

public class SideMenuController implements PropertyChangeListener {
    private SideMenuPanel sidebar;

    public SideMenuController(AppState app, SideMenuPanel sidebar) {
        this.sidebar = sidebar;

        app.addPropertyChangeListener(this);

        sidebar.setVisible(app.getSideBarVisibility());

        sidebar.getCloseButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("Close side menu");
                app.setSideBarVisibility(false);
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        String propertyName = e.getPropertyName();
        if ("sidebarVisibility".equals(propertyName)) {
            sidebar.setVisible((boolean) e.getNewValue());
        }
    }
}
