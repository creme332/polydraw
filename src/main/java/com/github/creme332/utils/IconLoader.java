package com.github.creme332.utils;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;

import com.github.creme332.utils.exception.InvalidIconSizeException;
import com.github.creme332.utils.exception.InvalidPathException;

public class IconLoader {
    private IconLoader() {

    }

    /**
     * Scales the dimensions of an image while maintaining aspect ratio.
     * <p>
     * Reference: https://stackoverflow.com/a/32679235/17627866
     * </p>
     * 
     * @param imageSize Current dimension of image
     * @param boundary  Desired dimension of image
     * @return Scaled dimension
     */
    public static Dimension getScaledDimension(Dimension imageSize, Dimension boundary) {

        double widthRatio = boundary.getWidth() / imageSize.getWidth();
        double heightRatio = boundary.getHeight() / imageSize.getHeight();
        double ratio = Math.min(widthRatio, heightRatio);

        return new Dimension((int) (imageSize.width * ratio),
                (int) (imageSize.height * ratio));
    }

    /**
     * Returns a resized icon from the resources folder.
     * 
     * @param path   Path to image. Must start with a forward slash (/). Path is
     *               relative to the resources folder.
     * @param height Height in pixels
     * @param width  Width in pixels
     * @return
     * @throws InvalidIconSizeException
     * @throws InvalidPathException
     */
    public static ImageIcon loadIcon(String path, Dimension dimension)
            throws InvalidIconSizeException, InvalidPathException {
        if (dimension.height < 1 || dimension.width < 1) {
            throw new InvalidIconSizeException("Icon size must be a positive integer");
        }

        try {
            ImageIcon icon = loadIcon(path);
            return new ImageIcon(
                    icon.getImage().getScaledInstance(dimension.width,
                            dimension.height,
                            Image.SCALE_DEFAULT));
        } catch (Exception e) {
            throw new InvalidPathException("Failed to load icon from path: " + path, e);
        }
    }

    /**
     * Returns a resized icon from the resources folder.
     * 
     * @param path     Path to image. Must start with a forward slash (/). Path is
     *                 relative to the resources folder.
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
     * @param path Path to image. Must start with a forward slash (/). Path is
     *             relative to the resources folder.
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