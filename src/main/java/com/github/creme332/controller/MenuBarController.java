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
import com.github.creme332.model.Mode;
import com.github.creme332.view.MenuBar;

public class MenuBarController {
    private MenuBar menubar;
    private AppState app;

    private int activeMenuIndex = 0;

    private static final MatteBorder VISIBLE_BORDER = BorderFactory.createMatteBorder(
            2, 2, 2, 2, new Color(97, 97, 255));

    Mode[][] modes = {
            new Mode[] { Mode.MOVE_CANVAS, Mode.DRAW_FREEHAND },
            new Mode[] { Mode.DRAW_LINE_DDA, Mode.DRAW_LINE_BRESENHAM },
            new Mode[] { Mode.DRAW_CIRCLE_DYNAMIC, Mode.DRAW_CIRCLE_FIXED },
            new Mode[] { Mode.DRAW_ELLIPSE },
            new Mode[] { Mode.DRAW_POLYGON_DYNAMIC, Mode.DRAW_REGULAR_POLYGON },
            new Mode[] { Mode.REFLECT_ABOUT_LINE, Mode.REFLECT_ABOUT_POINT, Mode.ROTATE_AROUND_POINT },
            new Mode[] { Mode.MOVE_GRAPHICS_VIEW, Mode.ZOOM_IN, Mode.ZOOM_OUT, Mode.DELETE }
    };

    Mode[] menuModes = new Mode[modes.length]; // currently selected option for each menu

    public MenuBarController(AppState app, MenuBar menu) {
        this.menubar = menu;
        this.app = app;
        app.setMode(modes[0][0]);

        for (int i = 0; i < menu.getMenuCount(); i++) {
            JMenu jMenu = menu.getMenu(i);

            // note: getMenuCount() returns the number of items in the menubar and not all
            // items are menus
            if (jMenu == null)
                break;

            final int menuIndex = i;
            final Border normalBorder = jMenu.getBorder();

            // set initial mode of menu to first mode present
            menuModes[i] = modes[i][0];

            if (i == activeMenuIndex) {
                jMenu.setBorder(VISIBLE_BORDER);
            }

            // when user clicks on a menu
            jMenu.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {

                    // remove border from previously active menu
                    menu.getMenu(activeMenuIndex).setBorder(normalBorder);

                    // store index of clicked menu
                    activeMenuIndex = menuIndex;

                    // add border to clicked menu
                    menu.getMenu(activeMenuIndex).setBorder(VISIBLE_BORDER);

                    // change mode of app
                    app.setMode(menuModes[activeMenuIndex]);
                }
            });

            for (int j = 0; j < jMenu.getItemCount(); j++) {
                JMenuItem jMenuItem = jMenu.getItem(j);

                final int menuItemIndex = j;
                // add mouse listener to menu item
                jMenuItem.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        // change menu icon to icon of menu item
                        jMenu.setIcon(jMenuItem.getIcon());
                        app.setMode(modes[menuIndex][menuItemIndex]);
                        menuModes[activeMenuIndex] = modes[menuIndex][menuItemIndex];
                    }
                });
            }
        }

        menu.getSideBarButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                app.setSideBarVisibility(!app.getSideBarVisibility());
            }
        });

        menu.getGuidelinesButton().addActionListener(e -> {
            app.getCanvasModel().setEnableGuidelines(!app.getCanvasModel().isEnableGuidelines());
        });
    }
}
