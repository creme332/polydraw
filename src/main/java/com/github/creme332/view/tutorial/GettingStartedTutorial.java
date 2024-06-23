package com.github.creme332.view.tutorial;

import static com.github.creme332.utils.IconLoader.loadIcon;
import static com.github.creme332.utils.IconLoader.getScaledDimension;

import java.awt.Dimension;

import com.github.creme332.model.TutorialModel;
import com.github.creme332.utils.exception.InvalidIconSizeException;
import com.github.creme332.utils.exception.InvalidPathException;

public class GettingStartedTutorial extends TutorialPanel {

    private static final String IMAGE_PATH_PREFIX = "/images/tutorials/getting-started/";
    private static final TutorialModel GETTING_STARTED_MODEL = new TutorialModel("Getting Started");

    public GettingStartedTutorial() throws InvalidPathException, InvalidIconSizeException {
        super(GETTING_STARTED_MODEL, loadIcon(IMAGE_PATH_PREFIX + "background.png",
                getScaledDimension(new Dimension(753, 453), TutorialCard.IMAGE_DIMENSION)));
    }

}
