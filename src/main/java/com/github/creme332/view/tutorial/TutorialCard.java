package com.github.creme332.view.tutorial;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Tutorial card displayed in TutorialCenter.
 */
public class TutorialCard extends JPanel {
    private String heading;
    private transient Icon icon;

    /**
     * Dimensions of tutorial card.
     */
    public static final Dimension DIMENSION = new Dimension(450, 300);

    /**
     * Dimensions of image on tutorial card.
     */
    public static final Dimension IMAGE_DIMENSION = new Dimension(DIMENSION.width, DIMENSION.height - 100);

    public TutorialCard(String heading, Icon icon) {
        this.heading = heading;
        this.icon = icon;

        // Set layout manager
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(DIMENSION);

        // set border
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Create image container
        JPanel imageContainer = new JPanel(new BorderLayout());
        imageContainer.setPreferredSize(new Dimension(DIMENSION.width, DIMENSION.height - IMAGE_DIMENSION.height));

        // add image to image container
        JLabel image = new JLabel();
        image.setIcon(icon);
        imageContainer.add(image);
        add(imageContainer);

        // create a title container with only a top border
        JPanel titleContainer = new JPanel();
        titleContainer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));

        // add title to title container
        JLabel title = new JLabel(heading);
        titleContainer.add(title);
        title.setBorder(new EmptyBorder(10, 10, 10, 0));
        title.putClientProperty("FlatLaf.style", "font: 110% $semibold.font");
        add(titleContainer);

        // Add mouse listener for hover effect
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBorder(new LineBorder(Color.BLACK, 3));
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBorder(BorderFactory.createLineBorder(Color.BLACK));
                setCursor(Cursor.getDefaultCursor());
            }
        });
    }

    public String getHeading() {
        return heading;
    }

    public Icon getIcon() {
        return icon;
    }
}
