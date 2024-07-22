package com.github.creme332.model;

import javax.swing.Icon;

import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.swing.FontIcon;

import static com.github.creme332.utils.IconLoader.loadSVGIcon;

public enum Mode {
    MOVE_CANVAS("Move", "Drag or select object", loadSVGIcon("/icons/cursor.svg")),

    DRAW_LINE_DDA("Line: DDA", "Select two points or positions", loadSVGIcon("/icons/red-line.svg")),
    DRAW_LINE_BRESENHAM("Line: Bresenham", "Select two points or positions", loadSVGIcon("/icons/blue-line.svg")),

    DRAW_CIRCLE_DYNAMIC("Circle with Center through Point", "Select center point, then point on circle",
            loadSVGIcon("/icons/circle.svg")),
    DRAW_CIRCLE_FIXED("Circle: Center & Radius", "Select center point, then enter radius",
            loadSVGIcon("/icons/circle-radius.svg")),

    DRAW_ELLIPSE("Ellipse with Foci through Point", "Select two foci, then point on ellipse",
            loadSVGIcon("/icons/ellipse.svg")),
    DRAW_ELLIPSE_FIXED("Ellipse: Foci & Radius", "Select two foci, then enter radii",
            loadSVGIcon("/icons/ellipse-foci.svg")),

    DRAW_POLYGON_DYNAMIC("Polygon", "Select all vertices, then first vertex again",
            loadSVGIcon("/icons/triangle.svg")),
    DRAW_REGULAR_POLYGON("Regular Polygon", "Select center, then enter number of vertices",
            loadSVGIcon("/icons/regular-polygon.svg")),

    REFLECT_ABOUT_LINE("Reflect about Line", "Select object to reflect, then line of reflection",
            loadSVGIcon("/icons/reflect-about-line.svg")),
    REFLECT_ABOUT_POINT("Reflect about Point", "Select object to reflect, then center point",
            loadSVGIcon("/icons/reflect-about-point.svg")),

    ROTATE_ABOUT_POINT("Rotate about Point", "Select object to rotate and center point, then enter angle",
            loadSVGIcon("/icons/rotate-about-point.svg")),

    ZOOM_IN("Zoom In", "Click/tap to zoom (or Mouse Wheel)", FontIcon.of(BootstrapIcons.ZOOM_IN, 35)),
    ZOOM_OUT("Zoom Out", "Click/tap to zoom (or Mouse Wheel)", FontIcon.of(BootstrapIcons.ZOOM_OUT, 35)),
    DELETE("Delete", "Select object which should be deleted", FontIcon.of(BootstrapIcons.ERASER, 40)),
    MOVE_GRAPHICS_VIEW("Move Graphics View", "Drag white background or axis",
            FontIcon.of(BootstrapIcons.ARROWS_MOVE, 35)),

    TRANSLATION("Translate by Vector", "Select object to translate, then enter vector",
            loadSVGIcon("/icons/translate-vector.svg")),
    SCALING("Scaling", "Select object to scale then enter scale factor",
            FontIcon.of(BootstrapIcons.ARROWS_ANGLE_EXPAND, 35)),
    SHEAR("Shear", "Select object then enter shear factor",
            FontIcon.of(BootstrapIcons.BOX_ARROW_DOWN_LEFT, 35)),
    CLIP("Clip", "Draw clipping region with mouse drag", FontIcon.of(BootstrapIcons.SCISSORS, 35));

    private final String title;
    private final String instructions;
    private Icon icon;

    Mode(String title, String instructions, Icon icon) {
        this.title = title;
        this.instructions = instructions;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public String getInstructions() {
        return instructions;
    }

    public Icon getIcon() {
        return icon;
    }
}
