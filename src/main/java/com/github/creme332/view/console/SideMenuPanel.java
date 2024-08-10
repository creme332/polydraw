package com.github.creme332.view.console;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.swing.FontIcon;

public class SideMenuPanel extends JPanel {
    private JButton newCanvasButton = createButton("New", BootstrapIcons.PLUS);
    private JButton exportImageButton = createButton("Export Image", BootstrapIcons.IMAGE);
    private JButton tutorialsButton = createButton("Tutorials", BootstrapIcons.BOOK);
    private JButton saveToComputerButton = createButton("Save to your computer", BootstrapIcons.SAVE);
    private JButton reportProblemButton = createButton("Report problem", BootstrapIcons.BUG);
    private JButton aboutButton = createButton("About", BootstrapIcons.INFO);
    private JButton openFileButton = createButton("Open", BootstrapIcons.FOLDER2_OPEN);

    private JCheckBox gridLinesCheckBox;
    private JCheckBox axesCheckbox;
    private JComboBox<String> fontSizeSelector;
    private JButton resetCanvasSettingsButton;

    public static final int BORDER_SIZE = 1;
    public static final int PREFERRED_WIDTH = 275;
    private static final String PROJECT_INFO = """
            Polydraw is an application for drawing rasterized shapes, inspired by Geogebra Classic.

            For more information, visit our GitHub page: https://github.com/creme332/polydraw/.

            Version: 0.0
            License: MIT
            """;

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

        // Display project info in a dialog when about button is clicked
        aboutButton.addActionListener(e -> {
            JDialog aboutDialog = new JOptionPane(PROJECT_INFO,
                    JOptionPane.INFORMATION_MESSAGE).createDialog("About");
            aboutDialog.setVisible(true);
        });
    }

    private JPanel createSettingsPanel() {
        JPanel settingsPanel = new JPanel(new BorderLayout());
        settingsPanel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
        JLabel heading = new JLabel("Canvas Settings", SwingConstants.HORIZONTAL);
        heading.putClientProperty("FlatLaf.style", "font: $h4.font");
        settingsPanel.add(heading, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        settingsPanel.add(formPanel, BorderLayout.CENTER);

        GridBagConstraints formPanelGBC = new GridBagConstraints();
        formPanelGBC.fill = GridBagConstraints.BOTH;
        formPanelGBC.insets = new Insets(10, 0, 10, 10);

        formPanelGBC.gridx = 0;
        formPanelGBC.gridy = 0;
        JLabel gridColorLabel = new JLabel("Guidelines", SwingConstants.LEFT);
        formPanel.add(gridColorLabel, formPanelGBC);

        formPanelGBC.gridx = 1;
        formPanelGBC.gridy = 0;
        gridLinesCheckBox = new JCheckBox();
        formPanel.add(gridLinesCheckBox, formPanelGBC);

        formPanelGBC.gridx = 0;
        formPanelGBC.gridy = 1;
        JLabel gridLinesLabel = new JLabel("Axes", SwingConstants.LEFT);
        formPanel.add(gridLinesLabel, formPanelGBC);

        formPanelGBC.gridx = 1;
        formPanelGBC.gridy = 1;
        axesCheckbox = new JCheckBox();
        formPanel.add(axesCheckbox, formPanelGBC);

        formPanelGBC.gridx = 0;
        formPanelGBC.gridy = 2;
        JLabel fontSizeLabel = new JLabel("Font size", SwingConstants.LEFT);
        formPanel.add(fontSizeLabel, formPanelGBC);

        formPanelGBC.gridx = 1;
        formPanelGBC.gridy = 2;
        fontSizeSelector = new JComboBox<>(
                new String[] { "12", "16", "18", "20", "24", "28" });
        formPanel.add(fontSizeSelector, formPanelGBC);

        JPanel resetButtonContainer = new JPanel(new BorderLayout());
        resetButtonContainer.setBorder(new EmptyBorder(0, 0, 0, 10));

        resetCanvasSettingsButton = new JButton("Reset");
        resetCanvasSettingsButton.putClientProperty("FlatLaf.style", "background: #FFB8B8");

        resetButtonContainer.add(resetCanvasSettingsButton);
        settingsPanel.add(resetButtonContainer, BorderLayout.SOUTH);

        return settingsPanel;
    }

    private JPanel createButtonsPanel() {
        /**
         * A vertical panel with buttons.
         */
        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        gbc.gridy = 0;
        buttonsPanel.add(newCanvasButton, gbc);

        gbc.gridy++;
        buttonsPanel.add(saveToComputerButton, gbc);

        gbc.gridy++;
        buttonsPanel.add(openFileButton, gbc);

        gbc.gridy++;
        buttonsPanel.add(exportImageButton, gbc);

        gbc.gridy++;
        buttonsPanel.add(tutorialsButton, gbc);

        gbc.gridy++;
        buttonsPanel.add(reportProblemButton, gbc);

        gbc.gridy++;
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

    public void handleCanvasClear(ActionListener listener) {
        newCanvasButton.addActionListener(listener);
    }

    public void handleImageExport(ActionListener listener) {
        exportImageButton.addActionListener(listener);
    }

    public void handleTutorialAction(ActionListener listener) {
        tutorialsButton.addActionListener(listener);
    }

    public void handleExportCanvasAsFile(ActionListener listener) {
        saveToComputerButton.addActionListener(listener);
    }

    public void handleReportAction(ActionListener listener) {
        reportProblemButton.addActionListener(listener);
    }

    public void handleOpenFile(ActionListener listener) {
        openFileButton.addActionListener(listener);
    }

    public void handleCanvasSettingsReset(ActionListener listener) {
        resetCanvasSettingsButton.addActionListener(listener);
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
