package com.github.creme332.model;

public enum LineType {
    SOLID("Solid Line", "/icons/solid-line.png"),
    DOTTED("Dotted Line", "/icons/dotted-line.png"),
    DASHED("Dashed Line", "/icons/dashed-line.png");

    private final String iconPath;
    private final String description;

    LineType(String description, String iconPath) {
        this.iconPath = iconPath;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getIconPath() {
        return iconPath;
    }
}
