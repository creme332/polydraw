package com.github.creme332.view.tutorial;

import static com.github.creme332.utils.IconLoader.loadIcon;
import static com.github.creme332.utils.IconLoader.getScaledDimension;

import java.awt.Dimension;

import com.github.creme332.utils.exception.InvalidIconSizeException;
import com.github.creme332.utils.exception.InvalidPathException;

public class DrawCircleTutorial extends TutorialPanel {

    private static final String IMAGE_PATH_PREFIX = "/images/tutorials/draw-circle/";

    public DrawCircleTutorial() throws InvalidPathException, InvalidIconSizeException {
        super("Draw Circle", loadIcon(IMAGE_PATH_PREFIX + "background.png",
                getScaledDimension(new Dimension(1291, 616), TutorialCard.IMAGE_DIMENSION)));
    }
}
