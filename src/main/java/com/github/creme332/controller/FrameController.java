package com.github.creme332.controller;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.*;

import com.github.creme332.model.AppState;
import com.github.creme332.model.MenuModel;
import com.github.creme332.model.Screen;
import com.github.creme332.utils.DesktopApi;
import com.github.creme332.utils.DesktopApi.EnumOS;
import com.github.creme332.view.Canvas;
import com.github.creme332.view.CanvasConsole;
import com.github.creme332.view.Frame;
import com.github.creme332.view.MenuBar;
import com.github.creme332.view.SideMenuPanel;

/**
 * Controller responsible for switching screens and resizing components as
 * required.
 */
public class FrameController implements PropertyChangeListener {
    private Frame frame;
    private AppState app;

    public FrameController(AppState model, Frame frame) {
        this.frame = frame;
        this.app = model;

        // create controller for menubar of frame
        new MenuBarController(app, frame.getMyMenuBar());

        model.addPropertyChangeListener(this);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeEverything();
            }
        });

        initializeKeyBindings();
        
        // Set initial frame state
        if (model.isMaximizeFrame()) {
            frame.setExtendedState(frame.getExtendedState() |
                    java.awt.Frame.MAXIMIZED_BOTH);
        }
    }

    private void initializeKeyBindings() {
        JComponent rootPane = (JComponent) frame.getContentPane();

        // Select cursor menu
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_1, 0), "selectCursorMenu");
        rootPane.getActionMap().put("selectCursorMenu", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectMenuByIndex(0);
            }
        });

        // Select line menu
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_2, 0), "selectLineMenu");
        rootPane.getActionMap().put("selectLineMenu", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectMenuByIndex(1);
            }
        });

        // Select circle menu
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_3, 0), "selectCircleMenu");
        rootPane.getActionMap().put("selectCircleMenu", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectMenuByIndex(2);
            }
        });

        // Select ellipse menu
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_4, 0), "selectEllipseMenu");
        rootPane.getActionMap().put("selectEllipseMenu", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectMenuByIndex(3);
            }
        });

        // Select polygon menu
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_5, 0), "selectPolygonMenu");
        rootPane.getActionMap().put("selectPolygonMenu", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectMenuByIndex(4);
            }
        });

        // Select transformations menu
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_6, 0), "selectTransformationsMenu");
        rootPane.getActionMap().put("selectTransformationsMenu", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectMenuByIndex(5);
            }
        });

        // Select move menu
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_7, 0), "selectMoveMenu");
        rootPane.getActionMap().put("selectMoveMenu", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectMenuByIndex(6);
            }
        });

        // Select delete menu
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_8, 0), "selectDeleteMenu");
        rootPane.getActionMap().put("selectDeleteMenu", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectMenuByIndex(7);
            }
        });

        // Open help center
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.ALT_DOWN_MASK), "openHelpCenter");
        rootPane.getActionMap().put("openHelpCenter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.switchScreen(Screen.TUTORIAL_SCREEN);
            }
        });

        // Toggle sidebar visibility
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK), "toggleSidebar");
        rootPane.getActionMap().put("toggleSidebar", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.setSideBarVisibility(!app.getSideBarVisibility());
            }
        });
    }

    private void selectMenuByIndex(int index) {
        MenuModel[] menuModels = app.getMenuModels();
        if (index >= 0 && index < menuModels.length) {
            app.setMode(menuModels[index].getActiveItem());
        }
    }

    /**
     * Resizes frame and its components when frame dimensions changes. This is the
     * function responsible for setting the size of canvas and canvas control.
     */
    private void resizeEverything() {
        final int frameWidth = frame.getWidth();
        final int frameHeight = frame.getHeight();

        Dimension canvasDimension = new Dimension(frameWidth, frameHeight - MenuBar.HEIGHT - 60);

        if (DesktopApi.getOs() == EnumOS.LINUX) {
            /**
             * dimensions for linux are different for some unknown reason. the dimensions
             * set below are a quick fix that prevents zoom panel and sidebar from
             * overflowing
             */

            canvasDimension = new Dimension(frameWidth - 80, frameHeight - MenuBar.HEIGHT - 100);
        }

        JLayeredPane canvasScreen = frame.getCanvasScreen();
        CanvasConsole canvasControl = (CanvasConsole) canvasScreen.getComponent(0);
        Canvas canvas = (Canvas) canvasScreen.getComponent(1);

        // update canvasScreen dimensions
        canvasScreen.setMaximumSize(canvasDimension);

        // update canvas position
        canvas.setMaximumSize(canvasDimension);
        canvas.setBounds(new Rectangle(canvasDimension));

        /**
         * Calculate dimensions of canvas control, taking into account whether the
         * sidebar is visible. Note: Hiding of the sidebar is made possible by pushing
         * it out the frame. This makes sidebar animation easier.
         */

        final boolean sidebarEnabled = app.getSideBarVisibility();
        final Dimension canvasControlDimension = new Dimension(
                canvasDimension.width + (sidebarEnabled ? 0 : SideMenuPanel.PREFERRED_WIDTH),
                canvasDimension.height);

        // update canvas control dimensions]
        canvasControl.setMaximumSize(canvasControlDimension);
        canvasControl.setBounds(new Rectangle(canvasControlDimension));
        canvasControl.revalidate();

        // update sidebar height
        frame.getCanvasConsole().getSidebar().setMaximumSize(new Dimension(SideMenuPanel.PREFERRED_WIDTH,
                frameHeight - MenuBar.HEIGHT));

        frame.repaint();
        frame.revalidate();
    }

    public void playStartAnimation() {
        final long animationDuration = 800; // ms

        Thread th = new Thread() {
            @Override
            public void run() {
                // Display the splash screen for 1 second
                frame.setMenuBarVisibility(false);
                frame.showScreen(Screen.SPLASH_SCREEN);

                try {
                    Thread.sleep(animationDuration);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.exit(0);
                }
                frame.showScreen(app.getCurrentScreen());
            }
        };
        th.start();
    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        String property = e.getPropertyName();
        if ("screen".equals(property)) {
            Screen newScreen = (Screen) e.getNewValue();
            frame.showScreen(newScreen);
        }

        if ("maximizeFrame".equals(property)) {
            boolean maximizeFrame = (boolean) e.getNewValue();
            if (maximizeFrame) {
                frame.setExtendedState(frame.getExtendedState() | java.awt.Frame.MAXIMIZED_BOTH);
            } else {
                frame.setExtendedState(java.awt.Frame.NORMAL);
            }
        }
    }
}
