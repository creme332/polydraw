package com.github.creme332.view.tutorial;

import static com.github.creme332.utils.IconLoader.loadSVGIcon;

import javax.swing.text.BadLocationException;

import com.github.creme332.model.TutorialModel;
import com.github.creme332.utils.exception.InvalidIconSizeException;
import com.github.creme332.utils.exception.InvalidPathException;

public class DrawPolygonTutorial extends AbstractTutorial {
        private static final TutorialModel TUTORIAL_MODEL = new TutorialModel("Draw Polygon");

        public DrawPolygonTutorial() throws InvalidPathException, InvalidIconSizeException, BadLocationException {
                super(TUTORIAL_MODEL, loadSVGIcon("/icons/regular-polygon.svg", TutorialCard.IMAGE_DIMENSION));
                model.addKeyword("regular");
                model.addKeyword("irregular");

                doc.insertString(doc.getLength(),
                                "In this tutorial you will learn how to draw regular and irregular polygons.\n\n",
                                regular);

                writeIrregularPolygonSection();
                writeRegularPolygonSection();
        }

        public void writeIrregularPolygonSection() throws BadLocationException, InvalidPathException {
                doc.insertString(doc.getLength(),
                                "Irregular Polygon\n\n",
                                boldLarge);

                insertImage("irregular-polygon.gif");

                doc.insertString(doc.getLength(),
                                "\n\n1. Click on the polygon icon (fifth icon from the left) in the menu bar and select the first option in the dropdown menu.\n",
                                regular);

                insertImage("irregular-polygon-icon.png");

                doc.insertString(doc.getLength(),
                                "\n\n2. Move your mouse cursor to the canvas and click on multiple points successively. You should see your polygon gradually being built.",
                                regular);
                doc.insertString(doc.getLength(),
                                "\n\n3. To end drawing you need to click on the first vertex of your polygon again.\n\n",
                                regular);
        }

        public void writeRegularPolygonSection() throws BadLocationException, InvalidPathException {
                doc.insertString(doc.getLength(),
                                "Regular Polygon",
                                boldLarge);

                doc.insertString(doc.getLength(),
                                "\n\n1. Click on the polygon icon (fifth icon from the left) in the menu bar and select the second option in the dropdown menu.\n",
                                regular);

                insertImage("regular-polygon-icon.png");

                doc.insertString(doc.getLength(),
                                "\n\n2. On your canvas, click on a pixel to select the center of the polygon.",
                                regular);

                doc.insertString(doc.getLength(),
                                "\n\n3. You will be prompted to enter the number of vertices for your polygon. ",
                                regular);
                doc.insertString(doc.getLength(),
                                "Your input value must be an integer greater than 2. \n",
                                highlightRed);

                insertImage("step-3.png");

                doc.insertString(doc.getLength(),
                                "\n\n4. You should now see a newly drawn polygon appear. You can control the side length of the polygon by moving your cursor towards or away from the polygon center.",
                                regular);

                doc.insertString(doc.getLength(),
                                "\n\n5. To fix the side length, perform one last click on the canvas.\n",
                                regular);

                insertImage("step-4.png");
        }

        @Override
        public String getImagePathPrefix() {
                return "/images/tutorials/draw-polygon/";
        }
}
