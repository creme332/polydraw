package com.github.creme332.controller;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

import com.github.creme332.model.AppState;
import com.github.creme332.model.MenuModel;
import com.github.creme332.model.CanvasModel;
import com.github.creme332.model.Mode;
import com.github.creme332.model.Screen;
import com.github.creme332.view.MenuBar;
import com.github.creme332.view.Toast;

public class MenuBarController implements PropertyChangeListener {
    private MenuBar menubar;
    private AppState app;

    private static final MatteBorder VISIBLE_BORDER = BorderFactory.createMatteBorder(
            2, 2, 2, 2, new Color(97, 97, 255));

    MenuModel[] menuModels;
    int activeMenuIndex;
    Border defaultBorder;
    private Toast toast;

    public MenuBarController(AppState app, MenuBar menubar, Toast toast) {
        this.menubar = menubar;
        this.app = app;
        this.menuModels = app.getMenuModels();
        this.toast = toast;

        app.addPropertyChangeListener(this);

        activeMenuIndex = app.getModeToMenuMapper().get(app.getMode());
        defaultBorder = menubar.getMenu(0).getBorder();

        // for each menu in menubar
        for (int i = 0; i < menuModels.length; i++) {
            JMenu jMenu = menubar.getMenu(i);
            MenuModel menuModel = menuModels[i];

            // listen to changes in each menu model
            menuModel.addPropertyChangeListener(this);

            // create a menu controller
            new MenuController(menuModel, jMenu, app, toast);

            if (i == activeMenuIndex) {
                jMenu.setBorder(VISIBLE_BORDER);
            }

            final int menuIndex = i;
            // when user clicks on a menu
            jMenu.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    // remove border from previously active menu
                    menubar.getMenu(activeMenuIndex).setBorder(defaultBorder);

                    activeMenuIndex = menuIndex;

                    JMenu clickedMenu = (JMenu) e.getComponent();

                    // add border to clicked menu
                    clickedMenu.setBorder(VISIBLE_BORDER);

                    // update global mode using menu model for clicked menu
                    app.setMode(menuModel.getActiveItem().getMode());

                    // Show toast
                    showTemporaryToast(menuModel.getActiveItem().getMode());
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

        menubar.getGuidelinesButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // toggle guidelines visibility
                CanvasModel canvasModel = app.getCanvasModel();
                canvasModel.setGuidelinesEnabled(!canvasModel.isGuidelinesEnabled());
            }
        });

        menubar.getHelpButton().addActionListener(e -> app.switchScreen(Screen.TUTORIAL_SCREEN));

        menubar.getToggleAxesButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // toggle axes visibility
                CanvasModel canvasModel = app.getCanvasModel();
                canvasModel.setAxesVisible(!canvasModel.isAxesVisible());
            }
        });
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

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        if (e.getPropertyName().equals("activeItem")) {
            int oldMenuIndex = activeMenuIndex;
            activeMenuIndex = app.getModeToMenuMapper().get(app.getMode());
            menubar.getMenu(oldMenuIndex).setBorder(defaultBorder);
            menubar.getMenu(activeMenuIndex).setBorder(VISIBLE_BORDER);
        } else if (e.getPropertyName().equals("sideBarVisibility")) {
            menubar.getSideBarButton().setSelected((boolean) e.getNewValue());
        }
    }
}
