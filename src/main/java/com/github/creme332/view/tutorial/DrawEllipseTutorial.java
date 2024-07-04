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

public class DrawEllipseTutorial extends AbstractTutorial {

    private static final String IMAGE_PATH_PREFIX = "/images/tutorials/draw-ellipse/";
    private static final TutorialModel TUTORIAL_MODEL = new TutorialModel("Draw Ellipse");

    public DrawEllipseTutorial() throws InvalidPathException, InvalidIconSizeException {
        super(TUTORIAL_MODEL, loadSVGIcon("/icons/ellipse.svg", TutorialCard.IMAGE_DIMENSION));
        model.addKeyword("ellipse");

        try {
            // Insert text
            doc.insertString(doc.getLength(),
                    "In this tutorial you will learn how to draw an Ellipse using the Midpoint Ellipse Algorithm.\n\n",
                    regular);

            ImageIcon icon = loadIcon(IMAGE_PATH_PREFIX + "draw-ellipse.gif", new Dimension(1000, 593));
            StyleConstants.setIcon(imageStyle, icon);
            doc.insertString(doc.getLength(), " ", imageStyle);

            doc.insertString(doc.getLength(),
                    "\n\n1. Click on the ellipse icon in the menu bar to select the ellipse drawing mode.\n",
                    regular);

            icon = loadIcon(IMAGE_PATH_PREFIX + "step-2.png");
            StyleConstants.setIcon(imageStyle, icon);
            doc.insertString(doc.getLength(), " ", imageStyle);

            doc.insertString(doc.getLength(),
                    "\n\n2. Move your mouse cursor on the canvas and click on a point where you want your first ellipse focus to be.\n",
                    regular);
            doc.insertString(doc.getLength(),
                    "\n\n3. Click on another point where you want your second ellipse focus to be.\n\n",
                    regular);

            doc.insertString(doc.getLength(),
                    "\n\n4. At this point, you can move your cursor to control the size of the ellipse. When you are done, click on a third point to complete your ellipse.\n\n",
                    regular);

            icon = loadIcon(IMAGE_PATH_PREFIX + "step-3.png");
            StyleConstants.setIcon(imageStyle, icon);
            doc.insertString(doc.getLength(), " ", imageStyle);
        } catch (BadLocationException | InvalidPathException e) {
            e.printStackTrace();
        }
    }

}
