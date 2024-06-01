package com.github.creme332.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.github.creme332.view.SideMenuPanel;

public class SideMenuController {
    private SideMenuPanel sidebar;

    public SideMenuController(SideMenuPanel sidebar) {
        this.sidebar = sidebar;

        sidebar.getCloseButton().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                System.out.println("Close side menu");
                sidebar.setVisible(false);
            }
        });
    }
}
