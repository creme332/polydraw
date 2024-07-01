package com.github.creme332.view.tutorial;

import static com.github.creme332.utils.IconLoader.loadSVGIcon;

import com.github.creme332.model.TutorialModel;
import com.github.creme332.utils.exception.InvalidIconSizeException;
import com.github.creme332.utils.exception.InvalidPathException;

public class DrawCircleTutorial extends TutorialPanel {

    private static final String IMAGE_PATH_PREFIX = "/images/tutorials/draw-circle/";
    private static final TutorialModel DRAW_CIRCLE_MODEL = new TutorialModel("Draw Circle");

    public DrawCircleTutorial() throws InvalidPathException, InvalidIconSizeException {
        super(DRAW_CIRCLE_MODEL, loadSVGIcon("/icons/circle.svg", TutorialCard.IMAGE_DIMENSION));
        model.addKeyword("bresenham");
    }
}
