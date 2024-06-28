package com.github.creme332.model;

import javax.swing.Icon;

import com.github.creme332.utils.IconLoader;
import com.github.creme332.utils.exception.InvalidPathException;

public enum LineType {
    SOLID("Solid Line", "/icons/solid-line.svg"),
    DOTTED("Dotted Line", "/icons/dotted-line.svg"),
    DASHED("Dashed Line", "/icons/dashed-line.svg");

    private final String iconPath;
    private final String description;

    LineType(String description, String iconPath) {
        this.iconPath = iconPath;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public Icon getIcon() {
        try {
            return IconLoader.loadSVGIcon(iconPath);
        } catch (InvalidPathException e) {
            e.printStackTrace();
        }
        return null;
    }
}
