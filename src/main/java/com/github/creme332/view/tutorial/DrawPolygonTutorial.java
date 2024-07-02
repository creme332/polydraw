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
    ImageIcon icon;

    public DrawPolygonTutorial() throws InvalidPathException, InvalidIconSizeException {
        super(TUTORIAL_MODEL, loadSVGIcon("/icons/regular-polygon.svg", TutorialCard.IMAGE_DIMENSION));
        model.addKeyword("regular");
        model.addKeyword("irregular");

       try {
            doc.insertString(doc.getLength(),
                    "In this tutorial you will learn two methods for drawing a polygon using the polygon algorithm.\n\n",
                    regular);

            doc.insertString(doc.getLength(),
                    "Method 1: Irregular Polygon\n\n",
                    boldLarge);

            doc.insertString(doc.getLength(),
                    "In this method, each time a point is pressed. It is added to the plottedPoints and when clicked on the first point, then the polygon is drawn.\n",
                    regular);

            doc.insertString(doc.getLength(),
                    "\n\n1. Click on the polygon icon (fifth icon from the left) in the menu bar and select the first option in the dropdown menu.\n",
                    regular);

            icon = loadIcon(IMAGE_PATH_PREFIX + "step-1.png");
            StyleConstants.setIcon(imageStyle, icon);
            doc.insertString(doc.getLength(), " ", imageStyle);

            doc.insertString(doc.getLength(),
                    "\n\n2. Move your mouse cursor to the canvas and select multiple points (more than 2).\n",
                    regular);
            doc.insertString(doc.getLength(),
                    "\n\n4. Once the you designed your polygon that satisfy you then click on the first point again.\n\n",
                    regular);

        //     icon = loadIcon(IMAGE_PATH_PREFIX + "step-2.png");
        //     StyleConstants.setIcon(imageStyle, icon);
        //     doc.insertString(doc.getLength(), " ", imageStyle);

            doc.insertString(doc.getLength(),
                    "\n\nMethod 2: Regular Polygon\n\n",
                    boldLarge);
            doc.insertString(doc.getLength(),
                    "In this method, you will first select the start point of the coordinate on the canvas before entering a fixed value for the number of sizes.\n",
                    regular);
            doc.insertString(doc.getLength(),
                    "\n\n1. Similar to the previous method, begin by clicking on the polygon icon in the menubar but this time choose the second option in the dropdown menu.",
                    regular);
            doc.insertString(doc.getLength(),
                    "\n\n2. After selecting the first point with your cursor, you will be prompted to enter the number of sizes of the polygon. ",
                    regular);
            doc.insertString(doc.getLength(),
                    "The sizes must be an integer greater than 2. \n",
                    highlightRed);

            icon = loadIcon(IMAGE_PATH_PREFIX + "step-3.png");
            StyleConstants.setIcon(imageStyle, icon);
            doc.insertString(doc.getLength(), " ", imageStyle);

            doc.insertString(doc.getLength(),
                    "\n\n6. After entering a valid size, you should see a newly drawn polygon:\n",
                    regular);

            icon = loadIcon(IMAGE_PATH_PREFIX + "step-4.png");
            StyleConstants.setIcon(imageStyle, icon);
            doc.insertString(doc.getLength(), " ", imageStyle);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
