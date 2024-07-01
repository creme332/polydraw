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

public class DrawCircleTutorial extends TutorialPanel {

    private static final String IMAGE_PATH_PREFIX = "/images/tutorials/draw-circle/";
    private static final TutorialModel DRAW_CIRCLE_MODEL = new TutorialModel("Draw Circle");

    public DrawCircleTutorial() throws InvalidPathException, InvalidIconSizeException {
        super(DRAW_CIRCLE_MODEL, loadSVGIcon("/icons/circle.svg", TutorialCard.IMAGE_DIMENSION));
        model.addKeyword("bresenham");

        try {
            // Insert text
            doc.insertString(doc.getLength(),
                    "In this tutorial you will learn how to draw a circle using either the circle with center through point algorithm or circle: center & radius algorithm.\n\n",
                    regular);

            ImageIcon icon = loadIcon(IMAGE_PATH_PREFIX + "draw-circle.gif", new Dimension(1000, 593));
            StyleConstants.setIcon(imageStyle, icon);
            doc.insertString(doc.getLength(), " ", imageStyle);

            doc.insertString(doc.getLength(),
                    "\n\n1. Click on the line icon (second icon from the left) in the menu bar.\n",
                    regular);
            doc.insertString(doc.getLength(),
                    "\n\n2. Choose the algorithm that you want to use from the expanded menu.\n\n",
                    regular);

            icon = loadIcon(IMAGE_PATH_PREFIX + "step-1.png");
            StyleConstants.setIcon(imageStyle, icon);
            doc.insertString(doc.getLength(), " ", imageStyle);

            doc.insertString(doc.getLength(),
                    "\n\n3. For circle with center through point, move your mouse cursor to the canvas and click on a point where you want your line to start.\n",
                    regular);
            doc.insertString(doc.getLength(),
                    "\n\n4. Click on another point where you want your line to end.\n\n",
                    regular);

            icon = loadIcon(IMAGE_PATH_PREFIX + "step-2.png");
            StyleConstants.setIcon(imageStyle, icon);
            doc.insertString(doc.getLength(), " ", imageStyle);

            doc.insertString(doc.getLength(),
                    "\n\n5. For circle: center & radius, enter a radius to be display.\n",
                    regular);

            icon = loadIcon(IMAGE_PATH_PREFIX + "step-3.png");
            StyleConstants.setIcon(imageStyle, icon);
            doc.insertString(doc.getLength(), " ", imageStyle);

            doc.insertString(doc.getLength(),
                    "\n\n6. Here is the circle after entering the radius.\n",
                    regular);

            icon = loadIcon(IMAGE_PATH_PREFIX + "step-4.png");
            StyleConstants.setIcon(imageStyle, icon);
            doc.insertString(doc.getLength(), " ", imageStyle);
        } catch (BadLocationException | InvalidPathException e) {
            e.printStackTrace();
        }
    }
}
