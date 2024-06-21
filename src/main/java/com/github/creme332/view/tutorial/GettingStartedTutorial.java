package com.github.creme332.view.tutorial;

import static com.github.creme332.utils.IconLoader.loadIcon;

import com.github.creme332.utils.exception.InvalidPathException;

public class GettingStartedTutorial extends TutorialPanel {

    private static final String IMAGE_PATH_PREFIX = "/images/tutorials/getting-started/";

    public GettingStartedTutorial() throws InvalidPathException {
        super("Getting Started", loadIcon(IMAGE_PATH_PREFIX + "background.png"));
    }

}
