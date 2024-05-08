package com.github.creme332.utils;

import java.awt.Image;

import javax.swing.ImageIcon;

public class IconLoader {

    /**
     * Returns a resized icon from the resources folder.
     * 
     * @param path     Must start with / since path is relative to resources folder.
     * @param iconSize size of icon in pixels
     * @return
     */
    public ImageIcon loadIcon(String path, int iconSize) throws Exception {
        if (iconSize < 1) {
            throw new Exception("Icon size must be a positive integer");
        }

        try {
            ImageIcon icon = loadIcon(path);
            return new ImageIcon(
                    icon.getImage().getScaledInstance(iconSize,
                            iconSize,
                            Image.SCALE_DEFAULT));
        } catch (Exception e) {
            throw (e);
        }
    }

    /**
     * Returns an icon from the resources folder.
     * 
     * @param path Must start with / since path is relative to resources folder.
     * @return
     */
    public ImageIcon loadIcon(String path) throws Exception {
        if (path.length() < 1 || path.charAt(0) != '/') {
            throw new Exception("Path should start with /");
        }
        return new ImageIcon(this.getClass().getResource(path));
    }

}
