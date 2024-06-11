package com.github.creme332.utils;

import java.awt.Image;

import javax.swing.ImageIcon;

import com.github.creme332.utils.exception.InvalidIconSizeException;
import com.github.creme332.utils.exception.InvalidPathException;

public class IconLoader {

    private IconLoader() {

    }

    /**
     * Returns a resized icon from the resources folder.
     * 
     * @param path     Must start with / since path is relative to resources folder.
     * @param iconSize size of icon in pixels
     * @return
     * @throws InvalidIconSizeException if iconSize is less than 1
     * @throws InvalidPathException     if path is invalid
     */
    public static ImageIcon loadIcon(String path, int iconSize) throws InvalidIconSizeException, InvalidPathException {
        if (iconSize < 1) {
            throw new InvalidIconSizeException("Icon size must be a positive integer");
        }

        try {
            ImageIcon icon = loadIcon(path);
            return new ImageIcon(
                    icon.getImage().getScaledInstance(iconSize,
                            iconSize,
                            Image.SCALE_DEFAULT));
        } catch (Exception e) {
            throw new InvalidPathException("Failed to load icon from path: " + path, e);
        }
    }

    /**
     * Returns an icon from the resources folder.
     * 
     * @param path Must start with / since path is relative to resources folder.
     * @return
     * @throws InvalidPathException if path is invalid
     */
    public static ImageIcon loadIcon(String path) throws InvalidPathException {
        if (path.length() < 1 || path.charAt(0) != '/') {
            throw new InvalidPathException("Path should start with /");
        }
        return new ImageIcon(IconLoader.class.getResource(path));
    }
}
