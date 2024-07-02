package com.github.creme332.view.tutorial;

import static com.github.creme332.utils.IconLoader.loadIcon;
import static com.github.creme332.utils.IconLoader.loadSVGIcon;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyleConstants;

import com.github.creme332.model.TutorialModel;
import com.github.creme332.utils.exception.InvalidIconSizeException;
import com.github.creme332.utils.exception.InvalidPathException;

public class DrawPolygonTutorial extends TutorialPanel {

    private static final String IMAGE_PATH_PREFIX = "/images/tutorials/draw-polygon/";
    private static final TutorialModel TUTORIAL_MODEL = new TutorialModel("Draw Polygon");

    public DrawPolygonTutorial() throws InvalidPathException, InvalidIconSizeException {
        super(TUTORIAL_MODEL, loadSVGIcon("/icons/regular-polygon.svg", TutorialCard.IMAGE_DIMENSION));
        model.addKeyword("regular");
        model.addKeyword("irregular");

                try {
            // Insert text
            doc.insertString(doc.getLength(),
                    "In this tutorial you will learn how to draw an Polygon using the Regular and Irregular Polygon Algorithm.\n\n",
                    regular);

            ImageIcon icon = loadIcon(IMAGE_PATH_PREFIX + "draw-polygon.gif", new Dimension(1000, 593));
            StyleConstants.setIcon(imageStyle, icon);
            doc.insertString(doc.getLength(), " ", imageStyle);

            doc.insertString(doc.getLength(),
                    "\n\n1. Click on the polygon icon in the menu bar to select the regular or irregular polygon drawing mode.\n",
                    regular);

            icon = loadIcon(IMAGE_PATH_PREFIX + "step-2.png");
            StyleConstants.setIcon(imageStyle, icon);
            doc.insertString(doc.getLength(), " ", imageStyle);

            doc.insertString(doc.getLength(),
                    "\n\n2. Move your mouse cursor on the canvas and click on a point where you want your first polygon focus to be.\n",
                    regular);
            doc.insertString(doc.getLength(),
                    "\n\n3. Click on another point where you want your second polygon focus to be.\n\n",
                    regular);

            icon = loadIcon(IMAGE_PATH_PREFIX + "step-3.png");
            StyleConstants.setIcon(imageStyle, icon);
            doc.insertString(doc.getLength(), " ", imageStyle);
        } catch (BadLocationException | InvalidPathException e) {
            e.printStackTrace();
        }
    }
}
