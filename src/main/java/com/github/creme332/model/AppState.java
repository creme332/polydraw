package com.github.creme332.model;

import java.beans.*;
import java.util.EnumMap;
import java.util.Map;

public class AppState {
    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    private Mode mode = Mode.MOVE_CANVAS;

    private boolean visibleSidebar = false;
    private boolean maximizeFrame = true;
    private CanvasModel canvasModel = new CanvasModel();

    private MenuModel[] menuModels;

    Map<Mode, Integer> modeToMenuMapper;

    Screen currentScreen = Screen.MAIN_SCREEN;

    public AppState() {
        menuModels = createMenuModels();
        modeToMenuMapper = initModeToMenuMapper();
    }

    private MenuModel[] createMenuModels() {
        MenuModel graphicsMenuModel = new MenuModel(new Mode[] {
                Mode.MOVE_GRAPHICS_VIEW,
                Mode.ZOOM_IN,
                Mode.ZOOM_OUT
        });

        MenuModel transformationsMenuModel = new MenuModel(new Mode[] {
                Mode.REFLECT_ABOUT_LINE,
                Mode.REFLECT_ABOUT_POINT,
                Mode.ROTATE_AROUND_POINT,
                Mode.TRANSLATION,
                Mode.SCALING,
                Mode.SHEAR
        });

        MenuModel polygonMenuModel = new MenuModel(new Mode[] {
                Mode.DRAW_POLYGON_DYNAMIC,
                Mode.DRAW_REGULAR_POLYGON

        });

        MenuModel ellipseMenuModel = new MenuModel(new Mode[] {
                Mode.DRAW_ELLIPSE });

        MenuModel circleMenuModel = new MenuModel(new Mode[] {
                Mode.DRAW_CIRCLE_DYNAMIC,
                Mode.DRAW_CIRCLE_FIXED

        });

        MenuModel lineMenuModel = new MenuModel(new Mode[] {
                Mode.DRAW_LINE_DDA,
                Mode.DRAW_LINE_BRESENHAM
        });

        MenuModel cursorMenuModel = new MenuModel(new Mode[] {
                Mode.MOVE_CANVAS
        });

        MenuModel deletionModel = new MenuModel(new Mode[] {
                Mode.DELETE, Mode.CLIPPING
        });

        return new MenuModel[] {
                cursorMenuModel,
                lineMenuModel,
                circleMenuModel,
                ellipseMenuModel,
                polygonMenuModel,
                transformationsMenuModel,
                graphicsMenuModel,
                deletionModel
        };
    }

    public void switchScreen(Screen newScreen) {
        support.firePropertyChange("screen", currentScreen, newScreen);
        currentScreen = newScreen;
    }

    public Screen getCurrentScreen() {
        return currentScreen;
    }

    /**
     * Create a map that stores a mapping from a Mode to a menu index. This allows
     * us to know in constant time in which menu a mode is found.
     * 
     * @return
     */
    private Map<Mode, Integer> initModeToMenuMapper() {
        Map<Mode, Integer> mapper = new EnumMap<>(Mode.class);
        for (int i = 0; i < menuModels.length; i++) {
            for (Mode item : menuModels[i].getItems()) {
                mapper.put(item, i);
            }
        }
        return mapper;
    }

    public Map<Mode, Integer> getModeToMenuMapper() {
        return modeToMenuMapper;
    }

    public MenuModel[] getMenuModels() {
        return menuModels;
    }

    public CanvasModel getCanvasModel() {
        return canvasModel;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener("sidebarVisibility", listener);
        support.addPropertyChangeListener("mode", listener);
        support.addPropertyChangeListener("screen", listener);
        support.addPropertyChangeListener("maximizeFrame", listener);
        support.addPropertyChangeListener("activateToast", listener);
        support.addPropertyChangeListener("printingCanvas", listener);
    }

    public boolean getSideBarVisibility() {
        return visibleSidebar;
    }

    /**
     * Informs ToastController that toast must be displayed and updated. Property
     * change is guaranteed to get fired.
     */
    public void activateToast() {
        support.firePropertyChange("activateToast", null, mode);
    }

    public void setSideBarVisibility(boolean newValue) {
        support.firePropertyChange("sidebarVisibility", visibleSidebar, newValue);
        visibleSidebar = newValue;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode newMode) {
        System.out.println("Mode: " + mode + " -> " + newMode);
        support.firePropertyChange("mode", mode, newMode);
        mode = newMode;
    }

    public boolean isMaximizeFrame() {
        return maximizeFrame;
    }

    public void setMaximizeFrame(boolean maximizeFrame) {
        support.firePropertyChange("maximizeFrame", this.maximizeFrame,
                maximizeFrame);
        this.maximizeFrame = maximizeFrame;
    }

    public void startPrintingProcess() {
        support.firePropertyChange("printingCanvas", null, true);
    }
}