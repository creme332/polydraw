package com.github.creme332.model;

import java.beans.*;
import java.util.EnumMap;
import java.util.Map;

import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.swing.FontIcon;

import com.github.creme332.utils.exception.InvalidIconSizeException;
import com.github.creme332.utils.exception.InvalidPathException;
import com.github.creme332.view.Canvas;

import static com.github.creme332.utils.IconLoader.loadIcon;

public class AppState {
    private Canvas canvas;

    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    private Mode mode = Mode.MOVE_CANVAS;

    private boolean visibleSidebar = false;

    private CanvasModel canvasModel = new CanvasModel();

    TutorialScreenModel tutorialModel = new TutorialScreenModel();

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

    public TutorialScreenModel getTutorialScreenModel() {
        return tutorialModel;
    }

    private MenuModel[] createMenuModels() throws InvalidIconSizeException, InvalidPathException {
        MenuModel graphicsMenuModel = new MenuModel(new MenuItemModel[] {
                new MenuItemModel("Move Graphics View", FontIcon.of(BootstrapIcons.ARROWS_MOVE, 35),
                        Mode.MOVE_GRAPHICS_VIEW),
                new MenuItemModel("Zoom In", FontIcon.of(BootstrapIcons.ZOOM_IN, 35), Mode.ZOOM_IN),
                new MenuItemModel("Zoom Out", FontIcon.of(BootstrapIcons.ZOOM_OUT, 35), Mode.ZOOM_OUT),
                new MenuItemModel("Delete", FontIcon.of(BootstrapIcons.ERASER, 40), Mode.DELETE)
        });

        MenuModel transformationsMenuModel = new MenuModel(new MenuItemModel[] {
                new MenuItemModel("Reflect about Line", loadIcon("/icons/reflect-about-line.png", 50),
                        Mode.REFLECT_ABOUT_LINE),
                new MenuItemModel("Reflect about Point", loadIcon("/icons/reflect-about-point.png", 50),
                        Mode.REFLECT_ABOUT_POINT),

                new MenuItemModel("Rotate around Point", loadIcon("/icons/rotate-around-point.png", 50),
                        Mode.ROTATE_AROUND_POINT),
                new MenuItemModel("Translation", loadIcon("/icons/translate-vector.png", 50),
                        Mode.TRANSLATION),
                new MenuItemModel("Scaling", FontIcon.of(BootstrapIcons.ARROWS_ANGLE_EXPAND, 35), Mode.SCALING),
                new MenuItemModel("Shear", FontIcon.of(BootstrapIcons.BOX_ARROW_DOWN_LEFT, 35), Mode.SHEAR),
                new MenuItemModel("Clipping", FontIcon.of(BootstrapIcons.SCISSORS, 35), Mode.CLIPPING),
        });

        MenuModel polygonMenuModel = new MenuModel(new MenuItemModel[] {
                new MenuItemModel("Polygon", loadIcon("/icons/triangle.png", 50), Mode.DRAW_POLYGON_DYNAMIC),
                new MenuItemModel("Regular Polygon", loadIcon("/icons/regular-polygon.png", 50),
                        Mode.DRAW_REGULAR_POLYGON)

        });

        MenuModel ellipseMenuModel = new MenuModel(new MenuItemModel[] {
                new MenuItemModel("Ellipse", loadIcon("/icons/ellipse.png", 50), Mode.DRAW_ELLIPSE)
        });

        MenuModel circleMenuModel = new MenuModel(new MenuItemModel[] {
                new MenuItemModel("Circle with Center through Point",
                        loadIcon("/icons/circle-center.png", 50), Mode.DRAW_CIRCLE_DYNAMIC),
                new MenuItemModel("Circle: Center & Radius", loadIcon("/icons/circle-radius.png", 50),
                        Mode.DRAW_CIRCLE_FIXED)
        });

        MenuModel lineMenuModel = new MenuModel(new MenuItemModel[] {
                new MenuItemModel("Line: DDA", loadIcon("/icons/line.png", 50), Mode.DRAW_LINE_DDA),
                new MenuItemModel("Line: Bresenham", loadIcon("/icons/line.png", 50), Mode.DRAW_LINE_BRESENHAM)
        });

        MenuModel cursorMenuModel = new MenuModel(new MenuItemModel[] {
                new MenuItemModel("Move", loadIcon("/icons/cursor.png", 50), Mode.MOVE_CANVAS),
                new MenuItemModel("Freehand Shape", loadIcon("/icons/freehand.png", 50), Mode.DRAW_FREEHAND)
        });

        return new MenuModel[] {
                cursorMenuModel,
                lineMenuModel,
                circleMenuModel,
                ellipseMenuModel,
                polygonMenuModel,
                transformationsMenuModel,
                graphicsMenuModel
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
    }

    public boolean getSideBarVisibility() {
        return visibleSidebar;
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

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }
}