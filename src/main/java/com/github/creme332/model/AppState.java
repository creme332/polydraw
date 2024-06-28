package com.github.creme332.model;

import java.beans.*;
import java.util.EnumMap;
import java.util.Map;

import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.swing.FontIcon;

import com.github.creme332.utils.exception.InvalidPathException;

import static com.github.creme332.utils.IconLoader.loadSVGIcon;

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
        try {
            menuModels = createMenuModels();
            modeToMenuMapper = initModeToMenuMapper();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }

    private MenuModel[] createMenuModels() throws InvalidPathException {
        MenuModel graphicsMenuModel = new MenuModel(new MenuItemModel[] {
                new MenuItemModel("Move Graphics View", FontIcon.of(BootstrapIcons.ARROWS_MOVE, 35),
                        Mode.MOVE_GRAPHICS_VIEW),
                new MenuItemModel("Zoom In", FontIcon.of(BootstrapIcons.ZOOM_IN, 35), Mode.ZOOM_IN),
                new MenuItemModel("Zoom Out", FontIcon.of(BootstrapIcons.ZOOM_OUT, 35), Mode.ZOOM_OUT),
        });

        MenuModel transformationsMenuModel = new MenuModel(new MenuItemModel[] {
                new MenuItemModel("Reflect about Line", loadSVGIcon("/icons/reflect-about-line.svg"),
                        Mode.REFLECT_ABOUT_LINE),
                new MenuItemModel("Reflect about Point", loadSVGIcon("/icons/reflect-about-point.svg"),
                        Mode.REFLECT_ABOUT_POINT),

                new MenuItemModel("Rotate around Point", loadSVGIcon("/icons/rotate-around-point.svg"),
                        Mode.ROTATE_AROUND_POINT),
                new MenuItemModel("Translation", loadSVGIcon("/icons/translate-vector.svg"),
                        Mode.TRANSLATION),
                new MenuItemModel("Scaling", FontIcon.of(BootstrapIcons.ARROWS_ANGLE_EXPAND, 35), Mode.SCALING),
                new MenuItemModel("Shear", FontIcon.of(BootstrapIcons.BOX_ARROW_DOWN_LEFT, 35), Mode.SHEAR),
        });

        MenuModel polygonMenuModel = new MenuModel(new MenuItemModel[] {
                new MenuItemModel("Polygon", loadSVGIcon("/icons/triangle.svg"), Mode.DRAW_POLYGON_DYNAMIC),
                new MenuItemModel("Regular Polygon", loadSVGIcon("/icons/regular-polygon.svg"),
                        Mode.DRAW_REGULAR_POLYGON)

        });

        MenuModel ellipseMenuModel = new MenuModel(new MenuItemModel[] {
                new MenuItemModel("Ellipse", loadSVGIcon("/icons/ellipse.svg"), Mode.DRAW_ELLIPSE)
        });

        MenuModel circleMenuModel = new MenuModel(new MenuItemModel[] {
                new MenuItemModel("Circle with Center through Point",
                        loadSVGIcon("/icons/circle.svg"), Mode.DRAW_CIRCLE_DYNAMIC),
                new MenuItemModel("Circle: Center & Radius", loadSVGIcon("/icons/circle-radius.svg"),
                        Mode.DRAW_CIRCLE_FIXED)
        });

        MenuModel lineMenuModel = new MenuModel(new MenuItemModel[] {
                new MenuItemModel("Line: DDA", loadSVGIcon("/icons/line.svg"), Mode.DRAW_LINE_DDA),
                new MenuItemModel("Line: Bresenham", loadSVGIcon("/icons/line.svg"), Mode.DRAW_LINE_BRESENHAM)
        });

        MenuModel cursorMenuModel = new MenuModel(new MenuItemModel[] {
                new MenuItemModel("Move", loadSVGIcon("/icons/cursor.svg"), Mode.MOVE_CANVAS),
                new MenuItemModel("Freehand Shape", loadSVGIcon("/icons/freehand.svg"), Mode.DRAW_FREEHAND)
        });

        MenuModel deletionModel = new MenuModel(new MenuItemModel[] {
                new MenuItemModel("Delete", FontIcon.of(BootstrapIcons.ERASER, 40), Mode.DELETE),
                new MenuItemModel("Clip", FontIcon.of(BootstrapIcons.SCISSORS, 35), Mode.CLIPPING)
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

    private Map<Mode, Integer> initModeToMenuMapper() {
        Map<Mode, Integer> mapper = new EnumMap<>(Mode.class);
        for (int i = 0; i < menuModels.length; i++) {
            for (MenuItemModel item : menuModels[i].getItems()) {
                mapper.put(item.getMode(), i);
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