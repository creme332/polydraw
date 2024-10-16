package com.github.creme332.view.tutorial;

import static com.github.creme332.utils.IconLoader.loadSVGIcon;

import javax.swing.text.BadLocationException;

import com.github.creme332.model.TutorialModel;
import com.github.creme332.utils.exception.InvalidIconSizeException;
import com.github.creme332.utils.exception.InvalidPathException;

public class DrawLineTutorial extends AbstractTutorial {

    private static final TutorialModel DRAW_LINE_MODEL = new TutorialModel("Draw Line");

    public DrawLineTutorial() throws InvalidPathException, InvalidIconSizeException {
        super(DRAW_LINE_MODEL, loadSVGIcon("/icons/blue-line.svg", TutorialCard.IMAGE_DIMENSION));
        model.addKeyword("bresenham");
        model.addKeyword("dda");

        try {
            doc.insertString(doc.getLength(),
                    "In this tutorial you will learn how to draw a line using either the DDA line algorithm or the Bresenham line algorithm.\n\n",
                    regular);

            insertImage("draw-line.gif");

            doc.insertString(doc.getLength(),
                    "\n\n1. Click on the line icon (second icon from the left) in the menu bar. You should see a dropdown menu appear.\n",
                    regular);

            insertImage("step-1.png");

            doc.insertString(doc.getLength(),
                    "\n\n2. Choose the algorithm that you want to use from the dropdown menu.\n",
                    regular);

            doc.insertString(doc.getLength(),
                    "\n\n3. Move your mouse cursor to the canvas and click on a point where you want your line to start.\n",
                    regular);

            insertImage("step-2.png");

            doc.insertString(doc.getLength(),
                    "\n\n4. As you move your mouse across the canvas, you should see a line preview. To complete your line, click on another point where you want your line to end.\n\n",
                    regular);

            insertImage("step-3.png");
        } catch (BadLocationException | InvalidPathException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getImagePathPrefix() {
        return "/images/tutorials/draw-line/";
    }

}
