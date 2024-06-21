package com.github.creme332.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.swing.FontIcon;

public class SideMenuPanel extends JPanel {
    // Labels
    JLabel titleLabel = new JLabel("Side Menu");

    // Buttons
    JButton newCanvasButton = new JButton("New Canvas", FontIcon.of(BootstrapIcons.FILE_EARMARK, 20));
    JButton exportImageButton = new JButton("Export Image", FontIcon.of(BootstrapIcons.IMAGE, 20));
    JButton tutorialsButton = new JButton("Tutorials", FontIcon.of(BootstrapIcons.BOOK, 20));

    // Settings
    JLabel settingsLabel = new JLabel("Settings");
    JLabel gridColorLabel = new JLabel("Grid Color:");
    JComboBox<String> gridColorComboBox = new JComboBox<>(new String[]{"Red", "Green", "Blue"});
    JLabel gridLinesLabel = new JLabel("Toggle Grid Lines:");
    JCheckBox gridLinesCheckBox = new JCheckBox();
    JLabel fontSizeLabel = new JLabel("Font Size:");
    JSlider fontSizeSlider = new JSlider(10, 30, 14);

    JButton closeButton = new JButton(FontIcon.of(BootstrapIcons.X, 20));

    public SideMenuPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // General settings
        this.setBackground(new Color(43, 43, 43));  // Darker background color
        this.setPreferredSize(new Dimension(250, 600));

        // Title Label settings
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Close Button settings
        JPanel closeButtonPanel = new JPanel();
        closeButtonPanel.setBackground(new Color(43, 43, 43));
        closeButtonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbcClose = new GridBagConstraints();
        gbcClose.anchor = GridBagConstraints.NORTHEAST;
        gbcClose.insets = new Insets(10, 10, 0, 10);
        closeButtonPanel.add(closeButton, gbcClose);

        // Adding Title Label and Close Button on the same line
        JPanel titlePanel = new JPanel(new GridBagLayout());
        titlePanel.setBackground(new Color(43, 43, 43));
        GridBagConstraints gbcTitle = new GridBagConstraints();

        gbcTitle.gridx = 0;
        gbcTitle.gridy = 0;
        gbcTitle.weightx = 1.0;
        gbcTitle.anchor = GridBagConstraints.WEST;
        gbcTitle.insets = new Insets(10, 10, 0, 10);
        titlePanel.add(titleLabel, gbcTitle);

        gbcTitle.gridx = 1;
        gbcTitle.weightx = 0;
        gbcTitle.anchor = GridBagConstraints.EAST;
        gbcTitle.insets = new Insets(10, 10, 0, 10);
        titlePanel.add(closeButton, gbcTitle);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(titlePanel, gbc);

        // Button settings
        newCanvasButton.setForeground(Color.WHITE);
        newCanvasButton.setBackground(new Color(60, 63, 65));
        newCanvasButton.setFocusPainted(false);

        exportImageButton.setForeground(Color.WHITE);
        exportImageButton.setBackground(new Color(60, 63, 65));
        exportImageButton.setFocusPainted(false);

        tutorialsButton.setForeground(Color.WHITE);
        tutorialsButton.setBackground(new Color(60, 63, 65));
        tutorialsButton.setFocusPainted(false);

        // Settings Labels settings
        settingsLabel.setForeground(Color.WHITE);
        gridColorLabel.setForeground(Color.WHITE);
        gridLinesLabel.setForeground(Color.WHITE);
        fontSizeLabel.setForeground(Color.WHITE);

        // Adding buttons
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 10, 10, 10);
        add(newCanvasButton, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(10, 10, 10, 10);
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

    public JButton getCloseButton() {
        return closeButton;
    }
}
