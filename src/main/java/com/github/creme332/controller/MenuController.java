package com.github.creme332.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.github.creme332.model.MenuModel;

/**
 * Controller for a JMenu
 */
public class MenuController {
    MenuModel model;
    JMenu view;

    public MenuController(MenuModel model, JMenu view) {
        this.model = model;
        this.view = view;

        // for each menu item, ensure the length does not exceed view items
        int itemCount = Math.min(model.getItems().length, view.getItemCount());
        for (int j = 0; j < itemCount; j++) {
            JMenuItem jMenuItem = view.getItem(j);
            final int menuItemIndex = j;

            if (jMenuItem != null) { // Ensure the JMenuItem exists
                // when user clicks on a menu item
                jMenuItem.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        // update active item for this menu
                        model.setActiveItem(menuItemIndex);

                        // change menu icon to icon of selected menu item
                        view.setIcon(model.getActiveItem().getIcon());
                    }
                });
            }
        }
    }
}
