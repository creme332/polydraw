package com.github.creme332.view.tutorial;

import static com.github.creme332.utils.IconLoader.loadIcon;

import javax.swing.ImageIcon;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyleConstants;

import com.github.creme332.utils.exception.InvalidPathException;

public class DrawLineTutorial extends TutorialPanel {

    public DrawLineTutorial() {
        super("Draw Line");

        try {
            // Insert text
            doc.insertString(doc.getLength(), "Here is some regular text.\n", regular);
            doc.insertString(doc.getLength(), "This text will wrap automatically if it is too long.\n", bold);

            // Insert an image
            ImageIcon icon = loadIcon("/icons/axes.png");
            StyleConstants.setIcon(imageStyle, icon);
            doc.insertString(doc.getLength(), " ", imageStyle);

            // Insert more text
            doc.insertString(doc.getLength(), "\nHere is more text after the image.\n", italic);
        } catch (BadLocationException | InvalidPathException e) {
            e.printStackTrace();
        }
    }

}
