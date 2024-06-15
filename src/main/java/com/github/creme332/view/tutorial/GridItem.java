package com.github.creme332.view.tutorial;

import javax.swing.*;
import java.awt.*;

public class GridItem extends JPanel {
    private String heading;
    private String imageHeaderPath;

    private JLabel headingLabel;
    private JLabel imageHeaderLabel;

    public GridItem(String heading, String imageHeaderPath) {
        this.heading = heading;
        this.imageHeaderPath = imageHeaderPath;

        // Initialize the labels
        headingLabel = new JLabel(heading, SwingConstants.CENTER);
        imageHeaderLabel = new JLabel(new ImageIcon(imageHeaderPath), SwingConstants.CENTER);

        // Customize the appearance of the labels if necessary
        headingLabel.setFont(new Font("Arial", Font.BOLD, 14));
        headingLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Set preferred sizes based on the given dimensions
        headingLabel.setPreferredSize(new Dimension(60, 10)); // 6cm x 1cm
        imageHeaderLabel.setPreferredSize(new Dimension(70, 40)); // 7cm x 4cm

        // Set layout manager for this panel
        setLayout(new BorderLayout());

        // Create a panel for the image with padding
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new GridBagLayout());
        imagePanel.setPreferredSize(new Dimension(70, 40)); // 7cm x 4cm
        imagePanel.add(imageHeaderLabel);
        imagePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add labels to the panel
        add(imagePanel, BorderLayout.CENTER);
        add(headingLabel, BorderLayout.SOUTH);
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
        headingLabel.setText(heading);
    }

    public String getImageHeader() {
        return imageHeaderPath;
    }

    public void setImageHeader(String imageHeaderPath) {
        this.imageHeaderPath = imageHeaderPath;
        imageHeaderLabel.setIcon(new ImageIcon(imageHeaderPath));
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(70, 50); // 7cm width x (4cm + 1cm) height
    }
}
