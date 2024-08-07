package com.github.creme332.controller.canvas;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

import com.github.creme332.model.AppState;
import com.github.creme332.model.MenuModel;
import com.github.creme332.model.Mode;
import com.github.creme332.model.Screen;
import com.github.creme332.model.ShapeManager;
import com.github.creme332.view.MenuBar;

/**
 * Controller responsible for managing the MenuBar displayed when canvas is
 * visible.
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
        app.getCanvasModel().getShapeManager().addPropertyChangeListener(this);

        activeMenuIndex = app.getModeToMenuMapper().get(app.getMode());
        defaultBorder = menubar.getMyMenu(0).getBorder();

        // for each menu in menubar
        for (int menuIndex = 0; menuIndex < menuModels.length; menuIndex++) {
            final JMenu jMenu = menubar.getMyMenu(menuIndex);
            final MenuModel menuModel = menuModels[menuIndex];

            // listen to changes in each menu model
            menuModel.addPropertyChangeListener(this);

            // create a menu controller
            new MenuController(menuModel, jMenu);

            if (menuIndex == activeMenuIndex) {
                jMenu.setBorder(VISIBLE_BORDER);
            }

            // when user clicks on a menu
            final int menuIndexCopy = menuIndex;
            jMenu.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    // remove border from previously active menu
                    menubar.getMyMenu(activeMenuIndex).setBorder(defaultBorder);

                    activeMenuIndex = menuIndexCopy;

                    // add border to clickedMenu
                    jMenu.setBorder(VISIBLE_BORDER);

                    // update global mode using menu model for clicked menu
                    app.setMode(menuModel.getActiveItem());
                }
            });

            // Add hover listener to each JMenuItem in current menu
            List<JMenuItem> items = menubar.getAllMenuItems(menuIndex);
            for (int menuItemIndex = 0; menuItemIndex < items.size(); menuItemIndex++) {
                final JMenuItem menuItem = items.get(menuItemIndex);

                final int menuItemIndexCopy = menuItemIndex;
                menuItem.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        app.activateToast(menuModel.getItems()[menuItemIndexCopy]);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        // do nothing
                    }
                });
            }
        }

        menubar.getSideBarButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // toggle sidebar visibility
                app.setSideBarVisibility(!app.getSideBarVisibility());
            }
        });

        menubar.handleRedo(e -> app.getCanvasModel().getShapeManager().redo());
        menubar.handleUndo(e -> app.getCanvasModel().getShapeManager().undo());

        menubar.getHelpButton().addActionListener(e -> app.switchScreen(Screen.TUTORIAL_SCREEN));
        menubar.setRedoEnabled(app.getCanvasModel().getShapeManager().isRedoPossible());
        menubar.setUndoEnabled(app.getCanvasModel().getShapeManager().isUndoPossible());
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

        if (ShapeManager.STATE_CHANGE_PROPERTY_NAME.equals(propertyName)) {
            // shapes on canvas were modified => disable/enable undo and redo buttons
            menubar.setRedoEnabled(app.getCanvasModel().getShapeManager().isRedoPossible());
            menubar.setUndoEnabled(app.getCanvasModel().getShapeManager().isUndoPossible());
        }
    }
}
