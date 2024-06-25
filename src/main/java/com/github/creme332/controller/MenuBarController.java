package com.github.creme332.controller;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

import com.github.creme332.model.AppState;
import com.github.creme332.model.MenuModel;
import com.github.creme332.model.Mode;
import com.github.creme332.model.Screen;
import com.github.creme332.view.MenuBar;

/**
 * Controller responsible for managing MenuBar.
 */
public class MenuBarController implements PropertyChangeListener {
    private MenuBar menubar;
    private AppState app;

    private static final MatteBorder VISIBLE_BORDER = BorderFactory.createMatteBorder(
            2, 2, 2, 2, new Color(97, 97, 255));

    MenuModel[] menuModels;
    int activeMenuIndex;
    Border defaultBorder;

    public MenuBarController(AppState app, MenuBar menubar) {
        this.menubar = menubar;
        this.app = app;
        this.menuModels = app.getMenuModels();

        app.addPropertyChangeListener(this);

        activeMenuIndex = app.getModeToMenuMapper().get(app.getMode());
        defaultBorder = menubar.getMyMenu(0).getBorder();

        // for each menu in menubar
        for (int i = 0; i < menuModels.length; i++) {
            JMenu jMenu = menubar.getMyMenu(i);
            MenuModel menuModel = menuModels[i];

            // listen to changes in each menu model
            menuModel.addPropertyChangeListener(this);

            // create a menu controller
            new MenuController(menuModel, jMenu);

            if (i == activeMenuIndex) {
                jMenu.setBorder(VISIBLE_BORDER);
            }

            final int menuIndex = i;
            // when user clicks on a menu
            jMenu.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    // remove border from previously active menu
                    menubar.getMyMenu(activeMenuIndex).setBorder(defaultBorder);

                    activeMenuIndex = menuIndex;
                    
                    // add border to clickedMenu
                    jMenu.setBorder(VISIBLE_BORDER);

                    // update global mode using menu model for clicked menu
                    app.setMode(menuModel.getActiveItem().getMode());

                    // display updated toast
                    app.activateToast();
                }
            });
        }

        menubar.getSideBarButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // toggle sidebar visibility
                app.setSideBarVisibility(!app.getSideBarVisibility());
            }
        });

        menubar.getHelpButton().addActionListener(e -> app.switchScreen(Screen.TUTORIAL_SCREEN));
    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        String propertyName = e.getPropertyName();
        if ("modeChange".equals(propertyName)) {
            Mode newMode = (Mode) e.getNewValue();

            // remove border from previously active menu
            menubar.getMyMenu(activeMenuIndex).setBorder(defaultBorder);

            // store index of clicked menu
            activeMenuIndex = app.getModeToMenuMapper().get(newMode);

            // add border to clicked menu
            menubar.getMyMenu(activeMenuIndex).setBorder(VISIBLE_BORDER);

            // update global mode
            app.setMode(newMode);

            // display updated toast
            app.activateToast();
        }

        if ("mode".equals(propertyName)) {
            Mode newMode = (Mode) e.getNewValue();

            // remove border from previously active menu
            menubar.getMyMenu(activeMenuIndex).setBorder(defaultBorder);

            // store index of new active menu
            activeMenuIndex = app.getModeToMenuMapper().get(newMode);

            // add border to clicked menu
            menubar.getMyMenu(activeMenuIndex).setBorder(VISIBLE_BORDER);
        }
    }
}
