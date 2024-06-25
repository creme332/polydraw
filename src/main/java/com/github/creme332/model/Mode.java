package com.github.creme332.model;

public enum Mode {
    MOVE_CANVAS("Move", "Drag or select object"),
    DRAW_FREEHAND("Freehand Shape", "Sketch a function or geometric object"),

    DRAW_LINE_DDA("Line: DDA", "Select two points or positions"),
    DRAW_LINE_BRESENHAM("Line: Bresenham", "Select two points or positions"),

    DRAW_CIRCLE_DYNAMIC("Circle with Center through Point", "Select center point, then point on circle"),
    DRAW_CIRCLE_FIXED("Circle: Center & Radius", "Select center point, then enter radius"),

    DRAW_ELLIPSE("Ellipse", "Select two foci, then point on ellipse"),

    DRAW_POLYGON_DYNAMIC("Polygon", "Select all vertices, then first vertex again"),
    DRAW_REGULAR_POLYGON("Regular Polygon", "Select center, then enter number of vertices"),

    REFLECT_ABOUT_LINE("Reflect about Line", "Select object to reflect, then line of reflection"),
    REFLECT_ABOUT_POINT("Reflect about Point", "Select object to reflect, then center point"),

    ROTATE_AROUND_POINT("Rotate around Point", "Select object to rotate and center point, then enter angle"),

    ZOOM_IN("Zoom In", "Click/tap to zoom (or Mouse Wheel)"),
    ZOOM_OUT("Zoom Out", "Click/tap to zoom (or Mouse Wheel)"),
    DELETE("Delete", "Select object which should be deleted"),
    MOVE_GRAPHICS_VIEW("Move Graphics View", "Drag white background or axis"),

    TRANSLATION("Translation", "Select object to translate"),
    NORMAL_ROTATION("Normal Rotation", "Select object to rotate"),
    SCALING("Scaling", "Select object to scale then enter scaling factor"),
    SHEAR("Shear", "Select object to translate, then enter scaling factor"),
    CLIPPING("Clipping", "Draw clipping region with mouse drag");

    private final String title;
    private final String instructions;

    Mode(String title, String instructions) {
        this.title = title;
        this.instructions = instructions;
    }

    public String getTitle() {
        return title;
    }

    public String getInstructions() {
        return instructions;
    }
}
