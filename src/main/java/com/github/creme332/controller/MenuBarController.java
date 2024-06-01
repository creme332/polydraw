package com.github.creme332.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.github.creme332.model.AppState;
import com.github.creme332.view.MenuBar;

public class MenuBarController {
    private MenuBar menu;
    private AppState app;

    public MenuBarController(AppState app, MenuBar menu) {
        this.menu = menu;
        this.app = app;

        menu.getSideBarButton().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                System.out.println("Clicked on burger menu");
                app.setSideBarVisibility(!app.getSideBarVisibility());
            }
        });
    }
}
