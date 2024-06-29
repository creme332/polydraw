package com.github.creme332.view.tutorial;

import static com.github.creme332.utils.IconLoader.loadIcon;
import static com.github.creme332.utils.IconLoader.getScaledDimension;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyleConstants;

import com.github.creme332.model.TutorialModel;
import com.github.creme332.utils.exception.InvalidIconSizeException;
import com.github.creme332.utils.exception.InvalidPathException;

public class DrawEllipseTutorial extends TutorialPanel {

    private static final String IMAGE_PATH_PREFIX = "/images/tutorials/draw-ellipse/";
    private static final TutorialModel DRAW_CIRCLE_MODEL = new TutorialModel("Draw Ellipse");

    public DrawEllipseTutorial() throws InvalidPathException, InvalidIconSizeException {
        super(DRAW_CIRCLE_MODEL, loadIcon(IMAGE_PATH_PREFIX + "background.png",
                getScaledDimension(new Dimension(1291, 616), TutorialCard.IMAGE_DIMENSION)));
        model.addKeyword("ellipse");

        try {
            // Insert text
            doc.insertString(doc.getLength(),
                    "In this tutorial you will learn how to draw an Ellipse using an ellipse algorithm.\n\n",
                    regular);

            ImageIcon icon = loadIcon(IMAGE_PATH_PREFIX + "draw-ellipse.gif", new Dimension(1000, 593));
            StyleConstants.setIcon(imageStyle, icon);
            doc.insertString(doc.getLength(), " ", imageStyle);

            doc.insertString(doc.getLength(),
                    "\n\n1. Click on the line icon in the menu bar.\n",
                    regular);
            doc.insertString(doc.getLength(),
                    "\n\n2. Select the ellipse algorithm that you want to use in the expanded menu.\n\n",
                    regular);

            icon = loadIcon(IMAGE_PATH_PREFIX + "step-2.png");
            StyleConstants.setIcon(imageStyle, icon);
            doc.insertString(doc.getLength(), " ", imageStyle);

            doc.insertString(doc.getLength(),
                    "\n\n3. Move your mouse cursor on the canvas and click on a point where you want your line to start then a second point.\n",
                    regular);
            doc.insertString(doc.getLength(),
                    "\n\n4. Click on another point where you want your line to end.\n\n",
                    regular);

            icon = loadIcon(IMAGE_PATH_PREFIX + "step-3.png");
            StyleConstants.setIcon(imageStyle, icon);
            doc.insertString(doc.getLength(), " ", imageStyle);
        } catch (BadLocationException | InvalidPathException e) {
            e.printStackTrace();
        }
    }

}
