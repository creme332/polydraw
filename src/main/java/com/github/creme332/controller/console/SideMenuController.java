package com.github.creme332.controller.console;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import com.github.creme332.model.AppState;
import com.github.creme332.model.CanvasModel;
import com.github.creme332.model.Screen;
import com.github.creme332.utils.DesktopApi;
import com.github.creme332.view.console.SideMenuPanel;

/**
 * Controller responsible for managing sidebar in CanvasConsole.
 */
public class SideMenuController {

    private static final String PROJECT_INFO = """
            Polydraw is an application for drawing rasterized shapes, inspired by Geogebra Classic.

            For more information, visit our GitHub page: https://github.com/creme332/polydraw/.

            Version: 0.0
            License: MIT
            """;

    private SideMenuPanel sidebar;
    private AppState app;

    public SideMenuController(AppState app, SideMenuPanel sidebar) {
        this.app = app;
        this.sidebar = sidebar;

        refreshCanvasSettingsUI();

        // Initialize button listeners
        initializeButtonListeners(app);

        // consume click events on sidebar otherwise the events will happen on the
        // canvas below it
        sidebar.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                e.consume();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                e.consume();
            }
        });
    }

    /**
     * Updates default values in canvas settings to match values from canvas model
     */
    public void refreshCanvasSettingsUI() {
        // set default values in canvas settings
        sidebar.getGridLinesCheckBox().setSelected(app.getCanvasModel().isGuidelinesEnabled());
        sidebar.getAxesCheckBox().setSelected(app.getCanvasModel().isAxesVisible());
        sidebar.getFontSizeSelector().setSelectedItem(String.format("%d", app.getCanvasModel().getLabelFontSize()));
    }

    private void initializeButtonListeners(AppState app) {
        CanvasModel canvasModel = app.getCanvasModel();

        // New button
        sidebar.getNewCanvasButton().addActionListener(e -> {
            int confirmation = JOptionPane.showConfirmDialog(null,
                    "Do you want to clear the current canvas? This action is irreversible.",
                    "Clear canvas",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

            if (confirmation == JOptionPane.YES_OPTION) {
                canvasModel.getShapeManager().reset();
            }
        });

        // Export Image button
        sidebar.getExportImageButton().addActionListener(e -> app.startPrintingProcess());

        // Tutorials button
        sidebar.getTutorialsButton().addActionListener(e -> app.switchScreen(Screen.TUTORIAL_SCREEN));

        // Report Problem button
        sidebar.getReportProblemButton().addActionListener(e -> {
            try {
                DesktopApi.browse(new java.net.URI("https://github.com/creme332/polydraw/issues"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // About button
        sidebar.getAboutButton().addActionListener(e -> {
            JDialog aboutDialog = new JOptionPane(PROJECT_INFO,
                    JOptionPane.INFORMATION_MESSAGE).createDialog("About");
            aboutDialog.setVisible(true);
        });

        // Guidelines checkbox
        sidebar.getGridLinesCheckBox().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                canvasModel.setGuidelinesEnabled(!canvasModel.isGuidelinesEnabled());
            }
        });

        // Axes checkbox
        sidebar.getAxesCheckBox().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                canvasModel.setAxesVisible(!canvasModel.isAxesVisible());
            }
        });

        sidebar.getFontSizeSelector().addActionListener(
                e -> canvasModel
                        .setLabelFontSize(
                                Integer.valueOf(
                                        (String) sidebar.getFontSizeSelector()
                                                .getSelectedItem())));

        // Reset button
        sidebar.getResetButton().addActionListener(e -> {
            int confirmation = JOptionPane.showConfirmDialog(null,
                    "Do you want to reset the canvas settings to its default values? This action is irreversible.",
                    "Reset canvas settings",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

            if (confirmation == JOptionPane.YES_OPTION) {
                boolean settingsChanged = false;
                // Reset guidelines checkbox and model
                if (!canvasModel.isGuidelinesEnabled()) {
                    settingsChanged = true;
                    canvasModel.setGuidelinesEnabled(true);
                }

                // Reset axes checkbox and model
                if (!canvasModel.isAxesVisible()) {
                    settingsChanged = true;
                    canvasModel.setAxesVisible(true);
                }

                // Reset font size
                if (canvasModel.getLabelFontSize() != CanvasModel.DEFAULT_LABEL_FONT_SIZE) {
                    settingsChanged = true;
                    canvasModel.setLabelFontSize(CanvasModel.DEFAULT_LABEL_FONT_SIZE);
                }

                if (settingsChanged)
                    refreshCanvasSettingsUI();
            }
        });
    }
}
