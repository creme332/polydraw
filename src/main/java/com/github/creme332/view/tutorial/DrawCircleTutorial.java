package com.github.creme332.view.tutorial;

import static com.github.creme332.utils.IconLoader.loadIcon;

import com.github.creme332.utils.exception.InvalidPathException;

public class DrawCircleTutorial extends TutorialPanel {

    private static final String IMAGE_PATH_PREFIX = "/images/tutorials/draw-circle/";

    public DrawCircleTutorial() throws InvalidPathException {
        super("Draw Circle", loadIcon(IMAGE_PATH_PREFIX + "background.png"));
    }
}
