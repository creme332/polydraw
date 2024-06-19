package com.github.creme332.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.github.creme332.model.MenuModel;
import com.github.creme332.model.AppState;
import com.github.creme332.model.Mode;
import com.github.creme332.view.Toast;

/**
 * Controller for a JMenu
 */
public class MenuController {
    private MenuModel model;
    private JMenu view;
    private AppState appState;
    private Toast toast;

    public MenuController(MenuModel model, JMenu view, AppState appState, Toast toast) {
        this.model = model;
        this.view = view;
        this.appState = appState;
        this.toast = toast;

        // for each menu item
        for (int j = 0; j < model.getItems().length; j++) {
            JMenuItem jMenuItem = view.getItem(j);
            final int menuItemIndex = j;

            // when user clicks on a menu item
            jMenuItem.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    // update active item for this menu
                    model.setActiveItem(menuItemIndex);

                    // change menu icon to icon of selected menu item
                    view.setIcon(model.getActiveItem().getIcon());
                    appState.setMode(model.getActiveItem().getMode());
                    showTemporaryToast(model.getActiveItem().getMode());
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    showTemporaryToast(model.getItems()[menuItemIndex].getMode());
                }
            });
        }
    }

    private void showTemporaryToast(Mode mode) {
        toast.setTitleText(mode.getTitle());
        toast.setInstructionText(mode.getInstructions());
        toast.setVisible(true);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.setVisible(false);
            }
        }, 10000);
    }
}
