package com.github.creme332.controller.canvas;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.github.creme332.model.MenuModel;
import com.github.creme332.model.Mode;

/**
 * Controller for a JMenu found in MenuBar.
 */
public class MenuController {
    MenuModel model;
    JMenu view;

    public MenuController(MenuModel model, JMenu view) {
        this.model = model;
        this.view = view;

        // for each menu item
        for (int j = 0; j < model.getItems().length; j++) {
            JMenuItem jMenuItem = view.getItem(j);
            final Mode newMenu = model.getItems()[j];

            // when user clicks on a menu item
            jMenuItem.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    // update active item for this menu
                    model.setActiveItem(newMenu);

                    // change menu icon to icon of selected menu item
                    view.setIcon(model.getActiveItem().getIcon());
                }
            });
        }
    }
}