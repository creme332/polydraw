package com.github.creme332.controller;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

import com.github.creme332.model.AppState;
import com.github.creme332.view.MenuBar;

public class MenuBarController {
    private MenuBar menubar;
    private AppState app;
    private int activeMenuIndex = 0;

    final private static MatteBorder VISIBLE_BORDER = BorderFactory.createMatteBorder(
            2, 2, 2, 2, new Color(97, 97, 255));

    public MenuBarController(AppState app, MenuBar menu) {
        this.menubar = menu;
        this.app = app;

        for (int i = 0; i < menu.getMenuCount(); i++) {
            JMenu jMenu = menu.getMenu(i);

            // note: getMenuCount() returns the number of items in the menubar and not all
            // items are menus
            if (jMenu == null)
                break;

            final int menuIndex = i;
            final Border normalBorder = jMenu.getBorder();

            if (i == activeMenuIndex) {
                jMenu.setBorder(VISIBLE_BORDER);
            }

            // add mouse listener to menu
            jMenu.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    // remove border from previously active menu
                    menu.getMenu(activeMenuIndex).setBorder(normalBorder);

                    activeMenuIndex = menuIndex;

                    // add border to clicked menu
                    menu.getMenu(activeMenuIndex).setBorder(VISIBLE_BORDER);
                }
            });

            for (int j = 0; j < jMenu.getItemCount(); j++) {
                JMenuItem jMenuItem = jMenu.getItem(j);

                // add mouse listener to menu item
                jMenuItem.addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        // change menu icon to icon of menu item
                        jMenu.setIcon(jMenuItem.getIcon());
                    }
                });
            }
        }

        menu.getSideBarButton().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                app.setSideBarVisibility(!app.getSideBarVisibility());
            }
        });
    }
}
