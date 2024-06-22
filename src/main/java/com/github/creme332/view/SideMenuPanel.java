package com.github.creme332.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.swing.FontIcon;

public class SideMenuPanel extends JPanel {
    // Buttons
    JButton newCanvasButton = createButton("New Canvas", BootstrapIcons.FILE_EARMARK);
    JButton exportImageButton = createButton("Export Image", BootstrapIcons.IMAGE);
    JButton tutorialsButton = createButton("Tutorials", BootstrapIcons.BOOK);

    // Settings
    JLabel settingsLabel = new JLabel("Settings");
    JLabel gridColorLabel = new JLabel("Grid Color:");
    JComboBox<String> gridColorComboBox = new JComboBox<>(new String[] { "Red", "Green", "Blue" });
    JLabel gridLinesLabel = new JLabel("Toggle Grid Lines:");
    JCheckBox gridLinesCheckBox = new JCheckBox();
    JLabel fontSizeLabel = new JLabel("Font Size:");
    JSlider fontSizeSlider = new JSlider(10, 30, 14);

    public SideMenuPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // General settings
        this.setPreferredSize(new Dimension(275, 600));

        // Adding buttons
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 10, 10, 10);
        gbc.weightx = 1;

        add(newCanvasButton, gbc);

        gbc.gridy++;
        add(exportImageButton, gbc);

        gbc.gridy++;
        add(tutorialsButton, gbc);

        // Section for settings
        gbc.gridy++;
        gbc.insets = new Insets(20, 10, 10, 10);
        add(settingsLabel, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        add(gridColorLabel, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 10, 10, 10);
        add(gridColorComboBox, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(10, 10, 5, 10);
        add(gridLinesLabel, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 10, 10, 10);
        add(gridLinesCheckBox, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(10, 10, 5, 10);
        add(fontSizeLabel, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 10, 10, 10);
        add(fontSizeSlider, gbc);
    }

    private JButton createButton(String text, BootstrapIcons icon) {
        JButton button = new JButton(text, FontIcon.of(icon, 20));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setIconTextGap(10);
        button.setBorder(new EmptyBorder(5, 5, 5, 5));
        return button;
    }
}
