package com.github.creme332.model;

import javax.swing.Icon;

/**
 * Model for a JMenuItem.
 */
public class MenuItemModel {
    String name;
    Icon icon;
    Mode mode;

    public MenuItemModel(String name, Icon icon, Mode mode) {
        this.name = name;
        this.icon = icon;
        this.mode = mode;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode newMode) {
        mode = newMode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }
}
