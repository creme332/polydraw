package com.github.creme332.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.github.creme332.model.AppState;
import com.github.creme332.view.SideMenuPanel;

public class SideMenuController {
    private SideMenuPanel sidebar;
    private AppState app;

    public SideMenuController(AppState app, SideMenuPanel sidebar) {
        this.sidebar = sidebar;
        this.app = app;

        sidebar.getCloseButton().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                System.out.println("Close side menu");
                app.setSideBarVisibility(false);
            }
        });
    }
}
