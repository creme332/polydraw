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

public class DrawLineTutorial extends TutorialPanel {

    private static final String IMAGE_PATH_PREFIX = "/images/tutorials/draw-line/";
    private static final TutorialModel DRAW_LINE_MODEL = new TutorialModel("Draw Line");

    public DrawLineTutorial() throws InvalidPathException, InvalidIconSizeException {
        super(DRAW_LINE_MODEL, loadSVGIcon("/icons/blue-line.svg", TutorialCard.IMAGE_DIMENSION));
        model.addKeyword("bresenham");
        model.addKeyword("dda");

        try {
            // Insert text
            doc.insertString(doc.getLength(),
                    "In this tutorial you will learn how to draw a line using either the DDA algorithm or Bresenham line algorithm.\n\n",
                    regular);

            ImageIcon icon = loadIcon(IMAGE_PATH_PREFIX + "draw-line.gif", new Dimension(1000, 593));
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
                    "\n\n3. Move your mouse cursor to the canvas and click on a point where you want your line to start.\n",
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
