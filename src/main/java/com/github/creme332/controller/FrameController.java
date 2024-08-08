package com.github.creme332.controller;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.*;

import com.github.creme332.controller.canvas.MenuBarController;
import com.github.creme332.model.AppState;
import com.github.creme332.model.MenuModel;
import com.github.creme332.model.Screen;
import com.github.creme332.utils.DesktopApi;
import com.github.creme332.utils.DesktopApi.EnumOS;
import com.github.creme332.view.Canvas;
import com.github.creme332.view.Frame;
import com.github.creme332.view.MenuBar;
import com.github.creme332.view.console.CanvasConsole;
import com.github.creme332.view.console.SideMenuPanel;

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
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_1, 0),
                "selectCursorMenu");
        rootPane.getActionMap().put("selectCursorMenu", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (app.getCurrentScreen() == Screen.MAIN_SCREEN) {
                    selectMenuByIndex(0);
                }
            }
        });

        // Select line menu
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_2, 0),
                "selectLineMenu");
        rootPane.getActionMap().put("selectLineMenu", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (app.getCurrentScreen() == Screen.MAIN_SCREEN) {
                    selectMenuByIndex(1);
                }
            }
        });

        // Select circle menu
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_3, 0),
                "selectCircleMenu");
        rootPane.getActionMap().put("selectCircleMenu", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (app.getCurrentScreen() == Screen.MAIN_SCREEN) {
                    selectMenuByIndex(2);
                }
            }
        });

        // Select ellipse menu
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_4, 0),
                "selectEllipseMenu");
        rootPane.getActionMap().put("selectEllipseMenu", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (app.getCurrentScreen() == Screen.MAIN_SCREEN) {
                    selectMenuByIndex(3);
                }
            }
        });

        // Select polygon menu
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_5, 0),
                "selectPolygonMenu");
        rootPane.getActionMap().put("selectPolygonMenu", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (app.getCurrentScreen() == Screen.MAIN_SCREEN) {
                    selectMenuByIndex(4);
                }
            }
        });

        // Select transformations menu
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_6, 0),
                "selectTransformationsMenu");
        rootPane.getActionMap().put("selectTransformationsMenu", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (app.getCurrentScreen() == Screen.MAIN_SCREEN) {
                    selectMenuByIndex(5);
                }
            }
        });

        // Select move menu
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_7, 0),
                "selectMoveMenu");
        rootPane.getActionMap().put("selectMoveMenu", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (app.getCurrentScreen() == Screen.MAIN_SCREEN) {
                    selectMenuByIndex(6);
                }
            }
        });

        // Select delete menu
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_8, 0),
                "selectDeleteMenu");
        rootPane.getActionMap().put("selectDeleteMenu", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (app.getCurrentScreen() == Screen.MAIN_SCREEN) {
                    selectMenuByIndex(7);
                }
            }
        });

        // Open help center
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_H, java.awt.event.InputEvent.ALT_DOWN_MASK), "openHelpCenter");
        rootPane.getActionMap().put("openHelpCenter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.switchScreen(Screen.TUTORIAL_SCREEN);
            }
        });

        // Toggle sidebar visibility
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_S,
                        java.awt.event.InputEvent.CTRL_DOWN_MASK | java.awt.event.InputEvent.SHIFT_DOWN_MASK),
                "toggleSidebar");
        rootPane.getActionMap().put("toggleSidebar", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (app.getCurrentScreen() == Screen.MAIN_SCREEN) {
                    app.setSideBarVisibility(!app.getSideBarVisibility());
                }
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

        /**
         * sync toolkit to prevent frame rate issues on linux.
         * 
         * Reference:
         * https://stackoverflow.com/questions/46626715/how-do-i-properly-render-at-a-high-frame-rate-in-pure-java
         */
        Toolkit.getDefaultToolkit().sync();
    }

    private class Task extends SwingWorker<Void, Integer> {
        static final long ANIMATION_DURATION = 800; // ms

        @Override
        protected Void doInBackground() throws Exception {
            // Display the splash screen for some time
            frame.setMenuBarVisibility(false);
            frame.showScreen(Screen.SPLASH_SCREEN);
            Thread.sleep(ANIMATION_DURATION);
            return null;
        }

        @Override
        protected void process(List<Integer> chunks) {
            // do nothing
        }

        @Override
        protected void done() {
            // Close splash screen and proceed to main application
            frame.showScreen(app.getCurrentScreen());
        }
    }

    public void playStartAnimation() {
        // Perform background loading task
        Task task = new Task();
        task.execute();
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
