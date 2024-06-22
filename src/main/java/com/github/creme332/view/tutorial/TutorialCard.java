package com.github.creme332.view.tutorial;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

/**
 * Tutorial card displayed in TutorialCenter.
 */
public class TutorialCard extends JPanel {
    private String heading;
    private Icon icon;

    public TutorialCard(String heading, Icon icon) {
        this.heading = heading;
        this.icon = icon;

        // Set layout manager
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(300, 300));
        // set border
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Create image container
        JPanel imageContainer = new JPanel(new BorderLayout());
        imageContainer.setBackground(Color.LIGHT_GRAY);

        // add image to image container
        JLabel image = new JLabel();
        image.setIcon(icon);
        imageContainer.add(image);
        add(imageContainer);

        // add title
        JLabel title = new JLabel(heading);
        title.setBorder(new EmptyBorder(10, 10, 10, 0));
        title.putClientProperty("FlatLaf.style", "font: 110% $semibold.font");
        add(title);
    }

    public String getHeading() {
        return heading;
    }

    public Icon getIcon() {
        return icon;
    }
}
