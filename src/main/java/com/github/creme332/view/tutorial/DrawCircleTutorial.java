package com.github.creme332.view.tutorial;

import static com.github.creme332.utils.IconLoader.loadSVGIcon;

import javax.swing.text.BadLocationException;

import com.github.creme332.model.TutorialModel;
import com.github.creme332.utils.exception.InvalidIconSizeException;
import com.github.creme332.utils.exception.InvalidPathException;

public class DrawCircleTutorial extends AbstractTutorial {

    private static final TutorialModel DRAW_CIRCLE_MODEL = new TutorialModel("Draw Circle");

    public DrawCircleTutorial() throws InvalidPathException, InvalidIconSizeException {
        super(DRAW_CIRCLE_MODEL, loadSVGIcon("/icons/circle.svg", TutorialCard.IMAGE_DIMENSION));
        model.addKeyword("bresenham");

        try {
            doc.insertString(doc.getLength(),
                    "In this tutorial you will learn two methods for drawing a circle using the Bresenham Circle algorithm.\n\n",
                    regular);

            doc.insertString(doc.getLength(),
                    "Method 1: Circle with Center through Point\n\n",
                    boldLarge);

            doc.insertString(doc.getLength(),
                    "In this method, you will first select the center of the circle and the radius will be automatically determined from the distance between your cursor and your center.\n",
                    regular);

            doc.insertString(doc.getLength(),
                    "\n\n1. Click on the circle icon (third icon from the left) in the menu bar and select the first option in the dropdown menu.\n\n",
                    regular);

            insertImage("step-1.png");

            doc.insertString(doc.getLength(),
                    "\n\n2. Move your mouse cursor to the canvas and click on a point to select the center of your circle.\n",
                    regular);
            doc.insertString(doc.getLength(),
                    "\n\n4. As you move your cursor, you should see the size of the circle changing. Once you are satisfied with the size, click on another point to fix the radius.\n\n",
                    regular);

            insertImage("step-2.png");

            doc.insertString(doc.getLength(),
                    "\n\nMethod 2: Circle: Center & Radius\n\n",
                    boldLarge);
            doc.insertString(doc.getLength(),
                    "In this method, you will first select the center of the circle on the canvas before entering a fixed value for the radius.\n",
                    regular);
            doc.insertString(doc.getLength(),
                    "\n\n1. Similar to the previous method, begin by clicking on the circle icon in the menubar but this time choose the second option in the dropdown menu.",
                    regular);
            doc.insertString(doc.getLength(),
                    "\n\n2. After selecting the circle center with your cursor, you will be prompted to enter the circle radius. ",
                    regular);
            doc.insertString(doc.getLength(),
                    "The radius must be an integer greater than 0. \n\n",
                    highlightRed);

            insertImage("step-3.png");

            doc.insertString(doc.getLength(),
                    "\n\n6. After entering a valid radius, you should see a newly drawn circle:\n\n",
                    regular);

            insertImage("step-4.png");
        } catch (BadLocationException | InvalidPathException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getImagePathPrefix() {
        return "/images/tutorials/draw-circle/";
    }
}
