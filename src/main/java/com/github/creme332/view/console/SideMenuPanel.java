package com.github.creme332.view.console;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.swing.FontIcon;

public class SideMenuPanel extends JPanel {
    JButton newCanvasButton = createButton("New", BootstrapIcons.PLUS);
    JButton exportImageButton = createButton("Export Image", BootstrapIcons.IMAGE);
    JButton tutorialsButton = createButton("Tutorials", BootstrapIcons.BOOK);
    JButton saveToComputerButton = createButton("Save to your computer", BootstrapIcons.SAVE);
    JButton reportProblemButton = createButton("Report problem", BootstrapIcons.BUG);
    JButton aboutButton = createButton("About", BootstrapIcons.INFO);
    JButton openButton = createButton("Open", BootstrapIcons.FOLDER2_OPEN);

    JCheckBox gridLinesCheckBox;
    JCheckBox axesCheckbox;
    JComboBox<String> fontSizeSelector;
    JButton resetButton;

    public static final int BORDER_SIZE = 1;
    public static final int PREFERRED_WIDTH = 275;

    public SideMenuPanel() {
        setLayout(new BorderLayout());

        JPanel container = new JPanel(new BorderLayout());
        container.setOpaque(false);
        this.setPreferredSize(new Dimension(PREFERRED_WIDTH + BORDER_SIZE, getHeight()));

        // add a left border to the sidebar to prevent it from blending with canvas
        MatteBorder leftBorder = BorderFactory.createMatteBorder(
                0, BORDER_SIZE, 0, 0, new Color(226, 226, 226));
        container.setBorder(leftBorder);

        container.add(createButtonsPanel(), BorderLayout.NORTH);
        JPanel settingsPanel = createSettingsPanel();
        container.add(settingsPanel, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(container);
        this.add(scrollPane);
    }

    private JPanel createSettingsPanel() {
        JPanel settingsPanel = new JPanel(new BorderLayout());
        settingsPanel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
        JLabel heading = new JLabel("Canvas Settings", SwingConstants.HORIZONTAL);
        heading.putClientProperty("FlatLaf.style", "font: $h3.font");
        settingsPanel.add(heading, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        settingsPanel.add(formPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 0, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel gridColorLabel = new JLabel("Guidelines", SwingConstants.LEFT);
        formPanel.add(gridColorLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gridLinesCheckBox = new JCheckBox();
        formPanel.add(gridLinesCheckBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel gridLinesLabel = new JLabel("Axes", SwingConstants.LEFT);
        formPanel.add(gridLinesLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        axesCheckbox = new JCheckBox();
        formPanel.add(axesCheckbox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel fontSizeLabel = new JLabel("Font size", SwingConstants.LEFT);
        formPanel.add(fontSizeLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        fontSizeSelector = new JComboBox<>(
                new String[] { "12", "16", "18", "20", "24", "28" });
        formPanel.add(fontSizeSelector, gbc);

        resetButton = new JButton("Reset");
        resetButton.putClientProperty("FlatLaf.style", "background: #FFB8B8");
        settingsPanel.add(resetButton, BorderLayout.SOUTH);

        return settingsPanel;
    }

    private JPanel createButtonsPanel() {
        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        gbc.gridy = 0;
        buttonsPanel.add(newCanvasButton, gbc);

        gbc.gridy = 1;
        buttonsPanel.add(saveToComputerButton, gbc);

        gbc.gridy = 2;
        buttonsPanel.add(openButton, gbc);

        gbc.gridy = 3;
        buttonsPanel.add(exportImageButton, gbc);

        gbc.gridy = 4;
        buttonsPanel.add(tutorialsButton, gbc);

        gbc.gridy = 5;
        buttonsPanel.add(reportProblemButton, gbc);

        gbc.gridy = 6;
        buttonsPanel.add(aboutButton, gbc);
        return buttonsPanel;
    }

    private JButton createButton(String text, BootstrapIcons icon) {
        final int buttonHeight = 60;
        final int iconSize = 22;
        JButton button = new JButton(text, FontIcon.of(icon, iconSize));
        button.setPreferredSize(new Dimension(PREFERRED_WIDTH - BORDER_SIZE, buttonHeight));
        button.setBorderPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setIconTextGap(10);
        button.setBorder(new EmptyBorder(5, 5, 5, 5));
        return button;
    }

    public JButton getNewCanvasButton() {
        return newCanvasButton;
    }

    public JButton getExportImageButton() {
        return exportImageButton;
    }

    public JButton getTutorialsButton() {
        return tutorialsButton;
    }

    public JButton getSaveToComputerButton() {
        return saveToComputerButton;
    }

    public JButton getReportProblemButton() {
        return reportProblemButton;
    }

    public JButton getAboutButton() {
        return aboutButton;
    }

    public JButton getResetButton() {
        return resetButton;
    }

    public JCheckBox getGridLinesCheckBox() {
        return gridLinesCheckBox;
    }

    public JCheckBox getAxesCheckBox() {
        return axesCheckbox;
    }

    public JComboBox<String> getFontSizeSelector() {
        return fontSizeSelector;
    }
}
